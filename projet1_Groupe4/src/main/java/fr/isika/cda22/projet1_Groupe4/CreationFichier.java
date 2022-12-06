package fr.isika.cda22.projet1_Groupe4;

import java.io.File;
import java.io.IOException;

public class CreationFichier implements Constantes {

	// Creation du dossier contenant les fichiers .txt et .bin
	public void creerUnDossier(String chemin) {
		File dossier_annuaire = new File(chemin);
		dossier_annuaire.mkdir();
	}

	// Creation du fichier .txt contenant la liste de stagiaire
	public void creerUnFichierTexte(String chemin) {
		try {
			File annuaire_Stagiaire_text = new File(chemin);
			annuaire_Stagiaire_text.createNewFile();
		} catch (IOException e) {
			System.out.println("ERREUR - Dossier introuvable");
			e.printStackTrace();
		}
	}

	// Creation du fichier .bin qui servira a placer la liste des stagiaires sous
	// forme d'arbre binaire
	public void creerUnFichierBinaire(String chemin) {
		try {
			File annuaire_Stagiaire_binaire = new File(chemin);
			annuaire_Stagiaire_binaire.createNewFile();
		} catch (IOException e) {
			System.out.println("ERREUR - Dossier introuvable");
			e.printStackTrace();
		}

	}

}
