package fr.unilim.dut.spaceInvaders.moteurjeu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import fr.unilim.dut.spaceInvaders.model.Envahisseur;
import fr.unilim.dut.spaceInvaders.model.Missile;
import fr.unilim.dut.spaceInvaders.model.SpaceInvaders;

public class DessinSpaceInvaders implements DessinJeu {

	private SpaceInvaders spaceInvaders;

	public DessinSpaceInvaders (SpaceInvaders spaceInvaders){
		this.spaceInvaders = spaceInvaders;
	}
	 
	@Override 
	public void dessiner(BufferedImage image) {
		Graphics2D crayon = (Graphics2D) image.getGraphics();
		dessinerUnVaisseau(crayon);
		if (spaceInvaders.aUnMissile())
		dessinerUnMssile(crayon);
		if (spaceInvaders.aUnEnvahisseur())
		dessinerUnEnvahisseur(crayon);
	}

	private void dessinerUnEnvahisseur(Graphics2D crayon) {
		crayon.setColor(Color.GREEN);
		for (Envahisseur envahisseur : this.spaceInvaders.envahisseurs()) {
			crayon.fillRect(envahisseur.abscisseLaPlusAGauche(),envahisseur.ordonneeLaPlusBasse(),envahisseur.dimension().longueur(),envahisseur.dimension().hauteur());
		}
	}

	private void dessinerUnVaisseau(Graphics2D crayon) {
		crayon.setColor(Color.GRAY);
		crayon.fillRect(this.spaceInvaders.vaisseau().abscisseLaPlusAGauche(),this.spaceInvaders.vaisseau().ordonneeLaPlusBasse(),this.spaceInvaders.vaisseau().dimension().longueur(),this.spaceInvaders.vaisseau().dimension().hauteur());
	}

	private void dessinerUnMssile(Graphics2D crayon) {
		crayon.setColor(Color.BLUE);
		for (Missile missile : this.spaceInvaders.missiles()) {
			crayon.fillRect(missile.abscisseLaPlusAGauche(),missile.ordonneeLaPlusBasse(),missile.dimension().longueur(),missile.dimension().hauteur());
		}
	}
}
