package ru.pf.service.vcs;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author a.kakushin
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
public @interface SourceCodeRepository {

    Types value();

    enum Types {
        DIRECTORY,
        CONFIGURATION_REPOSITORY,
        GIT
    }
}
