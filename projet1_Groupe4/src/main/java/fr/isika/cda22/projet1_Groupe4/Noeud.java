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

	
	/* Cette methode permet d'écrire le Noeud d'un Stagiaire dans le fichier binaire :

	 * Si le fichier est vide alors on doit écrire la racine
	 * S'il ne l'est pas, alors on doit ajouter à la racine un nouveau Noeud.
	 * Dans ce cas, on doit en premier lieu replacer le curseur à la fin de la racine
	 */
	public void ajouterStagiaireBinaire(Noeud noeudAjout, RandomAccessFile raf) {

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
	
	
	/* Cette methode permet d'ajouter un nouveau Noeud à la Racine

	 * On calcul l'index du noeud en cours puis l'index du noeud parent.
	 * Si le nom du stagiaire est alphabétiquement inférieur à celui du parent
	 * Alors si l'index du fils gauche est différent de -1
	 * Alors on recupère l'index du noeud parent puis on relance la methode sur le fils gauche.
	 * SINON si l'index du fils gauche est égal -1
	 * Alors on écrit l'index du fils gauche puis on écrit le nouveau noeud en fin de fichier.
	 * Les autres conditions traitent le sujet de la même façon, pour le fils droit et pour le doublon
	 */
	public void ecritureBinReccursive(Noeud noeudAjout, RandomAccessFile raf, int index) {

		try {
			int indexNoeud = (int) (raf.length() / TAILLE_NOEUD_OCTET);
			int indexParent = index;
			if (noeudAjout.getCle().getNomLong().compareTo(recupereNomIndex(indexParent, raf)) < 0) { 
				if (recupererIndexFilsGauche(indexParent, raf) != -1) { // il y a un fils gauche
					indexParent = recupererIndexFilsGauche(indexParent, raf);
					ecritureBinReccursive(noeudAjout, raf, indexParent); // on passe la methode au fils gauche
				} else { // il n'y a pas de fils gauche
					ecrireFilsGauche(indexParent, raf, indexNoeud);
					ecrireNoeudBin(noeudAjout, raf);
				}
			} else if (noeudAjout.getCle().getNomLong().compareTo(recupereNomIndex(indexParent, raf)) > 0) {
				if (recupererIndexFilsDroit(indexParent, raf) != -1) { // il y a un fils Droit
					indexParent = recupererIndexFilsDroit(indexParent, raf);
					ecritureBinReccursive(noeudAjout, raf, indexParent); // on passe la methode au fils Droit
				} else { // il n'y a pas de fils Droit
					ecrireFilsDroit(indexParent, raf, indexNoeud);
					ecrireNoeudBin(noeudAjout, raf);
				}
			} else if (noeudAjout.getCle().getNomLong().compareTo(recupereNomIndex(indexParent, raf)) == 0) {
				if (recupererIndexDoublon(indexParent, raf) != -1) { // Il y a un doublon
					indexParent = recupererIndexDoublon(indexParent, raf);
					ecritureBinReccursive(noeudAjout, raf, indexParent); // On passe la methode au doublon
				} else { // Il n'y a pas de doublon
					ecrireDoublon(indexParent, raf, indexNoeud);
					ecrireNoeudBin(noeudAjout, raf);
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

	}

	//Cette methode permet d'écrire un Noeud en fin de fichier binaire
	public void ecrireNoeudBin(Noeud noeud, RandomAccessFile raf) {
		try {

			raf.seek(raf.length()); //on place le curseur en fin de fichier
			recuperationAttributsStagiaireBin(noeud.getCle(), raf); // on recupère les attributs formatés
			raf.writeInt(noeud.getIndexfilsGauche());
			raf.writeInt(noeud.getIndexfilsDroit());
			raf.writeInt(noeud.getIndexDoublon());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Cette methode permet d'écrire l'index du fils gauche dans le noeud parent
	private void ecrireFilsGauche(int indexParent, RandomAccessFile raf, int indexNoeud) {
		try {
			raf.seek((indexParent * TAILLE_NOEUD_OCTET) + INDEX_FILS_GAUCHE_OCTET); //On place le curseur au niveau du noeud parent
			raf.writeInt(indexNoeud);											   // + le nombre d'octet pour arriver au fils gauche
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	//Cette methode permet d'écrire l'index du fils droit dans le noeud parent
	private void ecrireFilsDroit(int indexParent, RandomAccessFile raf, int indexNoeud) {
		try {
			raf.seek((indexParent * TAILLE_NOEUD_OCTET) + INDEX_FILS_DROIT_OCTET); //On place le curseur au niveau du noeud parent
			raf.writeInt(indexNoeud);											  // + le nombre d'octet pour arriver au fils droit
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	//Cette methode permet d'écrire l'index du doublon dans le noeud parent
	private void ecrireDoublon(int indexParent, RandomAccessFile raf, int indexNoeud) {
		try {
			raf.seek((indexParent * TAILLE_NOEUD_OCTET) + INDEX_DOUBLON_OCTET); //On place le curseur au niveau du noeud parent
			raf.writeInt(indexNoeud);                                          // + le nombre d'octet pour arriver au doublon
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	//Cette methode permet de récupérer l'index du fils gauche à partir du noeud parent
	private int recupererIndexFilsGauche(int indexNoeud, RandomAccessFile raf) {
		int indexParent = 0;
		try {
			raf.seek((indexNoeud * TAILLE_NOEUD_OCTET) + INDEX_FILS_GAUCHE_OCTET); //On place le curseur au niveau du noeud parent
			indexParent += raf.readInt();										  // + le nombre d'octet pour arriver au fils gauche
		} catch (IOException e) {
			e.printStackTrace();
		}
		return indexParent;
	}

	//Cette methode permet de récupérer l'index du fils droit à partir du noeud parent
	private int recupererIndexFilsDroit(int indexNoeud, RandomAccessFile raf) {
		int indexParent = 0;
		try {
			raf.seek((indexNoeud * TAILLE_NOEUD_OCTET) + INDEX_FILS_DROIT_OCTET); //On place le curseur au niveau du noeud parent
			indexParent += raf.readInt();										 // + le nombre d'octet pour arriver au fils droit
		} catch (IOException e) {
			e.printStackTrace();
		}
		return indexParent;
	}

	//Cette methode permet de récupérer l'index du doublon à partir du noeud parent
	private int recupererIndexDoublon(int indexNoeud, RandomAccessFile raf) {
		int indexParent = 0;
		try {
			raf.seek((indexNoeud * TAILLE_NOEUD_OCTET) + INDEX_DOUBLON_OCTET); //On place le curseur au niveau du noeud parent
			indexParent += raf.readInt();									  // + le nombre d'octet pour arriver au doublon
		} catch (IOException e) {
			e.printStackTrace();
		}
		return indexParent;
	}

	//Cette methode permet de récupérer le nom associé à un noeud parent
	public String recupereNomIndex(int indexParent, RandomAccessFile raf) {
		String nomRecup = "";
		try {
			raf.seek(indexParent * TAILLE_NOEUD_OCTET); //On place le curseur au niveau du noeud parent
			for (int k = 0; k < TAILLE_MAX_NOM; k++) {
				nomRecup += raf.readChar(); //On récupère le nom lu grace au curseur
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return nomRecup;
	}
	
	//Cette methode permet de récupérer + écrire les attributs formatés des stagiaires
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
