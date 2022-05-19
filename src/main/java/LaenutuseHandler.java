import java.time.LocalDate;

class LaenutuseHandler {
    private Tehnika tehnika;
    private Laenutaja laenutaja;
    private LocalDate kuupäev;

    LaenutuseHandler() {
    }

    void setTehnika(Tehnika tehnika) {
        this.tehnika = tehnika;
    }

    void setLaenutaja(Laenutaja laenutaja) {
        this.laenutaja = laenutaja;
    }

    void setKuupäev(LocalDate kuupäev) {
        this.kuupäev = kuupäev;
    }

    Laenutus looLaenutus() {
        return new Laenutus(laenutaja, tehnika, LocalDate.now(), kuupäev);
    }
}
