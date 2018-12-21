package ru.pf.service.conf.check;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pf.metadata.object.AbstractObject;
import ru.pf.metadata.object.Conf;
import ru.pf.metadata.object.MetadataObject;
import ru.pf.service.PropertiesService;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author a.kakushin
 */
@Service
public class NameLengthCheck {

    private static int defaultLength = 80;

    @Autowired
    PropertiesService propertiesService;

    /**
     * Длина объектов метаданных не должна превышать более 80-ти символов
     * @param conf Конфигурация
     * @return список объектов метаданных
     */
    public List<MetadataObject> check(Conf conf) throws InvocationTargetException, IllegalAccessException {
        List<MetadataObject> result = new ArrayList<>();

        int length = propertiesService.getCheckNameLength();
        if (length == 0) {
            length = defaultLength;
        }

        for (java.lang.reflect.Method method : Conf.class.getMethods()) {
            if (method.getReturnType().equals(Set.class)) {
                Set<MetadataObject> objects = (Set<MetadataObject>) method.invoke(conf);
                // todo: streamapi
                for (MetadataObject object : objects) {
                    if (((AbstractObject) object).getName().length() >= length) {
                        result.add(object);
                    }
                }
            }
        }
        return result;
    }
}
