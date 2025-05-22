/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package P4P;

/**
 *
 * @author benam
 */
public class Partie {
    private Joueur j1;
    private Joueur j2;
    private int nbCoups;
    private int res;

    // 0= match nul, 1 le joueur1 a gagné, 2 le joueur 2 a gagné
    public Partie(Joueur j1, Joueur j2, int nbCoups, int res) {
       this.j1=j1;
       this.j2=j2;
       this.nbCoups=nbCoups;
       this.res =res;
    }
    public Partie(){ 
        this.j1 = null;
        this.j2 = null;
        this.nbCoups = 0; 
        this.res = -1;
    }
    public Joueur getJ1(){
        return j1;
    }
    public void setJ1(Joueur j1){
        this.j1 = j1;
    }
    public Joueur getJ2(){
        return j2;
    }
    public void setJ2(Joueur j2){
        this.j2 = j2;
    }
    public int getNbCoups(){
        return nbCoups;
    }
    public void setNbCoups(int nbCoups){
        this.nbCoups = nbCoups;
    }
    public int getRes(){
        return res;
    }
    public void setRes(int res){
        this.res = res;
    }
    @Override
    public String toString(){
       String s= "Partie{" + "j1=" + j1.getPseudo() + ", j2=" + j2.getPseudo();
       s+=", nbCoups=" + nbCoups + ", res=" + res + '}';
       return s;
    }
    
}
