/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zavrsni.controller;

import java.util.List;
import javax.persistence.NoResultException;
import org.mindrot.jbcrypt.BCrypt;
import zavrsni.model.Operater;
import zavrsni.util.ZavrsniException;

/**
 *
 * @author juraj
 */
public class ObradaOperater extends Obrada {

    public Operater autoriziraj(String ime, String lozinka) {
        Operater operater = null;

        try {
            operater = (Operater) session.createQuery("from Operater where ime=:ime")
                    .setParameter("ime", ime).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

        if (operater == null) {
            return null;
        }

        return BCrypt.checkpw(lozinka, operater.getLozinka()) ? operater : null;
    }

    @Override
    public List read() {

        return session.createQuery("from Operater").list();

    }

    @Override
    protected void kontrolaCreate() throws ZavrsniException {
    }

    @Override
    protected void kontrolaUpdate() throws ZavrsniException {
    }

    @Override
    protected void kontrolaDelete() throws ZavrsniException {
    }

}
