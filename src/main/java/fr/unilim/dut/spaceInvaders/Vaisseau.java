package fr.unilim.dut.spaceInvaders;

public class Vaisseau {

	int x;
    int y;
    int longueur;
    int hauteur;

    public Vaisseau(int longueur, int hauteur, int x, int y) {
 	   this.longueur=longueur;
 	   this.hauteur=hauteur;
 	   this.x = x;
 	   this.y = y;
     }
    
    public Vaisseau(int longueur, int hauteur) {
		this(longueur, hauteur, 0, 0);
	}
    
    public boolean occupeLaPosition(int x, int y) {
	     if ((this.abscisseLaPlusAGauche()<=x) && (x<=abscisseLaPlusADroite())) 
		      if ( (ordoneeLaPlusHaute()-this.hauteur+1<=y) && (y<=ordoneeLaPlusHaute()))
			  return true;
		
	     return false;
    }

	private int ordoneeLaPlusHaute() {
		return this.y;
	}

	public int abscisseLaPlusADroite() {
		return this.x+this.longueur-1;
	}

	public void seDeplacerVersLaDroite() {
	}
	
	public void seDeplacerVersLaGauche() {
		this.x=x-1;
	}
	
	public int abscisseLaPlusAGauche() {
		return this.x;
	}
	
	public void positionner(int x, int y) {
	    this.x = x;
	    this.y = y;
    }
}