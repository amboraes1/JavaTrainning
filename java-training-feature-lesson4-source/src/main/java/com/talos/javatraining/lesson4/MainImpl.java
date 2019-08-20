package com.talos.javatraining.lesson4;


import com.talos.javatraining.lesson4.exceptions.AddressNotFoundException;
import com.talos.javatraining.lesson4.model.AddressModel;
import com.talos.javatraining.lesson4.model.CountryModel;
import com.talos.javatraining.lesson4.model.UserModel;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.Predicate;


public class MainImpl implements Main
{

	@Override
	public String getLine1(AddressModel addressModel)
	{
		Optional<String> result = Optional.empty();
		Optional<AddressModel> addres = Optional.ofNullable(addressModel);
		result =addres.map(AddressModel::getLine1);
		return result.filter(StringUtils::isNotBlank).orElse("");
	}

	@Override
	public String getFullName(AddressModel addressModel)
	{
		StringJoiner result = new StringJoiner(" ");
		Optional<AddressModel> address = Optional.ofNullable(addressModel);
		address.map(AddressModel::getFirstName).filter(StringUtils::isNotBlank).ifPresent(text->result.add(text));
		address.map(AddressModel::getLastName).filter(StringUtils::isNotBlank).ifPresent(text->result.add(text));
		return result.toString();
	}

	@Override
	public AddressModel getBillingAddress(UserModel userModel)
	{
		Optional<AddressModel> result = Optional.empty();
		Optional<UserModel> user = Optional.ofNullable(userModel);
		result = user
				.map(UserModel::getAddresses)
				.filter(CollectionUtils::isNotEmpty)
				.map(add->getAddress(add,a -> BooleanUtils.isTrue(a.getBillingAddress())));
		return result.orElse(null);
	}

	@Override
	public String getLastLoginFormatted(UserModel userModel)
	{
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		Optional<UserModel> user = Optional.ofNullable(userModel);
		return user.map(UserModel::getLastLogin).filter((tmp)->tmp!=null).map(x->format.format(x)).orElse("the user has not been logged yet");
	}

	@Override
	public String getContactCountry(UserModel userModel)
	{
		Optional<UserModel> user = Optional.ofNullable(userModel);
		return user
				.map(UserModel::getAddresses)
				.filter(CollectionUtils::isNotEmpty)
				.map(add->getAddress(add,a -> BooleanUtils.isTrue(a.getContactAddress())))
				.filter(cont->cont!=null)
				.map(AddressModel::getCountry)
				.filter(x->x!=null)
				.map(CountryModel::getIsocode)
				.filter(iso->iso!=null)
				.orElseGet(this::inferCountry);
	}

	@Override
	public AddressModel getShippingAddress(UserModel userModel) throws AddressNotFoundException
	{
		//AddressModel addressModel = null;
		Optional<AddressModel> addressModel = Optional.empty();
		Optional<UserModel> user = Optional.ofNullable(userModel);
		addressModel= user
				.map(UserModel::getAddresses)
				.filter(CollectionUtils::isNotEmpty)
				.map(add->getAddress(add,a -> BooleanUtils.isTrue(a.getShippingAddress())))
				.filter(add->add!=null);
		return addressModel.orElseThrow(AddressNotFoundException::new);

		/*if (CollectionUtils.isNotEmpty(userModel.getAddresses()))
		{
			addressModel = getAddress(userModel.getAddresses(), a -> BooleanUtils.isTrue(a.getShippingAddress()));
		}
		if(addressModel == null){
			throw new AddressNotFoundException();
		}
		return addressModel;*/
	}

	// ----------------------------------
	// DON'T MODIFY THE FOLLOWING METHODS
	// ----------------------------------

	/**
	 * This method returns an address based on the condition
	 *
	 * @param addresses the address list
	 * @param condition the condition
	 * @return the first address that matches the condition
	 */
	private AddressModel getAddress(Collection<AddressModel> addresses, Predicate<AddressModel> condition)
	{
		for (AddressModel addressModel : addresses)
		{
			if (condition.test(addressModel))
			{
				return addressModel;
			}
		}
		return null;
	}

	/**
	 * This method takes 1 second to return a response
	 *
	 * @return the user country
	 */
	private String inferCountry()
	{
		try
		{
			Thread.sleep(1000);
		}
		catch (InterruptedException ex)
		{

		}
		return "CA";
	}
}
