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
    protected Cartes main;
    protected boolean Enjeu;
    protected int Blinde;

    final static int GROSSEBLINDE = 20 ;
    final static int PETITEBLINDE = 10 ;

    Player(){
        this.mise = 0;
        this.Dealer = false;
        this.Enjeu = true;
        this.main = new Cartes();
        this.argent = 1000;
    }

    //pour reprendre constructeur dans les classes filles avec super();

    public void setArgent(int n){
        argent += n;
    }
    
    public int getArgent(){
        return this.argent;
    }
    
    public void setMise(int mise) {
        this.mise = mise;
    }

    public void setBlinde(int blinde) {
        Blinde = blinde;
    }

    public int getBlinde(){
        return Blinde;
    }

    public void setDealer(boolean dealer) {
        Dealer = dealer;
    }

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
        System.out.println("Le joueur "+this.getNom()+" se couche.");
        this.Enjeu = false;
    }
    public void seReleve(){
        this.Enjeu = true;
    }
    

    public int getMise(){
        return this.mise;
    }

    public int meilleurCombinaison(ArrayList <Carte> c,Cartes t){
        Cartes tab = new Cartes();
        for(int j=0;j<t.getTaille();j++){
            tab.addCarte(t.getCarte(j));
        }
        for(int j=0;j<2;j++){
            tab.addCarte(this.main.getCarte(j));
        }
        //recuperation de toutes les cartes utiles pour le test
        //attention aussi on type de verif avec carte haute meme pour une autre combi a voir

        boolean continuer = true;
        int i=0;
        boolean b = false;
        do{
            switch(i){
                //quinte flush royale
                case 0:
                    b = tab.QuinteFlushRoyal();
                    c.add(new Carte(14,tab.CouleurNom()));
                    break;

                //quinte flush
                case 1:
                    b = tab.QuinteFlush();
                    c.remove(0);
                    c.add(tab.SuiteHaute());
                    break;
                //carré
                case 2:
                    b = tab.Carré();
                    c.remove(0);
                    //quand carré la couleur n'importe pas car il les a toute dans combinaison
                    c.add(new Carte(tab.CarréChiffre(),"Coeur"));
                    break;
                //full
                case 3:
                    b = tab.Full();
                    c.remove(0);
                    //a verif ordre de comp
                    c.add(new Carte(tab.BrelanChiffre(),"Coeur"));
                    break;

                //couleur ou flush
                case 4:
                    b = tab.Couleur();
                    c.remove(0);
                    c.add(new Carte(tab.CarteHauteCouleur(),tab.CouleurNom()));
                    break;

                //quinte ou suite
                case 5:
                    b = tab.Suite();
                    c.remove(0);
                    c.add(tab.SuiteHaute());
                    break;

                //brelan
                case 6:
                    b = tab.Brelan();
                    c.remove(0);
                    //encore une fois la couleur n'est pas un facteur important dans cette cominaison
                    c.add(new Carte(tab.BrelanChiffre(),"Coeur"));
                    break;

                //double pairs
                case 7:
                    b =  tab.DoublePaire();
                    c.remove(0);
                    //pareil couleur peu importante
                    c.add(new Carte(tab.DoublePaireChiffre(),"Coeur"));
                    break;

                //paires
                case 8:
                    b = tab.Paire();
                    c.remove(0);
                    c.add(new Carte(tab.PaireChiffre(),"Coeur"));
                    break;

                //carte haute
                case 9:
                    b = true;
                    c.remove(0);
                    c.add(new Carte(tab.CarteHaute(),"Coeur"));
                    break;

            }
            i++;
        }while(!b);
        //System.out.println("c est de "+c.get(0));
        return i-1;
    }

    public abstract int proposition(int n,Cartes tapis,Partie p);

    public void RecevoirCarte(Carte c,int n){
        System.out.println("Le joueur "+this.nom+" a comme "+(n+1)+"eme carte "+c);

        if(n>=0 && n<2)
            main.addCarte(c);
    }



    public int alea(int min,int max){
        return (int)(Math.random()*(max-min+1)+min);
    }

}
