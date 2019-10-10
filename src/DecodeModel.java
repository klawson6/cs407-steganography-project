import javafx.scene.control.Alert;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static javafx.scene.control.Alert.AlertType.ERROR;

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
     * @param controller
     */
    protected void setController(DecodeController controller) {
        this.decodeController = controller;
        coverImg = new CoverImg();
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
            decodeController.setCoverImg(new Image(new FileInputStream(f)));
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(ERROR, "The cover image is not a 24 bit .bmp or .dib file. Please select another file.").show();
        }
    }

    /**
     * Will do the decoding, huzzah!
     */
    public void decodeSteganograph() {
        // TODO add in the Steganograph generation @FinFin
    }
}
