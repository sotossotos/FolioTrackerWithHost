package uk.co.pm.holding;

public class HSReference {
	private int PID;
	private String epic;
	private int noShares; 
	private String type;
	private String time;

	public HSReference(int PID, String epic, int noShares, String type, String time){
		this.PID =PID;
		this.epic=epic;
		this.noShares=noShares;
		this.type=type;
		this.time=time;

	}
    public int getPID() {
        return this.PID;
    }
    public String getEpic() {
        return this.epic;
    }
    public int getNoShares() {
        return this.noShares;
    }
    public String getType() {
        return this.type;
    }
    public String getTime() {
        return this.time;
    }

}
