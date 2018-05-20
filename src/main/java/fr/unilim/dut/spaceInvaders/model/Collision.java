package fr.unilim.dut.spaceInvaders.model;

public class Collision {

	public void detecterCollision(Sprite sprite1, Sprite sprite2) {
		if (sprite1!=null && sprite2!=null) {
			for (int i = sprite1.abscisseLaPlusAGauche(); i <= sprite1.abscisseLaPlusADroite(); i++) {
				for (int j = sprite1.ordonneeLaPlusBasse(); j<= sprite1.ordonneeLaPlusHaute(); j++) {
					if (sprite2.occupeLaPosition(i, j)) {
						sprite1.estDetruit(true);
						sprite2.estDetruit(true);
					}
				}  
			}
		}
	}
}
