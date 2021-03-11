package game.lib_custom.Json;

import game.lib_custom.Data;

public class JsonObject {
    Data content; 
    public JsonObject() {

    }

    public JsonObject(Object object) {
        content = new Data(object);
    }

    public JsonObject(Data data) {
        content = data;
    }
}
