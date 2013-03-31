package edu.hm.hafner.shareit;

import static org.mockito.Mockito.*;

import org.junit.Test;

import edu.hm.hafner.shareit.db.BenutzerController;
import edu.hm.hafner.shareit.db.RegistrierungsController;

/**
 * Testet die Klasse {@link BenutzerVerwaltungUsecaseControllerImpl}.
 *
 * @author Ulli Hafner
 */
public class BenutzerVerwaltungStubMitMockitoTest {
    private static final String TEST_PASSWORT = "geheim";
    private static final String TEST_NACHNAME = "Hafner";
    private static final String TEST_VORNAME = "Ullrich";

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
    public void testeAnlegenRegistrierungMoeglich() {
        // Given
        BenutzerController benutzerController = mock(BenutzerController.class);
        when(benutzerController.containsEmail("ist.schon@da")).thenReturn(false);

        RegistrierungsController registrierungsController = mock(RegistrierungsController.class);
        when(registrierungsController.containsEmail("ist.schon@da")).thenReturn(false);

        BenutzerVerwaltungUsecaseController controller = new BenutzerVerwaltungUsecaseControllerImpl(
                registrierungsController, benutzerController);
        // When
        controller.registriereBenutzer("ist.schon@da", TEST_VORNAME, TEST_NACHNAME, TEST_PASSWORT);
    }
}

