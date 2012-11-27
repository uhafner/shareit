package edu.hm.hafner.shareit.util;

import org.junit.Before;

import edu.hm.hafner.shareit.util.DatabaseFactory;

/**
 * Basisklasse für Tests die die Datenbank zurücksetzen müssen.
 */
public abstract class AbstractDatabaseTest {
    /**
     * Setzt die Datenbank zurück.
     */
    @Before
    public void clearDatabase() {
        DatabaseFactory.INSTANCE.reset();
    }
    
}
