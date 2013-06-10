package edu.hm.hafner.shareit.db;

/**
 * Erzeugt, findet und ändert Benutzer.
 *
 * @author Ulli Hafner
 */
public class BenutzerControllerImpl implements BenutzerController {
    private final UserController delegate;

    /**
     * Creates a new instance of {@link BenutzerControllerImpl}.
     */
    public BenutzerControllerImpl() {
        this(new UserControllerImpl());
    }

    /**
     * Creates a new instance of {@link BenutzerControllerImpl}.
     *
     * @param delegate
     *            die aufzurufende Implementierung
     */
    public BenutzerControllerImpl(final UserController delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean containsEmail(final String email) {
        return delegate.containsEmail(email);
    }
}

