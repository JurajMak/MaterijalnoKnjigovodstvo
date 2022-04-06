
package zavrsni.view;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import zavrsni.util.ZavrsniUtil;

/**
 *
 * @author juraj
 */
public class Izbornik extends javax.swing.JFrame {
    
    private SimpleDateFormat df;
   
    public Izbornik() {
        initComponents();
        postavke();
    }
        
     private void postavke(){
        setTitle(ZavrsniUtil.getNaslov("-Izbornik-"));
        jApp.setText(ZavrsniUtil.NAZIV_APP);
        df = new SimpleDateFormat("dd. MMMM. yyy. HH:mm:ss", new Locale("hr","HR"));
        Vrijeme v = new Vrijeme();
        v.start();
       
    }

    private class Vrijeme extends Thread{

        @Override
        public void run() {
            lblTime.setText(df.format(new Date()));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                
               
            }
            run();
        }
        
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTime = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jApp = new javax.swing.JMenu();
        jIzlaz = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jmnOtpremnica = new javax.swing.JMenuItem();
        jmnIra = new javax.swing.JMenuItem();
        jRobno = new javax.swing.JMenu();
        jPrimka = new javax.swing.JMenuItem();
        jmbUra = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jmnPartner = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jmbStanje = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblTime.setText("jLabel1");

        jMenuBar1.setPreferredSize(new java.awt.Dimension(159, 30));

        jApp.setText("File");

        jIzlaz.setText("Izlaz");
        jIzlaz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jIzlazActionPerformed(evt);
            }
        });
        jApp.add(jIzlaz);

        jMenuBar1.add(jApp);

        jMenu2.setText("Ira");

        jmnOtpremnica.setText("Pregled Otpremnica");
        jmnOtpremnica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmnOtpremnicaActionPerformed(evt);
            }
        });
        jMenu2.add(jmnOtpremnica);

        jmnIra.setText("Kreiraj Ira");
        jmnIra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmnIraActionPerformed(evt);
            }
        });
        jMenu2.add(jmnIra);

        jMenuBar1.add(jMenu2);

        jRobno.setText("Ura");

        jPrimka.setText("Pregled Primka");
        jPrimka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPrimkaActionPerformed(evt);
            }
        });
        jRobno.add(jPrimka);

        jmbUra.setText("Pregled Ura");
        jmbUra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmbUraActionPerformed(evt);
            }
        });
        jRobno.add(jmbUra);

        jMenuBar1.add(jRobno);

        jMenu1.setText("Partner");

        jmnPartner.setText("Pregled ");
        jmnPartner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmnPartnerActionPerformed(evt);
            }
        });
        jMenu1.add(jmnPartner);

        jMenuBar1.add(jMenu1);

        jMenu3.setText("Stanje");

        jmbStanje.setText("Stanje robe");
        jmbStanje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmbStanjeActionPerformed(evt);
            }
        });
        jMenu3.add(jmbStanje);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 376, Short.MAX_VALUE)
                .addComponent(lblTime, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(303, Short.MAX_VALUE)
                .addComponent(lblTime, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jIzlazActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jIzlazActionPerformed
        dispose();
    }//GEN-LAST:event_jIzlazActionPerformed

    private void jmnOtpremnicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmnOtpremnicaActionPerformed
       new OtpremnicaPregled().setVisible(true);
    }//GEN-LAST:event_jmnOtpremnicaActionPerformed

    private void jmnIraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmnIraActionPerformed
       
    }//GEN-LAST:event_jmnIraActionPerformed

    private void jPrimkaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPrimkaActionPerformed
        new PrimkaUpdate().setVisible(true);
    
    }//GEN-LAST:event_jPrimkaActionPerformed

    private void jmbUraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmbUraActionPerformed
       new UraProzor().setVisible(true);
    }//GEN-LAST:event_jmbUraActionPerformed

    private void jmnPartnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmnPartnerActionPerformed
       new UnosPartner().setVisible(true);
    }//GEN-LAST:event_jmnPartnerActionPerformed

    private void jmbStanjeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmbStanjeActionPerformed
        new StanjeMenu().setVisible(true);
    }//GEN-LAST:event_jmbStanjeActionPerformed

   
 
  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jApp;
    private javax.swing.JMenuItem jIzlaz;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jPrimka;
    private javax.swing.JMenu jRobno;
    private javax.swing.JMenuItem jmbStanje;
    private javax.swing.JMenuItem jmbUra;
    private javax.swing.JMenuItem jmnIra;
    private javax.swing.JMenuItem jmnOtpremnica;
    private javax.swing.JMenuItem jmnPartner;
    private javax.swing.JLabel lblTime;
    // End of variables declaration//GEN-END:variables
}
