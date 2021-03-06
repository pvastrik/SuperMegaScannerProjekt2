import com.opencsv.CSVReader;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

class Failid {

    protected static final String filepath = "C:\\Users\\priid\\OneDrive\\ati comp laenutus\\";

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


    static void kirjutaLaenutusCloudi(Laenutus laenutus) throws IOException {
        File fail = new File(filepath + "laenutused.xlsx");

        Object[] laenutuseInfo = {laenutus.getTehnika().getKirjeldus(), laenutus.getTehnika().getTriipkood().toString(), laenutus.getLaenutaja().getEesnimi() + " " + laenutus.getLaenutaja().getPerenimi(),
                laenutus.getAlgus(), laenutus.getLopp()};


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
                if (info.equals(LocalDate.MAX)) kast.setCellValue("Pikemaks ajaks");
                else kast.setCellValue(formatKuup??ev(info));
            }
        }
        inputStream.close();


        FileOutputStream out = new FileOutputStream(fail);
        workbook.write(out);
        workbook.close();
        out.close();
        System.out.println("Kirjutatud");

    }

    static void lisaTagastamineCloudi(Laenutus laenutus) throws IOException {
        File fail = new File(filepath + "laenutused.xlsx");
        FileInputStream inputStream = new FileInputStream(fail);
        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet leht = workbook.getSheetAt(0);

        Row rida = leht.getRow(laenutus.getKohtFailis());
        Cell kast = rida.createCell(rida.getLastCellNum());
        kast.setCellValue(formatKuup??ev(laenutus.getLopetatud()));

        inputStream.close();

        FileOutputStream out = new FileOutputStream(fail);
        workbook.write(out);
        workbook.close();
        out.close();
        System.out.println("L??pp lisatud");
    }

    static List<Laenutus> getPraegusedLaenutused() throws IOException {
        List<Laenutus> laenutused = new ArrayList<>();
        File fail = new File(filepath + "laenutused.xlsx");
        FileInputStream failInput = new FileInputStream(fail);
        XSSFWorkbook wb = new XSSFWorkbook(failInput);
        XSSFSheet leht = wb.getSheetAt(0);
        Iterator<Row> loendur = leht.iterator();
        Row pealkirjad = loendur.next();

        while (loendur.hasNext()) {
            Triipkood triipkood = null;
            Laenutaja laenutaja = null;
            boolean laenutatud = false;
            LocalDate algus = null, l??pp = null;
            Row rida = loendur.next();
            Iterator<Cell> kastiLoendur = rida.cellIterator();
            Cell laenutatudCell = rida.getCell(5);
            if (laenutatudCell == null || laenutatudCell.getCellType() == CellType.BLANK) laenutatud = true;
            else continue;
            while (kastiLoendur.hasNext()) {
                Cell kast = kastiLoendur.next();
                switch (kast.getColumnIndex()) {
                    case 2 -> {
                        String[] nimed = kast.getStringCellValue().split(" ");
                        laenutaja = new Laenutaja(nimed[0], nimed[1], "123");
                    }
                    case 1 -> triipkood = new Triipkood(kast.getStringCellValue());
                    case 3 -> {
                        String[] kuup??evad = kast.getStringCellValue().split("\\.");
                        algus = LocalDate.of(Integer.parseInt(kuup??evad[2]), Integer.parseInt(kuup??evad[1]), Integer.parseInt(kuup??evad[0]));
                    }
                    case 4 -> {
                        String[] kuup??evad = kast.getStringCellValue().split("\\.");
                        if (kuup??evad.length == 1) l??pp = LocalDate.MAX;
                        else
                            l??pp = LocalDate.of(Integer.parseInt(kuup??evad[2]), Integer.parseInt(kuup??evad[1]), Integer.parseInt(kuup??evad[0]));
                    }
                }
            }
            if (laenutatud && laenutaja != null && triipkood != null) {
                Tehnika tehnika = Peaklass.inventar.getTehnika(triipkood);
                Laenutus laenutus = new Laenutus(laenutaja, tehnika, algus, l??pp);
                laenutus.setKohtFailis(rida.getRowNum());
                tehnika.lisaLaenutus(laenutus);
                laenutused.add(laenutus);
            }
        }
        return laenutused;

    }

    static String formatKuup??ev(Object kuup??ev) {
        String[] kuup??evT??kid = kuup??ev.toString().split("-");
        Collections.reverse(Arrays.asList(kuup??evT??kid));
        return String.join(".", kuup??evT??kid);
    }
}
