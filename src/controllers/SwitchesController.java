/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;


import controllers.sunburst.ColorStrategyDecoratorRedSelected;
import controllers.sunburst.ColorStrategyDuplicateColored;
import controllers.sunburst.ColorStrategyDuplicateGreen;
import controllers.sunburst.ColorStrategySectorShades;
import controllers.sunburst.SunburstView;
import duplicateMachine.SearchDuplikate;
import duplicateMachine.Node;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
    
  // private ColorStrategyDuplicateRed colorStrategyDuplicateRed = new ColorStrategyDuplicateRed(); // prawdopodobnie nie uzywane , po usuniecia brak zwiaz we funkcjonalnosci
    
    
    private MainViewController mainController;
    
    @FXML private Button selectFolderButtom;
    
    private SunburstView sunburstView = new SunburstView();
    private SearchDuplikate badanieDuplikatow;
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
        ////////// file section
        final Set<Node> delFile = new HashSet<>();

        List<Node> pomocniczaFile = new ArrayList<>(); 
        pomocniczaFile = mainController.fileTabController.getObservableFolderList();
        

        
        for(Node nod : pomocniczaFile) 
        {
           if( nod.getSelected()) {
               delFile.add( nod );
           } 
        }
        
        //////////folder section
        
        final Set<Node> delFolder = new HashSet<>();

        List<Node> pomocniczaFolder = new ArrayList<>(); 
        pomocniczaFolder = mainController.foldersTabController.getObservableFolderList();
  
        
        
        for(Node nod : pomocniczaFolder) 
        {
           if( nod.getSelected()) {
               delFolder.add( nod );
           } 
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Uwaga");
        alert.setHeaderText("Chcesz usunąć "+ delFolder.size() + " folderów i " + delFile.size() +" plików");
        alert.setContentText("Jesteś pewien?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            
            for(Node nod : delFile) 
            {
               nod.getFile().delete();
            }
            for(Node nod : delFolder) 
            {
               nod.getFile().delete();
            }
           

            mainController.foldersTabController.getObservableFolderList().removeAll( delFile );
            mainController.foldersTabController.getObservableFolderList().removeAll( delFolder );
            
            ////refresh tables
            

            mainController.fileTabController.getDuplicateFolderTableView().refresh();            
            mainController.foldersTabController.getDuplicateFolderTableView().refresh();
            
            
        } else {
            // ... user chose CANCEL or closed the dialog
        }
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
                sunburstView.setColorStrategy(new ColorStrategyDecoratorRedSelected(new ColorStrategyDuplicateGreen()));
                
            }
            else{
                sunburstView.setColorStrategy(new ColorStrategyDuplicateGreen());
            
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
        layerSlider.setValue(sunburstView.getMaxDeepness()+1);
        layerSlider.valueProperty().addListener(x -> sunburstView.setMaxDeepness((int)layerSlider.getValue()-1));
        
        
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

    public void setBadanieDuplikatow(SearchDuplikate badanieDuplikatow) {
        this.badanieDuplikatow = badanieDuplikatow;
    }

    void initialize(MainViewController mainController) {
        this.mainController = mainController;
               
    }
    
    
    
    
    
}
