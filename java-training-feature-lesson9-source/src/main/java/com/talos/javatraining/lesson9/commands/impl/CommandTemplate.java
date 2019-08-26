package com.talos.javatraining.lesson9.commands.impl;

import com.talos.javatraining.lesson9.commands.AppCommand;
import com.talos.javatraining.lesson9.events.EventBus;
import com.talos.javatraining.lesson9.events.EventType;

import java.util.function.Function;
import java.util.function.Supplier;


public  class CommandTemplate implements AppCommand
{

	private String[] args;
	private EventBus eventBus;
	private Supplier<EventType> sup;

	public CommandTemplate(EventBus eventBus,Supplier<EventType> sup, String... args)
	{
		this.args = args;
		this.eventBus = eventBus;
		this.sup = sup;
	}

	@Override
	public void execute()
	{
		eventBus.notify(sup.get(), args);
	}
}
