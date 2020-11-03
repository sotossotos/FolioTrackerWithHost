package uk.co.pm.dao.csv.reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CSVReader {
	
	public CSVReader() {
		
	}

	/***
	 * 
	 * @param filepath - This is the filepath of the .CSV containing the Equity data
	 * @return A collection of equity objects
	 * @throws FileNotFoundException - This is thrown when the file could not be found
	 * @throws FileReadErrorException - This is thrown when the file was found but 
	 *         there was some error in reading it (that is not syntax related)
	 *         SyntaxErrorException - This is thrown when there is an error with the syntax of a line in a file
	 */
	public Set<IEquity> readEquities(String filepath) throws FileNotFoundException, FileReadErrorException{
	
		FileReader equityFileReader = new FileReader(filepath);
		BufferedReader equityBufferedReader = new BufferedReader(equityFileReader);
		String line = null;
		
		Set<IEquity> equities = new HashSet<>();
		
		try {
			equityBufferedReader.readLine();
			while( (line = equityBufferedReader.readLine()) != null) {
				equities.add(parseEquity(line));
				
			}
			equityBufferedReader.close();
			
			return equities;
			
		} catch (IOException e) {
			throw new FileReadErrorException("File at " + filepath + " was found but could not be read properly");
		}
	}
	///SETIPORTFOLIO
	public Set<IPortfolio> readPortfolio(String filepath) throws FileNotFoundException, FileReadErrorException{
		
		FileReader portfolioFileReader = new FileReader(filepath);
		BufferedReader portfolioBufferedReader = new BufferedReader(portfolioFileReader);
		String line = null;
		
		Set<IPortfolio> portfolio = new HashSet<>();
		
		try {
			portfolioBufferedReader.readLine();
			while( (line = portfolioBufferedReader.readLine()) != null) {
				portfolio.add(parsePortfolio(line));
				
			}
			portfolioBufferedReader.close();
			
			return portfolio;
			
		} catch (IOException e) {
			throw new FileReadErrorException("File at " + filepath + " was found but could not be read properly");
		}
	}
// SETIHOLDINGS	
	public Set<IHoldings> readHoldings(String filepath) throws FileNotFoundException, FileReadErrorException{
		
		FileReader holdingsFileReader = new FileReader(filepath);
		BufferedReader holdingsBufferedReader = new BufferedReader(holdingsFileReader);
		String line = null;
		
		Set<IHoldings> holdings = new HashSet<>();
		
		try {
			holdingsBufferedReader.readLine();
			while( (line = holdingsBufferedReader.readLine()) != null) {
				holdings.add(parseHoldings(line));
				
			}
			holdingsBufferedReader.close();
			
			return holdings;
			
		} catch (IOException e) {
			throw new FileReadErrorException("File at " + filepath + " was found but could not be read properly");
		}
	}
	/*The following method could be changed into reading portfolios pretty easily. 
	 * Token 0:Portfolio Identifier   Token 1:Portfolio Name  Token 2:Portfolio Manager Token 3:Cash
	 * Token 4:Currency
	 * Recommendation copy method and change it to a portfolio reader with the above changes to the 
	 * different tokens.
	 * */
	/* This could also be easily changed to read the Transaction CSV's pretty similarly to the changes
	 * needed to change it to the portfolio reader. Token 0:Portfolio Identifier Token 1:EPIC Token 2:No of Shares
	 * Token 3:Buy/Sell Token 4:Transaction Date 
	 * The catch for this is the naming of the transaction files must follow a specific convention there can be a
	 * prefix that team name but there does not have to be "transactions-YYYY-QQ.csv" if the newest file is not 
	 * the very next Quarter from the same year an error should be thrown.
	 **/	
	private IEquity parseEquity(String equityString) {
		IEquity equity = new Equity();
		
		String[] tokens = equityString.split(",");//\\s
		
		if(tokens.length != 5) {
			new IllegalNumberOfTokensException(equityString, 5, tokens.length);
		}
		
		//At the moment this is allowing for any file with 
		//five strings on each line to be a valid equity file.
		//The reasoning for this is that there are no given standards
		//for what a valid equity file contains
		String epic = tokens[0];
		String companyName = tokens[1];
		String assetType = tokens[2];
		String sector = tokens[3];
		String currency = tokens[4];		
		equity.setEPIC(epic);
		equity.setCompanyName(companyName);
		equity.setAssetType(assetType);
		equity.setSector(sector);
		equity.setCurrencyType(currency);
		
		return equity;
	}
	
	private IPortfolio parsePortfolio(String portfolioString) {
		IPortfolio portfolio = new Portfolio();
		
		String[] tokens = portfolioString.split(",");//\\s
		
		if(tokens.length != 6) {
			new IllegalNumberOfTokensException(portfolioString, 6, tokens.length);
		}
		
		//At the moment this is allowing for any file with 
		//five strings on each line to be a valid equity file.
		//The reasoning for this is that there are no given standards
		//for what a valid equity file contains
		String pId = tokens[0];
		String name = tokens[1];
		String managerName = tokens[2];
		String cash = tokens[3];
		String currency = tokens[4];
		String isBenchmark = tokens[5];
		portfolio.setPID(Integer.parseInt(pId));
	    portfolio.setName(name); 
	    portfolio.setManager(managerName);
		portfolio.setCashQ1(Double.parseDouble(cash));
		portfolio.setCashQ2(Double.parseDouble(cash));
		portfolio.setCurrency(currency);
		if(isBenchmark.equals("Y")){
			portfolio.setIsBenchmark();
		}
		return portfolio;
	}
	private IHoldings parseHoldings(String holdingsString) {
		IHoldings holdings = new Holdings();
		
		String[] tokens = holdingsString.split(",");//\\s
		
		if(tokens.length != 5) {
			new IllegalNumberOfTokensException(holdingsString, 5, tokens.length);
		}
		
		//At the moment this is allowing for any file with 
		//five strings on each line to be a valid equity file.
		//The reasoning for this is that there are no given standards
		//for what a valid equity file contains
		String pId = tokens[0];
		String epic = tokens[1];
		String numberShares= tokens[2];
		String buyOrSell = tokens[3];
		String time = tokens[4];
		// PID INT, EPIC VARCHAR(20), NO_SHARES INT, TYPE VARCHAR (10), TIME VARCHAR(20));		
		holdings.setPID(Integer.parseInt(pId));
		holdings.setEpic(epic);
		holdings.setNoShares(Integer.parseInt(numberShares));
		holdings.setType(buyOrSell);
		holdings.setTime(time);
		
		return holdings;
	}

	
	
	/***
	 * 
	 * @param filepath - The path of where the pricing equity file 
	 *                   is located
	 * @return
	 * @throws FileReadErrorException - Thrown when there is an error reading the given file (that is not a syntax error)
	 * @throws FileNotFoundException - Thrown when a file at the given path is not found
	 * @throws SyntaxErrorException - Thrown when a line in a file does not meet the required syntax
	 * 
	 */
	public Set<IEquityPricing> readEquityPricing(String filepath) throws FileReadErrorException, FileNotFoundException, SyntaxErrorException {
		FileReader equityFileReader = new FileReader(filepath);
		BufferedReader equityBufferedReader = new BufferedReader(equityFileReader);
		String line = null;
		
		Set<IEquityPricing> equityPricings = new HashSet<>();
		
		try {
			while( (line = equityBufferedReader.readLine()) != null) {
				equityPricings.add(parseEquityPricing(line));
			}
			equityBufferedReader.close();
			
			return equityPricings;
			
		} catch (IOException e) {
			throw new FileReadErrorException("File at " + filepath + " was found but could not be read properly");
		} 
	}
	
	private IEquityPricing parseEquityPricing(String equityPricingString) throws SyntaxErrorException {
		IEquityPricing equityPricing = new EquityPricing();
		
		String[] tokens = equityPricingString.split(",");
		
		if(tokens.length != 4) {
		    new IllegalNumberOfTokensException(equityPricingString, 4, tokens.length);
		}
		
		String epic = tokens[0];
		String date = tokens[1];
		String midPrice = tokens[2];
		String currency = tokens[3];
		
		equityPricing.setEPIC(epic);
		IEquityPricingDate epDate = new EquityPricingDate();
		try {
			epDate.setDate(date);
			equityPricing.setDate(epDate);
		} catch (IllegalEquityDateFormatException e) {
			throw new SyntaxErrorException(equityPricingString, e.getMessage());
		}
		
		IEquityPricingMidPrice epMidPrice = new EquityPricingMidPrice();
		try {
			epMidPrice.setPrice(midPrice);
			equityPricing.setMidPrice(epMidPrice);
		} catch (IllegalMidPriceFormatException e) {
			throw new SyntaxErrorException(equityPricingString, e.getMessage());
		}
		
		equityPricing.setCurrency(currency);
		
		return equityPricing;
	}
}
