package com.talos.javatraining.lesson5;

import com.talos.javatraining.lesson5.data.OrderData;
import com.talos.javatraining.lesson5.data.OrderEntryData;
import com.talos.javatraining.lesson5.data.PriceData;
import com.talos.javatraining.lesson5.data.ProductData;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * This implementation uses a traditional for block. Since there are some parts with similar code we created some private methods to reuse that code.
 * However, we need to refactor this class to use streams instead. In that case the private methods are not longer necessary.
 */
public class MainImpl implements Main
{
	@Override
	public boolean isThereAnOrderWithPriceLowerThan(List<OrderData> orders, BigDecimal price)
	{
		return orders.stream().anyMatch(o -> o.getSubTotal().getValue().compareTo(price) < 0);
	}

	@Override
	public boolean areThereAllOrdersWithPriceGreaterThan(List<OrderData> orders, BigDecimal price)
	{
		return orders.stream().noneMatch(o -> o.getSubTotal().getValue().compareTo(price) <= 0);
	}

	@Override
	public BigDecimal getLowestOrderPrice(List<OrderData> orders)
	{
		return orders.stream().map(o->o.getSubTotal().getValue()).min(BigDecimal::compareTo).orElse(null);
	}

	@Override
	public BigDecimal getHighestOrderPrice(List<OrderData> orders)
	{
		return orders.stream().map(o->o.getSubTotal().getValue()).max(BigDecimal::compareTo).orElse(null);
	}

	@Override
	public long countOrdersWithPriceGreaterThan(List<OrderData> orders, BigDecimal price)
	{
		return orders.stream().map(o->o.getSubTotal().getValue()).filter(o->o.compareTo(price)>0).count();
	}

	@Override
	public BigDecimal sumOrderPricesWithPriceLowerThan(List<OrderData> orders, BigDecimal price)
	{
		return orders
				.stream()
				.map(o->o.getSubTotal().getValue())
				.filter(o->o.compareTo(price)<0)
				.reduce(BigDecimal::add)
				.orElse(BigDecimal.ZERO);
	}

	@Override
	public long countAllEntriesWithPriceGreaterThan(List<OrderData> orders, BigDecimal price)
	{
		return orders
				.stream()
				.map(OrderData::getEntries)
				.flatMap(x->x.stream())
				.map(o->o.getBasePrice().getValue())
				.filter(x->x.compareTo(price)>0)
				.count();
	}

	@Override
	public long countEntriesWithProduct(List<OrderData> orders, String productCode)
	{
		return orders.stream()
				.map(x->x.getEntries())
				.flatMap(x->x.stream())
				.map(x->x.getProduct().getCode())
				.filter(x->x.equals(productCode))
				.count();
	}

	@Override
	public long sumQuantitiesForProduct(List<OrderData> orders, String productCode)
	{
		return orders
				.stream()
				.map(OrderData::getEntries)
				.flatMap(x->x.stream())
				.filter(x->x.getProduct().getCode().equals(productCode))
				.map(x->x.getQuantity())
				.reduce(Long::sum)
				.orElse(0L);
	}

	@Override
	public long getMaxQuantityOrderedForProduct(List<OrderData> orders, String productCode)
	{
		return orders
				.stream()
				.map(OrderData::getEntries)
				.flatMap(x->x.stream())
				.filter(x->x.getProduct().getCode().equals(productCode))
				.map(x->x.getQuantity())
				.max(Long::compareTo)
				.orElse(0L);
	}
}
