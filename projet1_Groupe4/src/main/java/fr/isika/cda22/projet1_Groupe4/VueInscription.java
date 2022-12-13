package fr.isika.cda22.projet1_Groupe4;

import java.io.FileWriter;
import java.io.IOException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class VueInscription extends Scene implements EffacerActions {

	private Button btn_inscrire;
	private TextField nom;
	private TextField prenom;
	private TextField username;
	private PasswordField password;

	public VueInscription() {
		super(new GridPane(), 1280, 720);

		GridPane root = (GridPane) this.getRoot();// organise les composants de la fenetre
		root.setPadding(new Insets(20)); //
		root.setHgap(25);// espace entre label et champs remplissage (horizontal)
		root.setVgap(15);// espace entre chaque label(vertical)
		root.setAlignment(Pos.CENTER);

		this.setRoot(root);

		Label label = new Label("Inscription");
		label.setFont(new Font("Arial Black", 20));
		
		nom = new TextField();
		nom.setPromptText("Entrez votre nom");
		
		prenom = new TextField();
		prenom.setPromptText("Entrez votre prenom");

		username = new TextField();
		username.setPromptText("Entrez votre nom d'utilisateur");
		username.setMinWidth(500);

		password = new PasswordField();
		password.setPromptText("Entrez votre mot de passe");

		HBox hb = new HBox();
		hb.getChildren().add(label);

		root.add(hb, 1, 0);
		hb.setAlignment(Pos.TOP_CENTER);
		
		root.add(nom, 1, 1);
		
		root.add(prenom, 1,	2);

		root.add(username, 1, 3);

		root.add(password, 1, 4);

		root.add(createButtunsValider(), 1, 7);

	}
	
	public HBox createButtunsValider() {
		btn_inscrire = new Button("S'inscrire");
		btn_inscrire.setMinSize(100, 35);

		HBox hb3 = new HBox();
		hb3.getChildren().addAll(btn_inscrire);
		hb3.setSpacing(50);
		hb3.setAlignment(Pos.CENTER);

		return hb3;
	}
	

	@Override
	public void eraseActionsdAfterUsage() {
		username.clear();
		password.clear();
	}

	public Button getBtn_inscrire() {
		return btn_inscrire;
	}

	public void setBtn_inscrire(Button btn_inscrire) {
		this.btn_inscrire = btn_inscrire;
	}

	public TextField getNom() {
		return nom;
	}

	public void setNom(TextField nom) {
		this.nom = nom;
	}

	public TextField getPrenom() {
		return prenom;
	}

	public void setPrenom(TextField prenom) {
		this.prenom = prenom;
	}

	public TextField getUsername() {
		return username;
	}

	public void setUsername(TextField username) {
		this.username = username;
	}

	public PasswordField getPassword() {
		return password;
	}

	public void setPassword(PasswordField password) {
		this.password = password;
	}
	
	

}
