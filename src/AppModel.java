import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static javafx.scene.control.Alert.AlertType.ERROR;

public class AppModel {

    private AppController controller;

    private AppFileInterface payload;

    private AppFileInterface coverImg;

    private ObservableList<AppFileInterface> files;

    protected void setController(AppController controller) {
        this.controller = controller;
        payload = new Payload();
        coverImg = new CoverImg();
    }

    public void stagePayload(File f) {
        if (f == null) return;
        System.out.println(f.getName() + "\nStaged to be hidden in a file.");
        if (payload.setFile(f) == null) {
            new Alert(ERROR);
            return;
        }
        controller.setPayload(f);
    }

    public void stageCoverImg(File f) {
        if (f == null) return;
        System.out.println(f.getName() + "\nStaged to conceal a file.");
        if (coverImg.setFile(f) == null) {
            new Alert(ERROR, "The cover image is not a 24 bit .bmp or .dib file. Please select another file.").show();
            return;
        }
        try {
            controller.setCoverImg(new Image(new FileInputStream(f)));
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(ERROR, "The cover image is not a 24 bit .bmp or .dib file. Please select another file.").show();
        }
    }

    private boolean checkCompatibility() {
        System.out.println("Payload size: " + payload.getBitSize());
        System.out.println("Cover Image: " + coverImg.getBitSize());
        if (!(payload.isSet() && coverImg.isSet())) return false;
        if (payload.getBitSize() > coverImg.getBitSize()) {
            new Alert(ERROR, "The payload file is too large to be hidden in the cover image. Please select a smaller payload file or a larger cover image.").show();
            return false;
        }
        return true;
    }

    public void generateSteganograph() {
        if (!checkCompatibility()) return;
        // TODO add in the Steganograph generation @ChloChlo
    }
}
