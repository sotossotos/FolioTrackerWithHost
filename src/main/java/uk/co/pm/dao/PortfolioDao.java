package uk.co.pm.dao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import uk.co.pm.dao.csv.reader.IEquity;
import uk.co.pm.dao.csv.reader.IEquityPricing;
import uk.co.pm.dao.csv.reader.IHoldings;
import uk.co.pm.dao.csv.reader.IPortfolio;
import uk.co.pm.equity.EQReference;
import uk.co.pm.holding.HSReference;
import uk.co.pm.holding.TransactionReference;
import uk.co.pm.holding.ViewTransaction;
import uk.co.pm.portfolio.PFReference;

public class PortfolioDao {

	private DataSource dataSource;
	private Set<IPortfolio> portfolios;
	private List<IHoldings> transactions;
	private List<IHoldings> benchmarkTransactions;
	private List<PFReference> ptf;
	private List<HSReference> trs;
	private List<IPortfolio> benchmarksPortfolio;
	private Set<String> benchmarks;
	private EQDao dao;

	public PortfolioDao(DataSource dataSource) {
		this.dataSource = dataSource;
		this.portfolios = new HashSet<>();
		this.transactions = new ArrayList<>();
		this.benchmarks = new HashSet<>();
		trs = new ArrayList<HSReference>();
		this.benchmarksPortfolio = new ArrayList<>();
		this.benchmarkTransactions = new ArrayList<>();
	}

	private void findBenchmarks() {
		for (IPortfolio pf : portfolios) {
			if (pf.getIsBenchmark().equals("Y")) {
				this.benchmarksPortfolio.add(pf);
			}
		}
	}

	public void setEQs(EQDao dao) {
		this.dao = dao;
		removeWrongTransactions();
		findBenchmarks();
		this.findBenchmarkTransactions();
	}

	public void setPortfolios(Set<IPortfolio> portfolios) {
		this.portfolios = portfolios;
	}

	public void setTransactions(IHoldings holding) {
		this.transactions.add(holding);
	}

	private double getPriceOfEQ(List<EQReference> eqs, String quarter) {
		double res = 0.0;
		for (EQReference eq : eqs) {
			if (eq.getDateTime().equals(quarter)) {
				res = eq.getPrice();
			}
		}
		return res;
	}

	private void findBenchmarkTransactions() {
		for (IHoldings h : transactions) {
			for (IPortfolio bF : benchmarksPortfolio) {
				if (h.getPID() == bF.getPID()) {
					this.benchmarkTransactions.add(h);
				}
			}
		}

	}

	private void removeWrongTransactions() {
		for (IPortfolio pf : portfolios) {
			double cash1 = pf.getCashQ1();
			List<IHoldings> toDelete = new ArrayList<>();

			for (IHoldings tr : transactions) {
				if (pf.getPID() == tr.getPID()) {
					if (tr.getTime().equals("2015-Q1")) {
						double eqPrice = getPriceOfEQ(dao.getSpecificEQ(tr.getEpic()), tr.getTime());
						if (tr.getType().equals("Buy")) {
							if ((cash1 - (eqPrice * tr.getNoShares())) < 0) {
								cash1 = 0;
							} else {
								cash1 = cash1 - (eqPrice * tr.getNoShares());
							}
						} else if (tr.getType().equals("Sell")) {
							cash1 = cash1 + (eqPrice * tr.getNoShares());
						}
					}
				}
				if (cash1 == 0) {
					toDelete.add(tr);
				}
			}
			double cash2 = cash1;
			for (IHoldings tr : transactions) {
				if (pf.getPID() == tr.getPID()) {
					if (tr.getTime().equals("2015-Q2")) {
						double eqPrice = getPriceOfEQ(dao.getSpecificEQ(tr.getEpic()), tr.getTime());
						if (tr.getType().equals("Buy")) {
							if ((cash2 - (eqPrice * tr.getNoShares())) < 0) {
								cash2 = 0;
							} else {
								cash2 = cash2 - (eqPrice * tr.getNoShares());

							}
						} else if (tr.getType().equals("Sell")) {
							cash2 = cash2 + (eqPrice * tr.getNoShares());
						}
					}
				}
				if (cash2 == 0) {
					toDelete.add(tr);
				}
			}
			pf.setCashQ1(cash1);
			pf.setCashQ2(cash2);
			for (IHoldings d : toDelete) {
				for (int j = 0; j < transactions.size(); j++) {
					if (transactions.get(j).getEpic().equals(d.getEpic()) && transactions.get(j).getPID() == d.getPID()
							&& transactions.get(j).getTime().equals(d.getTime())) {
						transactions.remove(d);
					}
				}
			}

		}
	}

	public void populateDB() {
		try {
			Connection connection = dataSource.getConnection();
			PreparedStatement statement1 = connection.prepareStatement(
					"insert into PORTFOLIO (PID,NAME,MANAGER,CASH,CURRENCY,BENCHMARK)" + "VALUES (?, ?, ?, ?, ?, ?)");
			PreparedStatement statement2 = connection
					.prepareStatement("insert into HOLDINGS (PID,EPIC,NO_SHARES,TYPE,TIME)" + "VALUES(?, ?, ?, ?, ?)");

			for (IPortfolio portfolio : portfolios) {
				statement1.setInt(1, portfolio.getPID());
				statement1.setString(2, portfolio.getName());
				statement1.setString(3, portfolio.getManager());
				statement1.setDouble(4, portfolio.getCashQ1());
				statement1.setString(5, portfolio.getCurrency());
				statement1.setString(6, portfolio.getIsBenchmark());
				statement1.execute();
			}
			for (IHoldings transaction : transactions) {
				statement2.setInt(1, transaction.getPID());
				statement2.setString(2, transaction.getEpic());
				statement2.setInt(3, transaction.getNoShares());
				statement2.setString(4, transaction.getType());
				statement2.setString(5, transaction.getTime());
				statement2.execute();
			}
			statement1.close();
			statement2.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Set<String> getBenchmarkNames() {
		try {
			Connection connection = dataSource.getConnection();
			Statement statement = connection.createStatement();
			statement.execute("select * from PORTFOLIO WHERE BENCHMARK =\'Y\'");
			ResultSet resultSet = statement.getResultSet();
			while (resultSet.next()) {
				String name = resultSet.getString("NAME");
				benchmarks.add(name);
			}
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return benchmarks;

	}

	private double calculateBenchmark(int portfolioQuantity, int benchMarkQuantity) {
		double percentage = 0.0;
		percentage = ((double) (portfolioQuantity - benchMarkQuantity) / (double) benchMarkQuantity) * 100.0;
		return round(percentage,2);
	}

	private static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public PFReference getSpecificPF(int id) {
		PFReference pf = null;
		try {
			Connection connection = dataSource.getConnection();
			Statement statement = connection.createStatement();
			statement.execute("select * from PORTFOLIO where PID=" + id);
			ResultSet resultSet = statement.getResultSet();
			while (resultSet.next()) {
				int pID = resultSet.getInt("PID");
				String name = resultSet.getString("NAME");
				String manager = resultSet.getString("MANAGER");
				int cash = resultSet.getInt("CASH");
				String currency = resultSet.getString("CURRENCY");
				pf = new PFReference(pID, name, manager, cash, currency);
			}
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pf;
	}

	public List<PFReference> getPF() {
		ptf = new ArrayList<PFReference>();
		try {
			Connection connection = dataSource.getConnection();
			Statement statement = connection.createStatement();
			statement.execute("select * from PORTFOLIO WHERE BENCHMARK =\'N\' ORDER BY PID ASC");
			ResultSet resultSet = statement.getResultSet();
			while (resultSet.next()) {
				int pID = resultSet.getInt("PID");
				String name = resultSet.getString("NAME");
				String manager = resultSet.getString("MANAGER");
				int cash = resultSet.getInt("CASH");
				String currency = resultSet.getString("CURRENCY");
				ptf.add(new PFReference(pID, name, manager, cash, currency));
			}
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ptf;
	}

	private double getEQprice(String epic, String quarter) {
		double res = 0.0;
		for (EQReference ref : dao.getEQ()) {
			if (ref.getEpic().equals(epic) && ref.getDateTime().equals(quarter)) {
				res = (double) ref.getPrice();
			}
		}
		return res;
	}

	private double getFolioCash(int pid, String quarter) {
		double res = 0.0;
		for (IPortfolio p : portfolios) {
			if (p.getPID() == pid) {
				switch (quarter) {
				case "2015-Q1":
					res = p.getCashQ1();
					break;
				case "2015-Q2":
					res = p.getCashQ2();
				}
			}
		}
		System.out.println("cash for " + pid + "is " + res);
		return res;
	}

	public TransactionReference getSnapShot(String quarter, int pid, String benchmarkName) {
		TransactionReference snapshot = new TransactionReference();
		snapshot.setCash(getFolioCash(pid, quarter));
		System.out.println(snapshot.getCash());
		double valueOfTransactions = 0.0;
		snapshot.setPid(pid);
		for (IHoldings h : transactions) {
			ViewTransaction temp = new ViewTransaction();
			if (pid == h.getPID()) {
				switch (quarter) {
				case "2015-Q1":
					if (h.getTime().equals(quarter)) {
						temp.setEpic(h.getEpic());
						temp.setQuantity(h.getNoShares());
						temp.setEquityprice(getEQprice(h.getEpic(), quarter));
						temp.setTotal(temp.getEquityprice() * temp.getQuantity());
						valueOfTransactions += temp.getEquityprice() * temp.getQuantity();
						snapshot.addTransaction(temp);
						if (!benchmarkName.equals("")) {
							for (IHoldings bH : benchmarkTransactions) {

								if (h.getEpic().equals(bH.getEpic()) && h.getTime().equals(quarter) && bH.getTime().equals(quarter)) {
									System.out.println("This is the Transaction EPIC " + h.getEpic()
											+ " This is the BENCHAMRK EPIC " + bH.getEpic());
									System.out.println("got herer  " + h.getNoShares() + "       " + bH.getNoShares());
									temp.setBenchmarkPercentage(calculateBenchmark(h.getNoShares(), bH.getNoShares()));
								}
							}
						}
						// snapshot.setBenchmarkNames(getBenchmarkNames());

					}

					break;
				case "2015-Q2":
					temp.setEpic(h.getEpic());
					temp.setQuantity(h.getNoShares());
					temp.setEquityprice(this.getEQprice(h.getEpic(), quarter));
					temp.setTotal(temp.getEquityprice() * temp.getQuantity());
					valueOfTransactions += temp.getEquityprice() * temp.getQuantity();
					snapshot.addTransaction(temp);
					if (!benchmarkName.equals("")) {
						for (IHoldings bH : benchmarkTransactions) {

							if (h.getEpic().equals(bH.getEpic())&& h.getTime().equals(quarter) && bH.getTime().equals(quarter)) {
								System.out.println("This is the Transaction EPIC " + h.getEpic()
										+ " This is the BENCHAMRK EPIC " + bH.getEpic());
								System.out.println("got herer  " + h.getNoShares() + "       " + bH.getNoShares());
								temp.setBenchmarkPercentage(calculateBenchmark(h.getNoShares(), bH.getNoShares()));
							}
						}
					}
					// snapshot.setBenchmarkNames(getBenchmarkNames());
					break;
				}

			}

		}
		snapshot.setBenchmarkNames(getBenchmarkNames());
		snapshot.setPortfolioValue(getFolioCash(pid, quarter) + valueOfTransactions);

		System.out.println(valueOfTransactions + " Value of transactions");

		return snapshot;
	}
}
