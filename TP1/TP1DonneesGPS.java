/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp1donneesgps;

import java.io.IOException;


public class TP1DonneesGPS {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
        Point p1 = new Point(23,1,12,0);
         Point p2 = new Point(12,1,12,5);
         System.out.println(p1.distance(p1));
         Parcours desPoints=new Parcours();
         
         
    }
    
}
