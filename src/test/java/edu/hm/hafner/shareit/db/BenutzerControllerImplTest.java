package edu.hm.hafner.shareit.db;

import static org.mockito.Mockito.*;

import org.junit.Test;

/**
 * Tests the class BenutzerControllerImpl.
 *
 * @author Ulli Hafner
 */
public class BenutzerControllerImplTest {
    private static final String EMAIL = "email@adresse";

    /**
     * Prüft, ob die contains Methode korrekt an die Instanz des {@link UserController} delegiert wird.
     */
    @Test
    public void testeContains() {
        UserController mockedUserController = mock(UserController.class);
        BenutzerControllerImpl controller = new BenutzerControllerImpl(mockedUserController);

        controller.containsEmail(EMAIL);

        verify(mockedUserController).containsEmail(EMAIL);
    }
}

