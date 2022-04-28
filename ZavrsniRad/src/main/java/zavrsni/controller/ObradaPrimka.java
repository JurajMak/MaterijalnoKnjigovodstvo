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
public class ObradaPrimka extends Obrada<Primka> {

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
        unosPrimke();

    }

    @Override
    protected void kontrolaDelete() throws ZavrsniException {
    }

    private void unosPrimke() throws ZavrsniException {
        if (entitet.getCijena() == null || entitet.getCijena().compareTo(BigDecimal.ZERO) < 0) {
            throw new ZavrsniException("Obavezan unos cijene robe");
        }

        if (entitet.getKolicina() == null || entitet.getKolicina() == 0) {
            throw new ZavrsniException("Obavezan unos količine");
        }

        if (entitet.getRoba() == null || entitet.getRoba().getId().equals(Long.valueOf(0))) {
            throw new ZavrsniException("Obavezan unos Robe");
        }

        if (entitet.getOtpremnicaPrimka() == null || entitet.getOtpremnicaPrimka().trim().isEmpty()) {
            throw new ZavrsniException("Obavezan unos broja Otpremnice-Primke");
        }

    }

    public void dodavanje(long id, Integer kolicina, BigDecimal cijena, Roba roba, Ura ura) {
        Session s = HibernateUtil.getSession();
        Transaction tr = s.beginTransaction();
        entitet = s.load(Primka.class, id);
        entitet.setKolicina(entitet.getKolicina() - kolicina);
        entitet.setCijena(cijena);
        entitet.setRoba(roba);
        entitet.setUra(ura);
        tr.commit();

    }

}
