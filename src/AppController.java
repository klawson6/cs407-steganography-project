import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    @FXML
    private GridPane mainPane;

    @FXML
    private Button upPayload;

    @FXML
    private ImageView upCoverImg;

    private AppModel model;

    private void onPayloadUpload(MouseEvent event) {
        model.stagePayload(openFileChooser(mainPane.getScene().getWindow()));
    }

    private void onCoverUpload(MouseEvent event) {
        model.stageCoverImg(openFileChooser(mainPane.getScene().getWindow()));
    }

    private File openFileChooser(Window w) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select a file");
        return fc.showOpenDialog(w);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        model = new AppModel();
        model.setController(this);
        addListeners();
    }

    private void addListeners(){
        upCoverImg.setOnMouseClicked(this::onCoverUpload);
        upPayload.setOnMouseClicked(this::onPayloadUpload);
        // TODO add steganize button click event
    }
}
