package uk.co.pm.dao;


import uk.co.pm.dao.csv.reader.IEquity;
import uk.co.pm.dao.csv.reader.IEquityPricing;
import uk.co.pm.equity.EQReference;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EQDao {

    private DataSource dataSource;
    private Set<IEquity> stocks;
    private Set<IEquityPricing> stocksPrice;
    private List<EQReference> eqs;

    public EQDao(DataSource dataSource) {
        this.dataSource = dataSource;
        this.stocks = new HashSet<>();
        this.stocksPrice = new HashSet<>();
    }
    public void setStocks(Set<IEquity> stocks){
    	this.stocks = stocks;
    }
    public void setStocksPrice(Set<IEquityPricing> stocksPrice){
    	for(IEquityPricing price : stocksPrice){
    		this.stocksPrice.add(price);
    	}
    }
    public void populateDB(){
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("insert into EQ (EPIC,COMPANYNAME,ASSETTYPE,SECTOR,CURRENCY,DATETIME,PRICE)"
            		+ "VALUES (?, ?, ?, ?, ?, ?, ?)");
            for(IEquity stock: stocks) {
            	for(IEquityPricing price: stocksPrice){
            		if(stock.getEPIC().equals(price.getEPIC())){
            			statement.setString(1, stock.getEPIC());
            			statement.setString(2, stock.getCompanyName());
            			statement.setString(3, stock.getAssetType());
            			statement.setString(4, stock.getSector());
            			statement.setString(5, stock.getCurrencyType());
            			statement.setString(6, price.getDate());
            			statement.setString(7, price.getMidPrice());
            			statement.execute();
            			}
            	}
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void connectRetrieveEQs(String EPIC) {
    	eqs = new ArrayList<>(); 
         try {
        	 Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             if(EPIC.equals("")){
            	 statement.execute("select * from EQ ORDER BY EPIC ASC");
             }else{
            	 statement.execute("select * from EQ where epic='"+EPIC+"'");
             }
             ResultSet resultSet = statement.getResultSet();
             while(resultSet.next()){
                 String epic = resultSet.getString("EPIC");
                 String companyname = resultSet.getString("COMPANYNAME");               
                 String assettype = resultSet.getString("ASSETTYPE");               
                 String sector = resultSet.getString("SECTOR");                
                 String currency = resultSet.getString("CURRENCY");                
                 String datetime = resultSet.getString("DATETIME");              
                 Float price = resultSet.getFloat("PRICE");                
                 eqs.add(new EQReference(epic,companyname,assettype,sector,currency,datetime,price));
             }
             statement.close();
             connection.close();
         } catch (SQLException e) {
             e.printStackTrace();
         }
    }
    public List<EQReference> getEQ(){
    	connectRetrieveEQs("");
        return eqs;
    }
    public List<EQReference>getSpecificEQ(String EPIC){
    	connectRetrieveEQs(EPIC);
        return eqs;
    }
 
}
