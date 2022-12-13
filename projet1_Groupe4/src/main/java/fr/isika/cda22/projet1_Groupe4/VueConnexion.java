package fr.isika.cda22.projet1_Groupe4;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Window;

public class VueConnexion extends Scene implements EffacerActions {
	
	private Button btn_connexion;
	private Button btn_inscription;
	private TextField username;
	private PasswordField password;

	
// ---------------------------------------LES ESSENTIELS---------------------------------------
	public VueConnexion() {
		super(new GridPane(), 1280, 720);
		
		GridPane root = (GridPane) this.getRoot();// organise les composants de la fenetre
		
// ---------------------------------------LES ESSENTIELS---------------------------------------
		
		
		root.setPadding(new Insets(20));
		root.setHgap(25);// espace entre label et champs remplissage (horizontal)
		root.setVgap(15);// espace entre chaque label(vertical)
		root.setAlignment(Pos.CENTER);

		this.setRoot(root);

		Label label = new Label("Connexion");
		label.setFont(new Font("Arial Black", 20));

		username = new TextField();
		username.setPromptText("Entrez votre nom d'utilisateur");
		username.setMinWidth(500);

		password = new PasswordField();
		password.setPromptText("Entrez votre mot de passe");
		


		HBox hb = new HBox();
		hb.getChildren().add(label);

		root.add(hb, 1, 0);
		hb.setAlignment(Pos.TOP_CENTER);

		root.add(username, 1, 1);

		root.add(password, 1, 2);


		root.add(createButtunsValSuppr(), 1, 7);

		
	}
	
	// Methodes spécifiques

		public HBox createButtunsValSuppr() {
			btn_connexion = new Button("Connexion");
			btn_connexion.setMinSize(100, 35);
			btn_inscription = new Button("Inscription");
			btn_inscription.setMinSize(100, 35);

			HBox hb3 = new HBox();
			hb3.getChildren().addAll(btn_connexion, btn_inscription);
			hb3.setSpacing(50);
			hb3.setAlignment(Pos.CENTER);

			return hb3;
		}
		
		 void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
			Alert alert = new Alert(alertType);
			alert.setTitle(title);
			alert.setHeaderText(null);
			alert.setContentText(message);
			alert.initOwner(owner);
			alert.show();
		}
		
		public void ajouterLabel(String nomPrenom) {
			Label label = new Label(nomPrenom);
			((GridPane) this.getRoot()).getChildren().add(label);
			
		}

		public void eraseActionsdAfterUsage() {
			username.clear();
			password.clear();
		}

		public Button getBtn_connexion() {
			return btn_connexion;
		}

		public void setBtn_connexion(Button btn_connexion) {
			this.btn_connexion = btn_connexion;
		}

		public Button getBtn_inscription() {
			return btn_inscription;
		}

		public void setBtn_inscription(Button btn_inscription) {
			this.btn_inscription = btn_inscription;
		}

		public TextField getUsername() {
			return username;
		}

		public void setUsername(PasswordField username) {
			this.username = username;
		}

		public TextField getPassword() {
			return password;
		}

		public void setPassword(PasswordField password) {
			this.password = password;
		}



		
	
	

}
