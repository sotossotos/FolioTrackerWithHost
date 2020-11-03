package uk.co.pm;

import org.glassfish.jersey.server.ResourceConfig;
import uk.co.pm.dao.EQDao;
import uk.co.pm.dao.PortfolioDao;
import uk.co.pm.dao.csv.reader.CSVReader;
import uk.co.pm.equity.EQResource;

import uk.co.pm.internal.ApplicationBase;
import uk.co.pm.internal.Configuration;
import uk.co.pm.internal.file.FileEventHandler;
import uk.co.pm.internal.file.FileEventHandlerImpl;
import uk.co.pm.internal.file.FileWatcher;
import uk.co.pm.portfolio.PFResource;

import javax.sql.DataSource;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class PortfolioManagerApplication extends ApplicationBase {

    @Override
    protected void configure(ResourceConfig jerseyConfig, Configuration config, DataSource dataSource, FileWatcher fileWatcher){
    	Path dir = Paths.get(System.getProperty("user.dir")+"/csv");
    	EQDao dao = new EQDao(dataSource);
    	PortfolioDao daoPF = new PortfolioDao(dataSource);
    	FileEventHandler handler = new FileEventHandlerImpl(dao, daoPF);
    	handler.readExistingCSV();
    	fileWatcher.register(dir, handler);
    	daoPF.populateDB();
    	dao.populateDB();
    	daoPF.setEQs(dao);
        EQResource eq = new EQResource(dao);
        PFResource pf = new PFResource(daoPF);
        jerseyConfig.register(eq);
        jerseyConfig.register(pf);
    }

    public static void main(String[] args) throws Exception {
        new PortfolioManagerApplication().init(args).run();
    }

}
