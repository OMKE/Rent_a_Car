package model;



import java.util.ArrayList;



public class Bicikl extends Vozilo {
    private ArrayList<Servis> servisi;


    public Bicikl(){
        super();
    }
    public Bicikl(String imeVozila, String registracioniBroj, double cijenaServisa, double cijenaIznajmljivanja, double predjenoKM, double kmDoServisa, int brojSjedista) {
        super(imeVozila, registracioniBroj, new Gorivo("Ne trosi gorivo",0), cijenaServisa, cijenaIznajmljivanja, 0, predjenoKM, kmDoServisa, brojSjedista, 0);
        this.servisi = new ArrayList<Servis>();
    }
}
