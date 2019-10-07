import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;

public class AppController {

    @FXML
    private GridPane mainPane;

    @FXML
    private Button upFileButton;

    @FXML
    private ImageView upCoverImg;

    private DummyHandler dh;

//    public AppController(DummyHandler dh){
//        //this.dh = dh;
//    }

    @FXML
    protected void handleFileUpload(ActionEvent event) {
        Window w = mainPane.getScene().getWindow();

        if (event.getSource().equals(upFileButton)) {
            //dh.stageFile(openFileChooser(w));
        } else if (event.getSource().equals(upCoverImg)) {
            //dh.stageCoverImg(openFileChooser(w));
        }
    }

    private File openFileChooser(Window w) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select a file");
        return fc.showOpenDialog(w);
    }
}
