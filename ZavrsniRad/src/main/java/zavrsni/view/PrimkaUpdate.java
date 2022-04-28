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
import zavrsni.controller.ObradaPrimka;
import zavrsni.controller.ObradaRoba;
import zavrsni.controller.ObradaUra;
import zavrsni.model.Primka;
import zavrsni.model.Ura;
import zavrsni.util.ZavrsniException;
import zavrsni.util.ZavrsniUtil;

/**
 *
 * @author juraj
 */
public class PrimkaUpdate extends javax.swing.JFrame {

    private DefaultTableModel m;
    private SimpleDateFormat df, fd;
    private DecimalFormat nf;
    private ObradaPrimka primka;
    private ObradaRoba roba;

    public PrimkaUpdate() {
        initComponents();

        primka = new ObradaPrimka();
        roba = new ObradaRoba();

        
        postavke();
        load();
        
        ucitajUru();

    }

       private void load() {

        List<Primka> entiteti = primka.read();

        Object[] red = new Object[4];

        for (int i = 0; i < entiteti.size(); i++) {
            red[0] = entiteti.get(i).getId();
            red[1] = entiteti.get(i).getOtpremnicaPrimka();
            red[2] = nf.format(entiteti.get(i).getCijena());
            red[3] = nf.format(entiteti.get(i).getCijena().multiply(BigDecimal.valueOf(1.25)));

            m.addRow(red);
        }
        tblPrimka.setModel(m);
    }

    private void postavke() {
        tblPrimka.getSelectionModel().addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            @Override
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                proba(evt);
            }
        });
        fd = new SimpleDateFormat("dd. MMMM. yyy.", new Locale("hr", "HR")); 

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("hr", "HR"));
        nf = new DecimalFormat("###,###.00", symbols);

        m = (DefaultTableModel) tblPrimka.getModel();
        setTitle(ZavrsniUtil.getNaslov("-Pregled Primka-"));

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

        var p = primka.getEntitet();
        p.setOtpremnicaPrimka(txtOtpPrim.getText());
        p.setUra((Ura) cmbUra.getSelectedItem());
        

        try {

            p.setCijena(new BigDecimal(nf.parse(txtCijena.getText()).toString()));
        } catch (Exception e) {

        }

        
        
    }

    private void proba(javax.swing.event.ListSelectionEvent evt) {
        if (evt.getValueIsAdjusting() || tblPrimka.getSelectedRow() < 0) {
            return;
        }
        
        List<Primka> entiteti = primka.read();
        var red = tblPrimka.getSelectedRow();
        primka.setEntitet(entiteti.get(red));
        var p = primka.getEntitet();
              
              
        var ro = roba.getEntitet();
        txtCijena.setText(p.getCijena() != null ? nf.format(p.getCijena()) : "");
        txtOtpPrim.setText(p.getOtpremnicaPrimka());

        if (p.getUra() == null) {
            cmbUra.setSelectedIndex(0);
        } else {
            cmbUra.setSelectedItem(p.getUra());
        }
       

    }

    private void brisanjePolja() {
        txtCijena.setText(null);
        txtOtpPrim.setText(null);
        
        cmbUra.setSelectedIndex(0);
        
    }

  
    private void ucitajUru() {
        DefaultComboBoxModel<Ura> u = new DefaultComboBoxModel<>();
        Ura ura = new Ura();
        ura.setId(Long.valueOf(0));
        ura.setBrojRacuna("Nije odabrano");

        u.addElement(ura);
        new ObradaUra().read().forEach(s -> {
            u.addElement(s);
        });
        cmbUra.setModel(u);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        txtCijena = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtOtpPrim = new javax.swing.JTextField();
        btnUnos = new javax.swing.JButton();
        btnIzlaz = new javax.swing.JButton();
        jSat = new javax.swing.JLabel();
        btnPromjena = new javax.swing.JButton();
        btnBrisanje = new javax.swing.JButton();
        btnPr = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPrimka = new javax.swing.JTable();
        cmbUra = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        btnAzuriraj = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        jLabel2.setText("Cijena");

        txtCijena.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCijenaKeyPressed(evt);
            }
        });

        jLabel3.setText("Br.Otp-Prim");

        txtOtpPrim.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtOtpPrimKeyPressed(evt);
            }
        });

        btnUnos.setText("Nova Primka");
        btnUnos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUnosActionPerformed(evt);
            }
        });

        btnIzlaz.setText("Povratak");
        btnIzlaz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIzlazActionPerformed(evt);
            }
        });

        jSat.setText("jLabel5");

        btnPromjena.setText("Promijeni");
        btnPromjena.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPromjenaActionPerformed(evt);
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

        btnPr.setText("Osvježi");
        btnPr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrActionPerformed(evt);
            }
        });

        tblPrimka.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Br. Primke", "Br. Otp-Prim", "Cijena", "Iznos"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPrimka.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPrimkaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPrimka);

        jLabel4.setText("Br. Računa");

        btnAzuriraj.setText("Spoji s Ura");
        btnAzuriraj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAzurirajActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnIzlaz, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(334, 334, 334)
                        .addComponent(jSat, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 583, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtOtpPrim, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPr, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(51, 51, 51)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCijena, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnBrisanje, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(28, 28, 28)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnUnos)
                                    .addComponent(btnPromjena)))
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cmbUra, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnAzuriraj, javax.swing.GroupLayout.Alignment.TRAILING)))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtCijena, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtOtpPrim, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnUnos, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnPromjena)
                                    .addComponent(btnBrisanje))
                                .addGap(48, 48, 48))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnPr)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbUra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAzuriraj)
                        .addGap(310, 310, 310)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnIzlaz)
                    .addComponent(jSat, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnUnosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUnosActionPerformed
        
 
        new NovaPrimka().setVisible(true);
    }//GEN-LAST:event_btnUnosActionPerformed

    private void btnIzlazActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIzlazActionPerformed
        dispose();
    }//GEN-LAST:event_btnIzlazActionPerformed

    private void btnPromjenaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPromjenaActionPerformed
        String samobroj = "[,./0-9]+";
        Pattern pe = Pattern.compile(samobroj);
        Matcher ma = pe.matcher(txtCijena.getText());
        Matcher mi = pe.matcher(txtOtpPrim.getText());
        if (!mi.matches() || !ma.matches()) {
            JOptionPane.showMessageDialog(getRootPane(), "Unesite brojčanu vrijednost!");
            return;
        }

        if (primka.getEntitet() == null) {
            JOptionPane.showMessageDialog(getRootPane(), "Prvo odaberite stavku");
            return;
        }
        
        if(cmbUra.getSelectedIndex()==0){
            JOptionPane.showMessageDialog(getRootPane(), "Promjena vrijednosti je moguća tek nakon unosa Ura i spajanja primke s Ura-om!");
            return;
        }
        
        
        
        if (primka.getEntitet() == null) {
            JOptionPane.showMessageDialog(getRootPane(), "Prvo odaberite stavku");
            return;
        }
         
        try {
            
            
            preuzmiVrijednosti();
            
            
            primka.update();
            JOptionPane.showMessageDialog(getRootPane(), "Primka pod brojem " + primka.getEntitet().getId() + " promijenjena!");
            m.setRowCount(0);
            load();

            brisanjePolja();
        } catch (ZavrsniException ex) {
            JOptionPane.showMessageDialog(getRootPane(), ex.getPoruka());
        }

    }//GEN-LAST:event_btnPromjenaActionPerformed

    private void btnBrisanjeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrisanjeActionPerformed

        if (primka.getEntitet() == null) {
            JOptionPane.showMessageDialog(getRootPane(), "Prvo odaberite stavku");
            return;
        }

        if (JOptionPane.showConfirmDialog(
                getRootPane(),
                "Sigurno obrisati \"" + "Primku broj " + primka.getEntitet().getId() + "\"?",
                "Brisanje",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION) {

            return;
        }

        try {

            primka.delete();
            JOptionPane.showMessageDialog(getRootPane(), "Primka pod brojem " + primka.getEntitet().getId() + " obrisana!");
            m.setRowCount(0);
            load();
            brisanjePolja();
        } catch (ZavrsniException ex) {
            JOptionPane.showMessageDialog(getRootPane(), ex.getPoruka());
        }

    }//GEN-LAST:event_btnBrisanjeActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked

        if (evt.getButton() == 3) {
            brisanjePolja();
        }
       

    }//GEN-LAST:event_formMouseClicked

    private void btnBrisanjeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnBrisanjeKeyPressed

    }//GEN-LAST:event_btnBrisanjeKeyPressed

    private void btnPrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrActionPerformed
         m.setRowCount(0);
            load();
    }//GEN-LAST:event_btnPrActionPerformed

    private void tblPrimkaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPrimkaMouseClicked

        if (evt.getButton() == 3) {
            brisanjePolja();
        }
    }//GEN-LAST:event_tblPrimkaMouseClicked

    private void btnAzurirajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAzurirajActionPerformed
        if (primka.getEntitet() == null) {
            JOptionPane.showMessageDialog(getRootPane(), "Prvo odaberite stavku!");
            return;
        }

        try {

            preuzmiVrijednosti();

            primka.update();
            JOptionPane.showMessageDialog(getRootPane(), "Primka pod brojem " + primka.getEntitet().getId() +
                " povezana sa Ura-om broj " + primka.getEntitet().getUra()+"!");
            m.setRowCount(0);
            load();

            brisanjePolja();
        } catch (ZavrsniException ex) {
            JOptionPane.showMessageDialog(getRootPane(), ex.getPoruka());
        }
    }//GEN-LAST:event_btnAzurirajActionPerformed

    private void txtCijenaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCijenaKeyPressed
      
    }//GEN-LAST:event_txtCijenaKeyPressed

    private void txtOtpPrimKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOtpPrimKeyPressed
   
    }//GEN-LAST:event_txtOtpPrimKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAzuriraj;
    private javax.swing.JButton btnBrisanje;
    private javax.swing.JButton btnIzlaz;
    private javax.swing.JButton btnPr;
    private javax.swing.JButton btnPromjena;
    private javax.swing.JButton btnUnos;
    private javax.swing.JComboBox<Ura> cmbUra;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jSat;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPrimka;
    private javax.swing.JTextField txtCijena;
    private javax.swing.JTextField txtOtpPrim;
    // End of variables declaration//GEN-END:variables
}
