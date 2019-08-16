package com.talos.javatraining.lesson3.impl.animals;

import com.talos.javatraining.lesson3.JawlessFish;
import com.talos.javatraining.lesson3.impl.AbstractAnimal;

import java.util.ArrayList;
import java.util.List;


public class Lamprey extends AbstractAnimal implements JawlessFish
{

	@Override
	public List<String> getParentCharacteristics() {
		List<String> characteristics = new ArrayList<>(JawlessFish.super.getCharacteristics());
		characteristics.add("They have a sucker mouth with horny teeth and a rasping tongue");
		populateCharacteristics(characteristics);
		return characteristics;
	}

	public void populateCharacteristics(List<String> characteristics) {

	}
}
