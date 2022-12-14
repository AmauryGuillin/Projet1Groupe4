package fr.isika.cda22.projet1_Groupe4;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Noeud implements Constantes {

	// Attributs
	private int indexNoeud = 0;
	private Stagiaire cle;
	private int indexfilsGauche = -1;
	private int indexfilsDroit = -1;
	private int indexdoublon = -1;

	// Constructeur
	public Noeud(int indexNoeud, Stagiaire cle, int indexFilsGauche, int indexFilsDroit, int indexDoublon) {
		super();
		this.indexNoeud = indexNoeud;
		this.cle = cle;
		this.indexfilsGauche = indexFilsGauche;
		this.indexfilsDroit = indexFilsDroit;
		this.indexdoublon = indexDoublon;
	}
	
	// Constructeur vide
	public Noeud() {	
	}
	
	public Noeud(Stagiaire cle) {
		super();
		this.indexNoeud = -1;
		this.cle = cle;
		this.indexfilsGauche = -1;
		this.indexfilsDroit = -1;
		this.indexdoublon = -1;
	}
	
	@Override
	public String toString() {
		return "Index [" + indexNoeud + "], nom = " + cle.getNom().trim() + " prenom  = " + cle.getPrenom().trim() + " departement = " + cle.getDepartement().trim() + " promotion = " + cle.getNomPromo().trim()
				+ " date de formation = " + cle.getDateFormation().trim() + ", indexfilsGauche = " + indexfilsGauche
				+ ", indexfilsDroit = " + indexfilsDroit + ", indexdoublon = " + indexdoublon + "]\n";
	}


	/*
	 * Cette methode permet d'écrire le Noeud d'un Stagiaire dans le fichier binaire
	 * :
	 * 
	 * Si le fichier binaire est vide alors on doit écrire la racine S'il ne l'est
	 * pas, alors on doit ajouter à la racine un nouveau Noeud. Dans ce cas, on doit
	 * en premier lieu replacer le curseur à la fin de la racine
	 */
	public void ajouterStagiaireBinaire(Noeud noeudAjout, int compteur, RandomAccessFile raf) {

		try {
			if (raf.length() == 0) {
				ecrireNoeudBin(noeudAjout, compteur, raf);
			} else {
				raf.seek(TAILLE_NOEUD_OCTET);
				ecritureBinReccursive(noeudAjout, compteur, raf, 0);
			}

		} catch (IOException e) {
			System.out.println("ERREUR - FICHIER INTROUVALE");
			e.printStackTrace();
		}

	}

	/*
	 * Methode qui permet de retourner une ArrayList contenant un noeud spécifique suite à une recherche multicritère
	 */
	public ArrayList<Noeud> rechercherUnStagiaire(String nom, String prenom, String departement, String nomPromo, String dateFormation, RandomAccessFile raf) {
		
		ArrayList<Noeud> tableauRecherche = new ArrayList<>();

		try {
			if (raf.length() == 0) {
				return tableauRecherche;
			} else {
				raf.seek(0);
				toArrayRechercheStagiaire(nom, prenom, departement, nomPromo, dateFormation, 0, 0, raf, tableauRecherche);
			}

		} catch (IOException e) {
			System.out.println("ERREUR - FICHIER INTROUVALE");
			e.printStackTrace();
		}

		return tableauRecherche;
	}
	

	/*
	 * Cette methode permet d'ajouter un nouveau Noeud à la Racine
	 * 
	 * On calcul l'index du noeud en cours puis l'index du noeud parent. Si le nom
	 * du stagiaire est alphabétiquement inférieur à celui du parent Alors si
	 * l'index du fils gauche est différent de -1 Alors on recupère l'index du noeud
	 * parent puis on relance la methode sur le fils gauche. SINON si l'index du
	 * fils gauche est égal -1 Alors on écrit l'index du fils gauche puis on écrit
	 * le nouveau noeud en fin de fichier. Les autres conditions traitent le sujet
	 * de la même façon, pour le fils droit et pour le doublon
	 */
	public void ecritureBinReccursive(Noeud noeudAjout, int compteur, RandomAccessFile raf, int index) {

		try {
			int indexNoeud = (int) (raf.length() / TAILLE_NOEUD_OCTET);
			int indexParent = index;
			if (noeudAjout.getCle().getNomLong().compareTo(recupereNomIndex(indexParent, raf)) < 0) {
				if (recupererIndexFilsGauche(indexParent, raf) != -1) { // il y a un fils gauche
					indexParent = recupererIndexFilsGauche(indexParent, raf);
					ecritureBinReccursive(noeudAjout, compteur, raf, indexParent); // on passe la methode au fils gauche
				} else { // il n'y a pas de fils gauche
					ecrireFilsGauche(indexParent, raf, indexNoeud);
					ecrireNoeudBin(noeudAjout, compteur, raf);
				}
			} else if (noeudAjout.getCle().getNomLong().compareTo(recupereNomIndex(indexParent, raf)) > 0) {
				if (recupererIndexFilsDroit(indexParent, raf) != -1) { // il y a un fils Droit
					indexParent = recupererIndexFilsDroit(indexParent, raf);
					ecritureBinReccursive(noeudAjout, compteur, raf, indexParent); // on passe la methode au fils Droit
				} else { // il n'y a pas de fils Droit
					ecrireFilsDroit(indexParent, raf, indexNoeud);
					ecrireNoeudBin(noeudAjout, compteur, raf);
				}
			} else if (noeudAjout.getCle().getNomLong().compareTo(recupereNomIndex(indexParent, raf)) == 0) {
				if (recupererIndexDoublon(indexParent, raf) != -1) { // Il y a un doublon
					indexParent = recupererIndexDoublon(indexParent, raf);
					ecritureBinReccursive(noeudAjout, compteur, raf, indexParent); // On passe la methode au doublon
				} else { // Il n'y a pas de doublon
					ecrireDoublon(indexParent, raf, indexNoeud);
					ecrireNoeudBin(noeudAjout, compteur, raf);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	//Methode qui retourne une arraylist contenant tous les stagiaires
	public ArrayList<Stagiaire> toArray(RandomAccessFile raf) {
		ArrayList<Stagiaire> tableau = new ArrayList<>();

		try {
			if (raf.length() == 0) {
				return tableau;
			} else {
				toArray(0, 0, raf, tableau);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return tableau;
	}
	
	
	//Methode qui permet de parcourir le fichier binaire et de ranger chaque stagiaire
	//dans une arraylist, par ordre alphabétique (critère : Noms)
	public void toArray(int indexP, int indexEC, RandomAccessFile raf, ArrayList<Stagiaire> tableau) {

		int indexParent = indexP;
		int indexEnCours = indexEC;
		int indexEnCoursLocal = indexEnCours;

		// Methode de parcours "infixe" GN D D

		// Gauche

		if (recupererIndexFilsGauche(indexEnCours, raf) != -1) {
			indexParent = indexEnCours;
			indexEnCours = recupererIndexFilsGauche(indexEnCours, raf);
			toArray(indexParent, indexEnCours, raf, tableau);
		}

		// Noeud
		tableau.add(recupererStagiaireIndex(indexEnCoursLocal, raf));
		
//		System.out.println(recupererStagiaireIndex(indexEnCoursLocal, raf));
		indexEnCours = indexEnCoursLocal;

		// Doublon
		if (recupererIndexDoublon(indexEnCours, raf) != -1) {
			toArrayDoublon(recupererIndexDoublon(indexEnCoursLocal, raf), raf, tableau);

		}

		// Droite
		if (recupererIndexFilsDroit(indexEnCours, raf) != -1) {
			indexParent = indexEnCours;
			indexEnCours = recupererIndexFilsDroit(indexEnCours, raf);
			toArray(indexParent, indexEnCours, raf, tableau);
		}

	}
	
	//Methode complémentaire de la méthode toArray consacrée à la recherche des doublons
	public void toArrayDoublon(int indexPremierDoublon, RandomAccessFile raf, ArrayList<Stagiaire> tableau) {

		int indexParcours = indexPremierDoublon;

		while (recupererIndexDoublon(indexParcours, raf) != -1) {
			tableau.add(recupererStagiaireIndex(indexParcours, raf));
			indexParcours = recupererIndexDoublon(indexParcours, raf);
		}
		tableau.add(recupererStagiaireIndex(indexParcours, raf));
	}
	
	/*
	 * Methode qui permet de rechercher un stagiaire précis dans le fichier binaire
	 * 
	 * On vérifie qu'il y ait au moins 1 critère de recherche utilisé
	 * Si oui,
	 * On parcours l'arbre binaire de façon "infixe" (Gauche, Noeud, Doublon, Droite)
	 * En fonction du nombre de critère de recherche utilisé en argument, la methode une ArrayList
	 * contenant plus ou moins de noeud.
	 * Si la recherche est utilisé à son plein potentiel, alors un seul noeud doit être contenu
	 * dans la liste en fin d'execution.
	 * 
	 */
	public void toArrayRechercheStagiaire(String nom, String prenom, String departement, String nomPromo, String dateFormation, 
			int indexP, int indexEC, RandomAccessFile raf, ArrayList<Noeud> tableauRecherche) {

		int indexParent = indexP;
		int indexEnCours = indexEC;
		int indexEnCoursLocal = indexEnCours;

		// Methode de parcours "infixe" GN D D

		// Gauche
		
		if (nom.equals("") && prenom.equals("") && departement.equals("") && nomPromo.equals("") && dateFormation.equals("")) {
			System.out.println("/!\\--------------ERREUR-------------- /!\\\n");
			System.out.println("VOUS N'AVEZ ENTRÉ AUCUN CRITERE DE RECHERCHE\n");
			System.out.println("/!\\--------------ERREUR-------------- /!\\\n");
			return;
		}

		if (recupererIndexFilsGauche(indexEnCours, raf) != -1) {
			indexParent = indexEnCours;
			indexEnCours = recupererIndexFilsGauche(indexEnCours, raf);
			toArrayRechercheStagiaire(nom, prenom, departement, nomPromo, dateFormation, indexParent, indexEnCours, raf, tableauRecherche);
		}

		// Noeud
		if ((recupererStagiaireIndex(indexEnCoursLocal, raf).getNomLong().contains(nom)) 
				&& (recupererStagiaireIndex(indexEnCoursLocal, raf).getPrenomLong().contains(prenom))
				&& (recupererStagiaireIndex(indexEnCoursLocal, raf).getDepartementLong().contains(departement))
				&& (recupererStagiaireIndex(indexEnCoursLocal, raf).getNomPromoLong().contains(nomPromo))
				&& (recupererStagiaireIndex(indexEnCoursLocal, raf).getDateFormationLong().contains(dateFormation))){
				
				Noeud noeudRecup = new Noeud();
				tableauRecherche.add(recupererNoeud(noeudRecup, indexEnCoursLocal, raf));
				
				
				
			}
		
		indexEnCours = indexEnCoursLocal;

		// Doublon
		if (recupererIndexDoublon(indexEnCours, raf) != -1) {
			toArrayDoublonRecherche(nom, prenom, departement, nomPromo, dateFormation, recupererIndexDoublon(indexEnCoursLocal, raf), raf, tableauRecherche);

		}

		// Droite
		if (recupererIndexFilsDroit(indexEnCours, raf) != -1) {
			indexParent = indexEnCours;
			indexEnCours = recupererIndexFilsDroit(indexEnCours, raf);
			toArrayRechercheStagiaire(nom, prenom, departement, nomPromo, dateFormation, indexParent, indexEnCours, raf, tableauRecherche);
		}

	}
	
	//Methode complémentaire à la méthode de recherche multicitère, consacrée à la recherche des doublons
	public void toArrayDoublonRecherche(String nom, String prenom, String departement, String nomPromo, String dateFormation, 
			int indexPremierDoublon, RandomAccessFile raf, ArrayList<Noeud> tableauRecherche) {

		int indexParcours = indexPremierDoublon;
		Noeud noeudRecup = new Noeud();
		
		if ((recupererStagiaireIndex(indexParcours, raf).getNomLong().contains(nom)) 
				&& (recupererStagiaireIndex(indexParcours, raf).getPrenomLong().contains(prenom)) 
				&& (recupererStagiaireIndex(indexParcours, raf).getDepartementLong().contains(departement))
				&& (recupererStagiaireIndex(indexParcours, raf).getNomPromoLong().contains(nomPromo))
				&& (recupererStagiaireIndex(indexParcours, raf).getDateFormationLong().contains(dateFormation))){
			
			while (recupererIndexDoublon(indexParcours, raf) != -1) {
				tableauRecherche.add(recupererNoeud(noeudRecup, indexParcours, raf));
				indexParcours = recupererIndexDoublon(indexParcours, raf);
			}
			tableauRecherche.add(recupererNoeud(noeudRecup, indexParcours, raf));
		}	
	}
	
	/*
	 * Methode qui permet de supprimer un stagiaire en fonction de son index
	 * 
	 * On recupère le noeud à supprimer puis on regarde quel est la disposition
	 * de ses fils droit/gauche, s'il en a.
	 * En fonction de cette observation, on va rentrer dans une des méthode spécifique
	 * pour chaque cas.
	 * 
	 */
	public void supprimerStagiaireNoeud(int indexStagiaireSupp) {
		try {

			int indexRacine = 0;
			int indexSansFils = -1;
			
			RandomAccessFile raf = new RandomAccessFile(CHEMIN_BIN, "rw");
			
			Noeud noeudSupp = new Noeud();
			noeudSupp = noeudSupp.recupererNoeudSupp(indexStagiaireSupp, raf);
//			System.out.println(noeudSupp);

			//Si c'est la racine
			if(noeudSupp.getIndexNoeud()==indexRacine) { 
				supprimerStagiaireRacine(noeudSupp, raf);
			}else {
				//Si le noeud à un doublon
				if(noeudSupp.getIndexDoublon()!=indexSansFils) {
					modifierNoeudAvecDoublon(noeudSupp, raf);
				}else {
					
			//Si le noeud à supprimer n'a pas de fils 
			if(noeudSupp.getIndexfilsGauche()==indexSansFils && noeudSupp.getIndexfilsDroit()==indexSansFils) {
				modifierNoeudParentSansFils(noeudSupp, raf);
			//Si le noeud à supprimer à 2 fils mm méthode que pour la racine 
			}else if (noeudSupp.getIndexfilsGauche()!=indexSansFils && noeudSupp.getIndexfilsDroit()!=indexSansFils) {
				supprimerStagiaireRacine(noeudSupp, raf);
			//Si le noeud à supprimer a un fils droit
			}else if (noeudSupp.getIndexfilsGauche()==indexSansFils) {
				modifierNoeudAvecFilsDroit(noeudSupp, raf);
			//Si le noeud à supprimer a un fils gauche
			}else if (noeudSupp.getIndexfilsDroit()==indexSansFils) {
				modifierNoeudAvecFilsGauche(noeudSupp, raf);
			}
				}	
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/*
	 * Methode qui permet de supprimer la racine de l'arbre
	 * (ou la racine d'un sous arbre)
	 * 
	 * On recupère le noeud successeur
	 * On écrit à la place du noeud à supprimer le stagiaire successeur
	 * On met à jour les index fils gauche/droit du nouveau noeud racine
	 * On supprime le noeud remplaçant de sa position initiale
	 */
	private void supprimerStagiaireRacine (Noeud noeudSupp,RandomAccessFile raf) {
		int indexSansFils = -1;
		int indexNoeudRemplacant = 0;
		
		int indexNoeudSupp = noeudSupp.getIndexNoeud();
		int indexNoeudSuppRecursif = noeudSupp.getIndexNoeud();

		int indexFDNoeudSupp = noeudSupp.getIndexfilsDroit();

		Noeud noeudRecup = new Noeud();
		noeudRecup = noeudRecup.recupererNoeud(noeudRecup, indexFDNoeudSupp, raf); 

		// chercher l'index du Noeud remplacant le noeud supp
		if (noeudRecup.getIndexfilsGauche() != indexSansFils) {
			indexNoeudSuppRecursif = chercherFilsPlusPetit(noeudRecup, raf, indexNoeudSuppRecursif);
		}
		indexNoeudRemplacant = noeudRecup.getIndexNoeud();
		
		//Récupérer le noeud remplacant en noeud
		Noeud noeudRemplacant = new Noeud();
		noeudRemplacant = noeudRemplacant.recupererNoeud(noeudRemplacant, indexNoeudRemplacant, raf);
		
		//Récupérer le stagiaire du noeud supp
		Stagiaire stgRecup = new Stagiaire();
		stgRecup = lireNoeudStagiaire(indexNoeudRemplacant, raf);
		
		//Ecriture dans le fichier .Bin du stagiaire 
		ecrireNoeudStagiaire(stgRecup, raf, indexNoeudSupp);
		
		//Supprission du stagiaire remplacant dans son ancienne place
		supprimerStagiaireNoeud(indexNoeudRemplacant);	
		
	}
	
	//Methode qui permet d'écrire un stagiaire dans le fichier binaire
	private void ecrireNoeudStagiaire(Stagiaire stgRecup, RandomAccessFile raf, int indexNoeudSupp) {

		try {
			raf.seek((indexNoeudSupp * TAILLE_NOEUD_OCTET)+4);

			raf.writeChars(stgRecup.getNomLong());
			raf.writeChars(stgRecup.getPrenomLong());
			raf.writeChars(stgRecup.getDepartementLong());
			raf.writeChars(stgRecup.getNomPromoLong());
			raf.writeChars(stgRecup.getDateFormationLong());
	

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//Methode qui permet de lire un Noeud dans le fichier binaire
	private Stagiaire lireNoeudStagiaire(int indexNoeudRemplacant, RandomAccessFile raf) {

		Stagiaire stgRecup = new Stagiaire();
		
			try {
				raf.seek((indexNoeudRemplacant * TAILLE_NOEUD_OCTET));

				int indexNoeudSupp = 0;
				String nomRecup = "";
				String prenomRecup = "";
				String departementRecup = "";
				String formationRecup = "";
				String anneeFormationRecup = "";

				indexNoeudSupp = raf.readInt();

				for (int k = 0; k < TAILLE_MAX_NOM; k++) {
					nomRecup += raf.readChar();
				}

				for (int k = 0; k < TAILLE_MAX_PRENOM; k++) {
					prenomRecup += raf.readChar();
				}

				for (int k = 0; k < TAILLE_MAX_DEPARTEMENT; k++) {
					departementRecup += raf.readChar();
				}

				for (int k = 0; k < TAILLE_MAX_NOM_PROMO; k++) {
					formationRecup += raf.readChar();
				}

				for (int k = 0; k < TAILLE_MAX_DATE_FORMATION; k++) {
					anneeFormationRecup += raf.readChar();
				}

				// Création Stagiaire qu'on récupère
				stgRecup = new Stagiaire(nomRecup, prenomRecup, departementRecup, formationRecup, anneeFormationRecup);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return stgRecup ;
		}
	
	//Methode qui permet de récupérer le noeud à supprimer
	private Noeud recupererNoeudSupp(int indexStagiaireSupp, RandomAccessFile raf) {

		Noeud noeudSupp = new Noeud();

		// Création noeud avec lecture .Bin
		try {
			raf.seek(indexStagiaireSupp * TAILLE_NOEUD_OCTET);

			int indexNoeudSupp = 0;
			String nomRecupSupp = "";
			String prenomRecupSupp = "";
			String departementRecupSupp = "";
			String formationRecupSupp = "";
			String anneeFormationRecupSupp = "";
			int indexFilsGaucheSupp = 0;
			int indexFilsDroitSupp = 0;
			int indexDoublonSupp = 0;

			indexNoeudSupp = raf.readInt();

			for (int k = 0; k < TAILLE_MAX_NOM; k++) {
				nomRecupSupp += raf.readChar();
			}

			for (int k = 0; k < TAILLE_MAX_PRENOM; k++) {
				prenomRecupSupp += raf.readChar();
			}

			for (int k = 0; k < TAILLE_MAX_DEPARTEMENT; k++) {
				departementRecupSupp += raf.readChar();
			}

			for (int k = 0; k < TAILLE_MAX_NOM_PROMO; k++) {
				formationRecupSupp += raf.readChar();
			}

			for (int k = 0; k < TAILLE_MAX_DATE_FORMATION; k++) {
				anneeFormationRecupSupp += raf.readChar();
			}

			indexFilsGaucheSupp += raf.readInt();
			indexFilsDroitSupp += raf.readInt();
			indexDoublonSupp += raf.readInt();

			// Création noeud
			noeudSupp = new Noeud(indexNoeudSupp, null, indexFilsGaucheSupp, indexFilsDroitSupp, indexDoublonSupp);

			// POUR VERIF DE RECUPERATION DU BON NOEUD

//			System.out.println("IndexRecupSupp = " + indexNoeudSupp);
//			System.out.println("nomRecupSupp = " + nomRecupSupp.trim());
//			System.out.println("prenomRecupSupp = " + prenomRecupSupp.trim());
//			System.out.println("departementRecupSupp = " + departementRecupSupp.trim());
//			System.out.println("formationRecupSupp = " + formationRecupSupp.trim());
//			System.out.println("anneeFormationRecupSupp = " + anneeFormationRecupSupp.trim());
//			System.out.println("indexFilsGauchSupp = " + indexFilsGaucheSupp);
//			System.out.println("indexFilsDroitSupp = " + indexFilsDroitSupp);
//			System.out.println("doublonSupp = " + indexDoublonSupp + "\n");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return noeudSupp;
	}
	
	// Methode qui permet de modifier un noeud Parent qui ne contient aucun fils droit/gauche
	private void modifierNoeudParentSansFils(Noeud noeudSupp, RandomAccessFile raf) {
		int indexNoeudSansFils = -1;

		// Chercher le noeud parent du noeud à supprimer
		int indexNoeudSupp = noeudSupp.getIndexNoeud();
		int indexFGNoeudSupp = noeudSupp.getIndexfilsGauche();
		int indexFDNoeudSupp = noeudSupp.getIndexfilsDroit();
		int indexDbNoeudSupp = noeudSupp.getIndexDoublon();

		int indexParent = 0;

		while ((indexNoeudSupp != recupererIndexFilsGauche(indexParent, raf)
				&& (indexNoeudSupp != recupererIndexFilsDroit(indexParent, raf))
				&& (indexNoeudSupp != recupererIndexDoublon(indexParent, raf)))) {
			indexParent++;
		}

		// Modif des index fils du parent
		if (indexNoeudSupp == recupererIndexFilsGauche(indexParent, raf)) {
			ecrireFilsGauche(indexParent, raf, indexNoeudSansFils);
		} else if (indexNoeudSupp == recupererIndexFilsDroit(indexParent, raf)) {
			ecrireFilsDroit(indexParent, raf, indexNoeudSansFils);
		} else {
			ecrireDoublon(indexParent, raf, indexNoeudSansFils);
		}

		// Modif des index fils du noeud Supp
		ecrireFilsGauche(indexNoeudSupp, raf, indexNoeudSansFils);
		ecrireFilsDroit(indexNoeudSupp, raf, indexNoeudSansFils);
		ecrireDoublon(indexNoeudSupp, raf, indexNoeudSansFils);

	}
	
	// Méthode pour supprimer un noeud qui a un doublon
	private void modifierNoeudAvecDoublon(Noeud noeudSupp, RandomAccessFile raf) {
		int indexNoeudSansFils = -1;

		int indexNoeudSupp = noeudSupp.getIndexNoeud();// 23
		int indexNoeudParent = 0;

		int indexFGnoeudSupp = noeudSupp.getIndexfilsGauche();// 58
		int indexFDnoeudSupp = noeudSupp.getIndexfilsDroit();// -1
		int indexDbNoeudSupp = noeudSupp.getIndexDoublon();// 28
		int indexNoeudRemplancant = indexDbNoeudSupp;

		// Chercher le parents
		indexNoeudParent = recupererIndexParent(indexNoeudSupp, raf, indexNoeudParent);// 21

		Noeud noeudParent = new Noeud();
		noeudParent = noeudParent.recupererNoeud(noeudParent, indexNoeudParent, raf);

		System.out.println("\nnoeud recup : " + noeudParent);

		indexNoeudParent = noeudParent.getIndexNoeud();

		System.out.println("\n" + indexNoeudSupp);
		System.out.println("\n" + indexNoeudParent);

		// Modifier le parent du noeud Supp
		if (noeudParent.getIndexfilsDroit() == indexNoeudSupp) {
			ecrireFilsDroit(indexNoeudParent, raf, indexDbNoeudSupp);
		} else if (noeudParent.getIndexfilsGauche() == indexNoeudSupp) {
			ecrireFilsGauche(indexNoeudParent, raf, indexDbNoeudSupp);
		}

		// Modifier le noeud remplacant le noeud Supp
		ecrireFilsGauche(indexNoeudRemplancant, raf, indexFGnoeudSupp);
		ecrireFilsDroit(indexNoeudRemplancant, raf, indexFDnoeudSupp);

		// Modifier noeud supp
		ecrireFilsGauche(indexNoeudSupp, raf, indexNoeudSansFils);
		ecrireFilsDroit(indexNoeudSupp, raf, indexNoeudSansFils);
		ecrireDoublon(indexNoeudSupp, raf, indexNoeudSansFils);

	}
	
	// Méthode pour supprimer un Noeud qui a un fils gauche
	private void modifierNoeudAvecFilsGauche(Noeud noeudSupp, RandomAccessFile raf) {
		int indexNoeudSansFils = -1;

		int indexNoeudParentRemplacant = 0;
		int indexNoeudParent = 0;
		int indexNoeudRemplacant = 0;
		int indexFilsNoeudRemplacant = 0;

		int indexNoeudSupp = noeudSupp.getIndexNoeud();
		int indexNoeudSuppRecursif = noeudSupp.getIndexNoeud();

		// Récupérer l'index du noeud parent du noeud Supp
		indexNoeudParent = recupererIndexParent(indexNoeudSuppRecursif, raf, indexNoeudParent);

		int indexFGNoeudSupp = noeudSupp.getIndexfilsGauche();
		int indexFDNoeudSupp = noeudSupp.getIndexfilsDroit();

		Noeud noeudRecup = new Noeud();
		noeudRecup = noeudRecup.recupererNoeud(noeudRecup, indexFGNoeudSupp, raf);

		// chercher le fils du noeud supp
		if (noeudRecup.getIndexfilsDroit() != indexNoeudSansFils) {
			indexNoeudSuppRecursif = chercherFilsPlusGrand(noeudRecup, raf, indexNoeudSuppRecursif);
		}
		indexNoeudRemplacant = noeudRecup.getIndexNoeud();

		// Récupérer le noeud remplacant
		Noeud noeudRemplacant = new Noeud();
		noeudRemplacant = noeudRemplacant.recupererNoeud(noeudRemplacant, indexNoeudRemplacant, raf);

		// Récupérer l'index du noeud parent du noeud Remplacant
		indexNoeudParentRemplacant = recupererIndexParent(indexNoeudRemplacant, raf, indexNoeudParentRemplacant);

		if (noeudRemplacant.getIndexfilsGauche() != indexNoeudSansFils) {
			indexFilsNoeudRemplacant = noeudRemplacant.getIndexfilsGauche();
		} else if (noeudRemplacant.getIndexfilsDroit() != indexNoeudSansFils) {
			indexFilsNoeudRemplacant = noeudRemplacant.getIndexfilsDroit();
		} else {
			indexFilsNoeudRemplacant = indexNoeudSansFils;
		}

		// Modifier le parent du noeud Supp
		ecrireFilsGauche(indexNoeudParent, raf, indexNoeudRemplacant);

		// Modifier le noeud remplacant le noeud Supp
		ecrireFilsGauche(indexNoeudRemplacant, raf, indexFGNoeudSupp);
		ecrireFilsDroit(indexNoeudRemplacant, raf, indexFDNoeudSupp);

		// Modifier le parent du noeud remplacant pour pas perdre le fils du noeud
		// remplacant
		ecrireFilsDroit(indexNoeudParentRemplacant, raf, indexFilsNoeudRemplacant);

		// Modifier noeud supp
		ecrireFilsGauche(indexNoeudSupp, raf, indexNoeudSansFils);
		ecrireFilsDroit(indexNoeudSupp, raf, indexNoeudSansFils);
		ecrireDoublon(indexNoeudSupp, raf, indexNoeudSansFils);

	}
	
	// Méthode pour supprimer un Noeud qui a un fils droit
	private void modifierNoeudAvecFilsDroit(Noeud noeudSupp, RandomAccessFile raf) {
		int indexNoeudSansFils = -1;

		int indexNoeudParentRemplacant = 0;
		int indexNoeudParent = 0;
		int indexNoeudRemplacant = 0;
		int indexFilsNoeudRemplacant = 0;

		int indexNoeudSupp = noeudSupp.getIndexNoeud();
		int indexNoeudSuppRecursif = noeudSupp.getIndexNoeud();
		// Récupérer l'index du noeud parent du noeud Supp
		indexNoeudParent = recupererIndexParent(indexNoeudSuppRecursif, raf, indexNoeudParent);

		int indexFGNoeudSupp = noeudSupp.getIndexfilsGauche();
		int indexFDNoeudSupp = noeudSupp.getIndexfilsDroit();

		Noeud noeudRecup = new Noeud();
		noeudRecup = noeudRecup.recupererNoeud(noeudRecup, indexFDNoeudSupp, raf);

		// chercher le fils du noeud supp
		if (noeudRecup.getIndexfilsGauche() != indexNoeudSansFils) {
			indexNoeudSuppRecursif = chercherFilsPlusPetit(noeudRecup, raf, indexNoeudSuppRecursif);
		}
		indexNoeudRemplacant = indexNoeudSuppRecursif;

		// Récupérer le noeud remplacant
		Noeud noeudRemplacant = new Noeud();
		noeudRemplacant = noeudRemplacant.recupererNoeud(noeudRemplacant, indexNoeudRemplacant, raf);

		// Récupérer l'index du noeud parent du noeud Remplacant
		indexNoeudParentRemplacant = recupererIndexParent(indexNoeudRemplacant, raf, indexNoeudParentRemplacant);

		if (noeudRemplacant.getIndexfilsGauche() != indexNoeudSansFils) {
			indexFilsNoeudRemplacant = noeudRemplacant.getIndexfilsGauche();
		} else if (noeudRemplacant.getIndexfilsDroit() != indexNoeudSansFils) {
			indexFilsNoeudRemplacant = noeudRemplacant.getIndexfilsDroit();
		} else {
			indexFilsNoeudRemplacant = indexNoeudSansFils;
		}

		// Modifier le parent du noeud Supp
		ecrireFilsDroit(indexNoeudParent, raf, indexNoeudRemplacant);

		// Modifier le noeud remplacant le noeud Supp
		ecrireFilsGauche(indexNoeudRemplacant, raf, indexFGNoeudSupp);
		ecrireFilsDroit(indexNoeudRemplacant, raf, indexFDNoeudSupp);

		// Modifier le parent du noeud remplacant pour pas perdre le fils du noeud
		// remplacant
		ecrireFilsGauche(indexNoeudParentRemplacant, raf, indexFilsNoeudRemplacant);

		// Modifier noeud supp
		ecrireFilsGauche(indexNoeudSupp, raf, indexNoeudSansFils);
		ecrireFilsDroit(indexNoeudSupp, raf, indexNoeudSansFils);
		ecrireDoublon(indexNoeudSupp, raf, indexNoeudSansFils);

	}
	
	//Methode qui permet de récupérer l'index du noeud Parent
	private int recupererIndexParent(int indexNoeudFils, RandomAccessFile raf, int indexNoeudParent) {
		indexNoeud = 0;
		int indexNoeudSansFils = -1;

		while ((indexNoeudFils != recupererIndexFilsGauche(indexNoeud, raf)
				&& (indexNoeudFils != recupererIndexFilsDroit(indexNoeud, raf))
				&& (indexNoeudFils != recupererIndexDoublon(indexNoeud, raf)))) {
			indexNoeud++;
		}

		return indexNoeudParent = indexNoeud;

	}
	
	//Methode qui permet de chercher l'élément le plus petit d'un sous arbre
	private int chercherFilsPlusPetit(Noeud noeudFils, RandomAccessFile raf, int indexNoeudNouveauFils) {
		int indexNoeudSansFils = -1;
		indexNoeud = 0;

		int indexFilsNoeud = noeudFils.getIndexfilsGauche();

		while (indexFilsNoeud != recupererIndexNoeud(indexNoeud, raf)) {
			indexNoeud++;
		}

		Noeud noeudRecup = new Noeud();
		noeudRecup = noeudRecup.recupererNoeud(noeudRecup, indexNoeud, raf);
//		System.out.println(noeudRecup);

		// Si pas de fils gauche
		if (noeudRecup.getIndexfilsGauche() != indexNoeudSansFils) {
			chercherFilsPlusPetit(noeudRecup, raf, indexNoeudNouveauFils);
		}
		indexNoeudNouveauFils = noeudRecup.getIndexNoeud();

		return indexNoeudNouveauFils;

	}
	
	//Methode qui permet de chercher l'élément le plus grand d'un sous arbre
	private int chercherFilsPlusGrand(Noeud noeudFils, RandomAccessFile raf, int indexNoeudNouveauFils) {
		int indexNoeudSansFils = -1;
		indexNoeud = 0;

		int indexFilsNoeud = noeudFils.getIndexfilsDroit();

		while (indexFilsNoeud != recupererIndexNoeud(indexNoeud, raf)) {
			indexNoeud++;
		}

		Noeud noeudRecup = new Noeud();
		noeudRecup = noeudRecup.recupererNoeud(noeudRecup, indexNoeud, raf);
//		System.out.println(noeudRecup);

		// Si pas de fils gauche
		if (noeudRecup.getIndexfilsDroit() != indexNoeudSansFils) {
			chercherFilsPlusGrand(noeudRecup, raf, indexNoeudNouveauFils);
		}
		indexNoeudNouveauFils = noeudRecup.getIndexNoeud();

		return indexNoeudNouveauFils;

	}
	
	//Methode qui permet de récupérer un noeud dans le ficheir binaire
	private Noeud recupererNoeud(Noeud noeudRecup, int indexNoeud, RandomAccessFile raf) {

		try {
			raf.seek(indexNoeud * TAILLE_NOEUD_OCTET);

			int indexNoeudSupp = 0;
			String nomRecup = "";
			String prenomRecup = "";
			String departementRecup = "";
			String formationRecup = "";
			String anneeFormationRecup = "";
			int indexFilsGauche = 0;
			int indexFilsDroit = 0;
			int indexDoublon = 0;

			indexNoeudSupp = raf.readInt();

			for (int k = 0; k < TAILLE_MAX_NOM; k++) {
				nomRecup += raf.readChar();
			}

			for (int k = 0; k < TAILLE_MAX_PRENOM; k++) {
				prenomRecup += raf.readChar();
			}

			for (int k = 0; k < TAILLE_MAX_DEPARTEMENT; k++) {
				departementRecup += raf.readChar();
			}

			for (int k = 0; k < TAILLE_MAX_NOM_PROMO; k++) {
				formationRecup += raf.readChar();
			}

			for (int k = 0; k < TAILLE_MAX_DATE_FORMATION; k++) {
				anneeFormationRecup += raf.readChar();
			}

			indexFilsGauche += raf.readInt();
			indexFilsDroit += raf.readInt();
			indexDoublon += raf.readInt();

			// Création noeud
			noeudRecup = new Noeud(indexNoeudSupp, recupererStagiaireIndex(indexNoeud, raf), indexFilsGauche, indexFilsDroit, indexDoublon);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return noeudRecup;
	}			

	//Methode qui permet d'écrire l'index d'un noeud dans le fichier binaire
	public void ecrireIndexNoeud(int indexParent, RandomAccessFile raf, int indexNoeud) {
		try {
			raf.seek((indexParent * TAILLE_NOEUD_OCTET));
			raf.writeInt(indexNoeud);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	//Methode qui permet d'ajouter un stagiaire dans notre fichier binaire
	public void ajouterUnStagiaire(String nom, String prenom, String departement, String nomPromo, String dateFormation, int compteur, RandomAccessFile raf) {
		Stagiaire stagiaire = new Stagiaire(nom, prenom, departement, nomPromo, dateFormation);
		Noeud noeud = new Noeud(compteur, stagiaire, -1, -1, -1);
		ajouterStagiaireBinaire(noeud, compteur, raf);	
	}
	
	/*
	 * Methode qui permet de modifier un stagiaire dans le fichier binaire
	 * On recherche le stagiaire à supprimer via la recherche multicritère
	 * On demande quels sont les attributs à modifier
	 * On recreer un stagiaire via les attributs récupérés au clavier (ou depuis la recherche multicritère s'ils sont inchangés)
	 * On supprime le stagiaire issus de la recherche
	 * On recreer un stagiaire avec les nouveaux attributs et un nouvel index (en fin de fichier binaire)
	 */
	public void modifierUnStagiaire(String nom, String prenom, String departement, String nomPromo, String dateFormation, RandomAccessFile raf) {
		
		try {
		Noeud noeudAModif = new Noeud();
		ArrayList<Noeud> liste = new ArrayList<>();
		liste = rechercherUnStagiaire(nom, prenom, departement, nomPromo, dateFormation, raf);
		noeudAModif = liste.get(0);
		System.out.println(liste.get(0));
		int indexNoeudSupp = noeudAModif.getIndexNoeud();
		
		String nomchange = "";
		String prenomchange = "";
		String departementchange = "";
		String promochange = "";
		String datepromochange = "";
		
		Scanner clavier = new Scanner(System.in); 
		String reponse;
		System.out.println("modifier le nom ?");
		reponse = clavier.nextLine();
		
		if (reponse.equals("oui")) {
			nomchange = clavier.nextLine();
			
		} else {
			nomchange = nom;
		}
		
		System.out.println("modifier le prenom ?");
		reponse = clavier.nextLine();
		
		if (reponse.equals("oui")) {
			prenomchange = clavier.nextLine();
		} else {
			prenomchange = prenom;
		}
		
		System.out.println("modifier le departement ?");
		reponse = clavier.nextLine();
		
		if (reponse.equals("oui")) {
			departementchange = clavier.nextLine();
		} else {
			departementchange = departement;
		}
		
		System.out.println("modifier le nom de promo ?");
		reponse = clavier.nextLine();
		
		if (reponse.equals("oui")) {
			promochange = clavier.nextLine();
		} else {
			promochange = nomPromo;
		}
		
		System.out.println("modifier la date de promo ?");
		reponse = clavier.nextLine();
		
		if (reponse.equals("oui")) {
			datepromochange = clavier.nextLine();
		} else {
			datepromochange = dateFormation;
		}
		
		
		Stagiaire stagiaire = new Stagiaire(nomchange, prenomchange, departementchange, promochange, datepromochange);
		stagiaire.setNom(stagiaire.getNomLong());
		stagiaire.setPrenom(stagiaire.getPrenomLong());
		stagiaire.setDepartement(stagiaire.getDepartementLong());
		stagiaire.setNomPromo(stagiaire.getNomPromoLong());
		stagiaire.setDateFormation(stagiaire.getDateFormationLong());
		
		supprimerStagiaireNoeud(indexNoeudSupp);
		
	
		int nouvelIndex = (int) (raf.length()/TAILLE_NOEUD_OCTET);
		
		ajouterUnStagiaire(nomchange, prenomchange, departementchange, promochange, datepromochange, nouvelIndex, raf);

		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
	}

	// Cette methode permet d'écrire un Noeud en fin de fichier binaire
	public void ecrireNoeudBin(Noeud noeud, int compteur, RandomAccessFile raf) {
		try {

			raf.seek(raf.length()); // on place le curseur en fin de fichier
			raf.writeInt(compteur);
			recuperationAttributsStagiaireBin(noeud.getCle(), raf); // on recupère les attributs formatés
			raf.writeInt(noeud.getIndexfilsGauche());
			raf.writeInt(noeud.getIndexfilsDroit());
			raf.writeInt(noeud.getIndexDoublon());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Methode qui permet d'écrire un fils gauche
	public void ecrireFilsGauche(int indexParent, RandomAccessFile raf, int indexNoeud) {
		try {
			raf.seek((indexParent * TAILLE_NOEUD_OCTET) + INDEX_FILS_GAUCHE_OCTET);
			raf.writeInt(indexNoeud);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Methode qui permet d'écrire un fils droit
	public void ecrireFilsDroit(int indexParent, RandomAccessFile raf, int indexNoeud) {
		try {
			raf.seek((indexParent * TAILLE_NOEUD_OCTET) + INDEX_FILS_DROIT_OCTET);
			raf.writeInt(indexNoeud);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Cette methode permet d'écrire l'index du doublon dans le noeud parent
	public void ecrireDoublon(int indexParent, RandomAccessFile raf, int indexNoeud) {
		try {
			raf.seek((indexParent * TAILLE_NOEUD_OCTET) + INDEX_DOUBLON_OCTET);
			raf.writeInt(indexNoeud);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Cette methode permet de récupérer l'index du fils gauche à partir du noeud
	// parent
	public int recupererIndexFilsGauche(int indexNoeud, RandomAccessFile raf) {
		int indexParent = 0;
		try {
			raf.seek((indexNoeud * TAILLE_NOEUD_OCTET) + INDEX_FILS_GAUCHE_OCTET);
			indexParent += raf.readInt();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return indexParent;
	}
	

	// Cette methode permet de récupérer l'index du fils droit à partir du noeud
	// parent
	public int recupererIndexFilsDroit(int indexNoeud, RandomAccessFile raf) {
		int indexParent = 0;
		try {
			raf.seek((indexNoeud * TAILLE_NOEUD_OCTET) + INDEX_FILS_DROIT_OCTET);
			indexParent += raf.readInt();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return indexParent;
	}
	
	//Methode qui permet de récupérer l'index d'un Noeud
	public int recupererIndexNoeud(int indexNoeud, RandomAccessFile raf) {
		int indexParent = 0;
		try {
			raf.seek((indexNoeud * TAILLE_NOEUD_OCTET));
			indexParent += raf.readInt();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return indexParent;
	}

	// Cette methode permet de récupérer l'index du doublon à partir du noeud parent
	public int recupererIndexDoublon(int indexNoeud, RandomAccessFile raf) {
		int indexParent = 0;
		try {
			raf.seek((indexNoeud * TAILLE_NOEUD_OCTET) + INDEX_DOUBLON_OCTET);
			indexParent += raf.readInt();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return indexParent;
	}

	// Cette methode permet de récupérer le nom associé à un noeud parent
	public String recupereNomIndex(int indexParent, RandomAccessFile raf) {
		String nomRecup = "";
		try {
			raf.seek(indexParent * TAILLE_NOEUD_OCTET + 4); // On place le curseur au niveau du noeud parent
			for (int k = 0; k < TAILLE_MAX_NOM; k++) {
				nomRecup += raf.readChar(); // On récupère le nom lu grace au curseur
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return nomRecup;
	}
	
	// Cette methode permet de récupérer le prenom associé à un noeud parent
	public String recuperePrenomIndex(int indexParent, RandomAccessFile raf) {
		String prenomRecup = "";
		try {
			raf.seek((indexParent * TAILLE_NOEUD_OCTET) + TAILLE_MAX_NOM*2); // On place le curseur au niveau du noeud parent et après le nom
			for (int k = 0; k < TAILLE_MAX_PRENOM; k++) {
				prenomRecup += raf.readChar(); // On récupère le prenom grace au curseur
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return prenomRecup;
	}
	
	// Cette methode permet de récupérer le departement associé à un noeud parent
	public String recupereDepartementIndex(int indexParent, RandomAccessFile raf) {
		String departementRecup = "";
		try {
			raf.seek((indexParent * TAILLE_NOEUD_OCTET) + (TAILLE_MAX_NOM + TAILLE_MAX_PRENOM)*2 ); // On place le curseur au niveau du noeud parent et après le nom
			for (int k = 0; k < TAILLE_MAX_DEPARTEMENT; k++) {
				departementRecup += raf.readChar(); // On récupère le prenom grace au curseur
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return departementRecup;
	}
	
	// Cette methode permet de récupérer le nom de promo associé à un noeud parent
	public String recupereNomPromoIndex(int indexParent, RandomAccessFile raf) {
		String nomPromoRecup = "";
		try {
			raf.seek((indexParent * TAILLE_NOEUD_OCTET) + (TAILLE_MAX_NOM + TAILLE_MAX_PRENOM + TAILLE_MAX_DEPARTEMENT)*2 ); // On place le curseur au niveau du noeud parent et après le nom
			for (int k = 0; k < TAILLE_MAX_NOM_PROMO; k++) {
				nomPromoRecup += raf.readChar(); // On récupère le prenom grace au curseur
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return nomPromoRecup;
	}
	
	// Cette methode permet de récupérer la date de formation associé à un noeud parent
	public String recupereDateFormationIndex(int indexParent, RandomAccessFile raf) {
		String dateFormationRecup = "";
		try {
			raf.seek((indexParent * TAILLE_NOEUD_OCTET) + (TAILLE_MAX_NOM + TAILLE_MAX_PRENOM + TAILLE_MAX_DEPARTEMENT + TAILLE_MAX_NOM_PROMO)*2 ); // On place le curseur au niveau du noeud parent et après le nom
			for (int k = 0; k < TAILLE_MAX_DATE_FORMATION; k++) {
				dateFormationRecup += raf.readChar(); // On récupère le prenom grace au curseur
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return dateFormationRecup;
	}
	
	//Cette méthode permet de 
	// 1) récupérer les attributs d'un stagiaire à un index précis
	// 2) de créer un stagiaire à l'aide des attributs récupérés
	public Stagiaire recupererStagiaireIndex(int indexParent, RandomAccessFile raf) {
		Stagiaire stagiaire_recup = null;

		try {
			raf.seek(indexParent * TAILLE_NOEUD_OCTET);

			// on crée les variables qui vont stocker les valeurs des attributs
			String nom_recup = "";
			String prenom_recup = "";
			String departement_recup = "";
			String nomPromo_recup = "";
			String dateFormation_recup = "";

			// DEBUT DE LA LECTURE
			
			raf.readInt();
			// 1)
			for (int k = 0; k < TAILLE_MAX_NOM; k++) {
				nom_recup += raf.readChar();
			}
			// 2)
			for (int k = 0; k < TAILLE_MAX_PRENOM; k++) {
				prenom_recup += raf.readChar();
			}
			// 3)
			for (int k = 0; k < TAILLE_MAX_DEPARTEMENT; k++) {
				departement_recup += raf.readChar();
			}
			// 4)
			for (int k = 0; k < TAILLE_MAX_NOM_PROMO; k++) {
				nomPromo_recup += raf.readChar();
			}
			// 5)
			for (int k = 0; k < TAILLE_MAX_DATE_FORMATION; k++) {
				dateFormation_recup += raf.readChar();
			}

			Stagiaire stagiaire = new Stagiaire(nom_recup, prenom_recup, departement_recup, nomPromo_recup,
					dateFormation_recup);

			stagiaire_recup = stagiaire;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return stagiaire_recup;
	}

	// Cette methode permet de récupérer + écrire les attributs formatés des
	// stagiaires
	public void recuperationAttributsStagiaireBin(Stagiaire stagiaire, RandomAccessFile raf) {
		try {
			raf.writeChars(stagiaire.getNomLong());
			raf.writeChars(stagiaire.getPrenomLong());
			raf.writeChars(stagiaire.getDepartementLong());
			raf.writeChars(stagiaire.getNomPromoLong());
			raf.writeChars(stagiaire.getDateFormationLong());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	//Methode qui retourne la taille actuelle de notre fichier binaire
	public int tailleFichierBinaire(RandomAccessFile raf) {
		int taille = 0;
		
		try {
			taille = (int) raf.length()/TAILLE_NOEUD_OCTET;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return taille;
	}

	// Getters & Setters

	public Stagiaire getCle() {
		return cle;
	}

	public void setCle(Stagiaire cle) {
		this.cle = cle;
	}

	public int getIndexfilsGauche() {
		return indexfilsGauche;
	}

	public void setIndexfilsGauche(int indexfilsGauche) {
		this.indexfilsGauche = indexfilsGauche;
	}

	public int getIndexfilsDroit() {
		return indexfilsDroit;
	}

	public void setIndexfilsDroit(int indexfilsDroit) {
		this.indexfilsDroit = indexfilsDroit;
	}

	public int getIndexDoublon() {
		return indexdoublon;
	}

	public void setIndexDoublon(int doublon) {
		this.indexdoublon = doublon;
	}

	public int getIndexNoeud() {
		return indexNoeud;
	}

	public void setIndexNoeud(int index) {
		this.indexNoeud = index;
	}

	public int getIndexdoublon() {
		return indexdoublon;
	}

	public void setIndexdoublon(int indexdoublon) {
		this.indexdoublon = indexdoublon;
	}
	
	

}
