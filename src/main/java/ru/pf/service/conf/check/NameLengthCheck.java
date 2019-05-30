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

/**
 * Проверка длины имен объектов метаданных, которая не должна превышать более 80-ти символов
 * @author a.kakushin
 */
@Service
public class NameLengthCheck implements ServiceCheck<MetadataObject> {

    private static int defaultLength = 80;

    @Autowired
    PropertiesService propertiesService;

    @Override
    public List<MetadataObject> check(Conf conf) throws InvocationTargetException, IllegalAccessException {
        List<MetadataObject> result = new ArrayList<>();

        int length = propertiesService.getCheckNameLength();
        if (length == 0) {
            length = defaultLength;
        }

        for (MetadataObject object : conf.getAllObjects()) {
            if (((AbstractObject) object).getName().length() >= length) {
                result.add(object);
            }
        }
        return result;
    }

    @Override
    public String getAlias() {
        return "Длина имен объектов метаданных";
    }
}
