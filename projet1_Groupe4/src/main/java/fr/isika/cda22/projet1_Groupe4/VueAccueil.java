package fr.isika.cda22.projet1_Groupe4;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import java.time.LocalDate;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.geometry.Orientation;    
import javafx.scene.control.ScrollBar;  


public class VueAccueil extends Scene {
	
	private MenuItem item1;
//	private MenuItem item2;
	private MenuItem item3;
	private MenuItem item4;
	private MenuItem item5;
	
public VueAccueil() {
	
	super(new VBox(), 1280, 720);
	
	VBox grille = (VBox) this.getRoot();
	
		
		VueStagiaireModifierAdmin vueStagiaireModifierAdmin = new VueStagiaireModifierAdmin();
		VueStagiaireAjouterAdmin vueStagiaireAjouterAdmin = new VueStagiaireAjouterAdmin();
		VueConnexion vueConnexion = new VueConnexion();
//		Recherche2 recherche2 = new Recherche2();
		
		
		
		Menu menu = new Menu("Portail de navigation");
		
		
	      
		
		 item1 = new MenuItem("Accueil");
//		 item2 = new MenuItem("Recherche Avancée");
		 item3 = new MenuItem("Ajouter un stagiaire");
		 item4 = new MenuItem("Modifier un stagiaire");
		 item5 = new MenuItem("Deconnexion");
	
		menu.getItems().add(item1);
//		menu.getItems().add(item2);
		menu.getItems().add(item3);
		menu.getItems().add(item4);
		menu.getItems().add(item5);
		
		
		MenuBar mb = new MenuBar();
		
		mb.getMenus().add(menu);
		
		

		
	   
		
		Label labelTitre = new Label("Interface de modification des Stagiaires");
		labelTitre.setFont(Font.font("Roboto", 20));
		
		Label labelNom = new Label("Nom :");
		labelNom.setFont(Font.font("Roboto", FontPosture.ITALIC, 12));
		
		Label labelPrenom = new Label("Prenom :");
		labelPrenom.setFont(Font.font("Roboto", FontPosture.ITALIC, 12));
		
		Label labelDepartement = new Label("Departement :");
		labelDepartement.setFont(Font.font("Roboto", FontPosture.ITALIC, 12));
		
		Label labelPromo = new Label("Promo :");
		labelPromo.setFont(Font.font("Roboto", FontPosture.ITALIC, 12));
		
		Label labelAnneePromo = new Label("Année Promo :");
		labelAnneePromo.setFont(Font.font("Roboto", FontPosture.ITALIC, 12));
		
		TextField textFieldNom = new TextField("");
		textFieldNom.setPromptText("Exemple : MARTIN");
		
		TextField textFieldPrenom = new TextField("");
		textFieldPrenom.setPromptText("Exemple : Jacques");
		
		TextField textFieldDepartement = new TextField("");
		textFieldDepartement.setPromptText("Exemple : 01");
		
		TextField textFieldPromo= new TextField("");
		textFieldPromo.setPromptText("Exemple : CDA 22");
		
		TextField textFieldAnneePromo = new TextField("");
		textFieldAnneePromo.setPromptText("Exemple : 2022");
		
		
		Button btnValider = new Button("Modifier");
		
		HBox hboxTitre = new HBox();
		hboxTitre.setAlignment(Pos.CENTER);
		
		HBox hboxNom = new HBox();
		hboxNom.setAlignment(Pos.CENTER);
		
		HBox hboxPrenom = new HBox();
		hboxPrenom.setAlignment(Pos.CENTER);
		
		
		HBox hboxDepartement = new HBox();
		hboxDepartement.setAlignment(Pos.CENTER);
		
		HBox hboxPromo = new HBox();
		hboxPromo.setAlignment(Pos.CENTER);
		
		HBox hboxAnneePromo = new HBox();
		hboxAnneePromo.setAlignment(Pos.CENTER);
		
		HBox hboxBtnModifier = new HBox();
		hboxBtnModifier.setAlignment(Pos.CENTER);
		
		
		hboxTitre.getChildren().addAll(labelTitre);
		//hboxNom.setPadding(new Insets(20, 20, 20, 60));
		hboxNom.getChildren().addAll(labelNom, textFieldNom);
		hboxNom.setPadding(new Insets(20, 20, 20, 60));
		hboxPrenom.getChildren().addAll(labelPrenom, textFieldPrenom);
		hboxPrenom.setPadding(new Insets(20, 20, 20, 60));
		hboxDepartement.getChildren().addAll(labelDepartement, textFieldDepartement);
		hboxDepartement.setPadding(new Insets(20, 20, 20, 60));
		hboxPromo.getChildren().addAll(labelPromo, textFieldPromo);
		hboxPromo.setPadding(new Insets(20, 20, 20, 60));
		hboxAnneePromo.getChildren().addAll(labelAnneePromo, textFieldAnneePromo);
		hboxAnneePromo.setPadding(new Insets(20, 20, 20, 60));
		hboxBtnModifier.getChildren().add(btnValider);
		hboxBtnModifier.setPadding(new Insets(20, 20, 20, 60));

		grille.getChildren().addAll(mb, hboxTitre, hboxNom, hboxPrenom, hboxDepartement, hboxPromo, hboxAnneePromo, hboxBtnModifier);
		
		grille.setPadding(new Insets(10, 10, 10, 10));

		
		
	}

public MenuItem getItem1() {
	return item1;
}

public void setItem1(MenuItem item1) {
	this.item1 = item1;
}

public MenuItem getItem3() {
	return item3;
}

public void setItem3(MenuItem item3) {
	this.item3 = item3;
}

public MenuItem getItem4() {
	return item4;
}

public void setItem4(MenuItem item4) {
	this.item4 = item4;
}

public MenuItem getItem5() {
	return item5;
}

public void setItem5(MenuItem item5) {
	this.item5 = item5;
}

}
