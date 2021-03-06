package hello;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mindspark.torjans.dto.BookFlightRequest;
import com.mindspark.torjans.dto.FlightSearchRequest;
import com.mindspark.torjans.handler.BookFlightHandler;
import com.mindspark.torjans.handler.SearchFlightHandler;
import com.mindspark.torjans.mapper.RequestMapper;


@RestController
public class FlightServiceController {

    private static final String template = "Welcome to ChatBot Service";
    
    private static final Logger logger = LoggerFactory.getLogger(FlightServiceController.class);
    
    private SearchFlightHandler searchFlightHandler;
    private BookFlightHandler bookFlightHandler;
    
    //private final AtomicLong counter = new AtomicLong();

    @RequestMapping(method = RequestMethod.POST, value = "/SearchFlight")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response SearchFlight(InputStream incomingData) throws JSONException{
    	logger.info("Entering searchFlight method");
    	searchFlightHandler = new SearchFlightHandler();
    	bookFlightHandler = new BookFlightHandler();
    	List<Map<String, String>> searchFlightsData = new ArrayList<Map<String,String>>();
    	Map<String, String> bookResponseMap = new HashMap<String, String>();
    	StringBuilder crunchifyBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				crunchifyBuilder.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		//System.out.println("Data Received: " + crunchifyBuilder.toString());
		JSONObject jsonObject = new JSONObject(crunchifyBuilder.toString());
		JSONObject object = (JSONObject)jsonObject.get("result");
		String action = (String) object.get("action");
		JSONObject params = (JSONObject) object.get("parameters");
		String bookFlights = "Welcome to Flight Booking Service";
		if ("SearchFlight".equalsIgnoreCase(action)) {
			logger.info("Calling search operation");
			RequestMapper mapper = new RequestMapper();
			FlightSearchRequest searchRequest = mapper
					.constructSearchRequest(params);
			searchFlightsData = searchFlightHandler.searchFlights(searchRequest);
		} else if ("BookFlight".equalsIgnoreCase(action)) {
			logger.info("Calling book operation");
			RequestMapper mapper = new RequestMapper();
			JSONArray contexts = (JSONArray) object.get("contexts");
			JSONObject contextparams = (JSONObject) contexts.getJSONObject(0).get("parameters");
			try {
				BookFlightRequest bookFlightRequest = mapper.constructBookFlightRequest(params, contextparams);
				Map<String, String> bookFlightsResp = bookFlightHandler.bookFlights(bookFlightRequest);
				if(bookFlightsResp == null) {
					bookFlights = "Sorry! We are facing some issue while booking your ticket. "
							+ "Please try after some time";
				}
				searchFlightsData.add(bookFlightsResp);
			} catch (Exception e) {
				bookFlights = e.getMessage();
			}
		}
		logger.info("Exiting searchFlight method");
        return new Response(bookFlights, bookFlights, searchFlightsData, new ArrayList<String>(), 
        		"ChatBotService");
    }
    
    /*@RequestMapping(method = RequestMethod.POST, value = "/BookFlight")
    public Response bookFlight() {
        return new Response(template, template, ArrayList<Map<String,String>>(), new ArrayList<String>(), 
        		"ChatBotService");
    }*/
    
    @RequestMapping("/welcome")
    public Response welcome() {
    	logger.info("Welcome to Flight Book Service");
        return new Response("Welcome to Flights booking", "Welcome to Flights booking"
        		,new ArrayList<Map<String,String>>(), new ArrayList<String>(), "ChatBotService");
    }
    
}
