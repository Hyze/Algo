
package poker_partie;

/**
 *
 * @author JppcestChaudSaMere
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.*; // Pour pouvoir utiliser les fichiers
import static javax.swing.JFrame.EXIT_ON_CLOSE;
   

public class Fenetre extends JFrame implements ActionListener 
{
   // 3 panneaux constituant la fen�tre    
   private JPanel table;            
   private JPanel p1;                    // zone boutons hauts
   private JPanel p2;                    // zone boutons bas  
    JFrame frame = new JFrame();	
   //-------------------------------
   private int nbIA;
   private int RiviereNum;
   private int JoueurNum;
   //-------------------------------
   
    
// CONSTRUCTEUR 
    
    public Fenetre(String titre, int largeur, int hauteur) 
    {
        super();
        RiviereNum=1;
        JoueurNum=1;
        mise_en_page( largeur, hauteur );   
    }
    

// ASSEMBLAGE PARTIES FENETRE : la fenetre est constitu�e de trois parties Panel Nord : boutons ; Sud : boutons; Centre: zone de zoneDessin
    public void mise_en_page(int maxX, int maxY) 
    {   
        setTitle("Poker");
        setSize(maxX,maxY);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // insertion boutons du haut
        this.p1 = new JPanel(new GridLayout());
  	getContentPane().add(p1,"North");
        //--------------------------------------------------------------------
        // insertion boutons du bas
        this.p2 = new JPanel(new GridLayout());
        getContentPane().add(p2,"South");
        //--------------------------------------------------------------------
        
        //CREATION DE L'ESPACE DU MILIEU , DONC LA PARTIE QUOI
        
        this.table = new JPanel();
        this.table.setSize(maxX,maxY);                                  
        this.table.setPreferredSize(new Dimension(maxX,maxY));
        getContentPane().add(this.table,"Center");
        
        // Ajout des boutons : 
        /*
        ajouteBouton("Ajouter une IA", p1);
        ajouteBouton("Retirer une IA",p1);
        */
  	ajouteBouton("Quitter", p2);
        
        pack();
        setVisible(true);
    }

    public void paint(Graphics g)  // dessin de la fen�tre g�n�rale
    {
        
         this.p1.repaint();  // on redessine les boutons hauts
         this.p2.repaint();  // on redessine les boutons bas
         this.table.repaint();
         
         
         // on redessine le fond MAIS CA MARCHE PAS 
         
         /*
         try {
                Image img = ImageIO.read(new File("../../../cartes/decord_bis.jpg"));
                g.drawImage(img, 0, 0, 1280, 720, this);
                
         } catch (IOException e) {
                e.printStackTrace();
            }      
*/
    }

    void quitter() {
        System.exit(0);
    }
    
    
    public void ajoutCarteJoueur(Carte carte){
        int x=0;
        int y=0;
        String nb = Integer.toString(carte.getNombre());
        String coul= carte.getCouleur();
        String chemin = "../../../cartes/"+nb+"_"+coul+".png";
        
        if(JoueurNum==1){
            x=680;
            y=500;
            JoueurNum++;
        }else if(JoueurNum == 2){
            x=680+71+1;
            y=500;
        }
        
         //      Pour afficher draw l'image
         try {
            Image img = ImageIO.read(new File(chemin));
            g.drawImage(img, x, y, this);
         } catch (IOException e) {
            e.printStackTrace();
        }  
    }
    public void ajoutCarteRiviere(Carte carte){
        
        int x=0;
        int y=0;
        String nb = Integer.toString(carte.getNombre());
        String coul= carte.getCouleur();
        String chemin = "../../../cartes/"+nb+"_"+coul+".png";
        
        if(RiviereNum==1){
            x=460;
            y=273;
            RiviereNum++;
        }else if(RiviereNum==2){
            x=460+71+1;
            y=273;
            RiviereNum++;
        }else if(RiviereNum==3){
            x=460+71+71+2;
            y=273;
            RiviereNum++;
        }else if(RiviereNum==4){
            x=460+71+71+71+3;
            y=273;
            RiviereNum++;
        }else if(RiviereNum==5){
            x=460+71+71+71+71+4;
            y=273;
            RiviereNum++;
        }
        
         //      Pour afficher draw l'image
       try {
            Image img = ImageIO.read(new File(chemin));
            g.drawImage(img, x, y, this);
         } catch (IOException e) {
            e.printStackTrace();
        }  
       repaint();
       
    }
    
    public void FinPartie(){
    
        // Ecrire Gagné ou Perdu en GRos ?
        
        
}
    
    
    private void ajouteBouton(String label, JPanel p) {
        // Ajoute un bouton, avec le texte label, au panneau p
        JButton b = new JButton(label);
        p.add(b);
        b.addActionListener(this);
    } 
    
    public void actionPerformed(ActionEvent e) 
    {
        String arg = e.getActionCommand();  
         /* 		
        if(arg.equals("Ajouter une IA")){
            if(nbIA<4){
            nbIA++;
            }
        }
	if(arg.equals("Retirer une IA")){
            if (nbIA>1){
               nbIA--; 
            }
              
        }
        */
        if (arg.equals("Quitter"))
        {
            quitter();
        }
 							
   	   
            
         
    repaint();
    }
}


