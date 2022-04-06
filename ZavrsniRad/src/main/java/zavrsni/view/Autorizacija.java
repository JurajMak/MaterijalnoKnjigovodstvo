package zavrsni.view;

import java.awt.Color;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import zavrsni.controller.ObradaOperater;
import zavrsni.model.Operater;
import zavrsni.util.ZavrsniUtil;

/**
 *
 * @author juraj
 */
public class Autorizacija extends javax.swing.JFrame {

    private ObradaOperater obradaOperater;

    public Autorizacija() {
        initComponents();

        obradaOperater = new ObradaOperater();
        postavke();

    }

    private void postavke() {

        txtKorisnik.setText("korisnik");
        txtLozinka.setText("1");

        setTitle(ZavrsniUtil.getNaslov("-Autorizacija-"));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtKorisnik = new javax.swing.JTextField();
        btnPrijava = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtLozinka = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        txtKorisnik.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtKorisnik.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtKorisnikFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtKorisnikFocusLost(evt);
            }
        });
        txtKorisnik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKorisnikKeyPressed(evt);
            }
        });

        btnPrijava.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnPrijava.setText("Prijava");
        btnPrijava.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrijavaActionPerformed(evt);
            }
        });

        jLabel1.setText("Korisničko ime");

        jLabel2.setText("Lozinka");

        txtLozinka.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtLozinka.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtLozinkaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtLozinkaFocusLost(evt);
            }
        });
        txtLozinka.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtLozinkaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(115, 115, 115)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(txtKorisnik, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtLozinka, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(120, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(134, 134, 134)
                .addComponent(btnPrijava, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 143, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(txtKorisnik, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(txtLozinka, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(btnPrijava, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtKorisnikFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtKorisnikFocusGained
        txtKorisnik.setBackground(Color.GRAY);
    }//GEN-LAST:event_txtKorisnikFocusGained

    private void txtKorisnikFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtKorisnikFocusLost
        txtKorisnik.setBackground(Color.WHITE);

    }//GEN-LAST:event_txtKorisnikFocusLost

    private void txtKorisnikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKorisnikKeyPressed
        if (txtKorisnik.getText().isEmpty()) {
            return;
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtLozinka.requestFocus();
        }
    }//GEN-LAST:event_txtKorisnikKeyPressed

    private void txtLozinkaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLozinkaFocusGained
        txtLozinka.setBackground(Color.GRAY);

    }//GEN-LAST:event_txtLozinkaFocusGained

    private void txtLozinkaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLozinkaFocusLost
        txtLozinka.setBackground(Color.WHITE);

    }//GEN-LAST:event_txtLozinkaFocusLost

    private void txtLozinkaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLozinkaKeyPressed
        if (txtKorisnik.getText().isEmpty()) {
            txtKorisnik.requestFocus();
            return;
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            autorizacija();
        }

    }//GEN-LAST:event_txtLozinkaKeyPressed

    private void btnPrijavaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrijavaActionPerformed
        autorizacija();
    }//GEN-LAST:event_btnPrijavaActionPerformed

    private void autorizacija() {
        if (txtKorisnik.getText().trim().isEmpty()) {
            txtKorisnik.requestFocus();
            return;
        }

        if (txtLozinka.getPassword().length == 0) {
            txtLozinka.requestFocus();
            return;
        }

        Operater operater = obradaOperater.autoriziraj(txtKorisnik.getText(), new String(txtLozinka.getPassword()));

        if (operater == null) {
            JOptionPane.showMessageDialog(getRootPane(), "Neispravno korisničko ime ili lozinka");
            return;
        }

        ZavrsniUtil.operater = operater;

        new Izbornik().setVisible(true);
        dispose();

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPrijava;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField txtKorisnik;
    private javax.swing.JPasswordField txtLozinka;
    // End of variables declaration//GEN-END:variables
}
