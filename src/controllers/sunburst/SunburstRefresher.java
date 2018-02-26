/*se License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.sunburst;

import controllers.SunburstController;
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
    private SunburstController sunburstController;
    private boolean isStopped = false;

    
    
    
    
    public SunburstRefresher(SunburstController sunburstController){
        this.sunburstController = sunburstController;
    }

    @Override
    public void run() {
        while(!isStopped){
            
            try {
                sleep(500);} catch (InterruptedException ex) {Logger.getLogger(SunburstRefresher.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(sunburstController.getMainViewController().switchesController.getSelectedBoxSelectedView().isSelected()){

                Platform.runLater(new Runnable() {  //mechanizm pozwalajacy na zmiane elementow FX GUI
                    @Override                           //mechanizm pozwalajacy na zmiane elementow FX GUI
                    public void run() {                 //mechanizm pozwalajacy na zmiane elementow FX GUI
                    sunburstController.refreshSunburstView();}});
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
