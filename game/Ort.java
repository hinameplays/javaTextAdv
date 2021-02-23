package game;

public class Ort {
    String name, beschreibung;
    Ort l,r,o,u;

    public Ort (String n, String b) {
        this.name = n;
        this.beschreibung = b;
    }

    public void baueWeg(String richtung, Ort or) {
        switch (richtung) {
            case "links":
                this.l = or;
                or.r = this;
                break;
            case "rechts":
                this.r = or;
                or.l = this;
                break;
            case "oben":
                this.o = or;
                or.u = this;
                break;
            case "unten":
                this.u = or;
                or.o = this;
                break;
            default:
                break;
        }
    }

    public Ort getNachbarort(String richtung) {
        switch (richtung) {
            case "links":
                return this.l;
            case "rechts":
                return this.r;
            case "oben":
                return this.o;
            case "unten":
                return this.u;
            default:
                return null;
        }
    }

    public String Beschreibung() {
        return beschreibung;
    }
}
