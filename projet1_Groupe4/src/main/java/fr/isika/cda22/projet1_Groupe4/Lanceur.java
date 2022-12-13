package fr.isika.cda22.projet1_Groupe4;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Lanceur extends Application implements Constantes {

	@Override
	public void start(Stage stage) throws Exception {

		// Class
		LectureFichier lectureFichier = new LectureFichier();

		// Vues
		VueConnexion vueConnexion = new VueConnexion();
		VueInscription vueInscription = new VueInscription();
		VueAccueil vueAccueil = new VueAccueil();
		VueStagiaireModifierAdmin vueStagiaireModifierAdmin = new VueStagiaireModifierAdmin();
		VueStagiaireAjouterAdmin vueStagiaireAjouterAdmin = new VueStagiaireAjouterAdmin();
		VuePrincipaleAdmin vuePrincipaleAdmin = new VuePrincipaleAdmin();
		VuePrincipaleUser vuePrincipaleUser = new VuePrincipaleUser();

		// Recupération des admins/users dans les BDD correspondantes
		lectureFichier.lireBDDAdmins(CHEMIN_BDD_TXT_ADMINISTRATEURS);
		lectureFichier.lireBDDUsers(CHEMIN_BDD_TXT_UTILISATEURS);

		// Button "Connexion"
		vueConnexion.getBtn_connexion().setOnAction(event -> {

			for (int i = 0; i < lectureFichier.getListeAdmins().size(); i++) {
				if ((vueConnexion.getUsername().getText()
						.equals(lectureFichier.getListeAdmins().get(i).getNom_de_compte()))
						&& (vueConnexion.getPassword().getText()
								.equals(lectureFichier.getListeAdmins().get(i).getMot_de_passe()))) {
					stage.setScene(vuePrincipaleAdmin);

					stage.setTitle("Tableau Administrateur - " + lectureFichier.getListeAdmins().get(i).getNom() + " "
							+ lectureFichier.getListeAdmins().get(i).getPrenom());
				}
			}

			for (int i = 0; i < lectureFichier.getListeUsers().size(); i++) {
				if ((vueConnexion.getUsername().getText()
						.equals(lectureFichier.getListeUsers().get(i).getNom_de_compte()))
						&& (vueConnexion.getPassword().getText()
								.equals(lectureFichier.getListeUsers().get(i).getMot_de_passe()))) {
					stage.setScene(vuePrincipaleUser);
					stage.setTitle("Tableau Utilisateur - " + lectureFichier.getListeUsers().get(i).getNom() + " "
							+ lectureFichier.getListeUsers().get(i).getPrenom());
				}
			}

			if (vueConnexion.getPassword().getText().isEmpty() && vueConnexion.getUsername().getText().isEmpty()) {
				vueConnexion.showAlert(Alert.AlertType.ERROR, stage.getScene().getWindow(), "Erreur",
						"Veuillez entrer vos identifiants");
				return;
			}

			if (vueConnexion.getUsername().getText().isEmpty()) {
				vueConnexion.showAlert(Alert.AlertType.ERROR, stage.getScene().getWindow(), "Erreur",
						"Vous n'avez pas saisi de nom d'utilisateur.");
				return;

			}

			if (vueConnexion.getPassword().getText().isEmpty()) {
				vueConnexion.showAlert(Alert.AlertType.ERROR, stage.getScene().getWindow(), "Erreur",
						"Vous n'avez pas saisi votre mot de passe.");
				return;
			}

		});

		// Button "Inscription"
		vueConnexion.getBtn_inscription().setOnAction(event -> {
			stage.setScene(vueInscription);
			vueConnexion.eraseActionsdAfterUsage();
		});

		// Button "S'inscrire"
		vueInscription.getBtn_inscrire().setOnAction(event -> {
			String nom = vueInscription.getNom().getText();
			String prenom = vueInscription.getPrenom().getText();
			String username = vueInscription.getUsername().getText();
			String password = vueInscription.getPassword().getText();

			Utilisateur user = new Utilisateur(nom, prenom, username, password);
			lectureFichier.getListeUsers().add(user);

			try {
				FileWriter fw = new FileWriter(
						"src/main/java/fr/isika/cda22/Projet1_Groupe4/dossierAnnuaire/Utilisateurs.txt", true);

				fw.write(nom + "\n");
				fw.write(prenom + "\n");
				fw.write(username + "\n");
				fw.write(password + "\n");
				fw.write("*" + "\n");

				fw.close();

			} catch (IOException e) {
				System.out.println("ERREUR - FICHIER INTROUVABLE");
				e.printStackTrace();
			}

			stage.setScene(vueConnexion);
		});
		
		// Les events à partir de la classe vueAccueil

		vueAccueil.getItem1().setOnAction(event -> {
			stage.setScene(vueAccueil);
		});

		vueAccueil.getItem3().setOnAction(event -> {
			stage.setScene(vueStagiaireAjouterAdmin);
		});

		vueAccueil.getItem4().setOnAction(event -> {
			stage.setScene(vueStagiaireModifierAdmin);
		});

		vueAccueil.getItem5().setOnAction(event -> {
			stage.setScene(vueConnexion);
		});

		// Les events à partir de la classe vueStagiaireAjouterAdmin

		vueStagiaireAjouterAdmin.getItem1().setOnAction(event -> {
			stage.setScene(vueAccueil);
		});

		vueStagiaireAjouterAdmin.getItem3().setOnAction(event -> {
			stage.setScene(vueStagiaireAjouterAdmin);
		});

		vueStagiaireAjouterAdmin.getItem4().setOnAction(event -> {
			stage.setScene(vueStagiaireModifierAdmin);
		});

		vueStagiaireAjouterAdmin.getItem5().setOnAction(event -> {
			stage.setScene(vueConnexion);
		});

		// Les events à partir de la classe vueStagiaireModifierAdmin

		vueStagiaireModifierAdmin.getItem1().setOnAction(event -> {
			stage.setScene(vueAccueil);
		});

		vueStagiaireModifierAdmin.getItem3().setOnAction(event -> {
			stage.setScene(vueStagiaireAjouterAdmin);
		});

		vueStagiaireModifierAdmin.getItem4().setOnAction(event -> {
			stage.setScene(vueStagiaireModifierAdmin);
		});

		vueStagiaireModifierAdmin.getItem5().setOnAction(event -> {
			stage.setScene(vueConnexion);
		});
		
		// Les events à partir de la classe vuePrincipaleAdmin

		vuePrincipaleAdmin.getItem1().setOnAction(event -> {
			stage.setScene(vueAccueil);
		});

		vuePrincipaleAdmin.getItem3().setOnAction(event -> {
			stage.setScene(vueStagiaireAjouterAdmin);
		});

		vuePrincipaleAdmin.getItem4().setOnAction(event -> {
			stage.setScene(vueStagiaireModifierAdmin);
		});

		vuePrincipaleAdmin.getItem5().setOnAction(event -> {
			stage.setScene(vueConnexion);
		});
		
		
		

		Image image = new Image(
				"file:///D:/Formation%20Ing%E9%20informatique/Projet1/projet1_Groupe4/src/main/java/fr/isika/cda22/projet1_Groupe4/images/student.jpg");
		stage.getIcons().add(image);

		stage.setScene(vueConnexion);
		stage.setTitle("NOM DE L'APPLICATION - NOM DE LA FENETRE");
		stage.show();

	}

	// --------------------------------------------------------------------------------BACK
	// FICHIER
	// BINAIRE--------------------------------------------------------------------------------//

	public static void main(String[] args) {
		launch();
		// Creation du dossier et des fichiers .txt & .bin
		CreationFichier creationfichier = new CreationFichier();
//		creationfichier.creerUnDossier(CHEMIN_DOSSIER);
//		creationfichier.creerUnFichierTexte(CHEMIN_TXT);
//		creationfichier.creerUnFichierBinaire(CHEMIN_BIN);

		// Traduction du .txt en arbre binaire dans le .bin
		Importation importation = new Importation();
//		importation.convertFileTxtToBinary(CHEMIN_TXT, CHEMIN_BIN);

		// Lecture du fichier .bin dans la console pour vérifications
		LectureFichier lireUnFichier = new LectureFichier();
//		lireUnFichier.lireFichierBinaireEnConsole(CHEMIN_BIN);	

		ArrayList<Stagiaire> tableau = new ArrayList<>();
		ArrayList<Noeud> tableauRecherche = new ArrayList<>();
		RandomAccessFile raf;

		Noeud noeud1 = new Noeud();
		try {

			raf = new RandomAccessFile(CHEMIN_BIN, "rw");

			// toArray
//			tableau = noeud1.toArray(raf);
//			System.out.println(tableau);
//			System.out.println(tableau.size());

			// Ajout
//			noeud1.ajouterUnStagiaire("GUILLIN", "Amaury", "91", "CDA 22", "2022", 16, raf);

			// Recherche
//			tableauRecherche = noeud1.rechercherUnStagiaire("GARIJO", "Rosie", "75", "AI 79", "2011", raf);			
//			System.out.println(tableauRecherche);

			// Suppression test
//			noeud1.supprimerStagiaireNoeud(17);

			// Modifier Stagiaire
//			noeud1.modifierUnStagiaire("GARIJO", "Rosie", "75", "AI 79", "2011", raf);

			System.out.println(
					"----------------------------------------------------------------------------------------------");

//			lireUnFichier.lireFichierBinaireEnConsole(CHEMIN_BIN);
//			
//			tableau = noeud1.toArray(raf);
//
//			System.out.println(tableau);
//			System.out.println(tableau.size());

			raf.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}