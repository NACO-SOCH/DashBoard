package gov.naco.soch.dashboard.DTO;



public class IndexBeneficiaryListDto {

	private Long id;
	private String uid;
	private String BeneficiaryName;
	private String genderName;
	private String artNumber;
	private Long totalIndexCount;
	private Long indexReferredToICTC;
	private Long indexAlreadyContacted;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getBeneficiaryName() {
		return BeneficiaryName;
	}

	public void setBeneficiaryName(String BeneficiaryName) {
		this.BeneficiaryName = BeneficiaryName;
	}

	public String getGenderName() {
		return genderName;
	}

	public void setGenderName(String genderName) {
		this.genderName = genderName;
	}

	public String getArtNumber() {
		return artNumber;
	}

	public void setArtNumber(String artNumber) {
		this.artNumber = artNumber;
	}

	public Long getTotalIndexCount() {
		return totalIndexCount;
	}

	public void setTotalIndexCount(Long totalIndexCount) {
		this.totalIndexCount = totalIndexCount;
	}

	public Long getIndexReferredToICTC() {
		return indexReferredToICTC;
	}

	public void setIndexReferredToICTC(Long indexReferredToICTC) {
		this.indexReferredToICTC = indexReferredToICTC;
	}

	public Long getIndexAlreadyContacted() {
		return indexAlreadyContacted;
	}

	public void setIndexAlreadyContacted(Long indexAlreadyContacted) {
		this.indexAlreadyContacted = indexAlreadyContacted;
	}

//	public IndexBeneficiaryListDto(Long id, String uid, String firstName, String middleName, String fullName,
//			String genderName, String artNumber, Long totalIndexCount, Long indexReferredToICTC,
//			Long indexAlreadyContacted) {
//		this.id = id;
//		this.uid = uid;
//		this.firstName = firstName;
//		this.middleName = middleName;
//		this.fullName = fullName;
//		this.genderName = genderName;
//		this.artNumber = artNumber;
//		this.totalIndexCount = totalIndexCount;
//		this.indexReferredToICTC = indexReferredToICTC;
//		this.indexAlreadyContacted = indexAlreadyContacted;
//	}

}
