package uk.co.pm.dao.csv.reader;

public class Portfolio implements IPortfolio{
	
	private int pID;
	private String name; 
	private String manager;
	private double cashQ1;
	private double cashQ2;
	private String currency;
	private String isBenchmark;
	
	public Portfolio(){
		this.pID = 0; 
		this.name = "";
		this.manager = "";
		this.cashQ1 = 0.0;
		this.cashQ2 = 0.0;
		this.currency = "";
		this.isBenchmark = "N";
	}

	@Override
	public void setPID(int pID) {
		this.pID = pID;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setManager(String manager) {
		this.manager = manager;
	}

	@Override
	public void setCashQ1(double cash) {
		this.cashQ1 = cash;
		
	}
	
	@Override
	public void setCashQ2(double cash) {
		this.cashQ2 = cash;
		
	}
	

	@Override
	public void setCurrency(String currency) {
		this.currency = currency;
		
	}

	@Override
	public int getPID() {
		return pID;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getManager() {
		return manager;
	}

	@Override
	public double getCashQ1() {
		return cashQ1;
	}
	
	@Override
	public double getCashQ2() {
		return cashQ2;
	}

	@Override
	public String getCurrency() {
		return currency;
	}

	@Override
	public void setIsBenchmark() {
		isBenchmark = "Y";
		
	}

	@Override
	public String getIsBenchmark() {
		return isBenchmark;
	}
}
