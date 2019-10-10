import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller to handle user input for the encode GUI
 */
public class EncodeController implements Initializable {

    // Fields denoted by @FXML point to the nodes in the encode GUI FXML file
    @FXML
    private GridPane mainPane;

    @FXML
    private Button upPayload;

    @FXML
    private ImageView upCoverImg;

    @FXML
    private Pane coverContainer;

    @FXML
    private Button encodeSteg;

    @FXML
    private Pane switchMode;

    @FXML
    private Label fileChosen;

    // The model to handle steganography encoding
    private EncodeModel model;

    // The controller to handle switching from encode to decode view
    private ViewController viewController;

    /**
     * Opens a file chooser menu and returns the selected File or null if it was closed without selection.
     * @param w
     * @return
     */
    private File openFileChooser(Window w) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select a file");
        return fc.showOpenDialog(w);
    }

    /**
     * Called when the controller is created by the FXML getting loaded.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Create a new model for encoding
        model = new EncodeModel();
        // Give the model a reference to this.
        model.setController(this);
        // Not really adding listeners, but binding actions to events.
        addListeners();
    }

    /**
     * Not really adding listeners, but binding actions to events.
     */
    private void addListeners() {
        // Open the file chooser when the upload cover image button is clicked
        upCoverImg.setOnMouseClicked(mouseEvent -> model.stageCoverImg(openFileChooser(mainPane.getScene().getWindow())));
        // Open the file chooser when the upload payload button is clicked
        upPayload.setOnMouseClicked(mouseEvent -> model.stagePayload(openFileChooser(mainPane.getScene().getWindow())));
        // Try to begin encoding the cover image with the payload when the encode button is clicked
        encodeSteg.setOnMouseClicked(mouseEvent -> model.encodeSteganograph());
        // Switch from encode to decode GUI when the switch GUI button is pressed
        switchMode.setOnMouseClicked(mouseEvent -> viewController.switchView(this));

        // Handles the tasty colour changing when the buttons are clicked. My fav code
        upPayload.setOnMousePressed(mouseEvent -> upPayload.setStyle("-fx-background-color: #6BFDEE; -fx-border-style: solid; -fx-border-radius: 5; -fx-background-radius: 5;"));
        upPayload.setOnMouseReleased(mouseEvent -> upPayload.setStyle("-fx-background-color:  #9CFFF4; -fx-border-style: solid; -fx-border-radius: 5; -fx-background-radius: 5;"));
        switchMode.setOnMousePressed(mouseEvent -> switchMode.setStyle("-fx-background-color: #6BFDEE; -fx-background-radius: 10; -fx-border-radius: 10; -fx-border-style: solid; -fx-border-width: 1;"));
        switchMode.setOnMouseReleased(mouseEvent -> switchMode.setStyle("-fx-background-color:  #9CFFF4; -fx-background-radius: 10; -fx-border-radius: 10; -fx-border-style: solid; -fx-border-width: 1;"));
        encodeSteg.setOnMousePressed(mouseEvent -> encodeSteg.setStyle("-fx-background-color: #6BFDEE; -fx-background-radius: 5; -fx-border-radius: 5; -fx-border-style: solid;"));
        encodeSteg.setOnMouseReleased(mouseEvent -> encodeSteg.setStyle("-fx-background-color:  #9CFFF4; -fx-background-radius: 5; -fx-border-radius: 5; -fx-border-style: solid;"));
    }

    /**
     * Displays the chosen image on the GUI for the cover image.
     * Resizes nicely, took bloomin ages to get, pls don't touch xoxo
     * @param img
     */
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

    /**
     * Displays the file name for the payload chosen
     * @param f
     */
    public void setPayload(File f) {
        fileChosen.setText(f.getName());
    }

    /**
     * Sets the reference to the ViewController
     * @param viewController
     */
    public void setViewController(ViewController viewController){
        this.viewController = viewController;
    }
}
