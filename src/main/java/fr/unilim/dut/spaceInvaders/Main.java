package fr.unilim.dut.spaceInvaders;

import fr.unilim.dut.spaceInvaders.model.Constante;
import fr.unilim.dut.spaceInvaders.model.SpaceInvaders;
import fr.unilim.dut.spaceInvaders.moteurjeu.DessinSpaceInvaders;
import fr.unilim.dut.spaceInvaders.moteurjeu.MoteurGraphique;

public class Main {
	
	public static void main(String[] args) throws InterruptedException {
		
		SpaceInvaders jeu = new SpaceInvaders(Constante.ESPACEJEU_LONGUEUR,Constante.ESPACEJEU_HAUTEUR);
		DessinSpaceInvaders aff = new DessinSpaceInvaders(jeu);

		
		jeu.initialiserJeu();
		// classe qui lance le moteur de jeu generique
		MoteurGraphique moteur = new MoteurGraphique(jeu, aff);
		moteur.lancerJeu(Constante.ESPACEJEU_LONGUEUR,Constante.ESPACEJEU_HAUTEUR+Constante.ESPACEJEU_SCORE);
	}
}
