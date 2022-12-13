package fr.isika.cda22.projet1_Groupe4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application implements Constantes {

	@Override
	public void start(Stage stage) {
		
		/*
		 * ------------------Instanciations------------------
		 */
		
		//Class
		LectureFichier lectureFichier = new LectureFichier();
		
		//Vues
		VueConnexion vueConnexion = new VueConnexion();
		VueInscription vueInscription = new VueInscription();
		VueTEST vuetest = new VueTEST();
		
		lectureFichier.lireBDDAdmins(CHEMIN_BDD_TXT_ADMINISTRATEURS);
		lectureFichier.lireBDDUsers(CHEMIN_BDD_TXT_UTILISATEURS);
		

	
		//Button "Connexion"
		vueConnexion.getBtn_connexion().setOnAction(event -> {
			
				
				for (int i = 0; i < lectureFichier.getListeAdmins().size(); i++) {
					if ((vueConnexion.getUsername().getText().equals(lectureFichier.getListeAdmins().get(i).getNom_de_compte())) 
							&& (vueConnexion.getPassword().getText().equals(lectureFichier.getListeAdmins().get(i).getMot_de_passe()))) {
						stage.setScene(vuetest);
						
						
						stage.setTitle("Tableau Administrateur - " + lectureFichier.getListeAdmins().get(i).getNom() + " "
								+ lectureFichier.getListeAdmins().get(i).getPrenom());
					}
				}
				
				for (int i = 0; i < lectureFichier.getListeUsers().size(); i++) {
					if ((vueConnexion.getUsername().getText().equals(lectureFichier.getListeUsers().get(i).getNom_de_compte()))
							&& (vueConnexion.getPassword().getText().equals(lectureFichier.getListeUsers().get(i).getMot_de_passe()))) {
						stage.setScene(vuetest);
						stage.setTitle("Tableau Utilisateur - " + lectureFichier.getListeUsers().get(i).getNom() + " "
								+ lectureFichier.getListeUsers().get(i).getPrenom());
					}
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
		
		//Button "Inscription"
		vueConnexion.getBtn_inscription().setOnAction(event -> {
			stage.setScene(vueInscription);
			vueConnexion.eraseActionsdAfterUsage();
		});
		
		//Button "S'inscrire"
		vueInscription.getBtn_inscrire().setOnAction(event -> {
			String nom = vueInscription.getNom().getText();
			String prenom = vueInscription.getPrenom().getText();
			String username = vueInscription.getUsername().getText();
			String password = vueInscription.getPassword().getText();
			
			Utilisateur user = new Utilisateur(nom, prenom, username, password);
			lectureFichier.getListeUsers().add(user);
			
			try {
				FileWriter fw = new FileWriter("src/main/java/fr/isika/cda22/Projet1_Groupe4/dossierAnnuaire/Utilisateurs.txt", true);
				
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
		


		// Details du Stage : Titre, scene de base...
		stage.setTitle("Connexion");
		stage.setScene(vueConnexion);
		stage.show();

	}

	public static void main(String[] args) {
		launch();
		
		
		
		
	}

}