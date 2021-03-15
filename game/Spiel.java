package game;

import java.util.Scanner;

import game.lib_custom.json.JSONArray;

public class Spiel {
    Ort start, ziel;
    Spieler s;
    final String dataPath = "data.json";

    public Spiel() {
        
        Ort[] Orte = readLocations();

        s = new Spieler(start);
        
    }

    public void spielen() {
        Scanner sc = new Scanner(System.in);
        while (s.ort !=ziel) {
            System.out.println("Du befindest dich hier: "+s.ort.name);
            System.out.println("Wohin möchtest du gehen?");
            while(!s.gehe(sc.next())) {
                System.out.println("Ungültige Eingabe, bitte wiederholen.");
            }
        }
        System.out.println("Gz; du bist am Ziel!");
        sc.close();
    }

    public Ort[] readLocations() {
        JSONArray j = new;
        
        return out;
    }
    
}
