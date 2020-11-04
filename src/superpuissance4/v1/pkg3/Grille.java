/*
 * 
 * Émile ROYER
 * TP 3, v1.3
 * 
 */
package superpuissance4.v1.pkg3;

/**
 * Implémente les grilles, qui sont constituées de cellules
 * @author Émile ROYER
 */
public class Grille {
    
	final int nb_lignes = 6;
	final int nb_colonnes = 7;
	
    Cellule [][] Cellules = new Cellule[nb_lignes][nb_colonnes];
	/*
	* On considère que la cellule de coordonées (0,0) est située en haut à
	* gauche de la grille.
	*/
	
	/**
	 * Constructeur qui initialise les cellules de la grille avec des valeurs
	 * par défaut (et qui crée les cellules en elles-mêmes), ce qui évite
	 * des erreurs.
	 */
	public Grille() {
		for (int i = 0; i<nb_lignes;i++) {
			for (int j = 0; j<nb_colonnes; j++) {
				Cellules[i][j] = new Cellule();
			}
		}
	}
    
	/**
	 * Ajoute le jeton dans la colonne ciblée, sur la cellule vide la plus
	 * basse. Renvoie faux si la colonne était pleine.
	 * @param jeton Le jeton à ajouter
	 * @param joueur Le joueur qui effectue l'action
	 * @param j La colonne cible
	 * @return Succès de l'opération
	 */
    public boolean ajouterJetonDansColonne(Jeton jeton, Joueur joueur, int j) {
		if (colonneRemplie(j)) return false;
		
		int i = nb_lignes-1;
		while (celluleOccupee(i,j)) {
			i--;
		}
		
		Cellules[i][j].jetonCourant = jeton;
		
		/* On active le trou noir, s'il y en a un */
		if (Cellules[i][j].presenceTrouNoir()) {
			Cellules[i][j].activerTrouNoir();
		}
		/* On récupère le désintégrateur, s'il y en a un */
		if (Cellules[i][j].presenceDesintegrateur()) {
			Cellules[i][j].recupererDesintegrateur();
			joueur.obtenirDesintegrateur();
		}
		return true;
    }
    
	/**
	 * Vérifie si la grille est pleine.
	 * @return État de grille complet de la colonne
	 */
    public boolean estRemplie() {
        for (int j = 0; j<nb_colonnes; j++) {
            if (!colonneRemplie(j)) {
                return false;
            }
        }
        return true;
    }
    
	/**
	 * Vide la grille.
	 */
    public void viderGrille() {
        for (Cellule[] Cellule : Cellules) {
            for (int j = 0; j<nb_colonnes; j++) {
                Cellule[j] = new Cellule();
            }
        }
    }
    
	/**
	 * Affiche la grille sur la console, avec jetons, trou noirs et
	 * désintégrateurs.
	 */
    public void afficherGrillesurConsole() {
		/*
		* Dans cette fonction, le numéro de colonne est représenté par 'k'
		* car 'j' représente ici la couleur jaune.
		*/
        String r = "\033[91m"; /* rouge clair */
        String j = "\033[93m"; /* jaune clair */
        String e = "\033[0m"; /* fin de couleur */
        String n = "\033[90m"; /* gris clair */
        String v = "\033[32m"; /* vert */
        String g = "\033[1m"; /* gras */
        
        String spc = " ";
      
		System.out.print("\n");
        for (int i = 0; i < nb_lignes; i++) {
			System.out.print("\t"+g+(i+1)+e);
            for (int k = 0; k < nb_colonnes; k++) {
                System.out.print(spc);
                if (celluleOccupee(i,k)) {
					switch (Cellules[i][k].lireCouleurDuJeton()) {
						case "\033[93mjaune\033[0m":
							System.out.print(j+"J"+e);
							break;
						case "\033[91mrouge\033[0m":
							System.out.print(r+"R"+e);
							break;
						default:
							System.out.print(Cellules[i][k].lireCouleurDuJeton().toUpperCase());
							break;
					}
                } else if (Cellules[i][k].presenceTrouNoir()) {
                    System.out.print(n+"T"+e);
                } else if (Cellules[i][k].presenceDesintegrateur()) {
                    System.out.print(v+"D"+e);
                } else {
                    System.out.print(" ");
                }
            }
			System.out.print("\n");
        }
		
		/* On affiche les numéros de colonne en bas pour une meilleure lisibilité */
		System.out.print("\t ");
		for (int k = 1; k <= nb_colonnes; k++) {
            System.out.print(spc+g+k+e);
        }
		System.out.print("\n");
    }
    
	/**
	 * Vérifie si la cellule est occupée par un jeton.
	 * @param i Ligne de la cellule
	 * @param j Colonne de la cellule
	 * @return État d'occupation de la cellule
	 */
    public boolean celluleOccupee(int i, int j) {
		if (i >= nb_lignes || j >= nb_colonnes) return false;
        if (Cellules[i][j] == null) return false;
        return (Cellules[i][j].recupererJeton() != null);
    }
    
	/**
	 * Donne la couleur du jeton occupant la cellule.
	 * @param i Ligne  de la cellule
	 * @param j Colonne de la cellule
	 * @return La couleur du jeton
	 */
    public String lireCouleurDuJeton(int i, int j) {
        return Cellules[i][j].recupererJeton().lireCouleur();
    }
    
	/**
	 * Vérifie si la grille est gagante pour le joueur spécifié
	 * Conditions de victoire : 4 jetons alignés en ligne, colonne ou diagonale.
	 * @param joueur Le joueur à vérifier la victoire
	 * @return État de victoire du joueur
	 */
    public boolean estGagnantePourJoueur(Joueur joueur) {
		String couleur = joueur.lireCouleur();
		
		/*
		* À chque fois, on vérifie si la couleur du jeton existe bien (s'il n'y a
		* pas de couleur, c'est qu'il n'y a pas d jeton, et il ne peut alors pas y
		* avoir de combinaison victorieuse à cet endroit-là).
		*/
		/* 
		* Vérification des colonnes
		* On ne vérifie pas explicitement les 3 dernières lignes car il n'y a pas
		* assez de place verticalement pour 4 jetons.
		*/
		for (int i = 0; i < nb_lignes-3; i++) {
			for (int j = 0; j < nb_colonnes; j++) {
				if (Cellules[i][j].recupererJeton() != null && Cellules[i][j].lireCouleurDuJeton().equals(couleur)) {
					if (Cellules[i+1][j].recupererJeton() != null && Cellules[i+1][j].lireCouleurDuJeton().equals(couleur)) {
						if (Cellules[i+2][j].recupererJeton() != null && Cellules[i+2][j].lireCouleurDuJeton().equals(couleur)) {
							if (Cellules[i+3][j].recupererJeton() != null && Cellules[i+3][j].lireCouleurDuJeton().equals(couleur)) {
								return true;
							}
						}
					}
				}
			}
		}
		
		/* Vérification des lignes*/
		for (int i = 0; i < nb_lignes; i++){
			
			/*
			* On ne vérifie pas explicitement les 3 dernières colonnes car il n'y
			* a pas assez de place horizontalement pour 4 jetons.
			*/
			for (int j = 0; j < nb_colonnes-3; j++) {
				if (Cellules[i][j].recupererJeton() != null && Cellules[i][j].lireCouleurDuJeton().equals(couleur)) {
					if (Cellules[i][j+1].recupererJeton() != null && Cellules[i][j+1].lireCouleurDuJeton().equals(couleur)) {
						if (Cellules[i][j+2].recupererJeton() != null && Cellules[i][j+2].lireCouleurDuJeton().equals(couleur)) {
							if (Cellules[i][j+3].recupererJeton() != null && Cellules[i][j+3].lireCouleurDuJeton().equals(couleur)) {
								return true;
							}
						}
					}
				}
			}
		}
		
		/*
		* Verification des diagonales NO-SE
		* Là encore, on ne vérifie pas explicitement les 3 denières lignes et
		* colonnes car il n'y a pas assez de place pour 4 jetons en diagonale.
		*/
		for (int i = 0; i < nb_lignes-3; i++) {
			for (int j = 0; j < nb_colonnes-3; j++) {
				if (Cellules[i][j].recupererJeton() != null && Cellules[i][j].lireCouleurDuJeton().equals(couleur)) {
					if (Cellules[i+1][j+1].recupererJeton() != null && Cellules[i+1][j+1].lireCouleurDuJeton().equals(couleur)) {
						if (Cellules[i+2][j+2].recupererJeton() != null && Cellules[i+2][j+2].lireCouleurDuJeton().equals(couleur)) {
							if (Cellules[i+3][j+3].recupererJeton() != null && Cellules[i+3][j+3].lireCouleurDuJeton().equals(couleur)) {
								return true;
							}
						}
					}
				}
			}
		}
		
		/*
		* Verification des diagonales NE-SO
		* Là encore, on ne vérifie pas explicitement les 3 denières lignes et
		* 3 premières colonnes car il n'y a pas assez de place pour 4 jetons en diagonale.
		*/
		for (int i = 0; i < nb_lignes-3; i++) {
			for (int j = 3; j < nb_colonnes; j++) {
				if (Cellules[i][j].recupererJeton() != null && Cellules[i][j].lireCouleurDuJeton().equals(couleur)) {
					if (Cellules[i+1][j-1].recupererJeton() != null && Cellules[i+1][j-1].lireCouleurDuJeton().equals(couleur)) {
						if (Cellules[i+2][j-2].recupererJeton() != null && Cellules[i+2][j-2].lireCouleurDuJeton().equals(couleur)) {
							if (Cellules[i+3][j-3].recupererJeton() != null && Cellules[i+3][j-3].lireCouleurDuJeton().equals(couleur)) {
								return true;
							}
						}
					}
				}
			}
		}
		/* Si aucune condition de victoire n'est remplie */
        return false;
    }
    
	/**
	 * Tasse la colonne spécifiée.
	 * Les jetons sont décalés vers les lignes d'indice supérieurs
	 * et complent les espaces vides.
	 * @param j Numéro de la colonne à tasser
	 */
    public void tasserGrille(int j) {
		int decalage = 0;
		for (int i = nb_lignes-1; i >= 0; i--) {
			if (Cellules[i][j].recupererJeton() == null) {
				decalage++;
			} else if (decalage != 0) {
				Cellules[i+decalage][j].affecterJeton(Cellules[i][j].recupererJeton());
				Cellules[i][j].supprimerJeton();
			}
		}
		
    }
    
	/**
	 * Vérifie si la colonne désignée est remplie
	 * @param j La colonne à vérifier
	 * @return État de remplissage complet de la colonne
	 */
	/*
	* Si on considère que la grille est tassée, une colonne est remplie quand
	* la cellule tout en haut est remplie.
	*/
    public boolean colonneRemplie(int j) {
        return celluleOccupee(0,j);
    }
    
	/**
	 * Ajoute un trou noir dans la cellule indiquée
	 * @param i Ligne de la cellule
	 * @param j Colonne de la cellule
	 * @return Succès de l'opération
	 */
    public boolean placerTrouNoir(int i, int j) {
        if (Cellules[i][j] == null) {
            Cellules[i][j] = new Cellule();
        }
        if (!Cellules[i][j].presenceTrouNoir()) {
            Cellules[i][j].trouNoir = true;
            return true;
        }
        return false;
    }
    
	/**
	 * Ajoute un désintégrateur dans la cellule indiquée
	 * @param i Ligne de la cellule
	 * @param j Colonne de la cellule
	 * @return Succès de l'opération
	 */
    public boolean placerDesintegrateur(int i, int j) {
        if (Cellules[i][j] == null) {
            Cellules[i][j] = new Cellule();
        }
        if (!Cellules[i][j].presenceDesintegrateur()) {
            Cellules[i][j].desintegrateur = true;
            return true;
        }
        return false;
    }
    
	/**
	 * Supprime le jeton de la cellule visée
	 * @param i Ligne de la cellule
	 * @param j Colonne de la cellule
	 * @return Succès de l'opérations
	 */
    public boolean supprimerJeton(int i, int j) {
        if (!celluleOccupee(i,j)) {
            return false;
        } else {
            Cellules[i][j].jetonCourant = null;
            return true;
        }
    }
    
	/**
	 * Enlève le jeton de la cellule visée et renvoie une référence
	 * vers ce jeton.
	 * @param i Ligne de la cellule
	 * @param j Colonne de la cellule
	 * @return Le jeton qui était dans la cellule
	 */
    public Jeton recupererJeton(int i, int j) {
        if (celluleOccupee(i,j)) {
			
			/* On sauvegarde la référence du jeton pour ne pas la perdre */
            Jeton jeton = Cellules[i][j].recupererJeton();
            
			/* On supprime le jeton */
			supprimerJeton(i,j);
            
			/* On retourne le jeton de la cellule et on sort de la fonction */
			return jeton;
        } else {
			return null;
		}
    }
    
}
