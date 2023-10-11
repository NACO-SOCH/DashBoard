package gov.naco.soch.dashboard.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;

public class GeoMappingDTO {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String facilityName;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String facilityTypeName;
	
	private String geoLatitude;
	
	private String geoLongitude;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String stateName;
	
	
	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public String getFacilityTypeName() {
		return facilityTypeName;
	}

	public void setFacilityTypeName(String facilityTypeName) {
		this.facilityTypeName = facilityTypeName;
	}

	public String getGeoLatitude() {
		return geoLatitude;
	}

	public void setGeoLatitude(String geoLatitude) {
		this.geoLatitude = geoLatitude;
	}

	public String getGeoLongitude() {
		return geoLongitude;
	}

	public void setGeoLongitude(String geoLongitude) {
		this.geoLongitude = geoLongitude;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
}
