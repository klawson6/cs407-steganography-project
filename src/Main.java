import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private AppController controller;

    private AppModel model;
    /**
     * Default Hello World GUI, this is our main where main calls are made, the actual main only calls launch(args).
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        // An object to extract information from an XML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AppView.fxml"));
        // Build a hierarchy of elements to traverse from the XML
        Parent root = loader.load();
        // Set the title of the window to be opened
        primaryStage.setTitle("Steganography Demo Tool");
        // Build the scene from the elements described in the XML hierarchy
        primaryStage.setScene(new Scene(root));
        // Display the scene
        primaryStage.show();
        // Set the focus to the root element to stop auto-selecting other elements
        root.requestFocus();
        // Get an instance of the controller used to handle GUI interaction
        controller = loader.getController();
    }


    /**
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
