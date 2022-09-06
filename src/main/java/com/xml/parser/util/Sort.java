package com.xml.parser.util;

import com.xml.parser.entity.Car;
import com.xml.parser.entity.Cars;

import java.util.Comparator;

public class Sort {

    public static void sortByPower(Cars car) {
        car.getCars().sort(Comparator.comparingInt(o -> o.getEngine().getPower()));
    }

    public static void sortByType(Cars car) {
        car.getCars().sort(Comparator.comparing(Car::getType));

    }
}