package edu.hm.hafner.shareit.db;

/**
 * Erzeugt, findet und ändert Benutzer.
 *
 * @author Ulli Hafner
 */
public interface BenutzerController {
    /**
     * Überprüft, ob die übergebenen EMail bereits als Benutzername verwendet wird.
     *
     * @param email
     *            die zu prüfende EMail
     * @return <code>true</code> falls die Email bereits verwendet wurde, <code>false</code> sonst
     */
    boolean containsEmail(String email);
}
