package com.goeuro.test.service;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.goeuro.test.Application;
import com.goeuro.test.domain.GeoPosition;
import com.goeuro.test.domain.Location;
import com.goeuro.test.wrapper.LocationWrapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class CsvServiceTest {

	@Autowired
	private CsvService csvService;

	@Test
	public void writeCsvTest() throws Exception {
		List<LocationWrapper> locations = new LinkedList<LocationWrapper>();

		Location location = new Location();
		location.setId(123456L);
		location.setName("Barueri");
		location.setType("location");

		GeoPosition geoPosition = new GeoPosition();
		geoPosition.setLatitude(1.123);
		geoPosition.setLongitude(4.567);
		location.setGeoPosition(geoPosition);

		LocationWrapper wrapper = new LocationWrapper(location);
		locations.add(wrapper);

		ByteArrayOutputStream output = new ByteArrayOutputStream();

		csvService.writeCsv(output, locations, LocationWrapper.class);

		assertEquals("123456,Barueri,location,1.123,4.567\n", output.toString());
	}
}
