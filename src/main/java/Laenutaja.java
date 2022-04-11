import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class Laenutaja implements Serializable {
    private String eesnimi;
    private String perenimi;
    private String isikukood;
    private String email;
    private List<Laenutus> laenutused = new ArrayList<>();

    Laenutaja(String eesnimi, String perenimi, String isikukood) {
        this.eesnimi = eesnimi;
        this.perenimi = perenimi;
        this.isikukood = isikukood;
    }

    void lisaLaenutus(Laenutus laenutus) {
        laenutused.add(laenutus);
    }

    String getEesnimi() {
        return eesnimi;
    }

    String getPerenimi() {
        return perenimi;
    }

    String getIsikukood() {
        return isikukood;
    }

    String getEmail() {
        return email;
    }

    List<Laenutus> getLaenutused() {
        return laenutused;
    }

    void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Laenutaja{" +
                "eesnimi='" + eesnimi + '\'' +
                ", perenimi='" + perenimi + '\'' +
                ", isikukood='" + isikukood + '\'' +
                '}';
    }
}
