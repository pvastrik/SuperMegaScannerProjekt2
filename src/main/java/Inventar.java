import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Inventar implements Serializable {
    private List<Tehnika> koguVara;
    private List<Laenutaja> laenutajad = new ArrayList<>();
    private List<Laenutus> laenutused = new ArrayList<>();

    Inventar() throws Exception {
        koguVara = Failid.looInventarXLSX();
    }

    Laenutus otsiLaenutust(String kood) {
        for (Laenutus l : laenutused) {
            if (Objects.equals(l.getTehnika().getTriipkood().getKood(), kood)) {
                return l;
            }
        }
        return null;
    }

    void setLaenutused(List<Laenutus> laenutused) {
        this.laenutused = laenutused;
    }

    Tehnika getTehnika(Triipkood triipkood) {
        for (Tehnika tehnika : koguVara) {
            if (triipkood.equals(tehnika.getTriipkood())) return tehnika;
        }
        throw new RuntimeException("Sellise koodiga tehnikat ei leitud.");
    }

    Tehnika getTehnika(String triipkood) {
        for (Tehnika tehnika : koguVara) {
            if (triipkood.equals(tehnika.getTriipkood().getKood())) return tehnika;
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
