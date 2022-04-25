/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zavrsni.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author juraj
 */
@Entity
public class Ira extends Entitet{
    
    @ManyToOne   
    private Partner partner;
    private String brojRacuna;
    private BigDecimal iznos;
    private Date datumIzdavanja;
    private Date datumDospijeca;
    @OneToMany(mappedBy = "ira")
    private List<Otpremnica> otpreminca;

    public List<Otpremnica> getOtpreminca() {
        return otpreminca;
    }

    public void setOtpreminca(List<Otpremnica> otpreminca) {
        this.otpreminca = otpreminca;
    }

 
  

    
    
    

    public Ira() {
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
