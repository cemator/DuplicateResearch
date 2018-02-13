
package inz.v1.pkg0;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Seweryn
 */
public class InzV10 extends Application {
    
    @Override
    public void start(Stage stage) throws Exception { //  brench test
        Parent root = FXMLLoader.load(getClass().getResource("/resources/fxml/MainView.fxml"));
       // FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/FoldersTab.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    
}