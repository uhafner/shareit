package edu.hm.hafner.shareit.db;

import java.util.Collection;
import java.util.NoSuchElementException;

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
     * @param email
     *            Email Adresse des Benutzers
     * @param vorname
     *            Vorname des Benutzers
     * @param nachname
     *            Nachname des Benutzers
     * @param passwort
     *            Passwort des Benutzers
     * @return die neue Registrierung
     * @throws IllegalStateException
     *             falls der Benutzername schon vergeben wurde
     */
    Registrierung create(String email, String vorname, String nachname, String passwort);

    /**
     * Liefert alle Registrierungen zurück.
     *
     * @return die gefundenen Registrierungen
     */
    Collection<Registrierung> findRegistrierungen();

    /**
     * Liefert die Registrierung mit der übergebenen EMail zurück.
     *
     * @param email
     *            die zu prüfende EMail
     * @return die gefundenen Registrierungen
     * @throws NoSuchElementException
     *             falls keine Registrierung mit dem Benutzernamen gefunden wurde
     * @see #containsEmail(String)
     */
    Registrierung findByPrimaryKey(String email);

    /**
     * Liefert alle Registrierungen zurück.
     *
     * @param text
     *            der zu findende Text
     * @return die gefundenen Registrierungen
     */
    Collection<Registrierung> findByText(String text);

    /**
     * Überprüft, ob die übergebenen EMail bereits als Benutzername verwendet wird.
     *
     * @param email
     *            die zu prüfende EMail
     * @return <code>true</code> falls die Email bereits verwendet wurde, <code>false</code> sonst
     */
    boolean containsEmail(String email);

    /**
     * Löscht die Registrierung zur gegebenen Email aus der Datenbank.
     *
     * @param email
     *            die Email der zu löschenden Registrierung
     * @throws NoSuchElementException
     *             falls keine Registrierung mit dem Benutzernamen gefunden wurde
     */
    void delete(String email);

    /**
     * Ändert die Eigenschaften der Registrierung mit der angegebenen Email.
     *
     * @param email
     *            die Email der Registrierung, der geändert werden soll
     * @param geaenderterVorname
     *            der neue Vorname
     * @param geaenderterNachname
     *            der neue Nachname
     * @param geaendertesPasswort
     *            das neue Passwort
     * @throws NoSuchElementException
     *             falls keine Registrierung mit dem Benutzernamen gefunden wurde
     */
    void updateProperties(String email, String geaenderterVorname, String geaenderterNachname,
            String geaendertesPasswort);
}
