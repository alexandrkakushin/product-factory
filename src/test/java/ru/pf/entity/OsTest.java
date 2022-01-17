package ru.pf.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author a.kakushin
 */
class OsTest {

    @Test
    void constructorTest(){
        Os os = new Os(1L, "test", null);

        assertNotNull(os.getId());
        assertEquals(1L, os.getId().longValue());
        assertNotNull(os.getName());
        assertNull(os.getComment());
    }
}