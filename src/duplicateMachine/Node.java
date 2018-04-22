package duplicateMachine;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

public class Node {
	final public static List<Node> EMPTY = Collections.emptyList();

	private File file;

        private List<Node> children;

	private String strHash;
        
	private LongProperty size = new SimpleLongProperty(); 
	private IntegerProperty numTotalChildren = new SimpleIntegerProperty(); 
        private StringProperty path = new SimpleStringProperty(); 
        private BooleanProperty selected = new SimpleBooleanProperty(false); 
        private BooleanProperty duplicate = new SimpleBooleanProperty(false); 
        
        
        
        private int groupFile = -1;
        private int groupFolder = -1;
        
        private IntegerProperty numTotalChildrenSelected = new SimpleIntegerProperty(0);
        
        private Node parentNode; 
        private boolean extension = false;
        private String extensionName = "";

        
        
	public Node(File file) {
		this.file = file;
		this.children = null;
                this.path.set(file.getPath());
                addListeners();
	}
        
        public Node(String ExtensionName, Boolean Extension, Long Size){ //kontruktor stworzony na potrzeby analizy rozszerzen i ich zajetosci
            this.extensionName = ExtensionName; 
            this.extension = Extension;
            this.size.set(Size);
        }

	public File getFile() {
		return this.file;
	}

	public void addChild(Node child) {
		if (this.children == null) {
			this.children = new ArrayList<Node>();
		}
		this.children.add(child);
                
	}

	public List<Node> getChildren() {
		return (this.children != null) ? this.children : EMPTY;

	}

        public void setChildren(List<Node> children) {
            this.children = children;
        }
        
        

	public void setHash(String strHash) {
		this.strHash = strHash;
	}

	public String getHash() {
		return this.strHash;
	}

	public void setSize(long size) {
 
		this.size.set(size);
   
	}

	public long getSize() {
            
  
		return this.size.get();
                
	}

	public void setNumTotalChildren(int numTotalChildren) {
           
		this.numTotalChildren.set(numTotalChildren);
   
	}

	public int getTotalChildrenCount() {
       
		return this.numTotalChildren.get();
             
	}

	@Override
	public String toString() {
           
            if(extension){
                return this.extensionName;
            }
            else{
                return this.file.getName();
            }
	}
        
        
        public String getStrHash() {
        return strHash;
        }

        public void setStrHash(String strHash) {
            this.strHash = strHash;
            
        }

        public Boolean getSelected() {
            return selected.get();
        }

        public void setSelected(Boolean selected) {
            this.selected.set(selected);

    }
        
        public Boolean isDuplicate() {
            return duplicate.get();
        }

        public void setDuplicate(Boolean selected) {
            this.duplicate.set(selected);

    }
        
        public final ObservableValue<Boolean> duplicateProperty() {
            return duplicate;
        }
        
        
        
        public final ObservableValue<Boolean> selectedProperty() {
            return selected;
        }
        public final ObservableValue<Number> sizeProperty() { 
            return size;
        }
        public final ObservableValue<Number> numTotalChildrenProperty() {
            return numTotalChildren;
        }
        public final ObservableValue<String> pathProperty() {
            return path;
        }
        
        public int getGroupFile() {
            return groupFile;
        }

        public void setGroupFile(int groupFile) {
            this.groupFile = groupFile;
        }
        public int getGroupFolder() {
            return groupFolder;
        }

        public void setGroupFolder(int groupFolder) {
            this.groupFolder = groupFolder;
        }

        public Node getParentNode() {
            return parentNode;
        }

        public void setParentNode(Node parentNode) {
            this.parentNode = parentNode;
        }
        
public void addListeners(){
    this.selected.addListener((Observable observable) ->{

        if(this.file.isDirectory()){ 
            if(this.selected.getValue()==true){            // w przypadku gdy zaznaczony zostaje folder,    
                for(Node childNode : this.getChildren()) { //zaznaczone zostają też wszystkie pliki i foldery potomne
                    childNode.setSelected(true);
                }
            }    
            else{ //w przypadku gdy folder zostaje odznaczony, należy sprawdzić czy zrobił to użytkownik, 
                  //czy folder został odznaczony z automatu, ponieważ któryś z jego potomnych został odznaczony.
                int iloscPlikowPotomnych = 0;
                int iloscPlikowZaznaczonychPotomnych = 0;
                for(Node childNode: this.getChildren()){
                    iloscPlikowPotomnych++; //liczenie plików/folderów potomnych
                    if(childNode.getSelected())
                        iloscPlikowZaznaczonychPotomnych++; //liczenie elementów zaznaczonych
                }
                if(iloscPlikowPotomnych == iloscPlikowZaznaczonychPotomnych){  //cała zawartość folderu jest zaznaczona, co oznacza że odznaczenie
                    for(Node childNode: this.getChildren()){                   //nie wynikało z automatu, tylko bezpośrednio przez użytkownika
                        childNode.setSelected(false);  // w takim przypadku, gdy użytkownik odznaczył cały folder, 
                    }                                   //wszystkie jest pliki i foldery również zostają odznaczone                    
                }
            }
        }

        //w przypadku gdy użytkownik, wybierze wszystkie pliki z danego folderu, sam folder również powinien zostać zaznaczony
        int iloscPlikowNaTymPoziomie = 0;
        int iloscPlikowZaznaczonychNaTymPoziomie = 0;

                for(Node parentsChildNode: this.parentNode.getChildren()){
                    iloscPlikowNaTymPoziomie++; //liczenie ilości plików/folderó w folderze nadrzednym
                    if(parentsChildNode.getSelected())
                        iloscPlikowZaznaczonychNaTymPoziomie++; //liczenie ilości zaznaczonych plików/folderów
                }

                if(iloscPlikowNaTymPoziomie == iloscPlikowZaznaczonychNaTymPoziomie ){ //jeśli po zmianie zaznaczenia tego pliku/folderu wszystkie pliki w folderze 
                    if(this.parentNode.getSelected()!= true)                           //nadrzednym sa zaznaczone, to folder nadrzędny też zostaje zaznaczony
                        this.parentNode.setSelected(true);
                }
                else{ //jeśli nie wszystkie pliki w folderze nadrzędnym są zaznaczone, to folder nadrzędny też nie może być zaznaczony
                    if(this.parentNode.getSelected()!= false)
                        this.parentNode.setSelected(false);
                }
            });
        }

   
    public boolean isExtension() {
        return extension;
    }

    public void setExtension(boolean extension) {
        this.extension = extension;
    }

    public String getExtensionName() {
        return extensionName;
    }

    public void setExtensionName(String extensionName) {
        this.extensionName = extensionName;
    }
        
}