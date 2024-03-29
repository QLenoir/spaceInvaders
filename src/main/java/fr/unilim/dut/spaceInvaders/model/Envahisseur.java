package fr.unilim.dut.spaceInvaders.model;

import fr.unilim.dut.spaceInvaders.utils.MissileException;

public class Envahisseur extends Sprite {

	private Direction direction;
	
	public Envahisseur(Dimension dimension, Position positionOrigine, int vitesse) {
		super(positionOrigine,dimension,vitesse);
		this.direction = Direction.DROITE;
	}

	public Direction direction() {
		return this.direction;
	}
	
	public void setDirection(Direction direction) {
		this.direction=direction;
	}

	public Missile tirerUnMissile(Dimension dimensionMissile, int vitesseMissile) {

		if (dimensionMissile.longueur>this.longueur()) { 
			throw new MissileException("La longueur du missile est supérieure à celle de l'envahisseur");
		}
		Position positionOrigineMissile = calculerLaPositionDeTirDuMissile(dimensionMissile);
		return new Missile(dimensionMissile, positionOrigineMissile, vitesseMissile);
	}

	public Position calculerLaPositionDeTirDuMissile(Dimension dimensionMissile) {
		int abscisseMilieuEnvahisseur = this.abscisseLaPlusAGauche() + (this.longueur() / 2);
		int abscisseOrigineMissile = abscisseMilieuEnvahisseur - (dimensionMissile.longueur() / 2);

		int ordonneeeOrigineMissile = this.ordonneeLaPlusHaute() + dimensionMissile.hauteur;
		return  new Position(abscisseOrigineMissile, ordonneeeOrigineMissile);
	}
}


