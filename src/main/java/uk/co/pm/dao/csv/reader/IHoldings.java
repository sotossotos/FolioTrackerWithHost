package uk.co.pm.dao.csv.reader;

public interface IHoldings {
	
	public void setPID(int pid);
			
	public void setEpic(String epic);
	
	public void setNoShares(int noShares);
	
	public void setType(String type);
	
	public void setTime(String time);
	
	public int getPID();
	
	public String getEpic();
	
	public int getNoShares();
	
	public String getType();
	
	public String getTime();
			
}
