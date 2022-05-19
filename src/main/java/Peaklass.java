import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

class Peaklass {
    private static final String filepath = Failid.filepath;
    //Inventar inventar = loeInventarCSV("G:\\Shared drives\\LTAT - ati.comp\\inventar.csv");

    //KUI UUENDADA KLASSE VÕI INVENTARI FAILI, SIIS PEAB SELLE UUESTI TEGEMA
    static Inventar inventar;

    static {
        try {
            inventar = new Inventar();
            //inventar = Failid.loeInventarTXT("file.txt");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {


        gui.alge();
        Scanner triipkoodiLugeja = new Scanner(System.in);
        while (true) {
            //kontrolliTehnikaAjalugu(triipkoodiLugeja, inventar);
//            String inputKood = triipkoodiLugeja.nextLine();
//            Tehnika skännitudEse = inventar.getTehnika(new Triipkood(inputKood));
//            if (skännitudEse.kasOlemas()) teostaLaenutus(triipkoodiLugeja, skännitudEse);
//            else skännitudEse.tagasta();


        }

    }

    static void teostaLaenutus(Scanner triipkoodiLugeja, Tehnika tehnika) throws IOException {
        System.out.print("Sisesta eesnimi, perenimi, isikukood: ");
        String input = triipkoodiLugeja.nextLine();
        String[] andmed = input.split(", ");
        Laenutaja laenutaja = inventar.otsiLaenutajat(andmed[2]);

        if (laenutaja == null) {
            laenutaja = new Laenutaja(andmed[0], andmed[1], andmed[2]);
        }

        System.out.println("Sisesta lõppkuupäev: ");
        String[] kuupäev = triipkoodiLugeja.nextLine().split("/|\\.|-");

        LocalDate dateLaenutus = LocalDate.of(Integer.parseInt(kuupäev[2]), Integer.parseInt(kuupäev[1]), Integer.parseInt(kuupäev[0]));
        Laenutus uusLaenutus = new Laenutus(laenutaja, tehnika, LocalDate.now(), dateLaenutus);
        inventar.lisaLaenutaja(laenutaja);
        inventar.lisaLaenutus(uusLaenutus);
        Failid.kirjutaLaenutusCloudi(uusLaenutus);
        Failid.salvestaObjektFaili(inventar);

    }


    static void kontrolliTehnikaAjalugu(Scanner triipkoodiLugeja, Inventar inventar) {
        System.out.println("Sisesta kood: ");
        Triipkood kood = new Triipkood(triipkoodiLugeja.nextLine());
        Tehnika tehnika = inventar.getTehnika(kood);

        System.out.println(tehnika.getAjalugu());

        System.out.println("Sisesta laenutaja, kelle andmeid tahad (isikukood): ");
        String isikukood = triipkoodiLugeja.nextLine();
        Laenutaja laenutaja = inventar.otsiLaenutajat(isikukood);
        if (laenutaja != null) {
            System.out.println(laenutaja.getLaenutused());
        }
    }
}
