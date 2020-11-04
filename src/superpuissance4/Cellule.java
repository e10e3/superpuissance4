/*
 * 
 * Émile ROYER
 * TP 3, v1.3
 * 
 */
package superpuissance4;

/**
 * Implémente les cellules, qui peuvent contenir un jeton, un trou noir et un
 * désintégrateur.
 * @author Émile ROYER
 */
public class Cellule {
    
    Jeton jetonCourant;
    boolean trouNoir;
    boolean desintegrateur;
    
	/**
	 * Construteur initialisant les attributs avec des valeurs par défaut
	 */
    public Cellule() {
        jetonCourant = null;
        trouNoir = false;
        desintegrateur = false;
    }
    
	/**
	 * Ajoute le jeton en paramètre à la cellule, et retourne vrai si l’ajout
	 * s’est bien passé, ou faux sinon
	 * @param jeton Le jeton à mettre dans la cellule
	 * @return Succès de l'opération
	 */
    public boolean affecterJeton(Jeton jeton) {
        if (jetonCourant != null) {
            return false;
        }
        else {
            jetonCourant = jeton;
            return true;
        }
    }
    
	/**
	 * Donne le jeton contenu dans la cellule, sans le supprimer.
	 * @return Le jeton contenu dans la cellule
	 */
	public Jeton recupererJeton() {
        return jetonCourant;
    }
    
	/**
	 * Supprime le jeton contenu dans la cellule, si elle en contient.
	 * @return Succès de l'opération
	 */
    public boolean supprimerJeton() {
        if (jetonCourant == null) {
            return false;
        }
        else {
            jetonCourant = null;
            return true;
        }
    }
    
	/**
	 * Ajoute un trou noir dans la cellule.
	 * @return Succès de l'opération
	 */
    public boolean placerTrouNoir() {
        if (trouNoir) {
            return false;
        }
        else {
            trouNoir = true;
            return true;
        }
    }
    
	/**
	 * Ajoute un désintégrateur dans la cellule.
	 * @return Succès de l'opération
	 */
    public boolean placerDesintegrateur() {
        if (desintegrateur) {
            return false;
        }
        else {
            desintegrateur = true;
            return true;
        }
    }
    
	/**
	 * Vérifie la présence d'un trou noir dans la cellule.
	 * @return Présence d'un trou noir
	 */
    public boolean presenceTrouNoir() {
        return trouNoir;
    }
    
	/**
	 * Vérifie la présence d'un désintégrateur dans la cellule.
	 * @return Présence d'un désintégrateur
	 */
    public boolean presenceDesintegrateur() {
        return desintegrateur;
    }
    
	/**
	 * Renvoie la couleur du jeton qui occupe la cellule.
	 * @return La couleur du jeton
	 */
    public String lireCouleurDuJeton() {
        return jetonCourant.Couleur;
    }
    
	/**
	 * Supprime le désintégrateur présent de la cellule.
	 * @return Succès de l'opération
	 */
    public boolean recupererDesintegrateur() {
        if (!desintegrateur) {
            return false;
        }
        else {
            desintegrateur = false;
			System.out.println("Vous avez récupéré un désintégrateur.");
            return true;
        }
    }
    
	/**
	 * Active le trou noir : le trou noir engloutit le jeton et disparait.
	 * @return Succès de l'opération
	 */
    public boolean activerTrouNoir() {
        if (!trouNoir) {
            return false;
        }
        else {
            trouNoir = false;
            jetonCourant = null;
			System.out.println("Votre jeton s'est fait aspirer par un trou noir !");
            return true;
        }
    }
    
}
