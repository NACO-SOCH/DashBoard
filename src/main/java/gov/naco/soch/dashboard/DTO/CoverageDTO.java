package gov.naco.soch.dashboard.DTO;


public class CoverageDTO {


	int year;
	int month;
	private String coverage1;
	private String coverage2;
	private String coverage3;
	
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}
	
	public String getCoverage1() {
		return coverage1;
	}

	public void setCoverage1(String coverage1) {
		this.coverage1 = coverage1;
	}

	public String getCoverage2() {
		return coverage2;
	}

	public void setCoverage2(String coverage2) {
		this.coverage2 = coverage2;
	}

	public String getCoverage3() {
		return coverage3;
	}
	
	public void setCoverage3(String coverage3) {
		this.coverage3 = coverage3;
	}

}
