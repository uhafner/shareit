package edu.hm.hafner.shareit.util;

import org.junit.Before;

/**
 * Basisklasse für Tests die die Datenbank zurücksetzen müssen.
 */
@SuppressWarnings ("PMD.AbstractClassWithoutAbstractMethod")
public abstract class AbstractDatabaseTest {
    /**
     * Setzt die Datenbank zurück.
     */
    @Before
    public void clearDatabase() {
        DatabaseFactory.INSTANCE.reset();
    }
}
