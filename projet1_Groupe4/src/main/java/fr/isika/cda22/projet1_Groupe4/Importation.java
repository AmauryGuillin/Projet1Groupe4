package fr.isika.cda22.projet1_Groupe4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Importation implements Constantes {

	/*
	 * Cette methode permet de lire le fichier .txt et de le retranscrire dans un
	 * fichier .bin sous forme d'arbre binaire. Pour ça on a besoin de 3 flux : 1)
	 * le FileReader pour lire le .txt. 2) le BufferedReader pour lire le .txt ligne
	 * par ligne. 3) le RandomAccessFile pour lire et ecrire dans dans le fichier
	 * binaire. A chaque ligne dans le .txt, on va récupérer une information (nom,
	 * prenom...) On "formate" chacun de ces attributs pour s'assurrer qu'ils aient
	 * le forme voulue au final. On créer un nouveau Stagiaire via les attributs
	 * réccupérés. On créer un nouveau Noeud avec le Stagiaire On va ensuite traiter
	 * ce noeud pour le placer dans le fichier binaire.
	 */

	public void convertFileTxtToBinary(String chemin_du_txt, String chemin_du_bin) {

		try {
			FileReader fr = new FileReader(chemin_du_txt);
			BufferedReader br = new BufferedReader(fr);
			RandomAccessFile raf = new RandomAccessFile(chemin_du_bin, "rw");

			while (br.ready()) {

				String nom = br.readLine();
				nom = verifierNoms(nom);
				System.out.println(nom);
				String prenom = br.readLine();
				prenom = verifierPrenoms(prenom);
				String departement = br.readLine();
				departement = verifierDepartement(departement);
				String nomPromo = br.readLine();
				nomPromo = verifierNomPromo(nomPromo);
				String dateFormation = br.readLine();
				dateFormation = vérifierDateFormation(dateFormation);
				br.readLine();

				Stagiaire stagiaire = new Stagiaire(nom, prenom, departement, nomPromo, dateFormation);

				Noeud noeud = new Noeud(stagiaire, -1, -1, -1);
				noeud.ajouterStagiaireBinaire(noeud, raf);

			}

			// Fermeture des flux
			br.close();
			fr.close();

		} catch (IOException e) {
			System.out.println("ERREUR - FICHIER INTROUVABLE");
			e.printStackTrace();
		}
	}

	// Cette methode permet de formater le nom en MAJUSCULE et d'enlever de
	// potentiels espaces en fin de ligne
	public String verifierNoms(String nom) {
		String nom_conforme = nom;
		nom_conforme = nom_conforme.toUpperCase();
		nom_conforme = nom_conforme.strip();
		return nom_conforme;

	}

	// Cette methode permet de formater le nom avec seulement la première lettre en
	// MAJUSCULE et d'enlever de potentiels espaces en fin de ligne
	private String verifierPrenoms(String prenom) {
		String prenom_conforme = prenom;
		prenom_conforme = prenom_conforme.substring(0, 1).toUpperCase() + prenom_conforme.substring(1);
		prenom_conforme = prenom_conforme.strip();
		return prenom_conforme;
	}

	// Cette methode permet d'enlever de potentiels espaces en fin de ligne
	private String verifierDepartement(String departement) {
		String departement_conforme = departement;
		departement_conforme = departement_conforme.strip();
		return departement_conforme;

	}

	// Cette methode permet de tout mettre en MAJUSCULES et d'enlever de potentiels
	// espaces en fin de ligne
	private String verifierNomPromo(String nomPromo) {
		String nom_promo_conforme = nomPromo;
		nom_promo_conforme = nom_promo_conforme.toUpperCase();
		nom_promo_conforme = nom_promo_conforme.strip();
		return nom_promo_conforme;
	}

	// Cette methode permet d'enlever de potentiels espaces en fin de ligne
	private String vérifierDateFormation(String annee) {
		String date_formation_conforme = annee;
		date_formation_conforme = date_formation_conforme.strip();
		return date_formation_conforme;
	}

}
