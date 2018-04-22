package controllers;

import controllers.sunburst.SunburstView;
import controllers.sunburst.WeightedTreeItem;
import duplicateMachine.Node;
import controllers.sunburst.SunburstRefresher;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import controllers.progressCircle.RingProgressIndicator;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author Seweryn Siedlecki
 */
public class SunburstController implements Initializable {

    
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
       
        clipChildren(pane,12);
        
        this.mainViewController = aThis;
        
        //ustalenia widoku SunBurstView
        sunburstView.setScaleX(0.7);
        sunburstView.setScaleY(0.7); 


         sunburstRefresherThread = new Thread(new SunburstRefresher(this));
         sunburstRefresherThread.setDaemon(true); 
         sunburstRefresherThread.start();
        
    }
    
    static void clipChildren(Region region, double arc) {

        final Rectangle outputClip = new Rectangle();
        outputClip.setArcWidth(arc);
        outputClip.setArcHeight(arc);
        region.setClip(outputClip);

        region.layoutBoundsProperty().addListener((ov, oldValue, newValue) -> {
            outputClip.setWidth(newValue.getWidth());
            outputClip.setHeight(newValue.getHeight());
        });
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
            sunburstIsNull = true;                      //jesli sunburst jeszcze nie gotowy, podnies flage
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
