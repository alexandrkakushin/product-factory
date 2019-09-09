package ru.pf.metadata.reader;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.pf.metadata.object.AbstractMetadataObject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author a.kakushin
 */
public class ObjectReader {

    static XPath path = XPathFactory.newInstance().newXPath();

    private Path fileXml;
    private Document doc;

    public ObjectReader(Path fileXml) {
        this.fileXml = fileXml;

        // todo: добавить свои исключения
        try {
            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            this.doc = docBuilder.parse(fileXml.toFile());

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    public List<String> readChild(String expression) {
        List<String> result = new ArrayList<>();

        try {
            NodeList nodes = (NodeList) path.evaluate(expression, doc, XPathConstants.NODESET);
            for (int i = 0; i < nodes.getLength(); i++) {
                result.add(nodes.item(i).getTextContent().trim());
            }

            return result;

        } catch (XPathExpressionException e) {
            // todo: добавить свои исключения
            e.printStackTrace();
        }
        return null;
    }

    public boolean readBool(String expression) {
        try {
            return Boolean.valueOf(path.evaluate(expression, doc));
        } catch (XPathExpressionException e) {
            // todo: добавить свои исключения
            e.printStackTrace();
        }
        return Boolean.parseBoolean(null);
    }

    public int readInt(String expression) {
        try {
            return Integer.valueOf(path.evaluate(expression, doc));
        } catch (XPathExpressionException e) {
            // todo: добавить свои исключения
            e.printStackTrace();
        }
        return Integer.parseInt(null);
    }

    public String read(String expression) {
        try {
            return path.evaluate(expression, doc).trim();
        } catch (XPathExpressionException e) {
            // todo: добавить свои исключения
            e.printStackTrace();
        }
        return null;
    }

    public UUID readUUID(String expression) {
        try {
            return UUID.fromString(path.evaluate(expression, doc));
        } catch (XPathExpressionException e) {
            // todo: добавить свои исключения
            e.printStackTrace();
        }
        return null;
    }


    public void fillCommonField(AbstractMetadataObject object) {

        String nodeObject = "/MetaDataObject/" + object.getXmlName();

        object.setUuid(readUUID(nodeObject + "/@uuid"));
        object.setName(read(nodeObject+ "/Properties/Name"));
        object.setSynonym(read(nodeObject + "/Properties/Synonym/item/content"));
        object.setComment(read(nodeObject+ "/Properties/Comment"));
    }
}
