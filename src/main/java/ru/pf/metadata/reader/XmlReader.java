package ru.pf.metadata.reader;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
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
public class XmlReader {

    private final XPath path = XPathFactory.newInstance().newXPath();
    private Document doc;
    private final Path file;

    public XmlReader(Path file) {
        this.file = file;
        parse();
    }

    private void parse() {
        try {
            DocumentBuilderFactory df = DocumentBuilderFactory.newDefaultInstance();
            df.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
            DocumentBuilder docBuilder = df.newDocumentBuilder();
            this.doc = docBuilder.parse(file.toFile());

            // TODO: добавить свои исключения
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
        } catch (XPathExpressionException e) {
            // todo: добавить свои исключения
            e.printStackTrace();
        }

        return result;
    }

    public boolean readBool(String expression) {
        try {
            return Boolean.valueOf(path.evaluate(expression, doc));
        } catch (XPathExpressionException e) {
            // todo: добавить свои исключения
            e.printStackTrace();
        }
        return false;
    }

    public int readInt(String expression) {
        try {
            return Integer.valueOf(path.evaluate(expression, doc));
        } catch (XPathExpressionException e) {
            // todo: добавить свои исключения
            e.printStackTrace();
        }
        return 0;
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

}
