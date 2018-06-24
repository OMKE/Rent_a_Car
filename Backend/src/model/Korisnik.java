package model;

import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.ArrayList;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
    @JsonSubTypes({
            @Type(value = Sluzbenik.class, name = "sluzbenik"),
            @Type(value = Iznajmljivac.class, name = "iznajmljivac")
    })

public abstract class Korisnik {
    @Override
	public String toString() {
		return "Korisnik [korisnickoIme=" + korisnickoIme + ", lozinka=" + lozinka + ", jmbg=" + jmbg + ", imeIPrezime="
				+ imeIPrezime + ", jeSluzbenik=" + jeSluzbenik + ", rezervacije=" + rezervacije + "]";
	}

	String korisnickoIme,
            lozinka,
            jmbg,
            imeIPrezime;
    boolean jeSluzbenik;
    private ArrayList<Rezervacija> rezervacije;

    public Korisnik(){
        super();
    }
    public Korisnik(String korisnickoIme, String lozinka, String jmbg, String imeIPrezime, boolean jeSluzbenik) {
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
        this.jmbg = jmbg;
        this.imeIPrezime = imeIPrezime;
        this.jeSluzbenik = jeSluzbenik;
        this.rezervacije = new ArrayList<>();
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getImeIPrezime() {
        return imeIPrezime;
    }

    public void setImeIPrezime(String imeIPrezime) {
        this.imeIPrezime = imeIPrezime;
    }

    public boolean isJeSluzbenik() {
        return jeSluzbenik;
    }

    public void setJeSluzbenik(boolean jeSluzbenik) {
        this.jeSluzbenik = jeSluzbenik;
    }
}
