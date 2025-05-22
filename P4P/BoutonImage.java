/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package P4P;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.*;
import javax.swing.*;
/**
 *
 * @author benam
 */
public class BoutonImage extends JButton {
    private Image img;//stock l'image
    
    //appele de la classe mere sans parametre
    public BoutonImage(){
        super();
        this.setMargin(new Insets(0,0,0,0));
        this.img=null;
    }
    // avec parametre
    public BoutonImage(Image img){
        super();
        this.setMargin(new Insets(0,0,0,0));
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
        System.out.println("dessin dans le bouton "+ this.getWidth()+" "+this.getHeight());
        if(img!=null){
            Image imgB= this.img.getScaledInstance(this.getWidth(),this.getHeight(),Image.SCALE_DEFAULT);
            this.setIcon(new ImageIcon(imgB));
        }
    }
    
}
