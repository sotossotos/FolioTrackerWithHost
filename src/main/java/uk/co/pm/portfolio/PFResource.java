	package uk.co.pm.portfolio;

	import org.glassfish.jersey.server.mvc.Viewable;
	import uk.co.pm.dao.PortfolioDao;
import uk.co.pm.holding.TransactionReference;
import uk.co.pm.holding.ViewTransaction;
import uk.co.pm.internal.view.ViewModelBuilder;

	import javax.ws.rs.Consumes;
	import javax.ws.rs.GET;
	import javax.ws.rs.Path;
	import javax.ws.rs.PathParam;
	import javax.ws.rs.Produces;
	import javax.ws.rs.core.MediaType;

	import java.util.ArrayList;
	import java.util.List;
	import java.util.Map;
	@Path("PF")
	public class PFResource {


	    private PortfolioDao dao;

	    public PFResource(PortfolioDao dao) {
	        this.dao = dao;
	    }

	    
	    @GET
	    @Consumes(MediaType.APPLICATION_JSON)
	    @Produces(MediaType.APPLICATION_JSON)
	    public List<PFJSONReference> allPortfoliosJSON() {
	    	List <PFJSONReference> list = new ArrayList<>();
	    	List<PFReference> PFReferences = dao.getPF();
	    	String link;
	    	for(PFReference ref : PFReferences){
	    		link ="http://localhost:8080/PF/"+ref.getPid();
	    		list.add(new PFJSONReference(ref.getPid(), ref.getName(), ref.getManager(), ref.getCashq1(), ref.getCurrency(), link));
	    	}
	    	return list;
	    }
	    @Path("/{PID}")
	    @GET
	    @Consumes(MediaType.APPLICATION_JSON)
	    @Produces(MediaType.APPLICATION_JSON)
	    public PFJSONReference specificPortfolioJSON(@PathParam("PID")int PID) {
	    	ArrayList<String> list = new ArrayList<>();
	    	PFReference PFReferences = dao.getSpecificPF(PID);
	    	list.add("http://localhost:8080/PF/"+PFReferences.getPid() + "/2015-Q1");
	    	list.add("http://localhost:8080/PF/"+PFReferences.getPid() + "/2015-Q2");
	    	PFJSONReference portfolio = new PFJSONReference(PFReferences.getPid(), PFReferences.getName(),PFReferences.getManager(),PFReferences.getCashq1() ,PFReferences.getCurrency(), list);
	    	return portfolio;
	    }
	    @Path("/{PID}/{QUARTER}")
	    @GET
	    @Consumes(MediaType.APPLICATION_JSON)
	    @Produces(MediaType.APPLICATION_JSON)
	    public TransactionReference specificPortfolioJSON(@PathParam("PID")int PID,@PathParam("QUARTER")String QUARTER) {
	    	TransactionReference result=dao.getSnapShot(QUARTER, PID,"");
	    	return result;
	    }
	    
	    @GET
	    @Produces(MediaType.TEXT_HTML)
	    public Viewable getAllView(){
	        List<PFReference> PFReferences = dao.getPF();
	        Map<String, Object> portfolios = ViewModelBuilder.init().add("PORTFOLIOS", PFReferences).build();
	        return new Viewable("/AllPortfolios.mustache", portfolios);
	    }
	    
	    @Path("/{PID}/{QUARTER}")
	    @GET
	    @Produces(MediaType.TEXT_HTML)
	    public Viewable getSpecificView(@PathParam("PID")int PID,@PathParam("QUARTER")String quarter){
	    	TransactionReference transRef = dao.getSnapShot(quarter, PID,"");
	    	System.out.println("number of benchmarks:"+transRef.getBenchmarkNames().size());
	    	Map<String, Object> portfolios= ViewModelBuilder.init()
	    			.add("FOLIO", transRef).build();
	        return new Viewable("/Portfolio.mustache", portfolios);
	    }

	    @Path("/{PID}/{QUARTER}/transactions")
	    @GET
	    @Produces(MediaType.TEXT_HTML)
	    public Viewable getTransactionsOfPortfolio(@PathParam("PID")int PID,@PathParam("QUARTER")String quarter){
	    	TransactionReference transRef = dao.getSnapShot(quarter, PID,"");
	    	Map<String, Object> portfolios= ViewModelBuilder.init().add("Transaction", transRef.getTransactions()).build();
	        return new Viewable("/Transaction.mustache", portfolios);
	    }
	    
	    @Path("/{PID}/{QUARTER}/transactions/{BENCHMARKNAME}")
	    @GET
	    @Produces(MediaType.TEXT_HTML)
	    public Viewable getTransactionsOfPortfolio(@PathParam("PID")int PID,@PathParam("QUARTER")String quarter,@PathParam("BENCHMARKNAME")String benchmarkName){
	    	TransactionReference transRef = dao.getSnapShot(quarter, PID,benchmarkName);
	    	Map<String, Object> portfolios= ViewModelBuilder.init().add("Transaction", transRef.getTransactions()).build();
	        return new Viewable("/Transaction.mustache", portfolios);
	    }
	    
	    public List<PFReference> getallPortfolios(){
	    	return dao.getPF();
	    }
	    
	    public PFReference getSpecificPF(int id){
	    	return dao.getSpecificPF(id);
	    }



	
}
