package ru.pf.core.sourcecode;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Виды источников исходных кодов
 * @author a.kakushin
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
public @interface SourceCodeRepository {

    Types value();

    enum Types {
        /**
         * Каталог
         */
        DIRECTORY,
        /**
         * Хранилище конфигурации 1С:Предприятие
         */
        CONFIGURATION_REPOSITORY,
        /**
         * GIT
         */
        GIT,
        /**
         * Информационная база 1С:Предприятие
         */
        INFO_BASE
    }
}
