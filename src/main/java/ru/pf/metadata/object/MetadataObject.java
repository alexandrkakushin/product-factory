package ru.pf.metadata.object;

import com.fasterxml.jackson.annotation.JsonView;
import org.xml.sax.SAXException;
import ru.pf.metadata.MetadataJsonView;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.nio.file.Path;

/**
 * @author a.kakushin
 */
public interface MetadataObject<T> {

    // todo: AOP (advice)
    void parse() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException;

    @JsonView({MetadataJsonView.List.class})
    default String getMetadataName() {
        return AbstractObject.getMetadataName(this.getClass());
    }

    default String getShortName(Path path) {
        String fileName = path.getFileName().toString();
        return fileName.substring(0, fileName.lastIndexOf("."));
    }
}
