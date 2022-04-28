
package zavrsni.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 *
 * @author juraj
 */
@Entity
public class Partner extends Entitet {

    private String naziv;
    private String adresa;
    private String oib;
    private String email;
     @OneToMany(mappedBy = "partner")
    private List <Ira> ira;
    @OneToMany(mappedBy = "partner")
    private List<Ura> ura;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }

    @Override
    public String toString() {
        return naziv;
    }

    public List<Ira> getIra() {
        return ira;
    }

    public void setIra(List<Ira> ira) {
        this.ira = ira;
    }

    public List<Ura> getUra() {
        return ura;
    }

    public void setUra(List<Ura> ura) {
        this.ura = ura;
    }

    
    
}
