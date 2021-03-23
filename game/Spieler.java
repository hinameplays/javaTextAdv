package game;

public class Spieler {
    
    public static Ort ort;
    public Item[] items = new Item[5];

    public Spieler(Ort start) {
        ort = start;
    }

    public boolean gehe(String richtung) {
        try {
            if (ort.getNachbarort(richtung)!=null) {
                ort = ort.getNachbarort(richtung);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean hasItem(Item i) {
        for (Item z : items) {
            if(z == i) {
                return true;
            }
        }
        return false;
    }

}
