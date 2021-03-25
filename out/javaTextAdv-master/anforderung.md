# **Anforderungsanalyse zu Adventure**  

## TOC  

|No| Ziel | Status|
---|---|---
1|Ziel/Gesamte Roadmap|teilweise erreicht|
2|unabdingbare Pflichten|erreicht|
3|Erweiterungen|erreicht|
4|Zusatzfeatures (nice-to-have)|zum Teil implementiert|  

### **Ziel**

Schaffen eines in der Konsole laufenden ("Text-") Adventures mithilfe von Java. Das Setting soll halbwegs modern und einfach zu verstehen sein.  

### unabdingbare Pflichten / Anforderungen  

- Spiel muss funktionales Ziel haben  (Win Condition) (done)

- eine funktionale Progression muss existieren  (done)

  - verknüpfte Orte

  - Quests (i.e. erhalten von items)

- Spiel muss intuitiv erschließbar sein  (nach Aussage meines kleinen Bruders und eines Freundes ist das Interface verständlich, allerdings spielen beide gerne ähnlich aufgebaute Spiele)

  - einfach nicht zu kompliziert machen

### Erweiterungen

- content aus datei lesen + Savegames (done)

- Parser (aus scanner erweitert) (haha switch go brrrrr)

- commands (as in man; help; $) (implementiert)

- ingame-Dokumentation (help-command zählt so halb)

- einfaches moddingsystem (bearbeiten einer JSON) (done)

### nice-to-have

(*Wird wahrscheinlich nicht passieren, ist aber bemerkenswert*)  

- UI in Textinterface (Grafikoutput über basically Ascii-Art)

  - minimap

- gute Story :P (so halb)

- intel & inventory system (funzt)

Insgesamt habe ich das ganze Spiel manuell auf Fehler geprüft. Da alle Klassen eigentlich verwendet werden und relativ fehlersicher gebaut sind, sollten keine Probleme entstehen. Das Spiel wurde unter Win10 und Linux mit OpenJDK getestet und funktioniert dort. Bei Kompabilitätsproblemen bitte einfach schreiben.

### zur Dateistruktur

Das Programm setzt sich wie folgt zusammen:  
adventure:  

ß  +anforderung (Anforderungsanalyse)  
ß  +Main.java (Hauptdatei)
ß  +README.md (Markdowndatei für Readme)  
ß  +game:  
ßß    +init.json (Quelldatei für content)  
ßß    + *.java (bekannte Javadateien aud Aufgabe)  
ßß    +lib_custom:
ßßß      +save_backup.json (Backup für story)  
ßßß      +json:  
ßßßß       +jsonbibliothek (modifizierte Quelldateien)  
ßßß      +Render (testklassen für Erweiterungen)  
