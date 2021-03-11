package game.lib_custom.Json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import game.lib_custom.Data;

public class Reader {

    BufferedReader f;

    public Reader(String path) {
        try {
            File target = new File(path);
            f = new BufferedReader(new FileReader(target));
        } catch (Exception e) {

        }
    }

    public Data getContent() {
        try {
            return new Data(f.readLine());
        } catch (IOException e) {
            return new Data("-1");
        }
    }
    
}
