package ru.pf.sourcecode;

import ru.pf.core.sourcecode.SourceCodeException;
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