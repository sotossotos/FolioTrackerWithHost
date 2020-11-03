package uk.co.pm.equity;

public class EQJSONReference extends EQReference{
	private String link;
	public EQJSONReference(String epic, String companyname, String assettype, String sector, String currency,
			String datetime, Float price,String link) {
		super(epic, companyname, assettype, sector, currency, datetime, price);
		this.link = link;
		}
	public String getLink() {
		return link;
	}

}
