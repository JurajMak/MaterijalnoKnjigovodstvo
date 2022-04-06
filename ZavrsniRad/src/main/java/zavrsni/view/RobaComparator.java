
package zavrsni.view;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import zavrsni.model.Roba;

/**
 *
 * @author juraj
 */
public class RobaComparator implements Comparator<Roba> {

    private Collator cl;

    public RobaComparator() {
        cl = Collator.getInstance(new Locale("hr", "HR")); //Your locale here
        cl.setStrength(Collator.PRIMARY);
    }

    @Override
    public int compare(Roba o1, Roba o2) {
        return cl.compare(o1.getNaziv(), o2.getNaziv());
    }

}