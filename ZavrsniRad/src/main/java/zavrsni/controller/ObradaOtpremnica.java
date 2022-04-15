/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zavrsni.controller;

import java.math.BigDecimal;
import java.util.List;
import zavrsni.model.Otpremnica;
import zavrsni.util.ZavrsniException;

/**
 *
 * @author juraj
 */
public class ObradaOtpremnica extends Obrada<Otpremnica> {

    @Override
    public List<Otpremnica> read() {

        return session.createQuery("from Otpremnica").list();
    }

    @Override
    protected void kontrolaCreate() throws ZavrsniException {
        kontrolaOtpremnice();
    }

    @Override
    protected void kontrolaUpdate() throws ZavrsniException {
        kontrolaOtpremnice();
    }

    @Override
    protected void kontrolaDelete() throws ZavrsniException {
    }

    private void kontrolaOtpremnice() throws ZavrsniException {
        
           List<Otpremnica> racun = session.createQuery("from Otpremnica u " + "where u.brojOtpremnice=:brojOtpremnice")
                .setParameter("brojOtpremnice", entitet.getBrojOtpremnice()).list();
        
        if(racun!= null && !racun.isEmpty()){
            throw new ZavrsniException("Broj računa već postoji " + racun.get(0).getBrojOtpremnice()); }
        
 
        if (entitet.getKolicina() == 0 || entitet.getKolicina() == null) {
            throw new ZavrsniException("Obavezan unos količine!");
        }
          if(entitet.getCijena()== null || entitet.getCijena().compareTo(BigDecimal.ZERO)<0){
            throw new ZavrsniException("Obavezan unos cijene robe");
        }
       
    }

}
