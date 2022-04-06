
package zavrsni.view;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
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
public class PrimkaTest extends javax.swing.JFrame {

    private DefaultTableModel m, r;
    private SimpleDateFormat df, fd;
    private DecimalFormat nf;
    private ObradaPrimka primka;
    private ObradaRoba roba;
    
    public PrimkaTest() {
        initComponents();
    
        primka = new ObradaPrimka();
        roba = new ObradaRoba();
        
        
       
        sirinaStupca();
        postavke();
        load();
        ucitajDobavljaca();
    //    ucitajRobe();
        ucitajUru();
        
    }

   private void load() {

        List<Primka> entiteti = primka.read();

        Object[] red = new Object[7];

        for (int i = 0; i < entiteti.size(); i++) {
            red[0] = entiteti.get(i).getId();
            red[1] = entiteti.get(i).getUra().getPartner().getNaziv();
          
            red[2] = entiteti.get(i).getKolicina();
            red[3] = nf.format(entiteti.get(i).getCijena());
            red[4] = nf.format(entiteti.get(i).getCijena().multiply(BigDecimal.valueOf(1.75)));
            red[5] = nf.format(entiteti.get(i).getCijena().multiply(BigDecimal.valueOf(entiteti.get(i).getKolicina()).multiply(BigDecimal.valueOf(1.25))));
            red[6] = fd.format(entiteti.get(i).getUra().getDatumDospijeca());

            m.addRow(red);
        }
    }
    
     private void postavke() {
        tblPrimka.getSelectionModel().addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            @Override
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                proba(evt);
            }
        });
           
//         lstDodajRobu.setCellRenderer(new PrikazRoba());
         
        
        fd = new SimpleDateFormat("dd. MMMM. yyy.", new Locale("hr", "HR"));

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("hr", "HR"));
        nf = new DecimalFormat("###,###.00", symbols);

        m = (DefaultTableModel) tblPrimka.getModel();
       
        setTitle(ZavrsniUtil.getNaslov("-Primka-"));

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
        
        p.setUra((Ura) cmbUra.getSelectedItem());
        
        
        try {
            p.getUra().setPartner((Partner) cmbDobavljac.getSelectedItem());
        } catch (Exception e) {
        }
        
        
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
        if (evt.getValueIsAdjusting() || tblPrimka.getSelectedRow() < 0) {
            return;
        }

        List<Primka> entiteti = primka.read();
        var ro = tblPrimka.getSelectedRow();
        primka.setEntitet(entiteti.get(ro));
        var p = primka.getEntitet();
        txtCijena.setText(p.getCijena().toString());
        txtKolicina.setText(p.getKolicina().toString());

        if ((p.getUra().getPartner())== null) {
            cmbDobavljac.setSelectedIndex(0);
        } else {
            cmbDobavljac.setSelectedItem(p.getUra().getPartner());
        } 

        if (p.getUra() == null) {
            cmbUra.setSelectedIndex(0);
        } else {
            cmbUra.setSelectedItem(p.getUra());
        }
 
       DefaultListModel<Roba> m = new DefaultListModel<>();
        if (p.getRoba()!= null) {
            
            
  //          m.addAll(p.getRoba());
        }
        lstDodajRobu.setModel(m);
      
       
       
    }

    private void brisanjePolja() {
      DefaultListModel<Roba> remove = new DefaultListModel<>();
         remove.removeAllElements();
        txtKolicina.setText(null);
        primka.setEntitet(null);
       cmbUra.setSelectedIndex(0);
       cmbDobavljac.setSelectedIndex(0);
        lstDodajRobu.setModel(remove);
    }

    private void sirinaStupca() {
        tblPrimka.getColumnModel().getColumn(0).setPreferredWidth(20);
        tblPrimka.getColumnModel().getColumn(1).setPreferredWidth(150);
        tblPrimka.getColumnModel().getColumn(2).setPreferredWidth(150);
        tblPrimka.getColumnModel().getColumn(3).setPreferredWidth(20);
        tblPrimka.getColumnModel().getColumn(4).setPreferredWidth(20);
        tblPrimka.getColumnModel().getColumn(5).setPreferredWidth(20);
        tblPrimka.getColumnModel().getColumn(6).setPreferredWidth(20);
        
        tblPrimka.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

    }

   private void ucitajRobe() {
    DefaultListModel<Roba> m = new DefaultListModel<>();
        List<Roba> entiteti;
  /*      
        
        entiteti = roba.read(txtTrazi.getText());
        
        
        Collections.sort(entiteti, new RobaComparator());
        
        for (Roba s : entiteti) {
            m.addElement(s);
        }
        
        lstRoba.setModel(m);
       
     */
        
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
    
    private void ucitajDobavljaca(){
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
   






    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        txtKolicina = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtCijena = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPrimka = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        cmbDobavljac = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cmbUra = new javax.swing.JComboBox<>();
        btnPromjena = new javax.swing.JButton();
        btnBrisanje = new javax.swing.JButton();
        btnUnos = new javax.swing.JButton();
        btnIzlaz = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        lstDodajRobu = new javax.swing.JList<>();
        jLabel7 = new javax.swing.JLabel();
        jSat = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btnTrazi = new javax.swing.JButton();
        txtTrazi = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        lstRoba = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel3.setText("Količina");

        jLabel2.setText("Cijena");

        tblPrimka.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Br. Primke", "Dobavljač", "Količina", "Nab. cijena", "Cijena", "Iznos", "Datum dospjeća"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
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

        jLabel1.setText("Dobavljač");

        jLabel5.setText("Broj Računa");

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

        jScrollPane6.setViewportView(lstDodajRobu);

        jLabel7.setText("Primka");

        jSat.setText("jLabel5");

        jButton1.setText("Dodaj");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Obriši");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btnTrazi.setText("Traži");
        btnTrazi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTraziActionPerformed(evt);
            }
        });

        jLabel4.setText("Roba");

        jScrollPane4.setViewportView(lstRoba);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTrazi, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnTrazi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jSat, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton1)
                                    .addComponent(jButton2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(40, 40, 40))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtKolicina, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCijena, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(33, 33, 33)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addGap(18, 18, 18)
                                                .addComponent(cmbDobavljac, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnPromjena, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel5)
                                                .addGap(26, 26, 26)
                                                .addComponent(cmbUra, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnUnos, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 923, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnIzlaz, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnBrisanje, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(btnPromjena, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbDobavljac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKolicina, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBrisanje))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUnos)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(cmbUra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnIzlaz)
                    .addComponent(txtCijena, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSat, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(txtTrazi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnTrazi))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(18, 18, 18)
                                .addComponent(jButton2)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblPrimkaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPrimkaMouseClicked

        if (evt.getButton() == 3) {
            brisanjePolja();
        }
    }//GEN-LAST:event_tblPrimkaMouseClicked

    private void btnPromjenaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPromjenaActionPerformed
        List<Primka> entiteti = primka.read();
        List<Roba> ent = roba.read();
        var red = tblPrimka.getSelectedRow();

        primka.setEntitet(entiteti.get(red));
        roba.setEntitet(ent.get(red));
        var p = primka.getEntitet();
        var ro = roba.getEntitet();

        if (primka.getEntitet() == null) {
            JOptionPane.showMessageDialog(getRootPane(), "Prvo odaberite stavku");
            return;
        }

        try {
            try {
                ro.setCijena(new BigDecimal(nf.parse(txtCijena.getText()).toString()));
            } catch (Exception e) {
            }

            ro.setKolicina(ro.getKolicina() - p.getKolicina() + Integer.parseInt(txtKolicina.getText()));

            preuzmiVrijednosti();

            primka.update();
            //     roba.update();
            JOptionPane.showMessageDialog(getRootPane(), "Primka pod brojem " + primka.getEntitet().getId() + " promijenjena!");
            m.setRowCount(0);
            load();
            ucitajRobe();
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
            ucitajRobe();
            brisanjePolja();
        } catch (ZavrsniException ex) {
            JOptionPane.showMessageDialog(getRootPane(), ex.getPoruka());
        }
    }//GEN-LAST:event_btnBrisanjeActionPerformed

    private void btnBrisanjeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnBrisanjeKeyPressed

    }//GEN-LAST:event_btnBrisanjeKeyPressed

    private void btnUnosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUnosActionPerformed

        var p = primka.getEntitet();
        var ro = roba.getEntitet();

        try {
            try {
                ro.setCijena(new BigDecimal(nf.parse(txtCijena.getText()).toString()));
            } catch (Exception e) {
            }
            try {
                ro.setKolicina(ro.getKolicina() + Integer.parseInt(txtKolicina.getText()));
            } catch (Exception e) {
            }

            primka.setEntitet(new Primka());
            preuzmiVrijednosti();

            primka.create();

            JOptionPane.showMessageDialog(getRootPane(), "Stvorena nova primka pod brojem " + primka.getEntitet().getId() + " !");
            m.setRowCount(0);

            load();
            ucitajRobe();
            brisanjePolja();
        } catch (ZavrsniException ex) {
            JOptionPane.showMessageDialog(getRootPane(), ex.getPoruka());
        }

    }//GEN-LAST:event_btnUnosActionPerformed

    private void btnIzlazActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIzlazActionPerformed
        dispose();
    }//GEN-LAST:event_btnIzlazActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        DefaultListModel<Roba> r;

 /*       if(primka.getEntitet()!=null){
            r = (DefaultListModel<Roba>)lstDodajRobu.getModel();
        }else{
            primka.setEntitet(new Primka());
            primka.getEntitet().setRoba(new ArrayList<>());
            preuzmiVrijednosti();
            r = new DefaultListModel<>();
            lstDodajRobu.setModel(r);
        }
        if(primka.getEntitet().getRoba()==null){
            primka.getEntitet().setRoba(new ArrayList<>());
        }
        for(Roba p : lstRoba.getSelectedValuesList()){
            if(!nadjiRobu(r,p)){
                primka.getEntitet().getRoba().add(p);
                r.addElement(p);
            }
        }
        lstDodajRobu.repaint(); */

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        DefaultListModel<Roba> m = (DefaultListModel<Roba>)lstDodajRobu.getModel();
  /*      for(Roba r : lstDodajRobu.getSelectedValuesList()){
            m.removeElement(r);
            for(Roba mp : primka.getEntitet().getRoba()){
                if(mp.getId().equals(r.getId())){
                    primka.getEntitet().getRoba().remove(mp);
                    break;
                }
            }
        }
        //obrada.getEntitet().getPolaznici().removeAll(lstPolazniciNaGrupi.getSelectedValuesList());

        lstDodajRobu.repaint(); */
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnTraziActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTraziActionPerformed
        ucitajRobe();
    }//GEN-LAST:event_btnTraziActionPerformed

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBrisanje;
    private javax.swing.JButton btnIzlaz;
    private javax.swing.JButton btnPromjena;
    private javax.swing.JButton btnTrazi;
    private javax.swing.JButton btnUnos;
    private javax.swing.JComboBox<Partner> cmbDobavljac;
    private javax.swing.JComboBox<Ura> cmbUra;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jSat;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JList<Roba> lstDodajRobu;
    private javax.swing.JList<Roba> lstRoba;
    private javax.swing.JTable tblPrimka;
    private javax.swing.JTextField txtCijena;
    private javax.swing.JTextField txtKolicina;
    private javax.swing.JTextField txtTrazi;
    // End of variables declaration//GEN-END:variables
}
