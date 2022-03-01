package ru.pf.metadata.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * Утилитный класс для работы с аннотациями метаданных конфигурации
 */
@Component
public class MetadataAnnotations {

    /**
     * Получение списка доступных аннотаций метаданных
     * @return Множество аннотаций метаданных
     */
    public Set<Class<? extends Annotation>> getAvailable() {
        Set<Class<? extends Annotation>> result = new HashSet<>();

        result.add(Attributes.class);
        result.add(CommandModule.class);
        result.add(Commands.class);
        result.add(Forms.class);
        result.add(ManagerModule.class);
        result.add(ObjectModule.class);
        result.add(Owners.class);
        result.add(PlainModule.class);
        result.add(Predefined.class);
        result.add(RecordSetModule.class);
        result.add(StandardAttributes.class);
        result.add(TabularSections.class);
        result.add(ValueManagerModule.class);

        return result;
    }

    /**
     * Преобразование имени класса в "верблюжью" запись
     * @param simpleName Имя класса
     * @return имя класса (camel-case)
     */
    public String toCamelCase(String simpleName) {

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < simpleName.length(); i++) {
            char currentChar = simpleName.charAt(i);
            if (i == 0) {
                builder.append(Character.toLowerCase(currentChar));
            } else {
                builder.append(currentChar);
            }
        }

        return builder.toString();
    }
}
