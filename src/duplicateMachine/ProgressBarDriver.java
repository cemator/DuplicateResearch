package duplicateMachine;

import controllers.MainViewController;
import javafx.application.Platform;

/**
 *
 * @author Seweryn Siedlecki
 */
public class ProgressBarDriver {
    private final MainViewController fXMLDocumentController;
    private double actualItems = 0.0;
    private double allItems = 0.0;
    
    ProgressBarDriver(MainViewController fXMLDocumentController) {
        this.fXMLDocumentController = fXMLDocumentController;
    }
    
    public void setProgressBar(double allItems){
        this.allItems = allItems;
        Platform.runLater(() -> {
            fXMLDocumentController.sunburstController.SetProgressBar();
        });
    }
    
    public void setProgressBarValue(){
        actualItems++; 
        Platform.runLater(() -> {       
            fXMLDocumentController.sunburstController.SetProgressBarValue((actualItems/(allItems))*100);
        });
    }
}
