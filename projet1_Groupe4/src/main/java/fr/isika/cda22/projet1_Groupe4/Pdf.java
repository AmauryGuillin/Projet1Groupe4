package fr.isika.cda22.projet1_Groupe4;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;





public class Pdf implements Constantes {
	
		//Identification du chemin de création du pdf à imprimer
	    public static String FILE = "D:\\Formation Ingé informatique\\TESTpdf.pdf";
	    public static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
	            Font.BOLD);
	    
	    public static Font catFont2 = new Font(Font.FontFamily.TIMES_ROMAN, 10,
	            Font.BOLD);


	    public static void main(String[] args) {
	    	
	    	//Recupération + écriture dans le fichier pdf
	        try {
	            Document document = new Document();
	            PdfWriter.getInstance(document, new FileOutputStream(FILE));
	            document.open();
	            ajouterContenu(document);
	            document.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }



	    //Methode qui pemet d'écrire des stagiaires dans le fichier pdf
	    public static void ajouterContenu(Document document) throws DocumentException {
	    	Paragraph preface = new Paragraph();
	    	addEmptyLine(preface, 1);
	    	preface.add(new Paragraph(20,"LISTE DES STAGIAIRES", catFont));
	    	preface.setIndentationLeft(140);
	        addEmptyLine(preface, 3);
	        
	        
	        document.add(preface);
	        
	        
	        addEmptyLine(preface, 2);
	    	

	        
	        PdfPTable table = new PdfPTable(5);

	        PdfPCell cellNom = new PdfPCell(new Phrase("Nom"));
	        cellNom.setHorizontalAlignment(Element.ALIGN_CENTER);
	        table.addCell(cellNom);

	        PdfPCell cellPrénom = new PdfPCell(new Phrase("Prénom"));
	        cellPrénom.setHorizontalAlignment(Element.ALIGN_CENTER);
	        table.addCell(cellPrénom);

	        PdfPCell cellDépartement = new PdfPCell(new Phrase("Département"));
	        cellDépartement.setHorizontalAlignment(Element.ALIGN_CENTER);
	        table.addCell(cellDépartement);
	        
	        PdfPCell cellPromo = new PdfPCell(new Phrase("Promo"));
	        cellPromo.setHorizontalAlignment(Element.ALIGN_CENTER);
	        table.addCell(cellPromo);
	        
	        PdfPCell cellAnneePromo = new PdfPCell(new Phrase("Année Promo"));
	        cellAnneePromo.setHorizontalAlignment(Element.ALIGN_CENTER);
	        table.addCell(cellAnneePromo);
	        
	        table.setHeaderRows(1);
	        
	        RandomAccessFile raf;
			Noeud noeud = new Noeud();
			ArrayList<Stagiaire> tableau = new ArrayList<>();

			try {
				raf = new RandomAccessFile(CHEMIN_BIN, "rw");
				tableau = noeud.toArray(raf);
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}


	        
	        for (int i = 0; i < tableau.size(); i++) {
	        	
	        	PdfPCell cellule1 = new PdfPCell(new Phrase(tableau.get(i).getNom(), catFont2));
	        	table.addCell(cellule1);
	        	
	        	PdfPCell cellule2 = new PdfPCell(new Phrase(tableau.get(i).getPrenom(), catFont2));
	        	table.addCell(cellule2);
	        	
	        	PdfPCell cellule3 = new PdfPCell(new Phrase(tableau.get(i).getDepartement(), catFont2));
	        	table.addCell(cellule3);
	        	
	        	PdfPCell cellule4 = new PdfPCell(new Phrase(tableau.get(i).getNomPromo(), catFont2));
	        	table.addCell(cellule4);
	        	
	        	PdfPCell cellule5 = new PdfPCell(new Phrase(tableau.get(i).getDateFormation(), catFont2));
	        	table.addCell(cellule5);

	        	
	        }

	        table.setHorizontalAlignment(Element.ALIGN_CENTER);
	        document.add(table);
	        
	        


	    }
	    
	    private static void addEmptyLine(Paragraph paragraph, int number) {
	        for (int i = 0; i < number; i++) {
	            paragraph.add(new Paragraph(" "));
	        }
	    }

}
