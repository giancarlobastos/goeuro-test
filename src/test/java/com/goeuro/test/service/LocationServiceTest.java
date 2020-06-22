package com.goeuro.test.service;

import com.goeuro.test.Application;
import com.goeuro.test.domain.Location;
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

import java.io.ByteArrayOutputStream;
import java.text.MessageFormat;

import static org.junit.Assert.assertEquals;

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

		String berlinRequestUrl = MessageFormat.format(serviceUrl, "Maringa");
		server.expect(MockRestRequestMatchers.requestTo(berlinRequestUrl))
				.andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
				.andRespond(
						MockRestResponseCreators.withSuccess(new ClassPathResource("Maringa.json"),
								MediaType.APPLICATION_JSON));

		Location expectedMaringaLocation = getMaringaLocation();

		Location[] locations = locationService.getLocations("Maringa");
		Location maringaLocation = locations[0];
		Assert.assertThat(expectedMaringaLocation, SamePropertyValuesAs.samePropertyValuesAs(maringaLocation));
	}

	@Test
	public void writeLocationCsv() throws Exception {
		Location[] locations = {getMaringaLocation()};
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		locationService.writeLocationCsv(output, locations);

		assertEquals("371131,Maringá,location,-23.42528,-51.93861\n", output.toString());
	}

	private Location getMaringaLocation() {
		Location location = new Location();
		location.setPositionId(371131L);
		location.setDisplayName("Maringá");
		location.setDefaultName("Maringá");
		location.setType("location");
		location.setInEurope(false);
		location.setCountryCode("BR");
		location.setDistanceToCityCenterInMeters(0L);
		location.setLatitude(-23.42528);
		location.setLongitude(-51.93861);
		return location;
	}
}
