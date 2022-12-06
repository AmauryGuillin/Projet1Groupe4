package fr.isika.cda22.projet1_Groupe4;

public interface Constantes {

	// Constantes pour le fichier binaire

	// Concernant les chemins
	public final static String CHEMIN_DOSSIER = "src/main/java/fr/isika/cda22/Projet1_Groupe4/dossierAnnuaire";
	public final static String CHEMIN_BIN = "src/main/java/fr/isika/cda22/Projet1_Groupe4/dossierAnnuaire/annuaireStagiaire.bin";
	public final static String CHEMIN_TXT = "src/main/java/fr/isika/cda22/Projet1_Groupe4/dossierAnnuaire/annuaireStagiaire.txt";
	

	// Concernant la place en octet de chaque attributs des Stagiaires
	public final static int TAILLE_MAX_NOM = 22;
	public final static int TAILLE_MAX_PRENOM = 20;
	public final static int TAILLE_MAX_DEPARTEMENT = 2;
	public final static int TAILLE_MAX_NOM_PROMO = 15;
	public final static int TAILLE_MAX_DATE_FORMATION = 4;

	// Concernant la place TOTALE qu'ocupe un Stagiaire
	public final static int TAILLE_STAGIAIRE_OCTET = (TAILLE_MAX_NOM * 2) + (TAILLE_MAX_PRENOM * 2)
			+ (TAILLE_MAX_DEPARTEMENT * 2) + (TAILLE_MAX_NOM_PROMO * 2) + (TAILLE_MAX_DATE_FORMATION * 2); // = 126
																											// octets

	// Concernant la place TOTALE qu'ocupe un Noeud dans le fichier binaire
	// (Stagiaire + fils gauche + fils droit + doublon)
	public final static int TAILLE_NOEUD_OCTET = TAILLE_STAGIAIRE_OCTET + 4 + 4 + 4; // = 136 octets

	// Concernant la position des indexes des fils gauche/droit + doublon
	public final static int INDEX_FILS_GAUCHE_OCTET = TAILLE_STAGIAIRE_OCTET; // 124 octets
	public final static int INDEX_FILS_DROIT_OCTET = TAILLE_STAGIAIRE_OCTET + 4; // 124 + 4 octets = 128 octets
	public final static int INDEX_DOUBLON_OCTET = TAILLE_STAGIAIRE_OCTET + 4 + 4; // 124 + 8 octets = 132 octets

}
