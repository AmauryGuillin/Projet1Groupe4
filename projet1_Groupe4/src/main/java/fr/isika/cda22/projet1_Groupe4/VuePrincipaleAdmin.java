package fr.isika.cda22.projet1_Groupe4;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class VuePrincipaleAdmin extends Scene {
	
	private Button btnSupprimer;
	private Button btnAjouter;
	private Button btnModifier;
	private MenuItem itemImprimer;
//	private MenuItem itemDeconnexion;
	private MenuItem item1;
//	private MenuItem item2;
	private MenuItem item3;
	private MenuItem item4;
	private MenuItem item5;
	
	
	
	public VuePrincipaleAdmin() {
		
		super(new HBox(), 1280, 720);
		HBox grille = (HBox) this.getRoot();
		
		grille.setPadding(new Insets(10, 10, 10, 10));
		
		//On crée un menu déroulant pour imprimer et se deconnecter
		Menu menu = new Menu("Menu");
		itemImprimer = new MenuItem("Imprimer");
//		itemDeconnexion = new MenuItem("Déconnexion");
		item1 = new MenuItem("Accueil");
//		item2 = new MenuItem("Recherche Avancée");
		item3 = new MenuItem("Ajouter un stagiaire");
		item4 = new MenuItem("Modifier un stagiaire");
		item5 = new MenuItem("Deconnexion");
		
		menu.getItems().add(itemImprimer);
//		menu.getItems().add(itemDeconnexion);
		menu.getItems().add(item1);
//		menu.getItems().add(item2);
		menu.getItems().add(item3);
		menu.getItems().add(item4);
		menu.getItems().add(item5);
		
		MenuBar mb = new MenuBar();
		
		mb.getMenus().add(menu);
		
		
		//On donne un label à notre future table on le met dans une HBox
		Label label = new Label("Liste des stagiaires");
        label.setFont(new Font("Arial", 20));
        
        HBox hboxLabel = new HBox();
        hboxLabel.getChildren().addAll(label);
        hboxLabel.setAlignment(Pos.CENTER);
        
        
        //Création de la table
		TableView<Stagiaire> table = new TableView<Stagiaire>();	
		table.setEditable(true);
		HBox hboxTable = new HBox();
		hboxTable.getChildren().add(table);
		hboxTable.setAlignment(Pos.CENTER);

        
		//Création des colonnes
        
        
        TableColumn<Stagiaire, String> nomCol = new TableColumn<Stagiaire, String>("Nom");
        nomCol.setMinWidth(100);
        nomCol.setStyle( "-fx-alignment: CENTER;");
        //spécifier une préférence de tri pour cette colonne
        //nomCol.setSortType(TableColumn.SortType.ASCENDING);
        //nomCol.setSortType(TableColumn.SortType.DESCENDING);
        
        //Spécifier comment remplir la donnée pour chaque cellule de cette colonne
        //Ceci se fait en specifiant un "cell value factory" pour cette colonne.
        nomCol.setCellValueFactory(
                new PropertyValueFactory<Stagiaire, String>("nom"));
       
        
        TableColumn<Stagiaire, String> prenomCol = 
        		new TableColumn<Stagiaire, String>("Prénom");
        prenomCol.setMinWidth(100);
        prenomCol.setStyle( "-fx-alignment: CENTER;");
        //Spécifier comment remplir la donnée pour chaque cellule de cette colonne
        //Ceci se fait en specifiant un "cell value factory" pour cette colonne.
        prenomCol.setCellValueFactory(
                new PropertyValueFactory<Stagiaire, String>("prenom"));
        
      
        
        TableColumn<Stagiaire, String> departementCol = new TableColumn<Stagiaire, String>("Département");
        departementCol.setMinWidth(100);
        departementCol.setStyle( "-fx-alignment: CENTER;");
        //Spécifier comment remplir la donnée pour chaque cellule de cette colonne
        //Ceci se fait en specifiant un "cell value factory" pour cette colonne.
        departementCol.setCellValueFactory(
                new PropertyValueFactory<Stagiaire, String>("departement"));
        
        
        TableColumn<Stagiaire, String> promoCol = new TableColumn<Stagiaire, String>("Promo");
        promoCol.setMinWidth(100);
        promoCol.setStyle( "-fx-alignment: CENTER;");
        //Spécifier comment remplir la donnée pour chaque cellule de cette colonne
        //Ceci se fait en specifiant un "cell value factory" pour cette colonne.
        promoCol.setCellValueFactory(
                new PropertyValueFactory<Stagiaire, String>("nomPromo"));
        
        
        TableColumn<Stagiaire, String> anneePromoCol = new TableColumn<Stagiaire, String>("Année Promo");
        anneePromoCol.setMinWidth(100);
        anneePromoCol.setStyle( "-fx-alignment: CENTER;");
        //Spécifier comment remplir la donnée pour chaque cellule de cette colonne
        //Ceci se fait en specifiant un "cell value factory" pour cette colonne.
        anneePromoCol.setCellValueFactory(
                new PropertyValueFactory<Stagiaire, String>("dateFormation"));
        
                                
        //On ajoute les colonnes à la table
        table.getColumns().addAll(nomCol, prenomCol, departementCol, promoCol, anneePromoCol);
        
        //On rempli la table avec la liste observable
        table.setItems(getStagiairesList());
     
       
        //On crée un textField de recherche avec et on le range dans une HBox
        
        TextField textFieldRecherche = new TextField();
        HBox hboxRecherche = new HBox(20);
        hboxRecherche.getChildren().addAll(textFieldRecherche);
        hboxRecherche.setAlignment(Pos.CENTER);
        
        textFieldRecherche.setPromptText("Cherchez ici!");
        
        
        //On intancie un objet FilteredList de notre observableList de stagiaire
        FilteredList<Stagiaire> flStagiaires = new FilteredList(getStagiairesList(), p -> true);//Pass the data to a filtered list
        table.setItems(flStagiaires);//Set the table's items using the filtered list
        
        //On modifie notre FilterdList afin qu'il filtre le tableau en fonction des informations entrées dans notre textFieldRecherche; 
        //La methode getAll() définie dans la classe Stagiaires permet de filtrer selon que le mot entré dans le textFieldRecherche soit un nom, un prénom, un département, la promo ou l'annee de promo
        textFieldRecherche.textProperty().addListener((obs, oldValue, newValue) -> {

                	flStagiaires.setPredicate(p -> p.getAll().toLowerCase().contains(newValue.toLowerCase().trim()));                

        });
        
        
        //On crée des bouttons ajouter, modifier et supprimer qu'on place dans un HBox
        HBox hboxBtn = new HBox(20);
        Button btnSupprimer = new Button("Supprimer");
        BorderPane.setMargin(btnSupprimer, new Insets(2,2,2,2));
        Button btnModifier = new Button("Modifier");
        BorderPane.setMargin(btnModifier, new Insets(2,2,2,2));
        Button btnAjouter = new Button("Ajouter");
        BorderPane.setMargin(btnAjouter, new Insets(2,2,2,2));
        hboxBtn.setAlignment(Pos.CENTER);
        hboxBtn.getChildren().addAll(btnAjouter, btnModifier, btnSupprimer);
        
        //On crée 2 VBox la prmiere vbox1 contiendra les Hbox(le label, la HBox recherche, le HBox du tableau et le HBox du bouton Ajouter, modifier et supprimer) la vbox2 contiendra le Menubar
        VBox vbox1 = new VBox(30);
        VBox vbox2 = new VBox();
        
        vbox1.getChildren().addAll(hboxLabel, hboxRecherche, hboxTable, hboxBtn);
        vbox1.setAlignment(Pos.CENTER);
        vbox2.getChildren().addAll(mb);
        vbox2.setAlignment(Pos.TOP_RIGHT);
        vbox2.setPadding(new Insets(2,150,2,2));
        
        //A la grille on affecte les 2 VBox
        grille.getChildren().addAll(vbox2, vbox1);
        grille.setAlignment(Pos.CENTER_LEFT);
        

	}
	

	public Button getBtnSupprimer() {
		return btnSupprimer;
	}

	public Button getBtnAjouter() {
		return btnAjouter;
	}

	public Button getBtnModifier() {
		return btnModifier;
	}

	public MenuItem getItemImprimer() {
		return itemImprimer;
	}
//
//	public MenuItem getItemDeconnexion() {
//		return itemDeconnexion;
//	}
	
	

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


	private ObservableList<Stagiaire> getStagiairesList() {

		Stagiaire stagiaire1 = new Stagiaire("Bleriot", "Louis", "94", "CDA22", "2022");
		Stagiaire stagiaire2 = new Stagiaire("Jeandes", "Bernard", "85", "CDA41", "2012");
		Stagiaire stagiaire3 = new Stagiaire("Goodesa", "Marie", "85", "CDA98", "2006");
		Stagiaire stagiaire4 = new Stagiaire("Goodesa", "Marie", "78", "CDA98", "2006");
		ObservableList<Stagiaire> list = FXCollections.observableArrayList(stagiaire1, stagiaire2, stagiaire3, stagiaire4);
		return list;
	}

}
