package com.xml.parser.controller;

import com.xml.parser.entity.*;
import com.xml.parser.util.XML;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
import javax.xml.transform.stream.StreamSource;
import java.math.BigDecimal;
import java.util.Objects;

import static com.xml.parser.util.Constants.*;

public class STAXController extends DefaultHandler {
    private final String xmlFileName;

    private Cars cars;

    public Cars getCars() {
        return cars;
    }

    public STAXController(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

    public void parse() throws XMLStreamException {

        Car car = null;
        Engine engine = null;
        Wheel wheel = null;
        String part = null;

        XMLInputFactory factory = XMLInputFactory.newInstance();

        factory.setProperty(IS_NAMESPACE_AWARE, true);

        StreamSource source = new StreamSource(xmlFileName);

        XMLEventReader reader = factory.createXMLEventReader(source);

        while (reader.hasNext()) {

            XMLEvent event = reader.nextEvent();

            if (event.isCharacters() && event.asCharacters().isWhiteSpace()) {
                continue;
            }

            if (event.isStartElement()) {

                StartElement start = event.asStartElement();

                part = start.getName().getLocalPart();

                if (Objects.equals(part, XML.ENGINE.value())) {
                    engine = new Engine();
                    continue;
                }

                if (Objects.equals(part, XML.WHEEL.value())) {
                    wheel = new Wheel();
                    continue;
                }

                if (Objects.equals(part, XML.CARS.value())) {
                    cars = new Cars();
                    continue;
                }

                if (Objects.equals(part, XML.CAR.value())) {

                    car = new Car();

                    Attribute attribute = start.getAttributeByName(new QName(XML.ID.value()));

                    if (attribute != null) {
                        car.setId(Integer.parseInt(attribute.getValue()));
                    }

                    attribute = start.getAttributeByName(new QName(XML.BRAND.value()));
                    if (attribute != null) {
                        car.setBrand(attribute.getValue());
                    }
                }
            }

            if (event.isCharacters()) {

                String data = event.asCharacters().getData();

                if (Objects.equals(part, XML.MODEL.value())) {
                    Objects.requireNonNull(car).setModel(data);
                    continue;
                }

                if (Objects.equals(part, XML.BRAND.value())) {
                    Objects.requireNonNull(car).setBrand(data);
                    continue;
                }

                if (Objects.equals(part, XML.COLOR.value())) {
                    Objects.requireNonNull(car).setColor(data);
                    continue;
                }

                if (Objects.equals(part, XML.POSITION.value())) {
                    Objects.requireNonNull(wheel).setPosition(data);
                    continue;
                }

                if (Objects.equals(part, XML.TIRE.value())) {
                    Objects.requireNonNull(wheel).setTire(data);
                    continue;
                }

                if (Objects.equals(part,XML.DIAMETER.value())) {
                    Objects.requireNonNull(wheel).setDiameter(Integer.parseInt(data));
                    continue;
                }

                if (Objects.equals(part,XML.POWER.value())) {
                    Objects.requireNonNull(engine).setPower(Integer.parseInt(data));
                    continue;
                }

                if (Objects.equals(part,XML.RUN.value())) {
                    Objects.requireNonNull(engine).setRun(Boolean.parseBoolean(data));
                    continue;
                }

                if (Objects.equals(part, XML.FUEL.value())) {
                    Objects.requireNonNull(engine).setFuel(Integer.parseInt(data));
                    continue;
                }

                if (Objects.equals(part, XML.TYPE.value())) {
                    Objects.requireNonNull(car).setType(Type.fromValue(data.toLowerCase()));
                    continue;
                }

                if (Objects.equals(part, XML.PRICE.value())) {
                    Objects.requireNonNull(car).setPrice(BigDecimal.valueOf(Double.parseDouble(data)));
                    continue;
                }
            }

            if (event.isEndElement()) {
                String end = event.asEndElement().getName().getLocalPart();

                if (Objects.equals(end, XML.CAR.value())) {
                    cars.getCars().add(car);
                    continue;
                }

                if (Objects.equals(end, XML.WHEEL.value())) {
                    Objects.requireNonNull(car).setWheel(wheel);
                    continue;
                }

                if (Objects.equals(end, XML.ENGINE.value())) {
                    Objects.requireNonNull(car).setEngine(engine);
                }
            }
        }
        reader.close();
    }
}
