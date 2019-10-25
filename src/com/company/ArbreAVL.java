package com.company;

public class ArbreAVL {
    /** Cette classe est une implémentation des arbres AVL pour les entiers.
      * @author : Faisal Salhi - Sylejmani Visar - Ayoub Naas
     */

    private static Integer initialBufferSize = 2000; /* La taille du buffer est initialement de 2000 entiers */
    private static Integer indiceRacine = 1; // La racine de l'arbre commence à 1 dans notre représentation.
    private static Integer vide = Integer.MIN_VALUE; /* On choisi la plus petite valeur sur 32 bit comme représentation
                                                        l'élément vide dans notre arbre. */
    private Integer numberOfreAlloc = 1; /* Le nombre de fois ou l'arbre a du être realloué (car sa taille n'est plus
                                            suffisante */
    private Integer []arbre = new Integer[initialBufferSize]; /* On code notre arbre dans un tableau */

    public ArbreAVL(){
        testAVL();
    }


    /***************************************************** FONCTION DE TEST ***********************/

    private void testAVL(){
        initAVL(this.arbre, initialBufferSize);
        this.arbre[1] = 10;
        this.arbre[2] = 5;
        this.arbre[3] = 15;
        this.arbre[4] = 2;
        this.arbre[5] = 7;
    }

    /**********************************************************************************************/


    /**
     * La procédure initialise toute les cases d'un tableau d'entier avec la valeur vide.
     * @param  arbre (OUT) - l'arbre à initialiser sous la forme d'un tableau d'entier
     * @param  taille (IN) - La taille de l'arbre à initialiser.
     */
    private void initAVL(Integer[] arbre, int taille){
        for (int i = 0; i < taille; i++){
            arbre[i] = vide;
        }
    }

    /**
     * Fonction qui renvoie l'indice du père d'un noeud.
     * @param   indiceNoeud - Le noeud en question.
     * @return  indiceDuPere
     */
    private Integer indiceDuPere(Integer indiceNoeud){
        /* Pour toujours obtenir un indice entier.
           Si le noeud est à un indice impair, comme deux fils sont toujours
           contingents, et qu'il y a une relation de parité (Pair: fils Gauche, Impaire:
           Fils droit), il suffit de se rapporter au fils gauche en décrémentant
           l'indice du noeud et diviser le résultat par 2, pour obtenir l'indice du père.
         */
        return (((indiceNoeud%2) == 1) ? ((indiceNoeud-1)/2) : indiceNoeud/2);
    }


    /**
     * Fonction qui renvoie l'indice du fils gauche d'un noeud.
     * @param  indiceNoeud - Le noeud en question.
     * @return indiceDuFilsGauche
     */
    private Integer indiceDuFilsGauche(Integer indiceNoeud){
        return indiceNoeud * 2;
    }

    /**
     * Fonction qui renvoie l'indice du fils droit d'un noeud.
     * @param  indiceNoeud - Le noeud en question.
     * @return  indiceDuFilsDroit
     */
    private Integer indiceDuFilsDroit(Integer indiceNoeud){
        return (indiceNoeud * 2)+1;
    }

    /**
     * Fonction qui renvoie la hauteur d'un sous arbre à un indice donné.
     * @param  arbre - l'arbre dans lequel effectuer l'opération
     * @param  indiceRacineSousArbre - l'indice de la racine du sous-arbre.
     * @return Integer hauteur
     */
    private Integer hauteur(Integer []arbre, Integer indiceRacineSousArbre){
        if (arbre[indiceRacineSousArbre].equals(vide)) {
            return 0;
        } else {
            return Integer.max(1+hauteur(arbre, indiceDuFilsGauche(indiceRacineSousArbre)), 1+hauteur(arbre,indiceDuFilsDroit(indiceRacineSousArbre)));
        }
    }

    /**
     * Fonction qui renvoie la valeur la plus grande d'un sous-arbre donné.
     * @param arbre - L'arbre en question
     * @param indiceSousArbre - indice du sous arbre
     * @return Integer - la hauteur
     */
    private Integer max(Integer[] arbre, Integer indiceSousArbre){
        Integer indice_courant = indiceSousArbre;
        Integer max = 0;

        while (!arbre[indice_courant].equals(vide)){
            max = arbre[indice_courant];

            indice_courant = indiceDuFilsDroit(indice_courant);
        }

        return max;
    }


    /**
     * Fonction qui renvoie le déséquilibre en un noeud donné.
     * @param  arbre - L'arbre dans lequel effectuer l'opération.
     * @param  indiceNoeud - L'indice du noeud en question.
     * @return  déséquilibre
     */
    private Integer déséquilibre(Integer []arbre, Integer indiceNoeud){
        return (hauteur(arbre, indiceDuFilsGauche(indiceNoeud)) - hauteur(arbre, indiceDuFilsDroit(indiceNoeud)));
    }

    /**
     * Fonction qui renvoie si un noeud dans un arbre donné est une feuille.
     * @param  arbre - L'arbre en question
     * @param  indiceNoeud - Le noeud en question.
     * @return : Boolean estFeuille
     */
    private Boolean estFeuille(Integer[] arbre, Integer indiceNoeud){
        return (arbre[indiceDuFilsGauche(indiceNoeud)].equals(vide) &&
                arbre[indiceDuFilsDroit(indiceNoeud)].equals(vide));
    }

    /**
     * Fonction qui retourne une chaine de caractére représentant un arbre AVL.
     * @param  arbre - L'arbre à afficher
     * @param  indiceRacine - La racine du sous-arbre à afficher.
     * @param  chaine - La chaine de caractère se construisant au fur et a mesure des iterations
     */
    private String toString(Integer[] arbre,Integer indiceRacine, String chaine){
        if (estFeuille(arbre, indiceRacine)){
            return chaine + (arbre[indiceRacine].equals(vide) ? "X" : arbre[indiceRacine]) + "\n";
        } else {
            return  toString(arbre, indiceDuFilsDroit(indiceRacine), chaine+ "    ")
                    + chaine + (arbre[indiceRacine].equals(vide) ? "X" : arbre[indiceRacine]) + "\n"
                    + toString(arbre, indiceDuFilsGauche(indiceRacine), chaine+"    ");
        }
    }

    /** Procédure qui affiche un arbre AVL. (Utilisé principalement pour debug et manipulation interne à la classe)
     * @param arbre - L'arbre à afficher
     * @param indiceRacine - La racine de l'arbre.
     */
    private void afficher(Integer[] arbre, Integer indiceRacine){
        System.out.println(toString(arbre,indiceRacine, ""));
    }

    /** Procédure qui effectue la recopie d'un arbre dans un autre arbre.
     * @param arbre1 - arbre source
     * @param indiceDebut - indice du debut de la recopie dans l'arbre source
     * @param arbre2 - arbre sortie
     * @param indiceDebut2 - indice ou debute la recopie dans l'arbre de sortie
     */
    private void recopie(Integer[] arbre1, Integer indiceDebut,Integer[] arbre2, Integer indiceDebut2){
        if (arbre1[indiceDebut] != vide){
            arbre2[indiceDebut2] = arbre1[indiceDebut];
            recopie(arbre1,indiceDuFilsGauche(indiceDebut),arbre2,indiceDuFilsGauche(indiceDebut2));
            recopie(arbre1,indiceDuFilsDroit(indiceDebut),arbre2,indiceDuFilsDroit(indiceDebut2));
        }
    }

    /** Procédure qui effectue la recopie d'un arbre dans un autre arbre sauf
     *  le sous arbre spécifié en paramètre.
     *  @param arbre1 - arbre source
     *  @param indiceDebut - indice du debut de la recopie dans l'arbre source
     *  @param arbre2 - arbre sortie
     *  @param indiceDebut2 - indice ou debute la recopie dans l'arbre de sortie
     *  @param indiceSousArbre - indice du sous arbre à ne pas recopier
     */
    private void recopieSaufSousArbre(Integer[] arbre1, Integer indiceDebut,
                                      Integer[] arbre2, Integer indiceDebut2,
                                      Integer indiceSousArbre){
        if (!arbre1[indiceDebut].equals(vide) && !indiceDebut.equals(indiceSousArbre)){
                arbre2[indiceDebut2] = arbre1[indiceDebut];
                recopieSaufSousArbre(arbre1,indiceDuFilsGauche(indiceDebut),arbre2,indiceDuFilsGauche(indiceDebut2),indiceSousArbre);
                recopieSaufSousArbre(arbre1,indiceDuFilsDroit(indiceDebut),arbre2,indiceDuFilsDroit(indiceDebut2),indiceSousArbre);
        }
    }

    /** Procédure qui effectue une rotation gauche de l'arbre donné en paramètre.
     *  Effectue la rotation a parti de la racine de l'arbre !!!
     * @param arbre - L'arbre sur lequel effectuer la rotation
     */
    private void rotationGauche(Integer[] arbre,Integer taille){
        Integer[] tampon = new Integer[numberOfreAlloc*initialBufferSize];
        initAVL(tampon, numberOfreAlloc*initialBufferSize);

        tampon[indiceDuFilsGauche(indiceRacine)] =  arbre[indiceRacine];
        tampon[indiceRacine] = arbre[indiceDuFilsDroit(indiceRacine)];
        recopie(arbre, indiceDuFilsDroit(indiceDuFilsDroit(indiceRacine)), tampon, indiceDuFilsDroit(indiceRacine));
        recopie(arbre, indiceDuFilsGauche(indiceDuFilsDroit(indiceRacine)), tampon, indiceDuFilsDroit(indiceDuFilsGauche(indiceRacine)));
        recopie(arbre, indiceDuFilsGauche(indiceRacine), tampon, indiceDuFilsGauche(indiceDuFilsGauche(indiceRacine)));

        initAVL(arbre, taille);
        recopie(tampon,indiceRacine,arbre,indiceRacine);
    }

    /** Procédure qui effectue une rotation droite de l'arbre donné en paramètre.
     *  Effectue la rotation a parti de la racine de l'arbre !!!
     * @param arbre - L'arbre sur lequel effectuer la rotation
     * @param taille - La taille de l'arbre
     */
    private void rotationDroite(Integer[] arbre, Integer taille){
        Integer[] tampon = new Integer[numberOfreAlloc*initialBufferSize];
        initAVL(tampon, numberOfreAlloc*initialBufferSize);

        tampon[indiceDuFilsDroit(indiceRacine)] =  arbre[indiceRacine];
        tampon[indiceRacine] = arbre[indiceDuFilsGauche(indiceRacine)];
        recopie(arbre, indiceDuFilsDroit(indiceRacine), tampon,indiceDuFilsDroit(indiceDuFilsDroit(indiceRacine)));
        recopie(arbre, indiceDuFilsDroit(indiceDuFilsGauche(indiceRacine)), tampon, indiceDuFilsGauche(indiceDuFilsDroit(indiceRacine)));
        recopie(arbre, indiceDuFilsGauche(indiceDuFilsGauche(indiceRacine)), tampon, indiceDuFilsGauche(indiceRacine));

        initAVL(arbre, taille);
        recopie(tampon,indiceRacine,arbre,indiceRacine);
    }

    /** Procédure qui effectue une rotation gauche-droite de l'arbre donné en paramètre.
     *  Effectue la rotation à partir de la racine de l'arbre !!!
     * @param arbre - L'arbre sur lequel effectuer la rotation
     * @param taille - La taille de l'arbre
     */
    private void rotationGaucheDroite(Integer[] arbre, Integer taille){
        /* On isole la partie que l'on va modifier en dernier dans un tampon. */
        Integer[] tampon1 = new Integer[numberOfreAlloc*initialBufferSize];
        initAVL(tampon1, numberOfreAlloc*initialBufferSize);
        recopieSaufSousArbre(arbre, indiceRacine, tampon1, indiceRacine, indiceDuFilsGauche(indiceRacine));

        /* On isole le sous arbre gauche pour y faire la rotation */
        Integer[] tampon2 = new Integer[numberOfreAlloc*initialBufferSize];
        initAVL(tampon2, numberOfreAlloc*initialBufferSize);
        recopie(arbre, indiceDuFilsGauche(indiceRacine), tampon2, indiceRacine);

        // Faire la rotation gauche sur le sous arbre Gauche
        rotationGauche(tampon2,numberOfreAlloc*initialBufferSize);
        recopie(tampon2, indiceRacine, tampon1, indiceDuFilsGauche(indiceRacine));

        // Faire la rotation droite désormais
        rotationDroite(tampon1,numberOfreAlloc*initialBufferSize);

        // recopie finale
        initAVL(arbre, numberOfreAlloc*initialBufferSize);
        recopie(tampon1, indiceRacine, arbre, indiceRacine);
    }

    /** Procédure qui effectue une rotation droite-gauche de l'arbre donné en paramètre.
     *  Effectue la rotation à partir de la racine de l'arbre !!!
     * @param arbre - L'arbre sur lequel effectuer la rotation
     * @param taille - La taille de l'arbre
     */
    private void rotationDroiteGauche(Integer[] arbre, Integer taille){
        /* On isole la partie que l'on va modifier en dernier dans un tampon. */
        Integer[] tampon1 = new Integer[numberOfreAlloc*initialBufferSize];
        initAVL(tampon1, numberOfreAlloc*initialBufferSize);
        recopieSaufSousArbre(arbre, indiceRacine, tampon1, indiceRacine, indiceDuFilsDroit(indiceRacine));

        /* On isole le sous arbre gauche pour y faire la rotation */
        Integer[] tampon2 = new Integer[numberOfreAlloc*initialBufferSize];
        initAVL(tampon2, numberOfreAlloc*initialBufferSize);
        recopie(arbre, indiceDuFilsDroit(indiceRacine), tampon2, indiceRacine);

        // Faire la rotation gauche sur le sous arbre Gauche
        rotationDroite(tampon2,numberOfreAlloc*initialBufferSize);
        recopie(tampon2, indiceRacine, tampon1, indiceDuFilsDroit(indiceRacine));

        // Faire la rotation droite désormais
        rotationGauche(tampon1,numberOfreAlloc*initialBufferSize);

        // recopie finale
        initAVL(arbre, numberOfreAlloc*initialBufferSize);
        recopie(tampon1, indiceRacine, arbre, indiceRacine);
    }

    /** Procédure qui rééquilibre l'arbre en un noeud donné.
     *  @param arbre - l'arbre en question
     *  @param taille - indice du sous-arbre
     */
    private void reequilibrage(Integer[] arbre, Integer taille){
        Integer déséquilibre = déséquilibre(arbre, indiceRacine);
        Integer déséquilibreArbreGauche = déséquilibre(arbre, indiceDuFilsGauche(indiceRacine));
        Integer déséquilibreArbreDroit = déséquilibre(arbre, indiceDuFilsDroit(indiceRacine));
        // debug System.out.println("D. sag : " + déséquilibreArbreGauche + " D. sad : " + déséquilibreArbreDroit);

        if (déséquilibre >= 2 ){
            if (déséquilibreArbreGauche >= 1){
                // Rotation Droite
                rotationDroite(arbre,taille);
            } else if (déséquilibreArbreGauche <= -1) {
                // Rotation Gauche Droite
                rotationGaucheDroite(arbre,taille);
            }
            else if (déséquilibreArbreGauche == 0) {
                // Rotation Droite
                rotationDroite(arbre,taille);}
        } else if (déséquilibre <= -2) {
            if (déséquilibreArbreGauche >= 1) {
                // Rotation Droite Gauche
                rotationDroiteGauche(arbre, taille);
            } else if (déséquilibreArbreDroit <= -1){
                // Rotation Gauche
                rotationGauche(arbre,taille);
            }
            else if (déséquilibreArbreDroit == 0){
                // Rotation Gauche
                rotationGauche(arbre,taille);
            }
        }
    }

    /** Procédure d'ajout d'un élément dans l'arbre.
     * @param elem - L'element à ajouter.
     */
    public void ajout(Integer elem){
        Integer indice_courant = indiceRacine;

        while (!this.arbre[indice_courant].equals(vide)){
            if (elem > this.arbre[indice_courant]){
                indice_courant = indiceDuFilsDroit(indice_courant);
            } else {
                indice_courant = indiceDuFilsGauche(indice_courant);
            }
        }

        this.arbre[indice_courant] = elem;
        indice_courant = indiceDuPere(indice_courant);

        while (indice_courant != 0){
            //System.out.println("Indice_courant : " + indice_courant + " déséquilibre : " + déséquilibre(this.arbre, indice_courant));
            // Tout sauf le sous arbre
            Integer[] tampon1 = new Integer[numberOfreAlloc*initialBufferSize];
            initAVL(tampon1, numberOfreAlloc*initialBufferSize);
            recopieSaufSousArbre(this.arbre, indiceRacine, tampon1, indiceRacine,indice_courant);

            // Extraction sous Arbre
            Integer[] tampon2 = new Integer[numberOfreAlloc*initialBufferSize];
            initAVL(tampon2, numberOfreAlloc*initialBufferSize);
            recopie(this.arbre,indice_courant,tampon2,indiceRacine);

            // Reequilibrage
            reequilibrage(tampon2,numberOfreAlloc*initialBufferSize);

            // Jointure des deux arbres
            recopie(tampon2, indiceRacine, tampon1,indice_courant);
            initAVL(this.arbre, numberOfreAlloc*initialBufferSize);
            recopie(tampon1,indiceRacine,this.arbre,indiceRacine);

            // Remonter au père
            indice_courant = indiceDuPere(indice_courant);
        }

        afficher();
    }

    /** Procédure de suppression d'un élément dans l'arbre
     *
     * @param element - Element à supprimer
     * @param indice_courant - indice du sous arbre analyser
     */
    private void suppression(Integer element, Integer indice_courant) {
        if (!this.arbre[indice_courant].equals(vide)) {
            if (this.arbre[indice_courant].equals(element)) {
                if (estFeuille(this.arbre, indice_courant)) {
                    this.arbre[indice_courant] = vide;
                } else if (!this.arbre[indiceDuFilsGauche(indice_courant)].equals(vide) &&
                        this.arbre[indiceDuFilsDroit(indice_courant)].equals(vide)) {
                    // remonter sous arbre gauche
                    Integer[] tampon1 = new Integer[numberOfreAlloc * initialBufferSize];
                    initAVL(tampon1, numberOfreAlloc * initialBufferSize);
                    recopieSaufSousArbre(this.arbre, indiceRacine, tampon1, indiceRacine, indice_courant);

                    // Recopie du sous arbre
                    Integer[] tampon2 = new Integer[numberOfreAlloc * initialBufferSize];
                    initAVL(tampon2, numberOfreAlloc * initialBufferSize);
                    recopie(this.arbre, indiceDuFilsGauche(indice_courant), tampon2, indiceRacine);

                    // Jonction des deux arbres
                    recopie(tampon2, indiceRacine, tampon1, indice_courant);
                    initAVL(this.arbre, numberOfreAlloc * initialBufferSize);
                    recopie(tampon1, indiceRacine, this.arbre, indiceRacine);

                } else if (!this.arbre[indiceDuFilsDroit(indice_courant)].equals(vide) &&
                        this.arbre[indiceDuFilsGauche(indice_courant)].equals(vide)) {
                    // remonter sous arbre gauche
                    Integer[] tampon1 = new Integer[numberOfreAlloc * initialBufferSize];
                    initAVL(tampon1, numberOfreAlloc * initialBufferSize);
                    recopieSaufSousArbre(this.arbre, indiceRacine, tampon1, indiceRacine, indice_courant);

                    // Recopie du sous arbre
                    Integer[] tampon2 = new Integer[numberOfreAlloc * initialBufferSize];
                    initAVL(tampon2, numberOfreAlloc * initialBufferSize);
                    recopie(this.arbre, indiceDuFilsDroit(indice_courant), tampon2, indiceRacine);

                    // Jonction des deux arbres
                    recopie(tampon2, indiceRacine, tampon1, indice_courant);
                    initAVL(this.arbre, numberOfreAlloc * initialBufferSize);
                    recopie(tampon1, indiceRacine, this.arbre, indiceRacine);

                } else {
                    Integer max = max(this.arbre, indiceDuFilsGauche(indice_courant));
                    this.arbre[indice_courant] = max;

                    suppression(max, indiceDuFilsGauche(indice_courant));
                }

                indice_courant = indiceDuPere(indice_courant);

                // rééquilibrer jusqu'au père
                while (indice_courant != 0) {
                    //System.out.println("Indice_courant : " + indice_courant + " déséquilibre : " + déséquilibre(this.arbre, indice_courant));
                    Integer[] tampon1 = new Integer[numberOfreAlloc*initialBufferSize];
                    initAVL(tampon1, numberOfreAlloc*initialBufferSize);
                    recopieSaufSousArbre(this.arbre, indiceRacine, tampon1, indiceRacine, indice_courant);

                    Integer[] tampon2 = new Integer[numberOfreAlloc*initialBufferSize];
                    initAVL(tampon2, numberOfreAlloc*initialBufferSize);
                    recopie(this.arbre, indice_courant, tampon2, indiceRacine);

                    reequilibrage(tampon2,numberOfreAlloc*initialBufferSize);

                    recopie(tampon2, indiceRacine, tampon1, indice_courant);
                    initAVL(this.arbre, numberOfreAlloc*initialBufferSize);
                    recopie(tampon1, indiceRacine, this.arbre, indiceRacine);

                    indice_courant = indiceDuPere(indice_courant);
                }
            } else if (this.arbre[indice_courant] < element) {
                suppression(element, indiceDuFilsDroit(indice_courant));
            } else {
                suppression(element, indiceDuFilsGauche(indice_courant));
            }
        }
    }

    /** Procédure de supression d'un élément dans l'arbre.
     *  @param element - l'élément a supprimer.
     */
    public void suppression(Integer element){
        suppression(element, indiceRacine);
        afficher();
    }

    /** Procédure publique qui affiche l'arbre contenu dans la classe.
     *
     */
    public void afficher(){
       afficher(this.arbre,indiceRacine);
    }

}
