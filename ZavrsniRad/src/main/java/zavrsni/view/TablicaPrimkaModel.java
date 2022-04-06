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
public class TablicaPrimkaModel extends AbstractTableModel{
        
    private List<Roba> robe;
    private List<Partner> partneri;
    private List<Primka> primke;
    private List<Ura> ure;

    TablicaPrimkaModel(List<Ura> read) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
  
/*
    public TablicaPrimkaModel(List<Roba> robe, List<Partner> partneri, List<Primka> primke, List<Ura> ure) {
        this.robe = robe;
        this.partneri = partneri;
        this.primke = primke;
        this.ure = ure;
    }

   */

    @Override
    public int getRowCount() {
                return (robe==null ? 0 : robe.size()) + (partneri== null ? 0 :partneri.size());

    }

    public List<Primka> getPrimke() {
        return primke;
    }

    public void setPrimke(List<Primka> primke) {
        this.primke = primke;
    }

    public List<Ura> getUre() {
        return ure;
    }

    public void setUre(List<Ura> ure) {
        this.ure = ure;
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
           Primka pr = primke.get(rowIndex);
           Partner p = partneri.get(rowIndex);
           Roba r = robe.get(rowIndex);
         Object value = "??";
        switch (columnIndex) {
            case 0 -> value = pr.getUra().getPartner().getNaziv();
            case 1 -> value = p.getNaziv();
        }
        return value;
    }

    public List<Roba> getRobe() {
        return robe;
    }

    public void setRobe(List<Roba> robe) {
        this.robe = robe;
    }

    public List<Partner> getPartneri() {
        return partneri;
    }

    public void setPartneri(List<Partner> partneri) {
        this.partneri = partneri;
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

   
    
    public Partner getPartneriAt(int selectedRow){
        return partneri.get(selectedRow);
    }
    
    public Primka getPrimkaAt(int selectedRow){
        return primke.get(selectedRow);
    }
    
    }
    
