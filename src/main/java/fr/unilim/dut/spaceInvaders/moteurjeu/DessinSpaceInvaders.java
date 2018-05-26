package fr.unilim.dut.spaceInvaders.moteurjeu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import fr.unilim.dut.spaceInvaders.model.Constante;
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
		if (spaceInvaders.aUnMissile()) {
			dessinerUnMssile(crayon);
		}
		if (spaceInvaders.aUnEnvahisseur()) {
			dessinerUnEnvahisseur(crayon);
		}
		crayon.setColor(Color.BLUE);
		crayon.setFont(new Font("Britannic Bold",Font.BOLD,50));
		crayon.drawString("Score : "+this.spaceInvaders.score(), Constante.ESPACEJEU_X_SCORE, Constante.ESPACEJEU_Y_SCORE);
	}

	private void dessinerUnEnvahisseur(Graphics2D crayon) {
		crayon.setColor(Color.GREEN);
		for (Envahisseur envahisseur : this.spaceInvaders.envahisseurs()) {
			crayon.fillRect(envahisseur.abscisseLaPlusAGauche(),envahisseur.ordonneeLaPlusBasse()+Constante.ESPACEJEU_SCORE,envahisseur.dimension().longueur(),envahisseur.dimension().hauteur());
		}
	}

	private void dessinerUnVaisseau(Graphics2D crayon) {
		crayon.setColor(Color.GRAY);
		crayon.fillRect(this.spaceInvaders.vaisseau().abscisseLaPlusAGauche(),this.spaceInvaders.vaisseau().ordonneeLaPlusBasse()+Constante.ESPACEJEU_SCORE,this.spaceInvaders.vaisseau().dimension().longueur(),this.spaceInvaders.vaisseau().dimension().hauteur());
	} 

	private void dessinerUnMssile(Graphics2D crayon) {
		crayon.setColor(Color.BLUE);
		for (Missile missile : this.spaceInvaders.missiles()) {
			crayon.fillRect(missile.abscisseLaPlusAGauche(),missile.ordonneeLaPlusBasse()+Constante.ESPACEJEU_SCORE,missile.dimension().longueur(),missile.dimension().hauteur());
		}
		crayon.setColor(Color.RED);
		for (Missile missileEnvahisseur : this.spaceInvaders.missilesEnvahisseurs()) {
			crayon.fillRect(missileEnvahisseur.abscisseLaPlusAGauche(),missileEnvahisseur.ordonneeLaPlusHaute()+Constante.ESPACEJEU_SCORE,missileEnvahisseur.dimension().hauteur(),missileEnvahisseur.dimension().longueur());
		}
	}
}
