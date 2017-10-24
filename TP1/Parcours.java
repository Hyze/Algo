package tp1donneesgps;

import tp1donneesgps.Point;
import java.util.ArrayList;
import java.io.*;


public class Parcours
{
   //-STRUCTURE DE DONNEES "points"-------------------------------------------------------------------
   private ArrayList <Point> points;
  
   
   //-CONSTRUCTEUR------------------------------------------------------------------------------------
public Parcours()
{
    this.points=new ArrayList<Point>() ;
  
}
   

   //-METHODES---------------------------------------------------------------------------------------

    /**
     *
     * @throws IOException
     * Methode qui permet le chargement des points a partir d'un fichier DonneesGPS.csv
     */

   public void chargement()throws IOException
   {
   
      double lat, lon, alt, temps;
                  
      FileReader fic = new FileReader("DonneesGPS.csv");
	   StreamTokenizer entree=new StreamTokenizer(fic) ;
      
      entree.quoteChar('"');
   	entree.nextToken();
   	int i =0;
   	while (entree.ttype!=StreamTokenizer.TT_EOF)
   	{
   		lat=entree.nval ;                            // lecture 4 par 4 des donn�es lat , lon, alt et temps
         
         entree.nextToken();
         lon=entree.nval ;
   		
         entree.nextToken();
         alt=entree.nval ;
         
         entree.nextToken();
         temps=entree.nval ;
         
         Point p = new Point( lat, lon, alt, temps ); // creation du point avec les donn�es
         points.add( p );                            // ajout du point au tableau nomm� "points"
         
         entree.nextToken();                          // et on recommence...
   	}
      
      fic.close();
   }
   
   public void afficher()
   {
       for(int i=0;i<this.points.size();i++)
       {
           System.out.println(this.points.get(i));
       }
   }

   public double altitudeMax()
   {
       double max=0;
       for(int i=0;i<this.points.size();i++)
       {
           if(this.points.get(i).getLat()>max)
           {
               max=this.points.get(i).getAlt();
           }
       }
      return max;
   }

   public double distance()
   {
       
       double somme=0;
        for(int i=0;i<this.points.size()-1;i++)
        {
            somme+=this.points.get(i).distance(this.points.get(i+1));
        }
      return somme;
    }

   public double temps()
   {
     double temp=0.;
        for(int i=0;i<this.points.size();i++)
        {
           temp+=this.points.get(i).getTemps();
        }
      return temp;
   }

   public void afficherVitesses()
   {
        
      double distance=0;
      double temps=0;
      double vitesse=0;
        for(int i=0;i<this.points.size()-1;i++)
        {
           distance=this.points.get(i).distance(this.points.get(i+1));
           temps=this.points.get(i+1).getTemps()-this.points.get(i).getTemps();
           vitesse=(distance/temps);
           System.out.println("La vitesse entre le points"+i+"et le point "+(i+1)+" est de : "+(vitesse*3.6)+" km/h");
        }
       
   }
   
   public double vitesseMoy()
   {
        return this.distance()/this.temps();
   }

    public ArrayList <Double> split( double intervalleDist )
   {
        ArrayList<Double> Resultat= new ArrayList();
        double distance=0;
        double temps=0;
        double vitesse;
        for(int i=0;i<points.size();i++)
        {
            temps+=this.points.get(i).getTemps();
            distance+=this.points.get(i).distance(this.points.get(i+1));
                while(distance>intervalleDist)
                {
                    vitesse=distance/temps;
                    Resultat.add(vitesse);
                    distance=0;
                    temps=0;
                }
        }
   return Resultat;
   }
   public double Ecart(Point p1, Point p2)
   {
       double ecart=0;
       
       return ecart;
   }
    public void afficherLesMax()
    {
        for(int i=0;i<this.points.size()-2;i++)
        {
            
        }
    }

   public void tracerAltitude()
   {
      
           
   }

   public void tracerVitesse()
   {
      
   }



}