package edu.hm.hafner.shareit.db;

import java.util.Collection;
import java.util.NoSuchElementException;

import edu.hm.hafner.shareit.model.Benutzer;

/**
 * Erzeugt, findet und ändert Benutzer.
 *
 * @author Ulli Hafner
 */
public interface BenutzerController {
    /**
     * Erzeugt einen neuen Benutzer.
     *
     * @param email
     *            Email Adresse des Benutzers
     * @param vorname
     *            Vorname des Benutzers
     * @param nachname
     *            Nachname des Benutzers
     * @param passwort
     *            Passwort des Benutzers
     * @return die neue Benutzer
     * @throws IllegalStateException
     *             falls der Benutzername schon vergeben wurde
     */
    Benutzer create(String email, String vorname, String nachname, String passwort);

    /**
     * Liefert den Benutzer mit der übergebenen EMail zurück.
     *
     * @param email
     *            die zu prüfende EMail
     * @return der gefundene Benutzer
     * @throws NoSuchElementException
     *             falls keine Benutzer mit dem Benutzernamen gefunden wurde
     * @see #containsEmail(String)
     */
    Benutzer findByPrimaryKey(String email);

    /**
     * Liefert alle Benutzer zurück.
     *
     * @return die gefundenen Benutzer
     */
    Collection<Benutzer> findBenutzer();

    /**
     * Liefert alle Benutzer mit dem übergebenen Administratorstatus zurück.
     *
     * @param isAdministrator
     *            definiert, ob nach Benutzern oder Administratoren gesucht werden soll
     * @return die gefundenen Benutzer
     */
    Collection<Benutzer> findByAdministrator(boolean isAdministrator);

    /**
     * Liefert alle Benutzer zurück, die gesperrt sind (wenn {@code isLocked=true}) bzw. nicht gesperrt sind (wenn
     * {@code isLocked=false}) sind.
     *
     * @param isLocked
     *            definiert, ob nach gesperrten oder nicht-gesperrten Benutzern gesucht werden soll
     * @return die gefundenen Benutzer
     */
    Collection<Benutzer> findByLocked(boolean isLocked);

    /**
     * Liefert alle Benutzer zurück, die den übergebenen String im Benutzernamen (EMail), Namen oder Vornamen enthalten.
     *
     * @param text
     *            der zu findende Text
     * @return die gefundenen Benutzer
     */
    Collection<Benutzer> findByText(String text);

    /**
     * Liefert alle Benutzer zurück, die den übergebenen String im Benutzernamen (EMail), Namen oder Vornamen enthalten.
     * Zusätzlich werden die Benutzer nach ihrem Administratorstatus gefiltert.
     *
     * @param text
     *            der zu findende Text
     * @param isAdministrator
     *            definiert, ob nach Benutzern oder Administratoren gesucht werden soll
     * @return die gefundenen Benutzer
     */
    Collection<Benutzer> findByTextAndAdministrator(String text, boolean isAdministrator);

    /**
     * Überprüft, ob die übergebenen EMail bereits als Benutzername verwendet wird.
     *
     * @param email
     *            die zu prüfende EMail
     * @return <code>true</code> falls die Email bereits verwendet wurde, <code>false</code> sonst
     */
    boolean containsEmail(String email);

    /**
     * Löscht den Benutzer zur gegebenen Email aus der Datenbank.
     *
     * @param email
     *            die Email des zu löschenden Benutzers
     * @throws NoSuchElementException
     *             falls keine Benutzer mit dem Benutzernamen gefunden wurde
     */
    void delete(String email);

    /**
     * Ändert die Eigenschaften des Benutzer mit der angegebenen Email.
     *
     * @param email
     *            die Email der Benutzer, der geändert werden soll
     * @param geaenderterVorname
     *            der neue Vorname
     * @param geaenderterNachname
     *            der neue Nachname
     * @param geaendertesPasswort
     *            das neue Passwort
     * @param isAdministor
     *            definiert, ob der Benutzer Administrator Rechte hat
     * @param isLocked
     *            definiert, ob der Benutzer gesperrt ist
     * @throws NoSuchElementException
     *             falls keine Benutzer mit dem Benutzernamen gefunden wurde
     */
    void updateProperties(String email, String geaenderterVorname, String geaenderterNachname,
            String geaendertesPasswort, boolean isAdministor, boolean isLocked);
}
