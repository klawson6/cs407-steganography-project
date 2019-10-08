import javafx.scene.control.Alert;

import java.io.File;

import static javafx.scene.control.Alert.AlertType.ERROR;

public class AppModel {

    private AppController controller;

    private AppFileInterface payload;

    private AppFileInterface coverImg;

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
        }
    }

    public void stageCoverImg(File f) {
        if (f == null) return;
        System.out.println(f.getName() + "\nStage to conceal a file.");
        if (coverImg.setFile(f) == null) {
            new Alert(ERROR, "The cover image is not a 24 bit .bmp or .dib file. Please select another file.").show();
        }
    }

    // TODO Check if payload fits in cover
}
