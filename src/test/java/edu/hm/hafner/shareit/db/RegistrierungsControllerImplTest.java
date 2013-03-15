package edu.hm.hafner.shareit.db;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Test;

import edu.hm.hafner.shareit.model.Registrierung;
import edu.hm.hafner.shareit.util.AbstractDatabaseTest;

/**
 * Testet die Klasse {@link RegistrierungsController}.
 */
public class RegistrierungsControllerImplTest extends AbstractDatabaseTest {
    private static final String TEST_PASSWORT = "geheim";
    private static final String TEST_EMAIL = "hafner@hm.edu";
    private static final String TEST_NACHNAME = "Hafner";
    private static final String TEST_VORNAME = "Ullrich";

    /**
     * Zeigt, dass ein neuer Benutzer erfolgreich angelegt werden kann.
     */
    @Test
    public void testeAnlegen() {
        RegistrierungsController controller = new RegistrierungsControllerImpl();

        ueberpruefeAnzahlRegistrierungen(controller, 0);

        controller.create(TEST_VORNAME, TEST_NACHNAME, TEST_EMAIL, TEST_PASSWORT);

        ueberpruefeAnzahlRegistrierungen(controller, 1);
        ueberpruefeInhaltRegistrierung(controller, TEST_EMAIL, TEST_VORNAME);

        controller.create("Neuer Vorname", "Neuer Nachname", "Neue Email", "Neues Passwort");

        ueberpruefeAnzahlRegistrierungen(controller, 2);
    }

    private void ueberpruefeAnzahlRegistrierungen(final RegistrierungsController controller, final int expectedNumber) {
        assertEquals("Falsche Anzahl registierte Benutzer", expectedNumber, controller.findRegistrierungen().size());
    }

    private void ueberpruefeInhaltRegistrierung(final RegistrierungsController controller, final String email,
            final String expectedVorname) {
        Registrierung benutzer = controller.findByEmail(email).iterator().next();

        assertEquals("Falscher Vorname", expectedVorname, benutzer.getVorname());
        assertEquals("Falscher Nachname", TEST_NACHNAME, benutzer.getNachname());
        assertEquals("Falsche Email", TEST_EMAIL, benutzer.getEmail());
        assertEquals("Falsches Passwort", TEST_PASSWORT, benutzer.getPasswort());
    }

    /**
     * Zeigt, dass eine Exception geworfen wird, falls zweimal die gleiche Email verwendet wird.
     */
    @Test(expected = IllegalStateException.class)
    public void testeDoppelteEmail() {
        RegistrierungsController controller = new RegistrierungsControllerImpl();

        controller.create(TEST_VORNAME, TEST_NACHNAME, TEST_EMAIL, TEST_PASSWORT);
        controller.create(TEST_VORNAME, TEST_NACHNAME, TEST_EMAIL, TEST_PASSWORT);
    }

    /**
     * Zeigt, dass der Vorname einer Registrierung im Nachhinein geändert werden kann.
     */
    @Test
    public void testeAendernVorname() {
        RegistrierungsController controller = new RegistrierungsControllerImpl();

        controller.create(TEST_VORNAME, TEST_NACHNAME, TEST_EMAIL, TEST_PASSWORT);
        ueberpruefeAnzahlRegistrierungen(controller, 1);
        ueberpruefeInhaltRegistrierung(controller, TEST_EMAIL, TEST_VORNAME);

        String geaenderterVorname = "Ulli";
        controller.updateProperties(TEST_EMAIL, geaenderterVorname);
        ueberpruefeAnzahlRegistrierungen(controller, 1);
        ueberpruefeInhaltRegistrierung(controller, TEST_EMAIL, geaenderterVorname);
    }

    /**
     * Zeigt, dass eine Exception geworfen wird, falls die zu ändernde Registrierung nicht gefunden wird.
     */
    @Test(expected = NoSuchElementException.class)
    public void testeEmailBeimAendernNichtGefunden() {
        RegistrierungsController controller = new RegistrierungsControllerImpl();

        controller.updateProperties(TEST_EMAIL, TEST_VORNAME);
    }

    /**
     * Zeigt, dass ein Benutzer mit seiner email wieder gefunden wird.
     */
    @Test
    public void testeSucheNachEmail() {
        RegistrierungsController controller = new RegistrierungsControllerImpl();

        String neueEmail = "Neue Email";
        ueberpruefeAnzahlRegistrierungenMitEmail(controller, TEST_EMAIL, 0);
        ueberpruefeAnzahlRegistrierungenMitEmail(controller, neueEmail, 0);

        controller.create(TEST_VORNAME, TEST_NACHNAME, TEST_EMAIL, TEST_PASSWORT);

        ueberpruefeAnzahlRegistrierungenMitEmail(controller, TEST_EMAIL, 1);
        ueberpruefeAnzahlRegistrierungenMitEmail(controller, neueEmail, 0);

        controller.create(TEST_VORNAME, TEST_NACHNAME, neueEmail, TEST_PASSWORT);

        ueberpruefeAnzahlRegistrierungenMitEmail(controller, TEST_EMAIL, 1);
        ueberpruefeAnzahlRegistrierungenMitEmail(controller, neueEmail, 1);
    }

    private void ueberpruefeAnzahlRegistrierungenMitEmail(final RegistrierungsController controller,
            final String email, final int expectedNumber) {
        assertEquals("Falsche Anzahl registierte Benutzer mit der Email " + email,
                expectedNumber, controller.findByEmail(email).size());
    }

    /**
     * Zeigt, dass eine Registrierung nicht mehr gefunden wird, sobald sie gelöscht wurde.
     */
    @Test
    public void testeErfolgreichesLoeschen() {
        RegistrierungsController controller = new RegistrierungsControllerImpl();

        controller.create(TEST_VORNAME, TEST_NACHNAME, TEST_EMAIL, TEST_PASSWORT);
        ueberpruefeAnzahlRegistrierungenMitEmail(controller, TEST_EMAIL, 1);

        controller.delete(TEST_EMAIL);
        ueberpruefeAnzahlRegistrierungenMitEmail(controller, TEST_EMAIL, 0);
    }

    /**
     * Zeigt, dass eine Exception geworfen wird, falls die zu löschende Registrierung nicht gefunden wird.
     */
    @Test(expected = NoSuchElementException.class)
    public void testeEmailBeimLoeschenNichtGefunden() {
        RegistrierungsController controller = new RegistrierungsControllerImpl();

        controller.delete(TEST_EMAIL);
    }
}
