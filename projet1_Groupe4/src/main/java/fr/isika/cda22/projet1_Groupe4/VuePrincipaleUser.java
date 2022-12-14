package fr.isika.cda22.projet1_Groupe4;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class VuePrincipaleUser extends Scene implements Constantes {
		
	//Attributs
	private Button btnAjouter;
	private MenuItem itemImprimer;
	private MenuItem itemDeconnexion;
	private MenuItem item1;
//	private MenuItem item2;
	private MenuItem item3;
	private MenuItem item4;
	private MenuItem item5;
	TableView<Stagiaire> table;

	// Constructeur
	public VuePrincipaleUser() {
		super(new VBox(), 1280, 720);
		VBox grille = (VBox) this.getRoot();

//		grille.setPadding(new Insets(10, 10, 10, 10));

		// BackGround image, hébergée sur un serveur distant
		grille.setStyle("-fx-background-image: url('https://i.goopics.net/yvsuuk.jpg');" + "-fx-background-repeat: stretch;"
						+ "-fx-background-size: 1280 720;" + "-fx-background-position: center center;");

		//On crée un menu déroulant pour imprimer et se deconnecter
		Menu menu = new Menu("Menu");
		itemImprimer = new MenuItem("Imprimer");
//		itemDeconnexion = new MenuItem("Déconnexion");
//		item1 = new MenuItem("Accueil");
//		item2 = new MenuItem("Recherche Avancée");
//		item3 = new MenuItem("Ajouter un stagiaire");
//		item4 = new MenuItem("Modifier un stagiaire");
		item5 = new MenuItem("Deconnexion");
		
		menu.getItems().add(itemImprimer);
//		menu.getItems().add(itemDeconnexion);
//		menu.getItems().add(item1);
//		menu.getItems().add(item2);
//		menu.getItems().add(item3);
//		menu.getItems().add(item4);
		menu.getItems().add(item5);
		
		MenuBar mb = new MenuBar();
		
		mb.getMenus().add(menu);
		
		
		//On donne un label à notre future table on le met dans une HBox
		Label label = new Label("Liste des stagiaires");
        label.setFont(new Font("Arial", 20));
        
        HBox hboxLabel = new HBox(250);
        hboxLabel.getChildren().addAll(label);
        hboxLabel.setAlignment(Pos.CENTER);
        
        
        
        //Création de la table
		table = new TableView<Stagiaire>();	
		table.setEditable(true);
		HBox hboxTable = new HBox();
		hboxTable.getChildren().add(table);
		hboxTable.setAlignment(Pos.CENTER);
		
		
        
      //Création des trois colonnes
        
        
        TableColumn<Stagiaire, String> nomCol = new TableColumn<Stagiaire, String>("Nom");
        nomCol.setMinWidth(200);
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
        prenomCol.setMinWidth(200);
        prenomCol.setStyle( "-fx-alignment: CENTER;");
        //Spécifier comment remplir la donnée pour chaque cellule de cette colonne
        //Ceci se fait en specifiant un "cell value factory" pour cette colonne.
        prenomCol.setCellValueFactory(
                new PropertyValueFactory<Stagiaire, String>("prenom"));
        
      
        
        TableColumn<Stagiaire, String> departementCol = new TableColumn<Stagiaire, String>("Département");
        departementCol.setMinWidth(200);
        departementCol.setStyle( "-fx-alignment: CENTER;");
        //Spécifier comment remplir la donnée pour chaque cellule de cette colonne
        //Ceci se fait en specifiant un "cell value factory" pour cette colonne.
        departementCol.setCellValueFactory(
                new PropertyValueFactory<Stagiaire, String>("departement"));
        
        
        TableColumn<Stagiaire, String> promoCol = new TableColumn<Stagiaire, String>("Promo");
        promoCol.setMinWidth(200);
        promoCol.setStyle( "-fx-alignment: CENTER;");
        //Spécifier comment remplir la donnée pour chaque cellule de cette colonne
        //Ceci se fait en specifiant un "cell value factory" pour cette colonne.
        promoCol.setCellValueFactory(
                new PropertyValueFactory<Stagiaire, String>("nomPromo"));
        
        
        TableColumn<Stagiaire, String> anneePromoCol = new TableColumn<Stagiaire, String>("Année Promo");
        anneePromoCol.setMinWidth(200);
        anneePromoCol.setStyle( "-fx-alignment: CENTER;");
        //Spécifier comment remplir la donnée pour chaque cellule de cette colonne
        //Ceci se fait en specifiant un "cell value factory" pour cette colonne.
        anneePromoCol.setCellValueFactory(
                new PropertyValueFactory<Stagiaire, String>("dateFormation"));
        
                                
        //On ajoute les trois colonnes à la table
        table.getColumns().addAll(nomCol, prenomCol, departementCol, promoCol, anneePromoCol);
        
        //On rempli la table avec la liste observable
        table.setItems(getStagiairesList());
        
        
        //On crée un choicebox de recherche multi critère et un textField de recherche avec et on les range dans une HBox
        
        TextField textFieldRecherche = new TextField();
        textFieldRecherche.setMinWidth(300);
          
        HBox hboxRecherche = new HBox(20);
        hboxRecherche.getChildren().addAll(textFieldRecherche);
        hboxRecherche.setAlignment(Pos.CENTER);
        hboxRecherche.setMinWidth(800);
        
        textFieldRecherche.setPromptText("Cherchez ici!");
        
      //On intancie un objet FilteredList de notre observableList de stagiaire
        FilteredList<Stagiaire> flStagiaires = new FilteredList<Stagiaire>(getStagiairesList(), p -> true);//Pass the data to a filtered list
        table.setItems(flStagiaires);//Set the table's items using the filtered list
        
        //On modifie notre FilterdList afin qu'il filtre le tableau en fonction des informations entrées dans notre textFieldRecherche; 
        //La methode getAll() définie dans la classe Stagiaires permet de filtrer selon que le mot entré dans le textFieldRecherche soit un nom, un prénom, un département,  la promo ou l'annee de promo
        textFieldRecherche.textProperty().addListener((obs, oldValue, newValue) -> {

                	flStagiaires.setPredicate(p -> p.getAll().toLowerCase().contains(newValue.toLowerCase().trim()));                

        });
      
  
        //On crée le bouton Ajouter que l'on place dans une HBox
        btnAjouter = new Button("Ajouter");
        BorderPane.setMargin(btnAjouter, new Insets(2,2,2,2));
    
        HBox hboxBtn = new HBox(20);
        hboxBtn.setAlignment(Pos.CENTER);
        hboxBtn.getChildren().add(btnAjouter);
        
        //On crée 2 VBox la prmiere vbox1 contiendra les Hbox(le label, la HBox recherche, le HBox du tableau et le HBox du bouton Ajouter) la vbox2 contiendra la barre de menu 
        VBox vbox1 = new VBox(30);
//        VBox vbox2 = new VBox();
        
        vbox1.getChildren().addAll(mb, hboxLabel, hboxRecherche, hboxTable, hboxBtn);
        vbox1.setAlignment(Pos.CENTER);
        
        
//        vbox2.getChildren().addAll(mb);
//        vbox2.setAlignment(Pos.TOP_LEFT);
//        vbox2.setPadding(new Insets(2,150,2,2));
        
        //A la grille on affecte les 2 VBox
        grille.getChildren().addAll(vbox1);
        grille.setAlignment(Pos.TOP_CENTER);
		

	}

	//Methode qui permet de lier le fichier binaire à l'affichage de la TableVieuw
	public ObservableList<Stagiaire> getStagiairesList() {
		RandomAccessFile raf;
		Noeud noeud = new Noeud();
		ObservableList<Stagiaire> list = null;
		ArrayList<Stagiaire> tableau = new ArrayList<>();

		try {
			raf = new RandomAccessFile(CHEMIN_BIN, "rw");
			tableau = noeud.toArray(raf);

			list = FXCollections.observableArrayList(tableau);
		
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}


	//Getters & Setters
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

	public void setBtnAjouter(Button btnAjouter) {
		this.btnAjouter = btnAjouter;
	}

	public TableView<Stagiaire> getTable() {
		return table;
	}

	public void setTable(TableView<Stagiaire> table) {
		this.table = table;
	}

	public void setItemImprimer(MenuItem itemImprimer) {
		this.itemImprimer = itemImprimer;
	}

	public void setItemDeconnexion(MenuItem itemDeconnexion) {
		this.itemDeconnexion = itemDeconnexion;
	}

	public Button getBtnAjouter() {
		return btnAjouter;
	}


	public MenuItem getItemImprimer() {
		return itemImprimer;
	}

	public MenuItem getItemDeconnexion() {
		return itemDeconnexion;
	}

}

