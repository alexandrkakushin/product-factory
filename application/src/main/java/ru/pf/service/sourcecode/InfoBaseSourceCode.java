package ru.pf.service.sourcecode;

import org.springframework.stereotype.Component;
import ru.pf.entity.Project;

/**
 * Компонент для получения исходных кодов из информационной базы как основной конфигурации, так и расширений
 * @author a.kakushin
 */
@Component
@SourceCodeRepository(SourceCodeRepository.Types.INFO_BASE)
public class InfoBaseSourceCode implements SourceCode {

    /**
     * Обновление из конфигурации / конфигурации расширения информационной базы
     * @param project Проект
     * @throws SourceCodeException Ошибки при работе с информационной базой
     */
    @Override
    public void pull(Project project) throws SourceCodeException {
        if (project.getType() == Project.Type.EXTENSION) {
            
        }
    }
}
