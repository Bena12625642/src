/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package P4P;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author benam
 */
public class ClassementDlg extends javax.swing.JDialog {
    private LesJoueurs lesJo;
    private LesParties lesPa;
    private int tabC[]; //utilisé pour le classement des joueurs

    /**
     * Creates new form StatjDiag
     */
    public ClassementDlg(java.awt.Frame parent, boolean modal,LesJoueurs lesj,LesParties lp) {
        super(parent, modal);
        initComponents();
        this.lesJo=lesj;
        this.lesPa=lp;
        Edition.setText(this.lesJo.toString());
        this.tabC= new int [this.lesJo.getNbJoueurs()];
        initClassement();
        initListeJoueursClasses();
        
    }
    
    private void initClassement(){ 
     int n =this.lesJo.getNbJoueurs();
     System.out.println("nombre de joueur a l'ouverture de la jdialog :"+ n);
     for (int i=0;i<n;i++){
         tabC[i]=i; //initialisation des indices
     }
     //Tri par sélection
     for (int i=0;i<n-1;i++){
         int maxIndex = i;//Premier indice prit pour le max
         for (int j=i+1;j<n;j++){
            if (this.lesJo.getj(tabC[j]).getNbpartiesGagnees() > this.lesJo.getj(tabC[maxIndex]).getNbpartiesGagnees()) {
                maxIndex =j;//si on trouve un joueur avec plus de victoire on change le max   
            }
         }
        //échange des indices pour trier tabC
        int temp=tabC[i];
        tabC[i]=tabC[maxIndex];
        tabC[maxIndex]=temp;
        }
    }
    
    public void paint(Graphics g){
        super.paint(g);
        dessineHisto();
        
    }

    private void initListeJoueursClasses(){
        DefaultListModel<String> model = new DefaultListModel<>();
        this.initClassement();

        for (int i=0;i<this.lesJo.getNbJoueurs();i++){
            String joueurInfo =(i+1) + "." + this.lesJo.getj(tabC[i]).getPseudo();
            model.addElement(joueurInfo);  
        }
        ListeClassement.setModel(model);
    }


    private void dessineHisto() {
        Graphics g =PCentreHisto.getGraphics();
        int nbj=this.lesJo.getNbJoueurs();
        //trouver le score max parmi tous les joueurs
        int Max=0;
        for (int i=0;i<nbj;i++){
            int score=this.lesJo.getj(i).getNbpartiesGagnees();
            if (score>Max){
                Max=score;
            }
        }
        // Taille maximale du graphique
        int largeurMax=this.getWidth()/2;
        int decalageLarg=50;
        int decalageHaut=50;
        int largRect=largeurMax/nbj;
        int hautMax=this.PCentreHisto.getHeight()/2;
        // Dessin des rectangles pour chaque joueur
        for (int i=0;i<nbj;i++){
            Joueur j=this.lesJo.getj(i);
            int nbpartg=j.getNbpartiesGagnees();
            // Hauteur proportionnelle au score max
            int hautRect;
            if (nbpartg==0){
                hautRect=10;
            } else {
                hautRect=hautMax*nbpartg/Max;
                if (hautRect< 10) {
                    hautRect=10; // on impose un minimum
                }
            }
            // Couleur aléatoire
            int v =(int)(Math.random()*255);
            int r =(int)(Math.random()*255);
            int b =(int)(Math.random()*255);
            g.setColor(new Color(r,v,b));
            // Affichage du pseudo et score
            g.drawString(j.getPseudo(),decalageLarg+i*largRect,hautMax+decalageHaut+20);
            g.drawString("" +nbpartg,decalageLarg+i*largRect,hautMax+decalageHaut+35);
            // Dessin de la barre
            g.fillRect(decalageLarg+i*largRect,hautMax+decalageHaut-hautRect,largRect,hautRect);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        ListeClassement = new javax.swing.JList<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        Edition = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        PCentreHisto = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new java.awt.GridLayout(2, 1));

        jScrollPane2.setViewportView(ListeClassement);

        jPanel4.add(jScrollPane2);

        Edition.setColumns(20);
        Edition.setRows(5);
        jScrollPane1.setViewportView(Edition);

        jPanel4.add(jScrollPane1);

        jPanel3.add(jPanel4, java.awt.BorderLayout.CENTER);

        jLabel2.setText("Classement des joueurs");
        jPanel3.add(jLabel2, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(jPanel3, java.awt.BorderLayout.EAST);

        jLabel1.setText("Statistiques joueurs");
        jPanel1.add(jLabel1);

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        javax.swing.GroupLayout PCentreHistoLayout = new javax.swing.GroupLayout(PCentreHisto);
        PCentreHisto.setLayout(PCentreHistoLayout);
        PCentreHistoLayout.setHorizontalGroup(
            PCentreHistoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 263, Short.MAX_VALUE)
        );
        PCentreHistoLayout.setVerticalGroup(
            PCentreHistoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 274, Short.MAX_VALUE)
        );

        getContentPane().add(PCentreHisto, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClassementDlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClassementDlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClassementDlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClassementDlg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                LesJoueurs lesj = new LesJoueurs(); 
                LesParties lp= new LesParties();
                lesj.creationJoueursTest(lp);
                ClassementDlg dialog = new ClassementDlg(new javax.swing.JFrame(), true, lesj,lp );
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea Edition;
    private javax.swing.JList<String> ListeClassement;
    private javax.swing.JPanel PCentreHisto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
