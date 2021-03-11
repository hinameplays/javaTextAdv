package game;

public class Spieler {
    
    public static Ort ort;

    public Spieler(Ort start) {
        ort = start;
    }

    public static boolean gehe(String richtung) {
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

}
