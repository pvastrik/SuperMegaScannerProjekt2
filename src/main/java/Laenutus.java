import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

class Laenutus implements Serializable {
    private Laenutaja laenutaja;
    private Tehnika tehnika;
    private LocalDate algus;
    private LocalDate lopp;
    private LocalDate lopetatud;
    private String markused;
    private int kohtFailis;

    Laenutus(Laenutaja laenutaja, Tehnika tehnika, LocalDate algus, LocalDate lopp) {
        this.laenutaja = laenutaja;
        this.tehnika = tehnika;
        this.algus = algus;
        this.lopp = lopp;
        laenutaja.lisaLaenutus(this);
        tehnika.lisaLaenutus(this);
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

    int getKohtFailis() {
        return kohtFailis;
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

    void setKohtFailis(int kohtFailis) {
        this.kohtFailis = kohtFailis;
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
