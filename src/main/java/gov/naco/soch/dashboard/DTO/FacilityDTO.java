package gov.naco.soch.dashboard.DTO;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;


public class FacilityDTO {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer facilityTypeId;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer count;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String name;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Date dateTime;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer activeUsers;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer loginCount;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer stateId;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer id;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long downloadCount;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String stateName;
	
	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDateTime() {
		return dateTime;
	}
	
	public Long getDownloadCount() {
		return downloadCount;
	}

	public void setDownloadCount(Long downloadCount) {
		this.downloadCount = downloadCount;
	}
	
	public Integer getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public Integer getFacilityTypeId() {
		return facilityTypeId;
	}

	public void setFacilityTypeId(Integer facilityTypeId) {
		this.facilityTypeId = facilityTypeId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getActiveUsers() {
		return activeUsers;
	}

	public void setActiveUsers(Integer activeUsers) {
		this.activeUsers = activeUsers;
	}
	 

}
