package com.talos.javatraining.lesson3;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public interface Reptile extends Animal {

  default  List<String> getCharacteristics()
	{
		List<String> characteristics = new ArrayList<>();
		characteristics.add("They ruled the earth for over 150 million years");
		characteristics.add("They can lay some distance away from bodies of water");
		return characteristics;
	}
}
