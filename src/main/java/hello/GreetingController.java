package hello;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

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
public class GreetingController {

    private static final String template = "Welcome to ChatBot Service";
    
    private static final Logger logger = LoggerFactory.getLogger(GreetingController.class);
    
    private SearchFlightHandler searchFlightHandler;
    private BookFlightHandler bookFlightHandler;
    
    //private final AtomicLong counter = new AtomicLong();

    @RequestMapping(method = RequestMethod.POST, value = "/SearchFlight")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response SearchFlight(InputStream incomingData) throws JSONException{
    	searchFlightHandler = new SearchFlightHandler();
    	bookFlightHandler = new BookFlightHandler();
    	List<Map<String, String>> searchFlightsData = new ArrayList<Map<String,String>>();
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
		String bookFlights = "";
		if ("SearchFlight".equalsIgnoreCase(action)) {
			RequestMapper mapper = new RequestMapper();
			FlightSearchRequest searchRequest = mapper
					.constructSearchRequest(params);
			searchFlightsData = searchFlightHandler.searchFlights(searchRequest);
		} else if ("BookFlight".equalsIgnoreCase(action)) {
			RequestMapper mapper = new RequestMapper();
			BookFlightRequest bookFlightRequest = mapper.constructBookFlightRequest(params);
			bookFlights = bookFlightHandler.bookFlights(bookFlightRequest);
		}
		
        return new Response(template, bookFlights, searchFlightsData, new ArrayList<String>(), 
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
