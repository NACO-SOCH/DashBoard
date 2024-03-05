package gov.naco.soch.dashboard.DTO;

import java.io.Serializable;
import java.util.List;


public class IndexSearchDTO  <T> implements Serializable{


	private List<T> searchResultList;
	private Long totalResultCount;
	private int pageSize = 10;
	private Long facilityId;

	private int pageCount = 0;

	public List <T> getSearchResultList() {
		return searchResultList;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setSearchResultList(List<T> searchResultList) {
		this.searchResultList = searchResultList;
	}

	public Long getTotalResultCount() {
		return totalResultCount;
	}

	public void setTotalResultCount(Long totalResultCount) {
		this.totalResultCount = totalResultCount;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public Long getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(Long facilityId) {
		this.facilityId = facilityId;
	}

	@Override
	public String toString() {
		return "IndexSearchResultDto{" +
				"searchResultList=" + searchResultList +
				", totalResultCount=" + totalResultCount +
				", pageSize=" + pageSize +
				", facilityId=" + facilityId +
				", pageCount=" + pageCount +
				'}';
	}
}
