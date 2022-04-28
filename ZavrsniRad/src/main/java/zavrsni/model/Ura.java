
package zavrsni.model;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author juraj
 */
@Entity
public class Ura extends Entitet{
        
    @ManyToOne
    private Partner partner;
    private String brojRacuna;
    private BigDecimal iznos;
    private Date datumIzdavanja;
    private Date datumDospijeca;
    
    @OneToMany(mappedBy = "ura")
    private List<Primka> primka;
    
   
    public Ura() {
    }
    
    

    public List<Primka> getPrimka() {
        return primka;
    }

    public void setPrimka(List<Primka> primka) {
        this.primka = primka;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public String getBrojRacuna() {
        return brojRacuna;
    }

    public void setBrojRacuna(String brojRacuna) {
        this.brojRacuna = brojRacuna;
    }

    public BigDecimal getIznos() {
        return iznos;
    }

    public void setIznos(BigDecimal iznos) {
        this.iznos = iznos;
    }

    public Date getDatumIzdavanja() {
        return datumIzdavanja;
    }

    public void setDatumIzdavanja(Date datumIzdavanja) {
        this.datumIzdavanja = datumIzdavanja;
    }

    public Date getDatumDospijeca() {
        return datumDospijeca;
    }

    public void setDatumDospijeca(Date datumDospijeca) {
        this.datumDospijeca = datumDospijeca;
    }

    @Override
    public String toString() {
        return brojRacuna;
    }
    
    
    
}
