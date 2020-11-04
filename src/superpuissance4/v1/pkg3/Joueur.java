/*
 * 
 * Émile ROYER
 * TP 3, v1.3
 * 
 */
package superpuissance4.v1.pkg3;

/**
 * Implémente les joueurs. Ils ont un nom, une couleur, une quantité limitée de
 * jetons, et peuvent avoir des désintégrateurs.
 * @author Émile ROYER
 */
public class Joueur {
    
    String Nom;
    String Couleur;
    Jeton [] ListeJetons = new Jeton[21];
    int nombreDesintegrateurs;
    int nombreJetonsRestants;
    
	/**
	 * Constructeur initialisant le nom du joueur.
	 * @param nom le nom du joueur
	 */
    public Joueur(String nom) {
        Nom = nom;
    }
    
	/**
	 * Affecte une couleur au joueur.
	 * @param couleur La couleur que doit prendre le joueur
	 */
    public void affecterCouleur(String couleur){
        Couleur = couleur;
    }
	
	/**
	 * Donne la couleur du joueur.
	 * @return La couleur du joueur
	 */
	public String lireCouleur() {
		return Couleur;
	}
	
	/**
	 * Ajoute un jeton au joueur.
	 * @param jeton Le jeton à ajouter
	 * @return Succès de l'opération
	 */
    public boolean ajouterJeton(Jeton jeton) {
        if (nombreJetonsRestants == ListeJetons.length){
            return false;
        }
        else {
         ListeJetons[nombreJetonsRestants] = jeton;
         nombreJetonsRestants++;
         return true;
        }
    }
	
	/**
	 * Enlève un jeton au joueur.
	 * @return Succès de l'opération
	 */
	public boolean enleverJeton() {
		if (nombreJetonsRestants == 0) {
			return false;
		} else {
			nombreJetonsRestants--;
			ListeJetons[nombreJetonsRestants] = null;
			return true;
		}
	}
    
	/**
	 * Incrémente le nombre de désintégrateurs du joueur.
	 */
    public void obtenirDesintegrateur() {
        nombreDesintegrateurs++;
    }
    
	/**
	 * Décrémente le nombre de désintégrateurs du joueur.
	 * @return Succès de l'opération
	 */
    public boolean utiliserDesintegrateur() {
        if (nombreDesintegrateurs >= 1) {
            nombreDesintegrateurs--;
            return true;
        }
        else {
            return false;
        }
    }
    
}
