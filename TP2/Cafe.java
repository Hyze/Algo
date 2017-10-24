import java.util.*;

public class Cafe
{
	//Declaration des constantes
	  public static final int NBPERSGROUP=6;
	 public static final int DELAI_ARRIVEES = 15;
	 public static final int DELAI_COM_MINI = 2;
	 public static final int DELAI_COM_MAXI = 5;
	 public static final int DELAI_SERV_MINI = 5 ;
	 public static final int DELAI_SERV_MAXI = 10;
	 public static final int DELAI_DEP_MINI = 10;
	 public static final int DELAI_DEP_MAXI = 30 ;

	 public static final int STAT_ARRIVEE = 0 ;	// les statuts
	 public static final int STAT_COMMANDE = 1 ;
	 public static final int STAT_SERVICE = 2 ;
	 public static final int STAT_DEPART = 3 ;

	// variable d'instance	

	private int nbPlaces;
	private String nom;
	private FilePrio fp;


	//Constructeurs
	Cafe(String n, int nbPlaces)
	{
		this.nom=n;
		this.nbPlaces=nbPlaces;
	}

	//Methodes
	public int getNombrePlaces(){ return this.nbPlaces;}

	public void ouverture(int nbreGroupe)
	{
		//if(this.nbPlaces<)
	 fp= new FilePrio();
	int cpt=1;
	int random=aleatoire(1,DELAI_ARRIVEES);

	for(int i=1;i<nbreGroupe+1;i++)
	{
		Groupe g= new Groupe(i,aleatoire(1,NBPERSGROUP),STAT_ARRIVEE,random);
		fp.add(g);
	;
		random=g.getPrio()+aleatoire(1,DELAI_ARRIVEES);
	}



	}


	public void gestion()	// programmation de l'automate
	{

		int temps=0;
		while(!fp.isEmpty())
		{
			if (temps==fp.getPrioTete())
			{
				Groupe tmp=fp.get();
					switch (tmp.getStatut())
					{
						case 0 :   // ARRIVEE //

							if(tmp.getNbPers()<=nbPlaces)    // test du nombre de place a l'arrivée du groupe
							{
								nbPlaces = nbPlaces - tmp.getNbPers();
								System.out.println("on installe le groupe N° " + tmp.getNumero());
								tmp.setStatut(STAT_COMMANDE);
								tmp.setPrio(temps + aleatoire(1, DELAI_ARRIVEES));
								fp.add(tmp);
								System.out.println("il reste " + nbPlaces + " place dans le cafe");
								System.out.println("--------------------------------------------"); //je trouve ca plus lisible
							}
							else{
								System.out.println("Il n'y  a plus de place ");
							}
							break;
						case 1 :  // COMMANDE //
							System.out.println("Commande du groupe n°"+tmp.getNumero()+" pour "+tmp.getNbPers()+" personnes");
							tmp.setStatut(STAT_SERVICE);
							tmp.setPrio(temps+aleatoire(1,DELAI_ARRIVEES));
							fp.add(tmp);
							System.out.println("-------------------------------");

							break;
						case 2 :	// SERVICE //
							System.out.println("Service du groupe n° "+tmp.getNumero()+ " pour "+tmp.getNbPers());
							tmp.setStatut(STAT_DEPART);
							tmp.setPrio(temps+aleatoire(1,DELAI_ARRIVEES));
							fp.add(tmp);
							System.out.println("------------------------------------");
							break;
						case 3 :
							System.out.println("Depart du groupe n° "+tmp.getNumero()+" pour "+tmp.getNbPers());
							nbPlaces=nbPlaces+tmp.getNbPers();
							System.out.println("------------------------------------");
							break;
					}


			}


			temps++;

		}
		System.out.println("Le bar est fermer");

	
			
	}

	public void afficherEvts()
	{
		fp.afficher();
	}
	
	
		
	private int aleatoire(int min, int max)
	{
		return  min +(int)(Math.random()*(max - min + 1));
	}


}

