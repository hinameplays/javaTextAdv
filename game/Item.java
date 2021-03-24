package game;

public class Item {
    
    public String name, beschreibung;
    public int id;

    public Item(String n, String b, int i) { //Konstruktor für Item
        id = i; //wichtigste property, einzigartige Zahl zum identifizieren eines Items
        name = n;
        beschreibung = b;
    }
}
