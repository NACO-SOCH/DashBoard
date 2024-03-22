package gov.naco.soch.dashboard.DTO;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;

public class BeneficiaryIndexDTO {

	    private LocalDate screeningDate;
	    private String status;
	    private String result;
	    private String outReferralTo;
	    
		@JsonInclude(JsonInclude.Include.NON_NULL)
	    private String remarks;
		@JsonInclude(JsonInclude.Include.NON_NULL)
	    private LocalDate createdOn;

	   
	    // Getters and Setters

	    public LocalDate getScreeningDate() {
	        return screeningDate;
	    }

	    public void setScreeningDate(LocalDate screeningDate) {
	        this.screeningDate = screeningDate;
	    }

	    public String getStatus() {
	        return status;
	    }

	    public void setStatus(String status) {
	        this.status = status;
	    }

	    public String getResult() {
	        return result;
	    }

	    public void setResult(String result) {
	        this.result = result;
	    }

	    public String getOutReferralTo() {
	        return outReferralTo;
	    }

	    public void setOutReferralTo(String outReferralTo) {
	        this.outReferralTo = outReferralTo;
	    }

		public String getRemarks() {
			return remarks;
		}

		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}

		public LocalDate getCreatedOn() {
			return createdOn;
		}

		public void setCreatedOn(LocalDate createdOn) {
			this.createdOn = createdOn;
		}
	    
	    
}
