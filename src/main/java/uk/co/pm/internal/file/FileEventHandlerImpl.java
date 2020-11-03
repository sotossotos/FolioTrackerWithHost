package uk.co.pm.internal.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import uk.co.pm.dao.EQDao;
import uk.co.pm.dao.PortfolioDao;
import uk.co.pm.dao.csv.reader.CSVReader;
import uk.co.pm.dao.csv.reader.FileReadErrorException;
import uk.co.pm.dao.csv.reader.IHoldings;
import uk.co.pm.dao.csv.reader.SyntaxErrorException;

public class FileEventHandlerImpl implements FileEventHandler{
	
	private EQDao dao;
	private PortfolioDao daoPF;
	private CSVReader reader;
	
	public FileEventHandlerImpl(EQDao dao, PortfolioDao daoPF) {
		// TODO Auto-generated constructor stub
		this.dao = dao;
		this.daoPF = daoPF;
		this.reader = new CSVReader();
	}
	public void readExistingCSV(){
		
			File directory = new File(System.getProperty("user.dir")+"/csv");
			File[] matchingFiles = directory.listFiles(new FilenameFilter() {
			    public boolean accept(File directory, String name) {
			        return name.endsWith("csv");
			    }
			});

			for(int i = 0;i<matchingFiles.length;i++) {
				reader(matchingFiles[i].getName());
			}
		
	}
	private void reader(String file ){
		if(file.contains("details")){
			setStocks(file);
		}
		if(file.contains("price")){
			try {
				dao.setStocksPrice(reader.readEquityPricing(System.getProperty("user.dir").toString()+"/csv/"+file));
			} catch (FileReadErrorException | SyntaxErrorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(file.contains("portfolio")){
			try {
				daoPF.setPortfolios(reader.readPortfolio(System.getProperty("user.dir").toString()+"/csv/"+file));
			} catch (FileNotFoundException | FileReadErrorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(file.contains("transactions")){
			try {
				for(IHoldings h:reader.readHoldings(System.getProperty("user.dir").toString()+"/csv/"+file)){
					daoPF.setTransactions(h);	
				}
				
			} catch (FileNotFoundException | FileReadErrorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	private void setStocks(String file){
		try {
			dao.setStocks(reader.readEquities(System.getProperty("user.dir").toString()+"/csv/"+file));
		} catch (FileReadErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void handle(Path file) throws IOException {
		System.out.println(file.toString());
		reader(file.toString());
		Path dir = Paths.get(System.getProperty("user.dir")+"/csv");

		
	}

}
