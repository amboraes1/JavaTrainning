package com.talos.javatraining.lesson3;

import java.util.ArrayList;
import java.util.List;

public interface Mammal extends Animal{
    default List<String> getCharacteristics()
    {
        List<String> characteristics = new ArrayList<>();
        characteristics.add("They have hair or fur");
        characteristics.add("They suckle milk when they are young");
        return characteristics;
    }

}
