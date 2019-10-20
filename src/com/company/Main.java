package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Boolean continu = true;
        Integer choix = 0;
        Integer element = 0;
        Scanner sc = new Scanner(System.in);
        ArbreAVL arbreAVL = new ArbreAVL();
        do {
            switch (choix) {
                case 1 :
                    arbreAVL.afficher();
                    choix = 0;
                    break;
                case 2 :
                    System.out.println("");
                    System.out.print("Element : ");
                    element = sc.nextInt();
                    arbreAVL.ajout(element);
                    System.out.println("");
                    choix = 0;
                    break;
                case 3 :
                    System.out.println("");
                    System.out.print("Element : ");
                    element = sc.nextInt();
                    arbreAVL.suppression(element);
                    System.out.println("");
                    choix = 0;
                    break;
                case -1 :
                    continu = false;
                    break;
                default:
                    System.out.println("Menu :\n  1. Afficher l'Arbre."
                            + "\n  2. Ajouter un Element dans l'arbre."
                            + "\n  3. Suppression d'un Element dans l'arbre."
                            + "\n -1. Quitter");
                    System.out.print(">>>");
                    choix = sc.nextInt();
            }

        } while (continu);
    }
}
