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
    

    public Spiel() {

        if (savePath.exists()) {
            this.rebuild(savePath);
        } else {
            this.rebuild(initPath);
        }

    }

    public void spielen() { 

        Scanner sc = new Scanner(System.in);
        Boolean save = true;

        while (s.ort != ziel) {

            if (save) save();
            save = !save;

            System.out.println("Du befindest dich hier: "+ s.ort.name);
            System.out.println(s.ort.beschreibung+"\n");
            System.out.println("Hier gibt es folgende Items:");
            System.out.println(s.ort.Item1 != null ? s.ort.Item1.name : "Nichts");
            System.out.println(s.ort.Item2 != null ? s.ort.Item2.name : "Nichts");
            System.out.println(s.ort.Item3 != null ? s.ort.Item3.name : "Nichts"+"\n");
            System.out.println("Was möchtest du jetzt tun?\n");

            String in = sc.next();
            switch (in) {
                case "exit", "Exit", "e", "E":
                    this.save();
                    return;
                case "save", "Save", "s", "S":
                    this.save();
                    break;
                case "items", "list", "l", "i":
                    System.out.println("Dein Inventar: \nItem 1: "+s.items[0].name+"\nItem 2: "+s.items[1].name+"\nItem 3: "+s.items[2].name+"\nItem 4: "+s.items[3].name+"\nItem 5: "+s.items[4].name+"\n");
                    break;
                case "help", "h", "/help", "/h", "Help", "H", "Hilfe":
                    System.out.println("Liste der möglichen Befehle:\n");
                    break;
                case "aufheben", "get", "g", "a":
                    System.out.println("Welches Item möchtest du aufheben? [Gebe die Zahl an]");
                    switch (sc.next()) {
                        case "1":
                            s.give(s.ort.get(s.ort.Item1));
                            break;
                        case "2":
                            s.give(s.ort.get(s.ort.Item2));
                            break;
                        case "3":
                            s.give(s.ort.get(s.ort.Item3));
                            break;
                        default:
                            System.out.println("Bitte prüfe deine Eingabe und gebe den Befehl dann erneut ein.");
                    }
                default:
                    if (!s.gehe(in)) System.out.println("Eingabefehler: Bitte Eingabe überprüfen oder 'help' für eine Liste an Befehlen eingeben.");
            }

            if (dead()) return; 
        }

    }

    public boolean dead() {
        return false;
    }

    public Ort getOrtById(int id) {
        
        if (id == 0) return null;
        for (Ort o : Orte) {
            if (o.id == id) return o;
        }
        return null;
        
    }

    public Item getItemById(int id) {
        
        if (id == 0) return null;
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
            JSONArray inventory = new JSONArray(spiel.get("inventory").toString());

            items = new Item[inventory.length()];

            for (int i = 0; i<inventory.length(); i++) {
                JSONObject temp = new JSONObject(inventory.get(i).toString());
                Item item = new Item(temp.getString("name"), temp.getString("beschreibung"), temp.getInt("id"));

                items[i] = item;
            }

            Orte = new Ort[locations.length()];

            for (int i = 0; i<locations.length(); i++) {
                JSONObject temp = new JSONObject(locations.get(i).toString());
                Ort ort = new Ort(temp.getString("beschreibung"), temp.getString("name"), temp.getBoolean("isStart"), temp.getBoolean("isGoal"), false, temp.getInt("id"));
                JSONArray ite = new JSONArray(temp.get("items").toString());
                ort.Item1 = getItemById((int) ite.get(0));
                ort.Item2 = getItemById((int) ite.get(1));
                ort.Item3 = getItemById((int) ite.get(2));
                ort.UnlockItem = getItemById((int) ite.get(3));

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
            JSONArray inventory = new JSONArray();

            for (Ort o: Orte) {
                JSONObject temp = new JSONObject(o);
                temp.put("id", o.id);
                temp.put("name", o.name);
                temp.put("beschreibung", o.beschreibung);
                temp.put("l", (o.l != null ? o.l.id : 0));
                temp.put("r", (o.r != null ? o.r.id : 0));
                temp.put("o", (o.o != null ? o.o.id : 0));
                temp.put("u", (o.u != null ? o.u.id : 0));
                temp.put("isStart", o.isStart);
                temp.put("isGoal", o.isGoal);
                temp.put("isLocked", o.isLocked);
                int[] t = {o.Item1 != null ? o.Item1.id : 0, o.Item2 != null ? o.Item2.id : 0, o.Item3 != null ? o.Item3.id : 0, o.UnlockItem != null ? o.UnlockItem.id : 0};
                temp.put("items", new JSONArray(t));

                locations.put(temp);                
            }

            for (Item i : items) {
                JSONObject temp = new JSONObject();
                temp.put("id", i.id);
                temp.put("name", i.name);
                temp.put("beschreibung", i.beschreibung);

                inventory.put(temp);
            }

            JSONObject player = new JSONObject();
            player.put("location", (s.ort != null ? s.ort.id : 0));
            int[] t = {s.items[0] != null ? s.items[0].id : 0, s.items[1] != null ? s.items[1].id : 0, s.items[2] != null ? s.items[2].id : 0, s.items[3] != null ? s.items[3].id : 0, s.items[4] != null ? s.items[4].id : 0};
            player.put("items", new JSONArray(t));
            
            JSONObject spiel = new JSONObject();
            spiel.put("player", player);
            spiel.put("locations", locations);
            spiel.put("inventory", inventory);

            spiel.write(file, 4, 0);

            file.flush();
            file.close();

        } catch (Exception e) {
            System.out.println(e);  
            e.printStackTrace();
        }         
    }
}
