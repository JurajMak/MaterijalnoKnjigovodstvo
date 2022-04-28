
package zavrsni.controller;

import java.util.List;
import zavrsni.model.Ira;
import zavrsni.model.Otpremnica;
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
             if(entitet.getOtp()!=null && entitet.getOtp().size()>0){
            
            StringBuilder sb = new StringBuilder();
            sb.append("\n");
            for(Otpremnica o:entitet.getOtp()){
                sb.append(o.getBrojOtpremnice());
                sb.append("\n");
            }
            
            throw new ZavrsniException("Ne možete obrisati Iru dok ne obrišete povezanu otpremnicu pod brojem" + sb.toString());
        }
    }

   
    private void kontrolaBrojRacuna() throws ZavrsniException {
        if (entitet.getBrojRacuna() == null || entitet.getBrojRacuna().trim().isEmpty() || !entitet.getBrojRacuna().contains("/")) {
            throw new ZavrsniException("Broj računa mora sadržavati 00/0000 format");
            
        }
    }

    private void kontrolaDatumDospijeca() throws ZavrsniException {
         if(entitet.getDatumIzdavanja()==null){
            throw new ZavrsniException("Obavezan unos datuma izdavanja!");
        }
        if (entitet.getDatumDospijeca() == null) {
            throw new ZavrsniException("Morate unijeti datum dospijeća računa");
        }
    }

    private void kontrolaPartner() throws ZavrsniException {
        if (entitet.getPartner() == null || entitet.getPartner().getId().equals(Long.valueOf(0))) {
            throw new ZavrsniException("Obavezan unos Kupca");
        }
    }

  
    
}
