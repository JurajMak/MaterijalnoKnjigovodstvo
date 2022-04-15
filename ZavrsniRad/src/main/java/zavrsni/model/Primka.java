
package zavrsni.model;


import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;




/**
 *
 * @author juraj
 */
@Entity
public class Primka extends Entitet{
    
    @ManyToOne  
    private Ura ura;  
    @ManyToMany   
    private List <Roba> roba; 
  
    private BigDecimal cijena;
    private Integer kolicina;
    private String otpremnicaPrimka;
    

    public String getOtpremnicaPrimka() {
        return otpremnicaPrimka;
    }

    public void setOtpremnicaPrimka(String otpremnicaPrimka) {
        this.otpremnicaPrimka = otpremnicaPrimka;
    }

    public List<Roba> getRoba() {
        return roba;
    }

    public void setRoba(List<Roba> roba) {
        this.roba = roba;
    }

    
    

    public Primka() {
    }

  
    
    public Ura getUra() {
        return ura;
    }

    public void setUra(Ura ura) {
        this.ura = ura;
    }



    public Integer getKolicina() {
        return kolicina;
    }

    public void setKolicina(Integer kolicina) {
        this.kolicina = kolicina;
    }


    public BigDecimal getCijena() {
        return cijena;
    }

    public void setCijena(BigDecimal cijena) {
        this.cijena = cijena;
    }

    @Override
    public String toString() {
        return otpremnicaPrimka;
    }
    
    
    
}
