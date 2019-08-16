package com.talos.javatraining.lesson3;

import java.util.ArrayList;
import java.util.List;

public interface Bird extends Animal {
    default List<String> getCharacteristics()
    {
        List<String> characteristics = new ArrayList<>();
        characteristics.add("Coat of feathers");
        characteristics.add("Warm-blooded metabolisms");
        return characteristics;
    }
}
