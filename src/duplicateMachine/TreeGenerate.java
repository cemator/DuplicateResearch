package duplicateMachine;

import controllers.sunburst.WeightedTreeItem;
import java.io.File;

/**
 *
 * @author Seweryn
 */
public class TreeGenerate {
    
    private double allItems = 0.0;
    
    public Node generateTreeNode(final Node node) { //tworzy strukture drzewiasta ze wszystkich plikow w badanym folderze
            allItems++; 
            File plik = node.getFile();
            if (plik.isFile()) { 
                long size = plik.length();
                node.setSize(size);   
                return node;  // jesli  node jest plikiem a nie folderem to wychodzi tedy
            }
            
            //czesc przeznaczona dla folderu \/
            
            for (File tempFile : plik.listFiles()) { //wywołanie rekurencyjne dla wszystkich plikow badanego folderu i dodanie ich do obecnego folderu
                node.addChild(generateTreeNode(
                        new Node(tempFile)));
            }

            return node;
        }
    
    
    public void assignParents (Node node){
        if(node.getChildren()!= null){
            for(Node childNode : node.getChildren()){
                childNode.setParentNode(node);
                if(childNode.getFile().isDirectory())
                    assignParents(childNode);
            }   
        }
    }

    public WeightedTreeItem<Node> buildTree(Node node){
        WeightedTreeItem<Node> tempTree;

        if (node.getFile().isFile()){
            tempTree = new WeightedTreeItem<>( node.getSize(),node);
        }
        else{ // node jest folderem
            tempTree = new WeightedTreeItem<>(node.getSize(),node);
            for(Node child : node.getChildren()){
                tempTree.getChildren().add(buildTree(child));
            }
        }
        return tempTree;
    }
    
    
    
    
    public double getAllItems() {
        return allItems;
    }
    
}
