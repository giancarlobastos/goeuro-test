package com.goeuro.test.service;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.text.MessageFormat;

import org.hamcrest.beans.SamePropertyValuesAs;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;

import com.goeuro.test.Application;
import com.goeuro.test.domain.GeoPosition;
import com.goeuro.test.domain.Location;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class LocationServiceTest {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private LocationService locationService;

	@Value("${serviceUrl}")
	private String serviceUrl;

	@Test
	public void getLocationsTest() {
		MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);

		String berlinRequestUrl = MessageFormat.format(serviceUrl, "Berlin");
		server.expect(MockRestRequestMatchers.requestTo(berlinRequestUrl))
				.andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
				.andRespond(
						MockRestResponseCreators.withSuccess(new ClassPathResource("Berlin.json"),
								MediaType.APPLICATION_JSON));

		Location expectedBerlinLocation = getBerlinLocation();

		Location[] locations = locationService.getLocations("Berlin");
		Location berlinLocation = locations[0];

		// compare geoPosition first to avoid deep compare with SamePropertyValuesAs.<T>samePropertyValuesAs()
		Assert.assertThat(expectedBerlinLocation.getGeoPosition(),
				SamePropertyValuesAs.samePropertyValuesAs(berlinLocation.getGeoPosition()));
		expectedBerlinLocation.setGeoPosition(null);
		berlinLocation.setGeoPosition(null);

		Assert.assertThat(expectedBerlinLocation, SamePropertyValuesAs.samePropertyValuesAs(berlinLocation));
	}

	@Test
	public void writeLocationCsv() throws Exception {
		Location[] locations = { getBerlinLocation() };
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		locationService.writeLocationCsv(output, locations);

		assertEquals("376217,Berlin,location,52.52437,13.41053\n", output.toString());
	}

	private Location getBerlinLocation() {
		Location location = new Location();
		location.setId(376217L);
		location.setKey(null);
		location.setName("Berlin");
		location.setFullName("Berlin, Germany");
		location.setIataAirportCode(null);
		location.setType("location");
		location.setCountry("Germany");
		location.setLocationId(8384L);
		location.setInEurope(true);
		location.setCountryCode("DE");
		location.setCoreCountry(true);
		location.setDistance(null);

		GeoPosition geoPosition = new GeoPosition();
		geoPosition.setLatitude(52.52437);
		geoPosition.setLongitude(13.41053);
		location.setGeoPosition(geoPosition);
		return location;
	}
}
