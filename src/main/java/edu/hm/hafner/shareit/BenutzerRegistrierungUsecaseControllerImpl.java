package edu.hm.hafner.shareit;

import java.util.Collection;

import edu.hm.hafner.shareit.db.BenutzerController;
import edu.hm.hafner.shareit.db.BenutzerControllerImpl;
import edu.hm.hafner.shareit.db.RegistrierungsController;
import edu.hm.hafner.shareit.db.RegistrierungsControllerImpl;
import edu.hm.hafner.shareit.model.Benutzer;
import edu.hm.hafner.shareit.model.Registrierung;

/**
 * Usecase Controller für den Anwendungsfall "Benutzer registrieren".
 *
 * @author Ulli Hafner
 */
public class BenutzerRegistrierungUsecaseControllerImpl implements BenutzerRegistrierungUsecaseController {
    private final RegistrierungsController registrierungsController = new RegistrierungsControllerImpl();
    private final BenutzerController benutzerController = new BenutzerControllerImpl();

    @Override
    public Registrierung registriereBenutzer(final String email, final String vorname, final String nachname, final String passwort) {
        if (registrierungsController.containsEmail(email) || benutzerController.containsEmail(email)) {
            throw new IllegalStateException("Die Email ist schon verwendet " + email);
        }
        return registrierungsController.create(email, vorname, nachname, passwort);
    }

    @Override
    public Collection<Registrierung> findeAlleRegistrierungen(final Benutzer aktuellerNutzer) {
        ueberpruefeAdministratorRechte(aktuellerNutzer);

        return registrierungsController.findRegistrierungen();
    }

    private void ueberpruefeAdministratorRechte(final Benutzer angemeldeterBenutzer) {
        if (!angemeldeterBenutzer.isAdminstrator()) {
            throw new SecurityException("Der Zugriff auf Registrierungen darf nur von einem Administrator durchgeführt werden: " + angemeldeterBenutzer);
        }
    }

    @Override
    public Registrierung findeRegistrierung(final Benutzer angemeldeterBenutzer, final String email) {
        ueberpruefeAdministratorRechte(angemeldeterBenutzer);

        return registrierungsController.findByPrimaryKey(email);
    }

    @Override
    public void loescheRegistrierung(final Benutzer angemeldeterBenutzer, final String email) {
        ueberpruefeAdministratorRechte(angemeldeterBenutzer);

        registrierungsController.delete(email);
    }
}

