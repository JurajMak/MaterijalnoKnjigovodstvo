package zavrsni.util;

import com.github.javafaker.Faker;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.mindrot.jbcrypt.BCrypt;
import zavrsni.model.Ira;
import zavrsni.model.Operater;
import zavrsni.model.Otpremnica;
import zavrsni.model.Partner;
import zavrsni.model.Primka;
import zavrsni.model.Roba;
import zavrsni.model.Ura;

/**
 *
 * @author juraj
 */
public class PocetniInsert {

    public static void sve() {
        izvedi();
        unosOperatera();
    }

    public static void izvedi() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Faker faker = new Faker();
        List<Partner> partneri = generirajPartnere(faker, session);
        List<Roba> robe = generirajRobu(faker, session);

        Partner p;
        Ura u;
        Primka pr;
        Roba r;
        Otpremnica ot;
        Ira ir;

        for (int i = 0; i < partneri.size(); i++) {
            p = partneri.get(i);
            r = robe.get(i);

            for (int j = 0; j < ((int) Math.random() * (5 - 2) + 1); j++) {
                u = new Ura();
                pr = new Primka();
                ot = new Otpremnica();
                ir = new Ira();

                u.setPartner(p);
                u.setBrojRacuna((i + 1) + "/2022");
                u.setDatumDospijeca(new Date());
                u.setDatumIzdavanja(new Date());
                u.setIznos(new BigDecimal(Math.random() * (1000 - 100) + 100));
                pr.setCijena(new BigDecimal(Math.random() * (1000 - 100) + 100));
                pr.setKolicina(faker.number().numberBetween(10, 100));
                pr.setUra(u);
                pr.setRoba(robe);
                pr.setOtpremnicaPrimka((i + 1)+"/2022");
                ir.setBrojRacuna((i + 1) + "/2022");
                ir.setDatumDospijeca(new Date());
                ir.setDatumIzdavanja(new Date());
                ir.setIznos(new BigDecimal(Math.random() * (1000 - 100) + 100));
                ir.setPartner(p);
                ot.setCijena(new BigDecimal(Math.random() * (1000 - 100) + 100));
                ot.setKolicina(faker.number().numberBetween(10, 100));
                ot.setBrojOtpremnice((i + 1) + "/2022");
                ot.setIra(ir);
                ot.setRoba(robe);

                session.save(u);
                session.save(pr);
                session.save(ir);
                session.save(ot);

            }

        }

        session.getTransaction().commit();
    }

    private static List<Partner> generirajPartnere(Faker faker, Session session) {
        List<Partner> partneri = new ArrayList();
        Partner p;

        for (int i = 0; i < 20; i++) {
            p = new Partner();
            p.setNaziv(faker.company().name());
            p.setAdresa(faker.address().fullAddress());
            p.setOib(ZavrsniUtil.generirajBoljiOib());
            session.save(p);
            partneri.add(p);

        }
        return partneri;
    }

    private static List<Roba> generirajRobu(Faker faker, Session session) {
        List<Roba> roba = new ArrayList();
        Roba r;

        for (int i = 0; i < 20; i++) {
            r = new Roba();
            r.setNaziv(faker.beer().name());
            r.setCijena(new BigDecimal(Math.random() * (40 - 10) + 10));
            r.setKolicina(faker.number().numberBetween(10, 100));
            r.setMjernaJedinica("Kom");
            session.save(r);
            roba.add(r);

        }
        return roba;
    }

    public static void unosOperatera() {

        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        Operater o = new Operater();
        o.setIme("Korisnik");
        o.setPrezime("Programa");
        o.setLozinka(BCrypt.hashpw("1", BCrypt.gensalt()));
        o.setUloga("Admin");
        session.save(o);
        session.getTransaction().commit();

    }

}
