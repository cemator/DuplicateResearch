/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import duplicate.BadanieDuplikatow;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import duplicate.Node;


/**
 *
 * @author Seweryn
 */
public class MainViewController implements Initializable {
    

    
    
    private BadanieDuplikatow badanieDuplikatow;
    
    @FXML FoldersTabController foldersTabController;
    @FXML FileTabController fileTabController;
    @FXML public SwitchesController switchesController;
    @FXML public SunburstController sunburstController;
    

    public void startResearch(File seletedDirectory){
        clearTables();
        this.badanieDuplikatow = new BadanieDuplikatow(seletedDirectory.getAbsolutePath(),this);
        switchesController.setBadanieDuplikatow(this.badanieDuplikatow);
        new Thread(badanieDuplikatow).start();
    }

    
    public void SetDuplicateFolderTable(java.util.List<Node> listaObiektow){
        foldersTabController.SetDuplicateFolderTable(listaObiektow);
    }
    
   
    public void SetDuplicateFileTable(java.util.List<Node> listaObiektow){
        fileTabController.SetDuplicateFileTable(listaObiektow);
    }
    
    public void clearTables(){

        foldersTabController.clearTable();
        fileTabController.clearTable();
        sunburstController.getPane().setVisible(false);
    }
    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        switchesController.initialize(this);
        sunburstController.initialize(this);
        switchesController.setSunburstView(sunburstController.getSunburstView()); //this.sunburstView

    }    


}
