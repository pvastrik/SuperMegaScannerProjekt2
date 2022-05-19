import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class Tehnika implements Serializable {
    private Triipkood triipkood;
    private String kirjeldus;
    private List<Laenutus> ajalugu;
    private boolean kasOlemas;

    Tehnika(Triipkood triipkood, String kirjeldus) {
        this.triipkood = triipkood;
        this.kirjeldus = kirjeldus;
        this.ajalugu = new ArrayList<>();
        this.kasOlemas = true;
    }

    public String getKirjeldus() {
        return kirjeldus;
    }

    Triipkood getTriipkood() {
        return triipkood;
    }

    void lisaLaenutus(Laenutus laenutus) {
        ajalugu.add(laenutus);
        kasOlemas = false;
    }

    void tagasta() throws IOException {
        if (!kasOlemas) {
            Laenutus viimaneLaenutus = ajalugu.get(ajalugu.size()-1);
            viimaneLaenutus.setLopetatud(LocalDate.now());
            kasOlemas = true;
            Failid.lisaTagastamineCloudi(viimaneLaenutus);
        }
    }

    @Override
    public String toString() {
        return "Tehnika{" +
                "triipkood=" + triipkood +
                ", kirjeldus='" + kirjeldus + '\'' +
                "}";
    }
}
