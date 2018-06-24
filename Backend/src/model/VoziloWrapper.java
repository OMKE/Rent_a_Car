package model;

import java.util.ArrayList;

public class VoziloWrapper {
    private ArrayList<Vozilo> vozila;

    public VoziloWrapper(ArrayList<Vozilo> vozila) {
        this.vozila = vozila;
    }
    public void add(Vozilo v){
        this.vozila.add(v);
    }

    public ArrayList<Vozilo> getVozila() {
        return vozila;
    }
    public VoziloWrapper() {
        super();
    }

    public void setVozila(ArrayList<Vozilo> vozila) {
        this.vozila = vozila;
    }
}
