package gov.naco.soch.dashboard.DTO;

import java.sql.Date;

public class BeneficiaryDTO {

	    private Long sskid;
	    private String firstName;
	    private String middleName;
	    private String lastName;
	    private String phoneNo;
	    private Date screeningDate;
	    private String status;
	    
	    
		public Long getSskid() {
			return sskid;
		}
		public void setSskid(Long sskid) {
			this.sskid = sskid;
		}
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getMiddleName() {
			return middleName;
		}
		public void setMiddleName(String middleName) {
			this.middleName = middleName;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public String getPhoneNo() {
			return phoneNo;
		}
		public void setPhoneNo(String phoneNo) {
			this.phoneNo = phoneNo;
		}
		public Date getScreeningDate() {
			return screeningDate;
		}
		public void setScreeningDate(Date screeningDate) {
			this.screeningDate = screeningDate;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
	    
	    

}
