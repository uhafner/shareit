package edu.hm.hafner.shareit;

import java.util.Collection;

/**
 * Controller für den Anwendungsfall "Benutzer registrieren".
 * @author Ulli Hafner
 */
public class RegistrierungsVerwaltung {
    private final RegistrierungsController controller = new RegistrierungsController();
    private final BenutzerController benutzerController = new BenutzerController();
    
    /**
     * Liefert alle bestehenden Registrierungen zurück.
     *
     * @return die Registrierungen
     */
    public Collection<Registrierung> getRegistrierungen() {
        return controller.findRegistrierungen();
    }
    
    /**
     * Registriert einen neuen Benutzer.
     *
     * @param vorname
     *            Vorname des Benutzers
     * @param nachname
     *            Nachname des Benutzers
     * @param email
     *            Email Adresse des Benutzers
     * @param passwort
     *            Passwort des Benutzers
     * @return der erzeugte Benutzer
     */
    public Registrierung registriereBenutzer(final String vorname, final String nachname, final String email, final String passwort) {
        if (!controller.findByEmail(email).isEmpty()
                || !benutzerController.findByEmail(email).isEmpty()) {
            throw new IllegalStateException("Die Email ist schon verwendet " + email);
        }
        return controller.create(vorname, nachname, email, passwort);
    }
}

