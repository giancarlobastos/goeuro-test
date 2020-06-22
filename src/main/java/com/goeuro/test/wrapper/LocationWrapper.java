package com.goeuro.test.wrapper;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.goeuro.test.domain.Location;

import java.io.Serializable;

/**
 * A wrapper for locations.
 *
 * @author Giancarlo Bastos Fernandes - giancarlo.bastos.fernandes@gmail.com
 */
@JsonPropertyOrder({"id", "name", "type", "latitude", "longitude"})
public class LocationWrapper implements Serializable {

	private static final long serialVersionUID = 991629356870781581L;

	private Location location;

	public LocationWrapper(Location location) {
		this.location = location;
	}

	public Long getId() {
		return location.getPositionId();
	}

	public String getName() {
		return location.getDisplayName();
	}

	public String getType() {
		return location.getType();
	}

	public Double getLatitude() {
		return location.getLatitude();
	}

	public Double getLongitude() {
		return location.getLongitude();
	}
}
