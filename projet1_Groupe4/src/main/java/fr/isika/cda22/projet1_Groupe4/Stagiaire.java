package fr.isika.cda22.projet1_Groupe4;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Stagiaire implements Constantes {

	// Attributs
	String nom = "";
	String prenom = "";
	String departement;
	String nomPromo = "";
	String dateFormation;

	private static int cptNumStagiaire = 0;

	// Constructeur
	public Stagiaire(String nom, String prenom, String departement, String nomPromo, String dateFormation) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.departement = departement;
		this.nomPromo = nomPromo;
		this.dateFormation = dateFormation;
	}

	// Getters & Setters
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getDepartement() {
		return departement;
	}

	public void setDepartement(String departement) {
		this.departement = departement;
	}

	public String getNomPromo() {
		return nomPromo;
	}

	public void setNomPromo(String nomPromo) {
		this.nomPromo = nomPromo;
	}

	public String getDateFormation() {
		return dateFormation;
	}

	public void setDateFormation(String dateFormation) {
		this.dateFormation = dateFormation;
	}

	@Override
	public String toString() {
		return "[nom : " + this.getNom().trim() + ", prenom : " + this.getPrenom().trim() + ", departement : "
				+ this.getDepartement().trim() + ", nomPromo : " + this.getNomPromo().trim() + ", dateFormation : "
				+ this.getDateFormation().trim() + "] | ";
	}

	public int compareTo(Stagiaire myStagiaire) {
		if (myStagiaire.getNom().compareTo(this.getNom()) == 0) {
			return myStagiaire.getPrenom().compareTo(this.getPrenom());
		} else {
			return myStagiaire.getNom().compareTo(this.getNom());
		}
	}

	public String getNomLong() {
		String nomLong = this.getNom();

		for (int i = this.getNom().length(); i < TAILLE_MAX_NOM; i++) {
			nomLong += " ";
		}
		return nomLong;
	}

	public String getPrenomLong() {
		String prenomLong = this.getPrenom();

		for (int i = this.getPrenom().length(); i < TAILLE_MAX_PRENOM; i++) {
			prenomLong += " ";
		}
		return prenomLong;
	}

	public String getDepartementLong() {
		String depLong = this.getDepartement();

		for (int i = this.getDepartement().length(); i < TAILLE_MAX_DEPARTEMENT; i++) {
			depLong += " ";
		}
		return depLong;
	}

	public String getNomPromoLong() {
		String nomPromoLong = getNomPromo();

		for (int i = this.getNomPromo().length(); i < TAILLE_MAX_NOM_PROMO; i++) {
			nomPromoLong += " ";
		}
		return nomPromoLong;
	}

	public String getDateFormationLong() {
		String dateFormationLong = getDateFormation();

		for (int i = this.getDateFormation().length(); i < TAILLE_MAX_DATE_FORMATION; i++) {
			dateFormationLong += " ";
		}
		return dateFormationLong;
	}

	public void sauvegarderFichierBinaireStagiaire() {

		try {
			// on accède au fichier binaire et on ouvre le flux "raf"
			RandomAccessFile raf = new RandomAccessFile(CHEMIN_BIN, "rw");

			// le lecteur de fichier binaire se place à la position "cptNumStagiaire *
			// TAILLE_STAGIAIRE_OCTET"
			// car .seek() se base sur l'octet et non sur le caractère (1 letter = 2 octets
			// / 1 int = 4 octets)
			raf.seek(cptNumStagiaire * TAILLE_STAGIAIRE_OCTET);

			// puis on ecrit dans le fichier binaire les attributs du stagiaire (avec si
			// besoin un formatage de la longueur de l'attribut)
			String nomLong = this.getNomLong();
			String prenomLong = this.getPrenomLong();
			raf.writeChars(nomLong);
			raf.writeChars(prenomLong);
			raf.writeChars(this.getDepartementLong());
			raf.writeChars(this.getNomPromoLong());
			raf.writeChars(this.getDateFormationLong());

			// puis on incrémente le compteur stagiaires cptNumStagiaire
			cptNumStagiaire++;

			// On ferme le flux "raf"
			raf.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
