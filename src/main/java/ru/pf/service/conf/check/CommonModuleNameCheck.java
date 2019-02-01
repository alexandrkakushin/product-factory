package ru.pf.service.conf.check;

import org.springframework.stereotype.Service;
import ru.pf.metadata.object.Conf;
import ru.pf.metadata.object.MetadataObject;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Проверка имен общих модулей, которые должны формироваться согласно стандартам 1С
 * @author a.kakushin
 */
@Service
public class CommonModuleNameCheck implements ServiceCheck<MetadataObject> {

    @Override
    public List<MetadataObject> check(Conf conf) throws InvocationTargetException, IllegalAccessException {
        return null;
    }
}
