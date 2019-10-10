import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private ViewController viewController;

    /**
     * The 'main' method called after starting the JavaFX application
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) {
        // Instantiate a ViewController to handle the switch between Encode and Decode modes.
        // Pass the primaryStage so it can handle what appears in the GUI.
        viewController = new ViewController(primaryStage);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
