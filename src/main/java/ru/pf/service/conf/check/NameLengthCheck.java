package ru.pf.service.conf.check;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pf.metadata.object.MetadataObject;
import ru.pf.metadata.object.Conf;
import ru.pf.metadata.object.IMetadataObject;
import ru.pf.service.PropertiesService;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Проверка длины имен объектов метаданных, которая не должна превышать более 80-ти символов
 * @author a.kakushin
 */
@Service
public class NameLengthCheck implements ServiceCheck<IMetadataObject> {

    private static int defaultLength = 80;

    @Autowired
    PropertiesService propertiesService;

    @Override
    public List<IMetadataObject> check(Conf conf) throws InvocationTargetException, IllegalAccessException {
        List<IMetadataObject> result = new ArrayList<>();

        int length = propertiesService.getCheckNameLength();
        if (length == 0) {
            length = defaultLength;
        }

        for (IMetadataObject object : conf.getAllObjects()) {
            if (((MetadataObject) object).getName().length() >= length) {
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
