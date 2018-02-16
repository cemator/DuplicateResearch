/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duplicateMachine;

import controllers.MainViewController;
import javafx.application.Platform;

/**
 *
 * @author Lenovo
 */
public class ProgressBarDriver {
    private MainViewController fXMLDocumentController;
    private double actualItems = 0.0;
    private double allItems = 0.0;

    ProgressBarDriver(MainViewController fXMLDocumentController) {
        this.fXMLDocumentController = fXMLDocumentController;
    }
    
    public void setProgressBar(double allItems){
            this.allItems = allItems*2;
            Platform.runLater(new Runnable() {  //mechanizm pozwalajacy na zmiane elementow FX GUI
                @Override                       //mechanizm pozwalajacy na zmiane elementow FX GUI
                public void run() {             //mechanizm pozwalajacy na zmiane elementow FX GUI
                    fXMLDocumentController.sunburstController.SetProgressBar();
                }});
        
        }
    
    public void setProgressBarValue(){
            actualItems++;
            //System.out.println(actualItems + "<- actual | all -> " + allItems + " | result ->" + ((actualItems/allItems)*100));
            Platform.runLater(new Runnable() {  //mechanizm pozwalajacy na zmiane elementow FX GUI
            @Override                       //mechanizm pozwalajacy na zmiane elementow FX GUI
            public void run() {             //mechanizm pozwalajacy na zmiane elementow FX GUI
                fXMLDocumentController.sunburstController.SetProgressBarValue((actualItems/(allItems))*100);
            }});
        }
}
