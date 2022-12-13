package fr.isika.cda22.projet1_Groupe4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class LectureFichier implements Constantes {

	ArrayList<Administrateur> listeAdmins;
	ArrayList<Utilisateur> listeUsers;

	/*
	 * Méthode pour afficher dans la console le contenu du fichier binaire
	 * 
	 * On positionne la tête de lecture en position "i * TAILLE_NOEUD_OCTET" car
	 * raf.seek() se base sur l'octet et non sur le caractère (1 letter = 2 octets /
	 * 1 int = 4 octets) (0*138 = 0 --> On tombe bien sur le premier stagiaire 1*138
	 * = 138 --> On tombe bien sur le deuxieme stagiaire...)
	 * 
	 * On commence à parcourir/lire le fichier binaire selon ce principe : Dans le
	 * premier cas 0*138 = 0 octets raf.seek(0) : 1) de 0 à TAILLE_MAX_NOM, on sait
	 * qu'il s'agit des lettres du "nom" du stagiaire 2) de 0 à TAILLE_MAX_PRENOM,
	 * on sait qu'il s'agit des lettres du "prenom" 3) de 0 à
	 * TAILLE_MAX_DEPARTEMENT, on sait qu'il s'agit des numeros du "département" du
	 * stagiaire 4) de 0 à TAILLE_MAX_FORMATION, on sait qu'il s'agit des lettres de
	 * la "formation" du stagiaire 5) de 0 à TAILLE_MAX_ANNEEFORMATION, on sait
	 * qu'il s'agit des lettres de l'"annee de formation" du stagiaire
	 */

	public void lireFichierBinaireEnConsole(String chemin_Bin) {
		try {
			RandomAccessFile raf = new RandomAccessFile(chemin_Bin, "rw");
			int nbrStagiaires = (int) (raf.length() / TAILLE_NOEUD_OCTET);
			for (int i = 0; i < nbrStagiaires; i++) {

				raf.seek(i * TAILLE_NOEUD_OCTET);

				// on crée les variables qui vont stocker les valeurs des attributs
				int index = 0;
				String nomRecup = "";
				String prenomRecup = "";
				String departementRecup = "";
				String formationRecup = "";
				String anneeFormationRecup = "";
				int indexFilsGauche = 0;
				int indexFilsDroit = 0;
				int indexDoublon = 0;

				// DEBUT DE LA LECTURE
					index += raf.readInt();
				// 1)
				for (int k = 0; k < TAILLE_MAX_NOM; k++) {
					nomRecup += raf.readChar();
				}
				// 2)
				for (int k = 0; k < TAILLE_MAX_PRENOM; k++) {
					prenomRecup += raf.readChar();
				}
				// 3)
				for (int k = 0; k < TAILLE_MAX_DEPARTEMENT; k++) {
					departementRecup += raf.readChar();
				}
				// 4)
				for (int k = 0; k < TAILLE_MAX_NOM_PROMO; k++) {
					formationRecup += raf.readChar();
				}
				// 5)
				for (int k = 0; k < TAILLE_MAX_DATE_FORMATION; k++) {
					anneeFormationRecup += raf.readChar();
				}
				indexFilsGauche += raf.readInt();
				indexFilsDroit += raf.readInt();
				indexDoublon += raf.readInt();

				// FIN DE LA LECTURE

				// On affiche les résultats
				System.out.println("index = " + index);
				System.out.println("nomRecup = " + nomRecup.trim());
				System.out.println("prenomRecup = " + prenomRecup.trim());
				System.out.println("departementRecup = " + departementRecup.trim());
				System.out.println("formationRecup = " + formationRecup.trim());
				System.out.println("anneeFormationRecup = " + anneeFormationRecup.trim());
				System.out.println("indexFilsGauche = " + indexFilsGauche);
				System.out.println("indexFilsDroit = " + indexFilsDroit);
				System.out.println("doublon = " + indexDoublon);
			}

			// fermeture du flux
			raf.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void lireBDDAdmins(String cheminBDDAdmins) {

		listeAdmins = new ArrayList<>();

		try {
			FileReader fr = new FileReader(CHEMIN_BDD_TXT_ADMINISTRATEURS);
			BufferedReader br = new BufferedReader(fr);

			while (br.ready()) {
				String nom = br.readLine();
				String prenom = br.readLine();
				String username = br.readLine();
				String password = br.readLine();
				br.readLine();

				Administrateur admin = new Administrateur(nom, prenom, username, password);
				listeAdmins.add(admin);
			}

			br.close();
			fr.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void lireBDDUsers(String cheminBDDUsers) {

		listeUsers = new ArrayList<>();

		try {
			FileReader fr = new FileReader(CHEMIN_BDD_TXT_UTILISATEURS);
			BufferedReader br = new BufferedReader(fr);

			while (br.ready()) {
				String nom = br.readLine();
				String prenom = br.readLine();
				String username = br.readLine();
				String password = br.readLine();
				br.readLine();

				Utilisateur user = new Utilisateur(nom, prenom, username, password);
				listeUsers.add(user);
			}

			br.close();
			fr.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void ecrireBDDUsers(String cheminBDDUsers) {

	}

	public ArrayList<Administrateur> getListeAdmins() {
		return listeAdmins;
	}

	public void setListeAdmins(ArrayList<Administrateur> listeAdmins) {
		this.listeAdmins = listeAdmins;
	}

	public ArrayList<Utilisateur> getListeUsers() {
		return listeUsers;
	}

	public void setListeUsers(ArrayList<Utilisateur> listeUsers) {
		this.listeUsers = listeUsers;
	}

}
