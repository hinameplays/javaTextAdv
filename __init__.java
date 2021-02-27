import game.lib_custom.*;

import java.io.File;
import java.io.FileWriter;

public class __init__ {
    
    public static Boolean initialise() {
        try {
            Data d = new Data(new String("@echo off\ncolor 07\nmode con: cols=160 lines=40\ntitle=\"Java Adventure\"\njavac \".\\game\\Main.java\"\njava \".\\game\\Main.java\""));
            File f = makeFile("init.bat", d);
            runBat(f);

            //File lib = new File(System.getProperty("user.dir")+"\\java_cache\\");
            //if (!lib.exists()){
            //    System.out.println("The json library file ist missing. Please download it from https://repo1.maven.org/maven2/org/json/json/20201115/json-20201115.jar and put it in the cache");
            //}

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static File makeFile(String name, Data d) {
        try {
            //Frame u = new Frame(20,20);

            File theDir = new File(System.getProperty("user.dir")+"\\java_cache\\");
            if (!theDir.exists()){
                theDir.mkdirs();
            }
            File file = new File(System.getProperty("user.dir")+"\\java_cache\\"+name);
            if (file.createNewFile()) System.out.println("File created");
            FileWriter w = new FileWriter(file);
            w.write(String.valueOf(d.getData()));
            w.close();

            return file;
        } catch (Exception e) {
            return null;
        }
    }

    private static void runBat(File file) {
        try {
            Process p = Runtime.getRuntime().exec("cmd /c start "+file.getAbsolutePath());
            p.waitFor(); 
        } catch (Exception e) {
            System.out.println("Execution failed. Trace:\n"+e);
        }
        
    }

    public static void generateDB() {
        
    }

    public static void main(String[] args) {
        initialise();
    }
}