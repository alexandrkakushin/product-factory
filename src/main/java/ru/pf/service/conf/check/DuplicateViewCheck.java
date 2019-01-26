package ru.pf.service.conf.check;

import org.springframework.stereotype.Service;
import ru.pf.metadata.object.AbstractObject;
import ru.pf.metadata.object.Conf;
import ru.pf.metadata.object.MetadataObject;

import java.util.*;

/**
 * Проверка на дубли представлений объектов (например, окно "Все функции")
 * @author a.kakushin
 */
@Service
public class DuplicateViewCheck {

    public List<MetadataObject> check(Conf conf) {
        List<MetadataObject> result = new ArrayList<>();

        Map<Class, List<MetadataObject>> typeObjects = new HashMap<>();

        // Уникальность имени проверяется в пределах типа объекта метаданных
        Set<MetadataObject> allObjects = conf.getAllObjects();
        for (MetadataObject object : allObjects) {
            Class objectClass = object.getClass();

            List<MetadataObject> current = typeObjects.get(objectClass);
            if (current == null) {
                current = new ArrayList<>();
                typeObjects.put(objectClass, current);
            }
            current.add(object);
        }

        ArrayList<MetadataObject> duplicate = new ArrayList<>();
        for (Class type : typeObjects.keySet()) {
            List<MetadataObject> objects = typeObjects.get(type);

            duplicate.clear();
            for (MetadataObject object : objects) {
                String viewObject = ((AbstractObject) object).getSynonym();
                if (viewObject.isEmpty()) {
                    viewObject = ((AbstractObject) object).getName();
                }

                for (MetadataObject potentialDuplicate : objects) {
                    if (object.equals(potentialDuplicate)) {
                        continue;
                    }

                    String viewPotential = ((AbstractObject) potentialDuplicate).getSynonym();
                    if (viewPotential.isEmpty()) {
                        viewPotential = ((AbstractObject) potentialDuplicate).getName();
                    }

                    if (viewObject.equalsIgnoreCase(viewPotential)) {
                        if (!duplicate.contains(potentialDuplicate)) {
                            duplicate.add(potentialDuplicate);
                        }

                        if (!duplicate.contains(object)) {
                            duplicate.add(object);
                        }
                    }
                }
            }

            result.addAll(duplicate);
        }

        return result;
    }
}
