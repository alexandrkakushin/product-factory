package ru.pf.service.vcs;

import ru.pf.entity.Project;

/**
 * Интерфейс для определения доступных действия с системами контроля версий
 * @author a.kakushin
 */
public interface VCS {

    /**
     * Обновление программного кода
     */
    void pull(Project project) throws VCSException;
}
