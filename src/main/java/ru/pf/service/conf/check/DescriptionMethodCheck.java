package ru.pf.service.conf.check;

import org.springframework.stereotype.Service;
import ru.pf.metadata.Method;
import ru.pf.metadata.object.Conf;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Проверка наличия описаний (комментариев) к методам в общих модулях
 * @author a.kakushin
 */
@Service
public class DescriptionMethodCheck implements ServiceCheck<Method> {

    @Override
    public List<Method> check(Conf conf) throws InvocationTargetException, IllegalAccessException {
        return null;
    }
}
