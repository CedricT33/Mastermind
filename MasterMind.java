import java.util.Scanner;
import java.util.Hashtable;
import java.util.Arrays;
import java.lang.Thread;

/**
*Classe principale du jeu MasterMind
*Codé avec amour par Vincent et Cédric de Simplon !!
*/
public class MasterMind {

  static Scanner saisie;

  public static void main(String[] args) {

    saisie = new Scanner(System.in);

    //Déclaration des variables
    char[] couleurs = {'J','R','V','B','N','O'};// Jaune, Rouge, Vert, Bleu, Noir, Orange
    char[] combiATrouver = new char[4];
    char[] combiUtilisateur = new char[4];
    int[] tableauPlace = new int[2];
    int nombreTours = 0;
    char difficulte;
    String nomJoueur1;
    String nomJoueur2;
    String joueur1;
    String joueur2;
    boolean bonnesLettres;

    //Début du jeux
    //Affichage titre et regles
    afficher("titre");
    afficher("regles");
    afficher("choixNomJoueur1");
    nomJoueur1 = saisie.nextLine();
    afficher("choixNomJoueur2");
    nomJoueur2 = saisie.nextLine();
    afficher("sautDeLigne");

    //boucle choix difficulte
    do {
      afficher("choixDifficulte");
      difficulte = saisie.nextLine().charAt(0);
      if(difficulte == '1'){
        nombreTours = 12;
      }
      else if(difficulte == '2'){
        nombreTours = 10;
      }
      else if(difficulte == '3'){
        nombreTours = 8;
      }
      else {
        afficher("difficulteFalse");
      }
    } while (difficulte != '1' && difficulte != '2' && difficulte != '3');

    afficher("nombreCoups", nombreTours);

    // Boucle vérification des lettres du joueur 1.
    do {
      afficher("choixUtilisateur", nomJoueur1);
      joueur1 = PasswordField.readPassword(); //affichage sous forme d'étoiles
      bonnesLettres = joueur1.matches("[RJVBNO]+");// Regex.
      if (joueur1.length() != 4 || verifLettresDiff(joueur1) == false || bonnesLettres == false) {
        afficher("erreurNbreLettres");
      }
    } while (joueur1.length() != 4 || verifLettresDiff(joueur1) == false || bonnesLettres == false);
    combiATrouver = combinaisonJoueur(joueur1);

    //boucle jeu
    do {
      afficher ("nombreCoups", nombreTours);

      // Boucle vérification des lettres du joueur 1.
      do {
        afficher("choixUtilisateur", nomJoueur2);
        joueur2 = saisie.nextLine();
        bonnesLettres = joueur2.matches("[RJVBNO]+");// Regex.
        if (joueur2.length() != 4 || verifLettresDiff(joueur2) == false || bonnesLettres == false) {
          afficher("erreurNbreLettres");
        }
      } while (joueur2.length() != 4 || verifLettresDiff(joueur2) == false || bonnesLettres == false);
      combiUtilisateur = combinaisonJoueur(joueur2);

      // Comparaison lettres joueur 1 et joueur 2.
      tableauPlace = comparaisonTableaux(combiATrouver, combiUtilisateur);
      afficherBienOuMalPlace("reponseBienOuMalPlace", tableauPlace[0], tableauPlace[1]);

      // Vérification si joueur 2 à gagné.
      if (Arrays.equals(combiATrouver, combiUtilisateur)){
        afficher("gagne");
      }
      nombreTours --;
    } while (!Arrays.equals(combiATrouver, combiUtilisateur) && nombreTours != 0);

    //Comparaison des deux suites de lettres pour afficher perdu.
    if (!Arrays.equals(combiATrouver, combiUtilisateur)){
      afficher("perdu");
    }
  } // Fin boucle jeu.

  //Méthode qui affiche et retourne une phrase.
  public static String afficher(String mot) {
    Hashtable<String, String> phrase = new Hashtable<String, String>();
    phrase.put("titre", "\n-------------------------\nBienvenue au MasterMind !\n-------------------------\n\n");
    phrase.put("choixDifficulte", "Veuillez choisir une difficulté\n1 : facile.\n2 : moyen.\n3 : difficile.\nChoix : ");
    phrase.put("regles", "Ici se trouverons les règles du jeu\n");
    phrase.put("choixNomJoueur1", "Joueur 1 entrez votre nom : ");
    phrase.put("choixNomJoueur2", "Joueur 2 entrez votre nom : ");
    phrase.put("difficulteFalse", "Veuillez saisir une difficulté entre 1 et 3 merci !\n\n");
    phrase.put("erreurNbreLettres", "Vous n'avez pas rentré 4 lettres différentes. Veuillez recommencer.\n");
    phrase.put("gagne", "\n-------------------------\nVous avez gagné !\nSUSHI GOOOOOOOOO\n-------------------------\n\n");
    phrase.put("perdu", "\n-------------------------\nVous avez perdu !\n-------------------------\n");
    phrase.put("sautDeLigne", "\n");
    ecritureLente(phrase.get(mot));
    return phrase.get(mot);
  }

  //Méthode qui affiche et retourne le prénom du joueur et une phrase invitant le joueur
  //à entrer les lettres.
  public static String afficher(String mot, String joueur) {
    Hashtable<String, String> phrase = new Hashtable<String, String>();
    phrase.put("choixUtilisateur", joueur + " veuillez entrer 4 lettres différentes (J,R,V,B,N,O) : ");

    System.out.print(phrase.get(mot));
    return phrase.get(mot);
  }

  //Méthode qui affiche et retourne une phrase qui indique le nombre de tours restant.
  public static String afficher(String mot, int compteur) {
    Hashtable<String, String> phrase = new Hashtable<String, String>();
    if (compteur <= 1){
      phrase.put("nombreCoups", "Vous disposez de " + compteur + " coup.");
    }
    else {
      phrase.put("nombreCoups", "Vous disposez de " + compteur + " coups.");
    }
    System.out.println(phrase.get(mot) + "\n");
    return phrase.get(mot);
  }

  //Méthode qui affiche et retourne la phrase de l'ordi disant que les lettres
  //sont bien ou mal placées.
  public static String afficherBienOuMalPlace(String mot, int bienPlace, int malPlace) {
    Hashtable<String, String> phrase = new Hashtable<String, String>();
    if (bienPlace <= 1 && malPlace <= 1){
      phrase.put("reponseBienOuMalPlace", "Vous avez " + bienPlace + " couleur bien placée et " + malPlace + " mal placée." );
    }
    else if (malPlace <= 1){
      phrase.put("reponseBienOuMalPlace", "Vous avez " + bienPlace + " couleurs bien placées et " + malPlace + " mal placée." );
    }
    else if (bienPlace <= 1){
      phrase.put("reponseBienOuMalPlace", "Vous avez " + bienPlace + " couleur bien placée et " + malPlace + " mal placées." );
    }
    else {
      phrase.put("reponseBienOuMalPlace", "Vous avez " + bienPlace + " couleurs bien placées et " + malPlace + " mal placées." );
    }
    System.out.println(phrase.get(mot));
    return phrase.get(mot);
  }

  //Méthode qui met les lettres rentrées par le joueur en majuscule
  //et sous forme de tableau.
  public static char[] combinaisonJoueur(String joueur) {
    char[] tableauCouleur = new char[4];

    for (int i = 0; i < 4; i++) {
      tableauCouleur[i] = joueur.toUpperCase().charAt(i);
    }
    return tableauCouleur;
  }

  //Méthode qui compare les 2 suites de lettres
  public static int[] comparaisonTableaux(char[] tableau1, char[] tableau2) {

    int bienPlace = 0;
    int malPlace = 0;
    int[] tableauPlace = new int[2];

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        if (tableau1[i] == tableau2[j] && i == j) {
          bienPlace++;
        } else if (tableau1[i] == tableau2[j]) {
          malPlace++;
        }
      }
    }
    tableauPlace[0] = bienPlace;
    tableauPlace[1] = malPlace;
    return tableauPlace;
  }

  //Méthode qui vérifie que le joueur rentre bien 4 lettres
  public static boolean verifLettresDiff(String mot) {
    boolean isGood = false;

    int nombreLettresDiff = (int)mot.chars().filter(Character::isAlphabetic).distinct().count();
    if (nombreLettresDiff == 4) {
      isGood = true;
    }
    return isGood;
  }

  //Méthode pour l'écriture des phrases lettre par lettre lentement.
  public static void ecritureLente(String mot){
    for (int i = 0; i < mot.length(); i++){
      System.out.print(mot.charAt(i));
      try {
        Thread.sleep(20);
            } catch(InterruptedException ie) {
               ie.printStackTrace();
            }
    }
  }

}
