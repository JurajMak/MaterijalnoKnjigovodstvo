/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zavrsni.view;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;
import zavrsni.model.Primka;

/**
 *
 * @author juraj
 */
public class TablePrimkaRenderer extends JTextArea implements TableCellRenderer {

    public TablePrimkaRenderer() {
        setOpaque(true);
    }

    public Component getTableCellRendererComponent(JTable table, Primka value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            setBackground(Color.BLUE);
            setForeground(Color.WHITE);
        } else {
            setBackground(Color.WHITE);
            setForeground(Color.BLACK);
        }

        setText(value.getId() == null ? " Nepoznato " : value.getId()
                + " " + (value.getCijena() == null ? " Nepoznato " : value.getCijena())
                + " " + (value.getKolicina() == null ? " Nepoznato " : value.getKolicina())
                + " " + (value.getRoba() == null ? " Nepoznato " : value.getRoba())
                + " " + (value.getUra() == null ? " Nepoznato " : value.getUra()));
        return this;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
