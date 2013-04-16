package edu.hm.hafner.shareit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Collection;

import org.junit.Test;

import edu.hm.hafner.shareit.db.BenutzerController;
import edu.hm.hafner.shareit.db.RegistrierungsController;
import edu.hm.hafner.shareit.model.Benutzer;
import edu.hm.hafner.shareit.model.Registrierung;

/**
 * Testet die Klasse {@link BenutzerVerwaltungUsecaseControllerImpl} mit einem handgeschriebenen Mock.
 *
 * @author Ulli Hafner
 */
public class BenutzerVerwaltungMockTest {
    private static final String TEST_PASSWORT = "geheim";
    private static final String TEST_EMAIL = "hafner@hm.edu";
    private static final String TEST_NACHNAME = "Hafner";
    private static final String TEST_VORNAME = "Ullrich";

    private Benutzer createAdmin() {
        return new Benutzer(TEST_EMAIL, TEST_VORNAME, TEST_NACHNAME, TEST_PASSWORT, true);
    }

    /**
     * Handgeschriebener Mock für den {@link RegistrierungsController}.
     *
     * @author Ulli Hafner
     */
    private final class RegistrierungMock implements RegistrierungsController {
        public boolean istDeleteAufgerufenWorden;

        @Override
        public void updateProperties(final String email, final String geaenderterVorname, final String geaenderterNachname,
                final String geaendertesPasswort) {
            // Not needed
        }

        @Override
        public Collection<Registrierung> findRegistrierungen() {
            return null;
        }

        @Override
        public Registrierung findByPrimaryKey(final String email) {
            return null;
        }

        @Override
        public void delete(final String email) {
            istDeleteAufgerufenWorden = true;
        }

        @Override
        public Registrierung create(final String email, final String vorname, final String nachname, final String passwort) {
            return null;
        }

        @Override
        public boolean containsEmail(final String email) {
            return false;
        }

        @Override
        public Collection<Registrierung> findByText(final String text) {
            return null;
        }
    }

    /**
     * Testet, dass das Löschen der Registrierung an die Datenbank weitergeleitet wird.
     */
    @Test
    public void testeLoeschenDerRegistrierung() {
        // Given
        RegistrierungMock registrierungsController = new RegistrierungMock();
        BenutzerVerwaltungUsecaseController controller = new BenutzerVerwaltungUsecaseControllerImpl(
                registrierungsController, mock(BenutzerController.class));

        // When
        controller.loescheRegistrierung(createAdmin(), "email@hm.edu");

        // Then
        assertTrue("Datenbankschicht nicht korrekt aufgerufen", registrierungsController.istDeleteAufgerufenWorden);
    }
}

