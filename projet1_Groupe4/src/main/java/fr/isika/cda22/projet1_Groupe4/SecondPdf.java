package fr.isika.cda22.projet1_Groupe4;

import java.io.FileOutputStream;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class SecondPdf {
    public static String FILE = "C:\\Users\\Admin\\Desktop\\Mes dossiers\\ISIKA\\Cours ISIKA\\Vincent.pdf";
    public static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);


    public static void main(String[] args) {
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

        table.addCell("Lacroix");
        table.addCell("Pascale");
        table.addCell("xxx");
        table.addCell("xxxx");
        table.addCell("xxxx");
        
        table.addCell("Chaveneau");
        table.addCell("Claire");
        table.addCell("xxx");
        table.addCell("xxxx");
        table.addCell("xxxx");
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        document.add(table);
        
        


    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}