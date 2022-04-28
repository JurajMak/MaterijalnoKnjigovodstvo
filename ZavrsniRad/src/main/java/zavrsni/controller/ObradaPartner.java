
package zavrsni.controller;

import java.util.List;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import zavrsni.model.Ira;
import zavrsni.model.Partner;
import zavrsni.model.Ura;
import zavrsni.util.OibValidator;                                                                           
import zavrsni.util.ZavrsniException;

/**
 *
 * @author juraj
 */
public class ObradaPartner extends Obrada<Partner> {

    @Override
    public List<Partner> read() {
        
        return session.createQuery("from Partner").list();
    }
    
         public List<Partner> read(String uvjet) {
        return session.createQuery("from Partner p "
                + " where concat(p.naziv) "
                + " like :uvjet order by p.naziv")
                .setParameter("uvjet","%" + uvjet + "%")
                .setMaxResults(50)
                .list();
    }
    
 
    @Override
    protected void kontrolaCreate() throws ZavrsniException {
        unosPartnera();
       //  oibPartner();
        kontrolaEmail();
    }

    @Override
    protected void kontrolaUpdate() throws ZavrsniException {
        unosPartnera();
       // oibPartner();
        kontrolaEmail();
    }

    @Override
    protected void kontrolaDelete() throws ZavrsniException {
         if(entitet.getUra()!=null){
            
            StringBuilder sb = new StringBuilder();
            sb.append("\n");
            for(Ura u:entitet.getUra()){
                sb.append(u.getBrojRacuna());
                sb.append("\n");
            }
             throw new ZavrsniException("Ne možete obrisati partnera dok ne obrišete povezane Ura-e pod brojem" + sb.toString());
        }
         if(entitet.getIra()!=null){
            
            StringBuilder sb = new StringBuilder();
            sb.append("\n");
            for(Ira i:entitet.getIra()){
                sb.append(i.getBrojRacuna());
                sb.append("\n");
            }
             throw new ZavrsniException("Ne možete obrisati partnera dok ne obrišete povezane Ira-e pod brojem" + sb.toString());
        }
    }
 
    private void unosPartnera() throws ZavrsniException{
        if(entitet.getNaziv()== null || entitet.getNaziv().trim().isEmpty()){
            throw new ZavrsniException("Obavezan unos naziva partnera");
            
        }
        if(entitet.getAdresa()== null || entitet.getAdresa().trim().isEmpty()){
            throw new ZavrsniException("Obavezan unos adrese partnera");
        }
        if(entitet.getOib()==null || entitet.getOib().trim().isEmpty()){
            throw new ZavrsniException("Obavezan unos OIB-a partnera");
        }
        
    }
    
    private void oibPartner() throws ZavrsniException{
         if (!OibValidator.checkOIB(entitet.getOib())) {
            throw new ZavrsniException("Format OIB-a nije ispravan");
        }
    }
     private void kontrolaEmail() throws ZavrsniException {
        
        try {
            InternetAddress emailAddr = new InternetAddress(entitet.getEmail());
            emailAddr.validate();
        } catch (AddressException ex) {
            throw new ZavrsniException("Format Email-a nije ispravan");
        }
    }
}
