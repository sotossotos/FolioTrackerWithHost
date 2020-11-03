package uk.co.pm.holding;

import java.text.DecimalFormat;

public class ViewTransaction {
	private String epic;
	private int quantity;
	private double equityPrice;
	private double total;
	private double benchmarkPercentage;
	


	public ViewTransaction(){
		this.epic = "";
		this.equityPrice = 0.0;
		this.quantity = 0;
		this.total = 0.0;
		this.benchmarkPercentage =0.0;
	}

	public double getBenchmarkPercentage() {
		return benchmarkPercentage;
	}

	public void setBenchmarkPercentage(double benchmarkPercentage) {
		this.benchmarkPercentage = benchmarkPercentage;
	}
	
	public void setEpic(String epic){
		this.epic = epic;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public void setEquityprice(double equityPrice) {
		this.equityPrice = equityPrice;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public String getEpic(){
		return this.epic;
	}
	public int getQuantity() {
		return this.quantity;
	}
	
	public double getEquityprice(){
		DecimalFormat df = new DecimalFormat("#.##");      
		double res = Double.valueOf(df.format(this.equityPrice));
		return res;
	}
	
	public double getTotal() {
		DecimalFormat df = new DecimalFormat("#.##");      
		double res = Double.valueOf(df.format(this.total));
		return res;
	}
	
	
	
}
