package gov.naco.soch.dashboard.DTO;

import java.time.LocalDate;

public class IndexReferralDTO {

	public Long sskid;
    
    private LocalDate ScreeningDate;
    
    private Long ScreeningResult;
    
    private Long ScreeningStatus;
    
    private Long OutReferralTo;
    
    private String Remarks;
    
    
    
    

    public Long getSskid() {
        return sskid;
    }

    public LocalDate getScreeningDate() {
        return ScreeningDate;
    }

    public Long getScreeningResult() {
        return ScreeningResult;
    }

    public Long getOutReferralTo() {
        return OutReferralTo;
    }

    public void setSskid(Long sskid) {
        this.sskid = sskid;
    }

    public void setScreeningDate(LocalDate screeningDate) {
        ScreeningDate = screeningDate;
    }

    public void setScreeningResult(Long screeningResult) {
        ScreeningResult = screeningResult;
    }

    public void setOutReferralTo(Long outReferralTo) {
        OutReferralTo = outReferralTo;
    }

	public Long getScreeningStatus() {
		return ScreeningStatus;
	}

	public void setScreeningStatus(Long screeningStatus) {
		ScreeningStatus = screeningStatus;
	}

	public String getRemarks() {
		return Remarks;
	}

	public void setRemarks(String remarks) {
		Remarks = remarks;
	}
    
    
}
