/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duplicateMachine;

import helpers.Utils;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Lenovo
 */
public class Hasher {
    
    ProgressBarDriver progressBarDriver;
    
    private MessageDigest messageDigest;
    
    private Map<Long, Node> mapaDlugosci = new HashMap<>();
    private Set<Long> setDlugosci = new HashSet<>();
    
    public Hasher(ProgressBarDriver progressBarDriver){
        this.progressBarDriver = progressBarDriver;
        
        try {
                this.messageDigest = MessageDigest.getInstance("MD5");
            }
            catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
    }
    
     public void hashujPliki(final Node node){                              // metoda do logicznej poprawki!

           progressBarDriver.setProgressBarValue();
           File file = node.getFile();
            
            if (file.isFile()) {
               long rozmiarPliku = file.length();
               if (this.mapaDlugosci.containsKey(rozmiarPliku) == false) { // sprawdza czy dlugoci pliku nie ma w mapie dlugoci plikow
                    this.mapaDlugosci.put(rozmiarPliku, node);              // jesli nie ma to go dodaje

                    String strHash = Utils.getHash(file,null);
                    node.setHash(strHash); //dodaje Hasha skladajacego sie tylko z dlugosci do noda
                           //  \wstepne hashowanie potrzebne do hashowania folderow - na podstawie pobierania hashow wszystkich plikow
                } 
                        //jesli dlugosc pliku jest juz w zbiorze mapadlugosci long,node 
                else { // jesli dlugosc pliku jest w mapie dlugosci (jest potencialnym duplikatem)
                    if (this.setDlugosci.contains(rozmiarPliku) == false) { //sprawdza czy dlugosc tego pliku jest juz w zbiorze set(pierwsze powtorzenie dlugosci pliku)
                        this.setDlugosci.add(rozmiarPliku);			//jesli nie ma to go dodaje

                        Node cachedNode = this.mapaDlugosci.get(rozmiarPliku);//wrzuca noda o dlugosci noda ktory sie raz powtarza do noda pamieciowego pobierajac go z mapy
                            // jesli rozmiar pliku sie dubluje w mapieDlugosci to wtedy dodaje ta dlugosc do setu - na tej podstawie bedzie sprawdzana pozniej przynalreznosc innych plikow. Na podstawie tej dlugosci jest wyciagana pierwszy nod o tej dlugosci i jest hashowany  - co było wczesniej pominiete azeby nie hashowa niepotrzebnie zbyd tuzej ilosci plikow (zasobozernosc)
                            //wszystkie nastepne nody o tej samej dlugosci zostaja juz w pełni hashowane
                            //cala struktura set jest tylko po to aby wychwycic pierwsze wystapienie drugi raz tej samej dlugosci - wtedy jst mozliwe wrocenie i zczytanie pierwszego noda o tej wielkosci i zahashowanie
                            String strHash = hashFile(cachedNode.getFile());
                        cachedNode.setHash(strHash);
                    }

                        String strHash = hashFile(node.getFile());
               
                    node.setHash(strHash); //dodaje pełnego hasha          
                }
                    return; //jesli jest to plik to wychodzi z metody, jesli nie to bada foldery potomne \/
            }

            for (Node child : node.getChildren()) {
                hashujPliki(child);
            }
        
        }
     
     private String hashFile(File file) {
            final int SIZE = 4096;
            InputStream in = null;
            try {
                this.messageDigest.reset();
                in = new BufferedInputStream(new FileInputStream(file));                                                                //.getAbsolutePath()
                byte[] buffer = new byte[SIZE];
                while (true) {
                    int b = in.read(buffer);
                    if (b == -1) {
                            break;
                    }
                    this.messageDigest.update(buffer);                                                                                  //, 0, buffer.length);
                }
            } catch (IOException e) {
                System.out.println("IOException with file " + file.getAbsolutePath());
            } finally {
                Closeable closeable = in;
                if (closeable != null) {
                    try {closeable.close();} 
                    catch (IOException e) {}
                }
            }
            return Utils.getHash(file, this.messageDigest);                                                                             //przekazuje plik i mechanizm hashujacy do narzedzia w Utils
        }
     
     public void hashujFoldery(Node node) {

            progressBarDriver.setProgressBarValue();
            
            if (node.getFile().isFile()) {
                return;
            }

            long totalSize = 0;
            int totalNumChildren = 0;
            List<String> sortedHashes = new ArrayList<>(); //zbior hashow folderow
            
            for (Node child : node.getChildren()) {
                
                hashujFoldery(child);

                sortedHashes.add(child.getHash()); //dodawanie hasha elementu potomnego (moze byc to folder lub tez PLIK)
                totalSize += child.getSize();

                if (child.getFile().isFile()) {
                    totalNumChildren++;
                } else {
                    totalNumChildren += child.getTotalChildrenCount() + 1; // children + this folder
                }
            }
            
            node.setSize(totalSize);
            node.setNumTotalChildren(totalNumChildren);

            this.messageDigest.reset();
            this.messageDigest.update("<folder>".getBytes(Charset.defaultCharset())); // folder's MD5 seed, includes illegal characters for filepaths
            Collections.sort(sortedHashes);
            for (String sortedHash : sortedHashes) {
                this.messageDigest.update(sortedHash.getBytes(Charset.defaultCharset()));
            }
            String strHash = Utils.getHash(node.getFile(), this.messageDigest);
        
            node.setHash(strHash);
	}
        
    
}
