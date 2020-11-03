package uk.co.pm.portfolio;

import java.util.ArrayList;

public class PFJSONReference extends PFReference{
	private String link;
	private ArrayList<String> l; 
	public PFJSONReference(int PID, String name, String manager, double cash, String currency, String link) {
		super(PID, name, manager, cash, currency);
		this.link = link;
		l = new ArrayList<String>();
	}
	
	public PFJSONReference(int PID, String name, String manager, double cash, String currency, ArrayList<String> link) {
		super(PID, name, manager, cash, currency);
		this.l = link;
		this.link = " ";
	}
	
	public String getLink() {
		return link;
	}
	
	public ArrayList<String> getLinkList(){
		return l;
	}

}
