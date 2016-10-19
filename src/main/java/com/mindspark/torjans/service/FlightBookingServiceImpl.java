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

/**
 * @author M1026329
 *
 */
public class FlightBookingServiceImpl implements FlightBookingService {
	
	private static final Logger logger = LoggerFactory.getLogger(FlightBookingServiceImpl.class);

	/* (non-Javadoc)
	 * @see com.mindspark.chatboot.FlightBookingService#searchForFlights(com.mindspark.chatboot.FlightSearchRequest)
	 */
	@Override
	public List<FlightSearchResponse> searchForFlights(final FlightSearchRequest flightSearchRequest) {
		List<FlightSearchResponse> flightSearchResponses = null;
		String query = constructAirShoppingQuery(flightSearchRequest);
		logger.info("sent request :" + query);
		FlightBookingGateWay flightBookingGateWay = new FlightBookingGateWay();
		String response = flightBookingGateWay.airShopping(query);
		logger.info("received response :" + query);
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
			        Element detailCurrencyPrice = getChildElement(airlineOffer, "DetailCurrencyPrice");
			        Element total = getChildElement(detailCurrencyPrice, "Total");
			        flightSearchResponse.setCost(total.getAttribute("Code") + getCharacterDataFromElement(total));
			        Element applicableFlight = getChildElement(airlineOffer, "ApplicableFlight");
			        Element originDestinationReferences = getChildElement(applicableFlight, "OriginDestinationReferences");
			        String originDestinationReference = getCharacterDataFromElement(originDestinationReferences);
			        Element originDestination = getElement(doc, "OriginDestination", "OriginDestinationKey", originDestinationReference);
			        if (originDestination != null) {
			        	Element departureCode = getChildElement(originDestination, "DepartureCode");
			        	flightSearchResponse.setFromStation(getCharacterDataFromElement(departureCode));
			        	Element arrivalCode = getChildElement(originDestination, "ArrivalCode");
			        	flightSearchResponse.setToStation(getCharacterDataFromElement(arrivalCode));
			        }
			        Element flightSegmentReferences = getChildElement(applicableFlight, "FlightSegmentReference");
			        String flightSegmentReference = flightSegmentReferences.getAttribute("ref");
			        Element flightSegment = getElement(doc, "FlightSegment", "SegmentKey", flightSegmentReference);
			        if (flightSegment != null) {
			        	String departureDateTime = getDateTime(flightSegment, "Departure");
			        	flightSearchResponse.setDepartureDateTime(departureDateTime);
			        	String arrivalDateTime = getDateTime(flightSegment, "Arrival");
			        	flightSearchResponse.setArrivalDateTime(arrivalDateTime);
			        	Element marketingCarrier = getChildElement(flightSegment, "MarketingCarrier");
			        	Element airline = getChildElement(marketingCarrier, "AirlineID");
			        	flightSearchResponse.setAirline(getCharacterDataFromElement(airline));
			        	Element flightNumber = getChildElement(marketingCarrier, "FlightNumber");
			        	flightSearchResponse.setFlightNo(getCharacterDataFromElement(flightNumber));
			        }
			        Element cabinDesignator = getChildElement(applicableFlight, "CabinDesignator");
		        	flightSearchResponse.setCabin(getCharacterDataFromElement(cabinDesignator));
			        Element ptc = getChildElement(doc, "PTC");
			        flightSearchResponse.setQuantity(Integer.parseInt(ptc.getAttribute("Quantity")));
			        flightSearchResponses.add(flightSearchResponse);
			      }
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
		}
		return flightSearchResponses;
	}
	
	@Override
	public BookFlightResponse bookFlight(final BookFlightRequest bookFlightRequest) {
		String query = constructOrderCreateQuery(bookFlightRequest);
		logger.info("Sent request:");
		logger.info(query);
		FlightBookingGateWay flightBookingGateWay = new FlightBookingGateWay();
		String response = flightBookingGateWay.airShopping(query);
		logger.info("Received response:");
		logger.info(response);
		if (response != null) {
			BookFlightResponse bookFlightResponse = new BookFlightResponse();
			try {
				DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			    InputSource is = new InputSource();
			    is.setCharacterStream(new StringReader(response));
			    Document doc = db.parse(is);
			    Element ptc = getChildElement(doc, "PTC");
			    bookFlightResponse.setQuantity(Integer.parseInt(ptc.getAttribute("Quantity")));
			    Element surname = getChildElement(doc, "Surname");
			    bookFlightResponse.setPaxSurname(getCharacterDataFromElement(surname));
			    Element bookingReference = getChildElement(doc, "BookingReference");
			    Element bookingReferenceId = getChildElement(bookingReference, "ID");
			    bookFlightResponse.setBookingReference(getCharacterDataFromElement(bookingReferenceId));
			    Element total = getChildElement(doc, "Total");
			    bookFlightResponse.setCost(total.getAttribute("Code") + getCharacterDataFromElement(total));
			    Element flightSegment = getChildElement(doc, "FlightSegment");
			    String deptStation = getStation(flightSegment, "Departure");
			    bookFlightResponse.setDeptStation(deptStation);
			    String arrivalStation = getStation(flightSegment, "Arrival");
			    bookFlightResponse.setArrivalStation(arrivalStation);
			    String departureDateTime = getDateTime(flightSegment, "Departure");
			    bookFlightResponse.setDepartureDateTime(departureDateTime);
			    String arrivalDateTime = getDateTime(flightSegment, "Arrival");
			    bookFlightResponse.setArrivalDateTime(arrivalDateTime);
			    Element marketingCarrier = getChildElement(flightSegment, "MarketingCarrier");
			    Element airline = getChildElement(marketingCarrier, "AirlineID");
			    bookFlightResponse.setAirline(getCharacterDataFromElement(airline));
			    Element flightNumber = getChildElement(marketingCarrier, "FlightNumber");
			    bookFlightResponse.setFlightNo(getCharacterDataFromElement(flightNumber));
			} catch (Exception ex) {
				logger.info("Exception in calling book flight service");
				ex.printStackTrace();
			}
			return bookFlightResponse;
		}
		return null;
	}
	
	private String getDateTime(Element flightSegment, String key) {
		Element departure = getChildElement(flightSegment, key);
		Element date = getChildElement(departure, "Date");
		Element time = getChildElement(departure, "Time");
		String dateTime = getCharacterDataFromElement(date) + getCharacterDataFromElement(time);
		return dateTime;
	}
	
	private String getStation(Element flightSegment, String key) {
		Element departure = getChildElement(flightSegment, key);
		Element airport = getChildElement(departure, "AirportCode");
		return getCharacterDataFromElement(airport);
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

	private String constructAirShoppingQuery(final FlightSearchRequest flightSearchRequest) {
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
	
	private String constructOrderCreateQuery(final BookFlightRequest bookFlightRequest) {
		String query = "<OrderCreateRQ xmlns=\"http://www.iata.org/IATA/EDIST\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" Version=\"15.2\" xsi:schemaLocation=\"http://www.iata.org/IATA/EDIST OrderCreateRQ.xsd\">"
				+ "<PointOfSale><Location><CountryCode>IN</CountryCode><CityCode>DEL</CityCode></Location>"
				+ "<RequestTime Zone=\"EST\">2016-10-18T07:38:00</RequestTime><TouchPoint><Device><Code>2</Code><Definition>Web Browser</Definition><Position><Latitude>38.89756</Latitude>"
				+ "<Longitude>-77.03650</Longitude><NAC>8KD7V PGGM0</NAC></Position></Device><Event><Code>9</Code><Definition>Shop</Definition></Event></TouchPoint></PointOfSale><Document>"
				+ "<Name>KRONOS NDC GATEWAY</Name><ReferenceVersion>1.0</ReferenceVersion></Document><Party><Sender><TravelAgencySender><Contacts><Contact><EmailContact><Address>book@trozentravel.com</Address>"
				+ "</EmailContact></Contact></Contacts><PseudoCity>A4A</PseudoCity><IATA_Number>98417900</IATA_Number><AgencyID Owner=\"TT\">TT</AgencyID><AgentUser><Name>ksmith</Name><AgentUserID>ksmith212</AgentUserID>"
				+ "<UserRole>Admin</UserRole></AgentUser></TravelAgencySender></Sender></Party><Query><Passengers><Passenger ObjectKey=\"PAX1\"><PTC Quantity=\"" + bookFlightRequest.getQuantity() +"\">ADT</PTC><Name><Surname>"
				+ bookFlightRequest.getPaxSurname() + "</Surname></Name></Passenger></Passengers><OrderItems><ShoppingResponse><Owner>" + bookFlightRequest.getAirline()
				+ "</Owner><ResponseID>RE211db390f52c453b982d0889f7e71551</ResponseID><Offers><Offer><OfferID Owner=\"" + bookFlightRequest.getAirline() + "\">1</OfferID><OfferItems><OfferItem><OfferItemID Owner=\""
				+ bookFlightRequest.getAirline() +"\">1#M#110013557#210013557</OfferItemID><Passengers><PassengerReference>PAX1</PassengerReference></Passengers></OfferItem></OfferItems></Offer></Offers></ShoppingResponse></OrderItems>"
				+ "<DataLists><FlightSegmentList><FlightSegment SegmentKey=\"SEG1\"><Departure><AirportCode>" + bookFlightRequest.getDeptStation() + "</AirportCode><Date>" + bookFlightRequest.getDeptDate() +"</Date>"
			    + "<Time>00:00:00</Time></Departure><Arrival><AirportCode>" + bookFlightRequest.getArrivalStation() + "</AirportCode><Date>"
			    + bookFlightRequest.getArrDate() + "</Date><Time>00:00:00</Time></Arrival><MarketingCarrier><AirlineID>" + bookFlightRequest.getAirline()
			    + "</AirlineID><FlightNumber>" + bookFlightRequest.getFlightNo() + "</FlightNumber></MarketingCarrier><OperatingCarrier><AirlineID>" + bookFlightRequest.getAirline()
			    + "</AirlineID><FlightNumber>" + bookFlightRequest.getFlightNo() + "</FlightNumber></OperatingCarrier></FlightSegment></FlightSegmentList><FlightList><Flight FlightKey=\"FL1\"><SegmentReferences>SEG1"
			    + "</SegmentReferences></Flight></FlightList><OriginDestinationList><OriginDestination OriginDestinationKey=\"OD1\"><DepartureCode>" + bookFlightRequest.getDeptStation()
			    + "</DepartureCode><ArrivalCode>BLR</ArrivalCode><FlightReferences>FL1</FlightReferences></OriginDestination></OriginDestinationList></DataLists></Query></OrderCreateRQ>";
		return query;
		
	}
	
}
