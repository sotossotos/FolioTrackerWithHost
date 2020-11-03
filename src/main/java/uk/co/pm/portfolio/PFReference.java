package uk.co.pm.portfolio;

public class PFReference {
	private int pid;
	private String name;
	private String manager; 
	private double cashq1;
	private double cashq2;
	private String currency;

	public PFReference(int PID, String name, String manager, double cash, String currency){
		this.pid =PID;
		this.name=name;
		this.manager=manager;
		this.cashq1=cash;
		this.cashq2=cash;
		this.currency=currency;

	}
    public int getPid() {
        return this.pid;
    }
    public String getName() {
        return this.name;
    }
    public String getManager() {
        return this.manager;
    }
    public double getCashq1() {
        return this.cashq1;
    }

    public double getCashq2() {
        return this.cashq2;
    }
    public String getCurrency() {
        return this.currency;
    }

}
