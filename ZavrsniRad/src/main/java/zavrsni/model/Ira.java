package zavrsni.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author juraj
 */
@Entity
public class Ira extends Entitet {

    @ManyToOne
    @JoinColumn(name = "partner")
    private Partner partner;
    private String brojRacuna;
    private BigDecimal iznos;
    private Date datumIzdavanja;
    private Date datumDospijeca;
    @OneToMany(mappedBy = "ira")
    private List<Otpremnica> otp;

    public Ira() {
    }

    public List<Otpremnica> getOtp() {
        return otp;
    }

    public void setOtp(List<Otpremnica> otp) {
        this.otp = otp;
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
