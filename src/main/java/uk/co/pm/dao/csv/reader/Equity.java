package uk.co.pm.dao.csv.reader;

public class Equity implements IEquity {

	private String epic;
	private String companyName;
	private String assetType;
	private String sector;
	private String currencyType;
	private IEquityPricing pricing;
	
	public Equity() {
		this.epic = "";
		this.companyName = "";
		this.assetType = "";
		this.sector = "";
		this.currencyType = "";
		this.pricing = new EquityPricing();
	}
	
	@Override
	public void setEPIC(String epic) {
		this.epic = epic;
	}

	@Override
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Override
	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	@Override
	public void setSector(String sector) {
		this.sector = sector;
	}

	@Override
	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	@Override
	public void setPricing(IEquityPricing equityPricing) {
		this.pricing = equityPricing; 
	}

	@Override
	public String getEPIC() {
		return this.epic;
	}

	@Override
	public String getCompanyName() {
		return this.companyName;
	}

	@Override
	public String getAssetType() {
		return this.assetType;
	}

	@Override
	public String getSector() {
		return this.sector;
	}

	@Override
	public String getCurrencyType() {
		return this.currencyType;
	}

	@Override
	public IEquityPricing getPricing() {
		return this.pricing;
	}

}
