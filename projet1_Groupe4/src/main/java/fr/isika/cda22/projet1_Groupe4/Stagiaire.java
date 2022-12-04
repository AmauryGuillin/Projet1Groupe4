package fr.isika.cda22.projet1_Groupe4;

public class Stagiaire {
	
	//Attributs
	String nom = "";
	String prenom = "";
	String departement;
	String nomPromo = "";
	String dateFormation = "";
	
	
	//Constructeur
	public Stagiaire(String nom, String prenom, String departement, String nomPromo, String dateFormation) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.departement = departement;
		this.nomPromo = nomPromo;
		this.dateFormation = dateFormation;
	}
	
	
	//Methodes spécifiques
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
		return "nom : " + nom + " prenom : " + prenom + " departement : " + departement + " nomPromo : " + nomPromo + " dateFormation : " + dateFormation + " // ";
	}
	
	
	
	
	
	
	
	
	

}
