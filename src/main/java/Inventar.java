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


    Inventar(String failinimi) throws IOException {
        this.koguVara = new ArrayList<>();
        looInventarXLSX(failinimi);
    }

    void looInventarFailist(String failinimi) {

        try (CSVReader reader = new CSVReader(new FileReader(failinimi))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                Triipkood kood = new Triipkood(nextLine[0]);
                Tehnika ese = new Tehnika(kood, nextLine[1]);
                koguVara.add(ese);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     void looInventarXLSX(String failinimi) throws IOException {
        File fail = new File(failinimi);
        FileInputStream failInput = new FileInputStream(fail);
        XSSFWorkbook wb = new XSSFWorkbook(failInput);
        XSSFSheet leht = wb.getSheetAt(0);
        Iterator<Row> loendur = leht.iterator();
        Row pealkirjad = loendur.next();
        while(loendur.hasNext()) {
            Triipkood triipkood = null;
            String kirjeldus = null;
            Row rida = loendur.next();
            Iterator<Cell> kastiLoendur = rida.cellIterator();
            while(kastiLoendur.hasNext()) {
                Cell kast = kastiLoendur.next();
                switch(kast.getColumnIndex()) {
                    case 0 -> triipkood = new Triipkood(kast.getStringCellValue());
                    case 1 -> kirjeldus = kast.getStringCellValue();
                }
            }
            if(triipkood != null && kirjeldus != null) {
                Tehnika toode = new Tehnika(triipkood, kirjeldus);
                koguVara.add(toode);
            }
        }

    }

    public List<Laenutaja> getLaenutajad() {
        return laenutajad;
    }

    void lisaLaenutaja(Laenutaja laenutaja) {
        laenutajad.add(laenutaja);
    }
    Tehnika getTehnika(Triipkood triipkood) {
        for (Tehnika tehnika : koguVara) {
            if(triipkood.equals(tehnika.getTriipkood())) return tehnika;
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
