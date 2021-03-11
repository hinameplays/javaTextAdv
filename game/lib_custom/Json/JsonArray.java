package game.lib_custom.Json;

import java.util.Arrays;

public class JsonArray {
    public JsonObject[] content;
    
    public JsonArray () {

    }

    public JsonArray (JsonObject[] array) {
        content = array;
    }
    
    public void append (JsonObject j) {
        content = Arrays.copyOf(content, content.length+1);
        content[content.length-1] = j;
    }

    public void append (JsonObject[] j) {
        content = Arrays.copyOf(content, content.length+j.length);

        int h = j.length;

        for (JsonObject i : j) {
            content[content.length-h] = i;
            h--;
        }
    }
}
