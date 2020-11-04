# superpuissance4
Un projet scolaire de Conception Programmation Objet pour consolider les connaissances en Java et commencer à utilier Git pour la collaboration.

En version 1.3, en mode console uniquement.

# Règles du jeu
Se joue à deux joueurs, chacun son tour soit place un jeton dans la grille, soit récupère un de ses propres jetons, soit (si le joueur possède un désintégrateur) désintègre un jeton adverse.

Le premier joueur à aligner 4 de ses jetons (en ligne, colonne ou diagonale) gagne la partie. En revanche, si un joueur enlève un jeton de la grille et cause une victoire des deux joueurs simultanément, ce joueur perd.

- Trou noir : Si un jeton s'arrête sur une cellule contenant un trou noir, il est aspiré et détruit.

- Désintégrateur : Si un jeton s'arrête sur une case contenant un désintégrateur, le joueur ayant posé ce jeton récupère le désintégrateur.

Au début de la partie, 5 trous noirs sont placés, ainsi que 5 désintégrateurs. 2 des désintégrateurs partagent une case avec un trou noir, et sont donc cachés.
