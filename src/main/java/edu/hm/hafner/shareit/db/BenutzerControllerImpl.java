package edu.hm.hafner.shareit.db;

/**
 * Erzeugt, findet und ändert Benutzer.
 *
 * @author Ulli Hafner
 */
public class BenutzerControllerImpl implements BenutzerController {
    private final UserController delegate = new UserControllerImpl();

    @Override
    public boolean containsEmail(final String email) {
        return delegate.containsEmail(email);
    }
}

