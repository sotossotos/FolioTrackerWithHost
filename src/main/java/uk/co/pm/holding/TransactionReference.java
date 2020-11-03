package uk.co.pm.holding;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TransactionReference {
	private int pid;
	private double cash;
	private double portfolioValue;
	private List<ViewTransaction>transactions;
	private Set<String> benchmarkNames; 

	public TransactionReference() {
		pid = 0;
		cash = 0.0;
		portfolioValue = 0.0;
		transactions = new ArrayList<>();
		benchmarkNames = new HashSet<>();
	}

	public Set<String> getBenchmarkNames() {
		return benchmarkNames;
	}

	public void setBenchmarkNames(Set<String> set) {
		this.benchmarkNames = set;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public void setCash(double cash) {
		this.cash = cash;
	}
	public void setPortfolioValue(double total) {
		this.portfolioValue = total;
	}

	public void addTransaction(ViewTransaction trans){
		this.transactions.add(trans);
	}
	public int getPid() {
		return this.pid;
	}
	public double getCash() {
		DecimalFormat df = new DecimalFormat("#.##");      
		double res = Double.valueOf(df.format(this.cash));
		return res;
	}
	public double getPortfolioValue() {
		DecimalFormat df = new DecimalFormat("#.##");      
		double res = Double.valueOf(df.format(this.portfolioValue));
		return res;
	}

	public List<ViewTransaction> getTransactions (){
		return this.transactions;
	}

	@Override
	public String toString() {
		return "TransactionReference [pid=" + pid + ", cash=" + cash + ", portfolioValue=" + portfolioValue
				+ ", transactions=" + transactions + "]";
	}

}
