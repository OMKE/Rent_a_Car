package model;




import java.util.ArrayList;



public class PutnickoVozilo extends Vozilo {
    private ArrayList<Servis> servisi;



    public PutnickoVozilo(){
        super();
    }
    public PutnickoVozilo(String imeVozila, String registracioniBroj, Gorivo gorivo, double cijenaServisa, double cijenaIznajmljivanja, double potrosnjaGoriva, double predjenoKM, double kmDoServisa, int brojSjedista, int brojVrata) {
        super(imeVozila, registracioniBroj, gorivo, cijenaServisa, cijenaIznajmljivanja, potrosnjaGoriva, predjenoKM, kmDoServisa, brojSjedista, brojVrata);
        this.servisi = new ArrayList<Servis>();
    }
}
