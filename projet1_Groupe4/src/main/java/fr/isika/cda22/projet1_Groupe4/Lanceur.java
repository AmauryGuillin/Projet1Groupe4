package fr.isika.cda22.projet1_Groupe4;

public class Lanceur implements Constantes {

	public static void main(String[] args) {

		// Creation du dossier et des fichiers .txt & .bin
		CreationFichier creationfichier = new CreationFichier();
		creationfichier.creerUnDossier(CHEMIN_DOSSIER);
		creationfichier.creerUnFichierTexte(CHEMIN_TXT);
		creationfichier.creerUnFichierBinaire(CHEMIN_BIN);

		// Traduction du .txt en arbre binaire dans le .bin
		Importation importation = new Importation();
		importation.convertFileTxtToBinary(CHEMIN_TXT, CHEMIN_BIN);

		// Lecture du fichier .bin dans la console pour vérifications
		LectureFichier lireUnFichier = new LectureFichier();
		lireUnFichier.lireFichierBinaireEnConsole(CHEMIN_BIN);

	}

}