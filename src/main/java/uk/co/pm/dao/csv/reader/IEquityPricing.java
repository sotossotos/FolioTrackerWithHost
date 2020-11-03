package uk.co.pm.dao.csv.reader;

public interface IEquityPricing {

	/***
	 * @modifies this
	 * @effects Sets the EPIC of this EquityPricing to the parameter
	 */
	public void setEPIC(String epic);
	
	/***
	 * @modifies this
	 * @effects Sets the date of this EquityPricing to the parameter
	 */
	public void setDate(IEquityPricingDate date);
	
	/***
	 * @modifies this
	 * @effects Sets the mid price of this EquityPricing to the parameter
	 */
	public void setMidPrice(IEquityPricingMidPrice midPrice);
	
	/***
	 * @modifies this
	 * @effects Sets the currency of this EquityPricing to the parameter
	 */
	public void setCurrency(String equityPricingCurrency);
	
	public String getEPIC();
	
	public String getDate();
	
	public String getMidPrice();	
	
	public String getCurrencyType();
}
