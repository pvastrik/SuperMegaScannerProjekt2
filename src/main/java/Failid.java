import com.opencsv.CSVReader;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

class Failid {

    private static final String filepath = "C:\\Users\\priid\\OneDrive\\ati comp laenutus\\";

    static List<Tehnika> looInventarCSV() throws Exception {
        List<Tehnika> koguVara = new ArrayList<>();
        CSVReader reader = new CSVReader(new FileReader("inventar.csv"));
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            Triipkood kood = new Triipkood(nextLine[0]);
            Tehnika ese = new Tehnika(kood, nextLine[1]);
            koguVara.add(ese);
        }
        return koguVara;
    }

    static List<Tehnika> looInventarXLSX() throws IOException {
        List<Tehnika> koguVara = new ArrayList<>();
        File fail = new File(filepath + "inventar.xlsx");
        FileInputStream failInput = new FileInputStream(fail);
        XSSFWorkbook wb = new XSSFWorkbook(failInput);
        XSSFSheet leht = wb.getSheetAt(0);
        Iterator<Row> loendur = leht.iterator();
        Row pealkirjad = loendur.next();
        while (loendur.hasNext()) {
            Triipkood triipkood = null;
            String kirjeldus = null;
            Row rida = loendur.next();
            Iterator<Cell> kastiLoendur = rida.cellIterator();
            while (kastiLoendur.hasNext()) {
                Cell kast = kastiLoendur.next();
                switch (kast.getColumnIndex()) {
                    case 0 -> triipkood = new Triipkood(kast.getStringCellValue());
                    case 1 -> kirjeldus = kast.getStringCellValue();
                }
            }
            if (triipkood != null && kirjeldus != null) {
                Tehnika toode = new Tehnika(triipkood, kirjeldus);
                koguVara.add(toode);
            }
        }
        return koguVara;

    }

    static Inventar loeInventarTXT(String failinimi) throws IOException, ClassNotFoundException {
        FileInputStream fileStream = null;
        fileStream = new FileInputStream(failinimi);

        // Creating an object input stream
        ObjectInputStream objStream = null;
        objStream = new ObjectInputStream(fileStream);

        //Using the readObject() method
        return (Inventar) objStream.readObject();

    }

    static void kirjutaLaenutusCloudi(Laenutus laenutus) throws IOException {
        File fail = new File(filepath + "laenutused.xlsx");

        Object[] laenutuseInfo = {laenutus.getTehnika().getKirjeldus(), laenutus.getLaenutaja().getEesnimi() + " " + laenutus.getLaenutaja().getPerenimi(),
                laenutus.getAlgus(), laenutus.getLopp()};

        try {
            FileInputStream inputStream = new FileInputStream(fail);
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet leht = workbook.getSheetAt(0);
            int ridu = leht.getLastRowNum();

            Row rida = leht.createRow(++ridu);
            laenutus.setKohtFailis(ridu);
            int tulp = 0;
            for (Object info : laenutuseInfo) {
                Cell kast = rida.createCell(tulp++);
                if (info instanceof String) kast.setCellValue((String) info);
                else if (info instanceof LocalDate) {
                    kast.setCellValue(formatKuupäev(info));
                }
            }
            inputStream.close();


            FileOutputStream out = new FileOutputStream(fail);
            workbook.write(out);
            workbook.close();
            out.close();
            System.out.println("Kirjutatud");
        } catch (IOException | EncryptedDocumentException e) {
            e.printStackTrace();
        }
    }

    static void lisaTagastamineCloudi(Laenutus laenutus) throws IOException {
        File fail = new File(filepath + "laenutused.xlsx");
        FileInputStream inputStream = new FileInputStream(fail);
        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet leht = workbook.getSheetAt(0);

        Row rida = leht.getRow(laenutus.getKohtFailis());
        Cell kast = rida.createCell(rida.getLastCellNum());
        kast.setCellValue(formatKuupäev(laenutus.getLopetatud()));

        inputStream.close();

        FileOutputStream out = new FileOutputStream(fail);
        workbook.write(out);
        workbook.close();
        out.close();
        System.out.println("Lõpp lisatud");
    }

    static void salvestaObjektFaili(Object o) throws IOException {
        FileOutputStream file = null;
        file = new FileOutputStream("file.txt");

        ObjectOutputStream output = null;

        output = new ObjectOutputStream(file);

        output.writeObject(o);

    }

    static String formatKuupäev(Object kuupäev) {
        String[] kuupäevTükid = kuupäev.toString().split("-");
        Collections.reverse(Arrays.asList(kuupäevTükid));
        return String.join(".", kuupäevTükid);
    }
}