package game;

import game.lib_custom.Json.JsonArray;
import game.lib_custom.Json.JsonObject;

public class Main {
    public static void main(String[] args) {
        // Testklassen für eigene JSON-Implementierung
        //TODO für Json: Parser schreiben
        JsonObject[] t = {new JsonObject(), new JsonObject(), new JsonObject()};
        JsonArray i = new JsonArray(t);
        i.append(t);

        Spiel s = new Spiel();
        s.spielen();
    }
}
