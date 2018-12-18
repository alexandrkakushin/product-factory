package ru.pf.metadata.object;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.nio.file.Path;

/**
 * @author a.kakushin
 */
public interface MetadataObject<T> {

    void parse() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException;

    @JsonIgnore
    default String getMetadataName() {
        return this.getClass().getSimpleName();
    }

    default String getShortName(Path path) {
        String fileName = path.getFileName().toString();
        return fileName.substring(0, fileName.lastIndexOf("."));
    }
}
