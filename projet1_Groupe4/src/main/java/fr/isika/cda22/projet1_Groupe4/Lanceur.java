package fr.isika.cda22.projet1_Groupe4;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class Lanceur implements Constantes {
	
	

	public static void main(String[] args) {

		// Creation du dossier et des fichiers .txt & .bin
		CreationFichier creationfichier = new CreationFichier();
//		creationfichier.creerUnDossier(CHEMIN_DOSSIER);
//		creationfichier.creerUnFichierTexte(CHEMIN_TXT);
//		creationfichier.creerUnFichierBinaire(CHEMIN_BIN);

		// Traduction du .txt en arbre binaire dans le .bin
		Importation importation = new Importation();
//		importation.convertFileTxtToBinary(CHEMIN_TXT, CHEMIN_BIN);
		
		// Lecture du fichier .bin dans la console pour vérifications
		LectureFichier lireUnFichier = new LectureFichier();
//		lireUnFichier.lireFichierBinaireEnConsole(CHEMIN_BIN);	
		
		ArrayList<Stagiaire> tableau = new ArrayList<>();
		ArrayList<Noeud> tableauRecherche = new ArrayList<>();
		RandomAccessFile raf;
		
		Noeud noeud1 = new Noeud();
		try {
			
			raf = new RandomAccessFile(CHEMIN_BIN, "rw");
			
			//toArray
//			tableau = noeud1.toArray(raf);
//			System.out.println(tableau);
//			System.out.println(tableau.size());
			
			
			//Ajout
//			noeud1.ajouterUnStagiaire("GUILLIN", "Amaury", "91", "CDA 22", "2022", 16, raf);
			
			
			//Recherche
//			tableauRecherche = noeud1.rechercherUnStagiaire("GARIJO", "Rosie", "75", "AI 79", "2011", raf);			
//			System.out.println(tableauRecherche);
	
			//Suppression test
//			noeud1.supprimerStagiaireNoeud(17);
			
			//Modifier Stagiaire
//			noeud1.modifierUnStagiaire("GARIJO", "Rosie", "75", "AI 79", "2011", raf);
			
			

			
			
			System.out.println("----------------------------------------------------------------------------------------------");
			
			lireUnFichier.lireFichierBinaireEnConsole(CHEMIN_BIN);
//			
			tableau = noeud1.toArray(raf);
			
			System.out.println(tableau);
			System.out.println(tableau.size());
			
			
			
			
			
			
			
			
			

			
			raf.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

}