package edu.hm.hafner.shareit.model;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Testet die Klasse {@link Benutzer}.
 *
 * @author Ulli Hafner
 */
public class BenutzerTest {
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
        Benutzer benutzer = new Benutzer(TEST_EMAIL, TEST_VORNAME, TEST_NACHNAME, TEST_PASSWORT);
        verifyProperties(benutzer);
    }

    private void verifyProperties(final Benutzer benutzer) {
        // When
        // Then
        assertEquals("Falscher Benutzername: ", TEST_EMAIL, benutzer.getEmail());
        assertEquals("Falscher Vorname: ", TEST_VORNAME, benutzer.getVorname());
        assertEquals("Falscher Nachname: ", TEST_NACHNAME, benutzer.getNachname());
        assertEquals("Falscher Passwort: ", TEST_PASSWORT, benutzer.getPasswort());
    }

    /**
     * Testet, ob alle String Parameter in der korrekten Reihenfolge im Konstruktor genutzt werden und das Administrator
     * Kennzeichen gesetzt ist.
     */
    @Test
    public void testeKonstruktorReihenfolgeAdministrator() {
        // Given
        Benutzer benutzer = new Benutzer(TEST_EMAIL, TEST_VORNAME, TEST_NACHNAME, TEST_PASSWORT, true);
        verifyProperties(benutzer);

        assertTrue("Benutzer ist kein Administrator", benutzer.isAdminstrator());
    }

    /**
     * Testet, ob alle String Parameter in der korrekten Reihenfolge im Konstruktor genutzt werden und das Administrator
     * Kennzeichen nicht gesetzt ist.
     */
    @Test
    public void testeKonstruktorReihenfolgeKeinAdministrator() {
        // Given
        Benutzer benutzer = new Benutzer(TEST_EMAIL, TEST_VORNAME, TEST_NACHNAME, TEST_PASSWORT, false);
        verifyProperties(benutzer);

        assertFalse("Benutzer ist Administrator", benutzer.isAdminstrator());
    }
}

