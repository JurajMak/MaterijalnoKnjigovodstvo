package zavrsni.view;

import com.github.lgooddatepicker.components.DatePickerSettings;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import zavrsni.controller.ObradaOtpremnica;
import zavrsni.controller.ObradaPartner;
import zavrsni.controller.ObradaRoba;
import zavrsni.model.Otpremnica;
import zavrsni.model.Partner;
import zavrsni.model.Roba;
import zavrsni.util.ZavrsniException;
import zavrsni.util.ZavrsniUtil;

/**
 *
 * @author juraj
 */
public class OtpremnicaProzor extends javax.swing.JFrame {

    private ObradaOtpremnica otpremnica;
    private ObradaRoba roba;
    private DecimalFormat nf;
    private SimpleDateFormat df, fd;
    private DefaultTableModel m;
    private List<Roba> prodaja = new ArrayList<>();
    private BigDecimal iznos = BigDecimal.ZERO;
    private BigDecimal total = BigDecimal.ZERO;

    public OtpremnicaProzor() {
        initComponents();

        otpremnica = new ObradaOtpremnica();
        roba = new ObradaRoba();
        lstRoba.setCellRenderer(new PrikazRoba());

        postavke();
        ucitajKupca();
        ucitajRobe();

        test();

    }

    private void ucitajRobe() {
        DefaultListModel<Roba> r = new DefaultListModel<>();
        List<Roba> entiteti;

        entiteti = roba.read(txtTrazi.getText());

        Collections.sort(entiteti, new RobaComparator());

        for (Roba ro : entiteti) {
            r.addElement(ro);
        }

        lstRoba.setModel(r);

    }

    private void postavke() {
        m = (DefaultTableModel) tblDodajOtpremnicu.getModel();
        DatePickerSettings dps = new DatePickerSettings(new Locale("hr", "HR"));
        dps.setFormatForDatesCommonEra("dd.MM.yyyy");
        dps.setTranslationClear("O??isti");
        dps.setTranslationToday("Danas");
        dpsDatum.setSettings(dps);

        dpsDatum.setDateToToday();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("hr", "HR"));
        nf = new DecimalFormat("###,###.00", symbols);

        setTitle(ZavrsniUtil.getNaslov("-Nova Otpremnica-"));
        fd = new SimpleDateFormat("dd. MMMM. yyy.", new Locale("hr", "HR"));
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

    private void brisanjePolja() {
        txtCijena.setText(null);
        txtKolicina.setText(null);
        otpremnica.setEntitet(null);
        cmbPartner.setSelectedIndex(0);
        m.setRowCount(0);

    }

    private void ucitajKupca() {
        DefaultComboBoxModel<Partner> p = new DefaultComboBoxModel<>();
        Partner partner = new Partner();
        partner.setId(Long.valueOf(0));
        partner.setNaziv("Nije odabrano");

        p.addElement(partner);
        new ObradaPartner().read().forEach(s -> {
            p.addElement(s);
        });
        cmbPartner.setModel(p);
    }

    private void preuzimanjeVrijednosti() {
        otpremnica.setEntitet(new Otpremnica());
        var p = otpremnica.getEntitet();

        p.setBrojOtpremnice(txtBrojOtpremnice.getText());
        p.setRoba((Roba) lstRoba.getSelectedValue());

        try {

            p.setCijena(new BigDecimal(nf.parse(txtCijena.getText()).toString()));
        } catch (Exception e) {

        }

        try {
            p.setKolicina(Integer.parseInt(txtKolicina.getText()));
        } catch (Exception e) {

        }
    }

    private void test() {
        m = new DefaultTableModel();
        otpremnica = new ObradaOtpremnica();
        String[] s = {"??ifra", "Naziv", "Kupac", "Koli??ina", "M.Jed", "Cijena", "Br.Otp", "Datum izdavanja"};
        for (String i : s) {
            m.addColumn(i);
        }

        tblDodajOtpremnicu.setModel(m);

    }

    private void vrijednosti() throws ParseException {

        otpremnica.setEntitet(new Otpremnica());

        var p = otpremnica.getEntitet();
        p.setBrojOtpremnice(txtBrojOtpremnice.getText());
        p.setRoba(lstRoba.getSelectedValue());

        try {

            p.setCijena(new BigDecimal(nf.parse(txtCijena.getText()).toString()));
        } catch (Exception e) {

        }
        p.setKolicina((Integer.parseInt(txtKolicina.getText())));
        // txtCijena.setText(p.getCijena() != null ? nf.format(p.getCijena()) : "");
        txtKolicina.setText(p.getKolicina().toString());

        for (int i = 0, rows = tblDodajOtpremnicu.getRowCount(); i < rows; i++) {
            Integer kol = (Integer) tblDodajOtpremnicu.getValueAt(i, 3);
            //  BigDecimal cije = (BigDecimal) nf.parseObject((String) (tblDodajPrimku.getValueAt(i, 5)));
            //  total = total.add(cije.multiply(BigDecimal.valueOf(kol)));
            Long cije = (Long) nf.parseObject((String) tblDodajOtpremnicu.getValueAt(i, 5));
            total = total.add(BigDecimal.valueOf(cije).multiply(BigDecimal.valueOf(kol)));

        }

        p.setCijena(total);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnTrazi = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        lstRoba = new javax.swing.JList<>();
        jLabel4 = new javax.swing.JLabel();
        txtTrazi = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtKolicina = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtCijena = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDodajOtpremnicu = new javax.swing.JTable();
        jSat = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cmbPartner = new javax.swing.JComboBox<>();
        btnTrazi1 = new javax.swing.JButton();
        btnDodaj = new javax.swing.JButton();
        btnSpremi = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtBrojOtpremnice = new javax.swing.JTextField();
        dpsDatum = new com.github.lgooddatepicker.components.DatePicker();
        btnIzlaz = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();

        btnTrazi.setText("Tra??i");
        btnTrazi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTraziActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        lstRoba.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstRobaMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(lstRoba);

        jLabel4.setText("Roba");

        txtTrazi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTraziKeyPressed(evt);
            }
        });

        jLabel1.setText("Koli??ina");

        jLabel2.setText("Cijena");

        tblDodajOtpremnicu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "??ifra", "Dobavlja??", "Naziv", "Koli??ina", "M.Jed", "Cijena", "Br. Otp", "Datum izdavanja"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDodajOtpremnicu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDodajOtpremnicuMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblDodajOtpremnicu);

        jSat.setText("jLabel1");

        jLabel3.setText("Kupac");

        btnTrazi1.setText("Tra??i");
        btnTrazi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrazi1ActionPerformed(evt);
            }
        });

        btnDodaj.setText("Dodaj");
        btnDodaj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDodajActionPerformed(evt);
            }
        });

        btnSpremi.setText("Spremi");
        btnSpremi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSpremiActionPerformed(evt);
            }
        });

        jLabel5.setText("Broj Otpremnice");

        btnIzlaz.setText("Povratak");
        btnIzlaz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIzlazActionPerformed(evt);
            }
        });

        jLabel6.setText("Datum izdavanja");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTrazi1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(68, 68, 68)
                                .addComponent(btnDodaj)
                                .addGap(44, 44, 44)
                                .addComponent(btnSpremi, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(56, 56, 56)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 693, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 37, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(txtTrazi, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(41, 41, 41)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(txtKolicina, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(45, 45, 45)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCijena, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(txtBrojOtpremnice, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(52, 52, 52)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbPartner, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3)
                                    .addComponent(dpsDatum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnIzlaz, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jSat, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(jLabel1)
                                .addComponent(jLabel2)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtTrazi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtKolicina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCijena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbPartner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(13, 13, 13)
                        .addComponent(txtBrojOtpremnice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(13, 13, 13)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTrazi1)
                    .addComponent(btnDodaj)
                    .addComponent(btnSpremi)
                    .addComponent(dpsDatum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSat, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnIzlaz))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lstRobaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstRobaMouseClicked
        if (evt.getButton() == 3) {
            brisanjePolja();
        }
    }//GEN-LAST:event_lstRobaMouseClicked

    private void txtTraziKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTraziKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            ucitajRobe();

        }
    }//GEN-LAST:event_txtTraziKeyPressed

    private void tblDodajOtpremnicuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDodajOtpremnicuMouseClicked
        if (evt.getButton() == 3) {

            brisanjePolja();
        }
    }//GEN-LAST:event_tblDodajOtpremnicuMouseClicked

    private void btnTraziActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTraziActionPerformed
        ucitajRobe();
    }//GEN-LAST:event_btnTraziActionPerformed

    private void btnTrazi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrazi1ActionPerformed
        ucitajRobe();
    }//GEN-LAST:event_btnTrazi1ActionPerformed

    private void btnDodajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDodajActionPerformed
        preuzimanjeVrijednosti();
        String samobroj = "[0-9]+";
        Pattern pe = Pattern.compile(samobroj);
        Matcher ma = pe.matcher(txtCijena.getText());
        Matcher mi = pe.matcher(txtKolicina.getText());
        if (!mi.matches() || !ma.matches()) {
            JOptionPane.showMessageDialog(getRootPane(), "Unesite broj??anu vrijednost!");
            return;
        }

        if (txtCijena.getText().trim().isEmpty() || txtKolicina.getText().trim().isEmpty() || cmbPartner.getSelectedIndex() == 0 || txtBrojOtpremnice.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(getRootPane(), "Popunite sve podatke!");
            return;
        }
        if (lstRoba.getSelectedValue() == null) {
            JOptionPane.showMessageDialog(getRootPane(), "Popunite sve podatke!");
            return;
        }

        Object[] red = new Object[8];
        red[0] = lstRoba.getSelectedValue().getId();
        red[1] = lstRoba.getSelectedValue().getNaziv();
        red[2] = cmbPartner.getSelectedItem();
        red[3] = Integer.parseInt(txtKolicina.getText());
        red[4] = lstRoba.getSelectedValue().getMjernaJedinica();
        red[5] = nf.format(new BigDecimal(txtCijena.getText()));
        red[6] = txtBrojOtpremnice.getText();
        red[7] = dpsDatum.getDate();

        m.addRow(red);
        tblDodajOtpremnicu.setModel(m);
        prodaja.add(lstRoba.getSelectedValue());


    }//GEN-LAST:event_btnDodajActionPerformed

    private void btnSpremiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSpremiActionPerformed
        if (tblDodajOtpremnicu.getRowCount() == 0) {
            JOptionPane.showMessageDialog(getRootPane(), "Ako ste unijeli sve podatke, prvo morate dodati podatke u tablicu "
                    + "pritiskom na tipku 'Dodaj', zatim ih mo??ete spremiti.");

            return;
        }

        try {

            vrijednosti();
            otpremnica.create();

            for (int i = 0; i < tblDodajOtpremnicu.getModel().getRowCount(); i++) {
                Long id = Long.parseLong(tblDodajOtpremnicu.getModel().getValueAt(i, 0).toString());
                Integer kolicina = Integer.parseInt(tblDodajOtpremnicu.getModel().getValueAt(i, 3).toString());

                roba.skidanjeKolicine(id, kolicina);
            }
            JOptionPane.showMessageDialog(getRootPane(), "Kreirana otpremnica " + txtBrojOtpremnice.getText() + " !");
        } catch (ZavrsniException ex) {
            JOptionPane.showMessageDialog(getRootPane(), ex.getPoruka());
        } catch (ParseException ex) {
            Logger.getLogger(OtpremnicaProzor.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_btnSpremiActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        if (evt.getButton() == 3) {

            brisanjePolja();
        }
    }//GEN-LAST:event_formMouseClicked

    private void btnIzlazActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIzlazActionPerformed
        dispose();
    }//GEN-LAST:event_btnIzlazActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDodaj;
    private javax.swing.JButton btnIzlaz;
    private javax.swing.JButton btnSpremi;
    private javax.swing.JButton btnTrazi;
    private javax.swing.JButton btnTrazi1;
    private javax.swing.JComboBox<Partner> cmbPartner;
    private com.github.lgooddatepicker.components.DatePicker dpsDatum;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jSat;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JList<Roba> lstRoba;
    private javax.swing.JTable tblDodajOtpremnicu;
    private javax.swing.JTextField txtBrojOtpremnice;
    private javax.swing.JTextField txtCijena;
    private javax.swing.JTextField txtKolicina;
    private javax.swing.JTextField txtTrazi;
    // End of variables declaration//GEN-END:variables
}
