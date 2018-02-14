/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

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
    }
