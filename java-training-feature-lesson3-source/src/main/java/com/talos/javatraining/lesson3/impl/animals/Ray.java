package com.talos.javatraining.lesson3.impl.animals;

import com.talos.javatraining.lesson3.CartilaginousFish;
import com.talos.javatraining.lesson3.impl.AbstractAnimal;

import java.util.ArrayList;
import java.util.List;


public class Ray extends AbstractAnimal implements CartilaginousFish
{
	@Override
	public List<String> getParentCharacteristics() {
		List<String> characteristics = new ArrayList<>(CartilaginousFish.super.getCharacteristics());
		characteristics.add("They are flattened");
		characteristics.add("They have five gill openings");
		populateCharacteristics(characteristics);
		return characteristics;
	}

	public void populateCharacteristics(List<String> characteristics) {

	}
}
