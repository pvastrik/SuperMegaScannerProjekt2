import com.opencsv.CSVWriter;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.time.LocalDate;
import java.util.Scanner;

class Peaklass {
    public static void main(String[] args) throws IOException, ClassNotFoundException {


        //KUI UUENDADA KLASSE VÕI INVENTARI FAILI, SIIS PEAB SELLE UUESTI TEGEMA
        //Inventar inventar = loeInventarCSV("G:\\Shared drives\\LTAT - ati.comp\\inventar.csv");

        Inventar inventar = loeInventarXLSX("C:\\Users\\priid\\OneDrive\\inventar.xlsx");

        //Inventar inventar = loeInventarTXT("file.txt");

        Scanner triipkoodiLugeja = new Scanner(System.in);
        while (true) {
            kontrolliTehnikaAjalugu(triipkoodiLugeja, inventar);

            teostaLaenutus(triipkoodiLugeja, inventar);

        }

        /*KUI MÜNDA KLASSI MUUTA SIIS PEAB UUESTI JOOKSUTAMA VÄLJAKOMMENTEERITUD OSA!!!!!!!!!!!!!*/
//        Inventar inventar = new Inventar("inventar.csv");
//        System.out.println(inventar);
////                Scanner triipkoodiLugeja = new Scanner(System.in);
////                while (true) {
////                    System.out.println("Loe kood: ");
////                    Triipkood kood = new Triipkood(triipkoodiLugeja.nextLine());
////
////                }
//        FileOutputStream file = null;
//        try {
//            file = new FileOutputStream("file.txt");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        // Creates an ObjectOutputStream
//        ObjectOutputStream output = null;
//        try {
//            output = new ObjectOutputStream(file);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // writes objects to output stream
//        try {
//            output.writeObject(inventar);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        /*file.txt-st input */
//        FileInputStream fileStream = null;
//        try {
//            fileStream = new FileInputStream("file.txt");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        // Creating an object input stream
//        ObjectInputStream objStream = null;
//        try {
//            objStream = new ObjectInputStream(fileStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        //Using the readInt() method
//        inventar = null;
//        try {
//            inventar = (Inventar) objStream.readObject();
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    static Inventar loeInventarCSV(String failinimi) throws IOException {
        return new Inventar(failinimi);
    }

    static Inventar loeInventarXLSX(String failinimi) throws IOException {return new Inventar(failinimi);}

    static Inventar loeInventarTXT(String failinimi) throws IOException, ClassNotFoundException {
        FileInputStream fileStream = null;
        fileStream = new FileInputStream(failinimi);

        // Creating an object input stream
        ObjectInputStream objStream = null;
        objStream = new ObjectInputStream(fileStream);

        //Using the readObject() method
        return (Inventar) objStream.readObject();

    }

    static void salvestaObjektFaili(Object o, String failinimi) throws IOException {
        FileOutputStream file = null;
        file = new FileOutputStream(failinimi);

        // Creates an ObjectOutputStream
        ObjectOutputStream output = null;

        output = new ObjectOutputStream(file);

        // writes objects to output stream

        output.writeObject(o);

    }

    static void teostaLaenutus(Scanner triipkoodiLugeja, Inventar inventar) throws IOException {
        System.out.print("Sisesta eesnimi, perenimi, isikukood: ");
        String input = triipkoodiLugeja.nextLine();
        String[] andmed = input.split(", ");
        Laenutaja laenutaja = otsiLaenutajat(andmed[2], inventar);

        if (laenutaja == null) {
            laenutaja = new Laenutaja(andmed[0], andmed[1], andmed[2]);
        }


        System.out.print("Loe kood: ");
        String kood = triipkoodiLugeja.nextLine();

        if (kood.equals("")) {
            salvestaObjektFaili(inventar, "file.txt");
            return;
        }

        Triipkood triipkood = new Triipkood(kood);
        Tehnika tehnika = inventar.getTehnika(triipkood);

        System.out.println("Sisesta lõppkuupäev: ");
        String[] kuupäev = triipkoodiLugeja.nextLine().split("/");

            LocalDate dateLaenutus = LocalDate.of(Integer.parseInt(kuupäev[2]), Integer.parseInt(kuupäev[1]), Integer.parseInt(kuupäev[0]));
            Laenutus uusLaenutus = new Laenutus(laenutaja, tehnika, LocalDate.now(), dateLaenutus);
            laenutaja.lisaLaenutus(uusLaenutus);
            tehnika.lisaLaenutus(uusLaenutus);
            inventar.lisaLaenutaja(laenutaja);
            //kirjutaLaenutusFaili(uusLaenutus, "resources\\laenutused.csv");
            kirjutaLaenutusCloudi(uusLaenutus, "C:\\Users\\priid\\OneDrive\\laenutused.xlsx");

    }

    static Laenutaja otsiLaenutajat(String isikukood, Inventar inventar) {
        for (Laenutaja l : inventar.getLaenutajad()) {
            if (l.getIsikukood().equals(isikukood)) {
                return l;
            }
        }
        return null;
    }

    static void kontrolliTehnikaAjalugu(Scanner triipkoodiLugeja, Inventar inventar) {
        System.out.println("Sisesta kood: ");
        Triipkood kood = new Triipkood(triipkoodiLugeja.nextLine());
        Tehnika tehnika = inventar.getTehnika(kood);

        System.out.println(tehnika.getAjalugu());

        System.out.println("Sisesta laenutaja, kelle andmeid tahad (isikukood): ");
        String isikukood = triipkoodiLugeja.nextLine();
        Laenutaja laenutaja = otsiLaenutajat(isikukood, inventar);
        if(laenutaja!=null) {
            System.out.println(laenutaja.getLaenutused());
        }
    }

    static void kirjutaLaenutusFaili(Laenutus laenutus, String failinimi) throws IOException {
        File file = new File(failinimi);
        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file, true);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            // add data to csv
            Laenutaja laenutaja = laenutus.getLaenutaja();
            Tehnika tehnika = laenutus.getTehnika();
            String[] data1 = {tehnika.getKirjeldus(), laenutaja.getEesnimi() + " " + laenutaja.getPerenimi(), laenutus.getAlgus().toString(), laenutus.getLopp().toString()};
            writer.writeNext(data1);
            writer.close();
        }  catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    static void kirjutaLaenutusCloudi(Laenutus laenutus, String failinimi) throws IOException {
        File fail = new File(failinimi);
        Tehnika toode = laenutus.getTehnika();
        Laenutaja laenutaja = laenutus.getLaenutaja();
        Object[] laenutuseInfo = {toode.getKirjeldus(), laenutaja.getEesnimi()+" "+laenutaja.getPerenimi(), laenutus.getAlgus(), laenutus.getLopp()};

        try {
            FileInputStream inputStream = new FileInputStream(fail);
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet leht = workbook.getSheetAt(0);
            int ridu = leht.getLastRowNum();

            Row rida = leht.createRow(++ridu);

            int tulp = 0;
            for (Object info : laenutuseInfo) {
                Cell kast = rida.createCell(tulp++);
                if(info instanceof String) kast.setCellValue((String) info);
                else if(info instanceof LocalDate) {
                    kast.setCellValue( info.toString());
                }
            }
            inputStream.close();


            FileOutputStream out = new FileOutputStream("C:\\Users\\priid\\OneDrive\\laenutused.xlsx");
            workbook.write(out);

            workbook.close();
            out.close();
            System.out.println("Kirjutatud");
        } catch (IOException | EncryptedDocumentException e) {
            e.printStackTrace();
        }
    }
}
