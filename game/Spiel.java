package game;


public class Spiel {
    Ort start, ziel;
    Spieler s;
    final String dataPath = "data.json";

    public Spiel() {
        
        //Ort[] Orte = {};
        s = new Spieler(start);
        
    }

    public void spielen() {
        while (s.ort !=ziel) {
            
        }
    }
}
