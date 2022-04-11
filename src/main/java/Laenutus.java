import java.io.Serializable;
import java.time.LocalDate;

class Laenutus implements Serializable {
    private Laenutaja laenutaja;
    private Tehnika tehnika;
    private LocalDate algus;
    private LocalDate lopp;
    private LocalDate lopetatud;
    private String markused;

    Laenutus(Laenutaja laenutaja, Tehnika tehnika, LocalDate algus, LocalDate lopp) {
        this.laenutaja = laenutaja;
        this.tehnika = tehnika;
        this.algus = algus;
        this.lopp = lopp;
    }

    Laenutaja getLaenutaja() {
        return laenutaja;
    }

    Tehnika getTehnika() {
        return tehnika;
    }

    LocalDate getAlgus() {
        return algus;
    }

    LocalDate getLopp() {
        return lopp;
    }

    LocalDate getLopetatud() {
        return lopetatud;
    }

    String getMarkused() {
        return markused;
    }

    void setLopetatud(LocalDate lopetatud) {
        this.lopetatud = lopetatud;
    }

    void setMarkused(String markused) {
        this.markused = markused;
    }

    @Override
    public String toString() {
        return "Laenutus{" +
                "laenutaja=" + laenutaja +
                ", tehnika=" + tehnika +
                ", algus=" + algus.toString() +
                ", lopp=" + lopp.toString() +
                '}';
    }
}
