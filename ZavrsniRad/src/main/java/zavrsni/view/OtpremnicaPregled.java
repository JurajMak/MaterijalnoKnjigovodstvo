
package zavrsni.view;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import zavrsni.controller.ObradaIra;
import zavrsni.controller.ObradaOtpremnica;
import zavrsni.model.Ira;
import zavrsni.model.Otpremnica;
import zavrsni.util.ZavrsniException;
import zavrsni.util.ZavrsniUtil;

/**
 *
 * @author juraj
 */
public class OtpremnicaPregled extends javax.swing.JFrame {
    private DefaultTableModel m;
    private SimpleDateFormat df, fd;
    private DecimalFormat nf;
    private ObradaOtpremnica otpremnica;
   
  
    public OtpremnicaPregled() {
        initComponents();
        otpremnica = new ObradaOtpremnica();
        

      
        postavke();
       
        ucitajIru();
        load();
       
    }
    
       private void load() {

        List<Otpremnica> entiteti = otpremnica.read();

        Object[] red = new Object[4];

        for (int i = 0; i < entiteti.size(); i++) {
            red[0] = entiteti.get(i).getId();
            red[1] = entiteti.get(i).getBrojOtpremnice();
            red[2] = nf.format(entiteti.get(i).getCijena());
            red[3] = nf.format(entiteti.get(i).getCijena().multiply(BigDecimal.valueOf(1.25)));
            m.addRow(red);
        }
    }
    
      private void postavke() {
        tblOtpremnica.getSelectionModel().addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            @Override
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                proba(evt);
            }
        });
        fd = new SimpleDateFormat("dd. MMMM. yyy.", new Locale("hr", "HR")); 

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("hr", "HR"));
        nf = new DecimalFormat("###,###.00", symbols);

        m = (DefaultTableModel) tblOtpremnica.getModel();
        setTitle(ZavrsniUtil.getNaslov("-Pregled Otpremnica-"));

        df = new SimpleDateFormat("dd. MMMM. yyy. HH:mm:ss", new Locale("hr", "HR"));
        Vrijeme v = new Vrijeme();
        v.start();
    }

    private class Vrijeme extends Thread {

        @Override
        public void run() {
            jSat.setText(df.format(new Date()));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {

            }
            run();
        }
        
    }
    
    
     private void preuzmiVrijednosti() {

        var p = otpremnica.getEntitet();
        
         p.setIra((Ira) cmbOtp.getSelectedItem());
         p.setBrojOtpremnice(txtOtp.getText());

        try {

            p.setCijena(new BigDecimal(nf.parse(txtCijena.getText()).toString()));
        } catch (Exception e) {

        }

        
        
    }
      private void proba(javax.swing.event.ListSelectionEvent evt) {
        if (evt.getValueIsAdjusting() || tblOtpremnica.getSelectedRow() < 0) {
            return;
        }
        
        List<Otpremnica> entiteti = otpremnica.read();
        var red = tblOtpremnica.getSelectedRow();
        otpremnica.setEntitet(entiteti.get(red));
        var p = otpremnica.getEntitet();
        txtOtp.setText(p.getBrojOtpremnice());
        txtCijena.setText(p.getCijena() != null ? nf.format(p.getCijena()) : "");
        
        
        if (p.getIra()== null) {
            cmbOtp.setSelectedIndex(0);
        } else {
            cmbOtp.setSelectedItem(p.getIra());
        }

    }
      
        private void ucitajIru() {
        DefaultComboBoxModel<Ira> u = new DefaultComboBoxModel<>();
        Ira ira = new Ira();
        ira.setId(Long.valueOf(0));
        ira.setBrojRacuna("Nije odabrano");

        u.addElement(ira);
        new ObradaIra().read().forEach(s -> {
            u.addElement(s);
        });
        cmbOtp.setModel(u);
    }
          private void brisanjePolja() {
        txtCijena.setText(null);
        txtOtp.setText(null);
        
        cmbOtp.setSelectedIndex(0);
        
    }
   
   
      
  
      
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSat = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblOtpremnica = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        txtCijena = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtOtp = new javax.swing.JTextField();
        btnPr = new javax.swing.JButton();
        btnBrisanje = new javax.swing.JButton();
        btnPromjena = new javax.swing.JButton();
        cmbOtp = new javax.swing.JComboBox<>();
        btnAzuriraj1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        btnIzlaz = new javax.swing.JButton();
        btnNovaOtp = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jSat.setText("jLabel5");

        tblOtpremnica.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Šifra Otp", "Br.Otp", "Cijena", "Iznos"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblOtpremnica.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblOtpremnicaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblOtpremnica);

        jLabel2.setText("Cijena");

        jLabel3.setText("Br. Otp");

        btnPr.setText("Osvježi");
        btnPr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrActionPerformed(evt);
            }
        });

        btnBrisanje.setText("Obriši");
        btnBrisanje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrisanjeActionPerformed(evt);
            }
        });
        btnBrisanje.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnBrisanjeKeyPressed(evt);
            }
        });

        btnPromjena.setText("Promijeni");
        btnPromjena.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPromjenaActionPerformed(evt);
            }
        });

        btnAzuriraj1.setText("Spoji s Ira");
        btnAzuriraj1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAzuriraj1ActionPerformed(evt);
            }
        });

        jLabel6.setText("Br. Računa");

        btnIzlaz.setText("Povratak");
        btnIzlaz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIzlazActionPerformed(evt);
            }
        });

        btnNovaOtp.setText("Nova Otp");
        btnNovaOtp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovaOtpActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtOtp, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCijena, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnPr, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnBrisanje, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnPromjena, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnAzuriraj1))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnNovaOtp)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(cmbOtp, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(60, 60, 60))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnIzlaz, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jSat, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 686, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnNovaOtp, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(cmbOtp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnPr)
                            .addComponent(btnBrisanje)
                            .addComponent(btnPromjena)
                            .addComponent(btnAzuriraj1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtOtp, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCijena, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnIzlaz, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tblOtpremnicaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblOtpremnicaMouseClicked

        if (evt.getButton() == 3) {
            brisanjePolja();
        }
    }//GEN-LAST:event_tblOtpremnicaMouseClicked

    private void btnPrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrActionPerformed
        m.setRowCount(0);
        load();
    }//GEN-LAST:event_btnPrActionPerformed

    private void btnBrisanjeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrisanjeActionPerformed

        if (otpremnica.getEntitet() == null) {
            JOptionPane.showMessageDialog(getRootPane(), "Prvo odaberite stavku!");
            return;
        }

        if (JOptionPane.showConfirmDialog(
            getRootPane(),
            "Sigurno obrisati \"" + "Otpremnicu broj " + otpremnica.getEntitet().getId() + "\"?",
            "Brisanje",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION) {

        return;
        }

        try {

            otpremnica.delete();
            JOptionPane.showMessageDialog(getRootPane(), "Otpremnica pod brojem " + otpremnica.getEntitet().getId() + " obrisana!");
            m.setRowCount(0);
            load();
            brisanjePolja();
        } catch (ZavrsniException ex) {
            JOptionPane.showMessageDialog(getRootPane(), ex.getPoruka());
        }
    }//GEN-LAST:event_btnBrisanjeActionPerformed

    private void btnBrisanjeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnBrisanjeKeyPressed

    }//GEN-LAST:event_btnBrisanjeKeyPressed

    private void btnPromjenaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPromjenaActionPerformed
        String samobroj = "[,./0-9]+";
        Pattern pe = Pattern.compile(samobroj);
        Matcher ma = pe.matcher(txtCijena.getText());
        Matcher mi = pe.matcher(txtOtp.getText());
        if (!mi.matches() || !ma.matches()) {
            JOptionPane.showMessageDialog(getRootPane(), "Unesite brojčanu vrijednost");
            return;
        }

        if (otpremnica.getEntitet() == null) {
            JOptionPane.showMessageDialog(getRootPane(), "Prvo odaberite stavku!");
            return;
        }
        if(cmbOtp.getSelectedIndex()==0){
            JOptionPane.showMessageDialog(getRootPane(), "Promjena vrijednosti je moguća tek nakon unosa Ira i spajanja otpremnice s Ira-om!");
            return;
        } 
        try {
            preuzmiVrijednosti();

            otpremnica.update();
            JOptionPane.showMessageDialog(getRootPane(), "Otpremnica pod brojem " + otpremnica.getEntitet().getId() + " promijenjena!");
            m.setRowCount(0);
            load();

            brisanjePolja();
        } catch (ZavrsniException ex) {
            JOptionPane.showMessageDialog(getRootPane(), ex.getPoruka());
        }
    }//GEN-LAST:event_btnPromjenaActionPerformed

    private void btnIzlazActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIzlazActionPerformed
        dispose();
    }//GEN-LAST:event_btnIzlazActionPerformed

    private void btnNovaOtpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovaOtpActionPerformed
        new OtpremnicaProzor().setVisible(true);
    }//GEN-LAST:event_btnNovaOtpActionPerformed

    private void btnAzuriraj1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAzuriraj1ActionPerformed
          if (otpremnica.getEntitet() == null) {
            JOptionPane.showMessageDialog(getRootPane(), "Prvo odaberite stavku!");
            return;
        }

        try {
            preuzmiVrijednosti();
           
            otpremnica.update();
            JOptionPane.showMessageDialog(getRootPane(), "Otpremnica pod brojem " + otpremnica.getEntitet().getId()  + 
                    " povezana sa Ira-om broj " + otpremnica.getEntitet().getIra()+"!");
            m.setRowCount(0);
            load();

            brisanjePolja();
        } catch (ZavrsniException ex) {
            JOptionPane.showMessageDialog(getRootPane(), ex.getPoruka());
        }
    }//GEN-LAST:event_btnAzuriraj1ActionPerformed

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAzuriraj1;
    private javax.swing.JButton btnBrisanje;
    private javax.swing.JButton btnIzlaz;
    private javax.swing.JButton btnNovaOtp;
    private javax.swing.JButton btnPr;
    private javax.swing.JButton btnPromjena;
    private javax.swing.JComboBox<Ira> cmbOtp;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jSat;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblOtpremnica;
    private javax.swing.JTextField txtCijena;
    private javax.swing.JTextField txtOtp;
    // End of variables declaration//GEN-END:variables
}
