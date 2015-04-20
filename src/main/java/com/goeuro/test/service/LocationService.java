package com.goeuro.test.service;

import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.goeuro.test.domain.Location;
import com.goeuro.test.wrapper.LocationWrapper;

/**
 * Service responsible to query locations.
 * 
 * @author Giancarlo Bastos Fernandes - giancarlo.bastos.fernandes@gmail.com
 */
@Service
public class LocationService {

	private static Logger log = Logger.getLogger(LocationService.class);

	@Autowired
	private CsvService csvService;

	@Autowired
	private RestTemplate restTemplate;

	/* URL of the webservice */
	@Value("${serviceUrl}")
	private String serviceUrl;

	/**
	 * Get the locations related to the search term.<br>
	 * <br>
	 * The service always returns an array instance.
	 * 
	 * @param term The search term.
	 * 
	 * @return All locations related to the term.
	 */
	public Location[] getLocations(String term) {
		try {
			return restTemplate.getForObject(serviceUrl, Location[].class, term);
		}
		catch (RestClientException e) {
			log.error(e.getMessage(), e);
			return new Location[0];
		}
	}

	/**
	 * Write the CSV of the locations to the given stream.
	 * 
	 * @param stream The stream where the CSV will be written.
	 * @param locations The locations to be represented.
	 */
	public void writeLocationCsv(OutputStream stream, Location[] locations) {
		csvService.writeCsv(stream, getLocationWrappers(locations), LocationWrapper.class);
	}

	/**
	 * Converts an array of locations to a list of location wrappers.
	 * 
	 * @param locations The array of locations.
	 * @return A list of wrappers.
	 */
	private List<LocationWrapper> getLocationWrappers(Location[] locations) {
		List<LocationWrapper> wrappers = new LinkedList<LocationWrapper>();

		for (Location location : locations) {
			wrappers.add(new LocationWrapper(location));
		}

		return wrappers;
	}
}
