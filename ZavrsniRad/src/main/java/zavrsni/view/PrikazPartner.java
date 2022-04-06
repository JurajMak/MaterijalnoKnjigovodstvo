/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zavrsni.view;

/**
 *
 * @author juraj
 */
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import zavrsni.model.Partner;


/**
 *
 * @author juraj
 */
public class PrikazPartner extends JLabel implements ListCellRenderer<Partner> {

    public PrikazPartner(){
    setOpaque(true);
}
    
    @Override
    public Component getListCellRendererComponent(JList<? extends Partner> list, Partner value, int index, boolean isSelected, boolean cellHasFocus) {
           if (isSelected) {
            setBackground(Color.BLUE);
            setForeground(Color.WHITE);
        } else {
            setBackground(Color.WHITE);
            setForeground(Color.BLACK);
        }
           
           setText(value.getNaziv()==null ? " Nepoznato " : value.getNaziv());
           
        
         
           
        return this;
    }
    
}