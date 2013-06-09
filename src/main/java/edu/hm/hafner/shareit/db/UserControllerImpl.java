package edu.hm.hafner.shareit.db;

import java.util.Collection;

import edu.hm.hafner.shareit.model.Benutzer;

/**
 * Erzeugt, findet und ändert Benutzer.
 *
 * @author Ulli Hafner
 */
public class UserControllerImpl implements UserController {
    @Override
    public boolean containsEmail(final String email) {
        // FIXME Add implementation
        return false;
    }

    @Override
    public Benutzer create(final String email, final String vorname, final String nachname, final String passwort) {
        // FIXME Add implementation
        return null;
    }

    @Override
    public Benutzer findByPrimaryKey(final String email) {
        // FIXME Add implementation
        return null;
    }

    @Override
    public Collection<Benutzer> findBenutzer() {
        // FIXME Add implementation
        return null;
    }

    @Override
    public Collection<Benutzer> findByAdministrator(final boolean isAdministrator) {
        // FIXME Add implementation
        return null;
    }

    @Override
    public Collection<Benutzer> findByLocked(final boolean isLocked) {
        // FIXME Add implementation
        return null;
    }

    @Override
    public Collection<Benutzer> findByText(final String text) {
        // FIXME Add implementation
        return null;
    }

    @Override
    public Collection<Benutzer> findByTextAndAdministrator(final String text, final boolean isAdministrator) {
        // FIXME Add implementation
        return null;
    }

    @Override
    public void delete(final String email) {
        // FIXME Add implementation

    }

    @Override
    public void updateProperties(final String email, final String geaenderterVorname, final String geaenderterNachname,
            final String geaendertesPasswort, final boolean isAdministor, final boolean isLocked) {
        // FIXME Add implementation
    }
}

