package com.xml.parser;

import com.xml.parser.controller.DOMController;
import com.xml.parser.controller.SAXController;
import com.xml.parser.controller.STAXController;
import com.xml.parser.entity.Cars;
import com.xml.parser.util.Save;
import com.xml.parser.util.Sort;
import com.xml.parser.util.Constants;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class Main {

    public static void main(String[] args)
            throws ParserConfigurationException,
            SAXException, IOException,
            XMLStreamException, TransformerException {

        if (args.length != 1) {
            System.out.println(args[0]);
            return;
        }

        String xmlFileName = args[0];
        System.out.println("Input ==> " + xmlFileName);

        //==========================DOMController=======================
        DOMController domController = new DOMController(xmlFileName);
        domController.parse(true);

        Cars cars = domController.getCars();

        Sort.sortByType(cars);

        DOMController.saveXML(cars, Constants.OUTPUT_DOM_XML);
        System.out.println("Output ==> " + Constants.OUTPUT_DOM_XML);

        //==========================SAXController=======================
        SAXController saxController = new SAXController(xmlFileName);
        saxController.parse(true);

        cars = saxController.getCars();

        DOMController.saveXML(cars, Constants.OUTPUT_SAX_XML);
        System.out.println("Output ==> " + Constants.OUTPUT_SAX_XML);

        //==========================STAXController======================
        STAXController staxController = new STAXController(xmlFileName);
        staxController.parse();
        cars = staxController.getCars();

        Sort.sortByPower(cars);

        DOMController.saveXML(cars, Constants.OUTPUT_STAX_XML);
        System.out.println("Output ==> " + Constants.OUTPUT_STAX_XML);
        //==========================saveToHTML==========================
        Save.saveToHTML(Constants.INPUT_XML, Constants.INPUT_XSL, Constants.INPUT_HTML);
        System.out.println("Html ==> " + Constants.INPUT_HTML);
    }
}
