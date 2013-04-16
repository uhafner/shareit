package edu.hm.hafner.shareit;

import java.util.Collection;

import org.junit.Test;

import edu.hm.hafner.shareit.db.BenutzerController;
import edu.hm.hafner.shareit.db.RegistrierungsController;
import edu.hm.hafner.shareit.model.Registrierung;

/**
 * Testet die Klasse {@link BenutzerVerwaltungUsecaseControllerImpl} mit einem handgeschriebenen Stub.
 *
 * @author Ulli Hafner
 */
public class BenutzerVerwaltungStubTest {
    private static final String TEST_PASSWORT = "geheim";
    private static final String TEST_NACHNAME = "Hafner";
    private static final String TEST_VORNAME = "Ullrich";

    /**
     * Überprüft, ob eine Exception geworfen wird, wenn der Benutzername schon vergeben wurde.
     */
    @Test(expected = IllegalStateException.class)
    public void testeAnlegenRegistrierungNichtMoeglichDaBenutzerSchonVorhandenIst() {
        BenutzerVerwaltungUsecaseController controller = new BenutzerVerwaltungUsecaseControllerImpl(
                new RegistrierungsControllerStub(), new BenutzerController() {
            @Override
            public boolean containsEmail(final String email) {
                return true;
            }
        });
        controller.registriereBenutzer("ist.schon@da", TEST_VORNAME, TEST_NACHNAME, TEST_PASSWORT);
    }

    /**
     * Test Stub für den Registrierungs Controller.
     *
     * @author Ulli Hafner
     */
    private final class RegistrierungsControllerStub implements RegistrierungsController {
        @Override
        public void updateProperties(final String email, final String geaenderterVorname, final String geaenderterNachname,
                final String geaendertesPasswort) {
            // Nichts zu tun
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
            // Nichts zu tun
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
}

