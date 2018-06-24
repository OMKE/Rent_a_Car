package model;





public class Iznajmljivac extends Korisnik {
    private String brojTelefona,
                   drzavljanstvo;

    public Iznajmljivac(){
        super();
    }
    public Iznajmljivac(String korisnickoIme, String lozinka, String jmbg, String imeIPrezime, String brojTelefona, String drzavljanstvo) {
        super(korisnickoIme, lozinka, jmbg, imeIPrezime, false);
        this.brojTelefona = brojTelefona;
        this.drzavljanstvo = drzavljanstvo;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }

    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona = brojTelefona;
    }

    public String getDrzavljanstvo() {
        return drzavljanstvo;
    }

    public void setDrzavljanstvo(String drzavljanstvo) {
        this.drzavljanstvo = drzavljanstvo;
    }
	@Override
	public String toString() {
		return super.toString() + "Iznajmljivac [brojTelefona=" + brojTelefona + ", drzavljanstvo=" + drzavljanstvo + "]";
	}
    
    
}
