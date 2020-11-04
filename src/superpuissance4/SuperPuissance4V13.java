/*
 * 
 * Émile ROYER
 * TP 3, v1.3
 * 
 */
package superpuissance4;

/**
 * Implémente la logique principale du Puissance 4
 * Contrôle le démarrage et l'arrêt du jeu
 * @author Émile ROYER
 */
public class SuperPuissance4V13 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {		
		/* On crée la partie et on l'initialise */
		Partie partie = new Partie();
		
		System.out.println("Bienvenue dans le jeu du Super Puissance 4 !");
		
		/* On débute la partie */
		partie.debuterPartie();
		
		System.out.println("Ce jeu a été réalisé par Émile ROYER. Merci d'y avoir joué !");
    }
}
