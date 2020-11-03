package uk.co.pm.dao.csv.reader;

public class EquityPricing implements IEquityPricing {

	private String epic;
	private IEquityPricingDate date;
	private IEquityPricingMidPrice midPrice;
	private String currency;
	
	public EquityPricing() {
		this.epic = "";
		this.date = new EquityPricingDate();
		this.midPrice = new EquityPricingMidPrice();
		this.currency = "";
	}
	
	@Override
	public void setEPIC(String epic) {
		this.epic = epic;
	}

	@Override
	public void setDate(IEquityPricingDate date) {
		this.date = date;
	}

	@Override
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Override
	public void setMidPrice(IEquityPricingMidPrice equityPricingMidPrice) {
		this.midPrice = equityPricingMidPrice;
	}

	@Override
	public String getEPIC() {
		return this.epic;
	}

	@Override
	public String getDate() {
		return this.date.toString();
	}

	@Override
	public String getMidPrice() {
		return this.midPrice.getPrice();
	}

	@Override
	public String getCurrencyType() {
		return this.currency;
	}


}
