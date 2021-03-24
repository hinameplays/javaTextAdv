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
    Ort[] Orte;     // globales permanentes Array aller Orte
    Item[] items;   // globales permanentes Array aller Items
    Spieler s;
    int winCount;   // Anzahl der Siege, persistent
    static File initPath = new File("game\\init.json");     // Quellcode für Content
    static File savePath = new File("game\\save.json");     // gespeicherte Spielstände
    

    public Spiel() {

        // konstruiert Spiel mithilfe von Datei
        if (savePath.exists()) {
            this.rebuild(savePath);
        } else {
            this.rebuild(initPath);
        }

    }

    public void spielen() { 

        Scanner sc = new Scanner(System.in);
        Boolean save = true; // Speichervariable: Alle zwei Durchgänge wird gespeichert

        while (s.ort != ziel) {

            if (save) save();
            save = !save;   // Autospeichern

            Ort o = s.ort;

            System.out.println("---------------------------------------------------------------------------");
            System.out.println("Du befindest dich hier: "+ o.name+".");
            System.out.println(o.beschreibung+"\n");
            System.out.println("Hier gibt es folgende Items:");
            System.out.println("1: "+(o.Item1 != null ? o.Item1.name+"\n"+o.Item1.beschreibung : "Nichts"));
            System.out.println("2: "+(o.Item2 != null ? o.Item2.name+"\n"+o.Item2.beschreibung : "Nichts"));
            System.out.println("3: "+(o.Item3 != null ? o.Item3.name+"\n"+o.Item3.beschreibung : "Nichts")+"\n");
            o.printOptions();
            System.out.println("Was möchtest du jetzt tun?\n");
            System.out.println("---------------------------------------------------------------------------");

            String in = sc.next();
            switch (in) {
                case "exit", "Exit", "e", "E":          // Möglichkeit, Spiel zu beenden
                    this.save();
                    return;
                case "save", "Save", "s", "S":          // manuelles Speichern
                    this.save();
                    break;
                case "items", "List", "i", "list":      // eigenes Inventar betrachten
                    s.printInventory();
                    break;
                case "help", "h", "/help", "/h", "Help", "H", "Hilfe":      // Hilfe zu Spielbefehlen erhalten

                    System.out.println("Liste der möglichen Befehle:\n");
                    System.out.println("'h, Hilfe': Diese Hilfe anzeigen \n'e, Exit': Spiel verlassen \n's, Save': speichern\n'i, items, List': Items aufzählen\n'g, get': Item aufheben\n'Oben, o/ Rechts, r/ Links, l/ Unten, u': In Richtung gehen\n");
                    break;

                case "aufheben", "get", "g", "a":       // Item aufnehmen

                    System.out.println("Welches Item möchtest du aufheben? [Gebe die Zahl an]");
                    switch (sc.next()) {
                        case "1":
                            Item i = o.get(o.Item1);
                            s.give(i);
                            System.out.println("Du hast "+i.name+" erhalten.");
                            System.out.println(i.beschreibung);
                            break;
                        case "2":
                            Item j = o.get(o.Item2);
                            s.give(j);
                            System.out.println("Du hast "+j.name+" erhalten.");
                            System.out.println(j.beschreibung);
                            break;
                        case "3":
                            Item k = o.get(o.Item3);
                            s.give(k);
                            System.out.println("Du hast "+k.name+" erhalten.");
                            System.out.println(k.beschreibung);
                            break;
                        default:
                            System.out.println("Bitte prüfe deine Eingabe und gebe den Befehl dann erneut ein.");
                    }
                    break;
                default:                // falsche Eingaben Abfangen und Ortseingabe prüfen

                    if (!s.gehe(in)) System.out.println("Eingabefehler: Bitte Eingabe überprüfen oder 'help' für eine Liste an Befehlen eingeben.");
            }

            System.out.println("---------------------------------------------------------------------------\n\n\n\n");
           
            if (dead()) {               // schaut, ob Game Over
                sc.close();             
                System.out.println("Du bist gestorben. Starte das Spiel neu, um vom letzten Sicherungspunkt aus weiter zu spielen.");
                return; 
            }
        }

        sc.close();
        this.winCount ++;
        System.out.println("Herzlichen Glückwunsch, du bist am Ziel! Das Spiel wird sich jetzt automatisch resetten.");

        rebuild(initPath);  // Spiel zurücksetzen
        save();

    }

    public boolean dead() {
        if (s.ort == getOrtById(1)) {           // Tod 1: ins Meer gesprungen

            System.out.println("Dachtest du wirklich, es sei eine gute Idee einfach so ins Wasser zu springen?");
            return true;
        } 
        if (s.ort == getOrtById(4)) {           // Tod 2: Auf Plattform versterben?

            System.out.println("Dein mulmiges Gefühl bei dieser verlassenen Plattform trügt nicht, dort drüben liegt ne Leiche.");
            System.out.println("Plötzlich kommt ein ärmlich aussehender Mann mit einer SKS um die Ecke und schreit dich an.");

            if(s.hasItem(getItemById(3))) {     // vielleicht nicht!

                System.out.println("Du hast den schnelleren Finger. Dein gegenüber ist tot. Vor Verzweiflung legst du die Waffe nieder und verlässt den Ort.");
                s.remove(3);
                s.ort = getOrtById(3);
                getOrtById(4).isLocked = true;
                return false;

            } else {                            // oder doch

                System.out.println("Du bist wehrlos. Du siehst deinen eigenen Fall noch, hörst aber schon den Schuss nicht mehr: Du wurdest erschossen.");
                return true;
            }
        }
        if (s.ort == getOrtById(7)) {           // sterben. In der Fabrik ohne Einflussnahme sterben.

            System.out.println("Vorsichtig schleichst du dich in die Fabrik. Plötzlich wirst du von einer Gruppe patrolierender Privater Militärdienstleister überwältigt. \nNicht sehr viel später erkennst du, woher die Geräusche kamen: Vor dir wurden schon einige andere Wesen aufgegriffen.\nGemeinsam mit anderen Menschen, Akten und Tieren wirst unter hohnischem Gelächter in ein brennendes Loch geworfen...");
            return true;
        }
        return false;                           // Normalfall: nicht gestorben
    }

    public Ort getOrtById(int id) {
        
        // Ortarray nach Ort per ID durchsuchen

        if (id == 0) return null;
        for (Ort o : Orte) {
            if (o.id == id) return o;
        }
        return null;
        
    }

    public Item getItemById(int id) {
        
        // Itemarray nach Item per ID durchsuchen

        if (id == 0) return null;
        for (Item i : items) {
            if (i.id == id) return i;
        }
        return null;
        
    }

    public void rebuild(File f) {
        
        FileReader inputStream;     // FileReader deklarieren

        try {
            inputStream = new FileReader(f);

            JSONTokener x = new JSONTokener(inputStream);      

            JSONObject spiel = new JSONObject(x);
            JSONArray locations = new JSONArray(spiel.get("locations").toString());
            JSONArray inventory = new JSONArray(spiel.get("inventory").toString());             // spezieller JSON-Objekte konstruieren; per token ["xyz"] wird ein Element aus der Datei aufgerufen, in einen String umgewandelt, der String wird zu Objekt aus JSON geparst -> sichere Anwendung

            items = new Item[inventory.length()];                                       // globales Itemarray überschreiben

            for (int i = 0; i<inventory.length(); i++) {
                JSONObject temp = new JSONObject(inventory.get(i).toString());
                Item item = new Item(temp.getString("name"), temp.getString("beschreibung"), temp.getInt("id"));

                items[i] = item;                    // Array füllen
            }   

            Orte = new Ort[locations.length()];                 //Ortarray überschreiben

            for (int i = 0; i<locations.length(); i++) {
                JSONObject temp = new JSONObject(locations.get(i).toString());

                String na = temp.getString("name");
                String bes = temp.getString("beschreibung");
                
                Ort ort = new Ort(na, bes, temp.getBoolean("isStart"), temp.getBoolean("isGoal"), false, temp.getInt("id"));    // mithilfe von id Ort aus Datei konstruieren          

                Orte[i] = ort;                      // Array füllen
            }

            for (int i = 0; i<Orte.length; i++) {                                   // Properties für Orte zuweisen, läuft Schritt für Schritt in die Objekte reintauchend
                JSONObject temp = new JSONObject(locations.get(i).toString());
                Ort ort = Orte[i];
                JSONArray ite = new JSONArray(temp.get("items").toString());
                ort.Item1 = getItemById((int) ite.get(0));
                ort.Item2 = getItemById((int) ite.get(1));
                ort.Item3 = getItemById((int) ite.get(2));
                ort.UnlockItem = getItemById((int) ite.get(3));
                ort.isLocked = temp.getBoolean("isLocked");

                ort.l = getOrtById(temp.getInt("l"));  
                ort.r = getOrtById(temp.getInt("r"));
                ort.o = getOrtById(temp.getInt("o"));
                ort.u = getOrtById(temp.getInt("u"));    

                if (ort.isGoal) ziel = ort;
                else if (ort.isStart) start = ort;
                
            }

            JSONObject player = new JSONObject(spiel.get("player").toString());     // player und Inventar rekonstruieren
            s = new Spieler(getOrtById(player.getInt("location"))); 

            this.winCount = spiel.getInt("winCount");

            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public void save() {

        // Spiel in JSON speichern
 
        FileWriter file; // Output

        try {
            file = new FileWriter(savePath);

            JSONArray locations = new JSONArray();
            JSONArray inventory = new JSONArray();

            for (Ort o: Orte) {                                 // locations-array mit den properties aller Orte in globalem Ortarray füllen
                JSONObject temp = new JSONObject();
                temp.put("id", o.id);                           // nutzen von id, da Objekt an sich Relativ -> so über Insatanzen hinweg identifizierbar
                temp.put("name", o.name);
                temp.put("beschreibung", o.beschreibung);
                temp.put("l", (o.l != null ? o.l.id : 0));      // jeweils konditionale Abfragen -> keinen Blödsinn outputten
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

            for (Item i : items) {                              // items abspeichern
                JSONObject temp = new JSONObject();
                temp.put("id", i.id);
                temp.put("name", i.name);
                temp.put("beschreibung", i.beschreibung);

                inventory.put(temp);
            }

            JSONObject player = new JSONObject();               // player speichern

            player.put("location", (s.ort != null ? s.ort.id : 0));
            int[] t = {s.items[0] != null ? s.items[0].id : 0, s.items[1] != null ? s.items[1].id : 0, s.items[2] != null ? s.items[2].id : 0, s.items[3] != null ? s.items[3].id : 0, s.items[4] != null ? s.items[4].id : 0};
            player.put("items", new JSONArray(t));
            
            JSONObject spiel = new JSONObject();                // alle Elemente zusammenfügen
            spiel.put("player", player);
            spiel.put("locations", locations);
            spiel.put("inventory", inventory);
            spiel.put("winCount", winCount);

            spiel.write(file, 4, 0);                            // Output vorbereiten
                
            file.flush();                                       // schreiben
            file.close();                                       // writer schließen

        } catch (Exception e) {
            System.out.println(e);  
            e.printStackTrace();
        }         
    }
}
