/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker_partie;

import java.util.ArrayList;
import java.util.HashMap;

//import static poker_partie.IA.main;

/**
 *
 * @author tomskiev
 */
public class Partie {

    private final static int NB_PAQUET = 52,NB_CARTE_JOUEUR = 2;


    private ArrayList <Player> tab; //sert aussi pour le nombre de joueurs
    private int tour;
    //add de var pour identifier le dealer
    private int numDealer;

    private Cartes paquet;
    private Cartes tapis;
    boolean continuer =true;
    //var pour test
    public int resultat=-1;


    Partie(int nb_Ia,ArrayList <String> Nom){//nombre de joueurs recuperer dans le size du ArrayList avec les noms des joueurs
        this.tour = 0;
        if(Nom.size() == 1 && nb_Ia == 0)
            System.exit(-1);
        else if(Nom.size() > 4){    //pas plus de 4 joueurs humain peut etre changer bien sur
            System.exit(-1);
        }
        tab = new ArrayList();

        for(int i=0;i<Nom.size();i++){
            tab.add(new Joueur(Nom.get(i)));
        }

        if(nb_Ia > 0){
            for(int i=0;i<nb_Ia;i++){
                tab.add(new IA(tab));
            }
        }

        //initPaquet();//ca pour chaque début de partie

        for(int i=0;i<tab.size();i++){
            System.out.println("Le joueur numero "+(i+1)+" s'appelle "+tab.get(i).getNom());
        }

        //alea pour dealer au début du premier tour d'une partie
        numDealer = alea(0,tab.size()-1);
        tab.get(numDealer).setDealer(true);
        //premier a mettre au début


        while(continuer){
            gestionTour();
        }


    }


    private void gestionTour(){
        //int cptBlinde=0;

        //on peut mettre le dealer pour le tour de maniere aléa au début
        //On va dans ce cas procéder au décalage du dealer a chaque fin de tour
        //au début du tour gestion de la blinde Grosse et moyenne

        tab.get(numDealer).setBlinde(Player.GROSSEBLINDE);
        //verif que ca ne sorte pas de la taille du tab
        if((numDealer+1) < tab.size())
            tab.get(numDealer+1).setBlinde(Player.PETITEBLINDE);

        else
            tab.get(0).setBlinde(Player.PETITEBLINDE);


        /*tab.get(cptBlinde).setDealer(true);
        tab.get(cptBlinde+1).setBlinde(Player.PETITEBLINDE);
        tab.get(cptBlinde+2).setBlinde(Player.GROSSEBLINDE);*/

        //suite a la fin du switch



        switch(this.tour){
            //distribution des deux cartes et trois carte sur le tapis face retournées
            case 0:
                initPaquet();
                tapis = new Cartes();
                distribution();
                gestionTapis();
                //paris desactivé pour test
                //paris();
                break;

            //on montre les cartes du tapis
            case 1 : case 2:
                if(nbrJoueur() < 2){
                    //provisoire plus tard partage des gains des tours précédents
                    System.out.println("Pas assez de joueurs");
                    System.exit(-1);
                }
                gestionTapis();
                //paris desactivé pour test
                //paris();
                break;

            //on ajoute la derniere carte et les joueurs toujours en jeu montrent ce qu'ils ont
            case 3:
                if(nbrJoueur() < 2){
                    //provisoire plus tard partage des gains des tours précédents
                    System.out.println("Pas assez de joueurs");
                    System.exit(-1);
                }
                gestionTapis();
                //paris desactivé pour test
                paris();
                comparaison();
                continuer = false;
                break;
        }
        if(this.tour != 3)
            this.tour++;

        //gestion blinde suite
        //change le dealer et les blindes
        tab.get(numDealer).setDealer(false);
        tab.get(numDealer).setBlinde(0);
        if((numDealer+1) > tab.size()){
            tab.get(numDealer+1).setBlinde(0);
        }
        else{
            tab.get(0).setBlinde(0);
        }

        if((numDealer+1) < tab.size()){
            tab.get(numDealer+1).setDealer(true);
            numDealer++;
        }
        else{
            tab.get(0).setDealer(true);
            numDealer = 0;
        }


    }

    //met les cartes dans le paquet j pour la couleur et i pour le nombre de 11 a 13 les tetes
    private void initPaquet(){
        Carte tmp = new Carte();
        paquet = new Cartes();
        for(int j=0;j<4;j++){
            for(int i=0;i<13;i++){
                paquet.addCarte(new Carte((i+1),tmp.getTabCouleur(j)));
            }
        }
    }
    //ici les test des scores, si socre identique on appronfondira
    private void comparaison(){
        Carte c = new Carte();
        ArrayList <Player> vainqueur = new ArrayList();
        ArrayList <Carte> haute = new ArrayList();
        HashMap <Integer,Integer> combinaison = new HashMap();
        //System.out.println("En travaux pour le moment");
        int min,indice;
        for(int i=0;i<this.tab.size();i++){
            if(this.tab.get(i).estEnJeu()){
                combinaison.put(i,this.tab.get(i).meilleurCombinaison(c,this.tapis));  //on utilisera le c comme pointeur pour le retour de la carte la plus haute sera utile pour score similaire
                System.out.println(this.tab.get(i).getNom()+" a "+combinaison.get(i));
            }
        }
        min = combinaison.get(0);
        indice = 0;
        for(int i=0;i<this.tab.size();i++){
            if(this.tab.get(i).estEnJeu()){
                if(min > combinaison.get(i)){
                    min = combinaison.get(i);
                    indice = i;
                }
            }
        }
        System.out.println("Le vainqueur est "+this.tab.get(indice).getNom()+" avec un nombre numero "+combinaison.get(indice));
        resultat = combinaison.get(indice);

    }



    private int nbrJoueur(){
        int cmpt = 0;
        for(int i=0;i<this.tab.size();i++){
            if(this.tab.get(i).estEnJeu())
                cmpt++;
        }
        return cmpt;
    }



    private void gestionTapis(){
        switch(this.tour){
            case 0:
                //face retournée avec skin retournée
                break;

            case 1:

                for(int i=0;i<3;i++){
                    this.tapis.addCarte(this.paquet.remove(alea(0,paquet.getTaille()-1)));
                }
                break;

            case 2: case 3:
                this.tapis.addCarte(this.paquet.remove(alea(0,paquet.getTaille()-1)));
                break;
        }
        System.out.println("On a sur le tapis :");
        for(int i=0;i<this.tapis.getTaille();i++){
            System.out.println(this.tapis.getCarte(i));
        }
    }

    private void distribution(){
        Carte c;
        for(int i=0;i<this.tab.size();i++){
            for(int j=0;j<NB_CARTE_JOUEUR;j++){
                c = this.paquet.remove(alea(0,this.paquet.getTaille()-1));
                tab.get(i).RecevoirCarte(c, j);
            }
        }
    }

    private void paris(){
        int tmp;
        int valeur = 0; //au dealer plutot mais on verra apres            //on va comp les deux valeur la tmp et la valeur servant de témoin entre chaque tour de proposition
        int valeurTmp = valeur;
        Cartes tapis = this.tapis;
        boolean continuer = true;
        do{
            if(valeur == 0){    //un tour de table obligatoire au debut
                for(int i=0;i<this.tab.size();i++){
                    if(this.tab.get(i).estEnJeu()){
                        tmp = this.tab.get(i).proposition(valeurTmp,tapis);
                        //se couche
                        if(tmp == -1){
                            this.tab.get(i).seCouche();
                        }
                        //suit
                        else if(tmp == valeurTmp){

                        }
                        //augmente
                        else if(tmp > valeurTmp){
                            valeurTmp = tmp;
                        }
                    }
                }
            }

            else{   //on regarde uniquement si les joueur on la mise qui est ajustée avec la mise actuelle
                for(int i=0;i<this.tab.size();i++){
                    if(this.tab.get(i).estEnJeu() && this.tab.get(i).getMise() < valeurTmp){
                        tmp = this.tab.get(i).proposition(valeurTmp,tapis);
                        //se couche
                        if(tmp == -1){
                            this.tab.get(i).seCouche();
                        }
                        //suit si tmp == valeurTmp

                        //augmente
                        else if(tmp > valeurTmp){
                            valeurTmp = tmp;
                        }
                    }
                }
            }

            if(valeur == valeurTmp)
                continuer = false;
            else{
                valeur = valeurTmp;
            }
        }while(continuer);
    }

    public int alea(int min,int max){
        return (int)(Math.random()*(max-min+1)+min);
    }




    public int ProbRejouer(){
        return alea(0,100);
    }
}