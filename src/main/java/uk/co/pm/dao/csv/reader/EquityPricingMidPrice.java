package uk.co.pm.dao.csv.reader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EquityPricingMidPrice implements IEquityPricingMidPrice {

	String priceString;
	Pattern midPricePattern;
	
	public EquityPricingMidPrice() {
		this.priceString = "";
		this.midPricePattern = Pattern.compile("\\d+[\\.\\d+]");
	}
	
	@Override
	public void setPrice(String price) throws IllegalMidPriceFormatException {
		if(isInvalidPriceFormat(price)) {
			throw new IllegalMidPriceFormatException("The price should only contain numbers and it should only contain at most, one decimal point. Price given was " + price);
		}
		else {
			this.priceString = price;
		}
	}
	
	private boolean isInvalidPriceFormat(String priceString) {
		Matcher m = this.midPricePattern.matcher(priceString);
		return m.matches();
	}

	@Override
	public String getPrice() {
		return this.priceString;
	}

}
