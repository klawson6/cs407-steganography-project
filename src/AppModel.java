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

    private boolean set;

    private ObservableList<AppFileInterface> files;

    protected void setController(AppController controller) {
        this.controller = controller;
        payload = new Payload();
        coverImg = new CoverImg();
        set = false;
    }

    public void stagePayload(File f) {
        if (f == null) return;
        System.out.println(f.getName() + "\nStaged to be hidden in a file.");
        if (payload.setFile(f) == null) {
            new Alert(ERROR);
            return;
        } else if (coverImg.isSet()) {
            checkCompatibility();
        }
        controller.setPayload(f);
    }

    public void stageCoverImg(File f) {
        if (f == null) return;
        System.out.println(f.getName() + "\nStage to conceal a file.");
        if (coverImg.setFile(f) == null) {
            new Alert(ERROR, "The cover image is not a 24 bit .bmp or .dib file. Please select another file.").show();
            return;
        } else if (payload.isSet()) {
            checkCompatibility();
        }
        try {
            controller.setCoverImg(new Image(new FileInputStream(f)));
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(ERROR, "The cover image is not a 24 bit .bmp or .dib file. Please select another file.").show();
        }
    }

    private void checkCompatibility() {
        System.out.println("Payload size: " + payload.getBitSize());
        System.out.println("Cover Image: " + coverImg.getBitSize());
        if (payload.getBitSize() > coverImg.getBitSize()) {
            new Alert(ERROR, "The payload file is too large to be hidden in the cover image. Please select a smaller payload file or larger cover image.").show();

        }
    }

    // TODO Check if payload fits in cover
}
