
package ru.pf.service.conf.check;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pf.metadata.MetadataJsonView;
import ru.pf.metadata.Module;
import ru.pf.metadata.object.Conf;
import ru.pf.metadata.object.MetadataObject;
import ru.pf.service.PropertiesService;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Максимальная длина строки в модулях по умолчанию не должна превышать 120 символов
 * @author a.kakushin
 */
@Service
public class LineSizeCheck implements ServiceCheck<LineSizeCheck.Response> {

    static int defaultMaxSize = 120;

    @Autowired
    PropertiesService propertiesService;

    @Override
    public List<Response> check(Conf conf) throws InvocationTargetException, IllegalAccessException {
        List<LineSizeCheck.Response> result = new ArrayList<>();

        int maxLength = propertiesService.getCheckLineSize();
        if (maxLength == 0) {
            maxLength = defaultMaxSize;
        }

        // todo: stream
        Map<Module, MetadataObject> modules = conf.getAllModules();
        for (Module module : modules.keySet()) {
            if (module.getText() == null) {
                continue;
            }

            Response response = null;
            String[] lines = module.getText().split(System.getProperty("line.separator"));

            for (int i = 0; i < lines.length; i++) {
                if (lines[i].length() >= maxLength) {
                    if (response == null) {
                        response = new Response(modules.get(module), module, new ArrayList<>());
                        result.add(response);
                    }
                    response.getFound().add(new Response.Found(i, lines[i]));
                }
            }
        }
        return result;
    }

    @Override
    public String getAlias() {
        return "Длина строк";
    }

    @Data
    @JsonView(MetadataJsonView.List.class)
    public static class Response {

        private MetadataObject object;
        private Module module;
        private List<Found> found;

        Response(MetadataObject object, Module module, List<Found> found) {
            this.object = object;
            this.module = module;
            this.found = found;
        }

        public List<Found> getFound() {
            return found;
        }

        @Data
        @JsonView(MetadataJsonView.List.class)
        public static class Found {
            private int number;
            private String string;

            public Found(int number, String string) {
                this.number = number;
                this.string = string;
            }
        }
    }
}
