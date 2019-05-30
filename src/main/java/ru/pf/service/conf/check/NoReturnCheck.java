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
 * Поиск функций, которые не возвращают результат
 * @author a.kakushin
 */
@Service
public class NoReturnCheck implements ServiceCheck<NoReturnCheck.Response> {

    @Override
    public List<Response> check(Conf conf) throws InvocationTargetException, IllegalAccessException {
        List<Response> result = new ArrayList<>();

        Map<Module, MetadataObject> modules = conf.getAllModules();
        for (Module module : modules.keySet()) {
            Response response = null;

            for (Method method : module.getMethods()) {
                if (method.isFunction()) {
                    if (method.getCode().toLowerCase().indexOf("возврат") != -1) {
                        if (response == null) {
                            response = new Response(modules.get(module), module, new ArrayList<>());
                            result.add(response);
                        }
                        response.getFound().add(method);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public String getAlias() {
        return "Функции без возврата результата";
    }

    @Data
    @JsonView(MetadataJsonView.List.class)
    public static class Response {

        private MetadataObject object;
        private Module module;
        private List<Method> found;

        public Response(MetadataObject object, Module module, List<Method> found) {
            this.object = object;
            this.module = module;
            this.found = found;
        }

        public List<Method> getFound() {
            return found;
        }
    }
}
