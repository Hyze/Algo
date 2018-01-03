/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker_partie;

import java.util.Scanner;

/**
 *
 * @author tomskiev
 */
public class Joueur extends Player {
    
    
    Joueur(String n){
        super();
        this.nom = n;
    }
    
    public int proposition(int n,Cartes tapis){
        int nb=0;
        boolean continuer = true;
        Scanner sc = new Scanner(System.in);
        String s;
        System.out.println("Tour de "+this.nom);
        //on etoffera ca un peu plus tard
        while(continuer){
        System.out.println("Les paris sont a "+n+"\nQue proposez vous ?");
        nb = Integer.parseInt(sc.nextLine());
            if((nb >= n && nb <= this.argent) || nb == -1){ //-1 pour se coucher
                continuer = false;
            }
            else{
                System.out.println("La somme doit etre superieur ou egal a "+n);
            }
        }
        this.mise = nb;
        return nb;
        
    }
    
    
    public boolean estJoueur(){
        return true;
    }
    public boolean estIA(){
        return false;
    }
}
