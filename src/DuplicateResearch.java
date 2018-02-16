


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Seweryn
 */
public class DuplicateResearch extends Application {
    
    @Override
    public void start(Stage stage) throws Exception { //  brench test
        Parent root = FXMLLoader.load(getClass().getResource("/resources/fxml/MainView.fxml"));

        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    
}