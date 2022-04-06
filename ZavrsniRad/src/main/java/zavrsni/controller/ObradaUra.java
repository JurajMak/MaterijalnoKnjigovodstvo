
package zavrsni.controller;

import java.math.BigDecimal;
import java.util.List;
import zavrsni.model.Ura;
import zavrsni.util.ZavrsniException;

/**
 *
 * @author juraj
 */
public class ObradaUra extends Obrada<Ura>{

    @Override
    public List<Ura> read() {
        return session.createQuery("from Ura").list();
    }

    @Override
    protected void kontrolaCreate() throws ZavrsniException {
        kontrolaUre();
    }

    @Override
    protected void kontrolaUpdate() throws ZavrsniException {
        kontrolaUre();
    }

    @Override
    protected void kontrolaDelete() throws ZavrsniException {
    }
    
    
    private void kontrolaUre()throws ZavrsniException{
        List<Ura> racun = session.createQuery("from Ura u " + "where u.brojRacuna=:brojRacuna")
                .setParameter("brojRacuna", entitet.getBrojRacuna()).list();
        
        if(racun!= null && !racun.isEmpty()){
            throw new ZavrsniException("Broj računa već postoji " + racun.get(0).getBrojRacuna());
        }
        
        if(entitet.getBrojRacuna()==null || entitet.getBrojRacuna().trim().isEmpty()){
            throw new ZavrsniException("Obavezan unos broja racuna");
        }
        if(entitet.getPartner()==null){
            throw new ZavrsniException("Obavezan unos šifre/naziva partnera");
        }
        if(entitet.getIznos()== BigDecimal.ZERO || entitet.getIznos()==null){
            throw new ZavrsniException("Obavezan unos iznosa računa u brojčanom formatu");
        }
        
        if(entitet.getDatumDospijeca()==null){
            throw new ZavrsniException("Obavezan unos datuma dospijeća");
        }
    
        if(entitet.getDatumIzdavanja()==null){
            throw new ZavrsniException("Obavezan unos datuma izdavanja");
        }
        
       
    
    
    }
}
