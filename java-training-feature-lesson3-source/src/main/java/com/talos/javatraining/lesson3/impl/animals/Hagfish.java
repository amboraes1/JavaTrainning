package com.talos.javatraining.lesson3.impl.animals;

import com.talos.javatraining.lesson3.JawlessFish;
import com.talos.javatraining.lesson3.impl.AbstractAnimal;

import java.util.ArrayList;
import java.util.List;


public class Hagfish extends AbstractAnimal implements JawlessFish
{
	@Override
	public List<String> getParentCharacteristics() {
		List<String> characteristics = new ArrayList<>(JawlessFish.super.getCharacteristics());
		characteristics.add("They are living fossils");
		populateCharacteristics(characteristics);
		return characteristics;
	}

	public void populateCharacteristics(List<String> characteristics) {

	}
}
