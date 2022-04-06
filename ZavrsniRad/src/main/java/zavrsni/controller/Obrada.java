package zavrsni.controller;

import java.util.List;
import org.hibernate.Session;
import zavrsni.util.HibernateUtil;
import zavrsni.util.ZavrsniException;

/**
 *
 * @author juraj
 */
public abstract class Obrada<T> {

    protected Session session;
    protected T entitet;

    public abstract List<T> read();

    protected abstract void kontrolaCreate() throws ZavrsniException;

    protected abstract void kontrolaUpdate() throws ZavrsniException;

    protected abstract void kontrolaDelete() throws ZavrsniException;

    public Obrada() {
        session = HibernateUtil.getSession();
    }

    public T create() throws ZavrsniException {
        kontrolaCreate();
        save();
        return entitet;
    }

    public T update() throws ZavrsniException {
        kontrolaUpdate();
        save();
        return entitet;
    }

    public void delete() throws ZavrsniException {
        kontrolaDelete();

        session.beginTransaction();
        session.delete(entitet);
        session.getTransaction().commit();

    }

    private void save() {
        session.beginTransaction();
        session.save(entitet);
        session.getTransaction().commit();
    }

    public T getEntitet() {
        return entitet;
    }

    public void setEntitet(T entitet) {
        this.entitet = entitet;
    }

}
