package zavrsni.view;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import zavrsni.model.Primka;

/**
 *
 * @author juraj
 */
public class PrikazPrimka extends JLabel implements ListCellRenderer<Primka> {

    public PrikazPrimka() {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Primka> list, Primka value, int index, boolean isSelected, boolean cellHasFocus) {
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

}
