package com.talos.javatraining.lesson3;

import java.util.ArrayList;
import java.util.List;

public interface Invertebrate extends Animal {
    default List<String> getCharacteristics()
    {
        List<String> characteristics = new ArrayList<>();
        characteristics.add("Lack of backbones and internal skeletons");
        characteristics.add("Simple anatomy");
        return characteristics;
    }
}
