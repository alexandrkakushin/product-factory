package ru.pf.entity.dto;

import lombok.Getter;
import lombok.Setter;
import ru.pf.entity.Os;

/**
 * DTO-класс для класса Os (Операционные системы)
 * @author a.kakushin
 */
@Getter
@Setter
public class OsDTO {

    private Long id;
    private String name;
    private String comment;

    public static OsDTO toDto(Os os) {
        return new OsDTO() {
            {
                setId(os.getId());
                setName(os.getName());
                setComment(os.getComment());
            }
        };
    }

    public Os toOs() {
        return new Os(id, name, comment);
    }
}
