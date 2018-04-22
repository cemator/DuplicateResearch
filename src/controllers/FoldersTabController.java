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
public class FoldersTabController implements Initializable {


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
        
        duplicateFolderTableView.setPlaceholder(new Label(""));
        duplicateFolderTableView.setEditable(true);
        selectedFoldersColumn.setCellValueFactory(p -> p.getValue().selectedProperty());
        sizeFoldersColumn.setCellValueFactory(p -> p.getValue().sizeProperty());     
        itemsFoldersColumn.setCellValueFactory(p -> p.getValue().numTotalChildrenProperty());
        pathFoldersColumn.setCellValueFactory(p -> p.getValue().pathProperty());
        
        CheckBoxTableCell box = new CheckBoxTableCell(); 
        selectedFoldersColumn.setCellFactory(box.forTableColumn(selectedFoldersColumn));
        selectedFoldersColumn.setCellValueFactory(new PropertyValueFactory<>("selected"));
        
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
        
        duplicateFolderTableView.setRowFactory(x -> new TableRow<Node>(){
            @Override
            public void updateItem(Node item, boolean empty){
                super.updateItem(item, empty);
                if(item != null)
                    setStyle(styles[item.getGroupFolder()%10]);   
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
