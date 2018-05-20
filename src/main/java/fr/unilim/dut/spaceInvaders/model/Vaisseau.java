package fr.unilim.dut.spaceInvaders.model;

import fr.unilim.dut.spaceInvaders.utils.MissileException;

public class Vaisseau extends Sprite {

	public Vaisseau(Dimension dimension, Position positionOrigine, int vitesse) {
		super(positionOrigine, dimension,vitesse);
	}

	public Missile tirerUnMissile(Dimension dimensionMissile, int vitesseMissile) {

		if (dimensionMissile.longueur>this.longueur()) { 
			throw new MissileException("La longueur du missile est supérieure à celle du vaisseau");
		}
		Position positionOrigineMissile = calculerLaPositionDeTirDuMissile(dimensionMissile);
		return new Missile(dimensionMissile, positionOrigineMissile, vitesseMissile);
	}

	public Position calculerLaPositionDeTirDuMissile(Dimension dimensionMissile) {
		int abscisseMilieuVaisseau = this.abscisseLaPlusAGauche() + (this.longueur() / 2);
		int abscisseOrigineMissile = abscisseMilieuVaisseau - (dimensionMissile.longueur() / 2);

		int ordonneeeOrigineMissile = this.ordonneeLaPlusBasse() - 1;
		Position positionOrigineMissile = new Position(abscisseOrigineMissile, ordonneeeOrigineMissile);
		return positionOrigineMissile; 
	}
}