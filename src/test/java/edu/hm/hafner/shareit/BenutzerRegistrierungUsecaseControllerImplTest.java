package edu.hm.hafner.shareit;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Testet die Klasse {@link BenutzerRegistrierungUsecaseControllerImpl}.
 */
public class BenutzerRegistrierungUsecaseControllerImplTest extends RegistrierungsControllerImplTest {
    /**
     * Zeigt, dass die Neuanlage einer Registrierung funktioniert.
     */
    @Test
    public void testeAnlegenEinerRegistrierung() {
        BenutzerRegistrierungUsecaseController verwaltung = new BenutzerRegistrierungUsecaseControllerImpl();
        verifyRegistrierungen(verwaltung, 0);
        
        Registrierung benutzer = verwaltung.registriereBenutzer(VORNAME, NACHNAME, EMAIL, PASSWORT);
        verifyRegistrierungen(verwaltung, 1);
        
        assertEquals("Falscher Vorname", VORNAME, benutzer.getVorname());
        assertEquals("Falscher Nachname", NACHNAME, benutzer.getNachname());
        assertEquals("Falsche EMail", EMAIL, benutzer.getEmail());
        assertEquals("Falsches Passwort", PASSWORT, benutzer.getPasswort());
    }
    
    private void verifyRegistrierungen(final BenutzerRegistrierungUsecaseController verwaltung, final int expectedNumber) {
        assertEquals("Falsche Anzahl registierte Benutzer", expectedNumber, verwaltung.getRegistrierungen().size());
    }
    
    /**
     * Zeigt, dass die Neuanlage einer Registrierung fehlschlägt, wenn der Benutzername bereits existiert.
     */
    @Test(expected = IllegalStateException.class)
    public void testeDoppeltenBenutzernamen() {
        BenutzerRegistrierungUsecaseController verwaltung = new BenutzerRegistrierungUsecaseControllerImpl();
        
        verwaltung.registriereBenutzer(VORNAME, NACHNAME, EMAIL, PASSWORT);
        verwaltung.registriereBenutzer("a", "b", EMAIL, "c");
    }
}

