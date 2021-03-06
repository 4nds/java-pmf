/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Prof_UI;

import DatabaseConnection.Kolegij;
import DatabaseConnection.KolegijService;
import DatabaseConnection.Profesor;
import DatabaseConnection.Student;
import DatabaseConnection.StudentService;
import DatabaseConnection.Upis;
import DatabaseConnection.UpisService;
import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author David
 */
public class ProfStartFrame extends javax.swing.JFrame {

    Profesor prof;
    String ime_odabranog_kolegija;
    Kolegij odabran_kolegij;
    String tekst_odabranog_upisa;
    Upis odabran_upis;
    ArrayList<Kolegij> kolegiji;
    ArrayList<Upis> upisi;
    ArrayList<Student> upisi_studenti;
    ArrayList<Kolegij> upisi_kolegiji;
    ArrayList<String> upisi_tekstovi;
    
    ComponentListener listener = new ComponentAdapter() {
      public void componentShown(ComponentEvent evt) {
        Component c = (Component) evt.getSource();
        getKolegiji();
        initKolegiji();
        getUpisi();
        initUpisi();
      }};
    
    /**
     * Creates new form ProfStartFrame
     */
    public ProfStartFrame(Profesor prof) {
        initComponents();
        this.addComponentListener(listener);
        this.prof = prof;
        
        jLabel1.setText(prof.getIme() + " " + prof.getPrezime());
        
        jList1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e)
                {
                    if(!e.getValueIsAdjusting()) {
                        if(!jList1.getSelectedValuesList().isEmpty())
                            ime_odabranog_kolegija = jList1.getSelectedValuesList().get(0);
                    }
                }
        });
        
        jTabbedPane2.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (e.getSource() instanceof JTabbedPane) {
                    JTabbedPane pane = (JTabbedPane) e.getSource();
                    int index = pane.getSelectedIndex();
                    
                    if (index == 0) {
                        System.out.println("Odabran popis kolegija.");
                        initKolegiji();
                    }
                    if (index == 1) {
                        initUpisi();
                        System.out.println("Odabran popis upisa.");
                    }
                }
            }
        });
        
        jList2.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e)
                {
                    if(!e.getValueIsAdjusting()) {
                        if (!jList2.getSelectedValuesList().isEmpty())
                            tekst_odabranog_upisa = jList2.getSelectedValuesList().get(0);
                    }
                }
        });
        
        getKolegiji();
        initKolegiji();
        getUpisi();
        initUpisi();
    }
    
    // dohvati zahtjeve za upis i napravi listu
    
    private void getUpisi()
    {
        try {
            upisi = UpisService.findAllByKolegiji(kolegiji);
            
            upisi_studenti = new ArrayList<>();
            upisi_kolegiji = new ArrayList<>();
            upisi_tekstovi = new ArrayList<>();
            for (int i=0; i<upisi.size(); i++)
            {
                upisi_studenti.add(StudentService.findByJmbag(upisi.get(i).getJmbag()));
                upisi_kolegiji.add(KolegijService.findById(upisi.get(i).getKolegijId()));
                upisi_tekstovi.add(upisi_studenti.get(i).getIme() + " " + upisi_studenti.get(i).getPrezime() + " - " + upisi_kolegiji.get(i).getIme());
            }
            
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, e + " initUpis");
        }
    }
    
    private void initUpisi()
    {
        DefaultListModel listModel = new DefaultListModel();
        for (int i=0; i<upisi.size(); i++)
        {
            if (upisi.get(i).getPotvrdjeno() == false)
            {
                //listModel.addElement(upisi.get(i).getJmbag() + " " + upisi.get(i).getKolegijId());
                listModel.addElement(upisi_tekstovi.get(i));
                System.out.println("Dodajem u upis");
            }
        }
        /*
        for (Upis upis: upisi)
        {
            if (upis.getPotvrdjeno() == false) {
                listModel.addElement(upis.getJmbag() + " " + upis.getKolegijId());                
                System.out.println("Dodajem u upis");
            }
        }
        */
        jList2.setModel(listModel);
    }
    
    private void otvoriStranicu(String ime) {
        try {
            odabran_kolegij = KolegijService.find(ime);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
        
        this.setVisible(false);
        new ProfStranicaFrame(prof, this, odabran_kolegij).setVisible(true);
    }
    
    // dohvacamo kolegije i stavljamo ih u listu
    
    private void getKolegiji()
    {
        try {
            kolegiji = KolegijService.findAllByProfesor(prof);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, e + " getKolegiji");
        }
    }
    
    private void initKolegiji()
    {
        DefaultListModel listModel = new DefaultListModel();
        for (Kolegij kolegij: kolegiji)
        {
            listModel.addElement(kolegij.getIme());
            System.out.println("Dodajem kolegij:" + kolegij.getIme());
        }
        jList1.setModel(listModel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList<>();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jList1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Moji kolegiji", jPanel1);

        jList2.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList2);

        jButton2.setText("Odobri");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jButton2))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 768, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Upisi", jPanel2);

        jButton1.setText("Novi kolegij");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Ime profesora");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTabbedPane2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        new KreirajKolegijFrame(prof, this).setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        //String[] upis_podaci = tekst_odabranog_upisa.split(" ");
        int i = upisi_tekstovi.indexOf(tekst_odabranog_upisa);
        //Upis novi_upis = new Upis(upis_podaci[0], upis_podaci[1], true);
        // odobri upis
        try {
            if (i > 0) {
                upisi.get(i).setPotvrdjeno(true);
                UpisService.update(upisi.get(i));
                getUpisi();
                initUpisi();
            }
           
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, e + " odobravanje upisa");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        // TODO add your handling code here:
        JList list = (JList)evt.getSource();
        if (evt.getClickCount() == 2) {
            otvoriStranicu(ime_odabranog_kolegija);
        }
    }//GEN-LAST:event_jList1MouseClicked

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
            java.util.logging.Logger.getLogger(ProfStartFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProfStartFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProfStartFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProfStartFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList<String> jList1;
    private javax.swing.JList<String> jList2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane2;
    // End of variables declaration//GEN-END:variables
}
