package fr.isika.cda22.projet1_Groupe4;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;


import javafx.application.Application;
import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class Lanceur extends Application implements Constantes {

	@Override
	public void start(Stage stage) throws Exception {

		// Instanciation des Class
		LectureFichier lectureFichier = new LectureFichier();

		// Instanciation des Vues
		VueConnexion vueConnexion = new VueConnexion();
		VueInscription vueInscription = new VueInscription();
		VueStagiaireModifierAdmin vueStagiaireModifierAdmin = new VueStagiaireModifierAdmin();
		VueStagiaireAjouterAdmin vueStagiaireAjouterAdmin = new VueStagiaireAjouterAdmin();
		VuePrincipaleAdmin vuePrincipaleAdmin = new VuePrincipaleAdmin();
		VuePrincipaleUser vuePrincipaleUser = new VuePrincipaleUser();
		VueStagiaireAjouterUser vueStagiaireAjouterUser = new VueStagiaireAjouterUser();
		

		// Recupération des admins/users dans les BDD correspondantes
		lectureFichier.lireBDDAdmins(CHEMIN_BDD_TXT_ADMINISTRATEURS);
		lectureFichier.lireBDDUsers(CHEMIN_BDD_TXT_UTILISATEURS);
		
		//Icon de l'application
		Image image = new Image("https://freepngimg.com/save/140469-pip-boy-fallout-free-transparent-image-hq/822x1009");

		//--------------------------------------------------------------------------------------Connexion--------------------------------------------------------------------------------------//
		
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
				vueConnexion.showAlert(Alert.AlertType.CONFIRMATION, stage.getScene().getWindow(), "Erreur",
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

			vueConnexion.eraseActionsdAfterUsage();
		});

		// Button "Inscription"
		vueConnexion.getBtn_inscription().setOnAction(event -> {
			stage.setScene(vueInscription);
			stage.setTitle("DataVault - Inscription");
			vueConnexion.eraseActionsdAfterUsage();
		});

		//--------------------------------------------------------------------------------------Inscription--------------------------------------------------------------------------------------//

		// Button "S'inscrire"
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
			vueInscription.eraseActionsdAfterUsage();
			stage.setScene(vueConnexion);
			stage.setTitle("DataVault - Connexion");
		});
		
		vueInscription.getBtn_retour().setOnAction(event ->{
			stage.setScene(vueConnexion);
			stage.setTitle("DataVault - Connexion");	
		});

		//--------------------------------------------------------------------------------------Tableau Admin--------------------------------------------------------------------------------------//

		// Button valider dans la vue "ajouter un stagiaire"
		vueStagiaireAjouterAdmin.getBtnValider().setOnAction(event -> {
			try {
				RandomAccessFile raf;
				
				raf = new RandomAccessFile(CHEMIN_BIN, "rw");
				Noeud noeud = new Noeud();
				
				String nom = vueStagiaireAjouterAdmin.getTextFieldNom().getText();
				String prenom = vueStagiaireAjouterAdmin.getTextFieldPrenom().getText();
				String departement = vueStagiaireAjouterAdmin.getTextFieldDepartement().getText();
				String promo = vueStagiaireAjouterAdmin.getTextFieldPromo().getText();
				String anneePromo = vueStagiaireAjouterAdmin.getTextFieldAnneePromo().getText();
				
				noeud.ajouterUnStagiaire(nom, prenom, departement, promo, anneePromo, noeud.tailleFichierBinaire(raf), raf);
				
				vuePrincipaleAdmin.getTable().setItems(vuePrincipaleAdmin.getStagiairesList());
				vuePrincipaleUser.getTable().setItems(vuePrincipaleAdmin.getStagiairesList());
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			stage.setScene(vuePrincipaleAdmin);
			stage.setTitle("DataVault - Tableau Administrateur");
		});
		
		
		//Button modifier
		vuePrincipaleAdmin.getBtnModifier().setOnAction(event -> {

			
			Stagiaire selected = vuePrincipaleAdmin.getTable().getSelectionModel().getSelectedItem();
			
			vueStagiaireModifierAdmin.getTextFieldNom().setText(selected.getNom().trim());
			vueStagiaireModifierAdmin.getTextFieldPrenom().setText(selected.getPrenom().trim());
			vueStagiaireModifierAdmin.getTextFieldDepartement().setText(selected.getDepartement().trim());
			vueStagiaireModifierAdmin.getTextFieldPromo().setText(selected.getNomPromo().trim());
			vueStagiaireModifierAdmin.getTextFieldAnneePromo().setText(selected.getDateFormation().trim());
			
			vuePrincipaleAdmin.setStagiaire(selected);
			
			stage.setScene(vueStagiaireModifierAdmin);
			stage.setTitle("DataVault - Modifier un stagiaire");
			
			
		});
		
		//Button valider vue "modifier Admin"
		vueStagiaireModifierAdmin.getBtnValider().setOnAction(event -> {
			ArrayList<Noeud> liste = new ArrayList<>();
			Noeud noeud = new Noeud();
			Noeud noeudASupp = new Noeud();
			RandomAccessFile raf;
			
			
			try {
				raf = new RandomAccessFile(CHEMIN_BIN, "rw");
			
			liste = noeud.rechercherUnStagiaire( vuePrincipaleAdmin.getStagiaire().getNom(),
					vuePrincipaleAdmin.getStagiaire().getPrenom(),
					vuePrincipaleAdmin.getStagiaire().getDepartement(),
					vuePrincipaleAdmin.getStagiaire().getNomPromo(),
					vuePrincipaleAdmin.getStagiaire().getDateFormation(),
					raf);
			
			noeudASupp = liste.get(0);
			int indexNoeudSupp = noeudASupp.getIndexNoeud();
			
			noeud.supprimerStagiaireNoeud(indexNoeudSupp);
			
			String nom = vueStagiaireModifierAdmin.getTextFieldNom().getText();
			String prenom = vueStagiaireModifierAdmin.getTextFieldPrenom().getText();
			String departement = vueStagiaireModifierAdmin.getTextFieldDepartement().getText();
			String promo = vueStagiaireModifierAdmin.getTextFieldPromo().getText();
			String anneePromo = vueStagiaireModifierAdmin.getTextFieldAnneePromo().getText();
			
			noeud.ajouterUnStagiaire(nom, prenom, departement, promo, anneePromo, noeud.tailleFichierBinaire(raf), raf);
			
			
			
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			vuePrincipaleAdmin.getTable().setItems(vuePrincipaleAdmin.getStagiairesList());
			vuePrincipaleUser.getTable().setItems(vuePrincipaleAdmin.getStagiairesList());
			stage.setScene(vuePrincipaleAdmin);
			stage.setTitle("DataVault - Tableau Administrateur");
			
		});

		//Button supprimer
		vuePrincipaleAdmin.getBtnSupprimer().setOnAction(event -> {
			
			Stagiaire selected = vuePrincipaleAdmin.getTable().getSelectionModel().getSelectedItem();
			
			ArrayList<Noeud> liste = new ArrayList<>();
			Noeud noeud = new Noeud();
			Noeud noeudASupp = new Noeud();
			RandomAccessFile raf;
			
			Alert alert = new Alert(AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
			alert.setHeaderText("Etes-vous sûr de vouloir supprimer " + selected.getNom().trim() + " " + selected.getPrenom().trim() + " de la liste ?");
			alert.setTitle("DataVault - Confirmation");
			alert.showAndWait();
			
			if (alert.getResult() == ButtonType.YES) {
				try {
					raf = new RandomAccessFile(CHEMIN_BIN, "rw");
				
				
				
				liste = noeud.rechercherUnStagiaire(selected.getNom(),
						selected.getPrenom(),
						selected.getDepartement(),
						selected.getNomPromo(),
						selected.getDateFormationLong(),
						raf);
				
				noeudASupp = liste.get(0);
				int indexNoeudSupp = noeudASupp.getIndexNoeud();
				
				noeud.supprimerStagiaireNoeud(indexNoeudSupp);
				
				
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				
				vuePrincipaleAdmin.getTable().setItems(vuePrincipaleAdmin.getStagiairesList());
				vuePrincipaleUser.getTable().setItems(vuePrincipaleAdmin.getStagiairesList());	
			}	
			
		});

		//Button "ajouter" vue tableau Admin
			vuePrincipaleAdmin.getBtnAjouter().setOnAction(event -> {
			stage.setScene(vueStagiaireAjouterAdmin);
			stage.setTitle("DataVault - Ajouter un stagiaire");
		});
	
			
		//--------------------------------------------------------------------------------------Tableau User--------------------------------------------------------------------------------------//
		
			//Button valider vue "ajouter un stagiaire - user"
			vueStagiaireAjouterUser.getBtnValider().setOnAction(event -> {
				
				try {
					RandomAccessFile raf;
					
					raf = new RandomAccessFile(CHEMIN_BIN, "rw");
					Noeud noeud = new Noeud();
					
					String nom = vueStagiaireAjouterUser.getTextFieldNom().getText();
					String prenom = vueStagiaireAjouterUser.getTextFieldPrenom().getText();
					String departement = vueStagiaireAjouterUser.getTextFieldDepartement().getText();
					String promo = vueStagiaireAjouterUser.getTextFieldPromo().getText();
					String anneePromo = vueStagiaireAjouterUser.getTextFieldAnneePromo().getText();
					
					
					noeud.ajouterUnStagiaire(nom, prenom, departement, promo, anneePromo, noeud.tailleFichierBinaire(raf), raf);
					
					Stagiaire stagiaire = new Stagiaire(nom, prenom, departement, promo, anneePromo);	
				
					vuePrincipaleUser.getTable().setItems(vuePrincipaleUser.getStagiairesList());
					vuePrincipaleAdmin.getTable().setItems(vuePrincipaleAdmin.getStagiairesList());

					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				
				stage.setScene(vuePrincipaleUser);
				stage.setTitle("DataVault - Tableau Utilisateur");

			});
			
			vueStagiaireAjouterUser.getItem5().setOnAction(event -> {
				stage.setScene(vueConnexion);
			});
			
			vueStagiaireAjouterUser.getItem1().setOnAction(event -> {
				
				stage.setScene(vuePrincipaleUser);
			});
			
			//Button ajouter
			vuePrincipaleUser.getBtnAjouter().setOnAction(event -> {
				stage.setScene(vueStagiaireAjouterUser);

			});
			

		//--------------------------------------------------------------------------------------MENU--------------------------------------------------------------------------------------//
		
		
		
		//---------------------- Les events à partir de la classe vueAccueil ADMIN ----------------------//

		//menu - button deconnexion
		vuePrincipaleAdmin.getItem5().setOnAction(event -> {
			stage.setScene(vueConnexion);
			stage.setTitle("DataVault - Connexion");
		});
		
		//menu - button imprimmer
		vuePrincipaleAdmin.getItemImprimer().setOnAction(event -> {
			
			final File file = new File("D:\\\\Formation Ingé informatique\\\\TESTpdf.pdf");
			
			try {
	            Document document = new Document();
	            PdfWriter.getInstance(document, new FileOutputStream(file));
	            document.open();
	            Pdf.ajouterContenu(document);
	            document.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			
			HostServices hostServices = getHostServices();
			hostServices.showDocument(file.getAbsolutePath());
			
		});	
		
		
		
		//---------------------- Les events à partir de la classe vue Ajouter Admin --------------------//
		
		//menu - button Accueil
		vueStagiaireAjouterAdmin.getItem1().setOnAction(event -> {
			stage.setScene(vuePrincipaleAdmin);
			stage.setTitle("DataVault - Tableau Administrateur");
		});
		
		//menu - button deconnexion
		vueStagiaireAjouterAdmin.getItem5().setOnAction(event -> {
			stage.setScene(vueConnexion);
			stage.setTitle("DataVault - Connexion");
		});
		
		
		//---------------------- Les events à partir de la classe vue Modifier Admin --------------------//

		//menu - button Accueil
		vueStagiaireModifierAdmin.getItem1().setOnAction(event -> {
			stage.setScene(vuePrincipaleAdmin);
			stage.setTitle("DataVault - Tableau Administrateur");
		});

		//menu - button deconnexion
		vueStagiaireModifierAdmin.getItem5().setOnAction(event -> {
			stage.setScene(vueConnexion);
			stage.setTitle("DataVault - Connexion");
		});
		
		
		//---------------------- Les events à partir de la classe vue principale User -------------------//
		
		//menu - boutton imprimmer
		vuePrincipaleUser.getItemImprimer().setOnAction(event -> {

			final File file = new File("D:\\\\Formation Ingé informatique\\\\TESTpdf.pdf");

			try {
				Document document = new Document();
				PdfWriter.getInstance(document, new FileOutputStream(file));
				document.open();
				Pdf.ajouterContenu(document);
				document.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			HostServices hostServices = getHostServices();
			hostServices.showDocument(file.getAbsolutePath());
		});

		//menu - button deconnexion
		vuePrincipaleUser.getItem5().setOnAction(event -> {
			stage.setScene(vueConnexion);
			stage.setTitle("DataVault - Connexion");
		});
		

		
		//--------------------------------------------------------------------------------------DETAIL DE DU STAGE---------------------------------------------------------------------------------------//
			
		
		
		stage.getIcons().add(image);
		stage.setScene(vueConnexion);
		stage.setTitle("DataVault - Connexion");
		stage.setResizable(false);
		stage.show();

	} // fin de la methode "start"

	// --------------------------------------------------------------------------------BACK FICHIER BINAIRE--------------------------------------------------------------------------------//

	public static void main(String[] args) {
		
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
		
		System.out.println("Application ON");
		launch();
		
		
	// --------------------------------------------------------------------------------ZONE DE TEST--------------------------------------------------------------------------------//
		
		ArrayList<Stagiaire> tableau = new ArrayList<>();
		ArrayList<Noeud> tableauRecherche = new ArrayList<>();
		RandomAccessFile raf;

		Noeud noeud1 = new Noeud();
		try {

			raf = new RandomAccessFile(CHEMIN_BIN, "rw");
			
			System.out.println("Application OFF");
			
			// toArray
//			tableau = noeud1.toArray(raf);
//			System.out.println(tableau);
//			System.out.println(tableau.get(15));
			// Ajout
//			noeud1.ajouterUnStagiaire("GUILLIN", "Amaury", "91", "CDA 22", "2022", 16, raf);

			// Recherche
//			tableauRecherche = noeud1.rechercherUnStagiaire("GARIJO", "Rosie", "75", "AI 79", "2011", raf);			
//			System.out.println(tableauRecherche);

			// Suppression test
//			noeud1.supprimerStagiaireNoeud(4);

			// Modifier Stagiaire
//			noeud1.modifierUnStagiaire("GARIJO", "Rosie", "75", "AI 79", "2011", raf);

			System.out.println(
					"----------------------------------------------------------------------------------------------");

//			lireUnFichier.lireFichierBinaireEnConsole(CHEMIN_BIN);
////			
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