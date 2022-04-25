package zavrsni.view;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
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

       ucitajIru();
        postavke();

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
        p.setBrojOtpremnice(txtOtp.getText());
        p.setIra((Ira) cmbOtp.getSelectedItem());
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

        DefaultListModel<Roba> r = new DefaultListModel<>();
        if (p.getRoba() != null) {
            Collections.sort(p.getRoba(), new RobaComparator());

            r.addAll(p.getRoba());
        }
        lstRoba.setModel(r);

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
        lstRoba.clearSelection();
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblOtpremnica = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        txtCijena = new javax.swing.JTextField();
        btnPr = new javax.swing.JButton();
        btnBrisanje = new javax.swing.JButton();
        btnPromjena = new javax.swing.JButton();
        btnIzlaz = new javax.swing.JButton();
        jSat = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstRoba = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        txtOtp = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cmbOtp = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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

        btnIzlaz.setText("Povratak");
        btnIzlaz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIzlazActionPerformed(evt);
            }
        });

        jSat.setText("jLabel5");

        jScrollPane2.setViewportView(lstRoba);

        jLabel1.setText("Roba");

        jLabel3.setText("Br. Otp");

        jLabel4.setText("Br. Računa");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnIzlaz, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSat, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCijena, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtOtp, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnPr, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                                        .addComponent(btnBrisanje, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(41, 41, 41)
                                        .addComponent(btnPromjena, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(130, 130, 130))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 686, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(cmbOtp, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(57, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(cmbOtp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCijena, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtOtp, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPr))
                        .addGap(53, 53, 53))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnBrisanje)
                            .addComponent(btnPromjena))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnIzlaz)
                        .addGap(33, 33, 33))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jSat, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
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
            JOptionPane.showMessageDialog(getRootPane(), "Prvo odaberite stavku");
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
            JOptionPane.showMessageDialog(getRootPane(), "Prvo odaberite stavku");
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBrisanje;
    private javax.swing.JButton btnIzlaz;
    private javax.swing.JButton btnPr;
    private javax.swing.JButton btnPromjena;
    private javax.swing.JComboBox<Ira> cmbOtp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jSat;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<Roba> lstRoba;
    private javax.swing.JTable tblOtpremnica;
    private javax.swing.JTextField txtCijena;
    private javax.swing.JTextField txtOtp;
    // End of variables declaration//GEN-END:variables
}
