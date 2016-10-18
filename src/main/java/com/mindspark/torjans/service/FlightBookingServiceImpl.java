/**
 * 
 */
package com.mindspark.torjans.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.mindspark.torjans.dto.BookFlightRequest;
import com.mindspark.torjans.dto.BookFlightResponse;
import com.mindspark.torjans.dto.FlightSearchRequest;
import com.mindspark.torjans.dto.FlightSearchResponse;
import com.mindspark.torjans.handler.SearchFlightHandler;

/**
 * @author M1026329
 *
 */
public class FlightBookingServiceImpl implements FlightBookingService {
	private static final Logger logger = LoggerFactory.getLogger(SearchFlightHandler.class);
	/* (non-Javadoc)
	 * @see com.mindspark.chatboot.FlightBookingService#searchForFlights(com.mindspark.chatboot.FlightSearchRequest)
	 */
	@Override
	public List<FlightSearchResponse> searchForFlights(final FlightSearchRequest flightSearchRequest) {
		logger.info("Inside FlightBookingServiceImpl :: searchForFlights");
		List<FlightSearchResponse> flightSearchResponses = null;
		String query = constructQuery(flightSearchRequest);
		FlightBookingGateWay flightBookingGateWay = new FlightBookingGateWay();
		String response = flightBookingGateWay.airShopping(query);
		if (response != null) {
			try {
				flightSearchResponses = new ArrayList<FlightSearchResponse>();
				DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			    InputSource is = new InputSource();
			    is.setCharacterStream(new StringReader(response));

			    Document doc = db.parse(is);
			    NodeList nodes = doc.getElementsByTagName("AirlineOffer");
			    for (int i = 0; i < nodes.getLength(); i++) {
			    	FlightSearchResponse flightSearchResponse = new FlightSearchResponse();
			        Element airlineOffer = (Element) nodes.item(i);
			        /*Element offerId = getChildElement(airlineOffer, "OfferID");
			        System.out.println("OfferID: " + getCharacterDataFromElement(offerId));
			        System.out.println("Owner: " + offerId.getAttribute("Owner"));
			        flightSearchResponse.setAirline(offerId.getAttribute("Owner"));*/
			        Element detailCurrencyPrice = getChildElement(airlineOffer, "DetailCurrencyPrice");
			        Element total = getChildElement(detailCurrencyPrice, "Total");
			        System.out.println("total: " + getCharacterDataFromElement(total));
			        System.out.println("currencyCode: " + total.getAttribute("Code"));
			        flightSearchResponse.setCost(total.getAttribute("Code") + getCharacterDataFromElement(total));
			        Element applicableFlight = getChildElement(airlineOffer, "ApplicableFlight");
			        Element originDestinationReferences = getChildElement(applicableFlight, "OriginDestinationReferences");
			        System.out.println("OriginDestinationReferences: " + getCharacterDataFromElement(originDestinationReferences));
			        String originDestinationReference = getCharacterDataFromElement(originDestinationReferences);
			        Element originDestination = getElement(doc, "OriginDestination", "OriginDestinationKey", originDestinationReference);
			        if (originDestination != null) {
			        	Element departureCode = getChildElement(originDestination, "DepartureCode");
			        	System.out.println("DepartureCode: " + getCharacterDataFromElement(departureCode));
			        	flightSearchResponse.setFromStation(getCharacterDataFromElement(departureCode));
			        	Element arrivalCode = getChildElement(originDestination, "ArrivalCode");
			        	System.out.println("ArrivalCode: " + getCharacterDataFromElement(arrivalCode));
			        	flightSearchResponse.setToStation(getCharacterDataFromElement(arrivalCode));
			        }
			        Element flightSegmentReferences = getChildElement(applicableFlight, "FlightSegmentReference");
			        System.out.println("FlightSegmentReference: " + flightSegmentReferences.getAttribute("ref"));
			        String flightSegmentReference = flightSegmentReferences.getAttribute("ref");
			        Element flightSegment = getElement(doc, "FlightSegment", "SegmentKey", flightSegmentReference);
			        if (flightSegment != null) {
			        	String departureDateTime = getDateTime(flightSegment, "Departure");
			        	System.out.println("DepartureDateTime: " + departureDateTime);
			        	flightSearchResponse.setDepartureDateTime(departureDateTime);
			        	String arrivalDateTime = getDateTime(flightSegment, "Arrival");
			        	System.out.println("ArrivalDateTime: " + arrivalDateTime);
			        	flightSearchResponse.setArrivalDateTime(arrivalDateTime);
			        	Element marketingCarrier = getChildElement(flightSegment, "MarketingCarrier");
			        	Element airline = getChildElement(marketingCarrier, "AirlineID");
			        	System.out.println("Airline: " + getCharacterDataFromElement(airline));
			        	flightSearchResponse.setAirline(getCharacterDataFromElement(airline));
			        	Element flightNumber = getChildElement(marketingCarrier, "FlightNumber");
			        	System.out.println("FlightNumber: " + getCharacterDataFromElement(flightNumber));
			        	flightSearchResponse.setFlightNo(getCharacterDataFromElement(flightNumber));
			        }
			        Element cabinDesignator = getChildElement(applicableFlight, "CabinDesignator");
			        System.out.println("CabinDesignator: " + getCharacterDataFromElement(cabinDesignator));
		        	flightSearchResponse.setCabin(getCharacterDataFromElement(cabinDesignator));
			        Element ptc = getChildElement(doc, "PTC");
			        System.out.println("Quantity: " + ptc.getAttribute("Quantity"));
			        flightSearchResponse.setQuantity(Integer.parseInt(ptc.getAttribute("Quantity")));
			        flightSearchResponses.add(flightSearchResponse);
			      }
			} catch (Exception ex) {
				logger.info("Exception in searchFlight");
				ex.printStackTrace();
			}
			
		}
		logger.info("Exiting FlightBookingServiceImpl :: searchForFlights");
		return flightSearchResponses;
	}
	
	private String getDateTime(Element flightSegment, String key) {
		Element departure = getChildElement(flightSegment, key);
		Element date = getChildElement(departure, "Date");
		Element time = getChildElement(departure, "Time");
		String dateTime = getCharacterDataFromElement(date) + getCharacterDataFromElement(time);
		return dateTime;
	}

	private Element getChildElement(Element element, String tagName) {
		NodeList nodeList = element.getElementsByTagName(tagName);
        Element childElement = (Element) nodeList.item(0);
		return childElement;
	}
	
	private Element getChildElement(Document document, String tagName) {
		NodeList nodeList = document.getElementsByTagName(tagName);
        Element childElement = (Element) nodeList.item(0);
		return childElement;
	}
	
	private Element getElement(Document document, String tagName, String key, String value) {
		NodeList nodeList = document.getElementsByTagName(tagName);
		 for (int i = 0; i < nodeList.getLength(); i++) {
			 Element element = (Element)nodeList.item(i);
			 if (element.getAttribute(key).equalsIgnoreCase(value)) {
				 return element; 
			 }
		 }
		return null;
	}
	
	private String getCharacterDataFromElement(Element e) {
	    Node child = e.getFirstChild();
	    if (child instanceof CharacterData) {
	      CharacterData cd = (CharacterData) child;
	      String data = cd.getData();
	      return data.trim();
	    }
	    return "";
	}

	private String constructQuery(final FlightSearchRequest flightSearchRequest) {
		String query = "<AirShoppingRQ xmlns=\"http://www.iata.org/IATA/EDIST\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.iata.org/IATA/EDIST AirShoppingRQ.xsd\""
				+ " EchoToken=\"8fdb1c621a7a4454aa3360556e7784d5\" TimeStamp=\"2016-18-12T12:39:00Z\" Version=\"15.2\" Target=\"Test\" PrimaryLangID=\"en\" AltLangID=\"en\" RetransmissionIndicator=\"false\""
				+ " AsynchronousAllowedInd=\"false\" TransactionIdentifier=\"a\" SequenceNmbr=\"201\"><PointOfSale><Location><CountryCode>IN</CountryCode><CityCode>DEL</CityCode></Location>"
				+ "<RequestTime Zone=\"EST\">2016-10-18T07:38:00</RequestTime><TouchPoint><Device><Code>2</Code><Definition>Web Browser</Definition><Position><Latitude>38.89756</Latitude>"
				+ "<Longitude>-77.03650</Longitude><NAC>8KD7V PGGM0</NAC></Position></Device><Event><Code>9</Code><Definition>Shop</Definition></Event></TouchPoint></PointOfSale><Document>"
				+ "<Name>KRONOS NDC GATEWAY</Name><ReferenceVersion>1.0</ReferenceVersion></Document><Party><Sender><TravelAgencySender><Contacts><Contact><EmailContact><Address>book@trozentravel.com</Address>"
				+ "</EmailContact></Contact></Contacts><PseudoCity>A4A</PseudoCity><IATA_Number>98417900</IATA_Number><AgencyID Owner=\"TT\">TT</AgencyID><AgentUser><Name>ksmith</Name><AgentUserID>ksmith212</AgentUserID>"
				+ "<UserRole>Admin</UserRole></AgentUser></TravelAgencySender></Sender></Party><Travelers><Traveler><AnonymousTraveler><PTC Quantity=\"" + flightSearchRequest.getQuantity()
				+ "\">ADT</PTC></AnonymousTraveler></Traveler></Travelers><CoreQuery><OriginDestinations><OriginDestination><Departure><AirportCode>" + flightSearchRequest.getFromStation() 
				+ "</AirportCode><Date>" + flightSearchRequest.getDepartureDate() + "</Date></Departure><Arrival><AirportCode>" + flightSearchRequest.getToStation() + "</AirportCode></Arrival></OriginDestination>"
				+ "</OriginDestinations></CoreQuery></AirShoppingRQ>";
		String preferences = null;
		if (flightSearchRequest.getAirline() != null || flightSearchRequest.getCabin() != null) {
			preferences = "<Preferences></Preferences>";
			if (flightSearchRequest.getAirline() != null) {
				String airlinePreference = "<Preference><AirlinePreferences><Airline><AirlineID>"
					+ flightSearchRequest.getAirline() + "</AirlineID></Airline></AirlinePreferences></Preference>";
				preferences = preferences.replaceAll("</Preferences>", airlinePreference + "</Preferences>");
			}
			if (flightSearchRequest.getCabin() != null) {
				String cabinPreference = "<Preference><CabinPreferences><CabinType><Code>"
					+ flightSearchRequest.getCabin() + "</Code></CabinType></CabinPreferences></Preference>";
				preferences = preferences.replaceAll("</Preferences>", cabinPreference + "</Preferences>");
			}
		}
		if (preferences != null) {
			query = query.replaceAll("</AirShoppingRQ>", preferences + "</AirShoppingRQ>");
		}
		return query;
	}

	@Override
	public BookFlightResponse bookFlight(BookFlightRequest bookFlightRequest) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
