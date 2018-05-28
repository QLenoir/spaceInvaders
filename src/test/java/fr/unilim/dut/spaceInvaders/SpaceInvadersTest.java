package fr.unilim.dut.spaceInvaders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import fr.unilim.dut.spaceInvaders.model.Collision;
import fr.unilim.dut.spaceInvaders.model.Constante;
import fr.unilim.dut.spaceInvaders.model.Dimension;
import fr.unilim.dut.spaceInvaders.model.Envahisseur;
import fr.unilim.dut.spaceInvaders.model.Missile;
import fr.unilim.dut.spaceInvaders.model.Position;
import fr.unilim.dut.spaceInvaders.model.SpaceInvaders;
import fr.unilim.dut.spaceInvaders.utils.DebordementEspaceJeuException;
import fr.unilim.dut.spaceInvaders.utils.HorsEspaceJeuException;
import fr.unilim.dut.spaceInvaders.utils.MissileException;

public class SpaceInvadersTest {

	private SpaceInvaders spaceinvaders;

	@Before
	public void initialisation() {
		spaceinvaders = new SpaceInvaders(15, 10);
	}

	@Test
	public void test_AuDebut_JeuSpaceInvaderEstVide() {
		assertEquals("" + 
				"...............\n" + 
				"...............\n" +
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"...............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}

	@Test
	public void test_unNouveauVaisseauEstCorrectementPositionneDansEspaceJeu() {
		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(1,1),new Position(7,9), 1);
		assertEquals("" + 
				"...............\n" + 
				"...............\n" +
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				".......V.......\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}

	@Test
	public void test_UnNouveauVaisseauPositionneHorsEspaceJeu_DoitLeverUneException() {
 
		try {
			spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(1,1), new Position(15,9), 1);
			fail("Position trop à droite : devrait déclencher une exception HorsEspaceJeuException");
		} catch (final HorsEspaceJeuException e) {
		}


		try {
			spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(1,1),new Position(-1,9), 1);
			fail("Position trop à gauche : devrait déclencher une exception HorsEspaceJeuException");
		} catch (final HorsEspaceJeuException e) {
		}


		try {
			spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(1,1),new Position(14,10), 1);
			fail("Position trop en bas : devrait déclencher une exception HorsEspaceJeuException");
		} catch (final HorsEspaceJeuException e) {
		}


		try {
			spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(1,1), new Position(14,-1), 1);
			fail("Position trop à haut : devrait déclencher une exception HorsEspaceJeuException");
		} catch (final HorsEspaceJeuException e) {
		}

	}

	@Test
	public void test_unNouveauVaisseauAvecDimensionEstCorrectementPositionneDansEspaceJeu() {
		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,2),new Position(7,9), 1);
		assertEquals("" + 
				"...............\n" + 
				"...............\n" +
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				".......VVV.....\n" + 
				".......VVV.....\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}

	@Test
	public void test_UnNouveauVaisseauPositionneDansEspaceJeuMaisAvecDimensionTropGrande_DoitLeverUneExceptionDeDebordement() {

		try {
			spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(9,2), new Position(7,9), 1);
			fail("Dépassement du vaisseau à droite en raison de sa longueur trop importante : devrait déclencher une exception DebordementEspaceJeuException");
		} catch (final DebordementEspaceJeuException e) {
		}


		try {
			spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,4), new Position(7,1), 1);
			fail("Dépassement du vaisseau vers le haut en raison de sa hauteur trop importante : devrait déclencher une exception DebordementEspaceJeuException");
		} catch (final DebordementEspaceJeuException e) {
		}

	}


	@Test
	public void test_VaisseauImmobile_DeplacerVaisseauVersLaDroite() {

		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,2),new Position(12,9), 3);
		spaceinvaders.deplacerVaisseauVersLaDroite();
		assertEquals("" + 
				"...............\n" + 
				"...............\n" +
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"............VVV\n" + 
				"............VVV\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}

	@Test
	public void VaisseauAvance_DeplacerVaisseauVersLaGauche() {

		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,2),new Position(7,9), 3);
		spaceinvaders.deplacerVaisseauVersLaGauche();

		assertEquals("" + 
				"...............\n" + 
				"...............\n" +
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"....VVV........\n" + 
				"....VVV........\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}

	@Test
	public void VaisseauImmobile_DeplacerVaisseauVersLaGauche() {

		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,2), new Position(0,9), 3);
		spaceinvaders.deplacerVaisseauVersLaGauche();

		assertEquals("" + 
				"...............\n" + 
				"...............\n" +
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"VVV............\n" + 
				"VVV............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}

	@Test
	public void test_VaisseauAvance_DeplacerVaisseauVersLaDroite() {

		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,2),new Position(7,9),3);
		spaceinvaders.deplacerVaisseauVersLaDroite();
		assertEquals("" + 
				"...............\n" + 
				"...............\n" +
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"..........VVV..\n" + 
				"..........VVV..\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}

	@Test
	public void test_VaisseauAvancePartiellement_DeplacerVaisseauVersLaDroite() {

		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,2),new Position(10,9),3);
		spaceinvaders.deplacerVaisseauVersLaDroite();
		assertEquals("" + 
				"...............\n" + 
				"...............\n" +
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"............VVV\n" + 
				"............VVV\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}

	@Test
	public void test_VaisseauAvancePartiellement_DeplacerVaisseauVersLaGauche() {

		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3,2),new Position(1,9), 3);
		spaceinvaders.deplacerVaisseauVersLaGauche();

		assertEquals("" + 
				"...............\n" + 
				"...............\n" +
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"VVV............\n" + 
				"VVV............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}

	@Test
	public void test_MissileBienTireDepuisVaisseau_VaisseauLongueurImpaireMissileLongueurImpaire() {

		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 2);
		spaceinvaders.tirerUnMissileVaisseau(new Dimension(3,2),2);

		assertEquals("" + 
				"...............\n" + 
				"...............\n" +
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				".......MMM.....\n" + 
				".......MMM.....\n" + 
				".....VVVVVVV...\n" + 
				".....VVVVVVV...\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}

	@Test(expected = MissileException.class)
	public void test_PasAssezDePlacePourTirerUnMissile_UneExceptionEstLevee() throws Exception { 
		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 1);
		spaceinvaders.tirerUnMissileVaisseau(new Dimension(7,9),1);
	}

	@Test
	public void test_MissileAvanceAutomatiquement_ApresTirDepuisLeVaisseau() {

		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 2);
		spaceinvaders.tirerUnMissileVaisseau(new Dimension(3,2),2);

		spaceinvaders.deplacerMissile();

		assertEquals("" + 
				"...............\n" + 
				"...............\n" +
				"...............\n" + 
				"...............\n" + 
				".......MMM.....\n" + 
				".......MMM.....\n" + 
				"...............\n" + 
				"...............\n" + 
				".....VVVVVVV...\n" + 
				".....VVVVVVV...\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}


	@Test
	public void test_MissileDisparait_QuandIlCommenceASortirDeEspaceJeu() {

		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 1);
		spaceinvaders.tirerUnMissileVaisseau(new Dimension(3,2),1);
		for (int i = 1; i <=6 ; i++) {
			spaceinvaders.deplacerMissile();
		}

		spaceinvaders.deplacerMissile();

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
	public void test_EnvahisseurBienPlace() {

		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 2);
		spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(2,2),new Position(1,1), 2);

		assertEquals("" + 
				".EE............\n" + 
				".EE............\n" +
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
	public void test_UnNouveauEnvahisseurPositionneDansEspaceJeuMaisAvecDimensionTropGrande_DoitLeverUneExceptionDeDebordement() {

		try {
			spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(9,2), new Position(7,9), 1);
			fail("Dépassement de l'envahisseur à droite en raison de sa longueur trop importante : devrait déclencher une exception DebordementEspaceJeuException");
		} catch (final DebordementEspaceJeuException e) {
		}


		try {
			spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(3,4), new Position(7,1), 1);
			fail("Dépassement de l'envahisseur vers le haut en raison de sa hauteur trop importante : devrait déclencher une exception DebordementEspaceJeuException");
		} catch (final DebordementEspaceJeuException e) {
		}

	}

	@Test
	public void test_UnNouveauEnvahisseurPositionneHorsEspaceJeu_DoitLeverUneException() {

		try {
			spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(1,1), new Position(15,9), 1);
			fail("Position trop à droite : devrait déclencher une exception HorsEspaceJeuException");
		} catch (final HorsEspaceJeuException e) {
		}


		try {
			spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(1,1),new Position(-1,9), 1);
			fail("Position trop à gauche : devrait déclencher une exception HorsEspaceJeuException");
		} catch (final HorsEspaceJeuException e) {
		}


		try {
			spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(1,1),new Position(14,10), 1);
			fail("Position trop en bas : devrait déclencher une exception HorsEspaceJeuException");
		} catch (final HorsEspaceJeuException e) {
		}


		try {
			spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(1,1), new Position(14,-1), 1);
			fail("Position trop à haut : devrait déclencher une exception HorsEspaceJeuException");
		} catch (final HorsEspaceJeuException e) {
		}

	}

	@Test
	public void test_EnvahisseurAvanceAutomatiquement_ApresLancementDuJeu() {

		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 2);
		spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(2,2),new Position(1,1), 1);

		spaceinvaders.deplacerEnvahisseur();

		assertEquals("" + 
				"..EE...........\n" + 
				"..EE...........\n" +
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
	public void test_EnvahisseurChangeDeSens_QuandIlCommenceASortirDeEspaceJeuADroite() {

		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 1);
		spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(2,2),new Position(1,1), 1);
		for (int i = 1; i <=12 ; i++) {
			spaceinvaders.deplacerEnvahisseur();
		}

		spaceinvaders.deplacerEnvahisseur();

		assertEquals("" +
				"............EE.\n" + 
				"............EE.\n" +
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
	public void test_EnvahisseurChangeDeSens_QuandIlCommenceASortirDeEspaceJeuAGauche() {

		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 1);
		spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(2,2),new Position(1,1), 1);
		
		for (Envahisseur envahisseur : this.spaceinvaders.envahisseurs()) {
			envahisseur.setDirectionAGauche(true);
		}

		spaceinvaders.deplacerEnvahisseur();
		spaceinvaders.deplacerEnvahisseur();
		spaceinvaders.deplacerEnvahisseur();

		assertEquals("" +
				"..EE...........\n" + 
				"..EE...........\n" +
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
	public void test_Tirer_Plusieurs_Missiles() {

		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 2);
		spaceinvaders.tirerUnMissileVaisseau(new Dimension(3,2),2);

		spaceinvaders.deplacerMissile();
		
		spaceinvaders.tirerUnMissileVaisseau(new Dimension(3,2),2);

		assertEquals("" + 
				"...............\n" + 
				"...............\n" +
				"...............\n" + 
				"...............\n" + 
				".......MMM.....\n" + 
				".......MMM.....\n" + 
				".......MMM.....\n" + 
				".......MMM.....\n" + 
				".....VVVVVVV...\n" + 
				".....VVVVVVV...\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}
	
	@Test
	public void test_Tirer_Plusieurs_Missiles_Qui_Se_Chevauchent() {

		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 2);
		spaceinvaders.tirerUnMissileVaisseau(new Dimension(3,2),1);

		spaceinvaders.deplacerMissile();
		spaceinvaders.tirerUnMissileVaisseau(new Dimension(3,2),1);

		assertEquals("" + 
				"...............\n" + 
				"...............\n" +
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				".......MMM.....\n" + 
				".......MMM.....\n" + 
				"...............\n" + 
				".....VVVVVVV...\n" + 
				".....VVVVVVV...\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}
	
	@Test
	public void test_Placer_Plusieurs_Envahisseur_sont_placés() {

		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 1);
		spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(2,2),new Position(2,1), 1);
		spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(2,2),new Position(6,1), 1);
 

		assertEquals("" +
				"..EE..EE.......\n" + 
				"..EE..EE.......\n" +
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
	public void test_Placer_Plusieurs_Envahisseur_sont_placés_et_bougent() {

		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 1);
		spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(2,2),new Position(2,1), 1);
		spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(2,2),new Position(6,1), 1);
 
		spaceinvaders.deplacerEnvahisseur();

		assertEquals("" +
				"...EE..EE......\n" + 
				"...EE..EE......\n" +
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
	public void test_Placer_Plusieurs_Envahisseur_ET_Changent_De_Sens_Au_Bord_Espace_De_Jeu() {

		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 1);
		spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(2,2),new Position(8,1), 1);
		spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(2,2),new Position(12,1), 1);
 
		spaceinvaders.deplacerEnvahisseur();
		spaceinvaders.deplacerEnvahisseur();

		assertEquals("" +
				"........EE..EE.\n" + 
				"........EE..EE.\n" +
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
	public void test_Placer_Plusieurs_Envahisseur_ET_Changent_De_Sens_Au_Bord_Espace_De_Jeu_Gauche() {
 
		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 1);
		spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(2,2),new Position(2,1), 1);
		spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(2,2),new Position(5,1), 1);
		for (Envahisseur envahisseur : spaceinvaders.envahisseurs()) {
			envahisseur.setDirectionAGauche(true);
		}
		spaceinvaders.deplacerEnvahisseur();
		spaceinvaders.deplacerEnvahisseur();
		spaceinvaders.deplacerEnvahisseur();

		assertEquals("" +
				".EE.EE.........\n" + 
				".EE.EE.........\n" +
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
	public void test_Placer_Plusieurs_Envahisseur_ET_Changent_De_Sens_Au_Bord_Espace_De_Jeu_Vitesse2() {

		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 1);
		spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(2,2),new Position(8,1), 2);
		spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(2,2),new Position(12,1), 2);
 
		spaceinvaders.deplacerEnvahisseur();
		spaceinvaders.deplacerEnvahisseur();

		assertEquals("" +
				"........EE..EE.\n" + 
				"........EE..EE.\n" +
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
	public void test_Score_Augmente_Apres_Avoir_Touche_Un_Envahisseur() {

		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 2);
		spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(2,2),new Position(7,1), 1);

		spaceinvaders.deplacerEnvahisseur();
		spaceinvaders.tirerUnMissileVaisseau(new Dimension(3,2),1);
		for (int i = 1; i <=5 ; i++) {
			spaceinvaders.deplacerMissile();
		}
		for (Missile missile : spaceinvaders.missiles()) {
			for (Envahisseur envahisseur : spaceinvaders.envahisseurs()) {
				Collision collision = new Collision();
				if(collision.detecterCollision(envahisseur, missile)) {
					envahisseur.estDetruit(true);
					missile.estDetruit(true);
				}
			}

		}
		spaceinvaders.destructionAutomatiquementSprite();

		assertEquals(100,spaceinvaders.score());
	}
	
	@Test
	public void test_Envahisseur_Tire_Missile() {
		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 2);
		spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(2,2),new Position(3,1), 1);
		 
		spaceinvaders.tirerUnMissileEnvahisseur(new Dimension(2,2), spaceinvaders.envahisseurs().get(spaceinvaders.envahisseurs().size()-1), 1);
		
		assertEquals("" +
				"...EE..........\n" + 
				"...EE..........\n" +
				"...MM..........\n" + 
				"...MM..........\n" +
				"...............\n" +
				"...............\n" + 
				"...............\n" +
				"...............\n" + 
				".....VVVVVVV...\n" + 
				".....VVVVVVV...\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}
	
	@Test
	public void test_Missile_Envahisseur_Avance_Automatiquement() {
		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 2);
		spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(2,2),new Position(3,1), 1);
		 
		spaceinvaders.tirerUnMissileEnvahisseur(new Dimension(2,2), spaceinvaders.envahisseurs().get(spaceinvaders.envahisseurs().size()-1),1);
		spaceinvaders.deplacerAutomatiquementSprite();
		
		assertEquals("" +
				"....EE.........\n" + 
				"....EE.........\n" +
				"...............\n" + 
				"...MM..........\n" +
				"...MM..........\n" +
				"...............\n" + 
				"...............\n" +
				"...............\n" + 
				".....VVVVVVV...\n" + 
				".....VVVVVVV...\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}
	
	@Test
	public void test_Missile_Envahisseur_Disparait_Quant_Il_Touche_Espace_De_Jeu() {
		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 2);
		spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(2,2),new Position(3,1), 1);
		
		spaceinvaders.tirerUnMissileEnvahisseur(new Dimension(2,2), spaceinvaders.envahisseurs().get(spaceinvaders.envahisseurs().size()-1),1);
		for(int i = 0; i < 20;i++) {
			spaceinvaders.deplacerAutomatiquementSprite();
		}
		
		assertEquals("" +
				"...EE..........\n" + 
				"...EE..........\n" +
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
	public void test_Placer_Plusieurs_Lignes_Envahisseur() {
 
		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 1);
		
		spaceinvaders.positionnerUneLigneEnvahisseur(new Dimension(1,1), 2, 15, 0, 1);
		spaceinvaders.positionnerUneLigneEnvahisseur(new Dimension(1,1), 2, 15, 2, 1);
		
		spaceinvaders.deplacerEnvahisseur();
		spaceinvaders.deplacerEnvahisseur();
		spaceinvaders.deplacerEnvahisseur();

		assertEquals("" +
				".E.E.E.E.E.E.E.\n" + 
				"...............\n" +
				".E.E.E.E.E.E.E.\n" + 
				"...............\n" +
				"...............\n" +
				"...............\n" + 
				"...............\n" +
				"...............\n" + 
				".....VVVVVVV...\n" + 
				".....VVVVVVV...\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}
}