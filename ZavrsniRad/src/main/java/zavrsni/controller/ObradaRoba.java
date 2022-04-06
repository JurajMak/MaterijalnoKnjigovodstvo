package zavrsni.controller;

import java.math.BigDecimal;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import zavrsni.model.Roba;
import zavrsni.util.HibernateUtil;
import zavrsni.util.ZavrsniException;

/**
 *
 * @author juraj
 */
public class ObradaRoba extends Obrada<Roba> {

    @Override
    public List<Roba> read() {
        return session.createQuery("from Roba").list();

    }

    public List<Roba> read(String uvjet) {
        return session.createQuery("from Roba r "
                + " where concat(r.naziv) "
                + " like :uvjet order by r.naziv")
                .setParameter("uvjet", "%" + uvjet + "%")
                .setMaxResults(50)
                .list();
    }

    @Override
    protected void kontrolaCreate() throws ZavrsniException {
        unosRobe();
    }

    @Override
    protected void kontrolaUpdate() throws ZavrsniException {
        unosRobe();
    }

    @Override
    protected void kontrolaDelete() throws ZavrsniException {
    }

    private void unosRobe() throws ZavrsniException {

        if (entitet.getNaziv() == null || entitet.getNaziv().trim().isEmpty()) {
            throw new ZavrsniException("Obavezan unos naziva robe");
        }

        if (entitet.getKolicina() == null || entitet.getKolicina() == 0) {
            throw new ZavrsniException("Obavezan unos količine u brojčanom formatu");
        }

        if (entitet.getMjernaJedinica() == null || entitet.getMjernaJedinica().trim().isEmpty()) {
            throw new ZavrsniException("Obavezan unos mjerne jedinice u tekstualnom formatu");
        }

        if (entitet.getCijena() == null || entitet.getCijena() == BigDecimal.ONE) {
            throw new ZavrsniException("Obavezan unos cijene robe");
        }
    }

    public void skidanjeKolicine(long id, Integer i/*,BigDecimal cijena*/) {
        Session s = HibernateUtil.getSession();
        Transaction tr = s.beginTransaction();
        entitet = s.load(Roba.class, id);
        entitet.setKolicina(entitet.getKolicina() - i);
        tr.commit();

    }

    public void dodavanjeKolicine(long id, Integer i) {
        Session s = HibernateUtil.getSession();
        Transaction tr = s.beginTransaction();
        entitet = s.load(Roba.class, id);
        entitet.setKolicina(i + entitet.getKolicina());
        tr.commit();
    }

}
