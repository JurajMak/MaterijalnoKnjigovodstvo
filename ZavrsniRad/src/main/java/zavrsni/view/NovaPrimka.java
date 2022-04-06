
package zavrsni.view;

import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import zavrsni.controller.ObradaPartner;
import zavrsni.controller.ObradaPrimka;
import zavrsni.controller.ObradaRoba;
import zavrsni.controller.ObradaUra;
import zavrsni.model.Partner;
import zavrsni.model.Primka;
import zavrsni.model.Roba;
import zavrsni.model.Ura;
import zavrsni.util.ZavrsniException;
import zavrsni.util.ZavrsniUtil;

/**
 *
 * @author juraj
 */
public class NovaPrimka extends javax.swing.JFrame {

    private ObradaPrimka primka;
    private DefaultTableModel m;
    private ObradaRoba roba;
    private DecimalFormat nf;

    private SimpleDateFormat df;

    public NovaPrimka() {
        initComponents();
        primka = new ObradaPrimka();
        roba = new ObradaRoba();
        Roba  ro = new Roba();
        lstRoba.setCellRenderer(new PrikazRoba());
        
        postavke();
        
        ucitajRobe();
        ucitajDobavljaca();
        ucitajUru();
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
  

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("hr", "HR"));
        nf = new DecimalFormat("###,###.00", symbols);

        setTitle(ZavrsniUtil.getNaslov("-Nova Primka-"));

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
        primka.setEntitet(null);
        cmbDobavljac.setSelectedIndex(0);
         m.setRowCount(0);

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
    
    

    private void test() {
        m = new DefaultTableModel();
        primka = new ObradaPrimka();
        String[] s = {"Šifra", "Naziv","Dobavljač", "Količina", "M.Jed", "Cijena","Iznos","Br.Rač"};
        for (String i : s) {
            m.addColumn(i);
        }
        
       
        tblDodajPrimku.setModel(m);
        
    }
    
     private void preuzmiVrijednosti() {

        var p = primka.getEntitet();
        p.setRoba((Roba) lstRoba.getSelectedValue());
        p.setUra((Ura) cmbUra.getSelectedItem());
        

        try {
           
          p.setCijena(new BigDecimal(nf.parse(txtCijena.getText()).toString()));
        } catch (Exception e) {

        }

        try {
            p.setKolicina(Integer.parseInt(txtKolicina.getText()));
        } catch (Exception e) {

        }
        
    }
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblPrimka = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        lstRoba = new javax.swing.JList<>();
        jLabel4 = new javax.swing.JLabel();
        txtTrazi = new javax.swing.JTextField();
        btnTrazi = new javax.swing.JButton();
        btnDodaj = new javax.swing.JButton();
        btnSpremi = new javax.swing.JButton();
        txtCijena = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtKolicina = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cmbDobavljac = new javax.swing.JComboBox<>();
        jSat = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDodajPrimku = new javax.swing.JTable();
        cmbUra = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        btnPovratak = new javax.swing.JButton();

        tblPrimka.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Šifra", "Dobavljač", "Naziv", "Količina", "M.Jed", "Cijena"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblPrimka);

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

        btnTrazi.setText("Traži");
        btnTrazi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTraziActionPerformed(evt);
            }
        });

        btnDodaj.setText("Unesi");
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

        jLabel1.setText("Količina");

        jLabel2.setText("Cijena");

        jLabel3.setText("Dobavljač");

        jSat.setText("jLabel1");

        tblDodajPrimku.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Šifra", "Dobavljač", "Naziv", "Količina", "M.Jed", "Cijena", "Br.Ura"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDodajPrimku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDodajPrimkuMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblDodajPrimku);

        jLabel6.setText("Broj Računa");

        btnPovratak.setText("Povratak");
        btnPovratak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPovratakActionPerformed(evt);
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
                            .addComponent(txtTrazi, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(btnTrazi, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(64, 64, 64)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtKolicina, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(txtCijena, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(23, 23, 23))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnDodaj, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cmbDobavljac, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(45, 45, 45)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6)
                                            .addComponent(cmbUra, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 235, Short.MAX_VALUE)
                                        .addComponent(btnSpremi, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(41, 41, 41))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 757, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(28, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(btnPovratak)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSat, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTrazi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtKolicina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCijena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbDobavljac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbUra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTrazi)
                    .addComponent(btnDodaj)
                    .addComponent(btnSpremi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnPovratak)
                    .addComponent(jSat, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnTraziActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTraziActionPerformed
        ucitajRobe();
    }//GEN-LAST:event_btnTraziActionPerformed

    private void btnDodajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDodajActionPerformed
        
      if(txtCijena.getText().trim().isEmpty() || txtKolicina.getText().trim().isEmpty() || cmbDobavljac.getItemCount()==0 ||cmbUra.getItemCount()==0){
          JOptionPane.showMessageDialog(getRootPane(), "Popunite sve podatke");
          return;
      }
         
      
       if (lstRoba.getSelectedValue() == null) {
           JOptionPane.showMessageDialog(getRootPane(), "Popunite sve podatke");
            return;
        }
            
        
    
        Object[] red = new Object[8];
        red[0] = lstRoba.getSelectedValue().getId();
        red[1] = lstRoba.getSelectedValue().getNaziv();
        red[2] = cmbDobavljac.getSelectedItem();        
        red[3] = Integer.parseInt(txtKolicina.getText());
        red[4] = lstRoba.getSelectedValue().getMjernaJedinica();       
        red[5] = nf.format(new BigDecimal(txtCijena.getText()));
        
        red[6] = nf.format(new BigDecimal((txtCijena.getText())).multiply(BigDecimal.valueOf(Integer.parseInt(txtKolicina.getText()))));
        red[7] = cmbUra.getSelectedItem();
        
        m.addRow(red);
        tblDodajPrimku.setModel(m);
         
        
        
        
    }//GEN-LAST:event_btnDodajActionPerformed

    private void btnSpremiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSpremiActionPerformed
        String samobroj = "[,0-9]+";
        Pattern pe = Pattern.compile(samobroj);
        Matcher ma = pe.matcher(txtCijena.getText());
        Matcher mi = pe.matcher(txtKolicina.getText());
        if (!ma.matches() && !mi.matches()) {
            JOptionPane.showMessageDialog(getRootPane(), "Unesite brojčanu vrijednost");
        } 
        
          
    /*         if (tblDodajPrimku.getRowCount() < 1) {
            JOptionPane.showMessageDialog(getRootPane(), "Odaberite stavku");
            return;
       
          
        }*/
        
            try {
    
            primka.setEntitet(new Primka());
            preuzmiVrijednosti();
           
            primka.create();
            JOptionPane.showMessageDialog(getRootPane(), "Stvorena nova primka pod brojem " + primka.getEntitet().getId() + " !");
               for (int i = 0; i < tblDodajPrimku.getModel().getRowCount(); i++) {
            Long id = Long.parseLong(tblDodajPrimku.getModel().getValueAt(i, 0).toString());
             // Roba r = (Roba)(tblDodajPrimku.getModel().getValueAt(i, 2));
            Integer kolicina = Integer.parseInt(tblDodajPrimku.getModel().getValueAt(i, 3).toString());
     //    BigDecimal cijena = new BigDecimal(tblDodajPrimku.getModel().getValueAt(i, 5));
      //      Ura u = (Ura)(tblDodajPrimku.getModel().getValueAt(i, 6));
        
           JOptionPane.showMessageDialog(getRootPane(), "Roba " + lstRoba.getSelectedValue() + " dodana na stanje!");
          
            roba.dodavanjeKolicine(id,kolicina);
      //      primka.dodavanje(id, kolicina, cijena, r, u);
        }

          
        } catch (ZavrsniException ex) {
            JOptionPane.showMessageDialog(getRootPane(), ex.getPoruka());
        } 
        
        
        
    }//GEN-LAST:event_btnSpremiActionPerformed

    private void txtTraziKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTraziKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            ucitajRobe();

        }
    }//GEN-LAST:event_txtTraziKeyPressed

    private void lstRobaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstRobaMouseClicked
        if(evt.getButton()==3){
            brisanjePolja();
        }
    }//GEN-LAST:event_lstRobaMouseClicked

    private void tblDodajPrimkuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDodajPrimkuMouseClicked
      if(evt.getButton()==3){
            brisanjePolja();
        }
    }//GEN-LAST:event_tblDodajPrimkuMouseClicked

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
         if(evt.getButton()==3){
            brisanjePolja();
        }
    }//GEN-LAST:event_formMouseClicked

    private void btnPovratakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPovratakActionPerformed
        dispose();
    }//GEN-LAST:event_btnPovratakActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDodaj;
    private javax.swing.JButton btnPovratak;
    private javax.swing.JButton btnSpremi;
    private javax.swing.JButton btnTrazi;
    private javax.swing.JComboBox<Partner> cmbDobavljac;
    private javax.swing.JComboBox<Ura> cmbUra;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jSat;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JList<Roba> lstRoba;
    private javax.swing.JTable tblDodajPrimku;
    private javax.swing.JTable tblPrimka;
    private javax.swing.JTextField txtCijena;
    private javax.swing.JTextField txtKolicina;
    private javax.swing.JTextField txtTrazi;
    // End of variables declaration//GEN-END:variables
}
