package edu.hm.hafner.shareit.model;

/**
 * Kann Bücher ausleihen und verleihen.
 *
 * @author Ulli Hafner
 */
public class Benutzer extends Registrierung {
    private final boolean hasAdminRights;

    /**
     * Erzeugt einen neuen {@link Benutzer}.
     *
     * @param nachname
     *            Nachname des Benutzers
     * @param vorname
     *            Vorname des Benutzers
     * @param email
     *            Email Adresse des Benutzers
     * @param passwort
     *            Passwort des Benutzers
     */
    public Benutzer(final String nachname, final String vorname, final String email, final String passwort) {
        this(email, nachname, vorname, passwort, false);
    }

    /**
     * Erzeugt einen neuen {@link Benutzer} mit oder ohne Administratorrechte.
     *
     * @param nachname
     *            Nachname des Benutzers
     * @param vorname
     *            Vorname des Benutzers
     * @param email
     *            Email Adresse des Benutzers
     * @param passwort
     *            Passwort des Benutzers
     * @param isAdministrator
     *            Legt fest, ob der neue Benutzer Administratorrechte hat
     *
     */
    public Benutzer(final String nachname, final String vorname, final String email, final String passwort, final boolean isAdministrator) {
        super(email, nachname, vorname, passwort);

        hasAdminRights = isAdministrator;
    }

    /**
     * Liefert zurück, ob dieser Nutzer erweiterte Rechte besitzt.
     *
     * @return <code>true</code> falls er erweiterte Rechte besitzt,
     *         <code>false</code> otherwise
     */
    public boolean isAdminstrator() {
        return hasAdminRights;
    }
}

