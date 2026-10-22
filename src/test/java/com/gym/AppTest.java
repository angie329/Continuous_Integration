package com.gym;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {

    @Test
    void testAppGreeting() {
        App app = new App();
        assertEquals("Gym Membership App Ready", app.getGreeting());
    }
}