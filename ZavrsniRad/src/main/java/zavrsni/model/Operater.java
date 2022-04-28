package zavrsni.model;

import javax.persistence.Entity;

/**
 *
 * @author juraj
 */
@Entity
public class Operater extends Entitet {

    private String lozinka;
    private String uloga;
    private String ime;
    private String prezime;

    public Operater() {

    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getUloga() {
        return uloga;
    }

    public void setUloga(String uloga) {
        this.uloga = uloga;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

}
