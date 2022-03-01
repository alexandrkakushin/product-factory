package ru.pf.service.conf.check;

import ru.pf.metadata.object.Conf;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author a.kakushin
 */
public interface ServiceCheck<T> {

    /**
     * Проверка конфигурации по определенному правилу
     * @param conf Конфигурация
     * @return Список объектов, например список объектов метаданных или методов
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    List<T> check(Conf conf) throws InvocationTargetException, IllegalAccessException;

    String getAlias();
}
