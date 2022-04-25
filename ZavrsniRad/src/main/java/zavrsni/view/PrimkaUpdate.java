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
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import zavrsni.controller.ObradaPrimka;
import zavrsni.controller.ObradaRoba;
import zavrsni.model.Primka;
import zavrsni.model.Roba;
import zavrsni.util.ZavrsniException;
import zavrsni.util.ZavrsniUtil;

/**
 *
 * @author juraj
 */
public class PrimkaUpdate extends javax.swing.JFrame {

    private DefaultTableModel m, k;
    private SimpleDateFormat df, fd;
    private DecimalFormat nf;
    private ObradaPrimka primka;
    private ObradaRoba roba;
    private TablePrimkaRenderer tbr;

    public PrimkaUpdate() {
        initComponents();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("hr", "HR"));
        nf = new DecimalFormat("###,###.00", symbols);
        primka = new ObradaPrimka();
        roba = new ObradaRoba();

        postavke();
        load();

        lstDodajRobu.setCellRenderer(new PrikazRoba());

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
        setTitle(ZavrsniUtil.getNaslov("- Pregled Primka -"));

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

        txtCijena.setText(p.getCijena() != null ? nf.format(p.getCijena()) : "");

        txtOtpPrim.setText(p.getOtpremnicaPrimka());

        DefaultListModel<Roba> r = new DefaultListModel<>();
        if (p.getRoba() != null) {
            Collections.sort(p.getRoba(), new RobaComparator());

            r.addAll(p.getRoba());
        }
        lstDodajRobu.setModel(r);

    }

    private void brisanjePolja() {
        txtCijena.setText(null);
        txtOtpPrim.setText(null);
        lstDodajRobu.clearSelection();

    }

    private void sirinaStupca() {
        tblPrimka.getColumnModel().getColumn(0).setPreferredWidth(20);
        tblPrimka.getColumnModel().getColumn(1).setPreferredWidth(150);
        tblPrimka.getColumnModel().getColumn(2).setPreferredWidth(150);
        tblPrimka.getColumnModel().getColumn(3).setPreferredWidth(20);
        tblPrimka.getColumnModel().getColumn(4).setPreferredWidth(20);
        tblPrimka.getColumnModel().getColumn(5).setPreferredWidth(20);

        tblPrimka.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblPrimka = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        txtCijena = new javax.swing.JTextField();
        btnIzlaz = new javax.swing.JButton();
        jSat = new javax.swing.JLabel();
        btnPromjena = new javax.swing.JButton();
        btnBrisanje = new javax.swing.JButton();
        btnPr = new javax.swing.JButton();
        txtOtpPrim = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        lstDodajRobu = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
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

        jLabel2.setText("Cijena");

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

        jLabel4.setText("Otp-Primka");

        jScrollPane3.setViewportView(lstDodajRobu);

        jLabel1.setText("Roba");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 583, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(btnIzlaz, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCijena, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtOtpPrim, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnPr, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnPromjena, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnBrisanje, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(334, 334, 334))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jSat, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel1))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCijena, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtOtpPrim, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPr)
                            .addComponent(btnPromjena)
                            .addComponent(btnBrisanje))
                        .addGap(0, 35, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3))
                .addGap(18, 18, 18)
                .addComponent(btnIzlaz)
                .addGap(4, 4, 4)
                .addComponent(jSat, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnIzlazActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIzlazActionPerformed
        dispose();
    }//GEN-LAST:event_btnIzlazActionPerformed

    private void btnPromjenaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPromjenaActionPerformed
        String samobroj = "[,.0-9]+";
        Pattern pe = Pattern.compile(samobroj);
        Matcher ma = pe.matcher(txtCijena.getText());
        Matcher mi = pe.matcher(txtOtpPrim.getText());
        if (!mi.matches() || !ma.matches()) {
            JOptionPane.showMessageDialog(getRootPane(), "Unesite brojčanu vrijednost");
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

    private void tblPrimkaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPrimkaMouseClicked

        if (evt.getButton() == 3) {
            brisanjePolja();
        }

    }//GEN-LAST:event_tblPrimkaMouseClicked

    private void btnBrisanjeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnBrisanjeKeyPressed

    }//GEN-LAST:event_btnBrisanjeKeyPressed

    private void btnPrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrActionPerformed
        m.setRowCount(0);
        load();


    }//GEN-LAST:event_btnPrActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBrisanje;
    private javax.swing.JButton btnIzlaz;
    private javax.swing.JButton btnPr;
    private javax.swing.JButton btnPromjena;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jSat;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JList<Roba> lstDodajRobu;
    private javax.swing.JTable tblPrimka;
    private javax.swing.JTextField txtCijena;
    private javax.swing.JTextField txtOtpPrim;
    // End of variables declaration//GEN-END:variables
}
