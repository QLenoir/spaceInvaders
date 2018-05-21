package fr.unilim.dut.spaceInvaders;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import fr.unilim.dut.spaceInvaders.model.Collision;
import fr.unilim.dut.spaceInvaders.model.Dimension;
import fr.unilim.dut.spaceInvaders.model.Envahisseur;
import fr.unilim.dut.spaceInvaders.model.Missile;
import fr.unilim.dut.spaceInvaders.model.Position;
import fr.unilim.dut.spaceInvaders.model.SpaceInvaders;

public class CollisionTest {

	private SpaceInvaders spaceinvaders;
	private Collision collision;

	@Before
	public void initialisation() {
		spaceinvaders = new SpaceInvaders(15, 10);
		collision = new Collision();
	}
 
	@Test
	public void test_envahisseur_disparait_apres_collision_avec_missile_de_face () {
		
		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 1);
		spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(2,2),new Position(8,1), 1);
		
		spaceinvaders.tirerUnMissile(new Dimension(3,2),2);
		spaceinvaders.deplacerMissile();
		spaceinvaders.deplacerMissile();
		spaceinvaders.deplacerMissile();

		for (Missile missile : spaceinvaders.missiles()) {
			for (Envahisseur envahisseur : spaceinvaders.envahisseurs()) {
				collision.detecterCollision(envahisseur,missile);
			}
		}
		
		
		Iterator<Envahisseur> iterator = spaceinvaders.envahisseurs().iterator();

		while (iterator.hasNext()) {
		    Envahisseur envahisseur = iterator.next();

		    if (envahisseur.estDetruit())
		        iterator.remove();
		}
		
		Iterator<Missile> iterator2 = spaceinvaders.missiles().iterator();

		while (iterator2.hasNext()) {
		    Missile missile = iterator2.next();

		    if (missile.estDetruit())
		        iterator2.remove();
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
	
	@Test
	public void test_envahisseur_disparait_apres_collision_avec_missile_de_cote () {
		
		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 1);
		spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(2,2),new Position(6,1), 1);
		
		spaceinvaders.tirerUnMissile(new Dimension(3,2),2);
		spaceinvaders.deplacerMissile();
		spaceinvaders.deplacerMissile();
		spaceinvaders.deplacerMissile();

		for (Missile missile : spaceinvaders.missiles()) {
			for (Envahisseur envahisseur : spaceinvaders.envahisseurs()) {
				collision.detecterCollision(envahisseur,missile);
			}
		}
		
		
		Iterator<Envahisseur> iterator = spaceinvaders.envahisseurs().iterator();

		while (iterator.hasNext()) {
		    Envahisseur envahisseur = iterator.next();

		    if (envahisseur.estDetruit())
		        iterator.remove();
		}
		
		
		Iterator<Missile> iterator2 = spaceinvaders.missiles().iterator();

		while (iterator2.hasNext()) {
		    Missile missile = iterator2.next();

		    if (missile.estDetruit())
		        iterator2.remove();
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

