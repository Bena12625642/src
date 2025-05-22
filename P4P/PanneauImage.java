/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package P4P;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.*;

/**
 *
 * @author benam
 */
public class PanneauImage extends JPanel{// extention de la classe jpanel donc c'est la classe fille
    
    private Image img;//stock l'image
    
    //appele de la classe mere sans parametre
    public PanneauImage(){
        super();
        this.img=null;
    }
    // avec parametre
    public PanneauImage(Image img){
        super();
        this.img=img;
    }
    
    public Image getImage(){
        return this.img;
    }
    //modifier l'image et la rafraichi
    public void setImage(Image im){
        this.img=im;
        this.repaint();
    }
    //methode qui permet de mettre l'image dans le jpanel
    public void paint(Graphics g){
        super.paint(g);// sans la surcharge le jpanel ce charge normalement en surchargant on l'obliger a dessiner dedans
        if(img!=null){
            g.drawImage(img,0,0,this.getWidth(),this.getHeight(),this);
        }
    }
    
}
