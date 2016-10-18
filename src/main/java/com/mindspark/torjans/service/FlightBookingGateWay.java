/**
 * 
 */
package com.mindspark.torjans.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author M1026329
 *
 */
public class FlightBookingGateWay {
	public String airShopping(String airShoppingQuery) {
		String response = null;
	    String request = "http://mockairline.m3ptdp5x43.us-west-2.elasticbeanstalk.com/AirShop/airbooking";
	    URL url = null;
		HttpURLConnection urlConnection = null;
		try {
			url = new URL(request);
			String charset = "UTF-8";

			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestProperty("Accept-Charset", charset);
			urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			urlConnection.setRequestProperty("Content-Length",
					"" + Integer.toString(airShoppingQuery.getBytes().length));
			urlConnection.setDoOutput(true);
			urlConnection.setDoInput(true);

			OutputStream out = new BufferedOutputStream(
					urlConnection.getOutputStream());
			out.write(airShoppingQuery.getBytes());
			out.close();

			InputStream in = new BufferedInputStream(
					urlConnection.getInputStream());
			response = getResponse(in);
			in.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			urlConnection.disconnect();
		}
		return response;
	}
	
	private static String getResponse(InputStream is) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}
}
