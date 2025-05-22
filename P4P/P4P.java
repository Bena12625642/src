
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package P4P;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author benam
 */
public class P4P extends javax.swing.JFrame {
    //objet utiliser pour le jeux 
    private LesJoueurs listeJ;
    private Joueur joueur1;
    private Joueur joueur2;
    private Plateau pjeu;
    private int jcourant;
    private LesParties lp;
    private Jeu jeu;
    
    private int cpt;// nombre de coup d'une party
    private int nbgj1;//nombre de galet j1
    private int nbgj2;//nombre galet j2
    private int xsel,ysel;//indice de la case selectionnée
    
    private Case casecourante;//copie de la case selectionnée pour le tour du joueur
    private int nbpiondeposer;//nombre de pion a distribuer, depuis la case courante
    
    private int xd,yd;//position du dernier depot du joueur courant
    private int pxd,pyd;//position precedente
    
    private boolean selection;//switch entre les joueur
    private boolean finParties;//indicateur pour la fin du jeux

    //pour avoir les bonne dimension pour les images
    private PanneauImage pan ;
    private PanneauImage pan2;
    
    public P4P() { 
        initComponents();
        // creation des bouton pour les image 
        this.pan = new PanneauImage();
        this.pan2= new PanneauImage();
        //donne la dimmension des panneau 
        pan.setSize(500,pan.getHeight());
        pan2.setSize(500,pan2.getHeight());
        //ajout des bouton dans les Jpanel
        jPanel2.add(pan2);
        jPanel1.add(pan);
        
        this.listeJ = new LesJoueurs();
        this.listeJ.creationJoueursTest(this.lp);
        this.lp=new LesParties();
        
        //par defaut on met pas de joueur
        this.joueur1 = this.listeJ.getj(2);
        this.joueur2 = this.listeJ.getj(1);

        this.jeu = new Jeu(); 
        this.pjeu = jeu.getPlateau(); // On récupère le plateau de Jeu

        initPanneau();
        afficheplateau();
        affichej();
        
        this.pjeu.afficheValPlateau();//methode de debug

        //attribue pour le changement de joueur ,la fin de parti,le compteur de coup 
        this.jcourant=1;
        this.nbgj1=8;
        this.nbgj2=8;
        this.cpt=0;
        this.casecourante=null;
        this.selection=false;
        this.finParties=false;
        message.setText("Selectionnée un joueur ");     
    }

    
    public void traitementActionPerformed(java.awt.event.ActionEvent evt) {
        if (!finParties) {
            // Création du bouton que le joueur va ajouter
            JButton jb = (JButton) evt.getSource();
            int num = Integer.parseInt(jb.getName());

            // Calcul pour récupérer la case
            int x = num/4;
            int y = num-4*x;

            if (!this.selection) { // Si aucun pion n'est en cours de déplacement
                
                if (this.pjeu.getCase(x, y).estVide()) {
                    message.setText("Case Vide !");
                    System.out.println("La case (" + x + ", " + y + ") est vide. Action annulée.");
                    this.revalidate();
                    this.repaint();
                    return;
                    
                } else {
                    this.cpt++; // Incrémente le compteur de coups
                    System.out.println("nombre de coups pour la parti : "+this.cpt);
                    this.selection = true; // Indique qu'un pion est en cours de déplacement
                    this.xsel = x; 
                    this.ysel = y;
                    this.xd = x;
                    this.yd = y;
                    // Position précédente
                    this.pxd = -1; 
                    this.pyd = -1;

                    // Dépose le pion du joueur courant
                    this.pjeu.deposePionCase(this.jcourant, xsel, ysel);
                    this.pjeu.afficheValPlateau();

                    // Duplique la case sélectionnée dans la variable casecourante
                    this.casecourante = new Case();
                    for (int i = 0; i < this.pjeu.getCase(xsel, ysel).getNbPions(); i++) {
                        this.casecourante.empilePion(this.pjeu.getCase(xsel, ysel).getValCase(i));
                    }

                    // Nombre total de pions après dépôt
                    this.nbpiondeposer = this.casecourante.getNbPions();
                    afficheplateau();
                    affichecasecourante(); // Met à jour l'affichage des pions
                    this.pjeu.getCase(xsel, ysel).videCase(); // Vide la case sélectionnée comprend pas pk utiliser par la prof
                    System.out.println("Pions restants à déposer: " + this.nbpiondeposer);
                }
            }
            // Si des pions sont encore à déposer
            else {
                if (this.nbpiondeposer != 0) {
                    
                    this.xsel = x;
                    this.ysel = y;
                    int val = this.casecourante.getValCase(0); // Récupère la valeur du pion à déposer
                    int code;

                    code = this.jeu.jouePion(this.pxd, this.pyd, this.xd, this.yd, x, y, val);// la methode renvoie 0 si la casse selectionner par le joueur est bien une case adjacente 
                    if (code == 0) { // Mouvement valide
                        message.setText("");
                        this.nbpiondeposer--; // Décrémente le nombre de pions restants
                        this.casecourante.defilePion(); // Retire le pion déposé
                        
                        this.pxd = this.xd; // Mise à jour des positions précédentes
                        this.pyd = this.yd;
                        this.xd = x;
                        this.yd = y;

                        afficheplateau();
                        affichecasecourante(); // Met à jour l'affichage des pions restants
                       
                        // Si le joueur a terminé son déplacement
                        if (this.nbpiondeposer == 0) {
                            traiteFinTour(); // Change le joueur
                        }
                    } else {
                        
                        message.setText("Mouvement impossible");
                       

                        
                        System.out.println("Mouvement impossible pour la case (" + x + ", " + y + ")"); 
                    }
                }
            }
        }
   
        
        this.revalidate();
        this.repaint();
   }


    
    public void initPanneau(){
        for(int i=0;i<16;i++){
           JPanel pan =new javax.swing.JPanel();
           pan.setLayout(new GridLayout(2,1));
           JButton bh = new JButton();
          
           String b=("");
           b+=i;
           bh.setName(b);
                      bh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                   traitementActionPerformed(evt);
                }
            });
           pan.add(bh);
           JPanel pb =new javax.swing.JPanel();
           pan.add(pb);
           center.setLayout(new GridLayout(4, 4, 5, 5));
           center.add(pan);
  
        } 

        this.pack();
        this.setSize(800,600);
    }
    
    public void afficheplateau(){// recupere la case met les bouton dans le jpanel pan et colorie le reste des boutons selon leur valeur
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){// boucle pour mettre les couleur 
                int num = (i*4)+j;//sert a recupere la place du composant
                
                //System.out.println("Accès à center.getComponent(" + num + ") sur un total de " + center.getComponentCount());
               JPanel pan=(JPanel)center.getComponent(num); // on recupere le panneau au centre est on regarde le composant la place num  
                //System.out.println("Panel " + num + " contient : " + pan.getComponentCount() + " composants.");
                JButton bh = (JButton)pan.getComponent(0);
                Case c =pjeu.getCase(i, j);
                int val = pjeu.getSommetCase(i, j);
                //System.out.println("sommet de la case : "+val+ " on est sur la case : "+i+ ","+j);
                
                bh.setBackground(getCouleurParValeur(val));//Appel la methode pour colorié le bouton 
                
                JPanel pb = (JPanel)pan.getComponent(1);// regarde dans la case le composant juste en dessous (donc le second)
                pb.setBackground(Color.YELLOW);
                pb.removeAll();
                int nbpion=c.getNbPions();
                pb.setLayout(new GridLayout(1,nbpion-1));
                
                for(int pion=0;pion<nbpion-1;pion++){// colorie les boutons en fonction de leur valeur 
                    JButton btpion = new JButton() ;
                    int col =c.getValCase(pion);
                    btpion.setBackground(getCouleurParValeur(col));//Appel la methode pour colorié le bouton
                    pb.add(btpion);  
                }
            }
        }
        this.revalidate();
        this.repaint();
    }//on peut crée une fonction qui affecte la couleur au pion independante (c'est fait) 
    
    private Color getCouleurParValeur(int val) {
        Color couleur = null;
        switch (val) {
            case -1:
                couleur = Color.YELLOW;
                break;
            case 0:
                couleur = Color.ORANGE;
                break;
            case 1:
                couleur = Color.RED;
                break;
            case 2:
                couleur = Color.MAGENTA;
                break;
            default:
                break;
        }
        return couleur;
    }
    
    public void affichej() {
        // Vérification si les joueurs sont créés
        if (joueur1 == null) {
            nomj1.setText("Joueur 1");  
            colorj1.setBackground(Color.red); 
            pan.setImage(new ImageIcon(getClass().getResource("/tp3/img/joueurDefaut.png")).getImage()); // getimage permet de recupere l'image de la photo 
        } else { //Si le joueur1 est cree alors on change 
            nomj1.setText(joueur1.getPseudo());  
            colorj1.setBackground(Color.red); 
            pan.setImage(joueur1.getPhoto().getImage()); 
        }
        if (joueur2 == null) {
            nomj2.setText("Joueur 2"); 
            colorj2.setBackground(Color.magenta); 
            pan2.setImage(new ImageIcon(getClass().getResource("/tp3/img/joueurDefaut.png")).getImage());
        } else {
            nomj2.setText(joueur2.getPseudo());  
            colorj2.setBackground(Color.magenta);  
            pan2.setImage(joueur2.getPhoto().getImage());  
            
        }
    }

    private void affichecasecourante(){
        CaseCourante.removeAll(); // Vide le panel avant d'ajouter les nouveaux galets
        if (casecourante!=null) {
            int nbpion=casecourante.getNbPions();
            CaseCourante.setLayout(new GridLayout(Math.max(1,nbpion),1));//On fait un gridlayout de la taille du nombre de pion
            for (int i=0;i<nbpion;i++){
                JButton galetButton=new JButton();
                int val=casecourante.getValCase(i);
                //On met la couleur au boutons
                galetButton.setBackground(getCouleurParValeur(val));//Appel la methode pour colorié le bouton
                CaseCourante.add(galetButton);
            }
        }
        //Rafraîchir l'affichage
        this.revalidate();
        this.repaint();
    }
    
    private void traiteFinTour(){
        this.cpt++;
        this.selection=false;
        this.pjeu.afficheValPlateau();
        if(this.jeu.gagne(this.jcourant)){
            message.setText("LE JOUEUR "+this.jcourant+" A GAGNER !!!!!");
            Partie part=new Partie(this.joueur1,this.joueur2,this.cpt,this.jcourant);
    
            if(this.jcourant==1){
                this.joueur1.ajoutPartieJoue(this.joueur2, 1);
                this.joueur2.ajoutPartieJoue(this.joueur1, 2);
            }
            else{
                this.joueur2.ajoutPartieJoue(this.joueur1, 1);
                this.joueur1.ajoutPartieJoue(this.joueur2, 2);}
            
            this.lp.ajoutePartie(part);
            
            this.cpt=0;
            this.finParties=true;
            
        }
        else if(this.jcourant==1){
            this.nbgj1--;
            System.out.println("nombre de pion restant a j1 :"+this.nbgj1);
            this.jcourant=2;
            if(this.nbgj2==0){
                message.setText("MATCH NULLLL ");
                this.joueur2.ajoutPartieJoue(this.joueur1, 0);
                this.joueur1.ajoutPartieJoue(this.joueur2, 0);
                this.finParties=true;
            }
            else
                message.setText("C'est au joueur 2 de jouer");
        }
        else{
            this.jcourant=1;
            this.nbgj2--;
            System.out.println("nombre de pion restant a j2 :"+this.nbgj2);
            if(this.nbgj1==0){
                message.setText("MATCH NULLLL");
                this.joueur2.ajoutPartieJoue(this.joueur1, 0);
                this.joueur1.ajoutPartieJoue(this.joueur2, 0);
         
                this.finParties=true;
            }
            else
                message.setText("C'est au joueur 1 de jouer");
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

        center = new javax.swing.JPanel();
        south = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        message = new javax.swing.JTextField();
        nord = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        nomj1 = new javax.swing.JLabel();
        colorj1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        nomj2 = new javax.swing.JLabel();
        colorj2 = new javax.swing.JButton();
        est = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        CaseCourante = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jeux = new javax.swing.JMenu();
        rejouer = new javax.swing.JMenuItem();
        quitter = new javax.swing.JMenuItem();
        Mjoueurs = new javax.swing.JMenu();
        selectionner = new javax.swing.JMenuItem();
        ajoute = new javax.swing.JMenuItem();
        visualiser = new javax.swing.JMenuItem();
        Statistiques = new javax.swing.JMenu();
        classement = new javax.swing.JMenuItem();
        score = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        center.setLayout(new java.awt.GridLayout(4, 4));
        getContentPane().add(center, java.awt.BorderLayout.CENTER);

        jLabel1.setText("Message :");
        south.add(jLabel1);

        message.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                messageActionPerformed(evt);
            }
        });
        south.add(message);

        getContentPane().add(south, java.awt.BorderLayout.SOUTH);

        nord.setLayout(new java.awt.GridLayout(2, 1));

        jPanel1.setPreferredSize(new java.awt.Dimension(200, 96));
        jPanel1.setLayout(new java.awt.GridLayout(2, 1));

        jPanel8.setLayout(new java.awt.GridLayout(3, 1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Joueur 1:");
        jPanel8.add(jLabel3);
        jPanel8.add(nomj1);
        jPanel8.add(colorj1);

        jPanel1.add(jPanel8);

        nord.add(jPanel1);

        jPanel2.setLayout(new java.awt.GridLayout(2, 1));

        jPanel7.setLayout(new java.awt.GridLayout(3, 0));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Joueur 2:");
        jPanel7.add(jLabel5);
        jPanel7.add(nomj2);
        jPanel7.add(colorj2);

        jPanel2.add(jPanel7);

        nord.add(jPanel2);

        getContentPane().add(nord, java.awt.BorderLayout.WEST);

        est.setPreferredSize(new java.awt.Dimension(125, 552));
        est.setLayout(new java.awt.GridLayout(4, 1));

        jPanel3.setPreferredSize(new java.awt.Dimension(85, 138));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Galet à déposer");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(jLabel2)
                .addContainerGap(59, Short.MAX_VALUE))
        );

        est.add(jPanel3);

        CaseCourante.setBackground(new java.awt.Color(24, 34, 242));
        CaseCourante.setPreferredSize(new java.awt.Dimension(75, 67));
        CaseCourante.setLayout(new java.awt.GridLayout(3, 1));
        est.add(CaseCourante);

        jPanel5.setPreferredSize(new java.awt.Dimension(75, 138));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 125, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 138, Short.MAX_VALUE)
        );

        est.add(jPanel5);

        jPanel6.setPreferredSize(new java.awt.Dimension(75, 138));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 125, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel4)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 138, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel4)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        est.add(jPanel6);

        getContentPane().add(est, java.awt.BorderLayout.EAST);

        jeux.setText("Jeux");

        rejouer.setText("Rejouer");
        rejouer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rejouerActionPerformed(evt);
            }
        });
        jeux.add(rejouer);

        quitter.setText("Quitter");
        quitter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitterActionPerformed(evt);
            }
        });
        jeux.add(quitter);

        jMenuBar1.add(jeux);

        Mjoueurs.setText("Joueurs");

        selectionner.setText("Sélectionner");
        selectionner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectionnerActionPerformed(evt);
            }
        });
        Mjoueurs.add(selectionner);

        ajoute.setText("Ajouter Joueur");
        ajoute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ajouteActionPerformed(evt);
            }
        });
        Mjoueurs.add(ajoute);

        visualiser.setText("Visualiser");
        visualiser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                visualiserActionPerformed(evt);
            }
        });
        Mjoueurs.add(visualiser);

        jMenuBar1.add(Mjoueurs);

        Statistiques.setText("Statistiques");

        classement.setText("Classement");
        classement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                classementActionPerformed(evt);
            }
        });
        Statistiques.add(classement);

        score.setText("Scores");
        score.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scoreActionPerformed(evt);
            }
        });
        Statistiques.add(score);

        jMenuBar1.add(Statistiques);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void messageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_messageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_messageActionPerformed

    private void classementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_classementActionPerformed
        // TODO add your handling code here:
        ClassementDlg dialog = new ClassementDlg(this,true,this.listeJ,this.lp);
        dialog.setVisible(true);
    }//GEN-LAST:event_classementActionPerformed

    private void rejouerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rejouerActionPerformed
        // TODO add your handling code here:
       // Créer un nouveau jeu et récupérer le plateau
        this.jeu = new Jeu();
        this.pjeu = jeu.getPlateau();
        // Vide la case courante si il y avait encore des galet 
        CaseCourante.removeAll();
        // Afficher le plateau avec les cases et les galets de base
        afficheplateau();
        // Réinitialiser les paramètres de jeu
        this.jcourant = 1;        // Le joueur 1 commence
        this.nbgj1 = 8;           // Nombre de galets pour le joueur 1
        this.nbgj2 = 8;           // Nombre de galets pour le joueur 2
        this.cpt = 0;             // Compteur de coups
        this.selection = false;   // Aucune case sélectionnée
        this.finParties = false;  // Partie en cours
        // Initialiser les variables de sélection et positions
        this.casecourante = null;
        this.xsel = -1;
        this.ysel = -1;
        this.xd = -1;
        this.yd = -1;
        this.pxd = -1;
        this.pyd = -1;
        // Afficher le message du joueur qui commence
        message.setText("C'est au joueur 1 de jouer");

    }//GEN-LAST:event_rejouerActionPerformed

    private void quitterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitterActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_quitterActionPerformed

    private void visualiserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_visualiserActionPerformed
        // TODO add your handling code here:
        VisuJoueursDlg dialog= new VisuJoueursDlg(this,true,listeJ);
        dialog.setVisible(true);
        
    }//GEN-LAST:event_visualiserActionPerformed

    private void ajouteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ajouteActionPerformed
        // TODO add your handling code here:
        SaisieJoueurDlg dialog =new SaisieJoueurDlg(this,true,listeJ);
        dialog.setVisible(true);
    }//GEN-LAST:event_ajouteActionPerformed

    private void selectionnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectionnerActionPerformed
        // TODO add your handling code here:
        SelectionJoueursDlg dialog = new SelectionJoueursDlg(this,true,listeJ);
        dialog.setVisible(true);
        // Vérifie si la sélection des joueurs a été validée
    if (dialog.getOk()) {
        // Récupère les joueurs sélectionnés dans SelectionJoueursDlg
        this.joueur1 = dialog.getJoueur1();
        this.joueur2 = dialog.getJoueur2();

        // Appelle la méthode pour mettre à jour l'affichage
        affichej();
    }
    }//GEN-LAST:event_selectionnerActionPerformed

    private void scoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scoreActionPerformed
        // TODO add your handling code here:
        Scoredlg dialog = new Scoredlg(this,true,listeJ,joueur1,joueur2);
        dialog.setVisible(true);
       
        
       
    }//GEN-LAST:event_scoreActionPerformed

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
            java.util.logging.Logger.getLogger(P4P.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(P4P.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(P4P.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(P4P.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                new P4P().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel CaseCourante;
    private javax.swing.JMenu Mjoueurs;
    private javax.swing.JMenu Statistiques;
    private javax.swing.JMenuItem ajoute;
    private javax.swing.JPanel center;
    private javax.swing.JMenuItem classement;
    private javax.swing.JButton colorj1;
    private javax.swing.JButton colorj2;
    private javax.swing.JPanel est;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JMenu jeux;
    private javax.swing.JTextField message;
    private javax.swing.JLabel nomj1;
    private javax.swing.JLabel nomj2;
    private javax.swing.JPanel nord;
    private javax.swing.JMenuItem quitter;
    private javax.swing.JMenuItem rejouer;
    private javax.swing.JMenuItem score;
    private javax.swing.JMenuItem selectionner;
    private javax.swing.JPanel south;
    private javax.swing.JMenuItem visualiser;
    // End of variables declaration//GEN-END:variables
}
