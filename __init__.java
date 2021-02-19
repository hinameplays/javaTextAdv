import render.Data;

import java.io.File;
import java.io.FileWriter;

public class __init__ {
    
    public static Boolean initialise() {
        try {
            Data d = new Data(new String("@echo off\njavac \".\\game\\Main.java\"\njava \".\\game\\Main.java\""));
            File f = makeFile("init.bat", d);
            runBat(f);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static File makeFile(String name, Data d) {
        try {

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

    public static void main(String[] args) {
        initialise();
    }
}