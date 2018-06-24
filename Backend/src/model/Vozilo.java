package model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PutnickoVozilo.class, name = "putnicko"),
        @JsonSubTypes.Type(value = TeretnoVozilo.class, name = "teretno"),
        @JsonSubTypes.Type(value = Bicikl.class, name = "bicikl")
})

public abstract class Vozilo{

    private String imeVozila,
            registracioniBroj;
    private Gorivo gorivo;
    private double cijenaServisa,
            cijenaIznajmljivanja,
            potrosnjaGoriva,
            predjenoKM,
            KmDoServisa;
    private int brojSjedista,
            brojVrata;




    public Vozilo(){
        super();
    }

    public Vozilo(String imeVozila, String registracioniBroj, Gorivo gorivo, double cijenaServisa, double cijenaIznajmljivanja, double potrosnjaGoriva, double predjenoKM, double kmDoServisa, int brojSjedista, int brojVrata) {
        this.imeVozila = imeVozila;
        this.registracioniBroj = registracioniBroj;
        this.gorivo = gorivo;
        this.cijenaServisa = cijenaServisa;
        this.cijenaIznajmljivanja = cijenaIznajmljivanja;
        this.potrosnjaGoriva = potrosnjaGoriva;
        this.predjenoKM = predjenoKM;
        KmDoServisa = kmDoServisa;
        this.brojSjedista = brojSjedista;
        this.brojVrata = brojVrata;
    }

    public String getImeVozila() {
        return imeVozila;
    }

    public void setImeVozila(String imeVozila) {
        this.imeVozila = imeVozila;
    }

    public String getRegistracioniBroj() {
        return registracioniBroj;
    }

    public void setRegistracioniBroj(String registracioniBroj) {
        this.registracioniBroj = registracioniBroj;
    }

    public Gorivo getGorivo() {
        return gorivo;
    }

    public void setGorivo(Gorivo gorivo) {
        this.gorivo = gorivo;
    }

    public double getCijenaServisa() {
        return cijenaServisa;
    }

    public void setCijenaServisa(double cijenaServisa) {
        this.cijenaServisa = cijenaServisa;
    }

    public double getCijenaIznajmljivanja() {
        return cijenaIznajmljivanja;
    }

    public void setCijenaIznajmljivanja(double cijenaIznajmljivanja) {
        this.cijenaIznajmljivanja = cijenaIznajmljivanja;
    }

    public double getPotrosnjaGoriva() {
        return potrosnjaGoriva;
    }

    public void setPotrosnjaGoriva(double potrosnjaGoriva) {
        this.potrosnjaGoriva = potrosnjaGoriva;
    }

    public double getPredjenoKM() {
        return predjenoKM;
    }

    public void setPredjenoKM(double predjenoKM) {
        this.predjenoKM = predjenoKM;
    }

    public double getKmDoServisa() {
        return KmDoServisa;
    }

    public void setKmDoServisa(double kmDoServisa) {
        KmDoServisa = kmDoServisa;
    }

    public int getBrojSjedista() {
        return brojSjedista;
    }

    public void setBrojSjedista(int brojSjedista) {
        this.brojSjedista = brojSjedista;
    }

    public int getBrojVrata() {
        return brojVrata;
    }

    public void setBrojVrata(int brojVrata) {
        this.brojVrata = brojVrata;
    }
}
