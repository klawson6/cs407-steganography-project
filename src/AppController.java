import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
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

    @FXML
    private Pane coverContainer;

    @FXML
    private Button hideButton;

    @FXML
    private Button switchMode;

    @FXML
    private Label fileChosen;
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

    private void addListeners() {
        upCoverImg.setOnMouseClicked(this::onCoverUpload);
        upPayload.setOnMouseClicked(this::onPayloadUpload);
        // TODO add steganize button click event
    }

    public void setCoverImg(Image img) {
        upCoverImg.setImage(img);
        if (img.getHeight() > img.getWidth()) {
            upCoverImg.setFitHeight(coverContainer.getHeight());
            upCoverImg.setFitWidth(-1);
            upCoverImg.relocate((coverContainer.getWidth()/2)-(img.getWidth()/img.getHeight()*upCoverImg.getFitHeight()/2),0);
        } else {
            upCoverImg.setFitWidth(coverContainer.getWidth());
            upCoverImg.setFitHeight(-1);
            upCoverImg.relocate(0, (coverContainer.getHeight()/2)-(img.getHeight()/img.getWidth()*upCoverImg.getFitWidth()/2));
        }
    }

    public void setPayload(File f) {
        fileChosen.setText(f.getName());
    }
}
