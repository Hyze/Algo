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
public abstract class Player {
//abstract pour utilisation du polymorphisme
//classe mere des deux autres dont joueur et IA
    protected String nom;
    protected int mise;
    protected boolean Dealer;
    protected int argent;
    protected Carte main[];
    protected boolean Enjeu;
    
    Player(){
        this.mise = 0;
        this.Dealer = false;
        this.Enjeu = true;
        this.main = new Carte[2];
        this.argent = 1000;
    }
    
    //pour reprendre constructeur dans les classes filles avec super();
    
    public abstract boolean estJoueur();
    public abstract boolean estIA();
    
    public String getNom(){
        return this.nom;
    }
    public boolean estEnJeu(){
        return Enjeu;
    }
    
    public boolean estLeDealer(){
        if(this.Dealer)
            return true;
        else
            return false;
    }
    
    public void seCouche(){
        this.mise = 0;  //pour reinit la mise entre chaque tour mais peut etre a la fin
        this.Enjeu = false;
    }
    
    public int getMise(){
        return this.mise;
    }
    
    public int meilleurCombinaison(Carte c,Cartes t){
        Cartes tab = new Cartes();
        for(int j=0;j<t.getTaille();j++){
            tab.addCarte(t.getCarte(j));
        }
        for(int j=0;j<2;j++){
            tab.addCarte(this.main[j]);
        }
        //recuperation de toutes les cartes utiles pour le test
        
        boolean continuer = true;
        int i=0;
        boolean b = false;
        do{
           switch(i){
               //quinte flush royale
               case 0:
                   b = tab.QuinteFlushRoyal();
               break;
               
               //quinte flush
               case 1:
                   b = tab.QuinteFlush();
               break;
               //carré
               case 2:
                   b = tab.Carré();
               break;
               //full
               case 3:
                   b = tab.Full();
               break;
               
               //couleur ou flush
               case 4:
                   b = tab.Couleur();
               break;
               
               //quinte ou suite
               case 5:
                   b = tab.Suite();
               break;
               
               //brelan
               case 6:
                   b = tab.Brelan();
               break;
               
               //double pairs
               case 7:
                    b =  tab.DoublePaire();
               break;
               
               //paires
               case 8:
                   b = tab.Paire();
               break;
               
               //carte haute
               case 9:
                   b = true;
               break;
                   
           }
           i++;
        }while(!b);
        return i-1;
    }
    
    public abstract int proposition(int n);
    
    public void RecevoirCarte(Carte c,int n){
        System.out.println("Le joueur "+this.nom+" a comme "+(n+1)+"eme carte "+c);
        
        if(n>=0 && n<2)
        main[n] = c;
    }
    
    
    
    public int alea(int min,int max){
        return (int)(Math.random()*(max-min+1)+min);
    }
    
}
