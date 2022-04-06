
package zavrsni.app;

import org.hibernate.Session;

import zavrsni.view.SplashScreen;

/**
 *
 * @author juraj
 */
public class Start {

    private Session session;

    public Start() {
    
    new SplashScreen().setVisible(true);
        
    }

    public static void main(String[] args) {
        new Start();

    }

}
