
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Seweryn
 */
public class DuplicateResearch extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("/resources/fxml/MainView.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Duplicate Research - Seweryn Siedlecki, Praca Inżynierska");
        stage.getIcons().add(new Image(DuplicateResearch.class.getResourceAsStream("/resources/icon.png")));

        
        stage.show();
    }
}