/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package P4P;

/**
 *
 * @author benam
 */
public class Plateau {

    private Case tab[][];
    
    public Plateau(){//constructeur du plateau par defaut 4x4 et appele de initPlateau 
        this.tab = new Case [4][4];
        initPlateau();
    }
    //crée le tableau
    private void initPlateau(){ 
        for (int i=0; i<4;i++){
            for (int j=0; j<4; j++){
                this.tab[i][j]= new Case();
            }
        }
    }
//verifier si la case et dans le tableau
    public boolean caseValide(int x, int y){
        return x >=0 && x<=3 && y>=0 && y <=3;
    }
//acces a la case donner en parametre
    public Case getCase(int x, int y){
        if (caseValide(x,y))
            return tab[x][y];
        else 
            return null;
    }
// regarde le nombre de pions dans la case
    public int getSommetCase (int x, int y){ 
        if (caseValide(x,y))
            return getCase(x,y).getSommetCase();
        else 
            return -1;
    }

    
    public void deposePionCase(int val, int x, int y){ 
        if (caseValide(x,y))
            getCase(x,y).empilePion(val);
    }

    public void initPlateauJeu(){
        deposePionCase(0,0,0);deposePionCase(0,0,0);
        deposePionCase(0,0,3);deposePionCase(0,0,3);
        deposePionCase(0,3,0);deposePionCase(0,3,0);
        deposePionCase(0,3,3);deposePionCase(0,3,3);
    }
    
   public void afficheValPlateau(){
        for (int x=0;x<4;x++){
            for (int y=0;y<4;y++){
                Case o=this.tab[x][y];
                if (o.getNbPions()==0){
                    System.out.print(" Vide ");
                } else {
                    System.out.print(o.getNbPions()+" pions ");
                }
            }
            System.out.println();//Passe à la ligne après chaque ligne du plateau
        }
    }
 
    
}
