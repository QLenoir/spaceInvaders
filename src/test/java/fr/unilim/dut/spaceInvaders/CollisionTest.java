package fr.unilim.dut.spaceInvaders;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import fr.unilim.dut.spaceInvaders.model.Collision;
import fr.unilim.dut.spaceInvaders.model.Dimension;
import fr.unilim.dut.spaceInvaders.model.Position;
import fr.unilim.dut.spaceInvaders.model.SpaceInvaders;

public class CollisionTest {

	private SpaceInvaders spaceinvaders;
	private Collision collision;

	@Before
	public void initialisation() {
		spaceinvaders = new SpaceInvaders(15, 10);
		collision = new Collision(spaceinvaders);
	}

	@Test
	public void test_envahisseur_disparait_apres_collision_avec_missile () {
		
		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 1);
		spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(2,2),new Position(8,1), 1);
		
		spaceinvaders.tirerUnMissile(new Dimension(3,2),2);
		spaceinvaders.deplacerMissile();
		spaceinvaders.deplacerMissile();
		spaceinvaders.deplacerMissile();

		collision.detecterCollision(spaceinvaders.envahisseur(),spaceinvaders.missile());
		
		if (spaceinvaders.envahisseur().estDetruit()) {
			spaceinvaders.enleverEnvahisseur();
		}
		
		if (spaceinvaders.missile().estDetruit()) {
			spaceinvaders.enleverMissile();
		}
		
		assertEquals("" +
				"...............\n" + 
				"...............\n" +
				"...............\n" + 
				"...............\n" +
				"...............\n" +
				"...............\n" + 
				"...............\n" +
				"...............\n" + 
				".....VVVVVVV...\n" + 
				".....VVVVVVV...\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}
}

