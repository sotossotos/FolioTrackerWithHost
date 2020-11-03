package uk.co.pm.dao.csv.reader;

public class Holdings implements IHoldings{
	
	private int pID;
	private String epic;
	private int noShares;
	private String type;
	private String time;
	
	public Holdings(){
		this.pID = 0;
		this.epic = "";
		this.noShares = 0;
		this.type = "";
		this.time = "";
		
	}

	@Override
	public void setPID(int pid) {
		// TODO Auto-generated method stub
		this.pID = pid;
		
	}

	@Override
	public void setEpic(String epic) {
		// TODO Auto-generated method stub
		this.epic = epic;
	}

	@Override
	public void setNoShares(int noShares) {
		// TODO Auto-generated method stub
		this.noShares = noShares;
	}

	@Override
	public void setType(String type) {
		// TODO Auto-generated method stub
		this.type = type;
	}

	@Override
	public void setTime(String time) {
		// TODO Auto-generated method stub
		this.time = time;
	}

	@Override
	public int getPID() {
		// TODO Auto-generated method stub
		return pID;
	}

	@Override
	public String getEpic() {
		// TODO Auto-generated method stub
		return epic;
	}

	@Override
	public int getNoShares() {
		// TODO Auto-generated method stub
		return noShares;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return type;
	}

	@Override
	public String getTime() {
		// TODO Auto-generated method stub
		return time;
	}

}
