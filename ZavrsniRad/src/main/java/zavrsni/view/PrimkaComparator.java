/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zavrsni.view;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import zavrsni.model.Primka;

/**
 *
 * @author juraj
 */
public class PrimkaComparator implements Comparator<Primka>{
        private Collator cl;
    
    public PrimkaComparator(){
        cl = Collator.getInstance(new Locale("hr", "HR"));
        cl.setStrength(Collator.PRIMARY);
    }

    

    @Override
    public int compare(Primka o1, Primka o2) {
        return cl.compare(o1.getOtpremnicaPrimka(), o2.getOtpremnicaPrimka());
    }
    

}
