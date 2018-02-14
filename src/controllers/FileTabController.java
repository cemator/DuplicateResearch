/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import duplicateMachine.Node;
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
public class FileTabController implements Initializable {

    
    @FXML private TableView<Node> duplicateFileTableView;
    @FXML private TableColumn<Node, Boolean> selectedFileColumn;
    @FXML private TableColumn<Node, Number> sizeFileColumn;
    @FXML private TableColumn<Node, String> folderFileColumn;
    ObservableList<Node> ObservableFileList = FXCollections.observableArrayList();
    
    public void SetDuplicateFileTable(java.util.List<Node> listaObiektow){
        ObservableFileList.addAll(listaObiektow); //tabelka
        duplicateFileTableView.itemsProperty().setValue(ObservableFileList);
    }
    
    public void clearTable(){
        ObservableFileList.clear(); //tabelka
        duplicateFileTableView.itemsProperty().setValue(ObservableFileList);
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        duplicateFileTableView.setEditable(true);
        selectedFileColumn.setCellValueFactory(p -> p.getValue().selectedProperty());
        sizeFileColumn.setCellValueFactory(p -> p.getValue().sizeProperty());     
        folderFileColumn.setCellValueFactory(p -> p.getValue().pathProperty());
  
        
        CheckBoxTableCell box2 = new CheckBoxTableCell();
         selectedFileColumn.setCellFactory(box2.forTableColumn(selectedFileColumn));
        selectedFileColumn.setCellValueFactory(new PropertyValueFactory<>("selected"));
        
        //// kolorowanie wierszy tabelki
      
      
        duplicateFileTableView.setRowFactory(x -> new TableRow<Node>() {
            @Override
            public void updateItem(Node item, boolean empty) {
                super.updateItem(item, empty) ;
                if(item != null)
                {
                    switch(item.getGroupFile()%10){
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
        return duplicateFileTableView;
    }

    public ObservableList<Node> getObservableFolderList() {
        return ObservableFileList;
    }
}
