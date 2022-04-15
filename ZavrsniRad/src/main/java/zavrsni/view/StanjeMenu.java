package zavrsni.view;

import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import zavrsni.controller.ObradaRoba;
import zavrsni.model.Roba;
import zavrsni.util.ZavrsniException;
import zavrsni.util.ZavrsniUtil;

/**
 *
 * @author juraj
 */
public class StanjeMenu extends javax.swing.JFrame {

    private DefaultTableModel m;
    private SimpleDateFormat df;
    private ObradaRoba obrada;
    private DecimalFormat nf;

    public StanjeMenu() {
        initComponents();
        
        obrada = new ObradaRoba();
     
        sirinaStupca();       
        postavke();
        load();
       

    }

    /**
     * metoda za popunjavanje tablice sa podatcima iz baze podataka
     */
    private void load() {

        List<Roba> entiteti = obrada.read();
        Object[] red = new Object[7];

        for (int i = 0; i < entiteti.size(); i++) {
            red[0] = entiteti.get(i).getId();
            red[1] = entiteti.get(i).getNaziv();
            red[2] = entiteti.get(i).getKolicina();
            red[3] = entiteti.get(i).getMjernaJedinica();
            red[4] = nf.format(entiteti.get(i).getCijena());
            red[5] = nf.format(entiteti.get(i).getCijena().multiply(BigDecimal.valueOf(1.50)));
            red[6] = nf.format(entiteti.get(i).getCijena().multiply(BigDecimal.valueOf(1.50).multiply(BigDecimal.valueOf(1.25))));
            m.addRow(red);
        }
    }

    /**
     * list selection listener implementiran u jtable
     */
    private void postavke() {
          tblMeni.getSelectionModel().addListSelectionListener((javax.swing.event.ListSelectionEvent evt) -> {
           proba(evt);
        });
        m = (DefaultTableModel) tblMeni.getModel();
        
        setTitle(ZavrsniUtil.getNaslov("- Stanje -"));
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("hr", "HR"));
        nf = new DecimalFormat("###,###.00", symbols);
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

    /**
     *
     * metoda za preuzimanje vrijednosti iz polja(jtextfield) za upisivanje kako
     * bi ih mogli pohraniti u listu/tablicu
     */
    private void preuzmiVrijednosti() {
        var s = obrada.getEntitet();
        s.setNaziv(txtNaziv.getText());
        s.setMjernaJedinica(txtMjerna.getText());
        try {
            s.setKolicina(Integer.parseInt(txtKolicina.getText()));
        } catch (Exception e) {
         
        }
        try {

            s.setCijena(new BigDecimal(nf.parse(txtCijena.getText()).toString()));
        } catch (Exception e) {
         
        }

    }

    /**
     * metoda služi za brisanje teksta u poljima(jtextfield) nakon unosa
     */
    private void brisanjePolja() {
        txtNaziv.setText(null);
        txtKolicina.setText(null);
        txtMjerna.setText(null);
        txtCijena.setText(null);
        obrada.setEntitet(null);
    }

    /**
     * metoda koja "osluškuje" kretanje po tablici i na klik odabire redove i
     * tekstualni zapis tablice prikazuje u poljima(jtextfield)
     */
    private void proba(javax.swing.event.ListSelectionEvent evt) {

        if (evt.getValueIsAdjusting() || tblMeni.getSelectedRow() < 0) {

            return;
        }
        List<Roba> entiteti = obrada.read();
        var red = tblMeni.getSelectedRow();
        obrada.setEntitet(entiteti.get(red));
        var p = obrada.getEntitet();
        txtNaziv.setText(p.getNaziv());
        txtKolicina.setText(p.getKolicina().toString());
        txtMjerna.setText(p.getMjernaJedinica());
        txtCijena.setText(p.getCijena() != null ? nf.format(p.getCijena()) : "");
    }

    /**
     * metoda za postavljanje sirine stupaca u tablici
     */
    private void sirinaStupca() {
        tblMeni.getColumnModel().getColumn(0).setPreferredWidth(20);
        tblMeni.getColumnModel().getColumn(1).setPreferredWidth(250);
        tblMeni.getColumnModel().getColumn(2).setPreferredWidth(30);
        tblMeni.getColumnModel().getColumn(3).setPreferredWidth(30);
        tblMeni.getColumnModel().getColumn(4).setPreferredWidth(30);
        tblMeni.getColumnModel().getColumn(5).setPreferredWidth(30);
        tblMeni.getColumnModel().getColumn(6).setPreferredWidth(30);
        tblMeni.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jStanje = new javax.swing.JScrollPane();
        tblMeni = new javax.swing.JTable();
        jSat = new javax.swing.JLabel();
        btnPromjena = new javax.swing.JButton();
        btnUnesi = new javax.swing.JButton();
        btnBrisanje = new javax.swing.JButton();
        jIzbornik = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtNaziv = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtKolicina = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtMjerna = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCijena = new javax.swing.JTextField();

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
                "Šifra", "Naziv", "Količina", "M.Jed.", "Nab. cijena", "VP. cijena", "MP. cijena"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMeni.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblMeni.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMeniMouseClicked(evt);
            }
        });
        jStanje.setViewportView(tblMeni);

        jSat.setText("jLabel1");

        btnPromjena.setText("Promjena");
        btnPromjena.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPromjenaActionPerformed(evt);
            }
        });

        btnUnesi.setText("Unos");
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

        btnBrisanje.setText("Brisanje");
        btnBrisanje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrisanjeActionPerformed(evt);
            }
        });

        jIzbornik.setText("Povratak");
        jIzbornik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jIzbornikActionPerformed(evt);
            }
        });

        jLabel1.setText("Naziv  Robe");

        txtNaziv.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNaziv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNazivKeyPressed(evt);
            }
        });

        jLabel2.setText("Količina");

        txtKolicina.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtKolicina.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKolicinaKeyPressed(evt);
            }
        });

        jLabel3.setText("M.Jedinica");

        txtMjerna.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtMjerna.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMjernaKeyPressed(evt);
            }
        });

        jLabel4.setText("Cijena");

        txtCijena.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtCijena.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCijenaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jStanje, javax.swing.GroupLayout.PREFERRED_SIZE, 648, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jSat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(txtNaziv, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(55, 55, 55)))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtKolicina, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMjerna, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnUnesi, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jIzbornik, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnBrisanje, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnPromjena, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(txtCijena, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNaziv, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtKolicina, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMjerna, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtCijena, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUnesi, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBrisanje, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPromjena, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jIzbornik, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSat, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jStanje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnUnesiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUnesiActionPerformed
        /**
         * metoda za unos podataka u tablicu i bazu podataka
         */

        try {
            obrada.setEntitet(new Roba());
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

    private void btnBrisanjeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrisanjeActionPerformed

        /**
         * metoda za brisanje podataka iz tablice i baze podataka
         */
        if (obrada.getEntitet() == null) {
            JOptionPane.showMessageDialog(getRootPane(), "Prvo odaberite stavku za brisanje");
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
            JOptionPane.showMessageDialog(getRootPane(), "Stavka " + obrada.getEntitet().getNaziv() + " uspješno " + "obrisana!");
           
            m.setRowCount(0);
            load();
            brisanjePolja();

        } catch (ZavrsniException ex) {
            JOptionPane.showMessageDialog(getRootPane(), ex.getPoruka());
        }


    }//GEN-LAST:event_btnBrisanjeActionPerformed

    private void jIzbornikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jIzbornikActionPerformed

        dispose();
    }//GEN-LAST:event_jIzbornikActionPerformed

    private void btnPromjenaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPromjenaActionPerformed
        /**
         * metoda za izmjenu podataka u tablici i bazi podataka
         */

        if (obrada.getEntitet() == null) {
            JOptionPane.showMessageDialog(getRootPane(), "Prvo odaberite stavku");
            return;
        }

        try {
            preuzmiVrijednosti();
            obrada.update();

            JOptionPane.showMessageDialog(getRootPane(), "Roba pod brojem " + obrada.getEntitet().getId() + " promijenjena!");
            m.setRowCount(0);
            load();
            brisanjePolja();
        } catch (ZavrsniException ex) {
            JOptionPane.showMessageDialog(getRootPane(), ex.getPoruka());
        }

    }//GEN-LAST:event_btnPromjenaActionPerformed

    private void txtMjernaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMjernaKeyPressed
        /**
         * metoda za restrikciju brojčanog unosa podataka pod mjernu jedinicu
         */

         String nemaBroja = "^[a-zA-Z]*$";
        Pattern pa = Pattern.compile(nemaBroja);
        Matcher match = pa.matcher(txtMjerna.getText());
        if (!match.matches()) {
            JOptionPane.showMessageDialog(getRootPane(), "Mjerna jedinica mora biti tekstualnog oblika!");
            JOptionPane.showMessageDialog(getRootPane(), "Nakon tipke 'OK' , na tipkovnici pritisnite  tipku 'Backspace' za nastavak unosa!");
            txtMjerna.setEditable(false);
            txtMjerna.setText(null);
        } else{
            txtMjerna.setEditable(true);
        }
          
        
  
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtCijena.requestFocus();
        }
    }//GEN-LAST:event_txtMjernaKeyPressed

    private void txtNazivKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNazivKeyPressed

        if (txtNaziv.getText().trim().isEmpty()) {
            return;
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnUnesiKeyPressed(evt);
            txtKolicina.requestFocus();
        }
    }//GEN-LAST:event_txtNazivKeyPressed

    private void btnUnesiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnUnesiKeyPressed
        /**
         * metoda koja služi kao shortcut, pritiskom tipke enter nakon svih
         * ispisanih polja spremamo podatke u bazu i tablicu,umjesto klikanja na
         * button Unos
         */

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

            try {
                obrada.setEntitet(new Roba());
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

    private void txtCijenaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCijenaKeyPressed
        if (txtCijena.getText().trim().isEmpty()) {
            return;
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

            btnUnesiKeyPressed(evt);
        }
    }//GEN-LAST:event_txtCijenaKeyPressed

    private void txtKolicinaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKolicinaKeyPressed
        if (txtKolicina.getText().trim().isEmpty()) {
            return;
        }

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnUnesiKeyPressed(evt);
            txtMjerna.requestFocus();
        }
    }//GEN-LAST:event_txtKolicinaKeyPressed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        /**
         * klikanjem desnog klika miša van tablice briše vrijednosti u poljima
         */
         if(evt.getButton()==3){
            brisanjePolja();
        }
    }//GEN-LAST:event_formMouseClicked

    private void tblMeniMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMeniMouseClicked
         /**
         * klikanjem desnog klika miša po tablici tablice briše vrijednosti u poljima
         */
      if(evt.getButton()==3){
            brisanjePolja();
        } 
            
           

        


    }//GEN-LAST:event_tblMeniMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBrisanje;
    private javax.swing.JButton btnPromjena;
    private javax.swing.JButton btnUnesi;
    private javax.swing.JButton jIzbornik;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jSat;
    private javax.swing.JScrollPane jStanje;
    private javax.swing.JTable tblMeni;
    private javax.swing.JTextField txtCijena;
    private javax.swing.JTextField txtKolicina;
    private javax.swing.JTextField txtMjerna;
    private javax.swing.JTextField txtNaziv;
    // End of variables declaration//GEN-END:variables
}
