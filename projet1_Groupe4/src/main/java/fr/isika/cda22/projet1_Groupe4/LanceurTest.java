package fr.isika.cda22.projet1_Groupe4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.stage.Stage;

public class LanceurTest {

	public static void main(String[] args) {
		
		//Creation du dossier contenant les fichiers
		File dossier_annuaire = new File("src/main/java/fr/isika/cda22/Projet1_Groupe4/dossierAnnuaire");
		dossier_annuaire.mkdir();
		
		//Creation du fichier .txt contenant la liste de stagiaire (fichier de base)
		File annuaire_Stagiaire_text = new File("src/main/java/fr/isika/cda22/Projet1_Groupe4/dossierAnnuaire/annuaireStagiaire.txt");
		try {
			annuaire_Stagiaire_text.createNewFile();
		} catch (IOException e) {
			System.out.println("ERREUR - Dossier introuvable");
			e.printStackTrace();
		}
		
		//Creation du fichier .bin qui servira a placer la liste des stagiaires sous forme d'arbre binaire
		File annuaire_Stagiaire_binaire = new File("src/main/java/fr/isika/cda22/Projet1_Groupe4/dossierAnnuaire/annuaireStagiaire.bin");
		try {
			annuaire_Stagiaire_binaire.createNewFile();
		} catch (IOException e) {
			System.out.println("ERREUR - Dossier introuvable");
			e.printStackTrace();
		}
		
		//Creation d'une ArrayList pour ranger nos différents stagiaires, extrait du .txt
		ArrayList<Stagiaire> listeStagiaires = new ArrayList<>();	
		
		//Extraction des Stagiaires du .txt puis stockage dans l'ArrayList "listeStagiaires"
		try {
			FileReader fr = new FileReader("src/main/java/fr/isika/cda22/Projet1_Groupe4/dossierAnnuaire/annuaireStagiaire.txt");
			BufferedReader br = new BufferedReader(fr);
			
			//Variables de stockage
			String nom = "";
			String prenom = "";
			String departement = "";
			String nomPromo = "";
			String dateFormation = "";
			String etoile = "";
			
			/*
			 * Tant que le BufferReader a quelque chose à lire alors,
			 * première ligne on stock le nom, deuxieme ligne le prenom...
			 * arrivé à l'etoile, on la stock dans une variable "de passage".
			 * On instancie un objet Stagiaire via les variables affectées juste avant
			 * puis on ajoute le stagiaire à l'ArrayList
			 * On recommence ensuite la même opération à la ligne après l'étoile.
			 * De cette façon on créer les stagiaires au fur et à mesure de la lecture, avec comme délimitation : l'étoile " * ".
			 * Puis on ajoute chacun de nos stagiaires à une ArrayList.
			 */
			
			while (br.ready() == true) {
				nom = br.readLine() + " " ;
				prenom = br.readLine() + " " ;
				departement = br.readLine() + " " ;
				nomPromo = br.readLine() + " " ;
				dateFormation = br.readLine();
				etoile = br.readLine();
				Stagiaire stagiaire = new Stagiaire(nom, prenom, departement, nomPromo, dateFormation);
				listeStagiaires.add(stagiaire);
			}
			
			//Fermeture des flux
			br.close();
			fr.close();

		} catch (IOException e) {
			System.out.println("ERREUR - FICHIER INTROUVABLE");
			e.printStackTrace();
		}
		
		//Verification que tout fonctionne correctement !
		System.out.println(listeStagiaires);
		System.out.println(listeStagiaires.size());
		System.out.println(listeStagiaires.get(0));
		System.out.println(listeStagiaires.get(1364));

		
		
		

		
		
		
		
		
		
		
		//Methode avec le Scanner
		
//		try {
//		Scanner input = new Scanner(annuaire_Stagiaire_text);
//		input.useDelimiter("\n");
//		
//		
//		while (input.hasNext()) {
//			String nom = input.next();
//			String prenom = input.next();
//			String departement = input.next();
//			String nomPromo = input.next();
//			String dateFormation = input.next();
//			String connerie = input.next();
//			Stagiaire stagiaire = new Stagiaire(nom, prenom, departement, nomPromo, dateFormation);
//			listeStagiaires.add(stagiaire);
//		}	
//		
//	} catch (IOException e) {
//		System.out.println("ERREUR - Fichier introuvable");
//		e.printStackTrace();
//	}
//	
//	System.out.println(listeStagiaires.size());
		
		
		
	}


}
