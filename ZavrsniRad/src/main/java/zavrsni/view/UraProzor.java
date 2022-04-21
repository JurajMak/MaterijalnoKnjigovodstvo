package zavrsni.view;

import com.github.lgooddatepicker.components.DatePickerSettings;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import zavrsni.controller.ObradaPartner;
import zavrsni.controller.ObradaPrimka;
import zavrsni.controller.ObradaUra;
import zavrsni.model.Partner;
import zavrsni.model.Primka;
import zavrsni.model.Ura;
import zavrsni.util.ZavrsniException;
import zavrsni.util.ZavrsniUtil;

/**
 *
 * @author juraj
 */
public class UraProzor extends javax.swing.JFrame {

    private DefaultTableModel m;
    private SimpleDateFormat df, fd;
    private DecimalFormat nf;
    private ObradaUra ura;
    private ObradaPrimka primka;
    private List<Primka> pr = new ArrayList<>();

    public UraProzor() {
        initComponents();

        ura = new ObradaUra();
        primka = new ObradaPrimka();
        postavke();
        ucitajDobavljaca();
       // ucitajPrimke();
        ucitajPr();
        load();

    }

    private void load() {

        List<Ura> entiteti = ura.read();

        Object[] red = new Object[7];

        for (int i = 0; i < entiteti.size(); i++) {
            red[0] = entiteti.get(i).getId();
            red[1] = entiteti.get(i).getPartner().getNaziv();
            red[2] = entiteti.get(i).getPartner().getOib();
            red[3] = entiteti.get(i).getBrojRacuna();
            red[4] = nf.format(entiteti.get(i).getIznos());
            red[5] = fd.format(entiteti.get(i).getDatumIzdavanja());
            red[6] = fd.format(entiteti.get(i).getDatumDospijeca());

            m.addRow(red);
        }
    }

    private void postavke() {
        tblUra.getSelectionModel().addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            @Override
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                promjenaVrijednosti(evt);
            }
        });

        DatePickerSettings dps = new DatePickerSettings(new Locale("hr", "HR"));
        DatePickerSettings dpr = new DatePickerSettings(new Locale("hr", "HR"));
        dps.setFormatForDatesCommonEra("dd.MM.yyyy");
        dps.setTranslationClear("Očisti");
        dps.setTranslationToday("Danas");
        dpr.setFormatForDatesCommonEra("dd.MM.yyyy");
        dpr.setTranslationClear("Očisti");
        dpr.setTranslationToday("Danas");
        dpsIzdavanja.setSettings(dps);
        dpsDospijeca.setSettings(dpr);
        fd = new SimpleDateFormat("dd. MMMM. yyy.", new Locale("hr", "HR"));

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("hr", "HR"));
        nf = new DecimalFormat("###,###.00", symbols);

        m = (DefaultTableModel) tblUra.getModel();

        setTitle(ZavrsniUtil.getNaslov("- Ura -"));

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

    private void ucitajDobavljaca() {
        DefaultComboBoxModel<Partner> p = new DefaultComboBoxModel<>();
        Partner partner = new Partner();
        partner.setId(Long.valueOf(0));
        partner.setNaziv("Nije odabrano");

        p.addElement(partner);
        new ObradaPartner().read().forEach(s -> {
            p.addElement(s);
        });
        cmbDobavljac.setModel(p);
    }
/*
    private void ucitajPrimke() {
        DefaultListModel<Primka> r = new DefaultListModel<>();
        List<Primka> entiteti;

        entiteti = primka.read(txtBrPr.getText());

        Collections.sort(entiteti, new PrimkaComparator());

        for (Primka ro : entiteti) {
            r.addElement(ro);
        }

        lstPrimka.setModel(r);

    }
    */
     private void ucitajPr() {
        DefaultComboBoxModel<Primka> p = new DefaultComboBoxModel<>();
        Primka prim = new Primka();
        prim.setId(Long.valueOf(0));
        prim.setOtpremnicaPrimka("Nije odabrano");

        p.addElement(prim);
        new ObradaPrimka().read().forEach(s -> {
            p.addElement(s);
        });
        cmbPrimka.setModel(p);
    }
    
    
    

    private void preuzmiVrijednosti() {

        
        var u = ura.getEntitet();

        if (dpsDospijeca.getDate() != null) {
            u.setDatumDospijeca(Date.from(dpsDospijeca.getDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        } else {
            u.setDatumDospijeca(null);
        }

        if (dpsIzdavanja.getDate() != null) {
            u.setDatumIzdavanja(Date.from(dpsIzdavanja.getDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        } else {
            u.setDatumIzdavanja(null);
        }
              
        u.setPrimka((Primka) cmbPrimka.getSelectedItem());
        u.setPartner((Partner) cmbDobavljac.getSelectedItem());
        u.setBrojRacuna(txtBrojRacuna.getText());
        
        try {
            u.setIznos(new BigDecimal(nf.parse(txtIznos.getText()).toString()));

        } catch (Exception e) {

        }

    }

    private void promjenaVrijednosti(javax.swing.event.ListSelectionEvent evt) {
        if (evt.getValueIsAdjusting() || tblUra.getSelectedRow() < 0) {
            return;
        }

        List<Ura> entiteti = ura.read();
        var ro = tblUra.getSelectedRow();

        ura.setEntitet(entiteti.get(ro));

        var p = ura.getEntitet();

        txtBrojRacuna.setText(p.getBrojRacuna());
        txtIznos.setText(p.getIznos() != null ? nf.format(p.getIznos()) : "");
        dpsIzdavanja.setDate(p.getDatumIzdavanja().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        dpsDospijeca.setDate(p.getDatumDospijeca().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

        if ((p.getPartner()) == null) {
            cmbDobavljac.setSelectedIndex(0);
        } else {
            cmbDobavljac.setSelectedItem(p.getPartner());
        }
        
          if ((p.getPrimka()) == null) {
            cmbPrimka.setSelectedIndex(0);
        } else {
            cmbPrimka.setSelectedItem(p.getPrimka());
        }

    }

    private void brisanjePolja() {
      //  txtBrPr.setText(null);
        txtBrojRacuna.setText(null);
        txtIznos.setText(null);
        
        ura.setEntitet(null);
        cmbDobavljac.setSelectedIndex(0);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        txtBrojRacuna = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtIznos = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblUra = new javax.swing.JTable();
        btnIzlaz = new javax.swing.JButton();
        jSat = new javax.swing.JLabel();
        dpsDospijeca = new com.github.lgooddatepicker.components.DatePicker();
        dpsIzdavanja = new com.github.lgooddatepicker.components.DatePicker();
        cmbDobavljac = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        btnUnos = new javax.swing.JButton();
        btnBrisanje = new javax.swing.JButton();
        btnPromjena = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cmbPrimka = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        jLabel3.setText("Broj Računa");

        jLabel5.setText("Iznos");

        tblUra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Br. Ure", "Partner", "OIB", "Broj računa", "Iznos", "Datum izdavanja", "Datum dospijeća"
            }
        ));
        tblUra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUraMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblUra);

        btnIzlaz.setText("Povratak");
        btnIzlaz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIzlazActionPerformed(evt);
            }
        });

        jSat.setText("jLabel5");

        dpsDospijeca.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);

        dpsIzdavanja.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);

        jLabel1.setText("Dobavljač");

        btnUnos.setText("Nova Ura");
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

        jLabel6.setText("Datum izdavanja");

        jLabel4.setText("Datum dospijeća");

        jLabel2.setText("Br.Primke");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnIzlaz, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(601, 601, 601)
                        .addComponent(jSat, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 850, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtBrojRacuna, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(cmbDobavljac, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1)))
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(txtIznos, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnUnos)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnBrisanje, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(40, 40, 40)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(btnPromjena, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(102, 102, 102)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel6)
                                        .addComponent(dpsDospijeca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4)
                                        .addComponent(dpsIzdavanja, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(cmbPrimka, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2)))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(dpsIzdavanja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(5, 5, 5))))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtBrojRacuna, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cmbDobavljac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cmbPrimka, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtIznos, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnUnos)
                        .addComponent(btnBrisanje)
                        .addComponent(btnPromjena))
                    .addComponent(dpsDospijeca, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jSat, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnIzlaz)
                        .addGap(38, 38, 38))))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnIzlazActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIzlazActionPerformed
        dispose();
    }//GEN-LAST:event_btnIzlazActionPerformed

    private void btnUnosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUnosActionPerformed

        try {
           
            ura.setEntitet(new Ura());
            primka.setEntitet(new Primka());
            preuzmiVrijednosti();
            ura.create();
            
            
            JOptionPane.showMessageDialog(getRootPane(), "Stvorena nova Ura pod brojem " + ura.getEntitet().getId() + " !");
            m.setRowCount(0);
            
            
            load();

            //     brisanjePolja();
        } catch (ZavrsniException ex) {
            JOptionPane.showMessageDialog(getRootPane(), ex.getPoruka());
        }
    }//GEN-LAST:event_btnUnosActionPerformed

    private void btnBrisanjeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrisanjeActionPerformed

        if (ura.getEntitet() == null) {
            JOptionPane.showMessageDialog(getRootPane(), "Prvo odaberite stavku");
            return;
        }

        if (JOptionPane.showConfirmDialog(
                getRootPane(),
                "Sigurno obrisati \"" + "Uru broj " + ura.getEntitet().getId() + "\"?",
                "Brisanje",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION) {

            return;
        }

        try {

            ura.delete();
            JOptionPane.showMessageDialog(getRootPane(), "Ura pod brojem " + ura.getEntitet().getId() + " obrisana!");
            m.setRowCount(0);
            load();
            //     brisanjePolja();
        } catch (ZavrsniException ex) {
            JOptionPane.showMessageDialog(getRootPane(), ex.getPoruka());
        }
    }//GEN-LAST:event_btnBrisanjeActionPerformed

    private void btnBrisanjeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnBrisanjeKeyPressed

    }//GEN-LAST:event_btnBrisanjeKeyPressed

    private void btnPromjenaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPromjenaActionPerformed

        if (ura.getEntitet() == null) {
            JOptionPane.showMessageDialog(getRootPane(), "Prvo odaberite stavku");
            return;
        }

        try {
           
            preuzmiVrijednosti();
            ura.update();
            
            JOptionPane.showMessageDialog(getRootPane(), "Ura pod brojem " + ura.getEntitet().getId() + " promijenjena!");
            m.setRowCount(0);
            load();

            //    brisanjePolja();
        } catch (ZavrsniException ex) {
            JOptionPane.showMessageDialog(getRootPane(), ex.getPoruka());
        }

    }//GEN-LAST:event_btnPromjenaActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        if (evt.getButton() == 3) {
            brisanjePolja();
        }
    }//GEN-LAST:event_formMouseClicked

    private void tblUraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUraMouseClicked
        if (evt.getButton() == 3) {
            brisanjePolja();
        }
    }//GEN-LAST:event_tblUraMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBrisanje;
    private javax.swing.JButton btnIzlaz;
    private javax.swing.JButton btnPromjena;
    private javax.swing.JButton btnUnos;
    private javax.swing.JComboBox<Partner> cmbDobavljac;
    private javax.swing.JComboBox<Primka> cmbPrimka;
    private com.github.lgooddatepicker.components.DatePicker dpsDospijeca;
    private com.github.lgooddatepicker.components.DatePicker dpsIzdavanja;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jSat;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tblUra;
    private javax.swing.JTextField txtBrojRacuna;
    private javax.swing.JTextField txtIznos;
    // End of variables declaration//GEN-END:variables
}
