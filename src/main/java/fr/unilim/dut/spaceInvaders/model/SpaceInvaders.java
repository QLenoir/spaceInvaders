package fr.unilim.dut.spaceInvaders.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.unilim.dut.spaceInvaders.moteurjeu.Commande;
import fr.unilim.dut.spaceInvaders.moteurjeu.Jeu;
import fr.unilim.dut.spaceInvaders.utils.DebordementEspaceJeuException;
import fr.unilim.dut.spaceInvaders.utils.HorsEspaceJeuException;
import fr.unilim.dut.spaceInvaders.utils.MissileException;

public class SpaceInvaders implements Jeu {

	int longueur;
	int hauteur;
	Vaisseau vaisseau; 
	List<Missile> missiles;
	List<Envahisseur> envahisseurs;
	Collision collision;

	public SpaceInvaders(int longueur, int hauteur) {
		this.longueur = longueur;
		this.hauteur = hauteur;
		this.missiles = new ArrayList<>();
		this.envahisseurs = new ArrayList<>();
		this.collision = new Collision();
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
		boolean occupeLaPosition = false;
		for (Missile missile : missiles) {
			if (missile.occupeLaPosition(x, y)) {
				occupeLaPosition = true;
			}
		}
		return this.aUnMissile() && occupeLaPosition;
	}

	private boolean aUnVaisseauQuiOccupeLaPosition(int x, int y) {
		return this.aUnVaisseau() && this.vaisseau.occupeLaPosition(x, y);
	}

	private boolean aUnEnvahisseurQuiOccupeLaPosition(int x, int y) {
		boolean occupeLaPosition = false;
		for (Envahisseur envahisseur : envahisseurs) {
			if (envahisseur.occupeLaPosition(x, y)) {
				occupeLaPosition = true;
			}
		}
		return this.aUnEnvahisseur() && occupeLaPosition;
	}

	public boolean aUnEnvahisseur() {
		return !this.envahisseurs.isEmpty();
	}

	private boolean aUnVaisseau() {
		return this.vaisseau!=null;
	}

	public boolean aUnMissile() {
		return !this.missiles.isEmpty();
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

	public List<Missile> missiles() {
		return this.missiles;
	}

	public void initialiserJeu() {
		Position positionVaisseau = new Position(this.longueur/2,this.hauteur-1);
		Dimension dimensionVaisseau = new Dimension(Constante.VAISSEAU_LONGUEUR, Constante.VAISSEAU_HAUTEUR);
		this.positionnerUnNouveauVaisseau(dimensionVaisseau, positionVaisseau, Constante.VAISSEAU_VITESSE);

		Position positionEnvahisseur = new Position(50,50);
		Position positionEnvahisseur2 = new Position(150,50);
		Dimension dimensionEnvahisseur = new Dimension(Constante.ENVAHISSEUR_LONGUEUR,Constante.ENVAHISSEUR_HAUTEUR);
		this.positionnerUnNouveauEnvahisseur(dimensionEnvahisseur, positionEnvahisseur, Constante.ENVAHISSEUR_VITESSE);
		this.positionnerUnNouveauEnvahisseur(dimensionEnvahisseur, positionEnvahisseur2, Constante.ENVAHISSEUR_VITESSE);
	} 

	@Override
	public void evoluer(Commande commandeUser) {
		if (commandeUser.gauche) {
			this.deplacerVaisseauVersLaGauche();
		}

		if (commandeUser.droite) {
			this.deplacerVaisseauVersLaDroite();
		}

		if (commandeUser.tir) {
			tirerUnMissile(new Dimension(Constante.MISSILE_LONGUEUR, Constante.MISSILE_HAUTEUR),
					Constante.MISSILE_VITESSE);
		}

		deplacerAutomatiquementSprite();

		for (Missile missile : missiles) {
			for (Envahisseur envahisseur : envahisseurs) {
				this.collision.detecterCollision(envahisseur, missile);	
			}

		}

		detecterDestructionSprite();
	}

	private void detecterDestructionSprite() {

		Iterator<Envahisseur> iterator = this.envahisseurs.iterator();

		while (iterator.hasNext()) {
			Envahisseur missile = iterator.next();

			if (missile.ordonneeLaPlusBasse()==0) {
				iterator.remove();
			} else {
				missile.deplacerVerticalementVers(Direction.HAUT_ECRAN);
			}
		}


		Iterator<Missile> iterator2 = this.missiles().iterator();

		while (iterator2.hasNext()) {
			Missile missile = iterator2.next();

			if (missile.estDetruit())
				iterator2.remove();
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
		
		for (Envahisseur envahisseur : envahisseurs) {
			if (envahisseur.DirectionAGauche()) {
				envahisseur.deplacerHorizontalementVers(Direction.GAUCHE_ECRAN);
			} else {
				envahisseur.deplacerHorizontalementVers(Direction.DROITE_ECRAN);
			}
		}
		
	}

	private void verifierDebordementEnvahisseur() {

		for (Envahisseur envahisseur : envahisseurs) {
			if (envahisseur.abscisseLaPlusADroite()==this.longueur-1) {
				for (Envahisseur envahisseur2 : envahisseurs) {
					envahisseur2.setDirectionAGauche(true);
				}
				
			} else if (envahisseur.abscisseLaPlusAGauche()<=0){
				for (Envahisseur envahisseur2 : envahisseurs) {
					envahisseur2.setDirectionAGauche(false);
				}
			}
		}
	}

	@Override
	public boolean etreFini() {
		return !this.aUnEnvahisseur();
	}

	public void tirerUnMissile(Dimension dimensionMissile, int vitesseMissile) {

		if ((vaisseau.hauteur()+ dimensionMissile.hauteur()) > this.hauteur )
			throw new MissileException("Pas assez de hauteur libre entre le vaisseau et le haut de l'espace jeu pour tirer le missile");

		this.missiles.add(this.vaisseau.tirerUnMissile(dimensionMissile,vitesseMissile));

		verifierChevauchementMissile();
	}

	private void verifierChevauchementMissile() {
		Iterator<Missile> iterator = this.missiles().iterator();
		Iterator<Missile> iterator2 = this.missiles().iterator();
		Missile missile2 = iterator2.next();

		while (iterator.hasNext() && iterator2.hasNext()) {
			Missile missile = iterator.next();
			missile2 = iterator2.next();
			this.collision.detecterCollision(missile, missile2);
			missile.estDetruit(false);
			this.enleverMissile();
		}
	}

	public void deplacerMissile() {
		Iterator<Missile> iterator = this.missiles.iterator();

		while (iterator.hasNext()) {
			Missile missile = iterator.next();

			if (missile.ordonneeLaPlusBasse()==0) {
				iterator.remove();
			} else {
				missile.deplacerVerticalementVers(Direction.HAUT_ECRAN);
			}
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

		this.envahisseurs.add(new Envahisseur(dimension,position,vitesse));
	}

	public List<Envahisseur> envahisseurs() {
		return this.envahisseurs;
	}
 
	public void enleverEnvahisseur() {
		Iterator<Envahisseur> iterator = this.envahisseurs.iterator();

		while (iterator.hasNext()) {
			Envahisseur envahisseur = iterator.next();

			if (envahisseur.estDetruit())
				iterator.remove();
		}
	}

	public void enleverMissile() {
		Iterator<Missile> iterator = this.missiles.iterator();

		while (iterator.hasNext()) {
			Missile missile = iterator.next();

			if (missile.estDetruit())
				iterator.remove();
		}
	}
}
