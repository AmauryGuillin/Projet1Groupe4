package fr.isika.cda22.projet1_Groupe4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Importation implements Constantes {

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
	
	
	public String verifierNoms(String nom) {
		String nom_conforme = nom;
		nom_conforme = nom_conforme.toUpperCase();
		nom_conforme = nom_conforme.strip();
		return nom_conforme;
		
	}
	
	private String verifierPrenoms(String prenom) {
		String prenom_conforme = prenom;
		prenom_conforme = prenom_conforme.substring(0, 1).toUpperCase() + prenom_conforme.substring(1);
		return prenom_conforme;
	}
	
	private String verifierDepartement(String departement) {
		String departement_conforme = departement;
		departement_conforme = departement_conforme.strip();
		return departement_conforme;
		
	}
	
	private String verifierNomPromo(String nomPromo) {
		String nom_promo_conforme = nomPromo;
		nom_promo_conforme = nom_promo_conforme.toUpperCase();
		nom_promo_conforme = nom_promo_conforme.strip();
		return nom_promo_conforme;
	}

	private String vérifierDateFormation(String annee) {
		String date_formation_conforme = annee;
		date_formation_conforme = date_formation_conforme.strip();
		return date_formation_conforme;
	}

	

	

	

	



}
