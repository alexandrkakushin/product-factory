package ru.pf.service.conf.check;

import org.springframework.stereotype.Service;
import ru.pf.metadata.object.MetadataObject;
import ru.pf.metadata.object.Conf;
import ru.pf.metadata.object.IMetadataObject;

import java.util.*;

/**
 * Проверка на дубли представлений объектов (например, окно "Все функции")
 * @author a.kakushin
 */
@Service
public class DuplicateViewCheck implements ServiceCheck<IMetadataObject> {

    @Override
    public List<IMetadataObject> check(Conf conf) {
        List<IMetadataObject> result = new ArrayList<>();

        Map<Class, List<IMetadataObject>> typeObjects = new HashMap<>();

        // Уникальность имени проверяется в пределах типа объекта метаданных
        Set<IMetadataObject> allObjects = conf.getAllObjects();
        for (IMetadataObject object : allObjects) {
            Class objectClass = object.getClass();

            List<IMetadataObject> current = typeObjects.get(objectClass);
            if (current == null) {
                current = new ArrayList<>();
                typeObjects.put(objectClass, current);
            }
            current.add(object);
        }

        ArrayList<IMetadataObject> duplicate = new ArrayList<>();
        for (Class type : typeObjects.keySet()) {
            List<IMetadataObject> objects = typeObjects.get(type);

            duplicate.clear();
            for (IMetadataObject object : objects) {
                String viewObject = ((MetadataObject) object).getSynonym();
                if (viewObject.isEmpty()) {
                    viewObject = ((MetadataObject) object).getName();
                }

                for (IMetadataObject potentialDuplicate : objects) {
                    if (object.equals(potentialDuplicate)) {
                        continue;
                    }

                    String viewPotential = ((MetadataObject) potentialDuplicate).getSynonym();
                    if (viewPotential.isEmpty()) {
                        viewPotential = ((MetadataObject) potentialDuplicate).getName();
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

    @Override
    public String getAlias() {
        return "Дубли в представлениях объектов";
    }
}
