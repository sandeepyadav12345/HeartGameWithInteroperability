package com.perisic.sixeq.peripherals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;


import org.json.JSONException;
import org.json.JSONObject;
/**
 * A simple code for fetching coronavirus live update data from external webservice.
 * 
 * 
 *
 */
public class ApiFetch {

	/**
	 * Main method to initiate fetching json data from api 
	 * 
	 * @throws IOException JSONException
	 * 
	 */

  public static void main(String[] args) throws IOException, JSONException {
	  
	  /**
	   * here i am fetching the coronavirus live json data from rapiapi .
	   * 
	   * 
	   *
	   */
	  HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://covid-19-coronavirus-statistics.p.rapidapi.com/v1/stats?country=Nepal"))
				.header("x-rapidapi-host", "covid-19-coronavirus-statistics.p.rapidapi.com")
				.header("x-rapidapi-key", "3156a51162msh61ca384dd671c70p147f96jsn702d5d5c6902")
				.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<String> response = null;
		try {
			response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(response.body().toString());
   
  }
}
