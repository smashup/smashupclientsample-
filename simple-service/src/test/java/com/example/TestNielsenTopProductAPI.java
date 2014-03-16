package com.example;

import static org.junit.Assert.*;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import com.jayway.jsonpath.Filter;
import com.jayway.jsonpath.JsonPath;

public class TestNielsenTopProductAPI {
	private WebTarget target;
	 
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	 @Before
	    public void setUp() throws Exception {
	        // create the client
	        

	        // uncomment the following line if you want to enable
	        // support for JSON in the client (you also have to uncomment
	        // dependency on jersey-media-json module in pom.xml and Main.startServer())
	        // --
	        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

	        
	    }
	 
	// @Test
	    public void testGetIt() {
		 Client c = ClientBuilder.newClient();
		 target = c.target("https://nielsen.api.tibco.com:443/AdView/Product/v1?productcategory=MOISTURIZER&pageno=14&pagesize=10&apikey=1504-71e5a462-f1d2-4f8c-a518-0a3bd9ba1916");
	        Invocation.Builder invocationBuilder =
	                target.request(MediaType.APPLICATION_JSON);
	        Response response = invocationBuilder.get();
	        System.out.println(response.getStatus());
	        System.out.println(response.readEntity(String.class));
	        
	        
	        
	    }
	 
	 //@Test
	 public void callMarketShareProductCategory() {
		 Client c = ClientBuilder.newClient();
		 target = c.target("https://nielsen.api.tibco.com:443/RMS/v1/Metadata/?apiname=manufacturerDolShr&apikey=1504-71e5a462-f1d2-4f8c-a518-0a3bd9ba1916");
	     
		 Invocation.Builder invocationBuilder =
	                target.request(MediaType.APPLICATION_JSON);
	        Response response = invocationBuilder.get();
	        String jsonResponse= response.readEntity(String.class);
	        List<String> productCategory =  JsonPath.read(jsonResponse, "$..category.metadataValue");
	        System.out.println(productCategory.toString());
	 }
	 
	 @Test
	 public void storeAvailabilityForaProductInAGivenLocation() {
		 Client c = ClientBuilder.newClient();
		 target = c.target("https://nielsen.api.tibco.com:443/RMS/v1/Metadata/?apiname=manufacturerDolShr&apikey=1504-71e5a462-f1d2-4f8c-a518-0a3bd9ba1916");
	     
		 Invocation.Builder invocationBuilder =
	                target.request(MediaType.APPLICATION_JSON);
	        Response response = invocationBuilder.get();
	        String jsonResponse= response.readEntity(String.class);
	        List<String> productCategory =  JsonPath.read(jsonResponse, "$..category.metadataValue");
	        System.out.println(productCategory.toString());
	 }
	 
	 
	 
	 
	 public void insertintoClouddb() {
		    Client c = ClientBuilder.newClient();
		    target = c.target("");
	        Invocation.Builder invocationBuilder =
	                target.request(MediaType.APPLICATION_JSON);
	        Response response = invocationBuilder.get();
	        System.out.println(response.getStatus());
	        System.out.println(response.readEntity(String.class));
	        
	        
	        
	    }
	 
}
