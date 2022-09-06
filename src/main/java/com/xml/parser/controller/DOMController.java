package com.xml.parser.controller;

import com.xml.parser.entity.*;
import com.xml.parser.util.Save;
import com.xml.parser.util.XML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.stream.IntStream;

import static com.xml.parser.util.Constants.*;
import static com.xml.parser.util.XML.*;

public class DOMController {
    private final String xmlFileName;
    private final Cars cars = new Cars();

    public DOMController(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

    public Cars getCars() {
        return cars;
    }

    public void parse(boolean value) throws ParserConfigurationException, SAXException, IOException {

        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance(
                        DOCUMENT_BUILDER_FACTORY,
                        this.getClass().getClassLoader());

        factory.setNamespaceAware(value);
        factory.setFeature(TURN_VALIDATION_ON, value);
        factory.setFeature(TURN_SCHEMA_VALIDATION_ON, value);

        Document document = factory.newDocumentBuilder().parse(xmlFileName);

        NodeList nodes = document.getDocumentElement().getElementsByTagName(CAR.value());

        IntStream.range(0, nodes.getLength()).mapToObj(j -> getCar(nodes.item(j))).forEach(car -> cars.getCars().add(car));
    }

    private Car getCar(Node node) {

        Car car = new Car();

        Element element = (Element) node;

        car.setId(Integer.parseInt(element.getAttribute(ID.value())));

        car.setBrand(element.getAttribute(BRAND.value()));

        car.setModel(getContext(element, MODEL));

        car.setColor(getContext(element, COLOR));

        car.setWheel(getWheel(getNode(element, WHEEL)));

        car.setEngine(getEngine(getNode(element, ENGINE)));

        car.setType(Type.fromValue(getContext(element, TYPE)));

        car.setPrice(BigDecimal.valueOf(Double.parseDouble(getContext(element, PRICE))));

        return car;
    }

    private Node getNode(Element element, XML data) {

        return element.getElementsByTagName(data.value()).item(0);
    }

    private String getContext(Element element, XML data) {

        return getNode(element, data).getTextContent();
    }

    public static void saveXML(Cars auto, String xmlFileName) throws ParserConfigurationException, TransformerException {

        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance(
                        DOCUMENT_BUILDER_FACTORY,
                        DOMController.class.getClassLoader());

        factory.setNamespaceAware(true);

        Document document = factory.newDocumentBuilder().newDocument();

        Element wrapper = document.createElementNS(URL, PREFIX + ":" + XML.CARS.value());

        wrapper.setAttributeNS(W3C_XML_SCHEMA_INSTANCE, SCHEMA_LOCATION, URI);

        document.appendChild(wrapper);

        for (Car cars : auto.getCars()) {

            Element car = document.createElement(CAR.value());

            if (document.getElementsByTagName(BRAND.value()) != null) {
                car.setAttribute(BRAND.value(), cars.getBrand());
            }

            car.setAttribute(ID.value(), String.valueOf(cars.getId()));
            wrapper.appendChild(car);

            Element model = document.createElement(MODEL.value());
            model.setTextContent(cars.getModel());
            car.appendChild(model);

            Element color = document.createElement(COLOR.value());
            color.setTextContent(cars.getColor());
            car.appendChild(color);

            Element wheel = document.createElement(WHEEL.value());

            {
                Element tire = document.createElement(TIRE.value());
                tire.setTextContent(cars.getWheel().getTire());
                wheel.appendChild(tire);

                Element position = document.createElement(POSITION.value());
                position.setTextContent(String.valueOf(cars.getWheel().getPosition()));
                wheel.appendChild(position);

                Element size = document.createElement(DIAMETER.value());
                size.setTextContent(String.valueOf(cars.getWheel().getDiameter()));
                wheel.appendChild(size);
            }
            car.appendChild(wheel);

            Element engine = document.createElement(ENGINE.value());
            {
                Element power = document.createElement(POWER.value());
                power.setTextContent(String.valueOf(cars.getEngine().getPower()));
                engine.appendChild(power);

                Element run = document.createElement(RUN.value());
                run.setTextContent(String.valueOf(cars.getEngine().getRun()));
                engine.appendChild(run);

                Element fuel = document.createElement(FUEL.value());
                fuel.setTextContent("" + cars.getEngine().getFuel());
                engine.appendChild(fuel);
            }

            car.appendChild(engine);

            Element type = document.createElement(TYPE.value());
            type.setTextContent("" + cars.getType().value());
            car.appendChild(type);

            Element price = document.createElement(PRICE.value());
            price.setTextContent("" + cars.getPrice());
            car.appendChild(price);
        }
        Save.saveToXML(document, xmlFileName);
    }

    private Wheel getWheel(Node node) {

        Element element = (Element) node;

        Wheel wheel = new Wheel();

        wheel.setTire(getContext(element, TIRE));

        wheel.setPosition(getContext(element, POSITION));

        wheel.setDiameter(Integer.parseInt(getContext(element, DIAMETER)));

        return wheel;
    }

    private Engine getEngine(Node node) {

        Element element = (Element) node;

        Engine engine = new Engine();

        engine.setPower(Integer.parseInt(getContext(element, POWER)));

        engine.setRun(Boolean.valueOf(getContext(element, RUN)));

        engine.setFuel(Integer.parseInt(getContext(element, FUEL)));

        return engine;
    }
}
