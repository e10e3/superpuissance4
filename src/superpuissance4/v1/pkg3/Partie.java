/*
 * 
 * Émile ROYER
 * TP 3, v1.3
 * 
 */
package superpuissance4.v1.pkg3;

import java.util.Random;
import java.util.Scanner;

/**
 * Implémente les parties. Elles ont une grille et deux joueurs.
 * @author Émile ROYER
 */
public class Partie {
    
    Joueur [] ListeJoueur = new Joueur[2];
    Grille grilleJeu;
    Joueur joueurCourant;
   
	/**
	 * Attribue aléatoirement des couleurs aux joueurs
	 */
    public void attribuerCouleursAuxJoueurs() {
        Scanner sc = new Scanner (System.in);
		Random rnd = new Random();
		int index;
		
        System.out.print("Entrez le nom du premier joueur : ");
        ListeJoueur[0] = new Joueur(sc.nextLine());
        
        System.out.print("Entrez le nom du deuxième joueur : ");
        ListeJoueur[1] = new Joueur(sc.nextLine());
		
		index = rnd.nextInt(2);
		
		/* On colore le nom des couleurs pour plus de lisibilité */
		ListeJoueur[index].affecterCouleur("\033[91mrouge\033[0m");
		ListeJoueur[1-index].affecterCouleur("\033[93mjaune\033[0m");
		
		System.out.println(ListeJoueur[0].Nom + " prend la couleur " +
				ListeJoueur[0].lireCouleur() + ", tandis que " + ListeJoueur[1].Nom +
				" prend la couleur " + ListeJoueur[1].lireCouleur()+".");
		
		/*
		* On colore le nom des joueurs avec leur couleur pour plus de lisibilité
		*/
		ListeJoueur[index].Nom = "\033[91m" + ListeJoueur[index].Nom + "\033[0m";
		ListeJoueur[1-index].Nom = "\033[93m" + ListeJoueur[1-index].Nom + "\033[0m";
    }
	
	/**
	 * Crée la grille, la vide si elle existait déjà, place les trous noirs et
	 * les désintégrateurs, crée les jetons et les attribue aux joueurs
	 * correspondants.
	 */
    public void initialiserPartie() {
		Random rnd = new Random();
		
		/* Crée la grille si elle n'existait pas, et la vide si elle existait */
        grilleJeu = new Grille();
		
		for (int i = 0; i < ListeJoueur.length; i++) {
			for (int j = 0; j < ListeJoueur[i].ListeJetons.length; j++) {
				ListeJoueur[i].ListeJetons[j] = new Jeton(ListeJoueur[i].lireCouleur());
			}
			ListeJoueur[i].nombreJetonsRestants = 21;
		}
		
		/*
		* Placement des trous noirs.
		* Si il y a déjà un trou noir à l'endroit où on peut en placer un, on
		* réessaye à un autre endroit.
		* On en profite pour placer les deux désintégrateurs cachés.
		*/
		for (int k = 0; k < 5; k++) {
			int i = rnd.nextInt(grilleJeu.nb_lignes);
			int j = rnd.nextInt(grilleJeu.nb_colonnes);
			if (grilleJeu.Cellules[i][j].presenceTrouNoir()) {
				k--;
			} else {
				grilleJeu.placerTrouNoir(i, j);
				if (k < 2) {
					grilleJeu.placerDesintegrateur(i, j);
				}
			}
		}
		
		/* Placement des 3 désintégrateurs restants */
		for (int k = 0; k < 3; k++) {
			int i = rnd.nextInt(grilleJeu.nb_lignes);
			int j = rnd.nextInt(grilleJeu.nb_colonnes);
			
			/*
			* Il ne faut pas qu'il y ait un désintégrateur sur la case, ni un
			* trou noir ()seulement deux désintégrateurs sur des cases de trou noir).
			*/
			if (grilleJeu.Cellules[i][j].presenceDesintegrateur() || grilleJeu.Cellules[i][j].presenceTrouNoir()) {
				k--;
			} else {
				grilleJeu.placerDesintegrateur(i, j);
			}
		}
		
    }
    
	/**
	 * Lance la partie en tirant au hasard le joueur qui commence.
	 */
    public void debuterPartie() {
		Random rnd = new Random();
		int index;
		boolean finDePartie = false;
		
		attribuerCouleursAuxJoueurs();
		initialiserPartie();
        
		index = rnd.nextInt(2);
		joueurCourant = ListeJoueur[index];
		System.out.println("Le joueur " + joueurCourant.lireCouleur()
				+ " commence la partie !");
		grilleJeu.afficherGrillesurConsole();
		
		while (!finDePartie) {
			joueurCourant = ListeJoueur[index];
			finDePartie = tourDeJeu(joueurCourant);
			if (!finDePartie) {
				if (grilleJeu.estRemplie()) {
					/* Si la grille est remplie, la partie ne peut pas continuer */
					finDePartie = true;
					System.out.print("La grille est pleine ! Félicitations aux deux joueurs, le match est nul.");
				} else {
					/* Changement de joueur */
					index = ++index%2;
				}
			}
		}
    }
	
	/**
	 * Effectue le tour de jeu d'un joueur, en le guidant dans les actions.
	 * @param joueur Le joueur dont c'est le tour
	 * @return Si la partie a été gagnée par le joueur
	 */
	/*
	* Si le joueur essaie de jouer en dehors de la zone de jeu, il ne pert pas
	* son jeton mais passe son tour et est invité à recommencer.
	* Le comportement est similaire s'il essaie de récupérer un jeton qui 
	* n'existe pas.
	*/
	public boolean tourDeJeu(Joueur joueur) {
		Scanner sc = new Scanner(System.in);
		String reponse;
		boolean partieGagnee;
		
		System.out.println(joueur.Nom + ", c'est votre tour de jouer.");
		System.out.print("Que voulez-vous faire ? [j(ouer)|r(écupérer)");
		if (joueur.nombreDesintegrateurs > 0) {
			System.out.print("|d(ésintégrer)");
		}
		System.out.print("] ");
		reponse = sc.nextLine();
		
		/* Jouer un jeton */
		if (reponse.equals("jouer") || reponse.equals("j")) {
				int colonne_jouee; /* le numéro de la clonne jouée */
				boolean succes_jeu; /* si le jeton a pu être placé (colonne pleine) */ 
				Jeton jeton_joue;
				
				System.out.print("Où voulez-vous placer votre jeton ? (1-" + grilleJeu.nb_colonnes + ") ");
				colonne_jouee = sc.nextInt()-1;
				if (colonne_jouee >= grilleJeu.nb_colonnes) {
					System.out.println("Votre jeton tombe en dehors de l'espace de jeu."
							+ " Vous le conservez, mais visez mieux la prochaine fois ! ;)");
					return false;
				} else {
					jeton_joue = joueur.ListeJetons[joueur.nombreJetonsRestants-1];
					succes_jeu = grilleJeu.ajouterJetonDansColonne(jeton_joue, joueur, colonne_jouee);
					if (!succes_jeu) {
						System.out.println("Il n'y a plus de place dans cette colonne. Gardez votre jeton, "
								+ "mais jouez-le mieux la prochaine fois !");
						return false;
					} else {
						joueur.enleverJeton();
						
						grilleJeu.tasserGrille(colonne_jouee);
						grilleJeu.afficherGrillesurConsole();
						partieGagnee = grilleJeu.estGagnantePourJoueur(joueur);
						if (partieGagnee) {
							System.out.println("Félicitations "+joueur.Nom+" ! Vous avez gagné la partie.");
						}
						return partieGagnee;
					}
				}
				
		/* Récupérer un jeton */
		} else if (reponse.equals("récupérer") || reponse.equals("r")) {
				int coord_i;
				int coord_j;
				boolean joueur1Gagne;
				boolean joueur2Gagne;
				
				System.out.print("Entrez la ligne du jeton à récupérer : ");
				coord_i = sc.nextInt()-1;
				System.out.print("Entrez la colonne du jeton à récupérer : ");
				coord_j = sc.nextInt()-1;
				if (!grilleJeu.celluleOccupee(coord_i,coord_j)) {
					System.out.println("Il n'y a pas de jeton à récupérer aux coordonnées indiquées. Vous ferez mieux la prochaine fois !");
					return false;
				} else if (!grilleJeu.Cellules[coord_i][coord_j].recupererJeton().lireCouleur().equals(joueur.lireCouleur())) {
					System.out.println("Vous ne pouvez pas récupérer un jeton de votre adversaire.");
					return false;
				} else {
					joueur.ajouterJeton(grilleJeu.recupererJeton(coord_i, coord_j));
					grilleJeu.tasserGrille(coord_j);
					grilleJeu.afficherGrillesurConsole();
					joueur1Gagne = grilleJeu.estGagnantePourJoueur(ListeJoueur[0]);
					joueur2Gagne = grilleJeu.estGagnantePourJoueur(ListeJoueur[1]);
					if (joueur1Gagne && joueur2Gagne) {
						System.out.println("Quel dommage "+joueur.Nom+", vous"
								+ " avez perdu. Votre action a eu trop de"
								+ " conséquences.");
						return true;
					} else if (joueur1Gagne) {
						System.out.println("Félicitations "+ListeJoueur[0].Nom+" ! Vous avez gagné la partie.");
						return true;
					} else if (joueur2Gagne) {
						System.out.println("Félicitations "+ListeJoueur[1].Nom+" ! Vous avez gagné la partie.");
						return true;
					} else {
						return false;
					}
				}
				
		/* Désintégrer un jeton */
		} else if (reponse.equals("désintégrer") || reponse.equals("d")) {
			int coord_i;
				int coord_j;
				boolean joueur1Gagne;
				boolean joueur2Gagne;
				
				System.out.print("Entrez la ligne du jeton à récupérer : ");
				coord_i = sc.nextInt()-1;
				System.out.print("Entrez la colonne du jeton à récupérer : ");
				coord_j = sc.nextInt()-1;
				if (!grilleJeu.celluleOccupee(coord_i,coord_j)) {
					System.out.println("Il n'y a pas de jeton à récupérer aux coordonnées indiquées. Vous ferez mieux la prochaine fois !");
					return false;
				} else if (grilleJeu.Cellules[coord_i][coord_j].recupererJeton().lireCouleur().equals(joueur.lireCouleur())) {
					System.out.println("Vous ne pouvez pas désintégrer un de vos propre jetons.");
					return false;
				} else {
					grilleJeu.supprimerJeton(coord_i, coord_j);
					joueur.utiliserDesintegrateur();
					grilleJeu.tasserGrille(coord_j);
					grilleJeu.afficherGrillesurConsole();
					joueur1Gagne = grilleJeu.estGagnantePourJoueur(ListeJoueur[0]);
					joueur2Gagne = grilleJeu.estGagnantePourJoueur(ListeJoueur[1]);
					if (joueur1Gagne && joueur2Gagne) {
						System.out.println("Quel dommage "+joueur.Nom+", vous"
								+ " avez perdu. Votre action a eu trop de"
								+ " conséquences.");
						return true;
					} else if (joueur1Gagne) {
						System.out.println("Félicitations "+ListeJoueur[0].Nom+" ! Vous avez gagné la partie.");
						return true;
					} else if (joueur2Gagne) {
						System.out.println("Félicitations "+ListeJoueur[1].Nom+" ! Vous avez gagné la partie.");
						return true;
					} else {
						return false;
					}
				}
		} else {
				System.out.println("Cette option n'est pas valide. Vérifiez votre clavier pour le prochain tour !");
				return false;
		}
	}
    
}
