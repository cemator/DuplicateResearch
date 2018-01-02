/*se License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inz.v1.pkg0;

import controllers.MainViewController;
import controls.sunburst.SunburstView;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import static java.lang.Thread.sleep;

/**
 *
 * @author Seweryn
 */
public class SunburstRefresher implements Runnable {
    
   // SunburstView sunburstview;
    private MainViewController fXMLDocumentController;
    private boolean isStopped = false;

    public SunburstRefresher(MainViewController fXMLDocumentController){
        this.fXMLDocumentController = fXMLDocumentController;
    }
    
    @Override
    public void run() {
        while(!isStopped){
            
            try {
                sleep(500);} catch (InterruptedException ex) {Logger.getLogger(SunburstRefresher.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(fXMLDocumentController.getSelectedBoxSelectedView().isSelected()){
                Platform.runLater(new Runnable() {  //mechanizm pozwalajacy na zmiane elementow FX GUI
                    @Override                           //mechanizm pozwalajacy na zmiane elementow FX GUI
                    public void run() {                 //mechanizm pozwalajacy na zmiane elementow FX GUI
                    fXMLDocumentController.refreshSunburstView();}});
            }
        }
    }
    
    
    public void stop() {
        this.isStopped = true;
    }

    public boolean isStopped() {
        return this.isStopped;
    }
}
