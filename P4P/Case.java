package P4P;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author benam
 */
public class Case {
    private ArrayList<Integer> pions;

     public Case()
     { this.pions=new ArrayList<>(); }

     public int getValCase(int i)
     { if ((!estVide()) && i >=0 && i < this.pions.size())
     return this.pions.get(i);
     else return -1;
     }

     public int getNbPions()
     { return pions.size(); }

     public void videCase()
     { this.pions.clear();}

     public boolean estVide()
     {return (this.pions.isEmpty());
     }
     
     public int getSommetCase(){
         int s=0;
         if(this.getNbPions()==0)
             return -1;
         else
             return this.getValCase(this.getNbPions()-1);
     }

     public int defilePion(){
         int p=0;
         if(this.getNbPions()==0)
             return -1;
         else{
             p=this.getValCase(0);
             this.pions.remove(0);
         }
         return p;
     }

     public void empilePion(int val){
         this.pions.add(val);
     }
}
