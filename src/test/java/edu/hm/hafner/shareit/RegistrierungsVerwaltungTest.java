package edu.hm.hafner.shareit;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Testet die Klasse {@link RegistrierungsVerwaltung}.
 */
public class RegistrierungsVerwaltungTest extends RegistrierungsControllerTest {
    /**
     * Zeigt, dass die Neuanlage einer Registrierung funktioniert.
     */
    @Test
    public void testeAnlegenEinerRegistrierung() {
        RegistrierungsVerwaltung verwaltung = new RegistrierungsVerwaltung();
        verifyRegistrierungen(verwaltung, 0);
        
        Registrierung benutzer = verwaltung.registriereBenutzer(VORNAME, NACHNAME, EMAIL, PASSWORT);
        verifyRegistrierungen(verwaltung, 1);
        
        assertEquals("Falscher Vorname", VORNAME, benutzer.getVorname());
        assertEquals("Falscher Nachname", NACHNAME, benutzer.getNachname());
        assertEquals("Falsche EMail", EMAIL, benutzer.getEmail());
        assertEquals("Falsches Passwort", PASSWORT, benutzer.getPasswort());
    }
    
    private void verifyRegistrierungen(final RegistrierungsVerwaltung verwaltung, final int expectedNumber) {
        assertEquals("Falsche Anzahl registierte Benutzer", expectedNumber, verwaltung.getRegistrierungen().size());
    }
    
    /**
     * Zeigt, dass die Neuanlage einer Registrierung fehlschlägt, wenn der Benutzername bereits existiert.
     */
    @Test(expected = IllegalStateException.class)
    public void testeDoppeltenBenutzernamen() {
        RegistrierungsVerwaltung verwaltung = new RegistrierungsVerwaltung();
        
        verwaltung.registriereBenutzer(VORNAME, NACHNAME, EMAIL, PASSWORT);
        verwaltung.registriereBenutzer("a", "b", EMAIL, "c");
    }
}

