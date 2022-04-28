package zavrsni.view;

import com.github.lgooddatepicker.components.DatePickerSettings;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import zavrsni.controller.ObradaIra;
import zavrsni.controller.ObradaPartner;
import zavrsni.model.Ira;
import zavrsni.model.Partner;
import zavrsni.util.ZavrsniException;
import zavrsni.util.ZavrsniUtil;

/**
 *
 * @author juraj
 */
public class IraProzor extends javax.swing.JFrame {

    private DefaultTableModel m;
    private SimpleDateFormat df, fd;
    private DecimalFormat nf;
    private ObradaIra ira;
    

    public IraProzor() {
        initComponents();
        ira = new ObradaIra();    
        postavke();
        ucitajKupca();       
        load();
    }

    private void load() {

        List<Ira> entiteti = ira.read();

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
        tblIra.getSelectionModel().addListSelectionListener(new javax.swing.event.ListSelectionListener() {
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

        m = (DefaultTableModel) tblIra.getModel();

        setTitle(ZavrsniUtil.getNaslov("- Ira -"));

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

    private void ucitajKupca() {
        DefaultComboBoxModel<Partner> p = new DefaultComboBoxModel<>();
        Partner partner = new Partner();
        partner.setId(Long.valueOf(0));
        partner.setNaziv("Nije odabrano");

        p.addElement(partner);
        new ObradaPartner().read().forEach(s -> {
            p.addElement(s);
        });
        cmbKupac.setModel(p);
    }

    

    private void preuzmiVrijednosti() {

        var i = ira.getEntitet();

        if (dpsDospijeca.getDate() != null) {
            i.setDatumDospijeca(Date.from(dpsDospijeca.getDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        } else {
            i.setDatumDospijeca(null);
        }

        if (dpsIzdavanja.getDate() != null) {
            i.setDatumIzdavanja(Date.from(dpsIzdavanja.getDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        } else {
            i.setDatumIzdavanja(null);
        }

       
        i.setPartner((Partner) cmbKupac.getSelectedItem());
        i.setBrojRacuna(txtBrojRacuna.getText());

        try {
            i.setIznos(new BigDecimal(nf.parse(txtIznos.getText()).toString()));

        } catch (Exception e) {

        }

    }

    private void promjenaVrijednosti(javax.swing.event.ListSelectionEvent evt) {
        if (evt.getValueIsAdjusting() || tblIra.getSelectedRow() < 0) {
            return;
        }

        List<Ira> entiteti = ira.read();
        var ro = tblIra.getSelectedRow();
        ira.setEntitet(entiteti.get(ro));
        var p = ira.getEntitet();

        txtBrojRacuna.setText(p.getBrojRacuna());
        txtIznos.setText(p.getIznos() != null ? nf.format(p.getIznos()) : "");
        dpsIzdavanja.setDate(p.getDatumIzdavanja().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        dpsDospijeca.setDate(p.getDatumDospijeca().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

        if ((p.getPartner()) == null) {
            cmbKupac.setSelectedIndex(0);
        } else {
            cmbKupac.setSelectedItem(p.getPartner());
        }
     
       

    }

    private void brisanjePolja() {
        txtBrojRacuna.setText(null);
        txtIznos.setText(null);
        ira.setEntitet(null);
        cmbKupac.setSelectedIndex(0);
       
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        txtBrojRacuna = new javax.swing.JTextField();
        txtIznos = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        cmbKupac = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        dpsIzdavanja = new com.github.lgooddatepicker.components.DatePicker();
        jLabel4 = new javax.swing.JLabel();
        dpsDospijeca = new com.github.lgooddatepicker.components.DatePicker();
        btnPromjena = new javax.swing.JButton();
        btnBrisanje = new javax.swing.JButton();
        btnUnos = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblIra = new javax.swing.JTable();
        btnIzlaz = new javax.swing.JButton();
        jSat = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel3.setText("Broj Računa");

        jLabel5.setText("Iznos");

        jLabel1.setText("Kupac");

        jLabel6.setText("Datum izdavanja");

        dpsIzdavanja.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);

        jLabel4.setText("Datum dospijeća");

        dpsDospijeca.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);

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

        btnUnos.setText("Nova Ira");
        btnUnos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUnosActionPerformed(evt);
            }
        });

        tblIra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Br. Ire", "Kupac", "OIB", "Broj računa", "Iznos", "Datum izdavanja", "Datum dospijeća"
            }
        ));
        tblIra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblIraMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblIra);

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
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(txtIznos, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnUnos)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnBrisanje, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnPromjena, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtBrojRacuna, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(62, 62, 62)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1)
                                        .addComponent(cmbKupac, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGap(124, 124, 124)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel6)
                                .addComponent(dpsDospijeca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4)
                                .addComponent(dpsIzdavanja, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
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
                    .addComponent(jLabel6))
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
                        .addComponent(cmbKupac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtIznos, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnUnos)
                        .addComponent(btnBrisanje)
                        .addComponent(btnPromjena))
                    .addComponent(dpsDospijeca, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
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

    private void btnPromjenaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPromjenaActionPerformed
        String samobroj = "[/0-9]+";
        Pattern pe = Pattern.compile(samobroj);

        Matcher mi = pe.matcher(txtBrojRacuna.getText());
        if (!mi.matches()) {
            JOptionPane.showMessageDialog(getRootPane(), "Obavezan broj računa u 00/0000 formatu!");
            return;
        } 

        if (ira.getEntitet() == null) {
            JOptionPane.showMessageDialog(getRootPane(), "Prvo odaberite stavku!");
            return;
        }
    /*    
        if(cmbKupac.getSelectedIndex()==0){
            JOptionPane.showMessageDialog(getRootPane(), "Obavezan unos Kupca! ");
        
        } */

        try {

            preuzmiVrijednosti();
            ira.update();

            JOptionPane.showMessageDialog(getRootPane(), "Ira pod brojem " + ira.getEntitet().getId() + " promijenjena!");
            m.setRowCount(0);
            load();

            brisanjePolja();
        } catch (ZavrsniException ex) {
            JOptionPane.showMessageDialog(getRootPane(), ex.getPoruka());
        }
    }//GEN-LAST:event_btnPromjenaActionPerformed

    private void btnBrisanjeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrisanjeActionPerformed

        if (ira.getEntitet() == null) {
            JOptionPane.showMessageDialog(getRootPane(), "Prvo odaberite stavku!");
            return;
        }

        if (JOptionPane.showConfirmDialog(
                getRootPane(),
                "Sigurno obrisati \"" + "Iru broj " + ira.getEntitet().getId() + "\"?",
                "Brisanje",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION) {

            return;
        }

        try {

            ira.delete();
            JOptionPane.showMessageDialog(getRootPane(), "Ira pod brojem " + ira.getEntitet().getId() + " obrisana!");
            m.setRowCount(0);
            load();
            brisanjePolja();
        } catch (ZavrsniException ex) {
            JOptionPane.showMessageDialog(getRootPane(), ex.getPoruka());
        }
    }//GEN-LAST:event_btnBrisanjeActionPerformed

    private void btnBrisanjeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnBrisanjeKeyPressed

    }//GEN-LAST:event_btnBrisanjeKeyPressed

    private void btnUnosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUnosActionPerformed
        String samobroj = "[/0-9]+";
        Pattern pe = Pattern.compile(samobroj);

        Matcher mi = pe.matcher(txtBrojRacuna.getText());
        if (!mi.matches()) {
            JOptionPane.showMessageDialog(getRootPane(), "Obavezan broj računa u 00/0000 formatu!");
            return;
        }
     /*   if(cmbKupac.getSelectedIndex()==0){
            JOptionPane.showMessageDialog(getRootPane(), "Obavezan unos Kupca! ");
        
        } */

        try {

            ira.setEntitet(new Ira());
            preuzmiVrijednosti();
            ira.create();

            JOptionPane.showMessageDialog(getRootPane(), "Stvorena nova Ira pod brojem " + ira.getEntitet().getId() + " !");
            m.setRowCount(0);

            load();

            brisanjePolja();
        } catch (ZavrsniException ex) {
            JOptionPane.showMessageDialog(getRootPane(), ex.getPoruka());
        }
    }//GEN-LAST:event_btnUnosActionPerformed

    private void tblIraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblIraMouseClicked
        if (evt.getButton() == 3) {
            brisanjePolja();
        }
    }//GEN-LAST:event_tblIraMouseClicked

    private void btnIzlazActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIzlazActionPerformed
        dispose();
    }//GEN-LAST:event_btnIzlazActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBrisanje;
    private javax.swing.JButton btnIzlaz;
    private javax.swing.JButton btnPromjena;
    private javax.swing.JButton btnUnos;
    private javax.swing.JComboBox<Partner> cmbKupac;
    private com.github.lgooddatepicker.components.DatePicker dpsDospijeca;
    private com.github.lgooddatepicker.components.DatePicker dpsIzdavanja;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jSat;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblIra;
    private javax.swing.JTextField txtBrojRacuna;
    private javax.swing.JTextField txtIznos;
    // End of variables declaration//GEN-END:variables
}
