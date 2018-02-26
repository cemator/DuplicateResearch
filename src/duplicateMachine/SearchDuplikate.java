/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

//problem przy załadowywaniu duzych folderow -- liczenie po czasie i nie zamykanie programu po akmnieciu to puki nie skonczy liczyc
//problem po porządkach -> nie zawsze da sie klikac select w tabelkach


 */
package duplicateMachine;

import helpers.Utils;
import controllers.sunburst.WeightedTreeItem;
import controllers.MainViewController;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Platform;

/**
 *
 * @author Seweryn
 */
    public class SearchDuplikate implements Runnable{
        
        
        private MainViewController fXMLDocumentController;
        private Node rootNode;
        private Map<String, List<Node>> mapDuplicates = new HashMap<>();                          //przechowywuje mape zduplikowanych plikow (uwaga, trzyma tez pojedyncze NIE zduplikowane Nody!)


        private List<Node> finalDuplicateFolderLists = new ArrayList<>();
        private List<Node> finalDuplicateFileLists = new ArrayList<>();
        
        private TreeGenerate treeGenerate = new TreeGenerate();
        private ProgressBarDriver progressBarDriver;
        private Hasher hasher;
        
        
        private WeightedTreeItem<Node> drzewo;    
        private WeightedTreeItem<Node> treeExt;
        

        public SearchDuplikate(String sciezkaPliku, MainViewController fXMLDocumentController){
            this.fXMLDocumentController = fXMLDocumentController; //instancja kontrolera widoku
            this.progressBarDriver = new ProgressBarDriver(fXMLDocumentController);
            this.hasher = new Hasher(progressBarDriver);
            
            this.rootNode = new Node(new File(sciezkaPliku));
    }

        @Override
        public void run() {

            this.rootNode = treeGenerate.stworzDrzewoNodow(this.rootNode);
            treeGenerate.ustawRodzicow(this.rootNode);
            
            progressBarDriver.setProgressBar(treeGenerate.getAllItems());

            hasher.hashujPliki(this.rootNode);
            hasher.hashujFoldery(this.rootNode); // dodaje do folderów romiar oraz liczbe plikow potomnych ORAZ tworzy hasha z hashy wszystkich plikow potomnych (wsteonie posortowanych)

            searchDuplicatesRecursively(this.rootNode);//tworzy mape nodow o takich samych hashach
            pruneNonDuplicates();  // usun z mapDuplicates wpisy z pojedynczymi nodami(plikami)
            
            populateViewers();            
            setSunBurstVisable();
            setTableView();           
            
            drzewo = treeGenerate.buildTree(this.rootNode);
            
            setSunburstDuplicate();
            
            ///////////// TO EXTENSION
           
            treeExt = Utils.fromNodeToTreeExt(this.rootNode);        
            
            
        }
        

        private void searchDuplicatesRecursively(Node node) { // uzupełnia mapę mapDuplicates o listę roznych hashów i dodaje do niej referencje do ich reprezentantow w nodach
            String strHash = node.getHash();
            if (this.mapDuplicates.containsKey(strHash) == false) {
                this.mapDuplicates.put(strHash, new ArrayList<>());
            }
            this.mapDuplicates.get(strHash).add(node); //dla kazdego hasha bedzie conajmniej jeden node (plik)
            for (Node childNode : node.getChildren()) {
                searchDuplicatesRecursively(childNode);
            }  //byc moze cala ta modeda dubluje pliki poniewaz jest dodawana po lub przed wyszukiwaniem folderow
	}
        
        private void pruneNonDuplicates() {
            String[] copyOfStrHashes = this.mapDuplicates.keySet().toArray(new String[0]);
            for (String strHash : copyOfStrHashes) {
                List<Node> duplicatesList = this.mapDuplicates.get(strHash);
                if (duplicatesList.size() < 2) {
                    this.mapDuplicates.remove(strHash);
                }
            }
	}
        
        public void populateViewers(){
            int groupFile = 0;
            int groupFolder = 0;
            
            for (Map.Entry<String, List<Node>> entry : this.mapDuplicates.entrySet()) {
                List<Node> duplicateList = entry.getValue();
                
                boolean isFile = duplicateList.get(0).getFile().isFile();
                if (isFile) {
                    groupFile++;
                    for(Node node : duplicateList){
                        node.setGroupFile(groupFile);
                        node.setDuplicate(true);
                    }
                    this.finalDuplicateFileLists.addAll(duplicateList); //na tym etapie dodawane sa juz zdublowane pliki nodow
                } 
                else {
                    groupFolder++;
                    for(Node node : duplicateList){
                        node.setGroupFolder(groupFolder);
                        node.setDuplicate(true);
                    }
                    this.finalDuplicateFolderLists.addAll(duplicateList);
                }
            }
            
        } //ta klasa ma pobrac wszystkie nody z mapy duplicatemap i dac je do jakiejs listy obiektow nod azeby moc je pokazac w tabelli
        
        
        
        /////UPDATE VIEWERS \\\\\\
        
        private void setSunBurstVisable(){
            Platform.runLater(new Runnable() {  //mechanizm pozwalajacy na zmiane elementow FX GUI
                @Override                       //mechanizm pozwalajacy na zmiane elementow FX GUI
                public void run() {             //mechanizm pozwalajacy na zmiane elementow FX GUI
                    fXMLDocumentController.sunburstController.SetSunBurstVisable();
                }
            });
        }
        
        private void setTableView(){
            fXMLDocumentController.SetDuplicateFolderTable(finalDuplicateFolderLists);
            fXMLDocumentController.SetDuplicateFileTable(finalDuplicateFileLists);
        }

        public void setSunburstDuplicate(){
            Platform.runLater(() -> {         //mechanizm pozwalajacy na zmiane elementow FX GUI
                fXMLDocumentController.sunburstController.SetSunBurst(drzewo);
            });
        }
        
        public void setSunburstExtension(){
            Platform.runLater(() -> {
                fXMLDocumentController.sunburstController.SetSunBurst(treeExt);
            });
        }     
    }