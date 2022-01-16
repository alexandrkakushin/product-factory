package ru.pf.entity;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertNull;

/**
 * @author a.kakushin
 */
public class OsTest {

    @Test
    public void constructorTest(){
        Os os = new Os(1L, "test", null);

        Assert.assertNotNull(os.getId());
        Assert.assertEquals(1L, os.getId().longValue());
        Assert.assertNotNull(os.getName());
        assertNull(os.getComment());
    }
}