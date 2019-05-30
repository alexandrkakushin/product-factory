package ru.pf.service.conf.check;

import org.springframework.stereotype.Service;
import ru.pf.metadata.object.Conf;
import ru.pf.metadata.object.MetadataObject;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Проверка на "автосинонимы"
 * @author a.kakushin
 */
@Service
public class AutoSynonymCheck implements ServiceCheck<MetadataObject> {

    @Override
    public List<MetadataObject> check(Conf conf) throws InvocationTargetException, IllegalAccessException {
        return null;
    }

    @Override
    public String getAlias() {
        return "Автосинонимы";
    }
}
