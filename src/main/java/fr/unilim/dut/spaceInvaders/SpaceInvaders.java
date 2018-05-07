package fr.unilim.dut.spaceInvaders;

import fr.unilim.dut.spaceInvaders.moteurjeu.Commande;
import fr.unilim.dut.spaceInvaders.moteurjeu.Jeu;
import fr.unilim.dut.spaceInvaders.utils.DebordementEspaceJeuException;
import fr.unilim.dut.spaceInvaders.utils.HorsEspaceJeuException;

public class SpaceInvaders implements Jeu {
	
	int longueur;
	int hauteur;
	Vaisseau vaisseau;

	public SpaceInvaders(int longueur, int hauteur) {
		this.longueur = longueur;
		this.hauteur = hauteur;
	}

	@Override
	public String toString() {
		return recupererEspaceJeuDansChaineASCII();
	}

	public String recupererEspaceJeuDansChaineASCII() {
		StringBuilder espaceDeJeu = new StringBuilder();
		for (int y = 0; y < hauteur; y++) {
			for (int x = 0; x < longueur; x++) {
				espaceDeJeu.append(recupererMarqueDeLaPosition(x, y));
			}
			espaceDeJeu.append(Constante.MARQUE_FIN_LIGNE);
		}
		return espaceDeJeu.toString();
	}

	private char recupererMarqueDeLaPosition(int x, int y) {
		char marque;
		if (this.aUnVaisseauQuiOccupeLaPosition(x, y))
			marque=Constante.MARQUE_VAISSEAU;
		else
			marque=Constante.MARQUE_VIDE;
		return marque;
	}

	private boolean aUnVaisseauQuiOccupeLaPosition(int x, int y) {
		return this.aUnVaisseau() && this.vaisseau.occupeLaPosition(x, y);
	}

	private boolean aUnVaisseau() {
		return this.vaisseau!=null;
	}

	private boolean estDansEspaceJeu(int x, int y) {
		return ((x >= 0) && (x < this.longueur)) && ((y >= 0) && (y < this.hauteur));
	}

	public void deplacerVaisseauVersLaGauche() {
		if (this.vaisseau.abscisseLaPlusAGauche() > 0) {
			this.vaisseau.seDeplacerVersLaGauche();
		}

	}

	public void deplacerVaisseauVersLaDroite() {
		if (this.vaisseau.abscisseLaPlusADroite() < (longueur - 1))
			this.vaisseau.seDeplacerVersLaDroite();
	}


	 public void positionnerUnNouveauVaisseau(Dimension dimension, Position position) {
			
			int x = position.abscisse();
			int y = position.ordonnee();
			
			if (!estDansEspaceJeu(x, y))
				throw new HorsEspaceJeuException("La position du vaisseau est en dehors de l'espace jeu");

			int longueurVaisseau = dimension.longueur();
			int hauteurVaisseau = dimension.hauteur();
			
			if (!estDansEspaceJeu(x + longueurVaisseau - 1, y))
				throw new DebordementEspaceJeuException("Le vaisseau déborde de l'espace jeu vers la droite à cause de sa longueur");
			if (!estDansEspaceJeu(x, y - hauteurVaisseau + 1))
				throw new DebordementEspaceJeuException("Le vaisseau déborde de l'espace jeu vers le bas à cause de sa hauteur");

			vaisseau = new Vaisseau(longueurVaisseau, hauteurVaisseau);
			vaisseau.positionner(x, y);
		}

	public Vaisseau vaisseau() {
		return this.vaisseau;
	}
	 
	public void initialiserJeu() {
		
		this.vaisseau = new Vaisseau(Constante.VAISSEAU_LONGUEUR,Constante.VAISSEAU_HAUTEUR,this.longueur/2,this.hauteur+1);
	}
	
	@Override
	public void evoluer(Commande commandeUser) {
		if (commandeUser.gauche) {
			this.deplacerVaisseauVersLaGauche();
		}
		
		if (commandeUser.droite) {
			this.deplacerVaisseauVersLaDroite();
		}
	}

	@Override
	public boolean etreFini() {
		
		return false;
	}
}
