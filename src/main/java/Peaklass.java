import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

class Peaklass {
    private static final String filepath = Failid.filepath;
    static Inventar inventar;

    static {
        try {
            inventar = new Inventar();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        gui.alge();

    }


}
