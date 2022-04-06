/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zavrsni.util;

import org.hibernate.Session;
import zavrsni.controller.ObradaPrimka;
import zavrsni.model.Primka;

/**
 *
 * @author juraj
 */
public class Testiranje {
    
    
    public static void main(String[] args) {
     
      test();
        
    }

 
         public static void test(){
             Session session = HibernateUtil.getSession();
             session.beginTransaction();
             Primka p = new Primka();
             ObradaPrimka op = new ObradaPrimka();
             op.setEntitet(p);
             p.setKolicina(0);
             session.save(p);
             session.getTransaction().commit();
             
             
         } 
          
        

    
    

}
