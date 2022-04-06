package zavrsni.view;

import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import zavrsni.controller.ObradaPartner;
import zavrsni.model.Partner;
import zavrsni.util.ZavrsniException;
import zavrsni.util.ZavrsniUtil;

/**
 *
 * @author juraj
 */
public class UnosPartner extends javax.swing.JFrame {

    private DefaultTableModel m;
    private SimpleDateFormat df;
    private ObradaPartner obrada;

    public UnosPartner() {
        initComponents();

        obrada = new ObradaPartner();

        sirinaStupca();
        postavke();
        load();
    }

    private void load() {

        List<Partner> entiteti = obrada.read();
        
        Object[] red = new Object[5];

        for (int i = 0; i < entiteti.size(); i++) {
            red[0] = entiteti.get(i).getId();
            red[1] = entiteti.get(i).getNaziv();
            red[2] = entiteti.get(i).getAdresa();
            red[3] = entiteti.get(i).getOib();
            red[4] = entiteti.get(i).getEmail();
            m.addRow(red);
        }
    }

    private void postavke() {

        tblMeni.getSelectionModel().addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            @Override
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                proba(evt);
            }
        });
        m = (DefaultTableModel) tblMeni.getModel();
        setTitle(ZavrsniUtil.getNaslov("-Partner-"));

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
        var s = obrada.getEntitet();
        s.setNaziv(txtNaziv.getText());
        s.setAdresa(txtAdresa.getText());
        s.setOib(txtOIB.getText());
        s.setEmail(txtEmail.getText());

    }

    private void brisanjePolja() {
        txtNaziv.setText(null);
        txtAdresa.setText(null);
        txtOIB.setText(null);
        txtEmail.setText(null);
        obrada.setEntitet(null);
    }

    private void proba(javax.swing.event.ListSelectionEvent evt) {
        if (evt.getValueIsAdjusting() || tblMeni.getSelectedRow() < 0) {
            return;
        }
        List<Partner> entiteti = obrada.read();
        var red = tblMeni.getSelectedRow();
        obrada.setEntitet(entiteti.get(red));
        var p = obrada.getEntitet();
        txtNaziv.setText(p.getNaziv());
        txtAdresa.setText(p.getAdresa());
        txtOIB.setText(p.getOib());
        txtEmail.setText(p.getEmail());

    }

    private void sirinaStupca() {
        tblMeni.getColumnModel().getColumn(0).setPreferredWidth(5);
        tblMeni.getColumnModel().getColumn(1).setPreferredWidth(150);
        tblMeni.getColumnModel().getColumn(2).setPreferredWidth(220);
        tblMeni.getColumnModel().getColumn(3).setPreferredWidth(20);
        tblMeni.getColumnModel().getColumn(4).setPreferredWidth(60);
        tblMeni.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblMeni = new javax.swing.JTable();
        jnaziv = new javax.swing.JLabel();
        jOIb = new javax.swing.JLabel();
        jemail = new javax.swing.JLabel();
        jadresa = new javax.swing.JLabel();
        txtNaziv = new javax.swing.JTextField();
        txtAdresa = new javax.swing.JTextField();
        txtOIB = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        btnUnesi = new javax.swing.JButton();
        btnPromjena = new javax.swing.JButton();
        jIzbornik = new javax.swing.JButton();
        btnBrisanje = new javax.swing.JButton();
        jSat = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        tblMeni.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Šifra", "Naziv", "Adresa", "OIB", "Email"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMeni.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMeniMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblMeni);

        jnaziv.setText("Naziv");

        jOIb.setText("OIB");

        jemail.setText("Email");

        jadresa.setText("Adresa");

        txtNaziv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNazivKeyPressed(evt);
            }
        });

        txtAdresa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAdresaKeyPressed(evt);
            }
        });

        txtOIB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtOIBKeyPressed(evt);
            }
        });

        txtEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtEmailKeyPressed(evt);
            }
        });

        btnUnesi.setText("Unesi");
        btnUnesi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUnesiActionPerformed(evt);
            }
        });
        btnUnesi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnUnesiKeyPressed(evt);
            }
        });

        btnPromjena.setText("Promijeni");
        btnPromjena.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPromjenaActionPerformed(evt);
            }
        });

        jIzbornik.setText("Povratak");
        jIzbornik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jIzbornikActionPerformed(evt);
            }
        });

        btnBrisanje.setText("Obriši");
        btnBrisanje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrisanjeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 798, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jemail, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jIzbornik, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                                        .addComponent(btnBrisanje, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnUnesi, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnPromjena, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(txtOIB, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jOIb, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtAdresa, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jadresa, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addContainerGap())
                        .addComponent(jSat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jnaziv, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNaziv, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jnaziv, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtNaziv, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jadresa, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAdresa, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jOIb, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtOIB, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jemail, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUnesi)
                    .addComponent(btnPromjena))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jIzbornik)
                    .addComponent(btnBrisanje))
                .addGap(33, 33, 33)
                .addComponent(jSat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtNazivKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNazivKeyPressed
        if (txtNaziv.getText().trim().isEmpty()) {
            return;
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnUnesiKeyPressed(evt);
            txtAdresa.requestFocus();
        }
    }//GEN-LAST:event_txtNazivKeyPressed

    private void txtAdresaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAdresaKeyPressed
        if (txtAdresa.getText().trim().isEmpty()) {
            return;
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnUnesiKeyPressed(evt);
            txtOIB.requestFocus();
        }
    }//GEN-LAST:event_txtAdresaKeyPressed

    private void txtOIBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOIBKeyPressed
        if (txtOIB.getText().trim().isEmpty()) {
            return;
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnUnesiKeyPressed(evt);
            txtEmail.requestFocus();
        }
    }//GEN-LAST:event_txtOIBKeyPressed

    private void txtEmailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailKeyPressed
        if (txtEmail.getText().trim().isEmpty()) {
            return;
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnUnesiKeyPressed(evt);

        }
    }//GEN-LAST:event_txtEmailKeyPressed

    private void btnUnesiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUnesiActionPerformed
        try {
            obrada.setEntitet(new Partner());
            preuzmiVrijednosti();
            obrada.create();
            JOptionPane.showMessageDialog(getRootPane(), "Unos" + " " + obrada.getEntitet().getNaziv() + " " + "uspješan");
            m.setRowCount(0);
            load();
            brisanjePolja();

        } catch (ZavrsniException ex) {
            JOptionPane.showMessageDialog(getRootPane(), ex.getPoruka());
        }
    }//GEN-LAST:event_btnUnesiActionPerformed

    private void btnUnesiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnUnesiKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

            try {
                obrada.setEntitet(new Partner());
                preuzmiVrijednosti();
                obrada.create();
                JOptionPane.showMessageDialog(getRootPane(), "Unos uspješan");
                m.setRowCount(0);
                load();

            } catch (ZavrsniException ex) {
                JOptionPane.showMessageDialog(getRootPane(), ex.getPoruka());
            }
        }
    }//GEN-LAST:event_btnUnesiKeyPressed

    private void btnBrisanjeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrisanjeActionPerformed

        if (obrada.getEntitet() == null) {
            JOptionPane.showMessageDialog(getRootPane(), "Prvo odaberite partnera");
            return;
        }

        if (JOptionPane.showConfirmDialog(
                getRootPane(),
                "Sigurno obrisati \"" + obrada.getEntitet().getNaziv() + "\"?",
                "Brisanje",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION) {

            return;
        }

        try {

            obrada.delete();
            JOptionPane.showMessageDialog(getRootPane(), "Partner " + obrada.getEntitet().getNaziv() + " uspješno " + "obrisan!");
            m.setRowCount(0);
            load();
            brisanjePolja();

        } catch (ZavrsniException ex) {
            JOptionPane.showMessageDialog(getRootPane(), ex.getPoruka());
        }
    }//GEN-LAST:event_btnBrisanjeActionPerformed

    private void btnPromjenaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPromjenaActionPerformed

        if (obrada.getEntitet() == null) {
            JOptionPane.showMessageDialog(getRootPane(), "Prvo odaberite partnera");
            return;
        }

        try {

            obrada.update();
            preuzmiVrijednosti();
            JOptionPane.showMessageDialog(getRootPane(), "Partner pod brojem " + obrada.getEntitet().getId() + " promijenjena!");
            m.setRowCount(0);
            load();
            brisanjePolja();
        } catch (ZavrsniException ex) {
            JOptionPane.showMessageDialog(getRootPane(), ex.getPoruka());
        }
    }//GEN-LAST:event_btnPromjenaActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
      if(evt.getButton()==3){
            brisanjePolja();
        }
    }//GEN-LAST:event_formMouseClicked

    private void jIzbornikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jIzbornikActionPerformed
        dispose();
    }//GEN-LAST:event_jIzbornikActionPerformed

    private void tblMeniMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMeniMouseClicked
      if(evt.getButton()==3){
            brisanjePolja();
        }
    }//GEN-LAST:event_tblMeniMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBrisanje;
    private javax.swing.JButton btnPromjena;
    private javax.swing.JButton btnUnesi;
    private javax.swing.JButton jIzbornik;
    private javax.swing.JLabel jOIb;
    private javax.swing.JLabel jSat;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jadresa;
    private javax.swing.JLabel jemail;
    private javax.swing.JLabel jnaziv;
    private javax.swing.JTable tblMeni;
    private javax.swing.JTextField txtAdresa;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNaziv;
    private javax.swing.JTextField txtOIB;
    // End of variables declaration//GEN-END:variables
}
