/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package P4P;

/**
 *
 * @author benam
 */
public class Jeu {
    private Plateau platjeu;
    
    public Jeu(){
        this.platjeu=new Plateau();
        this.platjeu.initPlateauJeu();
    }
    
    public Plateau getPlateau(){
        return this.platjeu;
    }
    
    
    public boolean gagne(int valj) {
        boolean diag1 = true; // Vérification de la diagonale "\"
        boolean diag2 = true; // Vérification de la diagonale "/"
        for (int i=0;i<4; i++){
            // Vérification des diagonales
            if (platjeu.getSommetCase(i,i)!=valj) {
                diag1 = false;
            }
            if (platjeu.getSommetCase(i,3-i)!=valj) {
                diag2 = false;
            }
            // Vérification des lignes
            boolean ligneGagne=true;
            for (int j=0;j<4;j++){
                if (platjeu.getSommetCase(i,j)!=valj){
                    ligneGagne = false;
                    break;
                }
            }
            if (ligneGagne)
                return true;
        }
        // Vérification des diagonales après la boucle
        if (diag1 || diag2){
            return true;}
        // Vérification des colonnes
        for (int j=0;j<4;j++) {
            boolean colonneGagne = true;
            for (int i=0;i<4;i++) {
                if (platjeu.getSommetCase(i,j)!=valj) {
                    colonneGagne = false;
                    break;
                }
            }
            if (colonneGagne) 
                return true;
        }
    return false; // Aucun alignement détecté
    }
    //verifier avec la valeur absolue si horizontal X difference de 1 ou vertical Y difference de 1
    public boolean adjacent(int xd, int yd, int xa, int ya){
        return (Math.abs(xd-xa)==1 && yd==ya) || (Math.abs(yd-ya)==1 && xd==xa);
    }

    //on utilise adjacent pour verifier la validité du pion deposer par le joueur
    public int jouePion(int pxd, int pyd, int xd, int yd, int x, int y, int valp) {
    int code = 0;
    if (x == pxd && y == pyd) {
        System.out.println("Mouvement interdit : pas de retour en arrière");
        code=1;
    } else if (!this.adjacent(xd, yd, x, y)) {
        System.out.println("Mouvement interdit : position non adjacente");
        code=2;
        
    } else {
        this.platjeu.deposePionCase(valp, x, y);
    }
    return code;
}

    

}