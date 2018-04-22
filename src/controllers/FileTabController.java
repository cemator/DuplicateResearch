package controllers;

import duplicateMachine.Node;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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
        
        duplicateFileTableView.setPlaceholder(new Label(""));
        
        duplicateFileTableView.setEditable(true);
        selectedFileColumn.setCellValueFactory(p -> p.getValue().selectedProperty());
        sizeFileColumn.setCellValueFactory(p -> p.getValue().sizeProperty());     
        folderFileColumn.setCellValueFactory(p -> p.getValue().pathProperty());
 
        selectedFileColumn.setCellFactory(CheckBoxTableCell.forTableColumn(selectedFileColumn));
        selectedFileColumn.setCellValueFactory(new PropertyValueFactory<>("selected"));
        
        // kolorowanie wierszy tabelki
      
      
        String styles[] = new String[10];
        styles[0] = "-fx-background-color: lightblue;";
        styles[1] = "-fx-background-color: lightcoral;"; 
        styles[2] = "-fx-background-color: lightcyan;"; 
        styles[3] = "-fx-background-color: lightgoldenrodyellow;"; 
        styles[4] = "-fx-background-color: lightgray;"; 
        styles[5] = "-fx-background-color: lightgreen;"; 
        styles[6] = "-fx-background-color: lightgrey;"; 
        styles[7] = "-fx-background-color: lightpink;"; 
        styles[8] = "-fx-background-color: lightsalmon;"; 
        styles[9] = "-fx-background-color: lightseagreen;"; 
        
        duplicateFileTableView.setRowFactory(x -> new TableRow<Node>(){
            @Override
            public void updateItem(Node item, boolean empty){
                super.updateItem(item, empty);
                if(item != null)
                    setStyle(styles[item.getGroupFile()%10]);   
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
