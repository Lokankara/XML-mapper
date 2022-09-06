package com.xml.parser.entity;

import java.util.ArrayList;
import java.util.List;

public class Cars {

    protected List<Car> car;

    public List<Car> getCars() {
        if (car == null) {
            car = new ArrayList<>();
        }
        return this.car;
    }

    @Override
    public String toString() {
        return "Cars=" + car;
    }

}
