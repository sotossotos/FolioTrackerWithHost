package uk.co.pm.dao.csv.reader;

public interface IPortfolio {
	
	/***
     * @modifies this
     * @effects  Sets the EPID of the portfolio to the argument given
     */
	public void setPID(int pID);
	
	/***
	 * @modifies this
	 * @effects Sets the Name of this portfolio the argument given
	 */
	public void setName(String name);
	
	public void setManager(String manager);
	
	public void setCashQ1(double cash);
	
	public void setCashQ2(double cash);
	
	public void setCurrency(String currency);
	
	public void setIsBenchmark();
	
	public int getPID();
	
	public String getName();
	
	public String getManager();
	
	public double getCashQ1();
	
	public double getCashQ2();
	
	public String getCurrency();
	
	public String getIsBenchmark();
	
}
