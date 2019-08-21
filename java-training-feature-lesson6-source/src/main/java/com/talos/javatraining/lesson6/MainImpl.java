package com.talos.javatraining.lesson6;

import com.talos.javatraining.lesson6.data.OrderData;
import com.talos.javatraining.lesson6.data.OrderEntryData;
import com.talos.javatraining.lesson6.data.ProductData;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import static java.util.Comparator.comparing;
import static org.apache.commons.collections4.ComparatorUtils.chainedComparator;


public class MainImpl implements Main
{
	private static final Comparator<Map.Entry<ProductData, Long>> COMPARATOR = chainedComparator(comparing(Map.Entry::getValue), comparing(e -> e.getKey().getCode()));
	private static final String PIPELINE = "|";

	@Override
	public List<OrderData> getOrdersWithPriceGreaterThan(List<OrderData> orders, BigDecimal price, long limit)
	{
		return orders
				.stream()
				.filter(x->x.getSubTotal().getValue().compareTo(price) > 0)
				.limit(limit)
				.collect(Collectors.toList());
	}

	@Override
	public List<OrderData> getOrdersThatContainsAProduct(List<OrderData> orders, String productCode)
	{
		return orders
				.stream().filter(x->x.getEntries().stream().anyMatch(y->y.getProduct().getCode().equals(productCode)))
				.collect(Collectors.toList());
	}

	@Override
	public Set<String> getOrderCodes(List<OrderData> orders)
	{
		return orders.stream().map(OrderData::getCode).collect(Collectors.toCollection(TreeSet::new));
	}

	@Override
	public List<OrderEntryData> getEntriesWithPriceLowerThan(List<OrderData> orders, BigDecimal price)
	{
		return orders
				.stream()
				.flatMap(x->x.getEntries().stream())
				.filter(y->y.getBasePrice().getValue().compareTo(price) < 0)
				.collect(Collectors.toList());
	}

	@Override
	public Map<Integer, OrderEntryData> getEntriesAsMap(List<OrderData> orders, String orderCode)
	{
		return orders
				.stream()
				.filter(x->x.getCode().equals(orderCode))
				.map(OrderData::getEntries)
				.flatMap(List::stream)
				.collect(Collectors.toMap(OrderEntryData::getEntryNumber,y->y));
	}

	@Override
	public String getEntriesAsString(List<OrderData> orders, String orderCode)
	{
		return orders
				.stream()
				.filter(x->x.getCode().equals(orderCode))
				.map(OrderData::getEntries)
				.flatMap(List::stream)
				.map(OrderEntryData::toString)
				.collect(Collectors.joining(PIPELINE));
	}

	@Override
	public Map<String, List<ProductData>> getProductsByOrderCode(List<OrderData> orders)
	{
		return orders
				.stream()
				.collect(Collectors.toMap(OrderData::getCode , x->x.getEntries().stream().map(OrderEntryData::getProduct).collect(Collectors.toList())));
	}

	@Override
	public List<ProductData> getBestSellingProducts(List<OrderData> orders, long limit)
	{
		Map<ProductData, Long> counter = orders
				.stream()
				.map(OrderData::getEntries)
				.flatMap(List::stream)
				.collect(Collectors.toMap(OrderEntryData::getProduct,OrderEntryData::getQuantity,(Long::sum)));

		Set<Map.Entry<ProductData, Long>> finalList = new TreeSet<>(COMPARATOR.reversed());
		finalList.addAll(counter.entrySet());
		return finalList
				.stream()
				.map(Map.Entry::getKey)
				.limit(limit)
				.collect(Collectors.toList());
	}
}
