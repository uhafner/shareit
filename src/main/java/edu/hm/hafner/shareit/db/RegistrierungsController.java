package edu.hm.hafner.shareit.db;

import java.util.Collection;

import edu.hm.hafner.shareit.model.Registrierung;

/**
 * Erzeugt, findet und ändert Registrierungen.
 *
 * @author Ulli Hafner
 */
public interface RegistrierungsController {
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
    Registrierung create(String vorname, String nachname, String email, String passwort);

    /**
     * Liefert alle Registrierungen zurück.
     *
     * @return die gefundenen Registrierungen
     */
    Collection<Registrierung> findRegistrierungen();

    /**
     * Liefert alle Registrierungen mit der übergebenen EMail zurück.
     *
     * @param email
     *            die zu prüfende EMail
     * @return die gefundenen Registrierungen
     */
    Collection<Registrierung> findByEmail(String email);

    /**
     * Löscht die Registrierung zur gegebenen Email aus der Datenbank.
     *
     * @param email
     *            die Email der zu löschenden Registrierung
     */
    void delete(String email);

    /**
     * Ändert die Eigenschaften der Registrierung mit der angegebenen Email.
     *
     * @param email
     *            die Email der Registrierung, der geändert werden soll
     * @param geaenderterVorname
     *            der neue Vorname
     */
    void updateProperties(String email, String geaenderterVorname);
}
