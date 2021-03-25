package game;

import java.util.Scanner;

public class Spieler {
    
    public Ort ort;
    public Item[] items = new Item[5]; //Inventar des Spielers

    public Spieler(Ort start) {
        ort = start;
    }

    public boolean gehe(String richtung) {
        try {
            Ort o = ort.getNachbarort(richtung); 
            if (o != null) {                        // schaut, ob Zielort existiert
                if (o.isLocked) {                   // schaut, ob Ort freigeschaltet ist
                    if (hasItem(o.UnlockItem)) {    // schaut, ob Spieler den Ort freischalten kann
                        remove(o.UnlockItem.id);
                        o.isLocked = false;
                        ort = o;
                        return true;                // freigeschaltet & bewegt
                    } else {
                        System.out.println("Leider fehlt dir noch das Item '"+o.UnlockItem.name+"' um diesen Ort zu entsperren. Du kannst also nicht hierhin gehen.");  
                        return false;               // nicht freischaltbar -> nicht bewegt
                    }
                } else {
                    ort = o;    
                    return true;                    // nur bewegt
                }
            } else return false;                    // Ort existiert nicht -> nicht bewegt
        } catch (Exception e) {
            // catch zum abfangen komischer Plattformabhäniger Fehler, normalerweise nicht vonnöten
            return false;
        }
    }

    public boolean hasItem(Item i) {
        // prüft, ob der Spieler ein Item hat
        for (Item z : items) {
            if(z == i) {
                return true;
            }
        }
        return false;
    }

    public void printInventory() {
        // gibt Inventar des Spielers aus
        System.out.println("Dein Inventar:");
        System.out.println("1: "+ (items[0] != null ? items[0].name : "Nichts"));
        System.out.println("2: "+ (items[1] != null ? items[1].name : "Nichts"));
        System.out.println("3: "+ (items[2] != null ? items[2].name : "Nichts"));
        System.out.println("4: "+ (items[3] != null ? items[3].name : "Nichts"));
        System.out.println("5: "+ (items[4] != null ? items[4].name : "Nichts")+"\n");
    }

    public void give(Item j) {

        // versucht Item auf freien Slot zu legen
        for (int i=0; i<items.length; i++) {
            if (items[i] == null) {
                items[i] = j;
                return;
            }
        }

        System.out.println("Es existiert kein freier Inventarplatz. Wähle den Inventarplatz zum Tauschen oder drücke Alternativ [Enter] oder irgendetwas anderes als Eingabe.");
        this.printInventory();

        // gibt Spieler die Möglichkeit, Items zu sortieren
        Scanner sc = new Scanner(System.in);
        switch (sc.next()) {
            case "1":
                if (ort.give(items[0])) {
                    items[0] = j;
                    break;
                } 
            case "2":
                if (ort.give(items[1])) {
                    items[1] = j;
                    break;
                } 
            case "3":
                if (ort.give(items[2])) {
                    items[2] = j;
                    break;
                } 
            case "4":
                if (ort.give(items[3])) {
                    items[3] = j;
                    break;
                } 
            case "5":
                if (ort.give(items[4])) {
                    items[4] = j;
                    break;
                } 
            default:
                sc.close();
                return;
        }
        sc.close();

    }

    public void remove(int id) {
        // item von Spieler nach ID entfernen
        for (int i=0; i<items.length; i++) {
            if (items[i] != null && items[i].id == id) items[i] = null;
        }
    }

}
