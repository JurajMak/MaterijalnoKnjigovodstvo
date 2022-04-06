/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zavrsni.view;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import zavrsni.model.Partner;

/**
 *
 * @author juraj
 */
public class PartnerComparator implements Comparator<Partner>{
    
    private Collator cl;
    
    public PartnerComparator(){
        cl = Collator.getInstance(new Locale("hr", "HR"));
        cl.setStrength(Collator.PRIMARY);
    }

    @Override
    public int compare(Partner o1, Partner o2) {
        return cl.compare(o1.getNaziv(), o2.getNaziv());
    }
    
}
