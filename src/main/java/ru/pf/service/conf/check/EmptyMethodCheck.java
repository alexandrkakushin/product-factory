package ru.pf.service.conf.check;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.springframework.stereotype.Service;
import ru.pf.metadata.MetadataJsonView;
import ru.pf.metadata.Method;
import ru.pf.metadata.Module;
import ru.pf.metadata.object.Conf;
import ru.pf.metadata.object.MetadataObject;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Проверка присутствия методов без реализации
 * @author a.kakushin
 */
@Service
public class EmptyMethodCheck implements ServiceCheck<EmptyMethodCheck.Response> {

    @Override
    public List<EmptyMethodCheck.Response> check(Conf conf) throws InvocationTargetException, IllegalAccessException {
        List<EmptyMethodCheck.Response> result = new ArrayList<>();

        Map<Module, MetadataObject> modules = conf.getAllModules();
        modules.keySet().stream()
                .forEach(module -> module.getMethods().forEach(method -> {
                    if (method.isEmpty()) {
                        result.add(new Response(conf.getAllModules().get(module), module, method));
                    }
                }));

        return result;
    }

    @Override
    public String getAlias() {
        return "Методы без реализации";
    }

    @Data
    @JsonView(MetadataJsonView.List.class)
    public class Response {

        private MetadataObject object;
        private Module module;
        private Method method;

        public Response(MetadataObject object, Module module, Method method) {
            this.object = object;
            this.module = module;
            this.method = method;
        }
    }
}
