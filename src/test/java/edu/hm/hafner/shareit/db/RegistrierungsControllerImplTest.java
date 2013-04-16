package edu.hm.hafner.shareit.db;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.NoSuchElementException;

import org.junit.Test;

import edu.hm.hafner.shareit.model.Registrierung;
import edu.hm.hafner.shareit.util.AbstractDatabaseTest;

/**
 * Testet die Klasse {@link RegistrierungsController}.
 */
public class RegistrierungsControllerImplTest extends AbstractDatabaseTest {
    private static final String TEST_EMAIL = "hafner@hm.edu";
    private static final String TEST_VORNAME = "Ullrich";
    private static final String TEST_NACHNAME = "Hafner";
    private static final String TEST_PASSWORT = "geheim";

    /**
     * Zeigt, dass ein neuer Benutzer erfolgreich angelegt werden kann.
     */
    @Test
    public void testeAnlegen() {
        // Given
        RegistrierungsController controller = new RegistrierungsControllerImpl();
        ueberpruefeAnzahlRegistrierungen(controller, 0);

        // When
        controller.create(TEST_EMAIL, TEST_VORNAME, TEST_NACHNAME, TEST_PASSWORT);

        // Then
        ueberpruefeAnzahlRegistrierungen(controller, 1);
        ueberpruefeInhaltTestRegistrierung(controller);
    }

    private void ueberpruefeAnzahlRegistrierungen(final RegistrierungsController controller, final int expectedNumber) {
        assertEquals("Falsche Anzahl registierte Benutzer:", expectedNumber, controller.findRegistrierungen().size());
    }

    private void ueberpruefeInhaltTestRegistrierung(final RegistrierungsController controller) {
        ueberpruefeInhaltRegistrierung(controller, TEST_EMAIL, TEST_VORNAME, TEST_NACHNAME, TEST_PASSWORT);
    }

    private void ueberpruefeInhaltRegistrierung(final RegistrierungsController controller, final String email,
            final String expectedVorname, final String expectedNachname, final String expectedPasswort) {
        Registrierung benutzer = controller.findByPrimaryKey(email);

        assertEquals("Falsche Email:", email, benutzer.getEmail());
        assertEquals("Falscher Vorname:", expectedVorname, benutzer.getVorname());
        assertEquals("Falscher Nachname:", expectedNachname, benutzer.getNachname());
        assertEquals("Falsches Passwort:", expectedPasswort, benutzer.getPasswort());
    }

    /**
     * Zeigt, dass mehrere neue Benutzer erfolgreich angelegt werden können.
     */
    @Test
    public void testeAnlegen2Personen() {
        // Given
        RegistrierungsController controller = new RegistrierungsControllerImpl();

        pruefeAnlegenRegistrierung(controller, TEST_EMAIL, TEST_VORNAME, TEST_NACHNAME, TEST_PASSWORT, 1);
        pruefeAnlegenRegistrierung(controller, "Neue Email", "Neuer Vorname", "Neuer Nachname", "Neues Passwort", 2);
    }

    private void pruefeAnlegenRegistrierung(final RegistrierungsController controller, final String email, final String vorname,
            final String nachname, final String passwort, final int expectedNumber) {
        // When
        controller.create(email, vorname, nachname, passwort);

        // Then
        ueberpruefeAnzahlRegistrierungen(controller, expectedNumber);
        ueberpruefeInhaltRegistrierung(controller, email, vorname, nachname, passwort);
    }

    /**
     * Zeigt, dass eine Exception geworfen wird, falls zweimal die gleiche Email verwendet wird.
     */
    @Test(expected = IllegalStateException.class)
    public void testeDoppelteEmail() {
        RegistrierungsController controller = new RegistrierungsControllerImpl();

        controller.create(TEST_EMAIL, TEST_VORNAME, TEST_NACHNAME, TEST_PASSWORT);
        controller.create(TEST_EMAIL, TEST_VORNAME, TEST_NACHNAME, TEST_PASSWORT);
    }

    /**
     * Zeigt, dass eine Exception geworfen wird, falls die zu ändernde Registrierung nicht gefunden wird.
     */
    @Test(expected = NoSuchElementException.class)
    public void testeEmailBeimAendernNichtGefunden() {
        RegistrierungsController controller = new RegistrierungsControllerImpl();

        controller.updateProperties(TEST_EMAIL, TEST_VORNAME, TEST_NACHNAME, TEST_PASSWORT);
    }

    /**
     * Zeigt, dass die Eigenschaften einer Registrierung im Nachhinein geändert werden können.
     */
    @Test
    public void testeAendernEigenschaften() {
        RegistrierungsController controller = new RegistrierungsControllerImpl();

        controller.create(TEST_EMAIL, TEST_VORNAME, TEST_NACHNAME, TEST_PASSWORT);
        ueberpruefeAnzahlRegistrierungen(controller, 1);
        ueberpruefeInhaltTestRegistrierung(controller);

        String geaenderterVorname = "Neuer Vorname";
        String geaenderterNachname = "Neuer Nachname";
        String geaendertesPasswort = "Neues Passwort";
        controller.updateProperties(TEST_EMAIL, geaenderterVorname, geaenderterNachname, geaendertesPasswort);
        ueberpruefeAnzahlRegistrierungen(controller, 1);
        ueberpruefeInhaltRegistrierung(controller, TEST_EMAIL, geaenderterVorname, geaenderterNachname, geaendertesPasswort);
    }

    /**
     * Zeigt, dass ein Benutzer mit seiner email wieder gefunden wird.
     */
    @Test
    public void testeSucheNachEmail() {
        RegistrierungsController controller = new RegistrierungsControllerImpl();

        String neueEmail = "Neue Email";

        ueberpruefeAnzahlRegistrierungenMitEmail(controller, TEST_EMAIL, false);
        ueberpruefeAnzahlRegistrierungenMitEmail(controller, neueEmail, false);

        controller.create(TEST_EMAIL, TEST_VORNAME, TEST_NACHNAME, TEST_PASSWORT);

        ueberpruefeAnzahlRegistrierungenMitEmail(controller, TEST_EMAIL, true);
        ueberpruefeAnzahlRegistrierungenMitEmail(controller, neueEmail, false);

        controller.create(neueEmail, TEST_VORNAME, TEST_NACHNAME, TEST_PASSWORT);

        ueberpruefeAnzahlRegistrierungenMitEmail(controller, TEST_EMAIL, true);
        ueberpruefeAnzahlRegistrierungenMitEmail(controller, neueEmail, true);
    }

    private void ueberpruefeAnzahlRegistrierungenMitEmail(final RegistrierungsController controller,
            final String email, final boolean expectedExists) {
        assertEquals("Falsche Erwartung für das Auffinden eines registrierten Benutzers mit der Email " + email,
                expectedExists, controller.containsEmail(email));
    }

    /**
     * Zeigt, dass eine Registrierung nicht mehr gefunden wird, sobald sie gelöscht wurde.
     */
    @Test
    public void testeErfolgreichesLoeschen() {
        RegistrierungsController controller = new RegistrierungsControllerImpl();

        controller.create(TEST_EMAIL, TEST_VORNAME, TEST_NACHNAME, TEST_PASSWORT);
        ueberpruefeAnzahlRegistrierungenMitEmail(controller, TEST_EMAIL, true);

        controller.delete(TEST_EMAIL);
        ueberpruefeAnzahlRegistrierungenMitEmail(controller, TEST_EMAIL, false);
    }

    /**
     * Zeigt, dass eine Exception geworfen wird, falls die zu löschende Registrierung nicht gefunden wird.
     */
    @Test(expected = NoSuchElementException.class)
    public void testeEmailBeimLoeschenNichtGefunden() {
        RegistrierungsController controller = new RegistrierungsControllerImpl();

        controller.delete(TEST_EMAIL);
    }

    /**
     * Zeigt, dass eine Exception geworfen wird, falls bei der Suche nach dem primary Key kein Objekt gefunden wird.
     */
    @Test(expected = NoSuchElementException.class)
    public void testeNoSuchElementFallsEmailNichtGefunden() {
        RegistrierungsController controller = new RegistrierungsControllerImpl();

        controller.findByPrimaryKey(TEST_EMAIL);
    }

    /**
     * Überpüft, dass korrekt nach dem Text gesucht wird in allen Attributen.
     */
    @Test
    public void testeSucheNachText() {
        // Given
        RegistrierungsController controller = new RegistrierungsControllerImpl();
        controller.create("1", "Vorname", "Nachname", "egal");
        controller.create("2", "Ulli", "Nachname", "egal");
        controller.create("3", "Ulli", "Hafner", "egal");
        controller.create("3-name", "Ulli", "Hafner", "egal");

        ueberpruefeAnzahlRegistrierungen(controller, 4);

        ueberpruefeSuche(controller, "Ulli", 3);
        ueberpruefeSuche(controller, "name", 3);
        ueberpruefeSuche(controller, "egal", 0);
        ueberpruefeSuche(controller, "3", 2);
        ueberpruefeSuche(controller, "[", 0);
    }

    private void ueberpruefeSuche(final RegistrierungsController controller, final String text, final int expectedElements) {
        // When
        Collection<Registrierung> treffer = controller.findByText(text);
        // Then
        assertEquals(String.format("Falsche Anzahl Elemente mit '%s' gefunden", text), expectedElements, treffer.size());
    }
}
