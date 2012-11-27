package edu.hm.hafner.shareit;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Testet die Klasse {@link RegistrierungsController}.
 */
public class RegistrierungsControllerTest {
    static final String PASSWORT = "geheim";
    static final String EMAIL = "hafner@hm.edu";
    static final String NACHNAME = "Hafner";
    static final String VORNAME = "Ullrich";
    
    /**
     * Zeigt, dass ein neuer Benutzer erfolgreich angelegt werden kann.
     */
    @Test
    public void testeAnlegen() {
        RegistrierungsController controller = new RegistrierungsController();
        
        verifyRegistrierungen(controller, 0);
        
        Registrierung benutzer = controller.create(VORNAME, NACHNAME, EMAIL, PASSWORT);
        verifyRegistrierungen(controller, 1);
        verifyBenutzer(benutzer);
        
        controller.create(PASSWORT, PASSWORT, PASSWORT, PASSWORT);
        verifyRegistrierungen(controller, 2);
    }
    
    private void verifyBenutzer(final Registrierung benutzer) {
        assertEquals("Falscher Vorname", VORNAME, benutzer.getVorname());
        assertEquals("Falscher Nachname", NACHNAME, benutzer.getNachname());
        assertEquals("Falsche EMail", EMAIL, benutzer.getEmail());
        assertEquals("Falsches Passwort", PASSWORT, benutzer.getPasswort());
    }
    
    private void verifyRegistrierungen(final RegistrierungsController controller, final int expectedNumber) {
        assertEquals("Falsche Anzahl registierte Benutzer",
                expectedNumber, controller.findRegistrierungen().size());
    }
    
    /**
     * Zeigt, dass ein Benutzer mit seiner EMail wieder gefunden wird.
     */
    @Test
    public void testeSucheNachEmail() {
        RegistrierungsController controller = new RegistrierungsController();
        
        verifyRegistrierungenMitEmail(controller, 0);
        controller.create(VORNAME, NACHNAME, EMAIL, PASSWORT);
        verifyRegistrierungenMitEmail(controller, 1);
        controller.create(PASSWORT, PASSWORT, PASSWORT, PASSWORT);
        verifyRegistrierungenMitEmail(controller, 1);
    }
    
    private void verifyRegistrierungenMitEmail(final RegistrierungsController controller, final int expectedNumber) {
        assertEquals("Falsche Anzahl registierte Benutzer mit der Email " + EMAIL,
                expectedNumber, controller.findByEmail(EMAIL).size());
    }
    
    /**
     * Zeigt, dass eine Exception geworfen wird, falls zweimal die gleiche Email verwendet wird.
     */
    @Test(expected = IllegalStateException.class)
    public void testeDoppelteEmail() {
        RegistrierungsController controller = new RegistrierungsController();
        
        controller.create(VORNAME, NACHNAME, EMAIL, PASSWORT);
        controller.create(VORNAME, NACHNAME, EMAIL, PASSWORT);
    }
    
    /**
     * Zeigt, dass eine Exception geworfen wird, falls die zu löschende Registrierung nicht gefunden wird.
     */
    @Test(expected = NoSuchElementException.class)
    public void testeEmailBeimLoeschenNichtGefunden() {
        RegistrierungsController controller = new RegistrierungsController();
        
        controller.delete(EMAIL);
    }
    
    /**
     * Zeigt, dass ein Benutzer mit seiner EMail wieder gefunden wird.
     */
    @Test
    public void testeErfolgreichesLöschen() {
        RegistrierungsController controller = new RegistrierungsController();
        
        controller.create(VORNAME, NACHNAME, EMAIL, PASSWORT);
        verifyRegistrierungen(controller, 1);
        controller.delete(EMAIL);
        verifyRegistrierungen(controller, 0);
    }
    
    /**
     * Setzt die Datenbank zurück.
     */
    @Before
    public void clearDatabase() {
        DatabaseFactory.INSTANCE.reset();
    }
}

