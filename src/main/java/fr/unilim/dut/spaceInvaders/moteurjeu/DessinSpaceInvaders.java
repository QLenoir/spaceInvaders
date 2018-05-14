package fr.unilim.dut.spaceInvaders.moteurjeu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import fr.unilim.dut.spaceInvaders.model.SpaceInvaders;
import fr.unilim.dut.spaceInvaders.moteurjeu.DessinJeu;

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
		crayon.fillRect(this.spaceInvaders.envahisseur().abscisseLaPlusAGauche(),this.spaceInvaders.envahisseur().ordonneeLaPlusBasse(),this.spaceInvaders.envahisseur().dimension().longueur(),this.spaceInvaders.envahisseur().dimension().hauteur());
	}

	private void dessinerUnVaisseau(Graphics2D crayon) {
		crayon.setColor(Color.GRAY);
		crayon.fillRect(this.spaceInvaders.vaisseau().abscisseLaPlusAGauche(),this.spaceInvaders.vaisseau().ordonneeLaPlusBasse(),this.spaceInvaders.vaisseau().dimension().longueur(),this.spaceInvaders.vaisseau().dimension().hauteur());
	}

	private void dessinerUnMssile(Graphics2D crayon) {
		crayon.setColor(Color.BLUE);
		crayon.fillRect(this.spaceInvaders.missile().abscisseLaPlusAGauche(),this.spaceInvaders.missile().ordonneeLaPlusBasse(),this.spaceInvaders.missile().dimension().longueur(),this.spaceInvaders.missile().dimension().hauteur());
	}
}
