package ru.pf;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.pf.entity.Os;
import ru.pf.entity.dto.OsDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Тестирование DTO "Операционные системы"
 * @author a.kakushin
 */
class OsDTOTest {

    private Os os;
    private Os os2;

    @BeforeEach
    public void setup() {
        os = new Os(1L, "os", null);
        os2 = new Os("os2");
    }

    @Test
    void testToDto() {
        OsDTO dto = OsDTO.toDto(os);
        assertEquals(os.getId().longValue(), dto.getId().longValue());
        assertEquals(os.getName(), dto.getName());
        assertEquals(os.getComment(), dto.getComment());

        dto = OsDTO.toDto(os2);
        assertNull(dto.getId());
        assertNull(dto.getComment());
    }
}