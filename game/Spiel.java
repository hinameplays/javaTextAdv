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
    Item[] items;
    Spieler s;
    static File initPath = new File("game\\init.json");
    static File savePath = new File("game\\data.json");
    static Boolean closeable;
    

    public Spiel() {

        if (savePath.exists()) {
            this.rebuild(savePath);
        } else {
            this.rebuild(initPath);
        }

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

    public Ort getOrtById(int id) {
        
        for (Ort o : Orte) {
            if (o.id == id) return o;
        }
        return null;
        
    }

    public Item getItemById(int id) {
        
        for (Item i : items) {
            if (i.id == id) return i;
        }
        return null;
        
    }

    public void rebuild(File f) {
        
        FileReader inputStream;
        try {
            inputStream = new FileReader(f);
            JSONTokener x = new JSONTokener(inputStream);
            JSONObject spiel = new JSONObject(x);

            JSONArray locations = new JSONArray(spiel.get("locations").toString());

            Orte = new Ort[locations.length()];

            for (int i = 0; i<locations.length(); i++) {
                JSONObject temp = new JSONObject(locations.get(i).toString());
                Ort ort = new Ort(temp.getString("beschreibung"), temp.getString("name"), temp.getBoolean("isStart"), temp.getBoolean("isGoal"), temp.getBoolean("isLocked"), temp.getInt("id"));
                
                Orte[i] = ort;
            }

            JSONObject player = new JSONObject(spiel.get("player").toString());
            s = new Spieler(getOrtById(player.getInt("location"))); 

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
                temp.put("isLocked", o.isLocked);
                int[] t = {o.Item1 != null ? o.Item1.id : null, o.Item2 != null ? o.Item2.id : null, o.Item3 != null ? o.Item3.id : null,o.UnlockItem != null ? o.UnlockItem.id : null};
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
