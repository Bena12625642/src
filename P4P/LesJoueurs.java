/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package P4P;
import java.util.*;
import javax.swing.ImageIcon;
/**
 *
 * @author benam
 */
public class LesJoueurs {
   private ArrayList<Joueur> lstj;
   
   public LesJoueurs(){
    this.lstj = new ArrayList<Joueur>();
    }

    public Joueur getj(int i){
    return lstj.get(i);
    }

    public int getIndiceJoueur(Joueur j){
    return this.lstj.indexOf(j);
    }

    public int getNbJoueurs() {
    return this.lstj.size();
    }

    public void ajoutej(Joueur j) {
        this.lstj.add(j);
    }

    public Joueur rechj(String p) {
        for(int i =0 ; i<this.lstj.size();i++){
            if(this.getj(i).getPseudo().equals(p))
                return this.getj(i);
        }
        return null;
    }

    public void supprimej(Joueur j) {
        this.lstj.remove(j);
    }

    public LesJoueurs getjniv(int niv) { 
       LesJoueurs lj = new LesJoueurs();
       for(int i =0 ; i<this.lstj.size();i++){
           Joueur j=this.getj(i);
           if(j.getNiveau()==niv)
               lj.ajoutej(j);       
       }
        return lj;
    }


   public LesParties creationJoueursTest(LesParties lesPa) {

       lesPa=new LesParties();
       // Création des joueurs
       Joueur jm = new Joueur("Mario");
       jm.setPhoto(new ImageIcon(getClass().getResource("/tp3/img/mario.png")));

       Joueur jma = new Joueur("Marcus");
       jma.setPhoto(new ImageIcon(getClass().getResource("/tp3/img/marcus.jpg")));

       Joueur jp = new Joueur("Pikatchu");
       jp.setPhoto(new ImageIcon(getClass().getResource("/tp3/img/pikatchu.png")));

       Joueur jh = new Joueur("Halo");
       jh.setPhoto(new ImageIcon(getClass().getResource("/tp3/img/halo.jpg")));

       Joueur js = new Joueur("Elie");
       js.setPhoto(new ImageIcon(getClass().getResource("/tp3/img/elie.jpg")));


       // Ajout des parties
       Partie p1 = new Partie(jm, jma, 5, 1);
       Partie p2 = new Partie(jm, jma, 3, 1);
       Partie p3 = new Partie(jm, jp, 4, 1);
       Partie p4 = new Partie(jp, jh, 6, 1);
       Partie p5 = new Partie(jh, jp, 6, 2);

       //ajoute les résultats des parties a chaque joueurs
       jm.ajoutPartieJoue(jma, 1);
       jm.ajoutPartieJoue(jma, 1);
       js.ajoutPartieJoue(jm,1);
       js.ajoutPartieJoue(jm,1);js.ajoutPartieJoue(jm,1);js.ajoutPartieJoue(jm,1);js.ajoutPartieJoue(jm,1);js.ajoutPartieJoue(jm,1);js.ajoutPartieJoue(jm,1);
       jm.ajoutPartieJoue(jp, 1);
       jp.ajoutPartieJoue(jh, 1);
       jh.ajoutPartieJoue(jp, 2);

       //ajouter les parties dans LesParties
       lesPa.ajoutePartie(p1);
       lesPa.ajoutePartie(p2);
       lesPa.ajoutePartie(p3);
       lesPa.ajoutePartie(p4);
       lesPa.ajoutePartie(p5);

       //ajouter les joueurs
       this.ajoutej(jm);
       this.ajoutej(jma);
       this.ajoutej(jp);
       this.ajoutej(jh);
       this.ajoutej(js);

       return lesPa;
    }




    public String toString(){ 
        String str="Les informations des joueurs : "+"\n";
        for(int i =0 ; i<this.lstj.size();i++){
            str+=this.getj(i).toString() + "\n";
        }
        return str;
    }
   
}
