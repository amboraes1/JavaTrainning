package com.talos.javatraining.lesson3;

import java.util.List;
import org.apache.commons.lang3.StringUtils;

public interface Animal
{
	default String getName(){
		return getClass().getSimpleName();
	};

	List<String> getCharacteristics();

	default String getFullDescription(){
		StringBuilder builder = new StringBuilder();
		String name = getName();
		String lastChr = name.substring(name.length()-1);
		builder.append(getName()).append(lastChr.equals("s") ? "es" : "s").append(" have these characteristics :");
		for (String characteristic : getCharacteristics())
		{
			builder.append(StringUtils.LF).append(StringUtils.CR).append("- ").append(characteristic);
		}
		return builder.toString();
	};

	static Animal create(String name){
		String classname = "com.talos.javatraining.lesson3.impl.animals.";
		try{
			Animal anim = (Animal)Class.forName(classname+name).newInstance();
			return anim;
		}catch (ClassCastException | ClassNotFoundException | InstantiationException| IllegalAccessException err){
			return null;
		}

	}

}
