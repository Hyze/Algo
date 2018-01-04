/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker_partie;

import java.util.ArrayList;
import java.util.HashMap;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.*; // Pour pouvoir utiliser les fichiers
import java.util.ArrayList;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

//import static poker_partie.IA.main;

/**
 *
 * @author tomskiev
 */
public class Partie extends JFrame implements ActionListener {

    private final static int NB_PAQUET = 52,NB_CARTE_JOUEUR = 2;
    private final int HAUTEUR = 720;
    private final int LARGEUR = 1280;
    
    
    private boolean action;
    private int ibis;
    
    private JPanel table;            
    private JPanel p1;                    // zone boutons hauts
    private JPanel p2;

    private ArrayList <Player> tab; //sert aussi pour le nombre de joueurs
    private int tour;
    //add de var pour identifier le dealer
    private int numDealer;
    //argent des mise pendant un round
    private int argent = 0;
            
    private Cartes paquet;
    private Cartes tapis;
    boolean continuer =true;
    public int resultat=-1;
    
    
    

    Partie(int nb_Ia,ArrayList <String> Nom){//nombre de joueurs recuperer dans le size du ArrayList avec les noms des joueurs
        super();
        mise_en_page( LARGEUR, HAUTEUR ); 
        
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
    
    
    
    public void mise_en_page(int maxX, int maxY) 
    {   
        setTitle("Poker");
        setSize(maxX,maxY);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

        this.p1 = new JPanel(new GridLayout());
  	getContentPane().add(p1,"North");
        this.p2 = new JPanel(new GridLayout());
        getContentPane().add(p2,"South");
        
        this.table = new JPanel();
        this.table.setSize(maxX,maxY);                                  
        this.table.setPreferredSize(new Dimension(maxX,maxY));
        getContentPane().add(this.table,"Center");
        /*JLabel j = new JLabel(new ImageIcon("cartes/decors.jpg"));
        this.table.add(j);*/
        
        
        
        // Ajout des boutons : 
        ajouteBouton("relance",p2);
        ajouteBouton("suit",p2);
        ajouteBouton("se couche",p2);
        /*
        ajouteBouton("Ajouter une IA", p1);
        ajouteBouton("Retirer une IA",p1);
        */
  	ajouteBouton("Quitter", p1);
        
        pack();
        setVisible(true);
        //JOptionPane.showMessageDialog(null,"Bienvenue dans le Jeu de Poker !","Information",JOptionPane.INFORMATION_MESSAGE);
        
    }
    
    
    private void ajouteBouton(String label, JPanel p) {
        // Ajoute un bouton, avec le texte label, au panneau p
        JButton b = new JButton(label);
        p.add(b);
        b.addActionListener(this);
    }
    
    
    
    


    private void gestionTour(){

        switch(this.tour){
            //distribution des deux cartes et trois carte sur le tapis face retournées
            case 0:
                initPaquet();
                tapis = new Cartes();
                distribution();
                debout();
                //gestion de tri en fonction des petite et grosse blindes
                triJoueursDealer();
                gestionTapis();
                paris();
                break;

            //on montre les cartes du tapis
            case 1 : case 2:
                if(nbrJoueur() < 2){
                    //provisoire plus tard partage des gains des tours précédents
                    partageArgent();
                    System.out.println("Pas assez de joueurs");
                    tour = -1;
                }
                gestionTapis();
                paris();
                break;

            //on ajoute la derniere carte et les joueurs toujours en jeu montrent ce qu'ils ont
            case 3:
                if(nbrJoueur() < 2){
                    partageArgent();
                    System.out.println("Pas assez de joueurs");
                    tour = -1;
                }
                gestionTapis();
                paris();
                comparaison();
                //reinit paquet des joueurs
                ReinitJoueurs();
                this.tour = -1;
                //verif de qui est encore en lys
                
                for(int i=0;i<this.tab.size();i++){
                    if(this.tab.get(i).getArgent() <= 0){
                        System.out.println("Le joueur "+this.tab.get(i).getNom()+" est éliminé.");
                        this.tab.remove(i);
                    }
                }
                //continuer = false;
                break;
                
        }
        if(this.tour != 3)
            this.tour++;

        //gestion blinde suite
        //change le dealer et les blindes
        this.tab.get(numDealer).setDealer(false);
        if(numDealer+1 <= this.tab.size()-1){
            numDealer++;
        }
        else{
            numDealer = 0;
        }
        this.tab.get(numDealer).setDealer(true);
        


    }
    
    public void debout(){
        for(int i=0;i<this.tab.size();i++){
            this.tab.get(i).seReleve();
        }
    }
    
    
    public void ReinitJoueurs(){
        for(int i=0;i<this.tab.size();i++){
            this.tab.get(i).main = new Cartes();
        }
    }
    
    public void triJoueursDealer(){
        //Dealer initié au début de chaque partie
        Player p1,p2;
        System.out.println("Dealer num est de "+numDealer+" et tab est de "+tab.size());
        
        switch(numDealer){
            case 0:
                p1 = this.tab.remove(this.tab.size()-1);
                //car arrayList
                p2 = this.tab.remove(this.tab.size()-1);
            break;
            case 1:
                p1 = this.tab.remove(0);
                p2 = this.tab.remove(this.tab.size()-1);
            break;
            default:
                p1 = this.tab.remove(numDealer-1);
                p2 = this.tab.remove(numDealer-2);
            break;
        }
        this.tab.add(0, p1);
        this.tab.add(1, p2);
        
    }
    
    public void partageArgent(){
        for(int i=0;i<this.tab.size();i++){
            if(this.tab.get(i).estEnJeu()){
                this.tab.get(i).setArgent(this.argent/nbrJoueur());
            }
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
        ArrayList <Carte> c = new ArrayList();
        int cmpt = 0;
        //int indiceTmp;
        ArrayList <Player> vainqueur = new ArrayList();
        HashMap <Integer,Carte> haute = new HashMap();
        HashMap <Integer,Integer> combinaison = new HashMap();
        //System.out.println("En travaux pour le moment");
        int min,indice;
        indice = 0;
        for(int i=0;i<this.tab.size();i++){
            if(this.tab.get(i).estEnJeu()){
                combinaison.put(i,this.tab.get(i).meilleurCombinaison(c,this.tapis));  //on utilisera le c comme pointeur pour le retour de la carte la plus haute sera utile pour score similaire
                System.out.println(this.tab.get(i).getNom()+" a "+combinaison.get(i));
                haute.put(i,new Carte(c.get(0).getNombre(),c.get(0).getCouleur()));
                c.remove(0);
                indice = i;
                //System.out.println("La carte haute de "+tab.get(i).getNom()+" est "+haute.get(i).getNombre());
                //System.out.println(c.get(0));
            }
        }
        min = combinaison.get(indice);
        
        //resultat pour le plus petit score donc la score le plus important
        
        for(int i=0;i<this.tab.size();i++){
            if(this.tab.get(i).estEnJeu()){
                if(min > combinaison.get(i)){
                    min = combinaison.get(i);
                    indice = i;
                }
            }
        }
        
        //verif si plusieurs joueurs ont le même score
        
        for(int i=0;i<this.tab.size();i++){
            if(this.tab.get(i).estEnJeu()){
                if(combinaison.get(i) == min){
                    cmpt++;
                }
            }
        }
        
        //comparaison des cartes les plus hautes
        int max = 0;
        if(cmpt > 1){
            //verif de la carte la plus haute
            for(int i=0;i<tab.size();i++){
                //fonctionne car element a gauche du if prio dans test
                if(tab.get(i).estEnJeu() && combinaison.get(i) == min){
                  if(max<haute.get(i).getNombre()){
                      max = haute.get(i).getNombre();
                      indice = i;
                  }   
                }
            }
            cmpt=0;
            //verif si plusieurs carte hautes
            for(int i=0;i<tab.size();i++){
                if(tab.get(i).estEnJeu() && (combinaison.get(i) == min) && (haute.get(i).getNombre() == max)){
                   cmpt++;  
                }
            }
            if(cmpt > 1){
                System.out.println("Il y a plusieurs vainqueurs !!!!!!");
                partageArgent();
                for(int i=0;i<tab.size();i++){
                    if(tab.get(i).estEnJeu() && combinaison.get(i) == min && haute.get(i).getNombre() == max){
                        System.out.println("Un des vainqueurs est "+this.tab.get(i).getNom()+" avec un nombre numero "+combinaison.get(i)+" avec en carte haute "+haute.get(i).getNombre());
                    }
                }
            }
            else{
                System.out.println("Le vainqueur est "+this.tab.get(indice).getNom()+" avec un nombre numero "+combinaison.get(indice));
                this.tab.get(indice).setArgent(this.argent);
                resultat = combinaison.get(indice);
            }
        }
        else{
        System.out.println("Le vainqueur est "+this.tab.get(indice).getNom()+" avec un nombre numero "+combinaison.get(indice));
        this.tab.get(indice).setArgent(this.argent);
        resultat = combinaison.get(indice);
        }
        
        
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

    private void paris() throws InterruptedException{
        int tmp = 0;
        int valeur = 0; //au dealer plutot mais on verra apres            //on va comp les deux valeur la tmp et la valeur servant de témoin entre chaque tour de proposition
        int valeurTmp = valeur;
        Cartes tapis = this.tapis;
        boolean continuer = true;
        do{
            if(valeur == 0){    //un tour de table obligatoire au debut
                if(this.tab.get(0).estEnJeu()){
                    argent+= Player.PETITEBLINDE;
                    valeurTmp = Player.PETITEBLINDE;
                    this.tab.get(0).setMise(Player.PETITEBLINDE);
                }
                if(this.tab.get(1).estEnJeu()){
                    argent+=Player.GROSSEBLINDE;
                    valeurTmp = Player.GROSSEBLINDE;
                    this.tab.get(1).setMise(Player.GROSSEBLINDE);
                }
                for(ibis=2;ibis<this.tab.size();ibis++){
                    if(this.tab.get(ibis).estEnJeu()){
                        if(this.tab.get(ibis).estIA())
                            tmp = this.tab.get(ibis).proposition(valeurTmp,tapis,this);
                        else{
                            action = false;
                            while(!action){
                                Thread.sleep(200);
                            }
                        }
                        //se couche
                        if(tmp == -1){
                            this.tab.get(ibis).seCouche();
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
                for(ibis=0;ibis<this.tab.size();ibis++){
                    if(this.tab.get(ibis).estEnJeu() && this.tab.get(ibis).getMise() < valeurTmp){
                        tmp = this.tab.get(ibis).proposition(valeurTmp,tapis,this);
                        argent += tmp;
                        //se couche
                        if(tmp == -1){
                            this.tab.get(ibis).seCouche();
                        }
                        //suit si tmp == valeurTmp

                        //augmente
                        else if(tmp > valeurTmp){
                            valeurTmp = tmp;
                        }
                    }
                }
                for(int i=0;i<this.tab.size();i++){
                    if(this.tab.get(i).estEnJeu()){
                        argent+=valeurTmp;
                        this.tab.get(i).setArgent(-valeurTmp);
                    }
                }
                if(!this.tab.get(0).estEnJeu()){
                    this.tab.get(0).setArgent(-Player.PETITEBLINDE);
                    this.argent+=Player.PETITEBLINDE;
                }
                if(!this.tab.get(1).estEnJeu()){
                    this.tab.get(1).setArgent(-Player.GROSSEBLINDE);
                    this.argent+=Player.GROSSEBLINDE;
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

    @Override
    public void actionPerformed(ActionEvent e) {
        String arg = e.getActionCommand();  
        
        if(arg.equals("relance")){
            
        }
        if(arg.equals("suit")){
            
        }
        if(arg.equals("se couche")){
            
        }
        
        if (arg.equals("Quitter"))
        {
            System.exit(0);
        }
 							
   	   
            
         
    repaint();
    }
}