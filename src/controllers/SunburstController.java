/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controls.sunburst.SunburstView;
import controls.sunburst.WeightedTreeItem;
import duplicate.Node;
import inz.v1.pkg0.SunburstRefresher;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import progressCircle.RingProgressIndicator;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class SunburstController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    MainViewController mainViewController;
    private RingProgressIndicator indicator = new RingProgressIndicator();
    @FXML private BorderPane pane;
    
    private WeightedTreeItem<Node> drzewo;
    private SunburstView sunburstView = new SunburstView();
    private Thread sunburstRefresherThread;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    void initialize(MainViewController aThis) {
       
        this.mainViewController = aThis;
        
        //ustalenia widoku SunBurstView
        sunburstView.setScaleX(0.7);
        sunburstView.setScaleY(0.7); // zmianę rozmiaru przeprowadzać przy kazdym kliknieciu na podstawie wielkosci wkresu


         sunburstRefresherThread = new Thread(new SunburstRefresher(this));
         sunburstRefresherThread.setDaemon(true); //dzieki temu apka jest zamykana razem z zakonczeniem ttego watka
         sunburstRefresherThread.start();
        
    }
    
    public void SetProgressBar(){
        
        pane.setVisible(true);
        this.indicator.setProgress(0);
        pane.setCenter(indicator);
        
    
    }
    
    public void SetProgressBarValue(double actualItems){
    
        this.indicator.setProgress((int)actualItems);

    }
    
    
    private boolean sunburstIsNull = false;
    
        
    public void SetSunBurst(WeightedTreeItem<Node> drzewo)
    {
       
        this.drzewo = drzewo;
        sunburstView.setRootItem(drzewo);

        if(sunburstIsNull){ //jesli flaga podniesiona (wykananie processbara zakonczone oraz proba wstrzykniecia sunburst na widok zakonczona niepowodzeniem
            pane.setVisible(true);
            pane.setCenter(sunburstView);

            sunburstIsNull = false; //opuszczenie flagi dla kolejnego ewentualnego wyboru folderu
        }
    }  
    
    
    public void SetSunBurstVisable() {
        pane.setVisible(true);
        if(sunburstView.getRootItem() != null){
            pane.setCenter(sunburstView);
          
        }
        else{
            sunburstIsNull = true; //jesli sunburst jeszcze nie gotowy podnies flage
        }
    }
    
    public void refreshSunburstView(){
  
        sunburstView.refresh();
    }
    
    

    public BorderPane getPane() {
        return pane;
    }

    public SunburstView getSunburstView() {
        return sunburstView;
    }

    public MainViewController getMainViewController() {
        return mainViewController;
    }
    
    
    
    
    
}
