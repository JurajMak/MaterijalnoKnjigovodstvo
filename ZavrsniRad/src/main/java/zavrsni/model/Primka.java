
package zavrsni.model;


import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;



/**
 *
 * @author juraj
 */
@Entity
public class Primka extends Entitet{
    
    @ManyToOne
    @JoinColumn(name = "ura")
    private Ura ura;
    @ManyToOne 
    @JoinColumn(name = "roba")
    private Roba roba;
    private BigDecimal cijena;
    private Integer kolicina;
    private String otpremnicaPrimka;

    public String getOtpremnicaPrimka() {
        return otpremnicaPrimka;
    }

    public void setOtpremnicaPrimka(String otpremnicaPrimka) {
        this.otpremnicaPrimka = otpremnicaPrimka;
    }

    public Primka() {
    }

  
    
    public Ura getUra() {
        return ura;
    }

    public void setUra(Ura ura) {
        this.ura = ura;
    }

    public Roba getRoba() {
        return roba;
    }

    public void setRoba(Roba roba) {
        this.roba = roba;
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
    
    
    
}
