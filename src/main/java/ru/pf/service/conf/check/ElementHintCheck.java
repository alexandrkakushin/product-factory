package ru.pf.service.conf.check;

import org.springframework.stereotype.Service;
import ru.pf.metadata.object.Conf;
import ru.pf.metadata.object.MetadataObject;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Поиск элементов, подсказки которых сгенерированы автоматически (по сути равные автосинонимам)
 * @author a.kakushin
 */
@Service
public class ElementHintCheck implements ServiceCheck<MetadataObject> {

    @Override
    public List<MetadataObject> check(Conf conf) throws InvocationTargetException, IllegalAccessException {
        return null;
    }
}
