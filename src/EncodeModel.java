import javafx.scene.control.Alert;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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
        if (payload.getBitSize() > coverImg.getBitSize()) {
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
        // A way to save to new file, can be added to, removed, modified whatevs. just had this for testing.
        try {
            new FileOutputStream("encoded_" + coverImg.getFile().getName()).write(new FileInputStream(coverImg.getFile()).readAllBytes());
            new Alert(INFORMATION, "Payload encoded successfully in the cover image!\nSaved to project directory as " + "encoded_" + coverImg.getFile().getName()).show();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
