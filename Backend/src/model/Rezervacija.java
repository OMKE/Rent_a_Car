package model;





public class Rezervacija {
    Korisnik korisnik;
    String pocetakRezervacije;
    String krajRezervacije;
    Vozilo rezervisanoVozilo;

    public Rezervacija(){
        super();
    }
    public Rezervacija(Korisnik korisnik, String pocetakRezervacije, String krajRezervacije, Vozilo rezervisanoVozilo) {
        this.korisnik = korisnik;
        this.pocetakRezervacije = pocetakRezervacije;
        this.krajRezervacije = krajRezervacije;
        this.rezervisanoVozilo = rezervisanoVozilo;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public String getPocetakRezervacije() {
        return pocetakRezervacije;
    }

    public void setPocetakRezervacije(String pocetakRezervacije) {
        this.pocetakRezervacije = pocetakRezervacije;
    }

    public String getKrajRezervacije() {
        return krajRezervacije;
    }

    public void setKrajRezervacije(String krajRezervacije) {
        this.krajRezervacije = krajRezervacije;
    }



    public Vozilo getRezervisanoVozilo() {
        return rezervisanoVozilo;
    }

    public void setRezervisanoVozilo(Vozilo rezervisanoVozilo) {
        this.rezervisanoVozilo = rezervisanoVozilo;
    }
}
