package ru.pf.service.conf.check;

import org.springframework.stereotype.Service;
import ru.pf.metadata.object.Conf;
import ru.pf.metadata.object.IMetadataObject;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Анализ ролей на предмет ошибок выставления прав над объектами метаданных
 * Запрет таких прав, как:
 *  - Интерактивное удаление
 *  - Интерактивное удаление помеченных
 * @author a.kakushin
 */
@Service
public class RightsCheck implements ServiceCheck<IMetadataObject> {
    @Override
    public List<IMetadataObject> check(Conf conf) throws InvocationTargetException, IllegalAccessException {
        List<IMetadataObject> result = new ArrayList<>();
        result.clear();

        return result;
    }

    @Override
    public String getAlias() {
        return "Проверка прав в ролях";
    }
}
