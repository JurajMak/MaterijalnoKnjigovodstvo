package zavrsni.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import us.codecraft.xsoup.Xsoup;
import zavrsni.model.Operater;

/**
 *
 * @author juraj
 */
public class ZavrsniUtil {

    public static Operater operater;
    public static final String NAZIV_APP = "Materijalno Knjigovodstvo";

    public static String getNaslov(String naslov) {
        if (ZavrsniUtil.operater == null) {
            return ZavrsniUtil.NAZIV_APP + " " + naslov;
        }
        return ZavrsniUtil.NAZIV_APP + " " + naslov + " " + ZavrsniUtil.operater.getIme() + " "
                + ZavrsniUtil.operater.getPrezime() + " - " + ZavrsniUtil.operater.getUloga();
    }

    public static String generirajBoljiOib() {
        // DZ
        // Umjesto spajanja na web stranicu napraviti generiranje u java programskom jeziku
        // https://regos.hr/app/uploads/2018/07/KONTROLA-OIB-a.pdf
        try {
            URL url = new URL("http://oib.itcentrala.com/oib-generator/");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            url.openStream()));
            String inputLine;
            StringBuilder sb = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
            in.close();
            Document d = Jsoup.parse(sb.toString());
            return Xsoup.compile("/html/body/div[1]/div[1]/text()").evaluate(d).get();
        } catch (Exception e) {
        }
        return "";
    }

}
