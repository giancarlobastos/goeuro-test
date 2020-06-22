package com.goeuro.test;

import com.goeuro.test.domain.Location;
import com.goeuro.test.service.LocationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author Giancarlo Bastos Fernandes - giancarlo.bastos.fernandes@gmail.com
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
	 * @param args the query params.
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
