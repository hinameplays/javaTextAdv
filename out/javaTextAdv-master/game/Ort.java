package game;

public class Ort {
    String name, beschreibung;
    Ort l,r,o,u; //Nachbarorte
    Boolean isGoal, isStart, isLocked; 
    Item Item1, Item2, Item3, UnlockItem;
    int id; 

    public Ort (String n, String b, Boolean s, Boolean g, Boolean l, int i) {
        this.name = n;
        this.beschreibung = b;

        this.isGoal = g;
        this.isStart = s;

        id = i;
    }

    // switch-statement zum Wegebau; deprecated
    public void baueWeg(String richtung, Ort or) {
        switch (richtung) {
            case "links", "l", "Links", "L":
                this.l = or;
                or.r = this;
                break;
            case "rechts", "r", "Rechts", "R":
                this.r = or;
                or.l = this;
                break;
            case "oben", "o", "Oben", "O":
                this.o = or;
                or.u = this;
                break;
            case "unten", "u", "Unten", "U":
                this.u = or;
                or.o = this;
                break;
            default:
                break;
        }
    }

    public Ort getNachbarort(String richtung) {
        switch (richtung) {
            case "links", "l", "Links", "L":
                return this.l;
            case "rechts", "r", "Rechts", "R":
                return this.r;
            case "oben", "o", "Oben", "O":
                return this.o;
            case "unten", "u", "Unten", "U":
                return this.u;
            default:
                return null;
        }
    }

    public void printOptions() {
        // druckt alle Möglichketien für Wege von diesem Ort an aus
        System.out.println("Von hieraus gibt es Wege nach: ");
        if (getNachbarort("o")!=null) System.out.println("Oben: "+getNachbarort("o").name);
        if (getNachbarort("r")!=null) System.out.println("Rechts: "+getNachbarort("r").name);
        if (getNachbarort("l")!=null) System.out.println("Links: "+getNachbarort("l").name);
        if (getNachbarort("u")!=null) System.out.println("Unten: "+getNachbarort("u").name+"\n");
    }

    public Boolean give(Item j) {
        // Methode um Item an diesen Ort zu übergegeben / hinterlegen
        if (Item1 == null) {
            Item1 = j; 
            return true;
        } else if (Item2 == null) {
            Item2 = j; 
            return true;
        } else if (Item3 == null) {
            Item3 = j; 
            return true;
        } else return false;
    }

    public Boolean remove (Item j) {
        // Item von diesem Ort entfernen
        if (Item1 == j) {
            Item1 = null; 
            return true;
        } else if (Item2 == j) {
            Item2 = null; 
            return true;
        } else if (Item3 == j) {
            Item3 = null; 
            return true;
        } else return false;
    }

    public Item get(Item j) { 
        // item aus diesem Ort übergeben
        if (remove(j)) return j;
        else return null;
    }
}
