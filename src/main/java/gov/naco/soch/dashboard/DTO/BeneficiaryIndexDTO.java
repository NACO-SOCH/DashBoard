package gov.naco.soch.dashboard.DTO;

import java.time.LocalDate;

public class BeneficiaryIndexDTO {

	    private LocalDate screeningDate;
	    private String status;
	    private String result;
	    private String outReferralTo;

	   
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
}
