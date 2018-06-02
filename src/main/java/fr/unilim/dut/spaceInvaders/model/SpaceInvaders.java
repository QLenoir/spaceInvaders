package fr.unilim.dut.spaceInvaders.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

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
	List<Missile> missilesEnvahisseurs;
	List<Envahisseur> envahisseurs;
	Collision collision;
	int score;
	int nombreVague;

	public SpaceInvaders(int longueur, int hauteur) {
		this.longueur = longueur;
		this.hauteur = hauteur;
		this.missiles = new ArrayList<>();
		this.envahisseurs = new ArrayList<>();
		this.missilesEnvahisseurs = new ArrayList<>();
		this.collision = new Collision();
		this.score = 0;
		this.nombreVague = 1;
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
		
		for (Missile missile : missilesEnvahisseurs) {
			if (missile.occupeLaPosition(x, y)) {
				occupeLaPosition = true;
			}
		}
		
		return occupeLaPosition;
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
		return occupeLaPosition;
	}

	public boolean aUnEnvahisseur() {
		return !this.envahisseurs.isEmpty();
	}

	public boolean aUnVaisseau() {
		return this.vaisseau!=null;
	}

	public boolean aUnMissile() {
		return !this.missiles.isEmpty() || !this.missilesEnvahisseurs.isEmpty();
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

		creerHordeEnvahisseur();

	}

	private void creerHordeEnvahisseur() {
		Dimension dimensionEnvahisseur = new Dimension(Constante.ENVAHISSEUR_LONGUEUR,Constante.ENVAHISSEUR_HAUTEUR);
		
		for (int i = 0; i < 4; i++) {
			positionnerUneLigneEnvahisseur(dimensionEnvahisseur, Constante.ENVAHISSEUR_ECART, Constante.TAILLE_LIGNE, Constante.ENVAHISSEUR_LIGNE*i+Constante.ENVAHISSEUR_LIGNE, Constante.ENVAHISSEUR_VITESSE );
		}
	}

	public void positionnerUneLigneEnvahisseur(Dimension dimensionEnvahisseur,int ecart, int tailleLigne ,int ordonnee, int vitesse ) {
		for (int i = ecart ;i<=tailleLigne;i=i+ecart) {
			Position positionEnvahisseur = new Position(i,ordonnee);
			this.positionnerUnNouveauEnvahisseur(dimensionEnvahisseur, positionEnvahisseur, vitesse);
		}
	} 

	@Override
	public void evoluer(Commande commandeUser) {
		if (commandeUser.gauche) {
			this.deplacerVaisseauVersLaGauche();
		}

		if (commandeUser.droite) {
			this.deplacerVaisseauVersLaDroite();
		}

		if (commandeUser.tir && this.missiles.size()<3) {
			tirerUnMissileVaisseau(new Dimension(Constante.MISSILE_LONGUEUR, Constante.MISSILE_HAUTEUR),
					Constante.MISSILE_VITESSE);
		}

		tirEnvahisseurAutomatique();
		
		deplacerAutomatiquementSprite();

		detecterAutomatiquementCollisions();

		destructionAutomatiquementSprite();
		
		creationAutomatiqueNouvelleVague();
	}

	private void creationAutomatiqueNouvelleVague() {
		if(!this.aUnEnvahisseur()) {
			creerHordeEnvahisseur();
			this.nombreVague++;
		}
	}

	private void tirEnvahisseurAutomatique() {
		
		Random rand = new Random();
		int proba = rand.nextInt(100);
		int numeroEnvahisseur = rand.nextInt(this.envahisseurs.size());
		
		if (proba<Constante.ENVAHISSEUR_PROBABILITE_TIR && !this.aUnEnvahisseurQuiOccupeLaPosition(this.envahisseurs.get(numeroEnvahisseur).abscisseLaPlusAGauche(),this.envahisseurs.get(numeroEnvahisseur).ordonneeLaPlusHaute()+Constante.ENVAHISSEUR_LIGNE)) {
			this.tirerUnMissileEnvahisseur(new Dimension(Constante.MISSILE_HAUTEUR,Constante.MISSILE_LONGUEUR),this.envahisseurs.get(numeroEnvahisseur),Constante.MISSILE_VITESSE);
		}
	}
 
	public void detecterAutomatiquementCollisions() {
		detecterAutomatiquementCollisionMissileEnvahisseur();
		
		detecterAutomatiquementCollisionMissileVaisseau();
	}

	private void detecterAutomatiquementCollisionMissileVaisseau() {
		Iterator<Missile> iterator = this.missilesEnvahisseurs.iterator();
		while(iterator.hasNext() && this.aUnVaisseau()) {
			Missile missileEnvahisseur = iterator.next();
				
			if(this.collision.detecterCollision(missileEnvahisseur, this.vaisseau)) {
					this.vaisseau.estDetruit(true);
					missileEnvahisseur.estDetruit(true);
				}
		}
	}

	private void detecterAutomatiquementCollisionMissileEnvahisseur() {
		for (Missile missile : missiles) {
			for (Envahisseur envahisseur : envahisseurs) {
				if(this.collision.detecterCollision(envahisseur, missile)) {
					envahisseur.estDetruit(true);
					missile.estDetruit(true);
				}
			}

		}
	}

	public void destructionAutomatiquementSprite() {

		destructionAutomatiqueEnvahisseur();

		destructionAutomatiqueMissile();
		
		destructionAutomatiqueMissileEnvahisseur();
		
		destructionAutomatiqueVaisseau();
	}

	private void destructionAutomatiqueVaisseau() {
		if(aUnVaisseau() && this.vaisseau.estDetruit) {
			this.vaisseau=null;
		}
	}

	private void destructionAutomatiqueMissileEnvahisseur() {
		Iterator<Missile> iterator3 = this.missilesEnvahisseurs().iterator();

		while (iterator3.hasNext()) {
			Missile missileEnvahisseur = iterator3.next();

			if (missileEnvahisseur.estDetruit())
				iterator3.remove();
		}
	}

	private void destructionAutomatiqueMissile() {
		Iterator<Missile> iterator2 = this.missiles().iterator();

		while (iterator2.hasNext()) {
			Missile missile = iterator2.next();

			if (missile.estDetruit())
				iterator2.remove();
		}
	}

	private void destructionAutomatiqueEnvahisseur() {
		Iterator<Envahisseur> iterator = this.envahisseurs().iterator();

		while (iterator.hasNext()) {
			Envahisseur envahisseur = iterator.next();

			if (envahisseur.estDetruit()) {
				iterator.remove();
				this.score=this.score+Constante.SCORE_PAR_ENVAHISSEUR;
			}
			
		}
	}

	public void deplacerAutomatiquementSprite() {
		if (this.aUnMissile()) {  
			this.deplacerMissile();
			this.deplacerMissileEnvahisseur(); 
		}
		if (this.aUnEnvahisseur()) {
			this.deplacerEnvahisseur();
		}
	}


	private void deplacerMissileEnvahisseur() {
		Iterator<Missile> iterator = this.missilesEnvahisseurs.iterator();

		while (iterator.hasNext()) {
			Missile missile = iterator.next();

			if (missile.ordonneeLaPlusHaute()>=Constante.ESPACEJEU_HAUTEUR) {
				iterator.remove();
			} else {
				missile.deplacerVerticalementVers(Direction.BAS_ECRAN);
			}
		}
	}

	public void deplacerEnvahisseur() {
		verifierDebordementEnvahisseur();
		deplacerEnvahisseurDansLeBonSens();
	}

	private void deplacerEnvahisseurDansLeBonSens() {

		for (Envahisseur envahisseur : envahisseurs) {
			if (envahisseur.direction()==Direction.GAUCHE_ECRAN) {
				envahisseur.deplacerHorizontalementVers(Direction.GAUCHE_ECRAN);
			} else {
				envahisseur.deplacerHorizontalementVers(Direction.DROITE_ECRAN);
			}
		}

	}

	private void verifierDebordementEnvahisseur() {

		for (Envahisseur envahisseur : envahisseurs) {
			if (envahisseur.abscisseLaPlusADroite()>=this.longueur-1) {
				for (Envahisseur envahisseur2 : envahisseurs) {
					envahisseur2.setDirection(Direction.GAUCHE_ECRAN);
					envahisseur2.deplacerVerticalementVers(Direction.BAS_ECRAN);
				}

			} else if (envahisseur.abscisseLaPlusAGauche()<=0){
				for (Envahisseur envahisseur2 : envahisseurs) {
					envahisseur2.setDirection(Direction.DROITE_ECRAN);
					envahisseur2.deplacerVerticalementVers(Direction.BAS_ECRAN);
				}
			}
		}
	}

	@Override
	public boolean etreFini() {
		return !this.aUnVaisseau();
	}

	public void tirerUnMissileVaisseau(Dimension dimensionMissile, int vitesseMissile) {

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
			
			if(this.collision.detecterCollision(missile, missile2)) {
				missile2.estDetruit(true);
			}
			 
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

	public int score() {
		return this.score;
	}

	public void tirerUnMissileEnvahisseur(Dimension dimension,Envahisseur envahisseur, int i) {
		this.missilesEnvahisseurs.add(envahisseur.tirerUnMissile(dimension,i));
	}

	public List<Missile> missilesEnvahisseurs() {
		return missilesEnvahisseurs;
	}

	public int nombreVague() {
		return this.nombreVague;
	}
}
