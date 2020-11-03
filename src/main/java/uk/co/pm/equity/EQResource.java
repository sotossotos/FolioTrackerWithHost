	package uk.co.pm.equity;

	import org.glassfish.jersey.server.mvc.Viewable;
	import uk.co.pm.dao.EQDao;
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
	@Path("EQ")
	public class EQResource {


	    private EQDao dao;

	    public EQResource(EQDao dao) {
	        this.dao = dao;
	    }

	    
	    @GET
	    @Consumes(MediaType.APPLICATION_JSON)
	    @Produces(MediaType.APPLICATION_JSON)
	    public List<EQJSONReference> allEquitiesJSON() {
	    	List <EQJSONReference> list = new ArrayList<>();
	    	List<EQReference> EQReferences = dao.getEQ();
	    	String link;
	    	for(EQReference ref : EQReferences){
	    		link ="http://localhost:8080/EQ/"+ref.getEpic();
	    		list.add(new EQJSONReference(ref.getEpic(), ref.getCompanyName(), ref.getAssetType(), ref.getSector(), ref.getCurrency(), ref.getDateTime(), ref.getPrice(), link));
	    	}
	    	return list;
	    }
	    @Path("/{EPIC}")
	    @GET
	    @Consumes(MediaType.APPLICATION_JSON)
	    @Produces(MediaType.APPLICATION_JSON)
	    public List<EQJSONReference> specificEquityJSON(@PathParam("EPIC")String epic) {
	    	List <EQJSONReference> list = new ArrayList<>();
	    	List<EQReference> EQReferences = dao.getSpecificEQ(epic);
	    	String link;
	    	for(EQReference ref : EQReferences){
	    		link ="http://localhost:8080/EQ/"+ref.getEpic();
	    		list.add(new EQJSONReference(ref.getEpic(), ref.getCompanyName(), ref.getAssetType(), ref.getSector(), ref.getCurrency(), ref.getDateTime(), ref.getPrice(), link));
	    	}
	    	return list;
	    }
	    
	    
	    @GET
	    @Produces(MediaType.TEXT_HTML)
	    public Viewable getAllView(){
	        List<EQReference> EQReferences = dao.getEQ();
	        Map<String, Object> equities = ViewModelBuilder.init().add("EQUITIES", EQReferences).build();
	        return new Viewable("/EQ.mustache", equities);
	    }
	    @Path("/{EPIC}")
	    @GET
	    @Produces(MediaType.TEXT_HTML)
	    public Viewable getSpecificView(@PathParam("EPIC")String epic){
	    	List<EQReference> EQReferences = dao.getSpecificEQ(epic);
	    	for(EQReference ref : EQReferences){
	    		System.out.println(ref);
	    		
	    	}
	    	System.out.println(EQReferences.size());
	    	Map<String, Object> equities = ViewModelBuilder.init().add("EQUITIES", EQReferences).build();
	        return new Viewable("/EQ.mustache", equities);
	    }
	    
	    public List<EQReference> getallEquities(){
	    	return dao.getEQ();
	    }
	    
	    public List<EQReference> getSpecificEQ(String epic){
	    	return dao.getSpecificEQ(epic);
	    }
	    



	
}
