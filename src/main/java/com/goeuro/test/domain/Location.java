package com.goeuro.test.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * Represents a location that could be a city, an airport, etc.
 *
 * @author Giancarlo Bastos Fernandes - giancarlo.bastos.fernandes@gmail.com
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location implements Serializable {

	private static final long serialVersionUID = -3901941611282679309L;

	private Long positionId;

	private String displayName;

	private String defaultName;

	private String type;

	private Double latitude;

	private Double longitude;

	private Boolean inEurope;

	private String countryCode;

	private Long distanceToCityCenterInMeters;
}
