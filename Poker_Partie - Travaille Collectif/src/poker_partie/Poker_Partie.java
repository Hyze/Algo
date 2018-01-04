/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker_partie;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author tomskiev
 */
public class Poker_Partie {
    
    //fonction de rechercher de chaine de caractere je ne sais plus trop pourquoi mais on laisse pour le moment
    public static int find(ArrayList <String> s,String n){
        int cmpt = 0;
        for(int i=0;i<s.size();i++){
            if(s.get(i).equalsIgnoreCase(n))
                cmpt++;
        }
        return cmpt;
    }

    //classe main avec un test avec deux joueurs
    public static void main(String[] args) {
        ArrayList <String> nom = new ArrayList();
        boolean continuer = true;
        
        Scanner sc = new Scanner(System.in);
        String tmp;
        int t=0;
        
        //nom.add("John");
        //nom.add("Battou");
        nom.add("Batman");
        nom.add("Joker");
        //pour tester resultat particulier
        /*while(continuer){
         Partie p = new Partie(2,nom);
        }*/
        Poker p = new Poker();
    }
    
    
    
}
