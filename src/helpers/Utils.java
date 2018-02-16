/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import controllers.sunburst.WeightedTreeItem;
import duplicateMachine.Node;
import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 *
 * @author Seweryn
 */
    public class Utils {


        public static String getHash(File file, MessageDigest messageDigest) {
            String hashOutput;
            if(messageDigest == null){
                final String strEmptyMd5Hash = "--------------------------------";
                hashOutput = strEmptyMd5Hash;
            }
            else{
                hashOutput = String.format("%032x", new BigInteger(1,messageDigest.digest()));
            }
            return getHashInternal(file, hashOutput);
        }

        private static String getHashInternal(File file, String strMd5Hash) {                                                               //zwraca stringa skladajacego sie z dlugosci pliku + hasha
            String strHash;
            if (file.isFile()) { //jesli plik
                strHash = String.format("%016x%s", file.length(), strMd5Hash);
                System.out.println(strHash);
            } else { //jesli folder
                final String strEmptyLength = "----------------";
                strHash = String.format("%s%s", strEmptyLength, strMd5Hash);
            }
            return strHash;
        }
        
        public static String ustalRozszerzenie(File file){
            String rozszerzenie2 = file.getName();
            String rozszerzenie = "";
            rozszerzenie2 = rozszerzenie2.toLowerCase();
            if(file.isDirectory()!=true){
                for(int i = rozszerzenie2.length()-1 ; i>=0 ; i--){
                    if(rozszerzenie2.charAt(i)!='.'){
                        rozszerzenie = rozszerzenie + rozszerzenie2.charAt(i);
                    }
                    else{
                        rozszerzenie2 = rozszerzenie;
                        rozszerzenie = "";
                        for(int j = rozszerzenie2.length()-1 ; j>=0 ; j--)
                        {
                            rozszerzenie = rozszerzenie + rozszerzenie2.charAt(j);
                        }
                        break;
                    }           
                }
            }
            else{
                rozszerzenie = "";
            }
            return rozszerzenie;
        }
        
        public static WeightedTreeItem<Node> zNodaNaDrzewoRozszerzen(Node node ){
            WeightedTreeItem<Node> treeExt = new WeightedTreeItem<>(node.getSize(),node);
            java.util.List<Node> listaTemp=new java.util.ArrayList();
            
            for(Node nodeTemp : node.getChildren()){
                boolean obecneRozszerzenie = false;
                int numerObiektuRoz = -1;
                if(nodeTemp.getFile().isFile()){//jesli plik
                    for(int j = listaTemp.size() ; j>0 ; j--){//przejscie przez cala liste juz dodanych nowych obiektow w sprawdzeniu czy rozszerzenie nie pasuje do ktoregos z nich
                        if(Utils.ustalRozszerzenie(nodeTemp.getFile()).equals((listaTemp.get(j-1).getExtensionName()).substring(2))){//sprawdzenie czy plik o posiada jakies piki o tym rozszerzeniu w bazie
                            obecneRozszerzenie=true; //jest obecne juz takie rozszerzenie
                            numerObiektuRoz = j-1; //oznaczenie na ktorej pozycji w listaTemp leży nasze rozszerzenie
                            //break; // <- po znalezieniepowoduje wyjscie z petli
                        }
                    }
                    
                    if(obecneRozszerzenie==false){ // jesli nie bylo wczesniej w tym folderze pliku o takim rozszerzeniu
                          listaTemp.add(new Node(("#."+Utils.ustalRozszerzenie(nodeTemp.getFile()) )
                                ,true
                                ,nodeTemp.getSize())); // dodaj do listy nowy plik rozszerzen z nazwa rozszerzenia i informacja ze to rozszerzenie a nie zwykły nod
                       
                    }
                    else{ // jesli takie rozszerzenie juz sie pojawilo wczesciej
                        listaTemp.get(numerObiektuRoz).setSize(listaTemp.get(numerObiektuRoz).getSize()+nodeTemp.getSize()); // dodaj do obiektu wielkosc nowego pliku
                    }
                }
                
                
                else if(nodeTemp.getFile().isDirectory()){
                    treeExt.getChildren().add(zNodaNaDrzewoRozszerzen( nodeTemp)); 
               
                }
            }
  
            for(Node listNode: listaTemp){
                treeExt.getChildren().add(new WeightedTreeItem<>(listNode.getSize(),listNode));
              
            }

            return treeExt;
        }
        
        
    }
