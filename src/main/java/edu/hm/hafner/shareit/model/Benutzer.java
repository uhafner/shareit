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
     * @param email
     *            Email Adresse des Benutzers
     * @param nachname
     *            Nachname des Benutzers
     * @param vorname
     *            Vorname des Benutzers
     * @param passwort
     *            Passwort des Benutzers
     */
    public Benutzer(final String email, final String vorname, final String nachname, final String passwort) {
        this(email, vorname, nachname, passwort, false);
    }

    /**
     * Erzeugt einen neuen {@link Benutzer} mit oder ohne Administratorrechte.
     *
     * @param email
     *            Email Adresse des Benutzers
     * @param nachname
     *            Nachname des Benutzers
     * @param vorname
     *            Vorname des Benutzers
     * @param passwort
     *            Passwort des Benutzers
     * @param isAdministrator
     *            Legt fest, ob der neue Benutzer Administratorrechte hat
     *
     */
    public Benutzer(final String email, final String vorname, final String nachname, final String passwort, final boolean isAdministrator) {
        super(email, vorname, nachname, passwort);

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

