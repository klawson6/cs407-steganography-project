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
    private Button genSteg;

    @FXML
    private Pane switchMode;

    @FXML
    private Label fileChosen;

    @FXML
    private ImageView switchImg;

    private AppModel model;

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
        upCoverImg.setOnMouseClicked(mouseEvent -> model.stageCoverImg(openFileChooser(mainPane.getScene().getWindow())));
        upPayload.setOnMouseClicked(mouseEvent -> model.stagePayload(openFileChooser(mainPane.getScene().getWindow())));
        genSteg.setOnMouseClicked(mouseEvent -> model.generateSteganograph());

        upPayload.setOnMousePressed(mouseEvent -> upPayload.setStyle("-fx-background-color: #86D3CB; -fx-border-style: solid; -fx-border-radius: 5; -fx-background-radius: 5;"));
        upPayload.setOnMouseReleased(mouseEvent -> upPayload.setStyle("-fx-background-color: #ABF0E9; -fx-border-style: solid; -fx-border-radius: 5; -fx-background-radius: 5;"));
        switchMode.setOnMousePressed(mouseEvent -> switchMode.setStyle("-fx-background-color: #86D3CB; -fx-background-radius: 10; -fx-border-radius: 10; -fx-border-style: solid; -fx-border-width: 1;"));
        switchMode.setOnMouseReleased(mouseEvent -> switchMode.setStyle("-fx-background-color: #ABF0E9; -fx-background-radius: 10; -fx-border-radius: 10; -fx-border-style: solid; -fx-border-width: 1;"));
        genSteg.setOnMousePressed(mouseEvent -> genSteg.setStyle("-fx-background-color: #86D3CB; -fx-background-radius: 5; -fx-border-radius: 5; -fx-border-style: solid;"));
        genSteg.setOnMouseReleased(mouseEvent -> genSteg.setStyle("-fx-background-color: #ABF0E9; -fx-background-radius: 5; -fx-border-radius: 5; -fx-border-style: solid;"));
    }


    public void setCoverImg(Image img) {
        upCoverImg.setImage(img);
        if (img.getHeight() > img.getWidth()) {
            upCoverImg.setFitHeight(coverContainer.getHeight());
            upCoverImg.setFitWidth(-1);
            upCoverImg.relocate((coverContainer.getWidth() / 2) - (img.getWidth() / img.getHeight() * upCoverImg.getFitHeight() / 2), 0);
        } else {
            upCoverImg.setFitWidth(coverContainer.getWidth());
            upCoverImg.setFitHeight(-1);
            upCoverImg.relocate(0, (coverContainer.getHeight() / 2) - (img.getHeight() / img.getWidth() * upCoverImg.getFitWidth() / 2));
        }
    }

    public void setPayload(File f) {
        fileChosen.setText(f.getName());
    }
}
