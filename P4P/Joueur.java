package P4P;  
import java.util.ArrayList;
import javax.swing.ImageIcon;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author benam
 */




public class Joueur {
    private String pseudo;
    private ImageIcon photo; 
    private int niveau; 
    private ArrayList<Joueur> adversaires; 
    private ArrayList<Integer> resultats; 

     

   public Joueur() {
        this.pseudo = "pseudo";
        this.niveau=1;
        this.adversaires= new ArrayList<>();
        this.resultats= new ArrayList<>();
        this.photo = new ImageIcon(getClass().getResource("/tp3/img/joueurDefaut.png"));
       
    }
   
    public Joueur(String pseudo) {
        this.pseudo = pseudo;
        this.niveau=1;
        this.adversaires= new ArrayList<>();
        this.resultats= new ArrayList<>();
        this.photo = new ImageIcon(getClass().getResource("/tp3/img/joueurDefaut.png"));
    }
    
  
    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public ImageIcon getPhoto() {
        return this.photo;
    }

    public void setPhoto(ImageIcon photo) {
        this.photo = photo;
    }

    public int getNiveau() {
        return this.niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }
    
    public int getNba()  
    {
        return this.adversaires.size();
    }
    public Joueur getAdversaire(int i) 
    {   if (i>=0 && i< this.adversaires.size())
          return this.adversaires.get(i);
        else return null;
    }
    
    public ArrayList<Integer> getResultatsAdv(String adv) 
    {
        ArrayList<Integer> lst = new ArrayList <>();
        for (int i= 0; i<this.adversaires.size(); i++)
            if (this.adversaires.get(i).pseudo.equals(adv))
                lst.add(this.resultats.get(i));
        return lst;
    }
    
    public LesJoueurs getAdversaires(){
       LesJoueurs lj = new LesJoueurs();
        for (int i=0; i<this.adversaires.size(); i++)
        { Joueur jo = this.adversaires.get(i);
            boolean trouve = false;
            for (int j=0; j< lj.getNbJoueurs() ;j++)
                if (lj.getj(i).getPseudo().equals(jo.pseudo))
                    trouve = true;
            if (!trouve) lj.ajoutej(jo);     
        }
        return lj;
        }
    
    public int getNbpartiesGagnees(){ 
     int compt=0;
     for(int i=0; i<this.resultats.size();i++)
       if (this.resultats.get(i)==1)
          compt++; 
     return compt;
    }
    
    public int getNbpartiesPerdues(){
       int compt=0;
       for(int i=0; i<this.resultats.size(); i++)
         if (this.resultats.get(i)==2)
            compt++; 
       return compt;
    }
         
    public int getNbpartiesNul(){  
     int compt=0;
     for(int i=0; i<this.resultats.size();i++)
       if (this.resultats.get(i)==0)
          compt++; 
     return compt;
     }

    public void ajoutPartieJoue(Joueur j,int res){       
        this.adversaires.add(j);
        this.resultats.add(res);
        if (res==1){
            if (this.niveau!=10)
                this.niveau+=1;//le niveau du joueur augmente de 1 s'il gagne et si n'est pas encore a 10(expert)
        }
    }
    @Override

    public String toString() {
        String str = "Joueur : "+this.pseudo +" | Niveau : "+this.niveau+"\n";
        str+="Parties jouées : "+this.getNba()+"\n";
        str+="Victoires : "+this.getNbpartiesGagnees()+" | Défaites : "+this.getNbpartiesPerdues()+" | Nuls : "+this.getNbpartiesNul()+"\n";
        if (this.getNba()==0){
            str+="Aucune partie jouée.\n";
        } 
        else{
            str+="Adversaires rencontrés :\n";
            for (int i =0;i<this.getNba();i++){
                Joueur adv=this.getAdversaire(i);
                
                int resultat=this.resultats.get(i);
                String res;
                if(resultat==1){
                    res="Gagné";
                } else if(resultat==0){
                    res="Match nul";
                } else {
                    res="Perdu";
                }
                str+="- Contre "+adv.getPseudo()+" : "+res+"\n";
                
                
                
            }
        }
        return str;
}



    
    
}

    

