/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zavrsni.view;

import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import zavrsni.model.Roba;

/**
 *
 * @author juraj
 */
public class PrikazRoba extends JLabel implements ListCellRenderer<Roba> {
    private String naziv;
   
     private DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
    
    public PrikazRoba(){
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Roba> list, Roba value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index,
                isSelected, cellHasFocus);
         if (isSelected) {
            setBackground(Color.BLUE);
            setForeground(Color.WHITE);
        } else {
            setBackground(Color.WHITE);
            setForeground(Color.BLACK);
          }
        
        naziv = value.getNaziv();
    
        renderer.setText(value.getNaziv()==null ? "Nepoznato" : value.getNaziv());
    //    + " " + (value.getPrimka().get().getKolicina()==null ? "Nepoznato" : value.getPrimka().get(index).getKolicina()));  
       
        
        return renderer;
    }
    
}