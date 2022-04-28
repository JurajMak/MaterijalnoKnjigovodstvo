package zavrsni.util;

/**
 *
 * @author juraj
 */
public class ZavrsniException extends Exception {

    private String poruka;

    public ZavrsniException(String poruka) {
        super();
        this.poruka = poruka;
    }

    public String getPoruka() {

        return poruka;
    }
}
