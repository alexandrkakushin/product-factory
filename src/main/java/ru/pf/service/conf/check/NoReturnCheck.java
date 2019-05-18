package ru.pf.service.conf.check;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.springframework.stereotype.Service;
import ru.pf.metadata.MetadataJsonView;
import ru.pf.metadata.Module;
import ru.pf.metadata.object.Conf;
import ru.pf.metadata.object.MetadataObject;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * todo
 * Поиск функций, которые не возвращают результат
 * @author a.kakushin
 */
@Service
public class NoReturnCheck implements ServiceCheck<NoReturnCheck.Response> {

    @Override
    public List<Response> check(Conf conf) throws InvocationTargetException, IllegalAccessException {
        return null;
    }

    @Data
    @JsonView(MetadataJsonView.List.class)
    public class Response {

        private MetadataObject object;
        private Module module;
        private List<String> found;

        public Response(MetadataObject object, Module module, List<String> found) {
            this.object = object;
            this.module = module;
            this.found = found;
        }

        public List<String> getFound() {
            return found;
        }
    }
}
