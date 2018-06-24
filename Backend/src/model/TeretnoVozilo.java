package model;



import java.util.ArrayList;



public class TeretnoVozilo extends Vozilo {
    private double maksimalnaTezina,
                    visina;
    private ArrayList<Servis> servisi;


    public TeretnoVozilo(){
        super();
    }
    public TeretnoVozilo(String imeVozila, String registracioniBroj, Gorivo gorivo, double cijenaServisa, double cijenaIznajmljivanja, double potrosnjaGoriva, double predjenoKM, double kmDoServisa, int brojSjedista, int brojVrata, double maksimalnaTezina, double visina) {
        super(imeVozila, registracioniBroj, gorivo, cijenaServisa, cijenaIznajmljivanja, potrosnjaGoriva, predjenoKM, kmDoServisa, brojSjedista, brojVrata);
        this.maksimalnaTezina = maksimalnaTezina;
        this.visina = visina;
        this.servisi = new ArrayList<Servis>();
    }

    public double getMaksimalnaTezina() {
        return maksimalnaTezina;
    }

    public void setMaksimalnaTezina(double maksimalnaTezina) {
        this.maksimalnaTezina = maksimalnaTezina;
    }

    public double getVisina() {
        return visina;
    }

    public void setVisina(double visina) {
        this.visina = visina;
    }
}
