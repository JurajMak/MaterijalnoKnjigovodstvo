/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zavrsni.model;


import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 *
 * @author juraj
 */
@Entity
public class Roba extends Entitet{
    private String naziv;
    private Integer kolicina;
    private String mjernaJedinica;
    private BigDecimal cijena;
  
    @ManyToMany(mappedBy = "roba")
    private List<Primka> primka; 
  

    public Roba(){
        
    }
    
    
    public Roba(String naziv, Integer kolicina, String mjernaJedinica, BigDecimal cijena) {
        super();
        this.naziv = naziv;
        this.kolicina = kolicina;
        this.mjernaJedinica = mjernaJedinica;
        this.cijena = cijena;
        
    }

  

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Integer getKolicina() {
        return kolicina;
    }

    public void setKolicina(Integer kolicina) {
        this.kolicina = kolicina;
    }

    public String getMjernaJedinica() {
        return mjernaJedinica;
    }

    public void setMjernaJedinica(String mjernaJedinica) {
        this.mjernaJedinica = mjernaJedinica;
    }

    public BigDecimal getCijena() {
        return cijena;
    }

    public void setCijena(BigDecimal cijena) {
        this.cijena = cijena;
    }

    public List<Primka> getPrimka() {
        return primka;
    }

    public void setPrimka(List<Primka> primka) {
        this.primka = primka;
    }

 

 


  

    @Override
    public String toString() {
        return naziv;
    }

   
    
    
    
}
