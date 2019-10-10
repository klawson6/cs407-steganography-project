import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewController {
    // A loaded FXML file where data from the FXML representing the encode GUI can be extracted
    private FXMLLoader encodeLoader;
    // A loaded FXML file where data from the FXML representing the decode GUI can be extracted
    private FXMLLoader decodeLoader;
    // An instance of the encode controller to handle the user input for the encode GUI
    private EncodeController encodeController;
    // An instance of the decode controller to handle the user input for the decode GUI
    private DecodeController decodeController;
    // The root node for the encode GUI
    private Parent encodeRoot;
    // The root node for the decode GUI
    private Parent decodeRoot;
    // The Stage where the GUI scene is placed
    private Stage primaryStage;

    /**
     * Constructor to build the initial GUI and generate instances of controllers and root nodes.
     * @param primaryStage
     */
    public ViewController(Stage primaryStage) {
        try {
            this.primaryStage = primaryStage;
            // An object to extract information from an XML file
            this.encodeLoader = new FXMLLoader(getClass().getResource("EncodeView.fxml"));
            // Build a hierarchy of elements to traverse from the XML
            this.encodeRoot = encodeLoader.load();
            // Set the title of the window to be opened
            this.primaryStage.setTitle("Steganography Demo Tool");
            // Build the scene from the elements described in the XML hierarchy
            this.primaryStage.setScene(new Scene(encodeRoot));
            // Display the scene
            this.primaryStage.show();
            // Get a reference to the encode controller implicitly generated from the loaded FXML
            encodeController = encodeLoader.getController();
            // Pass a reference of this ViewController to the encode controller
            encodeController.setViewController(this);
            // An object to extract information from an XML file
            this.decodeLoader = new FXMLLoader(getClass().getResource("DecodeView.fxml"));
            // Build a hierarchy of elements to traverse from the XML
            this.decodeRoot = this.decodeLoader.load();
            // Get a reference to the decode controller implicitly generated from the loaded FXML
            this.decodeController = this.decodeLoader.getController();
            // Pass a reference of this ViewController to the decode controller
            this.decodeController.setViewController(this);

        } catch (IOException e) {
            System.out.println("An FXML file could not load.");
            e.printStackTrace();
        }
    }

    /**
     * Switches the view from encode to decode
     * @param e
     */
    public void switchView(EncodeController e){
        primaryStage.getScene().setRoot(decodeRoot);
    }

    /**
     * Switches the view from decode to encode
     * @param d
     */
    public void switchView(DecodeController d){
        primaryStage.getScene().setRoot(encodeRoot);
    }
}
