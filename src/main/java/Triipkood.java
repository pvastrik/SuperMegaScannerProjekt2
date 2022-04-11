import java.io.Serializable;
import java.util.Objects;

class Triipkood implements Serializable {
    private String kood;

    Triipkood(String triipkood) {
//        this.unikaalneKood = triipkood.substring(2);
//        this.esemeKood = triipkood.substring(0,2);
        this.kood = triipkood;
    }

    String getKood() {
        return kood;
    }

    @Override
    public String toString() {
        return kood;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triipkood triipkood = (Triipkood) o;
        return Objects.equals(kood, triipkood.kood);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kood);
    }
}
