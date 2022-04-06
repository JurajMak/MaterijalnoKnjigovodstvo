/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zavrsni.util;

import java.util.Random;

/**
 *
 * @author juraj
 */
public class OibGenerator {

    //nije izračunat po  „Modul 11,10“ ISO 7064 sistemu
    // kod "brzinski sklepan" generira 11 nasumičnih znamenki kao simulaciju oiba
    public static String generirajOib(int[] niz) {

        Random num = new Random();

        niz = new int[11];
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 11; i++) {
            niz[i] += num.nextInt(9);
        }

        for (int j : niz) {
            builder.append(j);
        }

        //   System.out.println(builder);
        return builder.toString();

    }

}
