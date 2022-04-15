/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zavrsni.view;

import java.util.List;
import javax.swing.event.EventListenerList;
import javax.swing.table.AbstractTableModel;
import zavrsni.model.Partner;
import zavrsni.model.Primka;
import zavrsni.model.Roba;
import zavrsni.model.Ura;

/**
 *
 * @author juraj
 */
public class TablicaPrimkaModel extends AbstractTableModel {

    //  private List<Roba> robe;
    //  private List<Partner> partneri;
    private List<Primka> primke;
    //   private List<Ura> ure;

    public TablicaPrimkaModel(List<Primka> primke) {

        this.primke = primke;

    }

    @Override
    public int getRowCount() {
        return (primke == null ? 0 : primke.size());

    }

    public List<Primka> getPrimke() {
        return primke;
    }

    public void setPrimke(List<Primka> primke) {
        this.primke = primke;
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Primka pr = primke.get(rowIndex);

        Object value = "??";
        switch (columnIndex) {
            case 0 ->
                value = pr.getOtpremnicaPrimka();
            //    case 1 -> value = pr.getRoba().get(0).getNaziv();
            case 2 ->
                value = pr.getKolicina();
            case 3 ->
                value = pr.getCijena();
        }
        return value;
    }

    public EventListenerList getListenerList() {
        return listenerList;
    }

    public void setListenerList(EventListenerList listenerList) {
        this.listenerList = listenerList;
    }

    Roba getRobeAt(int selectedRow) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<Primka> getPrimka() {
        return primke;
    }

    public void setPrimka(List<Primka> primka) {
        this.primke = primka;
    }

    public Primka getPrimkaAt(int selectedRow) {
        return primke.get(selectedRow);
    }

}
