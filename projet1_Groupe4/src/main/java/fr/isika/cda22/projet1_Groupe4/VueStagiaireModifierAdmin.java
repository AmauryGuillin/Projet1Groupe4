package fr.isika.cda22.projet1_Groupe4;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;
import javafx.geometry.Orientation;    
import javafx.scene.control.ScrollBar;  

public class VueStagiaireModifierAdmin extends Scene implements EffacerActions{
	
	//Attributs
	private MenuItem item1;
//	private MenuItem item2;
	private MenuItem item3;
	private MenuItem item4;
	private MenuItem item5;
	private Button btnValider;
	private TextField textFieldNom;
	private TextField textFieldPrenom;
	private TextField textFieldDepartement;
	private TextField textFieldPromo;
	private TextField textFieldAnneePromo;
	
	//Constructeur
	public VueStagiaireModifierAdmin() {
		super(new VBox(), 1280, 720);
		VBox grille = (VBox)this.getRoot();
		
//		VueStagiaireAjouterAdmin vueStagiaireAjouterAdmin = new VueStagiaireAjouterAdmin();
//		VueConnexion vueConnexion = new VueConnexion();
//		Recherche2 recherche2 = new Recherche2();
		
		
		// BackGround image, hébergée sur un serveur distant
		grille.setStyle("-fx-background-image: url('https://i.goopics.net/whix8k.jpg');"
				+ "-fx-background-repeat: stretch;" + "-fx-background-size: 1280 720;" +
		        "-fx-background-position: center center;" );
		
		
		//Construction du menu
        Menu menu = new Menu("Menu");
	
		 item1 = new MenuItem("Accueil");
//		 item2 = new MenuItem("Recherche Avancée");
//		 item3 = new MenuItem("Ajouter un stagiaire");
//		 item4 = new MenuItem("Modifier un stagiaire");
		 item5 = new MenuItem("Deconnexion");
	
		menu.getItems().add(item1);
//		menu.getItems().add(item2);
//		menu.getItems().add(item3);
//		menu.getItems().add(item4);
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
		
		textFieldNom = new TextField("");
		textFieldNom.setPromptText("Exemple : MARTIN");

		textFieldPrenom = new TextField("");
		textFieldPrenom.setPromptText("Exemple : Jacques");

		textFieldDepartement = new TextField("");
		textFieldDepartement.setPromptText("Exemple : 01");

		textFieldPromo = new TextField("");
		textFieldPromo.setPromptText("Exemple : CDA 22");

		textFieldAnneePromo = new TextField("");
		textFieldAnneePromo.setPromptText("Exemple : 2022");
		
		btnValider = new Button("Valider");
		
		HBox hboxTitre = new HBox();
		hboxTitre.setAlignment(Pos.CENTER);
		hboxTitre.setPadding(new Insets(55));
		HBox hboxNom = new HBox(70);
		hboxNom.setAlignment(Pos.CENTER);
		HBox hboxPrenom = new HBox(55);
		hboxPrenom.setAlignment(Pos.CENTER);
		HBox hboxDepartement = new HBox(25);
		hboxDepartement.setAlignment(Pos.CENTER);
		HBox hboxPromo = new HBox(60);
		hboxPromo.setAlignment(Pos.CENTER);
		HBox hboxAnneePromo = new HBox(25);
		hboxAnneePromo.setAlignment(Pos.CENTER);
		HBox hboxBtnValider = new HBox();
		hboxBtnValider.setAlignment(Pos.CENTER);
		
		hboxTitre.getChildren().addAll(labelTitre);
		hboxNom.setPadding(new Insets(10, 10, 10, 60));
		hboxNom.getChildren().addAll(labelNom, textFieldNom);
		hboxNom.setPadding(new Insets(10, 10, 10, 60));
		hboxPrenom.getChildren().addAll(labelPrenom, textFieldPrenom);
		hboxPrenom.setPadding(new Insets(10, 10, 10, 60));
		hboxDepartement.getChildren().addAll(labelDepartement, textFieldDepartement);
		hboxDepartement.setPadding(new Insets(10, 10, 10, 60));
		hboxPromo.getChildren().addAll(labelPromo, textFieldPromo);
		hboxPromo.setPadding(new Insets(10, 10, 10, 60));
		hboxAnneePromo.getChildren().addAll(labelAnneePromo, textFieldAnneePromo);
		hboxAnneePromo.setPadding(new Insets(10, 10, 10, 60));
		hboxBtnValider.getChildren().add(btnValider);
		hboxBtnValider.setPadding(new Insets(10, 10, 10, 60));
		
		grille.getChildren().addAll(mb, hboxTitre, hboxNom, hboxPrenom, hboxDepartement, hboxPromo, hboxAnneePromo, hboxBtnValider);
	
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

	public Button getBtnValider() {
		return btnValider;
	}

	public void setBtnValider(Button btnValider) {
		this.btnValider = btnValider;
	}

	public TextField getTextFieldNom() {
		return textFieldNom;
	}

	public void setTextFieldNom(TextField textFieldNom) {
		this.textFieldNom = textFieldNom;
	}

	public TextField getTextFieldPrenom() {
		return textFieldPrenom;
	}

	public void setTextFieldPrenom(TextField textFieldPrenom) {
		this.textFieldPrenom = textFieldPrenom;
	}

	public TextField getTextFieldDepartement() {
		return textFieldDepartement;
	}

	public void setTextFieldDepartement(TextField textFieldDepartement) {
		this.textFieldDepartement = textFieldDepartement;
	}

	public TextField getTextFieldPromo() {
		return textFieldPromo;
	}

	public void setTextFieldPromo(TextField textFieldPromo) {
		this.textFieldPromo = textFieldPromo;
	}

	public TextField getTextFieldAnneePromo() {
		return textFieldAnneePromo;
	}

	public void setTextFieldAnneePromo(TextField textFieldAnneePromo) {
		this.textFieldAnneePromo = textFieldAnneePromo;
	}




	@Override
	public void eraseActionsdAfterUsage() {
		textFieldNom.clear();
		textFieldPrenom.clear();
		textFieldDepartement.clear();
		textFieldPromo.clear();
		textFieldAnneePromo.clear();
	}
	
}