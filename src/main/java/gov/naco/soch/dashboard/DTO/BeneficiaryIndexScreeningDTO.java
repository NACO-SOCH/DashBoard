package gov.naco.soch.dashboard.DTO;

import java.time.LocalDate;

public class BeneficiaryIndexScreeningDTO {

    private Long sskid;
    private LocalDate screeningDate;
    private Long hivScreeningResultId;
    private Long indexScreeningStatusId;
    private Long outReferralTo;
    private String remarks;
    private Long createdBy;
    private Long modifiedBy;

    
    public Long getSskid() {
        return sskid;
    }

    public void setSskid(Long sskid) {
        this.sskid = sskid;
    }

    public LocalDate getScreeningDate() {
        return screeningDate;
    }

    public void setScreeningDate(LocalDate screeningDate) {
        this.screeningDate = screeningDate;
    }

    public Long getHivScreeningResultId() {
        return hivScreeningResultId;
    }

    public void setHivScreeningResultId(Long hivScreeningResultId) {
        this.hivScreeningResultId = hivScreeningResultId;
    }

    public Long getIndexScreeningStatusId() {
        return indexScreeningStatusId;
    }

    public void setIndexScreeningStatusId(Long indexScreeningStatusId) {
        this.indexScreeningStatusId = indexScreeningStatusId;
    }

    public Long getOutReferralTo() {
        return outReferralTo;
    }

    public void setOutReferralTo(Long outReferralTo) {
        this.outReferralTo = outReferralTo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}

