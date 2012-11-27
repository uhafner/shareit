package edu.hm.hafner.shareit;

import java.util.Collection;
import java.util.Collections;

/**
 * Erzeugt, findet und ändert Registrierungen.
 *
 * @author Ulli Hafner
 */
public class BenutzerControllerImpl implements BenutzerController {
    @Override
    public Collection<Benutzer> findByEmail(final String email) {
        return Collections.emptyList(); // TODO: muss noch implementiert werden
    }
}

