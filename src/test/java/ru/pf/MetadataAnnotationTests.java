package ru.pf;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.pf.metadata.annotation.MetadataAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MetadataAnnotationTests {

    @Autowired
    MetadataAnnotations metadataAnnotations;

    @Test
    void injectTest() {
        assertNotNull(metadataAnnotations);
    }

    @Test
    void camelCaseTest() {
        assertEquals("camelCase", metadataAnnotations.toCamelCase("CamelCase"));
    }
}