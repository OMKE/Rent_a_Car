package model;





public class Sluzbenik extends Korisnik {

    public Sluzbenik(){
        super();
    }
    public Sluzbenik(String korisnickoIme, String lozinka, String jmbg, String imeIPrezime) {
        super(korisnickoIme, lozinka, jmbg, imeIPrezime, true);
    }

}
