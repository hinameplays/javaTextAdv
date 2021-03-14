package game.lib_custom;

import java.util.NoSuchElementException;

class Liste<T> {
    Listenelement anfang;
    Listenelement ende;
    int length = getLength();
    
    void add(T o) {
        Listenelement neuesElement = new Listenelement(o); // neues Listenelement aus Objekt erzeugt -> Listenelement()
        if(anfang == null) {
            // Sonderfall: Liste noch leer
            anfang = neuesElement;
            ende = neuesElement;
        } else {
            ende.next = neuesElement;
            ende = neuesElement;
        }
        length = getLength(); // neues berechnen von length
    }

    void remove(Listenelement e) {
        int a = getPosition(e);
        if (a>1) {
            get(a-1).next = e.next;
        } else {
            anfang = e.next; //wenn e erstes Element ist, Nachfolger zum ersten
        }
        e.next = null;  //e loslösen
        length = getLength(); // neues berechnen von length
    }

    T getEndeContent () {
        // Gib nicht das Listenelement, sondern dessen content zurück
        return ende.content;
    }

    int getPosition(Listenelement e) throws NoSuchElementException {
        Listenelement a=anfang;

        if (e==null) {
            throw new NoSuchElementException("Element given is null.");
        }

        for (int j=1; j<=length; j++) {
            if (a == e) {
                return j;
            }
            a=a.next;
        }

        throw new NoSuchElementException("Element given not part of list.");
    }

    Listenelement get (int i) {
        //Sonderfälle von letztem Element und ungültigem i puffern
        if(i>length||i<1) {
            return null;
        } else if (i==length) {
            return ende;
        }

        Listenelement a = anfang;
        for (int h = 1; h<i; h++) {
            a = a.next;
        }
        return a;
    }
    
    int getLength () {
        Listenelement a = anfang;
        int i=0;
        //loop über alle Elemente
        while (a!=null) {
            i++;
            a = a.next;
        }
        return i;
    }

    //interne Listenelement-Definition
    class Listenelement {
        Listenelement next;
        T content;
        
        public Listenelement(T o) {
            content = o;
        }
    }
    
}