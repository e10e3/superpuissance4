/*
 * 
 * Émile ROYER
 * TP 3, v1.3
 * 
 */
package superpuissance4;

/**
 * Implémente les jetons. Un jeton n'a qu'un seul attribut : sa couleur, qui le
 * lie au joueur qui l'a placé
 * @author Émile ROYER
 */
public class Jeton {
    
    String Couleur;
	
	/**
	 * Constructeur initialisant la couleur du jeton avec le paramètre
	 * @param couleur La couleur du jeton
	 */
    public Jeton(String couleur) {
    Couleur = couleur;
}
	/**
	 * Lit la couleur du jeton.
	 * @return La couleur du jeton
	 */
    public String lireCouleur() {
        return Couleur;
    }
    
}
