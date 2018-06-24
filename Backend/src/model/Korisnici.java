package model;

import java.util.ArrayList;

public class Korisnici {
	private ArrayList<Korisnik> ks;

	public ArrayList<Korisnik> getKs() {
		return ks;
	}



	public void setKs(ArrayList<Korisnik> ks) {
		this.ks = ks;
	}

	@Override
	public String toString() {
		return "Korisnici [ks=" + ks + "]";
	}

	public Korisnici(ArrayList<Korisnik> ks) {
		super();
		this.ks = ks;
	}

	public Korisnici() {
		super();
	}
	
	public void add(Korisnik k) {
		this.ks.add(k);
	}
}
