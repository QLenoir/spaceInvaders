package fr.unilim.dut.spaceInvaders.model;

public class Collision {

	public boolean detecterCollision(Sprite sprite1, Sprite sprite2) {
		int x = sprite1.abscisseLaPlusAGauche();
		int y = sprite1.ordonneeLaPlusBasse();
		boolean fini = false;
		while (	x <= sprite1.abscisseLaPlusADroite() && !fini) {
				while ( y <= sprite1.ordonneeLaPlusHaute() && !fini) {
					if (sprite2.occupeLaPosition(x, y)) {
						fini = true;
					}
					y++;
				}  
				y = sprite1.ordonneeLaPlusBasse();
				x++;
			}
		return fini;
		}
}
