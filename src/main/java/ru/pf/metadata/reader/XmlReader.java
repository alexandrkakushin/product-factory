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
 * Чтение XML-файлов конфигурации
 * @author a.kakushin
 */
public class XmlReader {

    private final XPath path = XPathFactory.newInstance().newXPath();
    private Document doc;
    private final Path file;

    /**
     * Конструктор для связи с обрабатываемым файлом
     * @param file XML-файл
     */
    public XmlReader(Path file) {
        this.file = file;
        parse();
    }

    /**
     * Парсинг XML-файла
     */
    private void parse() {
        try {
            DocumentBuilderFactory df = DocumentBuilderFactory.newDefaultInstance();
            df.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            df.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
            DocumentBuilder docBuilder = df.newDocumentBuilder();
            this.doc = docBuilder.parse(file.toFile());

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Получение списка по XPath-выражению
     * @param expression XPath-выражение
     * @return Список (List) строк
     */
    public List<String> readChild(String expression) {
        List<String> result = new ArrayList<>();
        try {
            NodeList nodes = (NodeList) path.evaluate(expression, doc, XPathConstants.NODESET);
            for (int i = 0; i < nodes.getLength(); i++) {
                result.add(nodes.item(i).getTextContent().trim());
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Получение булевого значения по XPath-выражению из строки
     * @param expression XPath-выражение
     * @return Булево
     */
    public boolean readBool(String expression) {
        try {
            return Boolean.parseBoolean(path.evaluate(expression, doc));
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Получение целочисленного значения по XPath-выражению из строки
     * @param expression XPath-выражение
     * @return Целочисленное значение (int)
     */
    public int readInt(String expression) {
        try {
            return Integer.parseInt(path.evaluate(expression, doc));
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Получение строкового значения по XPath-выражению из строки
     * @param expression XPath-выражение
     * @return Строка
     */
    public String read(String expression) {
        try {
            return path.evaluate(expression, doc).trim();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Получение уникального идентификатора по XPath-выражению из строки
     * @param expression XPath-выражение
     * @return UUID
     */
    public UUID readUUID(String expression) {
        try {
            return UUID.fromString(path.evaluate(expression, doc));
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return null;
    }
}