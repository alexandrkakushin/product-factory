package ru.pf.metadata.type;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Стандартный реквизит
 * @author a.kakushin
 */
@Data
public class StandardAttribute {

    /**
     * Наименование стандартного реквизита
     */
    private String name;

    /**
     * Русскоязычное наименование
     */
    private String russianName;

    /**
     * Конструктор по наименованию
     * @param name Наименование стандартного реквизита
     */
    public StandardAttribute(String name) {
        setName(name);
    }

    /**
     * Установка имени стандартного реквизита
     * @param name Имя стандартного реквизита
     */
    public void setName(String name) {
        this.name = name;
        setRussianName();
    }

    /**
     * Установка русскоязычного наименования
     */
    private void setRussianName() {
        // Русскоязычное наименование
        Map<String, String> titles = new HashMap<>();
        titles.put("PredefinedDataName", "ИмяПредопределенныхДанных");
        titles.put("Predefined", "Предопределенный");
        titles.put("Ref", "Ссылка");
        titles.put("DeletionMark", "ПометкаУдаления");
        titles.put("IsFolder", "ЭтоГруппа");
        titles.put("Owner", "Владелец");
        titles.put("Parent", "Родитель");
        titles.put("Description", "Наименование");
        titles.put("Code", "Код");

        this.russianName = titles.get(this.name);
        if (this.russianName == null) {
            this.russianName = name;
        }
    }
}