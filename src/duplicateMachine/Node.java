

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
        
	private LongProperty size = new SimpleLongProperty(); //Property
	private IntegerProperty numTotalChildren = new SimpleIntegerProperty(); //Property
        private StringProperty path = new SimpleStringProperty(); //Property
        private BooleanProperty selected = new SimpleBooleanProperty(false); //Property
        private BooleanProperty duplicate = new SimpleBooleanProperty(false); //Property
        
        
        
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
       
                AddListeners();
           
                
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
        public final ObservableValue<Number> sizeProperty() { //powinno byc chyba ObservableValue<Long>
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
        
        
        
        public void AddListeners(){
            this.selected.addListener((Observable observable) ->{
                if(this.file.isDirectory()){ //przypadek dla folderu
                    // listener reaguje tylko na zmiany, a wiec jesli zmiana nastapiła 
                    //na zaznaczenie(true) to jest to zmiana spowodowany tylko 3 przypadkami:
                    // - wszystkie potomne zozstały zaznaczone i powinno byc to zaznaczone z automatu
                    // - uzytkownik kliknał zaznacz
                    // - folder nadrzedny został zaznaczony 
                    // Wszystkie 3 zmiany sprowadzaja sie do tgo by bezwzglednie zaznaczyc wszystkie potomne
                    if(this.selected.getValue()==true){ // jesli folder byl klikniety na true
                   
                        for(Node childNode : this.getChildren()) {
                            childNode.setSelected(true);
                            
                        }
                    }    


                    else{ // w przypadku zmiany folderu na false
                    // W przypadku else, czyi zmiana nastAPIŁA NA ODZNACZENIE MOZE BYC TO SPOWODOWANE 3 PRZypadkami

                        // - ktorys z podrzednych elementow został odklikniety.
                            //POMYSŁ NA IDENTYFIKACJE
                            //- sprawdzic czy ilosc potomnych elementow zgadza sie iloscia zaznaczonych potomnych elementow
                            //ZADANIA
                            //-poza odkliknieciem samego siebie nie robic nic. A z racji tego ze caly listener reaguje na odklikniecie to po
                            //identyfikacji ze nastapilo to  skutek odklikania podrzedniego nie robic nic
                            //POMYSL PSEUDOKODU:
                        
                        int iloscPlikowPotomnych = 0;
                        int iloscPlikowZaznaczonychPotomnych = 0;
                        for(Node childNode: this.getChildren()){
                            iloscPlikowPotomnych++;
                            if(childNode.getSelected())
                                iloscPlikowZaznaczonychPotomnych++;
                        }
                        if(iloscPlikowPotomnych != iloscPlikowZaznaczonychPotomnych )
                            {
                            /*nie rob nic*/}
                        
                                
                    
                        // - folder nadrzedny został PRZEZ UZYTKOWNIKA odklikany (badz tez przez system odklikany?)
                            //pomysł na zidentyfikowanie:
                            //- aby o tym sie dowiedziec najlepiej bylo by w nodzie dac referencje do noda nadrzednego
                            // /\ ale to jest bez sensu bo przeciez jesli bylby true to nie moglby zostac klikniety 
                            //zadania dla tego przypadku:
                            //- wszystkie pliki potomne powinny rowniez zostac 'odklikane' (selected -> false)
                        // - uzytkonik odklikną recznie ten folder
                            //pomysl na zidentyfikowanie
                            //-generalnie uzytkownik moze odkliknac taki folder w dowolnej konfiguracji zaznaczenia folderu nadrzednego,
                            //ale wiadomo ze w momencie odklikniecia wszystkie rzeczy potomne musiały byc na sztywno ZAZNACZONE
                            //ZADANIA DLA PRZYPADKU
                            //-odznaczyc wszystkie pliki potomne
                        
                        
                        // /\ owe 2 przypadki mozna razem zespolic poniewaz maja jednakowo zareagowac - > odklikac wszystkie podrzdne    
                            
                        //PSEUDOKOD REKCJI (bez analizy reakcji -> zalozyc ze reakcje wychwyci kolejny przypadeka ten trafi do else?)
                        
                        else{
                            for(Node childNode: this.getChildren()){
                                childNode.setSelected(false);

                            }
                        }
                        // Kazdy z przypadkow nalezy inaczej rozpatrzyc
                    }
                }
                else{  //przypadek dla pliku  -> (y)
          
                    //zmiana nastapiła na zaznaczenie to oznaczac moze 2 przypadki:
                    //- uzytkownik kliknal folder nadrzedny
                    //- uzytkownik kliknał na ten plik recznie
                    //W obu przypadkach system nie powinien wgl reagowac. Zmiana statusu pliku nastapi sama.

                    // w przypadku zmiany na odznacz rownies sa 2 przypadki:
                    // uzytkownik odznaczyl recznie badz tez folder nadrzedny został odklikany PRZEZ UZYTKOWNIKA.
                    //Tak jak wyzej, system nie musi reagowac na ta reakcje

                    // w przypadku gdy folder nadrzedny zostanie odklikany z automatu przez system plik nie powinien sie odznaczac
                    // /\ ale to nalezy zaznaczyc w reakcji folderu a nie pliku
                    
                    //Koniec koncow gdy jasno jest powienizne z zozstalo zmienione zaznaczenie typowo dla pliku, nie robic nic
                    
                    /* nie rob nic */
                    
                }

                // zzarowno dla folderu jak i pliku zbadac czy zostaly zaznaczone one wszystkie w danym katalogu
                // i jesli tak to zareagowac w taki sposob ze zaznaczyc folder nadrzedny. Do tego celu najlepiej zeby byla w danym nodzie refelrencja do niego
                //jesli natomiast nie sa juz to kompletne to odznaczyc
                //Zeby tez nie klikac bezsensownie i nie wywoływac niechcianego listenera, przez zmiana sprawdzic czy jest to zmiana potrzebna

                //POMYSL PSEUDOKODU:
                
                int iloscPlikowNaTymPoziomie = 0;
                int iloscPlikowZaznaczonychNaTymPoziomie = 0;
                for(Node parentsChildNode: this.parentNode.getChildren()){
                    iloscPlikowNaTymPoziomie++;
                    if(parentsChildNode.getSelected())
                        iloscPlikowZaznaczonychNaTymPoziomie++;
                }
                if(iloscPlikowNaTymPoziomie == iloscPlikowZaznaczonychNaTymPoziomie ){

                    if(this.parentNode.getSelected()!= true){
       
                        this.parentNode.setSelected(true);
             
                    }
                }
                else{

                    if(this.parentNode.getSelected()!= false){
 
                        this.parentNode.setSelected(false);
                    
                    }
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
        

