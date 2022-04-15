/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zavrsni.model;


import javax.persistence.ManyToOne;

/**
 *
 * @author juraj
 */

public class RobaPrimka extends Entitet {
    
    @ManyToOne
    private Roba roba;
    
    @ManyToOne
    private Primka primka;

    public Roba getRoba() {
        return roba;
    }

    public void setRoba(Roba roba) {
        this.roba = roba;
    }

    public Primka getPrimka() {
        return primka;
    }

    public void setPrimka(Primka primka) {
        this.primka = primka;
    }
     
     
    
}
