package edu.hm.hafner.shareit.db;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.google.common.collect.Lists;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;

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
    public Registrierung create(final String email, final String vorname, final String nachname, final String passwort) {
        if (containsEmail(email)) {
            throw new IllegalStateException("Bereits eine Registrierung vorhanden mit der Email " + email);
        }

        BasicDBObject registrierung = new BasicDBObject(EMAIL_KEY, email);
        updateProperties(registrierung, vorname, nachname, passwort);

        getRegistrierungenCollection().insert(registrierung);

        return new Registrierung(email, vorname, nachname, passwort);
    }

    /**
     * Ändert den Vornamen, Nachnamen und das Passwort der Registrierung. Sind diese Werte noch nicht gesetzt, werden
     * sie neu angelegt.
     *
     * @param registrierung
     *            die zu ändernde Registrierung
     * @param vorname
     *            Vorname des Benutzers
     * @param nachname
     *            Nachname des Benutzers
     * @param passwort
     *            Passwort des Benutzers
     */
    private void updateProperties(final DBObject registrierung,
            final String vorname, final String nachname, final String passwort) {
        registrierung.put(VORNAME_KEY, vorname);
        registrierung.put(NACHNAME_KEY, nachname);
        registrierung.put(PASSWORT_KEY, passwort);
    }

    @Override
    public Collection<Registrierung> findRegistrierungen() {
        return asCollection(getRegistrierungenCollection().find());
    }

    @Override
    public Registrierung findByPrimaryKey(final String email) {
        return convertToRegistrierung(queryByPrimaryKey(email));
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

        return new Registrierung(email, vorname, nachname, passwort);
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
            if (cursor.size() == 0) {
                throw new NoSuchElementException("Keiner Registrierung gefunden zur EMail " + email);
            }
            if (cursor.size() > 1) {
                throw new IllegalStateException("Mehrere Registrierungen vorhanden mit der Email " + email);
            }
            return cursor.next();
        }
        finally {
            cursor.close();
        }
    }

    @Override
    public void updateProperties(final String email,
            final String geaenderterVorname, final String geaenderterNachname, final String geaendertesPasswort) {
        DBObject registrierung = queryByPrimaryKey(email);

        updateProperties(registrierung, geaenderterVorname, geaenderterNachname, geaendertesPasswort);

        getRegistrierungenCollection().save(registrierung);
    }

    @Override
    public boolean containsEmail(final String email) {
        DBCursor cursor = queryForEmail(email);
        try {
            return cursor.size() > 0;
        }
        finally {
            cursor.close();
        }
    }

    /**
     * Setzt eine Datenbanksuche ab, die alle Objekte findet, die den übergebenen Text enthalten (außer im Passwort).
     *
     * @param text
     *            der zu suchende Text
     * @return ein Datenbankcursor zum Navigieren über die Menge der gefundenen Registrierungen
     */
    private DBCursor queryForText(final String text) {
        Pattern searchPattern = Pattern.compile(text);
        DBObject query = QueryBuilder.start().or(
                QueryBuilder.start(EMAIL_KEY).regex(searchPattern).get(),
                QueryBuilder.start(VORNAME_KEY).regex(searchPattern).get(),
                QueryBuilder.start(NACHNAME_KEY).regex(searchPattern).get())
                .get();

        return getRegistrierungenCollection().find(query);
    }

    @Override
    public Collection<Registrierung> findByText(final String text) {
        try {
            return asCollection(queryForText(text));
        }
        catch (PatternSyntaxException exception) {
            return Collections.emptyList();
        }
    }
}
