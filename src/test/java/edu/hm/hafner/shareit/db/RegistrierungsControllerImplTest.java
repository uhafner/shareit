package edu.hm.hafner.shareit.db;

import java.util.NoSuchElementException;

import org.junit.Test;

import static org.junit.Assert.*;
import edu.hm.hafner.shareit.model.Registrierung;
import edu.hm.hafner.shareit.util.AbstractDatabaseTest;

/**
 * Testet die Klasse {@link RegistrierungsControllerImpl}.
 *
 * @author Ulli Hafner
 */
public class RegistrierungsControllerImplTest extends AbstractDatabaseTest {
    private static final String PASSWORT = "geheim";
    private static final String EMAIL = "hafner@hm.edu";
    private static final String NACHNAME = "Hafner";
    private static final String VORNAME = "Ullrich";
    
    /**
     * Zeigt, dass ein neuer Benutzer erfolgreich angelegt werden kann.
     */
    @Test
    public void testeAnlegen() {
        RegistrierungsController controller = new RegistrierungsControllerImpl();
        
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
        RegistrierungsController controller = new RegistrierungsControllerImpl();
        
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
        RegistrierungsController controller = new RegistrierungsControllerImpl();
        
        controller.create(VORNAME, NACHNAME, EMAIL, PASSWORT);
        controller.create(VORNAME, NACHNAME, EMAIL, PASSWORT);
    }
    
    /**
     * Zeigt, dass eine Exception geworfen wird, falls die zu löschende Registrierung nicht gefunden wird.
     */
    @Test(expected = NoSuchElementException.class)
    public void testeEmailBeimLoeschenNichtGefunden() {
        RegistrierungsController controller = new RegistrierungsControllerImpl();
        
        controller.delete(EMAIL);
    }
    
    /**
     * Zeigt, dass ein Benutzer mit seiner EMail wieder gefunden wird.
     */
    @Test
    public void testeErfolgreichesLöschen() {
        RegistrierungsController controller = new RegistrierungsControllerImpl();
        
        controller.create(VORNAME, NACHNAME, EMAIL, PASSWORT);
        verifyRegistrierungen(controller, 1);
        controller.delete(EMAIL);
        verifyRegistrierungen(controller, 0);
    }
}

