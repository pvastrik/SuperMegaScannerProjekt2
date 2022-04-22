import com.opencsv.CSVReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Inventar implements Serializable {
    private List<Tehnika> koguVara;
    private List<Laenutaja> laenutajad = new ArrayList<>();


    Inventar() throws Exception {
        koguVara = Failid.looInventarXLSX();
    }

    Laenutaja otsiLaenutajat(String isikukood) {
        for (Laenutaja l : laenutajad) {
            if (l.getIsikukood().equals(isikukood)) {
                return l;
            }
        }
        return null;
    }

    public List<Laenutaja> getLaenutajad() {
        return laenutajad;
    }

    void lisaLaenutaja(Laenutaja laenutaja) {
        laenutajad.add(laenutaja);
    }

    Tehnika getTehnika(Triipkood triipkood) {
        for (Tehnika tehnika : koguVara) {
            if (triipkood.equals(tehnika.getTriipkood())) return tehnika;
        }
        throw new RuntimeException("Sellise koodiga tehnikat ei leitud.");
    }

    @Override
    public String toString() {
        return "Inventar{" +
                "koguVara=" + koguVara +
                '}';
    }
}
