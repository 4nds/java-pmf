/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Student_UI;

import DatabaseConnection.*;
import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class StudentStartFrame extends javax.swing.JFrame {

    Student student;
    String ime_odabrani_kolegij;
    Kolegij odabrani_kolegij;
    ArrayList<Kolegij> mojiKolegiji;
    ArrayList<Kolegij> kolegiji;
    ArrayList<Obavijest> obavijesti;
    
    ComponentListener listener = new ComponentAdapter() {
      public void componentShown(ComponentEvent evt) {
        Component c = (Component) evt.getSource();
        init();
      }};
    
    private void init()
    {
        jLabel1.setText(student.getIme() + " " + student.getPrezime());
        getMojiKolegiji();
        initMojiKolegiji();
        getKolegiji();
        initKolegiji();
        getObavijesti();
        initObavijesti();
    }
    
    private void getMojiKolegiji()
    {
        try {
            mojiKolegiji = KolegijService.findAllByStudent(student);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, e + " getMojiKolegiji");
        }
    }
    
    private void getKolegiji()
    {
        try {
            kolegiji = KolegijService.getAll();
            kolegiji.removeAll(mojiKolegiji);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, e + " getKolegiji");
        }
    }
    
    private void getObavijesti()
    {
        try {
            obavijesti = new ArrayList<Obavijest>();
            for (Kolegij kolegij: mojiKolegiji)
            {
                ArrayList<Obavijest> nove_obavijesti = ObavijestService.findAllByKolegij(kolegij);
                obavijesti.addAll(nove_obavijesti);
            }
            // dodati sort po vremenu obavijesti
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, e + " getObavijesti");
        }
    }
    
    private void initMojiKolegiji()
    {
        DefaultListModel listModel = new DefaultListModel();
        for (Kolegij kolegij: mojiKolegiji)
        {
            String ime = kolegij.getIme();
            listModel.addElement(ime);
            System.out.println("Dodajem u moji kolegiji: " + ime);
        }
        jList1.setModel(listModel);
    }
    
    private void initKolegiji()
    {
        DefaultListModel listModel = new DefaultListModel();
        for (Kolegij kolegij: kolegiji)
        {
            String ime = kolegij.getIme();
            listModel.addElement(ime);
            System.out.println("Dodajem u kolegiji: " + ime);
        }
        jList2.setModel(listModel);
    }
    
    private void initObavijesti()
    {
        jTextArea1.setText("");
        String txt = "";
        for (Obavijest obvj: obavijesti)
        {
            String tekst_obvj = "";
            
            Timestamp ts = obvj.getVrijeme();
            Date date = new Date();
            date.setTime(ts.getTime());
            String formattedDate = new SimpleDateFormat("dd.MM.yyyy.").format(date);
            
            ArrayList<Obavijest> temp_obvj = new ArrayList<Obavijest>();
            temp_obvj.add(obvj);
            Kolegij kolegij_obvj = null;
            try {
                kolegij_obvj = KolegijService.findAllByObavijesti(temp_obvj).get(0);
            } 
            catch (Exception e) {
                JOptionPane.showMessageDialog(this, e + " initObavijesti");
            }
            String ime_kolegija = kolegij_obvj.getIme();
            
            tekst_obvj = "------ " + formattedDate + "\n" +
                         "------ " + ime_kolegija + "\n" +
                         obvj.getTekst() + "\n\n";
            
            txt += tekst_obvj;
        }
        jTextArea1.setText(txt);
    }
    
    /**
     * Creates new form StudentStartFrame
     */
    public StudentStartFrame(Student student) {
        initComponents();
        this.addComponentListener(listener);
        this.student = student;
        init();
        
        // prati odabir kolegija u Moji Kolegiji
        jList1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e)
                {
                    if(!e.getValueIsAdjusting()) {
                        if(!jList1.getSelectedValuesList().isEmpty())
                            ime_odabrani_kolegij = jList1.getSelectedValuesList().get(0);
                    }
                }
        });
        
        // prati odabir kolegija u Upiši novi kolegij
        jList2.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e)
                {
                    if(!e.getValueIsAdjusting()) {
                        if(!jList1.getSelectedValuesList().isEmpty())
                            ime_odabrani_kolegij = jList1.getSelectedValuesList().get(0);
                    }
                }
        });
        
        // prati odabir taba
        jTabbedPane1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (e.getSource() instanceof JTabbedPane) {
                    JTabbedPane pane = (JTabbedPane) e.getSource();
                    int index = pane.getSelectedIndex();
                    
                    if (index == 0) {
                        System.out.println("Odabran popis mojih kolegija.");
                        getMojiKolegiji();
                        initMojiKolegiji();
                    }
                    if (index == 1) {
                        getObavijesti();
                        initObavijesti();
                        System.out.println("Odabran popis obavijesti.");
                    }
                    if (index == 2) {
                        getKolegiji();
                        initKolegiji();
                        System.out.println("Odabran popis neupisanih kolegija.");
                    }
                }
            }
        });
    }
        
    private void otvoriStranicu(String ime) {
        try {
            odabrani_kolegij = KolegijService.find(ime);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
        
        this.setVisible(false);
        new StudentStraniceFrame(student, this, odabrani_kolegij).setVisible(true);
    }
    
     
    
    private void upisiKolegij(String ime_kolegija)
    {
        Kolegij kolegij_u = null;
        try {
            kolegij_u = KolegijService.find(ime_kolegija);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, e + " trazim kolegij za upis");
        }
        if (kolegij_u == null) {
            JOptionPane.showMessageDialog(this,"kolegij je null");
            return;
        }
        try {
            UpisService.insert(kolegij_u, student, false);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, e + " insert u upis");
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

        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Ime studenta");

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        jTabbedPane1.addTab("Moji kolegiji", jScrollPane1);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jTabbedPane1.addTab("Obavijesti", jScrollPane2);

        jList2.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(jList2);

        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton1.setText("Upiši");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(567, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane3)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(68, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Upiši novi kolegij", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {                                    
        // TODO add your handling code here:
        JList list = (JList)evt.getSource();
        if (evt.getClickCount() == 2) {
            otvoriStranicu(ime_odabrani_kolegij);
        }
    }
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        upisiKolegij(ime_odabrani_kolegij);
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(StudentStartFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StudentStartFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StudentStartFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StudentStartFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList<String> jList1;
    private javax.swing.JList<String> jList2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
