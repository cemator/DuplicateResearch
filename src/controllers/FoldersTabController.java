/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import duplicate.Node;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Seweryn
 */
public class FoldersTabController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    
    @FXML private TableView<Node> duplicateFolderTableView;
    @FXML private TableColumn<Node, Boolean> selectedFoldersColumn;
    @FXML private TableColumn<Node, Number> sizeFoldersColumn;
    @FXML private TableColumn<Node, Number> itemsFoldersColumn;
    @FXML private TableColumn<Node, String> pathFoldersColumn;
    ObservableList<Node> ObservableFolderList = FXCollections.observableArrayList();
    
    public void SetDuplicateFolderTable(java.util.List<Node> listaObiektow){
        ObservableFolderList.addAll(listaObiektow); //tabelka
        duplicateFolderTableView.itemsProperty().setValue(ObservableFolderList);
    }
    
    public void clearTable(){
        ObservableFolderList.clear();
        duplicateFolderTableView.itemsProperty().setValue(ObservableFolderList);

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        duplicateFolderTableView.setEditable(true);
        selectedFoldersColumn.setCellValueFactory(p -> p.getValue().selectedProperty());
        sizeFoldersColumn.setCellValueFactory(p -> p.getValue().sizeProperty());     
        itemsFoldersColumn.setCellValueFactory(p -> p.getValue().numTotalChildrenProperty());
        pathFoldersColumn.setCellValueFactory(p -> p.getValue().pathProperty());
        
        CheckBoxTableCell box = new CheckBoxTableCell(); 
        selectedFoldersColumn.setCellFactory(box.forTableColumn(selectedFoldersColumn));
        selectedFoldersColumn.setCellValueFactory(new PropertyValueFactory<>("selected"));
        
        duplicateFolderTableView.setRowFactory(x -> new TableRow<Node>() {
            @Override
            public void updateItem(Node item, boolean empty) {
                super.updateItem(item, empty) ;
                if(item != null)
                {
                switch(item.getGroupFolder()%10){
                        case 0: { //else if (item.getSelected() == true) { // przy zaznaczaniu selekcji dziala z opoznienien - odswierzanie wartosci w czasie rzeczywistym szwamkuje
                            setStyle("-fx-background-color: lightblue;"); //rgb(140,0,0);"); 
                            break;
                        }
                        case 1: { 
                            setStyle("-fx-background-color: lightcoral;"); 
                            break;
                        }
                        case 2: { 
                            setStyle("-fx-background-color: lightcyan;"); 
                            break;
                        }
                        case 3: { 
                            setStyle("-fx-background-color: lightgoldenrodyellow;"); 
                            break;
                        }
                        case 4: { 
                            setStyle("-fx-background-color: lightgray;"); 
                            break;
                        }
                        case 5: { 
                            setStyle("-fx-background-color: lightgreen;"); 
                            break;
                        }
                        case 6: { 
                            setStyle("-fx-background-color: lightgrey;"); 
                            break;
                        }
                        case 7: { 
                            setStyle("-fx-background-color: lightpink;"); 
                            break;
                        }
                        case 8: { 
                            setStyle("-fx-background-color: lightsalmon;"); 
                            break;
                        }
                        case 9: {                          
                            setStyle("-fx-background-color: lightseagreen;"); 
                            break;
                        }
                    }
                }
                
            }
        });
        
        
        
    }    

    public TableView<Node> getDuplicateFolderTableView() {
        return duplicateFolderTableView;
    }

    

    public ObservableList<Node> getObservableFolderList() {
        return ObservableFolderList;
    }
    
    
    
}
