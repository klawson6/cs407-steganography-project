import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.control.Alert.AlertType.ERROR;

/**
 * The controller to handle user input for the encode GUI
 */
public class DecodeController implements Initializable {

    // Fields denoted by @FXML point to the nodes in the decode GUI FXML file
    @FXML
    private GridPane mainPane;

    @FXML
    private ImageView upCoverImg;

    @FXML
    private Pane coverContainer;

    @FXML
    private Pane switchMode;

    @FXML
    private Button decodeSteg;

    @FXML
    private Label imgText;

    // The model to handle steganography decoding
    private DecodeModel model;

    // The controller to handle switching from decode to encode view
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
        model = new DecodeModel();
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
        // Switch from decode to encode GUI when the switch GUI button is pressed
        switchMode.setOnMouseClicked(mouseEvent -> viewController.switchView(this));
        // Try to begin decoding the cover image when the decode button is clicked
        decodeSteg.setOnMouseClicked(mouseEvent -> model.decodeSteganograph());

        // Handles the tasty colour changing when the buttons are clicked. My fav code
        switchMode.setOnMousePressed(mouseEvent -> switchMode.setStyle("-fx-background-color: #FFAA6C; -fx-background-radius: 10; -fx-border-radius: 10; -fx-border-style: solid; -fx-border-width: 1;"));
        switchMode.setOnMouseReleased(mouseEvent -> switchMode.setStyle("-fx-background-color:  #FFC69C; -fx-background-radius: 10; -fx-border-radius: 10; -fx-border-style: solid; -fx-border-width: 1;"));
        decodeSteg.setOnMousePressed(mouseEvent -> decodeSteg.setStyle("-fx-background-color: #FFAA6C; -fx-background-radius: 10; -fx-border-radius: 10; -fx-border-style: solid;"));
        decodeSteg.setOnMouseReleased(mouseEvent -> decodeSteg.setStyle("-fx-background-color:  #FFC69C; -fx-background-radius: 10; -fx-border-radius: 10; -fx-border-style: solid;"));
    }

    /**
     * Displays the chosen image on the GUI for the cover image.
     * Resizes nicely, took bloomin ages to get, pls don't touch xoxo
     * @param f
     */
    public void setCoverImg(File f) {
        try {
            Image img = new Image(new FileInputStream(f));
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
            imgText.setText(f.getName());
        } catch (IOException e){
            e.printStackTrace();
            new Alert(ERROR, "An error occurred displaying the cover image.");
        }
    }

    /**
     * Sets the reference to the ViewController
     * @param viewController
     */
    public void setViewController(ViewController viewController){
        this.viewController = viewController;
    }

}
