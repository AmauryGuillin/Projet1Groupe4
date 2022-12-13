package fr.isika.cda22.projet1_Groupe4;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class VueTEST extends Scene{
	
	public VueTEST() {
		
		super(new StackPane(), 1125, 620);
		
		StackPane root = (StackPane) this.getRoot();
		
		Label label = new Label("FENETRE DE TEST");
		
		root.getChildren().add(label);
		
		
	}

}
