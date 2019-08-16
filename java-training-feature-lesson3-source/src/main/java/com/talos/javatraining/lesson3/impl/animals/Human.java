package com.talos.javatraining.lesson3.impl.animals;

import com.talos.javatraining.lesson3.Mammal;
import com.talos.javatraining.lesson3.impl.AbstractAnimal;

import java.util.ArrayList;
import java.util.List;


public class Human extends AbstractAnimal implements Mammal
{
	@Override
	public List<String> getParentCharacteristics() {
		List<String> characteristics = new ArrayList<>(Mammal.super.getCharacteristics());
		characteristics.add("They rule the word!");
		populateCharacteristics(characteristics);
		return characteristics;
	}

	public void populateCharacteristics(List<String> characteristics) {

	}
}
