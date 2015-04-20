package com.goeuro.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.goeuro.test.domain.Location;
import com.goeuro.test.service.LocationService;

/**
 * Application that transforms query data into a csv output.<br>
 * <br>
 * The application command line tool that takes as an input parameter a string<br>
 * <br>
 * java -jar GoEuroTest.jar "CITY_NAME"<br>
 * <br>
 * The program takes this string and queries with it our Location JSON API: The app should use this API endpoint:<br>
 * <br>
 * http://api.goeuro.com/api/v2/position/suggest/en/CITY_NAME<br>
 * <br>
 * Where CITY_NAME is the string that the user has entered as a parameter when calling the tool, e.g.<br>
 * <br>
 * http://api.goeuro.com/api/v2/position/suggest/en/Berlin<br>
 * <br>
 * The API endpoint returns JSON documents.<br>
 * <br>
 * The endpoint always responds with a JSON array that contains JSON objects as elements. Each object, among other keys,
 * has a name and a geo_position key. The geo_position key is an object with latitude and longitude fields. If no
 * matches are found an empty JSON array is returned.<br>
 * <br>
 * The program should query the API with the user input and create a CSV file from it. The CSV file should have the
 * form: _id, name, type, latitude, longitude.
 * 
 * @author Giancarlo Bastos Fernandes - giancarlo.bastos.fernandes@gmail.com
 * @see https://github.com/goeuro/dev-test/blob/master/README.md
 */
@Configuration
@SpringBootApplication
public class Application {

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	/**
	 * The application command line tool that takes as an input parameter a string to query the location API.<br>
	 * <br>
	 * java -jar GoEuroTest.jar ["CITY_NAME"]+<br>
	 * 
	 * @param args the query parms.
	 * @throws Exception
	 */
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Application.class, args);
		LocationService locationService = context.getBean(LocationService.class);

		for (String arg : args) {
			Location[] locations = locationService.getLocations(arg);
			locationService.writeLocationCsv(System.out, locations);
		}
	}
}
