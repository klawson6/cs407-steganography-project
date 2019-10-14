import javafx.scene.control.Alert;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.BitSet;

import static javafx.scene.control.Alert.AlertType.*;

/**
 * The model to encode a payload in a cover image.
 * Handles verifying if a cover image is valid and if a payload fits in it.
 */
public class EncodeModel {

    // A reference to a controller for the encode GUI
    private EncodeController encodeController;
    // A reference to the payload object
    private AppFileInterface payload;
    // A reference to the cover image object
    private AppFileInterface coverImg;
    // Size of header (in bits)
    static final int HEADER_SIZE = 54*8;
    static final int PL_LENGTH_SIZE = 32*8;
    static final int EXT_SIZE = 64*8;


    /**
     * Initialises the model when the controller calls this.
     * Creates instances of a payload and cover image, as well as a reference to the encode controller.
     * @param controller
     */
    protected void setController(EncodeController controller) {
        this.encodeController = controller;
        payload = new Payload();
        coverImg = new CoverImg();
    }

    /**
     * Checks and sets a passed in file as the payload.
     * @param f
     */
    public void stagePayload(File f) {
        if (f == null) return;
        System.out.println(f.getName() + "\nStaged to be hidden in a file.");
        // Sets the file in the Payload object
        if (payload.setFile(f) == null) {
            new Alert(ERROR);
            return;
        }
        // Displays the filename on the GUI
        encodeController.setPayload(f);
    }

    /**
     * Checks and sets a passed file as the cover image.
     * @param f
     */
    public void stageCoverImg(File f) {
        if (f == null) return;
        System.out.println(f.getName() + "\nStaged to conceal a file.");
        // Sets the file in the coverImg object
        if (coverImg.setFile(f) == null) {
            new Alert(ERROR, "The cover image is not a 24 bit .bmp or .dib file. Please select another file.").show();
            return;
        }
        try {
            // Display the cover image in the encode GUI
            encodeController.setCoverImg(new Image(new FileInputStream(f)));
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(ERROR, "The cover image is not a 24 bit .bmp or .dib file. Please select another file.").show();
        }
    }

    /**
     * Checks if the payload and cover image are compatible with one another.
     * Checks by comparing the payload size to the available space in the cover image.
     * @return
     */
    private boolean checkCompatibility() {
        System.out.println("Payload size: " + payload.getBitSize());
        System.out.println("Cover Image: " + coverImg.getBitSize());
        // Check if both files have been selected
        if (!(payload.isSet() && coverImg.isSet())) return false;
        // Check if there is space for the payload in the cover image
        if (payload.getBitSize() > coverImg.getBitSize()/8) {
            new Alert(ERROR, "The payload file is too large to be hidden in the cover image. Please select a smaller payload file or a larger cover image.").show();
            return false;
        }
        return true;
    }

    /**
     * Will do the encoding, huzzah!
     */
    public void encodeSteganograph() {
        if (!checkCompatibility()) return;
        // TODO add in the Steganograph generation @ChloChlo, modify the original image, below saves to separate file.
        try {
            // Getting BitSet of original cover image for encoding.
            BitSet bitCoverImg = coverImg.getBitSet();
            // Create a BitSet, of size 32 bits, of the length of the payload
            BitSet payloadLength = BitSet.valueOf(ByteBuffer.allocate(4).putInt(payload.getBitSize()).array());
            //TODO check if null

            // Encodes payload length into cover image
            encodeLSB(bitCoverImg, payloadLength, HEADER_SIZE + 7, HEADER_SIZE + PL_LENGTH_SIZE);
            //System.out.println("Old size: " + coverImg.getBitSize() + " vs new size 1: " + bitCoverImg.size());

            // Creating a bitSet of the payload's file extension
            String ext = payload.getFile().getName().substring(payload.getFile().getName().lastIndexOf('.') + 1);
            System.out.println("Extension: " + ext);
            byte[] byteExt = ext.getBytes();
            BitSet bsExt = BitSet.valueOf(byteExt);
            // Padding out extension BitSet to 64 bits
            bsExt.or(new BitSet(64));  // TODO add length check
            System.out.println("Ext length: " + bsExt.size());

            // Encodes payload's file extension into cover image
            encodeLSB(bitCoverImg, bsExt, HEADER_SIZE + PL_LENGTH_SIZE + 7, HEADER_SIZE + PL_LENGTH_SIZE + EXT_SIZE);
            //System.out.println("Old size: " + coverImg.getBitSize() + " vs new size 2: " + bitCoverImg.size());

            // Encodes payload into cover image. If there is no more payload to encode, the remaining cover image bits are  left untouched.
            encodeLSB(bitCoverImg, payload.getBitSet(), HEADER_SIZE + PL_LENGTH_SIZE + EXT_SIZE + 7,  HEADER_SIZE + PL_LENGTH_SIZE + EXT_SIZE + 8*payload.getBitSize());

            //System.out.println("Old size: " + coverImg.getBitSize() + " vs new size: " + bitCoverImg.size());

            new FileOutputStream("encoded_" + coverImg.getFile().getName()).write(bitCoverImg.toByteArray());
            new Alert(INFORMATION, "Payload encoded successfully in the cover image!\nSaved to project directory as " + "encoded_" + coverImg.getFile().getName()).show();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void encodeLSB(BitSet cover, BitSet itemToEncode, int startIndex, int endIndexPlusOne){
        int count = 0;
        for (int i = startIndex; i < endIndexPlusOne; i+=5){
            //Replaces 4 LSBs in each colour of each pixel with the bits representing payload size
            cover.set(i, itemToEncode.get(count));
            //Counts how many bits we've encoded
            count++;
        }
    }
}
