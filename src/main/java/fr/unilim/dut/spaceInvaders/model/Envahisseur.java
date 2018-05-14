package fr.unilim.dut.spaceInvaders.model;

public class Envahisseur extends Sprite {

	private boolean DirectionAGauche;
	
	public Envahisseur(Dimension dimension, Position positionOrigine, int vitesse) {
		super(positionOrigine,dimension,vitesse);
		this.DirectionAGauche = false;
	}

	public boolean DirectionAGauche() {
		return this.DirectionAGauche;
	}
	
	public void setDirectionAGauche(boolean b) {
		this.DirectionAGauche=b;
	}
}


