
package zavrsni.view;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import zavrsni.controller.ObradaIra;
import zavrsni.controller.ObradaOtpremnica;
import zavrsni.controller.ObradaRoba;
import zavrsni.model.Ira;
import zavrsni.model.Otpremnica;
import zavrsni.model.Roba;
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
    private ObradaRoba roba;
  
    public OtpremnicaPregled() {
        initComponents();
        otpremnica = new ObradaOtpremnica();
        roba = new ObradaRoba();

        sirinaStupca();
        postavke();
        ucitajRobe();
        ucitajIru();
        load();
       
    }
    
       private void load() {

        List<Otpremnica> entiteti = otpremnica.read();

        Object[] red = new Object[8];

        for (int i = 0; i < entiteti.size(); i++) {
            red[0] = entiteti.get(i).getId();
            red[1] = entiteti.get(i).getIra().getPartner().getNaziv();
            red[2] = entiteti.get(i).getRoba().get(0).getNaziv();
            red[3] = entiteti.get(i).getKolicina();
            red[4] = nf.format(entiteti.get(i).getCijena());
      //      red[5] = nf.format(entiteti.get(i).getRoba().getCijena().multiply(BigDecimal.valueOf(1.50).multiply(BigDecimal.valueOf(1.25))));
            red[6] = nf.format(entiteti.get(i).getCijena().multiply(BigDecimal.valueOf(entiteti.get(i).getKolicina()).multiply(BigDecimal.valueOf(1.75))));        
            red[7] = fd.format(new Date());
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
        setTitle(ZavrsniUtil.getNaslov("- Pregled Otpremnica -"));

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
        p.setRoba((List<Roba>) cmbRoba.getSelectedItem());
        p.setIra((Ira) cmbIra.getSelectedItem());
        

        try {

            p.setCijena(new BigDecimal(nf.parse(txtCijena.getText()).toString()));
        } catch (Exception e) {

        }

        try {
            p.setKolicina(Integer.parseInt(txtKolicina.getText()));
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
         List<Roba> ent = roba.read();      
        roba.setEntitet(ent.get(red));         
              
        var ro = roba.getEntitet();
        txtCijena.setText(p.getCijena() != null ? nf.format(p.getCijena()) : "");
        txtKolicina.setText(p.getKolicina().toString());

        if (p.getRoba() == null) {
            cmbRoba.setSelectedIndex(0);
        } else {
            cmbRoba.setSelectedItem(p.getRoba());
        }

        if (p.getIra()== null) {
            cmbIra.setSelectedIndex(0);
        } else {
            cmbIra.setSelectedItem(p.getIra());
        }

    }
          private void brisanjePolja() {
        txtCijena.setText(null);
        txtKolicina.setText(null);
        
        cmbIra.setSelectedIndex(0);
        cmbRoba.setSelectedIndex(0);
    }
      private void sirinaStupca() {
        tblOtpremnica.getColumnModel().getColumn(0).setPreferredWidth(20);
        tblOtpremnica.getColumnModel().getColumn(1).setPreferredWidth(150);
        tblOtpremnica.getColumnModel().getColumn(2).setPreferredWidth(150);
        tblOtpremnica.getColumnModel().getColumn(3).setPreferredWidth(20);
        tblOtpremnica.getColumnModel().getColumn(4).setPreferredWidth(20);
        tblOtpremnica.getColumnModel().getColumn(5).setPreferredWidth(20);
        tblOtpremnica.getColumnModel().getColumn(6).setPreferredWidth(20);
        tblOtpremnica.getColumnModel().getColumn(7).setPreferredWidth(50);
        tblOtpremnica.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

    }
   
      
    private void ucitajRobe() {
        DefaultComboBoxModel<Roba> r = new DefaultComboBoxModel<>();
        Roba robe = new Roba();
        robe.setId(Long.valueOf(0));
        robe.setNaziv("Nije odabrano");

        r.addElement(robe);
        new ObradaRoba().read().forEach(s -> {
            r.addElement(s);
        });
        cmbRoba.setModel(r);
    }

      
    private void ucitajIru() {
        DefaultComboBoxModel<Ira> i = new DefaultComboBoxModel<>();
        Ira ira = new Ira();
        ira.setId(Long.valueOf(0));
        ira.setBrojRacuna("Nije odabrano");

        i.addElement(ira);
        new ObradaIra().read().forEach(s -> {
            i.addElement(s);
        });
        cmbIra.setModel(i);
    }

      
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblOtpremnica = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtKolicina = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtCijena = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        cmbRoba = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cmbIra = new javax.swing.JComboBox<>();
        btnPr = new javax.swing.JButton();
        btnUnos = new javax.swing.JButton();
        btnBrisanje = new javax.swing.JButton();
        btnPromjena = new javax.swing.JButton();
        btnIzlaz = new javax.swing.JButton();
        jSat = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tblOtpremnica.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Br. Primke", "Dobavljač", "Naziv robe", "Količina", "Nab. cijena", "Prod. cijena", "Iznos", "Datum dospjeća"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
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

        jLabel3.setText("Količina");

        jLabel2.setText("Cijena");

        jLabel1.setText("Naziv Robe");

        jLabel5.setText("Broj Računa");

        btnPr.setText("Osvježi");
        btnPr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrActionPerformed(evt);
            }
        });

        btnUnos.setText("Nova Otpremnica");
        btnUnos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUnosActionPerformed(evt);
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

        btnIzlaz.setText("Povratak");
        btnIzlaz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIzlazActionPerformed(evt);
            }
        });

        jSat.setText("jLabel5");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSat, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtKolicina, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCijena, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(42, 42, 42)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbRoba, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbIra, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnPr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnUnos))
                        .addGap(18, 18, 18)
                        .addComponent(btnBrisanje, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(btnPromjena, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(174, 174, 174))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1070, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnIzlaz, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5)
                    .addComponent(btnPr))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKolicina, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCijena, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbIra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbRoba, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPromjena, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUnos)
                    .addComponent(btnBrisanje))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                .addGap(26, 26, 26)
                .addComponent(btnIzlaz)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSat, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7))
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

    private void btnUnosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUnosActionPerformed

        /*

        try {
            ro.setCijena(new BigDecimal(nf.parse(txtCijena.getText()).toString()));

            try {
                ro.setKolicina(ro.getKolicina() + Integer.parseInt(txtKolicina.getText()));
            } catch (NumberFormatException e) {
            }

            primka.setEntitet(new Primka());
            preuzmiVrijednosti();

            primka.create();
            JOptionPane.showMessageDialog(getRootPane(), "Stvorena nova primka pod brojem " + primka.getEntitet().getId() + " !");
            m.setRowCount(0);

            load();

            brisanjePolja();
        } catch (ZavrsniException ex) {
            JOptionPane.showMessageDialog(getRootPane(), ex.getPoruka());
        }
        */
        new OtpremnicaProzor().setVisible(true);
    }//GEN-LAST:event_btnUnosActionPerformed

    private void btnBrisanjeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrisanjeActionPerformed

        if (otpremnica.getEntitet() == null) {
            JOptionPane.showMessageDialog(getRootPane(), "Prvo odaberite stavku");
            return;
        }

        if (JOptionPane.showConfirmDialog(
            getRootPane(),
            "Sigurno obrisati \"" + "Primku broj " + otpremnica.getEntitet().getId() + "\"?",
            "Brisanje",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION) {

        return;
        }

        try {

            otpremnica.delete();
            JOptionPane.showMessageDialog(getRootPane(), "Primka pod brojem " + otpremnica.getEntitet().getId() + " obrisana!");
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
        List<Otpremnica> entiteti = otpremnica.read();
        List<Roba> ent = roba.read();
        var red = tblOtpremnica.getSelectedRow();

        otpremnica.setEntitet(entiteti.get(red));
        roba.setEntitet(ent.get(red));
        var p = otpremnica.getEntitet();
        var ro = roba.getEntitet();

        if (otpremnica.getEntitet() == null) {
            JOptionPane.showMessageDialog(getRootPane(), "Prvo odaberite stavku");
            return;
        }

        try {

            preuzmiVrijednosti();
            ro.setCijena(new BigDecimal(nf.parse(txtCijena.getText()).toString()));
            ro.setKolicina((p.getKolicina()-p.getKolicina())+(ro.getKolicina() + Integer.parseInt(txtKolicina.getText())));
            otpremnica.update();
            JOptionPane.showMessageDialog(getRootPane(), "Primka pod brojem " + otpremnica.getEntitet().getId() + " promijenjena!");
            m.setRowCount(0);
            load();

            brisanjePolja();
        } catch (ZavrsniException ex) {
            JOptionPane.showMessageDialog(getRootPane(), ex.getPoruka());
        } catch (ParseException ex) {
            Logger.getLogger(OtpremnicaPregled.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }//GEN-LAST:event_btnPromjenaActionPerformed

    private void btnIzlazActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIzlazActionPerformed
        dispose();
    }//GEN-LAST:event_btnIzlazActionPerformed

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBrisanje;
    private javax.swing.JButton btnIzlaz;
    private javax.swing.JButton btnPr;
    private javax.swing.JButton btnPromjena;
    private javax.swing.JButton btnUnos;
    private javax.swing.JComboBox<Ira> cmbIra;
    private javax.swing.JComboBox<Roba> cmbRoba;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jSat;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblOtpremnica;
    private javax.swing.JTextField txtCijena;
    private javax.swing.JTextField txtKolicina;
    // End of variables declaration//GEN-END:variables
}
