
package zavrsni.controller;

import java.util.List;
import zavrsni.model.Ira;
import zavrsni.util.ZavrsniException;

/**
 *
 * @author juraj
 */
public class ObradaIra extends Obrada<Ira> {

    @Override
    public List<Ira> read() {
        return session.createQuery("from Ira").list();

    }

    @Override
    protected void kontrolaCreate() throws ZavrsniException {
        kontrolaBrojRacuna();
        kontrolaDatumDospijeca();
        kontrolaPartner();
    }

    @Override
    protected void kontrolaUpdate() throws ZavrsniException {
        kontrolaBrojRacuna();
        kontrolaDatumDospijeca();
        kontrolaPartner();
    }

    @Override
    protected void kontrolaDelete() throws ZavrsniException {
         if(entitet.getOtpremnica()!=null){
            throw new ZavrsniException("Ne možete obrisati Iru broj" + entitet.getId()+ "jer sadržava Otpremnicu pod brojem " + entitet.getOtpremnica());
        }
    }

   
    private void kontrolaBrojRacuna() throws ZavrsniException {
        if (entitet.getBrojRacuna() == null || entitet.getBrojRacuna().trim().isEmpty() || !entitet.getBrojRacuna().contains("/")) {
            throw new ZavrsniException("Broj računa mora sadržavati 00/0000 format");
            
        }
    }

    private void kontrolaDatumDospijeca() throws ZavrsniException {
        if (entitet.getDatumDospijeca() == null || entitet.getDatumDospijeca().equals((int)0)) {
            throw new ZavrsniException("Morate unijeti datum dospijeća računa");
        }
    }

    private void kontrolaPartner() throws ZavrsniException {
        if (entitet.getPartner() == null) {
            throw new ZavrsniException("Obavezan unos šifre Partnera");
        }
    }

  
    
}
