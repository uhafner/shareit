package edu.hm.hafner.shareit.model;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Testet die Klasse {@link Registrierung}.
 *
 * @author Ulli Hafner
 */
public class RegistrierungTest {
    private static final String TEST_PASSWORT = "geheim";
    private static final String TEST_EMAIL = "hafner@hm.edu";
    private static final String TEST_NACHNAME = "Hafner";
    private static final String TEST_VORNAME = "Ullrich";

    /**
     * Testet, ob alle String Parameter in der korrekten Reihenfolge im Konstruktor genutzt werden.
     */
    @Test
    public void testeKonstruktorReihenfolge() {
        // Given
        Registrierung benutzer = new Registrierung(TEST_EMAIL, TEST_VORNAME, TEST_NACHNAME, TEST_PASSWORT);
        // When
        // Then
        assertEquals("Falscher Benutzername: ", TEST_EMAIL, benutzer.getEmail());
        assertEquals("Falscher Vorname: ", TEST_VORNAME, benutzer.getVorname());
        assertEquals("Falscher Nachname: ", TEST_NACHNAME, benutzer.getNachname());
        assertEquals("Falscher Passwort: ", TEST_PASSWORT, benutzer.getPasswort());
    }
}

