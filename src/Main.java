import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    /**
     * Default Hello World GUI, this is our main where main calls are made, the actual main only calls launch(args).
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("AppView.fxml"));
        primaryStage.setTitle("Steganography Demo Tool");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        //AppController c = new AppController(new DummyHandler());
    }


    /**
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
