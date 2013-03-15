package edu.hm.hafner.shareit;

import java.util.Collection;
import java.util.NoSuchElementException;

import edu.hm.hafner.shareit.model.Registrierung;

/**
 * Usecase Controller für den Anwendungsfall "Benutzer registrieren".
 *
 * @author Ulli Hafner
 */
public interface BenutzerRegistrierungUsecaseController {
    /**
     * Liefert alle bestehenden Registrierungen zurück.
     *
     * @return die Registrierungen
     */
    Collection<Registrierung> findeAlleRegistrierungen();

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
     * @return der erzeugte Benutzer
     * @throws IllegalStateException falls der Benutzername schon vergeben wurde
     */
    Registrierung registriereBenutzer(String vorname, String nachname, String email, String passwort);

    /**
     * Findet die Registrierung mit der übergebenen EMail.
     *
     * @param email
     *            die zu suchende EMail
     * @return die gefundene Registrierung
     * @throws NoSuchElementException falls keine Registrierung mit dem Benutzername gefunden wurde
     */
    Registrierung findeRegistrierung(String email);
}
