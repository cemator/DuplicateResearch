/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controls.sunburst.*;
import duplicate.BadanieDuplikatow;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import duplicate.Node;
import inz.v1.pkg0.SunburstRefresher;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import java.util.Set;
import javafx.beans.Observable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ToggleGroup;
import progressCircle.RingProgressIndicator;


/**
 *
 * @author Seweryn
 */
public class MainViewController implements Initializable {
    
    private RingProgressIndicator indicator = new RingProgressIndicator();
    private BadanieDuplikatow badanieDuplikatow;
    
//    private ColorStrategyDuplicateRed colorStrategyDuplicateRed = new ColorStrategyDuplicateRed();
//    
//    
//    @FXML private Button selectFolderButtom;


    
    @FXML FoldersTabController foldersTabController;
    @FXML FileTabController fileTabController;
    @FXML SwitchesController switchesController;
    
  
    
    @FXML private BorderPane pane;
  ///////////////// switches  
//    @FXML private Slider zoomSlider;
//    @FXML private Slider layerSlider;
//    @FXML private RadioButton radioRozszerzenia;
//    
//    
//    @FXML private RadioButton radioShadow;
//    @FXML private RadioButton RadioGroup;
//    @FXML private RadioButton RadioSelected;
//    
//    @FXML private CheckBox SelectedBoxSelectedView;
//    @FXML private ToggleGroup ColorStrategy;
///////////////// /switches
    
//    private File seletedDirectory;
    private WeightedTreeItem<Node> drzewo;
    private SunburstView sunburstView = new SunburstView();
    private Thread sunburstRefresherThread;
    

    
    @FXML
    private void selectFolderButtomAction(ActionEvent event) throws IOException {
//        DirectoryChooser fd = new DirectoryChooser();
//        seletedDirectory = fd.showDialog(null);
//
//        if(seletedDirectory != null){
//            clearTables();
//  
//            this.badanieDuplikatow = new BadanieDuplikatow(seletedDirectory.getAbsolutePath(),this);
//            new Thread(badanieDuplikatow).start();
//                       
//
//        }
//        else{
//            System.out.println("Niema takiego Folderu");
//        }
        
    }
    
    public void startResearch(File seletedDirectory){
        clearTables();
        this.badanieDuplikatow = new BadanieDuplikatow(seletedDirectory.getAbsolutePath(),this);
        
        switchesController.setBadanieDuplikatow(this.badanieDuplikatow);
        new Thread(badanieDuplikatow).start();
    }

///   To Rebulit
    
    public void SetDuplicateFolderTable(java.util.List<Node> listaObiektow){
          foldersTabController.SetDuplicateFolderTable(listaObiektow);
//        ObservableFolderList.addAll(listaObiektow); //tabelka
//        duplicateFolderTableView.itemsProperty().setValue(ObservableFolderList);
    }
    
    ///to refactor
    public void SetDuplicateFileTable(java.util.List<Node> listaObiektow){
        fileTabController.SetDuplicateFileTable(listaObiektow);
        
//        ObservableFileList.addAll(listaObiektow); //tabelka
//        duplicateFileTableView.itemsProperty().setValue(ObservableFileList);
    }
    
    public void clearTables(){

        foldersTabController.clearTable();
        fileTabController.clearTable();
              
        pane.setVisible(false);
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
         
            if(sunburstIsNull){ //jesli flaga podniesiona (wykananie processbara zakonczone oraz proba strzykniecia sunburst na widok zakonczona niepowodzeniem
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
    
   
//    @FXML
//    private void RadioButton1Action(ActionEvent event){
//        
//        sunburstView.setColorStrategy(new ColorStrategySectorShades());
//
//        SelectedBoxSelectedView.setSelected(false);
//        SelectedBoxSelectedView.setDisable(true);
//
//       
//        radioRozszerzenia.selectedProperty().addListener((Observable observable)->{
//            if(!radioRozszerzenia.isSelected()){
//                SelectedBoxSelectedView.setDisable(false);
//                badanieDuplikatow.setSunburstDuplicate();
//            }
//        });
//        
//         badanieDuplikatow.setSunburstExtension();
//
//
//    }
//    
//    @FXML
//    void RadioGroupColoredAction(ActionEvent event) {
//        
//        if(RadioGroup.selectedProperty().getValue()){ //tutaj dodac aby przy zaznaczeniu dodawał sie proces odswierzania, a przy wylaczeniu aby sie pauzował
//            if(SelectedBoxSelectedView.isSelected()){
//                sunburstView.setColorStrategy(new ColorStrategyDecoratorRedSelected(new ColorStrategyDuplicateColored()));
//            }
//            else{
//                
//                sunburstView.setColorStrategy(new ColorStrategyDuplicateColored());
//            }
//        }
//        
//    }
//
//    @FXML
//    void RadioSelectedAction(ActionEvent event) {
//        
//        if(RadioSelected.selectedProperty().getValue()){
//            if(SelectedBoxSelectedView.isSelected()){
//                sunburstView.setColorStrategy(new ColorStrategyDecoratorRedSelected(new ColorStrategyDuplicateRed()));
//                
//            }
//            else{
//                sunburstView.setColorStrategy(new ColorStrategyDuplicateRed());
//            
//            }
//        }
//    }
//
//    @FXML
//    void RadioShadowAction(ActionEvent event) {
//        
//        if(radioShadow.selectedProperty().getValue()){
//            if(SelectedBoxSelectedView.isSelected()){
//                sunburstView.setColorStrategy(new ColorStrategyDecoratorRedSelected(new ColorStrategySectorShades()));
//            }
//            else{
//                sunburstView.setColorStrategy(new ColorStrategySectorShades());
//            }
//        }
//
//    }

    
    
    public void refreshSunburstView(){
  
           sunburstView.refresh();

    }
    
    
    @FXML
    void deleteButtonAction(ActionEvent event) {
        ////////// file section
        final Set<Node> delFile = new HashSet<>();

        List<Node> pomocniczaFile = new ArrayList<>(); 
        pomocniczaFile = fileTabController.getObservableFolderList();
        
//        pomocniczaFile.addAll(duplicateFolderTableView.getItems());
        
        for(Node nod : pomocniczaFile) 
        {
           if( nod.getSelected()) {
               delFile.add( nod );
           } 
        }
        
        //////////folder section
        
        final Set<Node> delFolder = new HashSet<>();

        List<Node> pomocniczaFolder = new ArrayList<>(); 
        pomocniczaFolder = foldersTabController.getObservableFolderList();
  
        
        
        for(Node nod : pomocniczaFolder) 
        {
           if( nod.getSelected()) {
               delFolder.add( nod );
           } 
        }

        Alert alert = new Alert(AlertType.CONFIRMATION);
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
           

            foldersTabController.getObservableFolderList().removeAll( delFile );
            foldersTabController.getObservableFolderList().removeAll( delFolder );
            
            ////refresh tables
            

            fileTabController.getDuplicateFolderTableView().refresh();            
            foldersTabController.getDuplicateFolderTableView().refresh();
            
            
        } else {
            // ... user chose CANCEL or closed the dialog
        }

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        switchesController.initialize(this);
        

        //ustalenia widoku SunBurstView
        sunburstView.setScaleX(0.7);
        sunburstView.setScaleY(0.7); // zmianę rozmiaru przeprowadzać przy kazdym kliknieciu na podstawie wielkosci wkresu

        

//        zoomSlider.setValue(sunburstView.getScaleX());
//        zoomSlider.valueProperty().addListener(x -> {
//            double scale = zoomSlider.getValue();
//            sunburstView.setScaleX(scale);
//            sunburstView.setScaleY(scale);
//        });
//        
//        //bindowanie suwaka
//        layerSlider.setValue(sunburstView.getMaxDeepness());
//        layerSlider.valueProperty().addListener(x -> sunburstView.setMaxDeepness((int)layerSlider.getValue()));

         sunburstRefresherThread = new Thread(new SunburstRefresher(this));
         sunburstRefresherThread.setDaemon(true); //dzieki temu apka jest zamykana razem z zakonczeniem ttego watka
         sunburstRefresherThread.start();

//         SelectedBoxSelectedView.selectedProperty().addListener((Observable observable)->{      
//            if(SelectedBoxSelectedView.isSelected()){
//                sunburstView.setColorStrategy(new ColorStrategyDecoratorRedSelected(sunburstView.getColorStrategy()));
//            }
//            else{
//                RadioButton selectedRadioButton =(RadioButton)ColorStrategy.getSelectedToggle();
//                selectedRadioButton.setSelected(false);
//                selectedRadioButton.fire();    
//            }
//        });
     
         switchesController.setSunburstView(this.sunburstView);
//         switchesController.setBadanieDuplikatow(this.badanieDuplikatow);

        
    }    

    public CheckBox getSelectedBoxSelectedView() {
        return switchesController.getSelectedBoxSelectedView();
    }

}
