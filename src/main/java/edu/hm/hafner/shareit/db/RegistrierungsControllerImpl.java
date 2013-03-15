package edu.hm.hafner.shareit.db;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import com.google.common.collect.Lists;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import edu.hm.hafner.shareit.model.Registrierung;
import edu.hm.hafner.shareit.util.DatabaseFactory;

/**
 * Erzeugt, findet und ändert Registrierungen.
 *
 * @author Ulli Hafner
 */
public class RegistrierungsControllerImpl implements RegistrierungsController {
    private static final String NACHNAME_KEY = "nachname";
    private static final String VORNAME_KEY = "vorname";
    private static final String EMAIL_KEY = "email";
    private static final String PASSWORT_KEY = "passwort";

    private DBCollection getRegistrierungenCollection() {
        return DatabaseFactory.INSTANCE.getDatabase().getCollection("registrierungen");
    }

    @Override
    public Registrierung create(final String vorname, final String nachname, final String email, final String passwort) {
        Collection<Registrierung> existing = findByEmail(email);
        if (!existing.isEmpty()) {
            throw new IllegalStateException("Bereits eine Registrierung vorhanden mit der Email " + email);
        }
        BasicDBObject registrierung = new BasicDBObject();
        registrierung.append(VORNAME_KEY, vorname);
        registrierung.append(NACHNAME_KEY, nachname);
        registrierung.append(EMAIL_KEY, email);
        registrierung.append(PASSWORT_KEY, passwort);

        getRegistrierungenCollection().insert(registrierung);

        return new Registrierung(vorname, nachname, email, passwort);
    }

    @Override
    public Collection<Registrierung> findRegistrierungen() {
        return asCollection(getRegistrierungenCollection().find());
    }

    @Override
    public Collection<Registrierung> findByEmail(final String email) {
        return asCollection(queryForEmail(email));
    }

    /**
     * Setzt eine Datenbanksuche ab, die alle Objekte findet, deren Email-Attribut mit der gegebenen Email
     * übereinstimmt.
     *
     * @param email
     *            die zu suchende Email
     * @return ein Datenbankcursor zum Navigieren über die Menge der gefundenen Registrierungen
     */
    private DBCursor queryForEmail(final String email) {
        BasicDBObject query = new BasicDBObject();
        query.append(EMAIL_KEY, email);

        return getRegistrierungenCollection().find(query);
    }

    /**
     * Wandelt eine Menge von Datenbankobjekte in eine Menge fachlicher Registrierungen um.
     *
     * @param result
     *            der Datenbank Cursor, der eine Menge von Registrierungen (als Datenbankobjekte) beinhaltet
     * @return die Registrierungen als fachliche Objekte
     */
    private Collection<Registrierung> asCollection(final DBCursor result) {
        try {
            List<Registrierung> registrierungen = Lists.newArrayList();
            for (DBObject dbObject : result) {
                Registrierung registrierung = convertToRegistrierung(dbObject);
                registrierungen.add(registrierung);
            }
            return registrierungen;
        }
        finally {
            result.close();
        }
    }

    /**
     * Konvertiert ein Datenbankobjekt (d.h. eine einfache Hashmap) in eine fachliche Registrierung.
     *
     * @param dbObject
     *            das Datenbankobjekt
     * @return die fachliche Registrierung
     */
    private Registrierung convertToRegistrierung(final DBObject dbObject) {
        String vorname = (String)dbObject.get(VORNAME_KEY);
        String nachname = (String)dbObject.get(NACHNAME_KEY);
        String email = (String)dbObject.get(EMAIL_KEY);
        String passwort = (String)dbObject.get(PASSWORT_KEY);

        return new Registrierung(vorname, nachname, email, passwort);
    }

    @Override
    public void delete(final String email) {
        getRegistrierungenCollection().remove(queryByPrimaryKey(email));
    }

    /**
     * Sucht nach einer Registrierung mit der gewünschten Email.
     *
     * @param email
     *            die zu suchende Email
     * @return das gefundene Datenbankobjekt
     */
    private DBObject queryByPrimaryKey(final String email) {
        DBCursor cursor = queryForEmail(email);
        try {
            if (!cursor.hasNext()) {
                throw new NoSuchElementException("Keiner Registrierung gefunden zur EMail " + email);
            }
            return cursor.next();
        }
        finally {
            cursor.close();
        }
    }

    @Override
    public void updateProperties(final String email, final String geaenderterVorname) {
        DBObject registrierung = queryByPrimaryKey(email);

        registrierung.put(VORNAME_KEY, geaenderterVorname);

        getRegistrierungenCollection().save(registrierung);
    }
}
