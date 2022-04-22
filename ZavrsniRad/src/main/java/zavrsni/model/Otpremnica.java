/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zavrsni.model;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 *
 * @author juraj
 */
@Entity
public class Otpremnica extends Entitet{
   @ManyToMany   
   private List<Roba> roba;
   @ManyToOne
   private Ira ira;
   private BigDecimal cijena;
   private String brojOtpremnice;

    public Otpremnica() {
    }
   

    public String getBrojOtpremnice() {
        return brojOtpremnice;
    }

    public void setBrojOtpremnice(String brojOtpremnice) {
        this.brojOtpremnice = brojOtpremnice;
    }

    

    public BigDecimal getCijena() {
        return cijena;
    }

    public void setCijena(BigDecimal cijena) {
        this.cijena = cijena;
    }

   
   private Integer kolicina;

    public List<Roba> getRoba() {
        return roba;
    }

    public void setRoba(List<Roba> roba) {
        this.roba = roba;
    }

 
   
 

    public Ira getIra() {
        return ira;
    }

    public void setIra(Ira ira) {
        this.ira = ira;
    }


    public Integer getKolicina() {
        return kolicina;
    }

    public void setKolicina(Integer kolicina) {
        this.kolicina = kolicina;
    }

    @Override
    public String toString() {
        return brojOtpremnice;
    }
   
   
}
