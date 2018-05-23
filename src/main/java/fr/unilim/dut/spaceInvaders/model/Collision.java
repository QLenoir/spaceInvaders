package fr.unilim.dut.spaceInvaders.model;

public class Collision {

	public boolean detecterCollision(Sprite sprite1, Sprite sprite2) {
		int i = sprite1.abscisseLaPlusAGauche();
		int j = sprite1.ordonneeLaPlusBasse();
		boolean fini = false;
		while (	i <= sprite1.abscisseLaPlusADroite() && !fini) {
				while ( j<= sprite1.ordonneeLaPlusHaute() && !fini) {
					if (sprite2.occupeLaPosition(i, j)) {
						fini = true;
					}
					j++;
				}  
				j = sprite1.ordonneeLaPlusBasse();
				i++;
			}
		return fini;
		}
}
