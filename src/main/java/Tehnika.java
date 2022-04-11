import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class Tehnika implements Serializable {
    private Triipkood triipkood;
    private String kirjeldus;
    private List<Laenutus> ajalugu;

    Tehnika(Triipkood triipkood, String kirjeldus) {
        this.triipkood = triipkood;
        this.kirjeldus = kirjeldus;
        this.ajalugu = new ArrayList<>();
    }

    public String getKirjeldus() {
        return kirjeldus;
    }

    Triipkood getTriipkood() {
        return triipkood;
    }

    List<Laenutus> getAjalugu() {
        return ajalugu;
    }

    void lisaLaenutus(Laenutus laenutus) {
        ajalugu.add(laenutus);
    }

    @Override
    public String toString() {
        return "Tehnika{" +
                "triipkood=" + triipkood +
                ", kirjeldus='" + kirjeldus + '\'' +
                "}";
    }
}
