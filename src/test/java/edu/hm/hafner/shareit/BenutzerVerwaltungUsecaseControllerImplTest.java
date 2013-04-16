package edu.hm.hafner.shareit;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

import org.junit.Test;

import edu.hm.hafner.shareit.db.BenutzerController;
import edu.hm.hafner.shareit.db.RegistrierungsController;
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

    private Benutzer createAdmin() {
        return new Benutzer(TEST_EMAIL, TEST_VORNAME, TEST_NACHNAME, TEST_PASSWORT, true);
    }

    private Benutzer createBenutzer() {
        return new Benutzer(TEST_EMAIL, TEST_VORNAME, TEST_NACHNAME, TEST_PASSWORT, false);
    }

    /**
     * Testet, dass das Löschen der Registrierung an die Datenbank weitergeleitet wird.
     */
    @Test
    public void testeLoeschenDerRegistrierung() {
        RegistrierungsController registrierungsController = mock(RegistrierungsController.class);
        BenutzerVerwaltungUsecaseController controller = createController(registrierungsController);

        controller.loescheRegistrierung(createAdmin(), TEST_EMAIL);

        verify(registrierungsController).delete(TEST_EMAIL);
    }

    private BenutzerVerwaltungUsecaseControllerImpl createController(final RegistrierungsController registrierungsController) {
        return new BenutzerVerwaltungUsecaseControllerImpl(registrierungsController, mock(BenutzerController.class));
    }

    /**
     * Zeigt, dass ein normaler Nutzer nicht Registrierungen löschen kann.
     */
    @Test(expected = SecurityException.class)
    public void testeBerechtigungFuerLoeschen() {
        RegistrierungsController registrierungsController = mock(RegistrierungsController.class);
        BenutzerVerwaltungUsecaseController controller = createController(registrierungsController);

        controller.loescheRegistrierung(createBenutzer(), TEST_EMAIL);
    }

    /**
     * Zeigt, dass ein normaler Nutzer nicht Registrierungen sehen kann.
     */
    @Test(expected = SecurityException.class)
    public void testeBerechtigungFuerSuchen() {
        RegistrierungsController registrierungsController = mock(RegistrierungsController.class);
        BenutzerVerwaltungUsecaseController controller = createController(registrierungsController);

        controller.findeRegistrierung(createBenutzer(), TEST_EMAIL);
    }

    /**
     * Zeigt, dass ein normaler Nutzer nicht Registrierungen sehen kann.
     */
    @Test(expected = SecurityException.class)
    public void testeBerechtigungFuerAlleSuchen() {
        RegistrierungsController registrierungsController = mock(RegistrierungsController.class);
        BenutzerVerwaltungUsecaseController controller = createController(registrierungsController);

        controller.findeAlleRegistrierungen(createBenutzer());
    }

    /**
     * Zeigt, dass Exceptions der Datenhaltung durchgereicht werden.
     */
    @Test(expected = IllegalStateException.class)
    public void testeExceptionDurchreichen() {
        RegistrierungsController registrierungsController = mock(RegistrierungsController.class);
        BenutzerVerwaltungUsecaseController controller = createController(registrierungsController);

        doThrow(new IllegalStateException()).when(registrierungsController).delete(anyString());
        controller.loescheRegistrierung(createAdmin(), TEST_EMAIL);
    }

    /**
     * Testet, dass die richtige Registrierung gefunden wird.
     */
    @Test
    public void testeFindenEinerDerRegistrierung() {
        RegistrierungsController registrierungsController = mock(RegistrierungsController.class);
        Registrierung registrierung = mock(Registrierung.class);
        when(registrierungsController.findByPrimaryKey(TEST_EMAIL)).thenReturn(registrierung);
        BenutzerVerwaltungUsecaseController controller = createController(registrierungsController);

        Registrierung actual = controller.findeRegistrierung(createAdmin(), TEST_EMAIL);

        assertSame("Nicht die gleiche Registrierung gefunden", registrierung, actual);
    }

    /**
     * Zeigt, dass Exceptions der Datenhaltung durchgereicht werden.
     */
    @Test(expected = NoSuchElementException.class)
    public void testeExceptionBySucheNachPrimaryKey() {
        RegistrierungsController registrierungsController = mock(RegistrierungsController.class);
        BenutzerVerwaltungUsecaseController controller = createController(registrierungsController);

        doThrow(new NoSuchElementException()).when(registrierungsController).findByPrimaryKey(anyString());
        controller.findeRegistrierung(createAdmin(), TEST_EMAIL);
    }

    /**
     * Testet, dass alle Registrierungen gefunden werden.
     */
    @Test
    public void testeFindenAllerRegistrierungen() {
        RegistrierungsController registrierungsController = mock(RegistrierungsController.class);
        ArrayList<Registrierung> registrierungen = new ArrayList<Registrierung>();
        when(registrierungsController.findRegistrierungen()).thenReturn(registrierungen);
        BenutzerVerwaltungUsecaseController controller = createController(registrierungsController);

        Collection<Registrierung> actual = controller.findeAlleRegistrierungen(createAdmin());

        assertSame("Nicht die gleichen Registrierungen gefunden", registrierungen, actual);
    }

    /**
     * Überprüft, ob eine Exception geworfen wird, wenn der Benutzername schon vergeben wurde.
     */
    @Test(expected = IllegalStateException.class)
    public void testeAnlegenRegistrierungNichtMoeglichDaBenutzerSchonVorhandenIst() {
        BenutzerController benutzerController = mock(BenutzerController.class);
        when(benutzerController.containsEmail("ist.schon@da")).thenReturn(true);

        RegistrierungsController registrierungsController = mock(RegistrierungsController.class);
        BenutzerVerwaltungUsecaseController controller = new BenutzerVerwaltungUsecaseControllerImpl(
                registrierungsController, benutzerController);

        controller.registriereBenutzer("ist.schon@da", TEST_VORNAME, TEST_NACHNAME, TEST_PASSWORT);
    }

    /**
     * Überprüft, ob eine Exception geworfen wird, wenn die Registrierung schon vorhanden ist.
     */
    @Test(expected = IllegalStateException.class)
    public void testeAnlegenRegistrierungNichtMoeglichDaRegistrierungSchonVorhandenIst() {
        BenutzerController benutzerController = mock(BenutzerController.class);
        when(benutzerController.containsEmail("ist.schon@da")).thenReturn(false);

        RegistrierungsController registrierungsController = mock(RegistrierungsController.class);
        when(registrierungsController.containsEmail("ist.schon@da")).thenReturn(true);

        BenutzerVerwaltungUsecaseController controller = new BenutzerVerwaltungUsecaseControllerImpl(
                registrierungsController, benutzerController);

        controller.registriereBenutzer("ist.schon@da", TEST_VORNAME, TEST_NACHNAME, TEST_PASSWORT);
    }

    /**
     * Überprüft, ob eine Exception geworfen wird, wenn der Benutzername schon vergeben wurde.
     */
    @Test
    public void testeAnlegenRegistrierung() {
        // Given
        BenutzerController benutzerController = mock(BenutzerController.class);
        when(benutzerController.containsEmail("ist.schon@da")).thenReturn(false);

        RegistrierungsController registrierungsController = mock(RegistrierungsController.class);
        when(registrierungsController.containsEmail("ist.schon@da")).thenReturn(false);

        Registrierung registrierung = mock(Registrierung.class);
        when(registrierungsController.create(TEST_EMAIL, TEST_VORNAME, TEST_NACHNAME, TEST_PASSWORT)).thenReturn(registrierung);

        BenutzerVerwaltungUsecaseController controller = new BenutzerVerwaltungUsecaseControllerImpl(
                registrierungsController, benutzerController);

        // When
        Registrierung actual = controller.registriereBenutzer(TEST_EMAIL, TEST_VORNAME, TEST_NACHNAME, TEST_PASSWORT);

        // Then
        assertSame("Nicht die gleiche Registrierung gefunden", registrierung, actual);
    }
}

