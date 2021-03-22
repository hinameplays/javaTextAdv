package game;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import game.lib_custom.json.JSONArray;
import game.lib_custom.json.JSONObject;
import game.lib_custom.json.JSONTokener;

public class Spiel {
    Ort start, ziel;
    Ort[] Orte;
    Spieler s;
    static File initPath = new File("game\\init.json");
    static File savePath = new File("game\\data.json");
    static Boolean closeable;
    

    public Spiel() {

        Ort[] temp = { new Ort("No1", "b", false, true, 1), new Ort("No2", "B", true, false, 2) }; //initialiser-declaration umgehen
        Orte = temp;


        Orte[0].baueWeg("oben", Orte[1]);
        start = Orte[0];
        ziel = Orte[1];

        this.rebuild();

        s = new Spieler(start);
        closeable = false;


    }

    public void spielen() {
        Scanner sc = new Scanner(System.in);
        while (s.ort != ziel) {
            System.out.println("Du befindest dich hier: " + s.ort.name);
            System.out.println("Wohin möchtest du gehen?");
            while (!s.gehe(sc.next())) {
                System.out.println("Ungültige Eingabe, bitte wiederholen.");
            }
        }
        this.save();
        System.out.println("Gz; du bist am Ziel!");
        sc.close();
    }

    public Ort getById(int id) {
        for (Ort o : Orte) {
            if (o.id == id) return o;
        }
        return null;
    }

    public void rebuild() {
        
        FileReader inputStream;
        try {
            inputStream = new FileReader(savePath);
            JSONTokener x = new JSONTokener(inputStream);
            JSONObject spiel = new JSONObject(x);

            JSONArray locations = new JSONArray(spiel.get("locations").toString());

            Orte = new Ort[locations.length()];

            for (int i = 0; i<locations.length(); i++) {
                JSONObject temp = new JSONObject(locations.get(i).toString());
                Ort ort = new Ort(temp.getString("beschreibung"), temp.getString("name"), temp.getBoolean("isStart"), temp.getBoolean("isGoal"), temp.getInt("id"));
                
                Orte[i] = ort;
            }

            JSONObject player = new JSONObject(spiel.get("player").toString());
            s = new Spieler(getById(player.getInt("location"))); 

            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public void save() {
        FileWriter file;

        try {
            file = new FileWriter(savePath);

            JSONArray locations = new JSONArray();

            for (Ort o: Orte) {
                JSONObject temp = new JSONObject(o);
                temp.put("id", o.id);
                temp.put("name", o.name);
                temp.put("beschreibung", o.beschreibung);
                temp.put("l", (o.l != null ? o.l.id : null));
                temp.put("r", (o.r != null ? o.r.id : null));
                temp.put("o", (o.o != null ? o.o.id : null));
                temp.put("u", (o.u != null ? o.u.id : null));
                temp.put("isStart", o.isStart);
                temp.put("isGoal", o.isGoal);
                Object[] t = {o.Item1, o.Item2, o.Item3};
                temp.put("items", new JSONArray(t));

                locations.put(temp);                
            }

            JSONObject player = new JSONObject();

            Item[] t = Spieler.items;
            player.put("location", (Spieler.ort != null ? Spieler.ort.id : null));
            player.put("items", new JSONArray(t));
            
            JSONObject spiel = new JSONObject();
            spiel.put("player", player);
            spiel.put("locations", locations);

            spiel.write(file, 4, 0);

            file.flush();
            file.close();
            
            closeable = true;
        } catch (Exception e) {
            
            System.out.println(e);
            
            closeable = false;
        }         
    }
}
