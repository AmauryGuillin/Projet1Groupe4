package fr.isika.cda22.projet1_Groupe4;


public class Stagiaire implements Constantes {

	// Attributs
	String nom = "";
	String prenom = "";
	String departement;
	String nomPromo = "";
	String dateFormation;

	// Constructeur
	public Stagiaire(String nom, String prenom, String departement, String nomPromo, String dateFormation) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.departement = departement;
		this.nomPromo = nomPromo;
		this.dateFormation = dateFormation;
	}
	
	public Stagiaire() {
		
	}


	// Cette methode permet d'afficher un stagiaire précis avec une certaine mise en
	// page
	@Override
	public String toString() {
		
//		return "(nom : " + this.getNom().trim() + " prenom : " + this.getPrenom().trim() + ")";
		
		return "\nnom : " + this.getNom().trim() + ", prenom : " + this.getPrenom().trim() + ", departement : "
				+ this.getDepartement().trim() + ", nomPromo : " + this.getNomPromo().trim() + ", dateFormation : "
				+ this.getDateFormation().trim() + " |\n";
	}

	// Cette methode permet de comparer deux stagiaires entre-eux. D'une part part
	// leurs noms, d'autre part via leurs prenoms s'ils les noms sont égaux
	public int compareTo(Stagiaire myStagiaire) {
		if (myStagiaire.getNom().compareTo(this.getNom()) == 0) {
			return myStagiaire.getPrenom().compareTo(this.getPrenom());
		} else {
			return myStagiaire.getNom().compareTo(this.getNom());
		}
	}

	// Cette methode permet de formater le nom pour qu'il soit d'une taille 22
	// caractères
	public String getNomLong() {
		String nomLong = this.getNom();

		for (int i = this.getNom().length(); i < TAILLE_MAX_NOM; i++) {
			nomLong += " ";
		}
		return nomLong;
	}

	// Cette methode permet de formater le nom pour qu'il soit d'une taille 20
	// caractères
	public String getPrenomLong() {
		String prenomLong = this.getPrenom();

		for (int i = this.getPrenom().length(); i < TAILLE_MAX_PRENOM; i++) {
			prenomLong += " ";
		}
		return prenomLong;
	}

	// Cette methode permet de formater le nom pour qu'il soit d'une taille 2
	// caractères
	public String getDepartementLong() {
		String depLong = this.getDepartement();

		for (int i = this.getDepartement().length(); i < TAILLE_MAX_DEPARTEMENT; i++) {
			depLong += " ";
		}
		return depLong;
	}

	// Cette methode permet de formater le nom pour qu'il soit d'une taille 15
	// caractères
	public String getNomPromoLong() {
		String nomPromoLong = getNomPromo();

		for (int i = this.getNomPromo().length(); i < TAILLE_MAX_NOM_PROMO; i++) {
			nomPromoLong += " ";
		}
		return nomPromoLong;
	}

	// Cette methode permet de formater le nom pour qu'il soit d'une taille 4
	// caractères
	public String getDateFormationLong() {
		String dateFormationLong = getDateFormation();

		for (int i = this.getDateFormation().length(); i < TAILLE_MAX_DATE_FORMATION; i++) {
			dateFormationLong += " ";
		}
		return dateFormationLong;
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

}
