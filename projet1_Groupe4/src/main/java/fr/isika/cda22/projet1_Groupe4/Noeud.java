package fr.isika.cda22.projet1_Groupe4;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Noeud implements Constantes {

	// Attributs
	private Stagiaire cle;
	private int indexfilsGauche = -1;
	private int indexfilsDroit = -1;
	private int indexdoublon = -1;

	// Constructeur
	public Noeud(Stagiaire cle, int indexFilsGauche, int indexFilsDroit, int indexDoublon) {
		super();
		this.cle = cle;
		this.indexfilsGauche = indexFilsGauche;
		this.indexfilsDroit = indexFilsDroit;
		this.indexdoublon = indexDoublon;
	}

	public void ajouterStagiaireBinaire(Noeud noeudAjout, RandomAccessFile raf) { // OK

		try {
			if (raf.length() == 0) { // Si le fichier binaire est vide, alors on écrit le stagiaire
				ecrireNoeudBin(noeudAjout, raf);
			} else {
				raf.seek(TAILLE_NOEUD_OCTET);
				ecritureBinReccursive(noeudAjout, raf, 0);
			}

		} catch (IOException e) {
			System.out.println("ERREUR - FICHIER INTROUVALE");
			e.printStackTrace();
		}

	}

	public void ecritureBinReccursive(Noeud noeudAjout, RandomAccessFile raf, int index) {

		try {
			int indexNoeud = (int) (raf.length() / TAILLE_NOEUD_OCTET);
			int indexParent = index;
			if (noeudAjout.getCle().getNomLong().compareTo(recupereNomIndex(indexParent, raf)) < 0) { // on part a
																										// gauche

				if (recupererIndexFilsGauche(indexParent, raf) != -1) { // il y a un fils gauche
					indexParent = recupererIndexFilsGauche(indexParent, raf);
					ecritureBinReccursive(noeudAjout, raf, indexParent); // on passe la methode au fils gauche
				} else { // il n'y a pas de fils gauche
					ecrireFilsGauche(indexParent, raf, indexNoeud);
					ecrireNoeudBin(noeudAjout, raf);
				}
			} else if (noeudAjout.getCle().getNomLong().compareTo(recupereNomIndex(indexParent, raf)) > 0) { // on part
																												// a
																												// Droite

				if (recupererIndexFilsDroit(indexParent, raf) != -1) { // il y a un fils Droit
					indexParent = recupererIndexFilsDroit(indexParent, raf);
					ecritureBinReccursive(noeudAjout, raf, indexParent); // on passe la methode au fils Droit
				} else { // il n'y a pas de fils Droit
					ecrireFilsDroit(indexParent, raf, indexNoeud);
					ecrireNoeudBin(noeudAjout, raf);
				}
			} else if (noeudAjout.getCle().getNomLong().compareTo(recupereNomIndex(indexParent, raf)) == 0) {
				if (recupererIndexDoublon(indexParent, raf) != -1) {
					indexParent = recupererIndexDoublon(indexParent, raf);
					ecritureBinReccursive(noeudAjout, raf, indexParent);
				} else {
					ecrireDoublon(indexParent, raf, indexNoeud);
					ecrireNoeudBin(noeudAjout, raf);
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

	}

	public void ecrireNoeudBin(Noeud noeud, RandomAccessFile raf) { // OK
		try {

			raf.seek(raf.length());
			recuperationAttributsStagiaireBin(noeud.getCle(), raf);
			raf.writeInt(noeud.getIndexfilsGauche());
			raf.writeInt(noeud.getIndexfilsDroit());
			raf.writeInt(noeud.getIndexDoublon());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void ecrireFilsGauche(int indexParent, RandomAccessFile raf, int indexNoeud) {
		try {
			raf.seek((indexParent * TAILLE_NOEUD_OCTET) + INDEX_FILS_GAUCHE_OCTET);
			raf.writeInt(indexNoeud);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void ecrireFilsDroit(int indexParent, RandomAccessFile raf, int indexNoeud) {
		try {
			raf.seek((indexParent * TAILLE_NOEUD_OCTET) + INDEX_FILS_DROIT_OCTET);
			raf.writeInt(indexNoeud);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void ecrireDoublon(int indexParent, RandomAccessFile raf, int indexNoeud) {
		try {
			raf.seek((indexParent * TAILLE_NOEUD_OCTET) + INDEX_DOUBLON_OCTET);
			raf.writeInt(indexNoeud);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private int recupererIndexFilsGauche(int indexNoeud, RandomAccessFile raf) {
		int indexParent = 0;
		try {
			raf.seek((indexNoeud * TAILLE_NOEUD_OCTET) + INDEX_FILS_GAUCHE_OCTET);
			indexParent += raf.readInt();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return indexParent;
	}

	private int recupererIndexFilsDroit(int indexNoeud, RandomAccessFile raf) {
		int indexParent = 0;
		try {
			raf.seek((indexNoeud * TAILLE_NOEUD_OCTET) + INDEX_FILS_DROIT_OCTET);
			indexParent += raf.readInt();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return indexParent;
	}

	private int recupererIndexDoublon(int indexNoeud, RandomAccessFile raf) {
		int indexParent = 0;
		try {
			raf.seek((indexNoeud * TAILLE_NOEUD_OCTET) + INDEX_DOUBLON_OCTET);
			indexParent += raf.readInt();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return indexParent;
	}

	public String recupereNomIndex(int indexParent, RandomAccessFile raf) {
		String nomRecup = "";
		try {
			raf.seek(indexParent * TAILLE_NOEUD_OCTET);
			for (int k = 0; k < TAILLE_MAX_NOM; k++) {
				nomRecup += raf.readChar();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return nomRecup;
	}

	public void recuperationAttributsStagiaireBin(Stagiaire stagiaire, RandomAccessFile raf) {
		try {

			raf.writeChars(stagiaire.getNomLong());
			raf.writeChars(stagiaire.getPrenomLong());
			raf.writeChars(stagiaire.getDepartementLong());
			raf.writeChars(stagiaire.getNomPromoLong());
			raf.writeChars(stagiaire.getDateFormationLong());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Getters & Setters

	public Stagiaire getCle() {
		return cle;
	}

	public void setCle(Stagiaire cle) {
		this.cle = cle;
	}

	public int getIndexfilsGauche() {
		return indexfilsGauche;
	}

	public void setIndexfilsGauche(int indexfilsGauche) {
		this.indexfilsGauche = indexfilsGauche;
	}

	public int getIndexfilsDroit() {
		return indexfilsDroit;
	}

	public void setIndexfilsDroit(int indexfilsDroit) {
		this.indexfilsDroit = indexfilsDroit;
	}

	public int getIndexDoublon() {
		return indexdoublon;
	}

	public void setIndexDoublon(int doublon) {
		this.indexdoublon = doublon;
	}

}
