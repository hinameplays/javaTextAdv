package render;

import java.io.File;
import java.io.FileWriter;

public class __init__ {
    static Engine e;
    static public Boolean initialise() {
        try {
            Data d = new Data(new String("@echo off\njavac \".\\game\\Main.java\"\njava \".\\game\\Main.java\""));
            File f = makeFile("init.bat", d);
            runBat(f);
            e = new Engine();
            return true;
        } catch (Exception e) {
            return false;
        }
    } 

    public void renderImage() {
        e.render();
    }

    private static File makeFile(String name, Data d) {
        try {

            
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
        }
        
    }
}