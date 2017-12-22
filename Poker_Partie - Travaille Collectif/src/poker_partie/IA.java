/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker_partie;

import java.util.ArrayList;

/**
 *
 * @author tomskiev
 */
public class IA extends Player {
    final static String tabnom[] = {"johnny","arthur","paul","joe"}; 
    
    
    IA(ArrayList <Player> nom){
        super();
        String tmp;
        tmp = Choosenom();
        //pour des nom differents des joueurs présent et on pourra verif plus tard les doublons entre IA
        for(int i=0;i<nom.size();i++){
            if(nom.get(i).getNom().equals(tmp)){
                this.nom = tmp+alea(4,100);
            }
        }
        if(this.nom == null)
            this.nom = tmp;
        
        System.out.println("L'ia s'appelle "+this.nom);
        
    }
    
    public int proposition(int n){
        boolean b = true;
        calculeProba();
        b = choix();
        if(b){
            return choixPari(n); 
        }
        else{
            return -1;
        }
    }


    
    public void calculeProba(){

        for(int i=0;i<main.length;i++)
        {
            main[i].getNombre();
        }
        //a faire en utilisant la Class Cartes et ses test
        System.out.println("Calcul des chances de gagner avec actualisation des chances par combinaison");
    }
    
    public boolean choix(){
        //a faire en utilisant la Class Cartes et ses test
        return true;
    }
    
    public String Choosenom(){
        return tabnom[alea(0,3)];
    }
    
    public int choixPari(int n){
        //avec proba a faire
        return n;
    }
    
    public boolean estJoueur(){
        return true;
    }
    public boolean estIA(){
        return false;
    }
}
