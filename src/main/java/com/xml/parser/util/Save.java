package com.xml.parser.util;

import org.w3c.dom.Document;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

public class Save {
    private static final Transformer transformer;

    static {
        try {
            transformer = TransformerFactory.newInstance().newTransformer();
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
    public static void saveToHTML(String xmlFileName, String xslFileName, String htmlFileName) throws TransformerException {

        Transformer transformer = TransformerFactory.newInstance().newTransformer(new StreamSource(new File(xslFileName)));

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        transformer.transform(new StreamSource(xmlFileName), saveResult(htmlFileName));
    }

    public static void saveToXML(Document document, String xmlFileName) throws TransformerException {

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        transformer.transform(new DOMSource(document), saveResult(xmlFileName));
    }
    private static StreamResult saveResult(String xmlFileName) {

        return new StreamResult(new File(xmlFileName));
    }
}