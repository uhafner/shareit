package edu.hm.hafner.shareit;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Test;

import edu.hm.hafner.shareit.model.Benutzer;
import edu.hm.hafner.shareit.model.Registrierung;
import edu.hm.hafner.shareit.util.AbstractDatabaseTest;

/**
 * Testet die Klasse {@link BenutzerVerwaltungUsecaseControllerImpl}.
 *
 * @author Ulli Hafner
 */
public class BenutzerVerwaltungUsecaseControllerImplTest extends AbstractDatabaseTest {
    private static final String TEST_PASSWORT = "geheim";
    private static final String TEST_EMAIL = "hafner@hm.edu";
    private static final String TEST_NACHNAME = "Hafner";
    private static final String TEST_VORNAME = "Ullrich";

    /**
     * Zeigt, dass die Neuanlage einer Registrierung funktioniert.
     */
    @Test
    public void testeAnlegenEinerRegistrierung() {
        BenutzerVerwaltungUsecaseController verwaltung = new BenutzerVerwaltungUsecaseControllerImpl();
        pruefeErwarteteAnzahlRegistrierungen(verwaltung, 0);

        verwaltung.registriereBenutzer(TEST_EMAIL, TEST_VORNAME, TEST_NACHNAME, TEST_PASSWORT);
        pruefeErwarteteAnzahlRegistrierungen(verwaltung, 1);
        pruefeInhaltRegistrierung(verwaltung);
    }

    private void pruefeInhaltRegistrierung(final BenutzerVerwaltungUsecaseController verwaltung) {
        Registrierung benutzer = verwaltung.findeRegistrierung(createAdmin(), TEST_EMAIL);

        assertEquals("Falscher Vorname", TEST_VORNAME, benutzer.getVorname());
        assertEquals("Falscher Nachname", TEST_NACHNAME, benutzer.getNachname());
        assertEquals("Falsche EMail", TEST_EMAIL, benutzer.getEmail());
        assertEquals("Falsches Passwort", TEST_PASSWORT, benutzer.getPasswort());
    }

    private Benutzer createAdmin() {
        return new Benutzer(TEST_EMAIL, TEST_VORNAME, TEST_NACHNAME, TEST_PASSWORT, true);
    }

    private Benutzer createBenutzer() {
        return new Benutzer(TEST_EMAIL, TEST_VORNAME, TEST_NACHNAME, TEST_PASSWORT, false);
    }

    private void pruefeErwarteteAnzahlRegistrierungen(final BenutzerVerwaltungUsecaseController verwaltung, final int expectedNumber) {
        assertEquals("Falsche Anzahl registierte Benutzer", expectedNumber, verwaltung.findeAlleRegistrierungen(createAdmin()).size());
    }

    /**
     * Zeigt, dass die Neuanlage einer Registrierung fehlschlägt, wenn der Benutzername bereits existiert.
     */
    @Test(expected = IllegalStateException.class)
    public void testeDoppeltenBenutzernamen() {
        BenutzerVerwaltungUsecaseController verwaltung = new BenutzerVerwaltungUsecaseControllerImpl();

        verwaltung.registriereBenutzer(TEST_EMAIL, TEST_VORNAME, TEST_NACHNAME, TEST_PASSWORT);
        String gleicheEmail = TEST_EMAIL;
        verwaltung.registriereBenutzer(gleicheEmail, "Neuer Vorname", "Neuer Nachname", "Neues Passwort");
    }

    /**
     * Zeigt, dass eine Exception geworfen wird, falls eine Registrierung mit einem Nutzernamen nicht existiert.
     */
    @Test(expected = NoSuchElementException.class)
    public void testeEmailNichtGefunden() {
        BenutzerVerwaltungUsecaseController verwaltung = new BenutzerVerwaltungUsecaseControllerImpl();

        verwaltung.findeRegistrierung(createAdmin(), "unbekannte@email");
    }

    /**
     * Zeigt, dass ein normaler Nutzer nicht alle vorhandenen Registrierungen auslesen kann.
     */
    @Test(expected = SecurityException.class)
    public void testeAutorisierungAlleRegistrierungen() {
        BenutzerVerwaltungUsecaseController verwaltung = new BenutzerVerwaltungUsecaseControllerImpl();

        verwaltung.findeAlleRegistrierungen(createBenutzer());
    }

    /**
     * Zeigt, dass ein normaler Nutzer nicht eine bestimmte Registrierungen auslesen kann.
     */
    @Test(expected = SecurityException.class)
    public void testeAutorisierungSpezifischeRegistrierung() {
        BenutzerVerwaltungUsecaseController verwaltung = new BenutzerVerwaltungUsecaseControllerImpl();

        verwaltung.findeRegistrierung(createBenutzer(), "eine@email");
    }

    /**
     * Zeigt, dass eine Registrierung auch wieder gelöscht werden kann.
     */
    @Test
    public void testeLoeschenEinerRegistrierung() {
        BenutzerVerwaltungUsecaseController verwaltung = new BenutzerVerwaltungUsecaseControllerImpl();

        verwaltung.registriereBenutzer(TEST_EMAIL, TEST_VORNAME, TEST_NACHNAME, TEST_PASSWORT);
        pruefeErwarteteAnzahlRegistrierungen(verwaltung, 1);
        pruefeInhaltRegistrierung(verwaltung);

        verwaltung.loescheRegistrierung(createAdmin(), TEST_EMAIL);
        pruefeErwarteteAnzahlRegistrierungen(verwaltung, 0);
    }

    /**
     * Zeigt, dass ein normaler Nutzer nicht Registrierungen löschen kann.
     */
    @Test(expected = SecurityException.class)
    public void testeAutorisierungLoeschen() {
        BenutzerVerwaltungUsecaseController verwaltung = new BenutzerVerwaltungUsecaseControllerImpl();

        verwaltung.loescheRegistrierung(createBenutzer(), "eine@email");
    }

    /**
     * Zeigt, dass eine Exception geworfen wird, falls es die zu löschende Registrierung nicht gibt.
     */
    @Test(expected = NoSuchElementException.class)
    public void testeEmailBeimLoeschenNichtGefunden() {
        BenutzerVerwaltungUsecaseController verwaltung = new BenutzerVerwaltungUsecaseControllerImpl();

        verwaltung.loescheRegistrierung(createAdmin(), "gibt.es@nicht");
    }
}

