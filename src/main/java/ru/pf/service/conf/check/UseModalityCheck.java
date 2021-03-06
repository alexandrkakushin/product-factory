package ru.pf.service.conf.check;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.springframework.stereotype.Service;
import ru.pf.metadata.MetadataJsonView;
import ru.pf.metadata.Module;
import ru.pf.metadata.object.Conf;
import ru.pf.metadata.object.IMetadataObject;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Проверка на наличие модальных вызовов, устаревших в новых версиях платформы
 * @author a.kakushin
 */
@Service
public class UseModalityCheck implements ServiceCheck<UseModalityCheck.Response> {

    @Override
    public List<UseModalityCheck.Response> check(Conf conf) throws InvocationTargetException, IllegalAccessException {
        Map<Module, IMetadataObject> modules = conf.getAllModules();

        List<String> modalWords = getModalWords();

        List<UseModalityCheck.Response> result = new ArrayList<>();
        for (Module module : modules.keySet()) {
            if (module.getText() == null) {
                continue;
            }

            UseModalityCheck.Response response = null;
            String[] lines = module.getText().split(System.getProperty("line.separator"));
            for (int i = 0; i < lines.length; i++) {
                String line = lines[i].toLowerCase();
                if (line.trim().length() == 0) {
                    continue;
                }
                for (String word : modalWords) {
                    int found = 0;
                    while ((found = line.indexOf(word, found)) != -1) {
                        if (response == null) {
                            response = new Response(modules.get(module), module, new ArrayList<>());
                            result.add(response);
                        }
                        response.getFound().add(String.format("(%s,%s)", i + 1, found + 1));
                        found = found + word.length();
                    }
                }
            }
        }
        return result;
    }

    @Override
    public String getAlias() {
        return "Модальные вызовы";
    }

    private List<String> getModalWords() {
        List<String> words = new ArrayList<>();

        // todo: перенести в конфиг
        words.add("Вопрос");
        words.add("ОткрытьФормуМодально");
        words.add("ОткрытьЗначение");
        words.add("ВвестиДату");
        words.add("ВвестиЗначение");
        words.add("ВвестиСтроку");
        words.add("ВвестиЧисло");

        return words.stream()
                .map(item -> item.toLowerCase()).collect(Collectors.toList());
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
