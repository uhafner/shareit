package edu.hm.hafner.shareit;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import com.google.common.collect.Lists;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 * Erzeugt, findet und ändert Registrierungen.
 * @author Ulli Hafner
 */
public class RegistrierungsController {
    private static final String NACHNAME = "nachname";
    private static final String VORNAME = "vorname";
    private static final String EMAIL = "email";
    private static final String PASSWORT = "nachname";
    
    private DBCollection getRegistrierungenCollection() {
        return DatabaseFactory.INSTANCE.getDatabase().getCollection("registrierungen");
    }
    
    /**
     * Registriert einen neuen Benutzer.
     *
     * @param vorname
     *            Vorname des Benutzers
     * @param nachname
     *            Nachname des Benutzers
     * @param email
     *            Email Adresse des Benutzers
     * @param passwort
     *            Passwort des Benutzers
     * @return die neue Registrierung
     */
    public Registrierung create(final String vorname, final String nachname, final String email, final String passwort) {
        Collection<Registrierung> existing = findByEmail(email);
        if (!existing.isEmpty()) {
            throw new IllegalStateException("Bereits eine Registrierung vorhanden mit der Email " + email);
        }
        BasicDBObject registrierung = new BasicDBObject();
        registrierung.append(VORNAME, vorname);
        registrierung.append(NACHNAME, nachname);
        registrierung.append(EMAIL, email);
        registrierung.append(PASSWORT, passwort);
        
        getRegistrierungenCollection().insert(registrierung);
        return new Registrierung(vorname, nachname, email, passwort);
    }
    
    /**
     * Liefert alle Registrierungen zurück.
     *
     * @return die gefundenen Registrierungen
     */
    public Collection<Registrierung> findRegistrierungen() {
        return asCollection(getRegistrierungenCollection().find());
    }
    
    /**
     * Liefert alle Registrierungen mit der übergebenen EMail zurück.
     *
     * @param email die zu prüfende EMail
     * @return die gefundenen Registrierungen
     */
    public Collection<Registrierung> findByEmail(final String email) {
        return asCollection(queryForEmail(email));
    }
    
    private DBCursor queryForEmail(final String email) {
        BasicDBObject query = new BasicDBObject();
        query.append(EMAIL, email);
        
        return getRegistrierungenCollection().find(query);
    }
    
    private Collection<Registrierung> asCollection(final DBCursor result) {
        try {
            List<Registrierung> buecher = Lists.newArrayList();
            for (DBObject dbObject : result) {
                String vorname = (String)dbObject.get(VORNAME);
                String nachname = (String)dbObject.get(NACHNAME);
                String email = (String)dbObject.get(EMAIL);
                String passwort = (String)dbObject.get(PASSWORT);
                buecher.add(new Registrierung(vorname, nachname, email, passwort));
            }
            return buecher;
        }
        finally {
            result.close();
        }
    }
    
    /**
     * Löscht die Registrierung zur gegebenen Email aus der Datenbank.
     *
     * @param email die Email der zu löschenden Registrierung
     */
    public void delete(final String email) {
        DBCursor cursor = queryForEmail(email);
        try {
            if (!cursor.hasNext()) {
                throw new NoSuchElementException("Keiner Registrierung gefunden zur EMail " + email);
            }
            getRegistrierungenCollection().remove(cursor.next());
        }
        finally {
            cursor.close();
        }
    }
}

