package fr.isika.cda22.projet1_Groupe4;

public class Utilisateur extends Personne {

	// Attributs
	String nom_de_compte = "";
	String mot_de_passe = "";

	// Constructeur
	public Utilisateur(String nom, String prenom, String nom_de_compte, String mot_de_passe) {
		super(nom, prenom);
		this.nom_de_compte = nom_de_compte;
		this.mot_de_passe = mot_de_passe;
	}

	// Getters & Setters
	public String getNom_de_compte() {
		return nom_de_compte;
	}

	public void setNom_de_compte(String nom_de_compte) {
		this.nom_de_compte = nom_de_compte;
	}

	public String getMot_de_passe() {
		return mot_de_passe;
	}

	public void setMot_de_passe(String mot_de_passe) {
		this.mot_de_passe = mot_de_passe;
	}

}
