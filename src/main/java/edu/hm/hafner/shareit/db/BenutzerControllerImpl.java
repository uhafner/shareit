package edu.hm.hafner.shareit.db;

import java.util.Collection;
import java.util.Collections;

import edu.hm.hafner.shareit.model.Benutzer;

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

