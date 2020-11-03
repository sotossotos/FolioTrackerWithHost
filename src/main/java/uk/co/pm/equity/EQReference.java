package uk.co.pm.equity;

public class EQReference {
	private String datetime;
	private Float price;
	private String currency;
	private String assettype;
	private String companyname;
	private String epic;
	private String sector;

	public EQReference(String epic,String companyname,String assettype,String sector,String currency,String datetime,Float price){
		this.epic =epic;
		this.companyname=companyname;
		this.assettype=assettype;
		this.sector=sector;
		this.currency=currency;
		this.datetime=datetime;
		this.price=price;
	}
    public String getEpic() {
        return this.epic;
    }
    public String getCompanyName() {
        return this.companyname;
    }
    public String getAssetType() {
        return this.assettype;
    }
    public String getSector() {
        return this.sector;
    }
    public String getCurrency() {
        return this.currency;
    }
    public String getDateTime() {
        return this.datetime;
    }
    public Float getPrice() {
        return this.price;
    }
    



}
