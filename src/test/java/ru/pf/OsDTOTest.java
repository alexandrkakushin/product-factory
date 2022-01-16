package ru.pf;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.pf.entity.Os;
import ru.pf.entity.dto.OsDTO;

import static org.junit.Assert.assertEquals;

/**
 * Тестирование DTO "Операционные системы"
 * @author a.kakushin
 */
public class OsDTOTest {

    private Os os;
    private Os os2;

    @Before
    public void setup() {
        os = new Os(1L, "os", null);
        os2 = new Os("os2");
    }

    @Test
    public void testToDto() {
        OsDTO dto = OsDTO.toDto(os);
        assertEquals(os.getId().longValue(), dto.getId().longValue());
        assertEquals(os.getName(), dto.getName());
        assertEquals(os.getComment(), dto.getComment());

        dto = OsDTO.toDto(os2);
        Assert.assertNull(dto.getId());
        Assert.assertNull(dto.getComment());
    }
}