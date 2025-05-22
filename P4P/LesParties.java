
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package P4P;
import java.util.ArrayList;
/**
 *
 * @author benam
 */
public class LesParties {
    private ArrayList<Partie> lstp;

    public LesParties(){
    this.lstp = new ArrayList<>();
    }
    public Partie getPartie(int i){
        return lstp.get(i);
    }
    public int getIndicePartie(Partie p){
        return this.lstp.indexOf(p);
    }
    public int getNbPartie(){
        return this.lstp.size();
    }
    public void ajoutePartie(Partie p){
        this.lstp.add(p);
    }
    public LesParties rechPartie(String p){
        LesParties lp=new LesParties();
        for (int i=0; i<lstp.size();i++)
            if (lstp.get(i).getJ1().equals(p) || getPartie(i).getJ2().equals(p) ) {
            lp.ajoutePartie(lstp.get(i));
        return lp;
        }
        return null;
    }
    
    
    public String toString(){
        String s = "";
        for(int i = 0; i < lstp.size(); i++)
        s += "Parties " + i + "{" + lstp.get(i).toString() + "}\n";
        return s;

    }
}
