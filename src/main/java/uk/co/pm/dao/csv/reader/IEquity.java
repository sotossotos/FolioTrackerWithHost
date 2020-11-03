package uk.co.pm.dao.csv.reader;

public interface IEquity {

    /***
     * @modifies this
     * @effects  Sets the EPIC of this equity to the argument given
     */
	public void setEPIC(String epic);
	
	/***
	 * @modifies this
	 * @effects Sets the Company Name of this equity to the argument given
	 */
	public void setCompanyName(String companyName);
	
	/***
	 * @modifies this
	 * @effects Sets the Asset Type of this equity to the argument given
	 */
	public void setAssetType(String assetType);
	
	/***
	 * @modifies this
	 * @effects Sets the Sector of this equity to the argument given
	 */
	public void setSector(String sector);
	
	/***
	 * @modifies this
	 * @effects Sets the Currency type of this equity to the argument given
	 */
	public void setCurrencyType(String currencyType);
	
	/***
	 * @modifies this
	 * @effects Sets the equity pricing of this equity to the argument given
	 * @param equityPricing
	 */
	public void setPricing(IEquityPricing equityPricing);
	
	
	
	public String getEPIC();
	
	public String getCompanyName();
	
	public String getAssetType();
	
	public String getSector();	
	
	public String getCurrencyType();

	public IEquityPricing getPricing();

}
