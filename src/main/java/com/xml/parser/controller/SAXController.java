package com.xml.parser.controller;

import com.xml.parser.entity.*;
import com.xml.parser.util.XML;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Objects;

import static com.xml.parser.util.Constants.*;

public class SAXController extends DefaultHandler {
    private Car car;
    private Cars cars;
    private String local;
    private Engine engine;
    private Wheel wheel;
    private final String xmlFileName;

    public SAXController(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

    public Cars getCars() {
        return cars;
    }

    public void parse(boolean value) throws ParserConfigurationException, SAXException, IOException {

        SAXParserFactory factory =
                SAXParserFactory.newInstance(
                        SAX_PARSER_FACTORY,
                        this.getClass().getClassLoader());

        factory.setNamespaceAware(value);
        factory.setFeature(TURN_VALIDATION_ON, value);
        factory.setFeature(TURN_SCHEMA_VALIDATION_ON, value);
        factory.newSAXParser().parse(xmlFileName, this);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {

        local = localName;

        if (Objects.equals(local, XML.CARS.value())) {
            cars = new Cars();
            return;
        }

        if (Objects.equals(local, XML.CAR.value())) {
            car = new Car();
            if (attributes.getLength() > 0) {
                car.setId(Integer.parseInt(attributes.getValue(uri, XML.ID.value())));
                car.setBrand((attributes.getValue(uri, XML.BRAND.value())));
            }
            return;
        }

        if (Objects.equals(local, XML.WHEEL.value())) {
            wheel = new Wheel();
            return;
        }

        if (Objects.equals(local, XML.ENGINE.value())) {
            engine = new Engine();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {

        if (localName.equals(XML.CAR.value())) {
            cars.getCars().add(car);
            return;
        }
        if (localName.equals(XML.WHEEL.value())) {
            car.setWheel(wheel);
            return;
        }
        if (localName.equals(XML.ENGINE.value())) {
            car.setEngine(engine);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {

        String element = new String(ch, start, length).trim();

        if (element.isEmpty())
            return;

        if (local.equals(XML.MODEL.value())) {
            car.setModel(element);
            return;
        }

        if (local.equals(XML.BRAND.value())) {
            car.setBrand(element);
            return;
        }

        if (local.equals(XML.COLOR.value())) {
            car.setColor(element);
            return;
        }

        if (local.equals(XML.TIRE.value())) {
            wheel.setTire(element);
            return;
        }

        if (local.equals(XML.POSITION.value())) {
            wheel.setPosition(element);
            return;
        }

        if (local.equals(XML.DIAMETER.value())) {
            wheel.setDiameter(Integer.parseInt(element));
            return;
        }

        if (local.equals(XML.POWER.value())) {
            engine.setPower(Integer.parseInt(element));
            return;
        }

        if (local.equals(XML.RUN.value())) {
            engine.setRun(Boolean.parseBoolean(element));
            return;
        }

        if (local.equals(XML.FUEL.value())) {
            engine.setFuel(Integer.parseInt(element));
            return;
        }

        if (local.equals(XML.PRICE.value())) {
            car.setPrice(BigDecimal.valueOf(Double.parseDouble(element)));
            return;
        }

        if (local.equals(XML.TYPE.value())) {
            car.setType(Type.fromValue(element.toLowerCase()));
        }
    }
}
