package com.talos.javatraining.lesson3.impl.animals;

import com.talos.javatraining.lesson3.Reptile;
import com.talos.javatraining.lesson3.impl.AbstractAnimal;

import java.util.ArrayList;
import java.util.List;


public class Crocodile extends AbstractAnimal implements Reptile
{
	@Override
	public List<String> getParentCharacteristics() {
		List<String> characteristics = new ArrayList<>(Reptile.super.getCharacteristics());
		characteristics.add("They have V-shaped snouts");
		characteristics.add("Toothy grin");
		populateCharacteristics(characteristics);
		return characteristics;
	}

	public void populateCharacteristics(List<String> characteristics) {

	}
}
