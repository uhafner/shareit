package edu.hm.hafner.shareit;

import java.util.Collection;
import java.util.NoSuchElementException;

import edu.hm.hafner.shareit.model.Benutzer;
import edu.hm.hafner.shareit.model.Registrierung;

/**
 * Usecase Controller für die Komponente Benutzerverwaltung.
 *
 * @author Ulli Hafner
 */
public interface BenutzerVerwaltungUsecaseController {
    /**
     * Registriert einen neuen Benutzer.
     *
     * @param email
     *            Email Adresse des Benutzers
     * @param vorname
     *            Vorname des Benutzers
     * @param nachname
     *            Nachname des Benutzers
     * @param passwort
     *            Passwort des Benutzers
     * @return der erzeugte Benutzer
     * @throws IllegalStateException
     *             falls der Benutzername schon vergeben wurde
     */
    Registrierung registriereBenutzer(String email, String vorname, String nachname, String passwort);

    /**
     * Liefert alle bestehenden Registrierungen zurück.
     *
     * @param angemeldeterBenutzer
     *            der angemeldete Benutzer
     * @return die Registrierungen
     * @throws SecurityException
     *             falls die Operation von keinem Administrator durchgeführt wird
     */
    Collection<Registrierung> findeAlleRegistrierungen(Benutzer angemeldeterBenutzer);

    /**
     * Findet die Registrierung mit der übergebenen EMail.
     *
     * @param angemeldeterBenutzer
     *            der angemeldete Benutzer
     * @param email
     *            die zu suchende EMail
     * @return die gefundene Registrierung
     * @throws SecurityException
     *             falls die Operation von keinem Administrator durchgeführt wird
     * @throws NoSuchElementException
     *             falls keine Registrierung mit dem Benutzernamen gefunden wurde
     */
    Registrierung findeRegistrierung(Benutzer angemeldeterBenutzer, String email);

    /**
     * Löscht die Registrierung zur gegebenen Email aus der Datenbank.
     *
     * @param angemeldeterBenutzer
     *            der angemeldete Benutzer
     * @param email
     *            die Email der zu löschenden Registrierung
     * @throws NoSuchElementException
     *             falls keine Registrierung mit dem Benutzernamen gefunden wurde
     * @throws SecurityException
     *             falls die Operation von keinem Administrator durchgeführt wird
     */
    void loescheRegistrierung(Benutzer angemeldeterBenutzer, String email);

    /**
     * Erzeugt einen neuen Benutzer auf Basis der übergebenen Registrierung.
     *
     * @param angemeldeterBenutzer
     *            der angemeldete Benutzer
     * @param freizugebenderBenutzer
     *            der freizugebende Benutzer
     * @return der erzeugte Benutzer
     * @throws IllegalStateException
     *             falls der Benutzername schon vergeben wurde
     * @throws SecurityException
     *             falls die Operation von keinem Administrator durchgeführt wird
     * @throws IllegalArgumentException
     *             falls das Passwort nicht sicher genug ist
     * @throws IllegalArgumentException
     *             falls der Benutzername leer ist
     */
    Benutzer erzeugeNutzer(Benutzer angemeldeterBenutzer, Registrierung freizugebenderBenutzer);

    /**
     * Liefert alle bestehenden Benutzer zurück.
     *
     * @param angemeldeterBenutzer
     *            der angemeldete Benutzer
     * @return die Benutzer
     * @throws SecurityException
     *             falls die Operation von keinem Administrator durchgeführt wird
     */
    Collection<Benutzer> findeAlleBenutzer(Benutzer angemeldeterBenutzer);

    /**
     * Liefert alle bestehenden Administratoren zurück.
     *
     * @param angemeldeterBenutzer
     *            der angemeldete Benutzer
     * @return die Administratoren
     * @throws SecurityException
     *             falls die Operation von keinem Administrator durchgeführt wird
     */
    Collection<Benutzer> findeAlleAdministratoren(Benutzer angemeldeterBenutzer);

    /**
     * Findet den Benutzer mit der übergebenen EMail.
     *
     * @param angemeldeterBenutzer
     *            der angemeldete Benutzer
     * @param email
     *            die zu suchende EMail
     * @return der gefundene Benutzer
     * @throws SecurityException
     *             falls die Operation von keinem Administrator durchgeführt wird
     * @throws NoSuchElementException
     *             falls kein Benutzer mit dem Benutzernamen gefunden wurde
     */
    Benutzer findeBenutzer(Benutzer angemeldeterBenutzer, String email);

    /**
     * Suche nach allen Benutzern, die den übergebenen String im Benutzernamen, Namen oder Vornamen enthalten.
     *
     * @param angemeldeterBenutzer
     *            der angemeldete Benutzer
     * @param text
     *            der zu suchende Text
     * @return der gefundene Benutzer
     * @throws SecurityException
     *             falls die Operation von keinem Administrator durchgeführt wird
     */
    Collection<Benutzer> sucheBenutzer(Benutzer angemeldeterBenutzer, String text);

    /**
     * Suche nach allen Benutzern, die den übergebenen String im Benutzernamen, Namen oder Vornamen enthalten. Sucht
     * entweder nach normalen Benutzern oder nach Administratoren.
     *
     * @param angemeldeterBenutzer
     *            der angemeldete Benutzer
     * @param text
     *            der zu suchende Text
     * @param isAdministrator
     *            definiert, ob nach Benutzern oder Administratoren gesucht werden soll
     * @return der gefundene Benutzer
     * @throws SecurityException
     *             falls die Operation von keinem Administrator durchgeführt wird
     */
    Collection<Benutzer> sucheBenutzer(Benutzer angemeldeterBenutzer, String text, boolean isAdministrator);

    /**
     * Macht den Benutzer mit der übergebenen EMail zum Administrator.
     *
     * @param angemeldeterBenutzer
     *            der angemeldete Benutzer
     * @param email
     *            die zu suchende EMail
     * @throws SecurityException
     *             falls die Operation von keinem Administrator durchgeführt wird
     * @throws NoSuchElementException
     *             falls kein Benutzer mit dem Benutzernamen gefunden wurde
     * @throws IllegalStateException
     *             falls der Benutzer schon Administrator ist
     */
    void setzteAdministratorRechte(Benutzer angemeldeterBenutzer, String email);

    /**
     * Nimmt dem Benutzer mit der übergebenen EMail die Administrator Rechte wieder weg.
     *
     * @param angemeldeterBenutzer
     *            der angemeldete Benutzer
     * @param email
     *            die zu suchende EMail
     * @throws SecurityException
     *             falls die Operation von keinem Administrator durchgeführt wird
     * @throws NoSuchElementException
     *             falls kein Benutzer mit dem Benutzernamen gefunden wurde
     * @throws IllegalStateException
     *             falls der Benutzer kein Administrator ist
     */
    void entferneAdministratorRechte(Benutzer angemeldeterBenutzer, String email);

    /**
     * Ändert das Passwort.
     *
     * @param angemeldeterBenutzer
     *            der angemeldete Benutzer
     * @param altesPasswort
     *            das alte Passwort
     * @param neuesPasswort
     *            das neue Passwort
     * @throws SecurityException
     *             falls das alte Passwort nicht stimmt
     * @throws IllegalArgumentException
     *             falls das neue Passwort nicht sicher genug ist
     */
    void aenderePasswort(Benutzer angemeldeterBenutzer, String altesPasswort, String neuesPasswort);

    /**
     * Sperrt den Benutzer mit der angegebenen Email.
     *
     * @param angemeldeterBenutzer
     *            der angemeldete Benutzer
     * @param email
     *            die zu suchende EMail
     * @throws SecurityException
     *             falls die Operation von keinem Administrator durchgeführt wird
     * @throws NoSuchElementException
     *             falls kein Benutzer mit dem Benutzernamen gefunden wurde
     * @throws IllegalStateException
     *             falls der Benutzer schon gesperrt ist
     */
    void sperreBenutzer(Benutzer angemeldeterBenutzer, String email);

    /**
     * Entsperrt den Benutzer mit der angegebenen Email.
     *
     * @param angemeldeterBenutzer
     *            der angemeldete Benutzer
     * @param email
     *            die zu suchende EMail
     * @throws SecurityException
     *             falls die Operation von keinem Administrator durchgeführt wird
     * @throws NoSuchElementException
     *             falls kein Benutzer mit dem Benutzernamen gefunden wurde
     * @throws IllegalStateException
     *             falls der Benutzer nicht gesperrt ist
     */
    void entsperreBenutzer(Benutzer angemeldeterBenutzer, String email);

    /**
     * Ändert die eigenen Benutzerdaten.
     *
     * @param angemeldeterBenutzer
     *            der angemeldete Benutzer
     * @param neuerVorname
     *            der neue Vorname
     * @param neuerNachname
     *            der neue Nachname
     */
    void aendereDaten(Benutzer angemeldeterBenutzer, String neuerVorname, String neuerNachname);
}
