package com.smashup.util;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.jayway.jsonpath.JsonPath;

public class NielsenAPIUtil implements Configuration {
	
	 private  Client  nielsenRestclient  = null;
	 private WebTarget target;
	 public String getStoreForSecondaryThemesProductInTheLocation(String upc, float latitude, float longitude){
		 //initialize REST end point 
		 nielsenRestclient= ClientBuilder.newClient();
		 String t = String.format( "%s%s?product_id=%s&lat=%f&long=%f&apikey=%s",BASEHACKATHLONHOST,"StoreAvailability/v1" ,upc,latitude,longitude, APIKEY );
		 System.out.println("before calling " + t);
		 target = nielsenRestclient.target(t);
		
		// target = nielsenRestclient.target("https://nielsen.api.tibco.com/StoreAvailability/v1?product_id=0016000275270&lat=29.7904&long=-95.1624&apikey=1504-71e5a462-f1d2-4f8c-a518-0a3bd9ba1916");
		 Invocation.Builder invocationBuilder =
	                target.request(MediaType.APPLICATION_JSON);
	        Response response = invocationBuilder.get();
	        String jsonResponse= response.readEntity(String.class);
		 System.out.println(jsonResponse);
		 return jsonResponse;
	 }
	 
	 public String searchProducts (String productName) {
		//initialize REST end point 
		 nielsenRestclient= ClientBuilder.newClient();
		 String t = String.format( "%s%s?search=%s&apikey=%s",BASEHACKATHLONHOST,"Products/v1/" ,productName,  APIKEY );
		 //System.out.println("before calling " + t);
		 target = nielsenRestclient.target(t);
		
		// target = nielsenRestclient.target("https://nielsen.api.tibco.com/StoreAvailability/v1?product_id=0016000275270&lat=29.7904&long=-95.1624&apikey=1504-71e5a462-f1d2-4f8c-a518-0a3bd9ba1916");
		 Invocation.Builder invocationBuilder =
	                target.request(MediaType.APPLICATION_JSON);
	        Response response = invocationBuilder.get();
	        String jsonResponse= response.readEntity(String.class);
		 System.out.println(jsonResponse);
		 return jsonResponse;
	 }
	 
	 // Get all the stores frequented by dominant demography 
	 public String getStoresBydemography(String dominatrace, String dominatageGroup, int pageNo, int pageSize){
		 nielsenRestclient= ClientBuilder.newClient();
		 String t = null;
		 if ( pageNo == -1 || pageSize == -1){
			  t = String.format( "%s%s?agegroup=%s&race=%s&apikey=%s",BASEHACKATHLONHOST,"Stores/v1/demographic/" ,dominatageGroup, dominatrace, APIKEY );	 
		 }else {
			 t = String.format( "%s%s?agegroup=%s&race=%s&apikey=%s&pagesize=%d&pageno=%d",BASEHACKATHLONHOST,"Stores/v1/demographic/" ,dominatageGroup, dominatrace, APIKEY, pageSize, pageNo );
		 }
		 
		 //System.out.println("before calling " + t);
		 target = nielsenRestclient.target(t);
		
		// target = nielsenRestclient.target("https://nielsen.api.tibco.com/StoreAvailability/v1?product_id=0016000275270&lat=29.7904&long=-95.1624&apikey=1504-71e5a462-f1d2-4f8c-a518-0a3bd9ba1916");
		 Invocation.Builder invocationBuilder =
	                target.request(MediaType.APPLICATION_JSON);
	        Response response = invocationBuilder.get();
	        String jsonResponse= response.readEntity(String.class);
		 //System.out.println(jsonResponse);
		 return jsonResponse;
	 }
	 
	 public Map<String, Map<String, Integer>> getAllStoresBydemography(String dominatrace, String dominatageGroup){
		 String response = getStoresBydemography(dominatrace,dominatageGroup, 1,25);
		 Map<String, Map<String, Integer>> storeByRace = new HashMap<String, Map<String, Integer>>(); 
		 Map<String, Integer> hm  = new HashMap<String, Integer>( );
		 List<String> pageSize = JsonPath.read(response, "$..TotalPages");
		 String size = pageSize.get(0);
		 int pages  =  Integer.parseInt(size);
		 List<String> storename = JsonPath.read(response, "$..StoreName");
		 manageCount(hm,storename);
		 storeByRace.put(dominatrace+dominatageGroup,hm);
		 String r;  
		 for (int page =2; page<= pages  ; page++)
		 {
			  r = getStoresBydemography(dominatrace,dominatageGroup, page,25);
			  storename = JsonPath.read(r, "$..StoreName");
			  manageCount(hm,storename);
			  storeByRace.put(dominatrace+dominatageGroup,hm);
			  
		 };
				
		 
		 
		 
		 return storeByRace;
	 }
	 
	 
	 private void manageCount(Map<String, Integer> hm , List<String> storename){
		 for (String name : storename){
			 if (hm.containsKey(name) ){
				 int count = (Integer)hm.get(name).intValue();
				 hm.put(name, ++count);
			 }else {
				 hm.put(name, Integer.valueOf(1));
			 }
				 
		 }
		 return;
	 }
	 
	 
}


