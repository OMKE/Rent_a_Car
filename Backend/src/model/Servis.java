package model;

public class Servis {
    String tipServisa;
    String datumServisa;
    Vozilo vozilo;
    double kilometriPrijeServisa;

    public Servis(String tipServisa, String datumServisa, Vozilo vozilo, double kilometriPrijeServisa) {
        this.tipServisa = tipServisa;
        this.datumServisa = datumServisa;
        this.vozilo = vozilo;
        this.kilometriPrijeServisa = kilometriPrijeServisa;
    }

    public String getTipServisa() {
        return tipServisa;
    }

    public void setTipServisa(String tipServisa) {
        this.tipServisa = tipServisa;
    }

    public String getDatumServisa() {
        return datumServisa;
    }

    public void setDatumServisa(String datumServisa) {
        this.datumServisa = datumServisa;
    }

    public Vozilo getVozilo() {
        return vozilo;
    }

    public void setVozilo(Vozilo vozilo) {
        this.vozilo = vozilo;
    }

    public double getKilometriPrijeServisa() {
        return kilometriPrijeServisa;
    }

    public void setKilometriPrijeServisa(double kilometriPrijeServisa) {
        this.kilometriPrijeServisa = kilometriPrijeServisa;
    }
}
