package com.talos.javatraining.lesson9.strategies;

import com.talos.javatraining.lesson9.events.EventBus;
import com.talos.javatraining.lesson9.events.EventType;
import com.talos.javatraining.lesson9.strategies.impl.CalculatorTemplate;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;


public class Calculator
{
	private EventBus eventBus;
	private Map<String, CalculatorStrategy> strategies;
	private String mode = "basic";// default mode

	@Inject
	public Calculator(EventBus eventBus)
	{
		strategies = new HashMap<>();
		strategies.put("scientific",new CalculatorTemplate((val)-> {DecimalFormat formatter = new DecimalFormat("0.00E00");
		formatter.setRoundingMode(RoundingMode.HALF_UP);
		formatter.setMinimumFractionDigits(10);
		return formatter.format(val);}));
		strategies.put("basic",new CalculatorTemplate((val)->val.setScale(5, RoundingMode.HALF_UP).toString()));
		this.eventBus = eventBus;
		eventBus.register(EventType.ADD, args -> calculate(getStrategy()::add, args));
		eventBus.register(EventType.SUBTRACT, args -> calculate(getStrategy()::subtract, args));
		eventBus.register(EventType.MULTIPLY, args -> calculate(getStrategy()::multiply, args));
		eventBus.register(EventType.DIVIDE, args -> calculate(getStrategy()::divide, args));
		eventBus.register(EventType.CHANGE_MODE, this::changeMode);

	}

	private CalculatorStrategy getStrategy()
	{
		return strategies.get(mode);
	}

	private void calculate(BiFunction<String, String, String> operation, String[] args)
	{
		String result = operation.apply(args[0], args[1]);
		eventBus.notify(EventType.RESULT, "> " + result);
	}

	private void changeMode(String[] args)
	{
		String tempMode = args[0];
		if(!strategies.containsKey(tempMode)){
			eventBus.notify(EventType.RESULT, "not supported mode: " + tempMode);
			return;
		}
		mode = tempMode;
		eventBus.notify(EventType.RESULT, "mode: " + mode);
	}

	private String getKey(CalculatorStrategy strategy)
	{
		String simpleName = strategy.getClass().getSimpleName();
		return StringUtils.removeEnd(simpleName, "Strategy").toLowerCase();
	}

	//@Inject
	public void setStrategies(Set<CalculatorStrategy> strategySet)
	{
		strategies = strategySet.stream().collect(Collectors.toMap(this::getKey, s -> s));
	}


}
