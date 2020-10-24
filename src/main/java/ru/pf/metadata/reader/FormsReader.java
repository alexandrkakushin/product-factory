package ru.pf.metadata.reader;

import ru.pf.metadata.object.Form;
import ru.pf.metadata.object.MetadataObject;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Класс-читатель, позволяющий получить множество форм объекта метаданных
 * @author a.kakushin
 */
public class FormsReader {

    private FormsReader() {
        throw  new IllegalStateException("Utility reader class");
    }

    /**
     * Чтение форм объекта метаданных
     * @param xmlReader Экземпляр класса для чтения открытого XML-файла
     * @param object Объект метаданных
     * @return Множество форм
     * @throws ReaderException Обобщенное исключение парсинга
     */
    public static Set<Form> read(XmlReader xmlReader, MetadataObject object) throws ReaderException {

        String nodeRoot = Utils.nodeRoot(object);

        List<String> formNames = xmlReader.readChild(nodeRoot + "/ChildObjects/Form");
        Path pathForms = object.getFile()
                .getParent()
                .resolve(object.getShortName(object.getFile()))
                .resolve("Forms");

        Set<Form> forms = new HashSet<>();
        for (String formName : formNames) {
            Form form = new Form(pathForms.resolve(formName + ".xml"));
            form.parse();
            forms.add(form);
        }

        return forms;
    }
}
