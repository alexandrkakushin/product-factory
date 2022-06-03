package ru.pf.entity.licence;

import lombok.Getter;
import lombok.Setter;
import ru.pf.entity.PfEntity;

import javax.persistence.*;

/**
 * Журнал создания защищенного решения
 * Используется для истории и дальнейшего скачивания файла
 * @author a.kakushin
 */
@Getter
@Setter
@Entity
@Table(name = "JOURNAL_BUILD_SOLUTION")
public class JournalBuildSolution implements PfEntity<JournalBuildSolution> {

    /**
     * Идентификатор записи
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

}
