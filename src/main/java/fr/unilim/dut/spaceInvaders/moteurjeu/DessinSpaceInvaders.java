package fr.unilim.dut.spaceInvaders.moteurjeu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import fr.unilim.dut.spaceInvaders.SpaceInvaders;
import fr.unilim.dut.spaceInvaders.moteurjeu.DessinJeu;

public class DessinSpaceInvaders implements DessinJeu {

	private SpaceInvaders spaceInvaders;

	public DessinSpaceInvaders (SpaceInvaders spaceInvaders){
		this.spaceInvaders = spaceInvaders;
	}
	@Override
	public void dessiner(BufferedImage image) {
		Graphics2D crayon = (Graphics2D) image.getGraphics();
		crayon.setColor(Color.GRAY);
		crayon.fillRect(this.spaceInvaders.vaisseau().abscisseLaPlusAGauche(),this.spaceInvaders.vaisseau().ordonneeLaPlusBasse(),this.spaceInvaders.vaisseau().dimension().longueur(),this.spaceInvaders.vaisseau().dimension().hauteur());
	}

}
