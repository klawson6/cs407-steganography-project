import javafx.scene.control.Alert;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.Arrays;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;


/**
 * The model to decode a payload in a cover image.
 */
public class DecodeModel {

    // A reference to a controller for the decode GUI
    private DecodeController decodeController;
    // A reference to the cover image object
    private AppFileInterface coverImg;

    /**
     * Initialises the model when the controller calls this.
     * Creates instances of a cover image, as well as a reference to the decode controller.
     *
     * @param controller
     */
    protected void setController(DecodeController controller) {
        this.decodeController = controller;
        coverImg = new CoverImg();
    }

    /**
     * Checks and sets a passed file as the cover image.
     *
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
        decodeController.setCoverImg(f);
    }

    /**
     * Will do the decoding, huzzah!
     */
    public void decodeSteganograph() {
        // TODO add in the Steganograph generation @FinFin
        if (!coverImg.isSet()) {
            new Alert(ERROR, "Decoding failed, no image supplied").show();
            return;
        }


        byte[] payload = coverImg.getBitSet().toByteArray();
        payload = Arrays.copyOfRange(payload, 54, payload.length);


        int lengthnum = ByteBuffer.wrap(extractBytes(Arrays.copyOfRange(payload, 0, 32))).getInt();
        if (lengthnum < 0) {
            new Alert(ERROR, "Payload decoding failed. Decoded length was < 0").show();
            return;
        }

        if (lengthnum > (payload.length)) {
            new Alert(ERROR, "Payload decoding failed. Decoded length was greater than the size of the file").show();
            return;
        }

        byte[] extension = extractBytes(Arrays.copyOfRange(payload, 32, 96));

        String extensionType = new String(extension);
        String filename = "decoded." + extensionType;
        //String filename = "decoded.mp3";
        payload = extractBytes(Arrays.copyOfRange(payload, 96, lengthnum + 96));
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filename);
            fileOutputStream.write(payload);
            new Alert(INFORMATION, "Payload successfully decoded to the project directory as " + filename + ".").show();
        } catch (FileNotFoundException e) {
            new Alert(ERROR, "File Not Found Exception occured").show();
        } catch (IOException e) {
            new Alert(ERROR, "IO Exception occured").show();
        }
    }


    private byte[] extractBytes(byte[] bytes) {

        byte[] returnByteArray = new byte[bytes.length / 8];
        for (int i = 0; i < bytes.length; i += 8) {
            byte newByte = 0;
            for (int j = 0; j < 8; j++) {
                //newByte = (byte) (newByte + ((bytes[i+j] << (7-j) & (int) Math.pow(2, (7-j)))));
                newByte = (byte) (newByte + ((bytes[i + j] & 0x1) << (7 - j)));
            }
            returnByteArray[i / 8] = newByte;
        }
        return returnByteArray;
    }


}
