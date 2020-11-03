package uk.co.pm.dao.csv.reader;

public interface IEquityPricingMidPrice{

	/***
	 * @modifies this
	 * @effects Sets the price value to the parameter
	 */
	public void setPrice(String price) throws IllegalMidPriceFormatException;

	/***
	 * @effects Returns the price as a string
	 */
	public String getPrice();
}