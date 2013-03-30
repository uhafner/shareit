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
}
