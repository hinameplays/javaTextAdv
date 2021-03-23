package game;

import java.util.Scanner;

public class Spieler {
    
    public static Ort ort;
    public Item[] items = new Item[5];

    public Spieler(Ort start) {
        ort = start;
    }

    public boolean gehe(String richtung) {
        try {
            Ort o = ort.getNachbarort(richtung);
            if (o != null) {
                if (o.isLocked) {
                    if (hasItem(o.UnlockItem)) {
                        remove(o.UnlockItem.id);
                        o.isLocked = false;
                        ort = o;
                        return true;
                    } else {
                        System.out.println("Leider fehlt dir noch "+o.UnlockItem.name+" um diesen Ort zu entsperren. Du kannst also nicht hierhin gehen.");
                        return false;
                    }
                } else {
                    ort = o;
                    return true;
                }
            } else return false;
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

    public void give(Item j) {
        for (int i=0; i<items.length; i++) {
            if (items[i] != null) {
                items[i] = j;
                return;
            }
        }

        System.out.println("Es existiert kein freier Inventarplatz. Wähle den Inventarplatz zum Tauschen oder drücke Alternativ [Enter] oder irgendetwas anderes als Eingabe.");
        System.out.println("Item 1: "+items[0].name+"\nItem 2: "+items[1].name+"\nItem 3: "+items[2].name+"\nItem 4: "+items[3].name+"\nItem 5: "+items[4].name);
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
        for (int i=0; i<items.length; i++) {
            if (items[i].id == id) items[i] = null;
        }
    }

}
