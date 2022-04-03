package ru.pf.service.sourcecode;

import ru.pf.entity.Project;

/**
 * Интерфейс для определения доступных действия с исходниками
 * @author a.kakushin
 */
public interface SourceCode {

    /**
     * Обновление программного кода
     */
    void pull(Project project) throws SourceCodeException;

}
