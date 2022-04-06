/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zavrsni.util;

/**
 *
 * @author juraj
 */
public class ZavrsniException extends Exception{

    private String poruka;

    public ZavrsniException(String poruka) {
        super();
        this.poruka = poruka;
    }

    public String getPoruka() {

        return poruka;
    }
}
