package com.talos.javatraining.lesson3;

import java.util.ArrayList;
import java.util.List;

public interface Fish extends Animal{
    default List<String> getCharacteristics()
    {
        List<String> characteristics = new ArrayList<>();
        characteristics.add("They breathe using gills");
        characteristics.add("They have dominated the world's oceans, lakes and rivers");
        return characteristics;
    }
}
