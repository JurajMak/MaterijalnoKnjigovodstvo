
package zavrsni.controller;

import java.math.BigDecimal;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import zavrsni.model.Primka;
import zavrsni.model.Roba;
import zavrsni.model.Ura;
import zavrsni.util.HibernateUtil;
import zavrsni.util.ZavrsniException;

/**
 *
 * @author juraj
 */
public class ObradaPrimka  extends Obrada<Primka>{

    @Override
    public List<Primka> read() {
        
        return session.createQuery("from Primka").list();
    }

    @Override
    protected void kontrolaCreate() throws ZavrsniException {
      unosPrimke();
    }

    @Override
    protected void kontrolaUpdate() throws ZavrsniException {
     // unosPrimke();
        
    }

    @Override
    protected void kontrolaDelete() throws ZavrsniException {
    }
    
    private void unosPrimke() throws ZavrsniException{
        if(entitet.getCijena()== null || entitet.getCijena().compareTo(BigDecimal.ZERO)<0){
            throw new ZavrsniException("Obavezan unos cijene robe");
        }
        
        if(entitet.getKolicina()==null || entitet.getKolicina()==0){
            throw new ZavrsniException("Obavezan unos količine");
        }
        
          List<Primka> racun = session.createQuery("from Primka u " + "where u.otpremnicaPrimka=:otpremnicaPrimka")
                .setParameter("otpremnicaPrimka", entitet.getOtpremnicaPrimka()).list();
        
        if(racun!= null && !racun.isEmpty()){
            throw new ZavrsniException("Broj računa već postoji " + racun.get(0).getOtpremnicaPrimka()); }
        
        
    }
     
       public void dodavanje(long id,Ura u) {
        Session s = HibernateUtil.getSession();
        Transaction tr = s.beginTransaction();
        entitet = s.load(Primka.class, id);
        entitet.setUra(u);
    //    entitet.setRoba(robe);
        
         tr.commit();
    
    }

  
    public List<Primka> read(String uvjet) {
        return session.createQuery("from Primka p "
                + " where concat(p.otpremnicaPrimka) "
                + " like :uvjet order by p.otpremnicaPrimka")
                .setParameter("uvjet", "%" + uvjet + "%")
                .setMaxResults(50)
                .list();
    }
    
    
}
