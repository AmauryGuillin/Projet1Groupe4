package fr.isika.cda22.projet1_Groupe4;

import java.io.IOException;
import java.io.RandomAccessFile;

public class LectureFichier implements Constantes {

	// Méthode pour afficher dans la console le contenu du fichier binaire
	public void lireFichierBinaireEnConsole(String chemin_Bin) {
		try {
			RandomAccessFile raf = new RandomAccessFile(chemin_Bin, "rw");
			int nbrStagiaires = (int) (raf.length() / TAILLE_NOEUD_OCTET);
			// on ouvre le flux "raf" associé au fichier binaire

			// int nbrStagiaires = 100; // A DEFINIR PAR UNE FONCTION
			// annuaire.getNombreStagiaires() qui calcule le nombre de noeuds dans
			// l'annuaire;
			for (int i = 0; i < nbrStagiaires; i++) {
				// on positionne la tête de lecture en position "i * TAILLE_STAGIAIRE_OCTET"
				// car .seek() se base sur l'octet et non sur le caractère (1 letter = 2 octets
				// / 1 int = 4 octets)
				// contrairement à .readChar() qui lui se déplace de caractère en caractère
				raf.seek(i * TAILLE_NOEUD_OCTET);

				// on crée les variables qui vont stocker les valeurs des attributs
				String nomRecup = "";
				String prenomRecup = "";
				String departementRecup = "";
				String formationRecup = "";
				String anneeFormationRecup = "";
				int indexFilsGauche = 0;
				int indexFilsDroit = 0;
				int indexDoublon = 0;

				// DEBUT DE LA LECTURE DU "raf"
				// On commence à parcourir/lire le fichier binaire selon ce principe :
				// 1--> de 0 à TAILLE_MAX_NOM, on sait qu'il s'agit des lettres du "nom" du
				// stagiaire
				for (int k = 0; k < TAILLE_MAX_NOM; k++) {
					nomRecup += raf.readChar();
				}
				// 1--> de 0 à TAILLE_MAX_PRENOM, on sait qu'il s'agit des lettres du "prenom"
				// du stagiaire
				for (int k = 0; k < TAILLE_MAX_PRENOM; k++) {
					prenomRecup += raf.readChar();
				}
				// 1--> de 0 à TAILLE_MAX_DEPARTEMENT, on sait qu'il s'agit des numeros du
				// "département" du stagiaire
				for (int k = 0; k < TAILLE_MAX_DEPARTEMENT; k++) {
					departementRecup += raf.readChar();
				}
				// 1--> de 0 à TAILLE_MAX_FORMATION, on sait qu'il s'agit des lettres de la
				// "formation" du stagiaire
				for (int k = 0; k < TAILLE_MAX_NOM_PROMO; k++) {
					formationRecup += raf.readChar();
				}
				// 1--> de 0 à TAILLE_MAX_ANNEEFORMATION, on sait qu'il s'agit des lettres de l'
				// "annee de formation" du stagiaire
				for (int k = 0; k < TAILLE_MAX_DATE_FORMATION; k++) {
					anneeFormationRecup += raf.readChar();
				}
				indexFilsGauche += raf.readInt();
				indexFilsDroit += raf.readInt();
				indexDoublon += raf.readInt();

				// FIN DE LA LECTURE

				// On affiche les résultats
				System.out.println("nomRecup = " + nomRecup.trim());
				System.out.println("prenomRecup = " + prenomRecup.trim());
				System.out.println("departementRecup = " + departementRecup.trim());
				System.out.println("formationRecup = " + formationRecup.trim());
				System.out.println("anneeFormationRecup = " + anneeFormationRecup.trim());
				System.out.println("indexFilsGauche = " + indexFilsGauche);
				System.out.println("indexFilsDroit = " + indexFilsDroit);
				System.out.println("doublon = " + indexDoublon);
			}

			// on ferme le flux "raf"
			raf.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
