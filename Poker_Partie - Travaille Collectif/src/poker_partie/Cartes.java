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
public class Cartes {
    private ArrayList <Carte> tab;
    
    Cartes(){
        tab = new ArrayList();
    }
    
    public Carte getCarte(int i){
        if(i<tab.size() && i>=0)
        return this.tab.get(i);
        else
            return null;
    }
    public Cartes copie(){
        Cartes tmp = new Cartes();
        for(int i=0;i<this.tab.size();i++){
            tmp.addCarte(new Carte(this.getCarte(i).getNombre(),this.getCarte(i).getCouleur()));
        }
        return tmp;
    }
    
    public Carte remove(int n){
        if(n>=0 && n<this.tab.size())
            return this.tab.remove(n);
        else
            return null;
    }
    
    public ArrayList <Carte> getCartes(){
        return this.tab;
    }
    
    public void addCarte(Carte c){
        this.tab.add(c);
    }
    
    public int getTaille(){
        return this.tab.size();
    }
    
    public int CarteHaute(){
        int max = this.getCarte(0).getNombre();
        int indice = 0;
        for(int i=0;i<this.getTaille();i++){
            if(max < this.getCarte(i).getNombre()){
                max = this.getCarte(i).getNombre();
                indice = i;
            }
        }
        return max;
    }
    
    
    public boolean Couleur(){
        boolean continuer =true;
        boolean couleur = false;
        int j = 0;
        int tab[] = {0,0,0,0};
        for(int i=0;i<this.tab.size();i++){
            switch(this.tab.get(i).getCouleur()){
                case "coeur":
                    tab[0]++;
                break;
                
                case "carreau":
                    tab[1]++;
                break;
                
                case "pique":
                    tab[2]++;
                break;
                
                case "treffle":
                    tab[3]++;
                break;
            }
        }
        while(j<4 && continuer){
            if(tab[j] >= 5){
                couleur = true;
                continuer = false;
            }
            j++;
        }
        return couleur;
    }
    
    public String CouleurNom(){
        boolean continuer =true;
        boolean couleur = false;
        int j = 0;
        String s="";
        int indice = -1;
        int tab[] = {0,0,0,0};
        for(int i=0;i<this.tab.size();i++){
            switch(this.tab.get(i).getCouleur()){
                case "coeur":
                    tab[0]++;
                break;
                
                case "carreau":
                    tab[1]++;
                break;
                
                case "pique":
                    tab[2]++;
                break;
                
                case "treffle":
                    tab[3]++;
                break;
            }
        }
        while(j<4 && continuer){
            if(tab[j] >= 5){
                couleur = true;
                continuer = false;
                indice = j;
            }
            j++;
        }
        switch(indice){
                case 0:
                  s =  "coeur";
                break;
                
                case 1:
                  s = "carreau";
                break;
                
                case 2:
                    s = "pique";
                break;
                
                case 3:
                    s = "treffle";
                break;
                
                default:
                    s = "none";
                break;    
            }
        return s;
    }
    
    
    
    public boolean Paire(){
        boolean b = false;
        int cmpt = 0;
        Cartes p = this.tri();
        for(int i=0;i<p.getTaille()-1;i++){
            if(p.getCarte(i).getNombre() == p.getCarte(i+1).getNombre()){
                cmpt++;
            }
            else{
                cmpt = 0;
            }
            if(cmpt == 1){
                if((i+2)<p.getTaille()){
                    if(p.getCarte(i+2).getNombre() != p.getCarte(i).getNombre())
                        b = true;
                }
                else
                    b = true;
            }
        }
        return b;
    }
    
    public boolean Paire(int n){
        boolean b = false;
        int cmpt = 0;
        Cartes p = this.tri();
        for(int i=0;i<p.getTaille()-1;i++){
            if(p.getCarte(i).getNombre() == p.getCarte(i+1).getNombre() && p.getCarte(i).getNombre() != n){
                cmpt++;
            }
            else{
                cmpt = 0;
            }
            if(cmpt == 1){
                if((i+2)<p.getTaille()){
                    if(p.getCarte(i+2).getNombre() != p.getCarte(i).getNombre())
                        b = true;
                }
                else
                    b = true;
            }
        }
        return b;
    }
    
    
    public int PaireChiffre(){     
        boolean b = false;
        int cmpt = 0;
        int nombre = 0; 
        Cartes p = this.tri();
        for(int i=0;i<p.getTaille()-1;i++){
            if(p.getCarte(i).getNombre() == p.getCarte(i+1).getNombre()){
                cmpt++;
            }
            else{
                cmpt = 0;
            }
            if(cmpt == 1){
                if((i+2)<p.getTaille()){
                    if(p.getCarte(i+2).getNombre() != p.getCarte(i).getNombre())
                        nombre = p.getCarte(i).getNombre();
                }
                else
                nombre = p.getCarte(i).getNombre();
            }
        } 
        return nombre;
    }
    
    public int PaireChiffre(int n){    
        boolean b = false;
        int cmpt = 0;
        int nombre = 0; 
        Cartes p = this.tri();
        for(int i=0;i<p.getTaille()-1;i++){
            if(p.getCarte(i).getNombre() == p.getCarte(i+1).getNombre() && p.getCarte(i).getNombre() != n){
                cmpt++;
            }
            else{
                cmpt = 0;
            }
            if(cmpt == 1){
                if((i+2)<p.getTaille()){
                    if(p.getCarte(i+2).getNombre() != p.getCarte(i).getNombre())
                        nombre = p.getCarte(i).getNombre();
                }
                else
                nombre = p.getCarte(i).getNombre();
            }
        } 
        return nombre;
    }
    
    public boolean DoublePaire(){   
        boolean b = false;
        int nombre = -1;
        int nombre1 = -1;
        nombre = PaireChiffre();
        if(nombre != 0){
            b = Paire(nombre);
            return b;
        }
        else
            return false;
    }
    
    public int[] DoublePaireTab(){      
        boolean b = false;
        int nombre = 0;
        int nombre1 = 0;
        int [] tab = {0,0};
        nombre = PaireChiffre();
        tab[0] = nombre;
        if(nombre != 0){
            tab[1] = PaireChiffre(nombre);
            return tab;
        }
        else
            return tab;
    }
    
    
    
    public boolean Brelan(){
         boolean b = false;
        int cmpt = 0;
        Cartes p = this.tri();
        for(int i=0;i<p.getTaille()-1;i++){
            if(p.getCarte(i).getNombre() == p.getCarte(i+1).getNombre()){
                cmpt++;
            }
            else{
                cmpt = 0;
            }
            if(cmpt == 2){
                b = true;
            }
        }
        return b;
    }
    
    public boolean Brelan(int n){
         boolean b = false;
        int cmpt = 0;
        Cartes p = this.tri();
        for(int i=0;i<p.getTaille()-1;i++){
            if(p.getCarte(i).getNombre() == p.getCarte(i+1).getNombre() && p.getCarte(i).getNombre() != n){
                cmpt++;
            }
            else{
                cmpt = 0;
            }
            if(cmpt == 2){
                b = true;
            }
        }
        return b;
    }
    
    public int BrelanChiffre(){
        boolean b = false;
        int cmpt = 0;
        int nombre = 0; 
        Cartes p = this.tri();
        for(int i=0;i<p.getTaille()-1;i++){
            if(p.getCarte(i).getNombre() == p.getCarte(i+1).getNombre()){
                cmpt++;
            }
            else{
                cmpt = 0;
            }
            if(cmpt == 2){
                nombre = p.getCarte(i).getNombre();
            }
        }
        
        return nombre;
    }
    
    public int BrelanChiffre(int n){
        boolean b = false;
        int cmpt = 0;
        int nombre = 0; 
        Cartes p = this.tri();
        for(int i=0;i<p.getTaille()-1;i++){
            if(p.getCarte(i).getNombre() == p.getCarte(i+1).getNombre() && p.getCarte(i).getNombre() != n){
                cmpt++;
            }
            else{
                cmpt = 0;
            }
            if(cmpt == 2){
                nombre = p.getCarte(i).getNombre();
            }
        }
        
        return nombre;
    }
    
    
    public boolean Full(){
        int n = PaireChiffre();
        if(n != 0)
            return Brelan(n);
        else
            return false;
    }
    
    public int [] FullChiffre(){
        int n = PaireChiffre();
        int [] tab = {n,0};
        if(n != 0){
            tab[1] = BrelanChiffre(n);
            return tab;
        }
        else
            return tab;
    }
    
    
    
    
    public boolean Carré(){
        boolean b = false;
        int cmpt = 0;
        Cartes p = this.tri();
        for(int i=0;i<p.getTaille()-1;i++){
            if(p.getCarte(i).getNombre() == p.getCarte(i+1).getNombre()){
                cmpt++;
            }
            else{
                cmpt = 0;
            }
            if(cmpt == 3){
                b = true;
            }
        }
        return b;
    }
    
    public int CarréChiffre(){
        boolean b = false;
        int cmpt = 0;
        int nombre = 0; 
        Cartes p = this.tri();
        for(int i=0;i<p.getTaille()-1;i++){
            if(p.getCarte(i).getNombre() == p.getCarte(i+1).getNombre()){
                cmpt++;
            }
            else{
                cmpt = 0;
            }
            if(cmpt == 3){
                nombre = p.getCarte(i).getNombre();
            }
        }
        
        return nombre;
    }
    
    
    public boolean Suite(){
        Cartes p = this.copie();
        Cartes p1 = this.copie();
        p = p.tri();                                    //cmpt qui regarde si enchainement ininterrompu de chiffre
        p1 = p1.triSuiteAs();
        boolean b = false;
        int cmpt = 0;
        for(int i=p1.getTaille()-1;i>0;i--){                 
            if(!(p1.getCarte(i).getNombre() == (p1.getCarte(i-1).getNombre()+1))){
                if(!(p1.getCarte(i).getNombre() == (p1.getCarte(i-1).getNombre())))
                    cmpt = 0;
            }
            else{
                cmpt++;
                if(cmpt == 4)
                    b = true;
            }
        }
        if(!b){
            cmpt = 0;
            for(int i=p.getTaille()-1;i>0;i--){                 
            if(!(p.getCarte(i).getNombre() == (p.getCarte(i-1).getNombre()+1))){
                if(!(p1.getCarte(i).getNombre() == (p.getCarte(i-1).getNombre())))
                    cmpt = 0;
            }
                else{
                cmpt++;
                if(cmpt == 4)
                    b = true;
                }
            }
        }
        if(b)
            return true;
        else
            return false;
    }
    
    public boolean Suite(String s){
        Cartes p = this.copie();
        Cartes p1 = this.copie();
        p = p.tri();                                    //cmpt qui regarde si enchainement ininterrompu de chiffre
        p1 = p1.triSuiteAs();
        boolean b = false;
        int cmpt = 0;
        for(int i=p1.getTaille()-1;i>0;i--){                 
            if(!(p1.getCarte(i).getNombre() == (p1.getCarte(i-1).getNombre()+1))){
                if(!((p1.getCarte(i).getNombre() == (p1.getCarte(i-1).getNombre()+1)) && p1.getCarte(i).getCouleur() == s))
                    cmpt = 0;
            }
            else{
                cmpt++;
                if(cmpt == 4)
                    b = true;
            }
        }
        if(!b){
            cmpt = 0;
            for(int i=p.getTaille()-1;i>0;i--){                 
            if(!(p.getCarte(i).getNombre() == (p.getCarte(i-1).getNombre()+1))){
                if(!((p.getCarte(i).getNombre() == (p.getCarte(i-1).getNombre()+1)) && p.getCarte(i).getCouleur() == s))
                    cmpt = 0;
            }
                else{
                cmpt++;
                if(cmpt == 4)
                    b = true;
                }
            }
        }
        if(b)
            return true;
        else
            return false;
    }
    
    public Carte SuiteHaute(){
        Cartes p = this.copie();
        Cartes p1 = this.copie();
        p = p.tri();
        p1 = p1.triSuiteAs();
        Carte c = new Carte(0,"");
        boolean b = false;
        int cmpt = 0;
        for(int i=p1.getTaille()-1;i>0;i--){                 
            if(!((p1.getCarte(i).getNombre() == (p1.getCarte(i-1).getNombre()+1)))){
                if(!(p1.getCarte(i).getNombre() == (p1.getCarte(i-1).getNombre())))
                    cmpt = 0;
            }
            else{
                if(cmpt == 0 && !b)
                    c = p1.getCarte(i);
                cmpt++;
                if(cmpt == 4)
                    b = true;
            }
        }
        if(!b){
            cmpt = 0;
            for(int i=p.getTaille()-1;i>0;i--){                 
            if(!(p.getCarte(i).getNombre() == (p.getCarte(i-1).getNombre()+1))){
                if(!((p.getCarte(i).getNombre() == (p.getCarte(i-1).getNombre()+1))))
                    cmpt = 0;
            }
                else{
                    if(cmpt == 0 && !b)
                        c = p.getCarte(i);
                    cmpt++;
                    if(cmpt == 4)
                    b = true;
                }
            }
        }
        if(!b)
            c.setCouleur("");
        
        return c;
    }
    
    public Carte SuiteHaute(String s){
        //pour couleur
        Cartes p = this.copie();
        Cartes p1 = this.copie();
        p = p.tri();
        p1 = p1.triSuiteAs();
        Carte c = new Carte(0,"");
        boolean b = false;
        int cmpt = 0;
        for(int i=p1.getTaille()-1;i>0;i--){                 
            if(!((p1.getCarte(i).getNombre() == (p1.getCarte(i-1).getNombre()+1)) && p1.getCarte(i).getCouleur().equals(s) && p1.getCarte(i-1).getCouleur().equals(s))){
                if(!(p1.getCarte(i).getNombre() == p1.getCarte(i-1).getNombre()))
                    cmpt = 0;
            }
            else{
                if(cmpt == 0 && !b)
                    c = p1.getCarte(i);
                cmpt++;
                if(cmpt == 4)
                    b = true;
            }
        }
        if(!b){
            cmpt = 0;
            for(int i=p.getTaille()-1;i>0;i--){                 
            if(!((p.getCarte(i).getNombre() == (p.getCarte(i-1).getNombre()+1)) && p.getCarte(i).getCouleur().equals(s) && p.getCarte(i-1).getCouleur().equals(s))){
                if(!(p.getCarte(i).getNombre() == (p.getCarte(i-1).getNombre()+1)))
                    cmpt = 0;
            }
                else{
                    if(cmpt == 0 && !b)
                        c = p.getCarte(i);
                    cmpt++;
                    if(cmpt == 4)
                    b = true;
                }
            }
        }
        if(!b)
            c.setCouleur("");
        
        return c;
    }
    
    
    public boolean QuinteFlush(){
        String s = CouleurNom();
        Carte c;
        if(!s.equals("none")){
            c = SuiteHaute(s);
            if(!c.getCouleur().equals("")){
                return true;
            }
            else
                return false;
        }
        else
            return false;
    }
    
    public boolean QuinteFlushRoyal(){
        String s = CouleurNom();
        Carte c;
        if(!s.equals("none")){
            c = SuiteHaute(s);
            if(!c.getCouleur().equals("") && c.getNombre() == 14){
                return true;
            }
            else
                return false;
        }
        else
            return false;
    }
    
    
    
    public Cartes tri(){
        Cartes t1  = new Cartes();
        Cartes t2 = this.copie();
        Carte c;
        int min;
        int indice = 0;
        min = t2.getCarte(0).getNombre();
        while(t1.getTaille() < this.getTaille()){
            min = t2.getCarte(0).getNombre();
            for(int i=0;i<t2.getTaille();i++){
                if(min >= t2.getCarte(i).getNombre()){ 
                    min = t2.getCarte(i).getNombre();
                    indice = i;
                }
            }
            t1.addCarte(t2.remove(indice));
        }
        return t1;
    }
    
    public Cartes triSuiteAs(){
        Cartes t1  = new Cartes();
        Cartes t2 = this.copie();
        Carte c;
        int min;
        int indice = 0;
        //remplacement du 1 par la suite du roi soit 14
        for(int i=0;i<this.getTaille();i++){
            if(t2.getCarte(i).getNombre() == 1)
                t2.getCarte(i).setNombre(14);
        }
        
        min = t2.getCarte(0).getNombre();
        while(t1.getTaille() < this.getTaille()){
            min = t2.getCarte(0).getNombre();
            for(int i=0;i<t2.getTaille();i++){
                if(min >= t2.getCarte(i).getNombre()){
                    min = t2.getCarte(i).getNombre();
                    indice = i;
                }
            }
            t1.addCarte(t2.remove(indice));
        }
        return t1;
    }
    
}
