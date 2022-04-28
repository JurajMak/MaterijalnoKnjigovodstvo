package zavrsni.view;

import javax.swing.JOptionPane;
import org.hibernate.Session;
import zavrsni.controller.ObradaOperater;
import zavrsni.util.HibernateUtil;
import zavrsni.util.PocetniInsert;

/**
 *
 * @author juraj
 */
public class SplashScreen extends javax.swing.JFrame {

    private int i = 0;
    private boolean hibernateGotov;

    public SplashScreen() {
        initComponents();
        i = 0;
        hibernateGotov = false;
        Ucitanje ucitanje = new Ucitanje();
        ucitanje.start();

        TijekUcitanja tijekUcitanja = new TijekUcitanja();
        tijekUcitanja.start();
    }

    private class TijekUcitanja extends Thread {

        @Override
        public void run() {
            if (hibernateGotov) {
                return;
            }
            try {
                pbUcitanje.setValue(++i);
                Thread.sleep(1000);
                run();
            } catch (InterruptedException ex) {

            }
        }

    }

    private class Ucitanje extends Thread {

        @Override
        public void run() {
            Session s = HibernateUtil.getSession();
            if (s.getMetamodel().getEntities().size() > 0) {

                if (new ObradaOperater().read().isEmpty()) {
                    PocetniInsert.sve();
                }
                hibernateGotov = true;
                for (int t = i; t < 100; t++) {
                    try {
                        pbUcitanje.setValue(++i);
                        Thread.sleep(3);
                    } catch (InterruptedException ex) {

                    }
                }

                new Autorizacija().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(getRootPane(), "Problem s povezivanje na bazu");
            }
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        pbUcitanje = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/zavrsni.jpg"))); // NOI18N
        jLabel1.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1206, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pbUcitanje, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 775, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pbUcitanje, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JProgressBar pbUcitanje;
    // End of variables declaration//GEN-END:variables
}
