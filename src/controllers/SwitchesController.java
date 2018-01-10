/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controls.sunburst.ColorStrategyDecoratorRedSelected;
import controls.sunburst.ColorStrategyDuplicateColored;
import controls.sunburst.ColorStrategyDuplicateRed;
import controls.sunburst.ColorStrategySectorShades;
import controls.sunburst.SunburstView;
import duplicate.BadanieDuplikatow;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.stage.DirectoryChooser;

/**
 * FXML Controller class
 *
 * @author Seweryn
 */
public class SwitchesController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML private Slider zoomSlider;
    @FXML private Slider layerSlider;
    @FXML private RadioButton radioRozszerzenia;
    
    
    @FXML private RadioButton radioShadow;
    @FXML private RadioButton radioGroupColored;
    @FXML private RadioButton radioHighContrast;
    
    @FXML private CheckBox SelectedBoxSelectedView;
    @FXML private ToggleGroup ColorStrategy;
    
    private ColorStrategyDuplicateRed colorStrategyDuplicateRed = new ColorStrategyDuplicateRed();
    private MainViewController mainController;
    
    @FXML private Button selectFolderButtom;
    
    private SunburstView sunburstView = new SunburstView();
    private BadanieDuplikatow badanieDuplikatow;
    private File seletedDirectory; // tutaj zostaje przypisany wybrany folder do badania
    
    
    
    @FXML
    private void selectFolderButtomAction(ActionEvent event) throws IOException {
        DirectoryChooser fd = new DirectoryChooser();
        seletedDirectory = fd.showDialog(null);

        if(seletedDirectory != null){
            mainController.startResearch(seletedDirectory);
                       
        }
        else{
            System.out.println("Niema takiego Folderu");
        }
    }
    
     @FXML
    void deleteButtonAction(ActionEvent event) {
        //TODO
    }
     
    
    
    
    
    @FXML
    private void RadioRozszerzeniaAction(ActionEvent event){
        
        sunburstView.setColorStrategy(new ColorStrategySectorShades());

        SelectedBoxSelectedView.setSelected(false);
        SelectedBoxSelectedView.setDisable(true);

       
        radioRozszerzenia.selectedProperty().addListener((Observable observable)->{
            if(!radioRozszerzenia.isSelected()){
                SelectedBoxSelectedView.setDisable(false);
                badanieDuplikatow.setSunburstDuplicate();
            }
        });
        
         badanieDuplikatow.setSunburstExtension();


    }
    
    @FXML
    void RadioGroupColoredAction(ActionEvent event) {
        
        if(radioGroupColored.selectedProperty().getValue()){ //tutaj dodac aby przy zaznaczeniu dodawał sie proces odswierzania, a przy wylaczeniu aby sie pauzował
            if(SelectedBoxSelectedView.isSelected()){
                sunburstView.setColorStrategy(new ColorStrategyDecoratorRedSelected(new ColorStrategyDuplicateColored()));
            }
            else{
                
                sunburstView.setColorStrategy(new ColorStrategyDuplicateColored());
            }
        }
        
    }

    @FXML
    void RadioHighContrastAction(ActionEvent event) {
        
        if(radioHighContrast.selectedProperty().getValue()){
            if(SelectedBoxSelectedView.isSelected()){
                sunburstView.setColorStrategy(new ColorStrategyDecoratorRedSelected(new ColorStrategyDuplicateRed()));
                
            }
            else{
                sunburstView.setColorStrategy(new ColorStrategyDuplicateRed());
            
            }
        }
    }

    @FXML
    void RadioShadowAction(ActionEvent event) {
        
        if(radioShadow.selectedProperty().getValue()){
            if(SelectedBoxSelectedView.isSelected()){
                sunburstView.setColorStrategy(new ColorStrategyDecoratorRedSelected(new ColorStrategySectorShades()));
            }
            else{
                sunburstView.setColorStrategy(new ColorStrategySectorShades());
            }
        }

    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        zoomSlider.setValue(sunburstView.getScaleX());
        zoomSlider.valueProperty().addListener(x -> {
            double scale = zoomSlider.getValue();
            sunburstView.setScaleX(scale);
            sunburstView.setScaleY(scale);
        });
        
        //bindowanie suwaka
        layerSlider.setValue(sunburstView.getMaxDeepness());
        layerSlider.valueProperty().addListener(x -> sunburstView.setMaxDeepness((int)layerSlider.getValue()));
        
        
        SelectedBoxSelectedView.selectedProperty().addListener((Observable observable)->{      
            if(SelectedBoxSelectedView.isSelected()){
                sunburstView.setColorStrategy(new ColorStrategyDecoratorRedSelected(sunburstView.getColorStrategy()));
            }
            else{
                RadioButton selectedRadioButton =(RadioButton)ColorStrategy.getSelectedToggle();
                selectedRadioButton.setSelected(false);
                selectedRadioButton.fire();    
            }
        });

    }    

    
    public CheckBox getSelectedBoxSelectedView() {
        return SelectedBoxSelectedView;
    }

    
    
    public void setSunburstView(SunburstView sunburstView) {
        this.sunburstView = sunburstView;
    }

    public void setBadanieDuplikatow(BadanieDuplikatow badanieDuplikatow) {
        this.badanieDuplikatow = badanieDuplikatow;
    }

    void initialize(MainViewController mainController) {
        this.mainController = mainController;
       
    }
    
    
    
    
    
}
