package fr.unilim.dut.spaceInvaders;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import fr.unilim.dut.spaceInvaders.model.Dimension;
import fr.unilim.dut.spaceInvaders.model.Position;
import fr.unilim.dut.spaceInvaders.model.SpaceInvaders;

public class CollisionTest {

	private SpaceInvaders spaceinvaders;

	@Before
	public void initialisation() {
		spaceinvaders = new SpaceInvaders(15, 10);
	}
 
	@Test
	public void test_envahisseur_disparait_apres_collision_avec_missile_de_face () {
		
		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 1);
		spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(2,2),new Position(8,1), 1);
		
		spaceinvaders.tirerUnMissileVaisseau(new Dimension(3,2),2);
		spaceinvaders.deplacerMissile();
		spaceinvaders.deplacerMissile();
		spaceinvaders.deplacerMissile();

		this.spaceinvaders.detecterAutomatiquementCollisions();

		this.spaceinvaders.destructionAutomatiquementSprite();
		
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
		
		spaceinvaders.tirerUnMissileVaisseau(new Dimension(3,2),2);
		spaceinvaders.deplacerMissile();
		spaceinvaders.deplacerMissile();
		spaceinvaders.deplacerMissile();

		this.spaceinvaders.detecterAutomatiquementCollisions();

		this.spaceinvaders.destructionAutomatiquementSprite();

		
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
	public void test_vaisseau_disparait_apres_collision_avec_missile_de_face () {
		
		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 1);
		spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(2,2),new Position(6,1), 1);
		
		spaceinvaders.tirerUnMissileEnvahisseur(new Dimension(2,2), spaceinvaders.envahisseurs().get(spaceinvaders.envahisseurs().size()-1),1);
		
		spaceinvaders.deplacerAutomatiquementSprite();
		spaceinvaders.deplacerAutomatiquementSprite();
		spaceinvaders.deplacerAutomatiquementSprite();
		spaceinvaders.deplacerAutomatiquementSprite();
		spaceinvaders.deplacerAutomatiquementSprite();
		
		this.spaceinvaders.detecterAutomatiquementCollisions();

		this.spaceinvaders.destructionAutomatiquementSprite();

		
		assertEquals("" +
				"...........EE..\n" + 
				"...........EE..\n" +
				"...............\n" + 
				"...............\n" +
				"...............\n" +
				"...............\n" + 
				"...............\n" +
				"...............\n" + 
				"...............\n" + 
				"...............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}
}

