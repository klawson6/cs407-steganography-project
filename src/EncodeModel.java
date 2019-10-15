import javafx.scene.control.Alert;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

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
    static final int HEADER_SIZE = 54;
    static final int PL_LENGTH_SIZE = 32;
    static final int EXT_SIZE = 64;


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
        System.out.println("Payload size: " + payload.getByteArraySize());
        System.out.println("Cover Image: " + coverImg.getByteArraySize());
        // Check if both files have been selected
        if (!(payload.isSet() && coverImg.isSet())) return false;
        // Check if there is space for the payload in the cover image
        if (payload.getByteArraySize() > coverImg.getByteArraySize()/8) {
            new Alert(ERROR, "The payload file is too large to be hidden in the cover image. Please select a smaller payload file or a larger cover image.").show();
            return false;
        }
        return true;
    }

    /**
     * Encodes the size, extension and data of the payload into the cover image.
     * @param
     * @return
     */
    public void encodeSteganograph() {
        if (!checkCompatibility()) return;

        try {
            // Getting byte array of original cover image for encoding.
            byte[] newCover = coverImg.getByteArray();

            // Create a byte array, of size 32 bits, of the length of the payload
            byte[] payloadLength = ByteBuffer.allocate(4).putInt(payload.getByteArraySize()*8).array();

            // Encodes payload length into cover image
            encodeLSB(newCover, payloadLength, HEADER_SIZE, HEADER_SIZE + PL_LENGTH_SIZE);

            // Creating a byte array of the payload's file extension
            String ext = payload.getFile().getName().substring(payload.getFile().getName().lastIndexOf('.') + 1);
            byte[] byteExt = new byte[8];
            System.arraycopy(ext.getBytes(), 0, byteExt, 8-ext.getBytes().length, ext.getBytes().length);

            // Encodes payload's file extension into cover image
            encodeLSB(newCover, byteExt, HEADER_SIZE + PL_LENGTH_SIZE, HEADER_SIZE + PL_LENGTH_SIZE + EXT_SIZE);

            // Encodes payload into cover image. If there is no more payload to encode, the remaining cover image bits are  left untouched.
            encodeLSB(newCover, payload.getByteArray(), HEADER_SIZE + PL_LENGTH_SIZE + EXT_SIZE,  HEADER_SIZE + PL_LENGTH_SIZE + EXT_SIZE + payload.getByteArraySize()*8);

            new FileOutputStream("encoded_" + coverImg.getFile().getName()).write(newCover);
            new Alert(INFORMATION, "Payload encoded successfully in the cover image!\nSaved to project directory as " + "encoded_" + coverImg.getFile().getName()).show();
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    /**
     * Replaces least significant bit of each byte in cover image data with the item you wish to encode
     * @param cover
     * @param itemToEncode
     * @param startIndex
     * @param endIndexPlusOne
     * @return
     */
    private void encodeLSB(byte[] cover, byte[] itemToEncode, int startIndex, int endIndexPlusOne){
        int count = 0;

        //Replaces LSB in each colour of each pixel with the bits to be encoded
        for (int i = startIndex; i < endIndexPlusOne; i++){
            // Check we're not encoding past the limit
            if (count >= itemToEncode.length){
                break;
            }

            for (int j = 0; j < 8; j++) {
                if (i >= endIndexPlusOne){
                    break;
                }
                // Determines whether bit to be encoded is 0 or 1
                byte b = (byte) ((byte) (itemToEncode[count] >> (7 - j)) & 0x1);
                // Encodes if bit is 1
                if (b == 0x1) {
                    cover[i] = (byte) (cover[i] | b);
                }
                // Encodes if bit is 0
                else{
                    cover[i] = (byte) (cover[i] & ~0x1);
                }
                if (j != 7) {
                    i++;
                }
            }

            //Counts how many bytes we've encoded
            count++;
        }
    }
}
