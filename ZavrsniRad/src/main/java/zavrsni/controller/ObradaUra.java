
package zavrsni.controller;

import java.math.BigDecimal;
import java.util.List;
import zavrsni.model.Primka;
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
        if(entitet.getPrimka()!=null && entitet.getPrimka().size()>0){
            
            StringBuilder sb = new StringBuilder();
            sb.append("\n");
            for(Primka p:entitet.getPrimka()){
                sb.append(p.getId());
                sb.append("\n");
            }
            
            throw new ZavrsniException("Ne možete obrisati Uru dok ne obrišete povezanu primku pod brojem" + sb.toString());
             
        } 
   
         
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
        if(entitet.getPartner()==null || entitet.getPartner().getId().equals(Long.valueOf(0))){
            throw new ZavrsniException("Obavezan unos partnera");
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
