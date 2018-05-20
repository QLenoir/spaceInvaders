package fr.unilim.dut.spaceInvaders.model;

import fr.unilim.dut.spaceInvaders.moteurjeu.Commande;
import fr.unilim.dut.spaceInvaders.moteurjeu.Jeu;
import fr.unilim.dut.spaceInvaders.utils.DebordementEspaceJeuException;
import fr.unilim.dut.spaceInvaders.utils.HorsEspaceJeuException;
import fr.unilim.dut.spaceInvaders.utils.MissileException;

public class SpaceInvaders implements Jeu {

	int longueur;
	int hauteur;
	Vaisseau vaisseau; 
	Missile missile;
	Envahisseur envahisseur;
	Collision collision;

	public SpaceInvaders(int longueur, int hauteur) {
		this.longueur = longueur;
		this.hauteur = hauteur;
		this.collision = new Collision(this);
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
			marque = Constante.MARQUE_VAISSEAU;
		else if (this.aUnMissileQuiOccupeLaPosition(x, y))
			marque = Constante.MARQUE_MISSILE;
		else if (this.aUnEnvahisseurQuiOccupeLaPosition(x, y))
			marque = Constante.MARQUE_ENVAHISSEUR;
		else
			marque = Constante.MARQUE_VIDE;
		return marque;
	}

	private boolean aUnMissileQuiOccupeLaPosition(int x, int y) {
		return this.aUnMissile() && this.missile.occupeLaPosition(x, y);
	}

	private boolean aUnVaisseauQuiOccupeLaPosition(int x, int y) {
		return this.aUnVaisseau() && this.vaisseau.occupeLaPosition(x, y);
	}

	private boolean aUnEnvahisseurQuiOccupeLaPosition(int x, int y) {
		return this.aUnEnvahisseur() && this.envahisseur.occupeLaPosition(x, y);
	}

	public boolean aUnEnvahisseur() {
		return this.envahisseur!=null;
	}

	private boolean aUnVaisseau() {
		return this.vaisseau!=null;
	}

	public boolean aUnMissile() {
		return this.missile!=null;
	}

	private boolean estDansEspaceJeu(int x, int y) {
		return ((x >= 0) && (x < this.longueur)) && ((y >= 0) && (y < this.hauteur));
	}

	public void deplacerVaisseauVersLaGauche() {
		if (0 < vaisseau.abscisseLaPlusAGauche()) {
			vaisseau.deplacerHorizontalementVers(Direction.GAUCHE);

			if (!estDansEspaceJeu(vaisseau.abscisseLaPlusAGauche(), vaisseau.ordonneeLaPlusHaute())) {
				vaisseau.positionner(0, vaisseau.ordonneeLaPlusHaute());
			}
		}
	}

	public void deplacerVaisseauVersLaDroite() {
		if (vaisseau.abscisseLaPlusADroite() < (longueur - 1)) {
			vaisseau.deplacerHorizontalementVers(Direction.DROITE);

			if (!estDansEspaceJeu(vaisseau.abscisseLaPlusADroite(), vaisseau.ordonneeLaPlusHaute())) {
				vaisseau.positionner(longueur - vaisseau.longueur(), vaisseau.ordonneeLaPlusHaute());
			}
		}
	}


	public void positionnerUnNouveauVaisseau(Dimension dimension, Position position, int vitesse) {

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

		this.vaisseau = new Vaisseau(dimension,position,vitesse);
	}

	public Vaisseau vaisseau() {
		return this.vaisseau;
	}

	public Missile missile() {
		return this.missile;
	}

	public void initialiserJeu() {
		Position positionVaisseau = new Position(this.longueur/2,this.hauteur-1);
		Dimension dimensionVaisseau = new Dimension(Constante.VAISSEAU_LONGUEUR, Constante.VAISSEAU_HAUTEUR);
		this.positionnerUnNouveauVaisseau(dimensionVaisseau, positionVaisseau, Constante.VAISSEAU_VITESSE);

		Position positionEnvahisseur = new Position(this.longueur-this.longueur+50,this.hauteur-this.hauteur+50);
		Dimension dimensionEnvahisseur = new Dimension(Constante.ENVAHISSEUR_LONGUEUR,Constante.ENVAHISSEUR_HAUTEUR);
		this.positionnerUnNouveauEnvahisseur(dimensionEnvahisseur, positionEnvahisseur, Constante.ENVAHISSEUR_VITESSE);
		this.envahisseur.setDirectionAGauche(true);
	}

	@Override
	public void evoluer(Commande commandeUser) {
		if (commandeUser.gauche) {
			this.deplacerVaisseauVersLaGauche();
		}

		if (commandeUser.droite) {
			this.deplacerVaisseauVersLaDroite();
		}

		if (commandeUser.tir && !this.aUnMissile()) {
			tirerUnMissile(new Dimension(Constante.MISSILE_LONGUEUR, Constante.MISSILE_HAUTEUR),
					Constante.MISSILE_VITESSE);
		}

		deplacerAutomatiquementSprite();

		this.collision.detecterCollision(envahisseur, missile);

		detecterDestructionSprite();
	}

	private void detecterDestructionSprite() {

		if (this.aUnEnvahisseur() && this.envahisseur.estDetruit) {
			this.enleverEnvahisseur();
		}

		if(this.aUnMissile() && this.missile.estDetruit) {
			this.enleverMissile();
		}
	}

	private void deplacerAutomatiquementSprite() {
		if (this.aUnMissile()) {
			this.deplacerMissile();
		}
		if (this.aUnEnvahisseur()) {
			this.deplacerEnvahisseur();
		}
	}


	public void deplacerEnvahisseur() {
		verifierDebordementEnvahisseur();

		deplacerEnvahisseurDansLeBonSens();
	}

	private void deplacerEnvahisseurDansLeBonSens() {

		if (this.envahisseur.DirectionAGauche()) {
			this.envahisseur.deplacerHorizontalementVers(Direction.GAUCHE_ECRAN);
		} else {
			this.envahisseur.deplacerHorizontalementVers(Direction.DROITE_ECRAN);
		}
	}

	private void verifierDebordementEnvahisseur() {

		if (this.envahisseur.abscisseLaPlusADroite()==this.longueur-1) {
			this.envahisseur.setDirectionAGauche(true);
		} else if (this.envahisseur.abscisseLaPlusAGauche()<=0){
			this.envahisseur.setDirectionAGauche(false);
		}
	}

	@Override
	public boolean etreFini() {
		if (!this.aUnEnvahisseur()) {
			return true;
		} else {
			return false;
		}

	}

	public void tirerUnMissile(Dimension dimensionMissile, int vitesseMissile) {

		if ((vaisseau.hauteur()+ dimensionMissile.hauteur()) > this.hauteur )
			throw new MissileException("Pas assez de hauteur libre entre le vaisseau et le haut de l'espace jeu pour tirer le missile");

		this.missile = this.vaisseau.tirerUnMissile(dimensionMissile,vitesseMissile);
	}

	public void deplacerMissile() {
		if (this.missile.ordonneeLaPlusBasse()==0) {
			this.missile=null;
		} else {
			this.missile.deplacerVerticalementVers(Direction.HAUT_ECRAN);
		}
	}

	public void positionnerUnNouveauEnvahisseur(Dimension dimension, Position position, int vitesse) {

		int x = position.abscisse();
		int y = position.ordonnee();

		if (!estDansEspaceJeu(x, y))
			throw new HorsEspaceJeuException("La position de l'envahisseur est en dehors de l'espace jeu");

		int longueurEnvahisseur = dimension.longueur();
		int hauteurEnvahisseur = dimension.hauteur();

		if (!estDansEspaceJeu(x + longueurEnvahisseur - 1, y))
			throw new DebordementEspaceJeuException("L'envahisseur déborde de l'espace jeu vers la droite à cause de sa longueur");
		if (!estDansEspaceJeu(x, y - hauteurEnvahisseur + 1)) 
			throw new DebordementEspaceJeuException("L'envahisseur déborde de l'espace jeu vers le bas à cause de sa hauteur");

		this.envahisseur = new Envahisseur(dimension,position,vitesse);
	}

	public Envahisseur envahisseur() {
		return envahisseur;
	}

	public void enleverEnvahisseur() {
		this.envahisseur = null;
	}

	public void enleverMissile() {
		this.missile = null;
	}
}
