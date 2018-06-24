package model;


import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
public class Gorivo {
    String tipGoriva;
    double cijenaGoriva;
// Ovako, ↓
    public Gorivo(){
        super();
    }
    public Gorivo(String tipGoriva, double cijenaGoriva) {
        this.tipGoriva = tipGoriva;
        this.cijenaGoriva = cijenaGoriva;
    }

    public String getTipGoriva() {
        return tipGoriva;
    }

    public void setTipGoriva(String tipGoriva) {
        this.tipGoriva = tipGoriva;
    }

    public double getCijenaGoriva() {
        return cijenaGoriva;
    }

    public void setCijenaGoriva(double cijenaGoriva) {
        this.cijenaGoriva = cijenaGoriva;
    }
}
