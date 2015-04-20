package com.goeuro.test.wrapper;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.goeuro.test.domain.Location;

/**
 * A wrapper for locations.
 * 
 * @author Giancarlo Bastos Fernandes - giancarlo.bastos.fernandes@gmail.com
 */
@JsonPropertyOrder({ "id", "name", "type", "latitude", "longitude" })
public class LocationWrapper implements Serializable {

	private static final long serialVersionUID = 991629356870781581L;

	private Location location;

	public LocationWrapper(Location location) {
		this.location = location;
	}

	@JsonProperty("_id")
	public Long getId() {
		return location.getId();
	}

	public String getName() {
		return location.getName();
	}

	public String getType() {
		return location.getType();
	}

	public Double getLatitude() {
		return location.getGeoPosition().getLatitude();
	}

	public Double getLongitude() {
		return location.getGeoPosition().getLongitude();
	}
}
