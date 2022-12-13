package fr.isika.cda22.projet1_Groupe4;

public interface Constantes {

	// Constantes pour le fichier binaire

	// Concernant les chemins
	public final static String CHEMIN_DOSSIER = "src/main/java/fr/isika/cda22/Projet1_Groupe4/dossierAnnuaire";
	public final static String CHEMIN_BIN = "src/main/java/fr/isika/cda22/Projet1_Groupe4/dossierAnnuaire/annuaireStagiaire.bin";
	public final static String CHEMIN_TXT = "src/main/java/fr/isika/cda22/Projet1_Groupe4/dossierAnnuaire/annuaireStagiaire.txt";
	public final static String CHEMIN_BDD_TXT_ADMINISTRATEURS = "src/main/java/fr/isika/cda22/Projet1_Groupe4/dossierAnnuaire/Administrateurs.txt"; 
	public final static String CHEMIN_BDD_TXT_UTILISATEURS = "src/main/java/fr/isika/cda22/Projet1_Groupe4/dossierAnnuaire/Utilisateurs.txt";
	

	// Concernant la place en octet de chaque attributs des Stagiaires
	public final static int TAILLE_MAX_NOM = 22; //(= 22*2 octets)
	public final static int TAILLE_MAX_PRENOM = 20; //(= 20*2 octets)
	public final static int TAILLE_MAX_DEPARTEMENT = 2; //(=2*2 octets)
	public final static int TAILLE_MAX_NOM_PROMO = 15; //(=15*2 octets)
	public final static int TAILLE_MAX_DATE_FORMATION = 4; //(=4*2 octets)


	// Concernant la place TOTALE qu'ocupe un Stagiaire
	public final static int TAILLE_STAGIAIRE_OCTET = (TAILLE_MAX_NOM * 2) + (TAILLE_MAX_PRENOM * 2)
			+ (TAILLE_MAX_DEPARTEMENT * 2) + (TAILLE_MAX_NOM_PROMO * 2) + (TAILLE_MAX_DATE_FORMATION * 2); // = 126 octets
																											 

	// Concernant la place TOTALE qu'ocupe un Noeud dans le fichier binaire
	// (Stagiaire + fils gauche + fils droit + doublon --> un int = 4 octets)
	public final static int TAILLE_NOEUD_OCTET = 4 + TAILLE_STAGIAIRE_OCTET + 4 + 4 + 4; // = 142 octets

	// Concernant la position des indexes des fils gauche/droit + doublon
	public final static int INDEX_FILS_GAUCHE_OCTET = 4 + TAILLE_STAGIAIRE_OCTET; // 126 octets
	public final static int INDEX_FILS_DROIT_OCTET = 4 +TAILLE_STAGIAIRE_OCTET + 4; // 124 + 4 octets = 130 octets
	public final static int INDEX_DOUBLON_OCTET = 4 + TAILLE_STAGIAIRE_OCTET + 4 + 4; // 124 + 8 octets = 134 octets

}
