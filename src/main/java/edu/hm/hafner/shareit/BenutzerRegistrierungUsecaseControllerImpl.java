package edu.hm.hafner.shareit;

import java.util.Collection;
import java.util.NoSuchElementException;

import edu.hm.hafner.shareit.db.BenutzerController;
import edu.hm.hafner.shareit.db.BenutzerControllerImpl;
import edu.hm.hafner.shareit.db.RegistrierungsController;
import edu.hm.hafner.shareit.db.RegistrierungsControllerImpl;
import edu.hm.hafner.shareit.model.Registrierung;

/**
 * Usecase Controller für den Anwendungsfall "Benutzer registrieren".
 *
 * @author Ulli Hafner
 */
public class BenutzerRegistrierungUsecaseControllerImpl implements BenutzerRegistrierungUsecaseController {
    private final RegistrierungsController controller = new RegistrierungsControllerImpl();
    private final BenutzerController benutzerController = new BenutzerControllerImpl();

    @Override
    public Collection<Registrierung> findeAlleRegistrierungen() {
        return controller.findRegistrierungen();
    }

    @Override
    public Registrierung registriereBenutzer(final String vorname, final String nachname, final String email, final String passwort) {
        if (isRegistrierungSchonVorhanden(email) || isBenutzerSchonVorhanden(email)) {
            throw new IllegalStateException("Die Email ist schon verwendet " + email);
        }
        return controller.create(vorname, nachname, email, passwort);
    }

    private boolean isBenutzerSchonVorhanden(final String email) {
        return !benutzerController.findByEmail(email).isEmpty();
    }

    private boolean isRegistrierungSchonVorhanden(final String email) {
        return !controller.findByEmail(email).isEmpty();
    }

    @Override
    public Registrierung findeRegistrierung(final String email) {
        Collection<Registrierung> registrierungen = controller.findByEmail(email);
        if (registrierungen.isEmpty()) {
            throw new NoSuchElementException("Keine Registrierung gefunden mit der Email " + email);
        }

        return registrierungen.iterator().next();
    }
}

