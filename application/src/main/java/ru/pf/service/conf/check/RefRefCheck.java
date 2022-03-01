package ru.pf.service.conf.check;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.springframework.stereotype.Service;
import ru.pf.metadata.MetadataJsonView;
import ru.pf.metadata.object.Conf;
import ru.pf.metadata.object.IMetadataObject;
import ru.pf.metadata.Module;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Проверка ошибок вида "Ссылка.Ссылка" в запросах и в программном коде
 * @author a.kakushin
 */
@Service
public class RefRefCheck implements ServiceCheck<RefRefCheck.Response> {

    @Override
    public List<RefRefCheck.Response> check(Conf conf) throws InvocationTargetException, IllegalAccessException {
        Map<Module, IMetadataObject> modules = conf.getAllModules();

        List<RefRefCheck.Response> result = new ArrayList<>();
        for (Module module : modules.keySet()) {
            if (module.getText() == null) {
                continue;
            }

            Response response = null;
            String[] lines = module.getText().split(System.getProperty("line.separator"));
            for (int i = 0; i < lines.length; i++) {
                int found = 0;
                while ((found = lines[i].toLowerCase().indexOf("ссылка.ссылка", found)) != -1) {
                    found = found + 5;
                    if (response == null) {
                        response = new Response(modules.get(module), module, new ArrayList<>());
                        result.add(response);
                    }
                    response.getFound().add(String.format("(%s,%s)", i + 1, found + 1));
                }
            }
        }

        return result;
    }

    @Override
    public String getAlias() {
        return "Ссылка.Ссылка";
    }

    @Data
    @JsonView(MetadataJsonView.List.class)
    public class Response {

        private IMetadataObject object;
        private Module module;
        private List<String> found;

        public Response(IMetadataObject object, Module module, List<String> found) {
            this.object = object;
            this.module = module;
            this.found = found;
        }

        public List<String> getFound() {
            return found;
        }
    }
}
