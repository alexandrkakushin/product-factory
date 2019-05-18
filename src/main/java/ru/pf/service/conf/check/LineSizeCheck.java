package ru.pf.service.conf.check;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.springframework.stereotype.Service;
import ru.pf.metadata.MetadataJsonView;
import ru.pf.metadata.Module;
import ru.pf.metadata.object.Conf;
import ru.pf.metadata.object.MetadataObject;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * todo
 * Максимальная длина строки в модулях не должна превышать 120 символов
 * @author a.kakushin
 */
@Service
public class LineSizeCheck implements ServiceCheck<LineSizeCheck.Response> {

    @Override
    public List<Response> check(Conf conf) throws InvocationTargetException, IllegalAccessException {
        List<LineSizeCheck.Response> result = new ArrayList<>();

        // todo: stream
        Map<Module, MetadataObject> modules = conf.getAllModules();
        for (Module module : modules.keySet()) {
            Response response = null;
            String[] lines = module.getText().split(System.getProperty("line.separator"));

            for (int i = 0; i < lines.length; i++) {
                // todo: save value in properties or db
                if (lines[i].length() >= 120) {
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

    @Data
    @JsonView(MetadataJsonView.List.class)
    public static class Response {

        private MetadataObject object;
        private Module module;

        // todo: add line's text
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
