package ru.pf.service.conf.check;

import org.springframework.stereotype.Service;
import ru.pf.metadata.Method;
import ru.pf.metadata.object.Conf;

import java.util.List;

/**
 * @author a.kakushin
 */
@Service
public class EmptyMethodCheck {

    /**
     * Проверка наличия методов без реализации
     * @param conf Конфигурация
     * @return список методов
     */
    public List<Method> check(Conf conf) {
        return null;
    }
}
