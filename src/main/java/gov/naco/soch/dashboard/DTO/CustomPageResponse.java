package gov.naco.soch.dashboard.DTO;

import java.util.List;

public class CustomPageResponse {

	 private long totalRecords;
     private int totalPages;
     private int currentPage;
     private List<IndexBeneficiaryListDto> data;
     
     public long getTotalRecords() {
         return totalRecords;
     }
     public void setTotalRecords(long totalRecords) {
         this.totalRecords = totalRecords;
     }
     public int getTotalPages() {
         return totalPages;
     }
     public void setTotalPages(int totalPages) {
         this.totalPages = totalPages;
     }
     public int getCurrentPage() {
         return currentPage;
     }
     public void setCurrentPage(int currentPage) {
         this.currentPage = currentPage;
     }
     public List<IndexBeneficiaryListDto> getData() {
         return data;
     }
     public void setData(List<IndexBeneficiaryListDto> data) {
         this.data = data;
     }
     
     public CustomPageResponse(long totalRecords, int totalPages, int currentPage, List<IndexBeneficiaryListDto> data) {
         this.totalRecords = totalRecords;
         this.totalPages = totalPages;
         this.currentPage = currentPage;
         this.data = data;
     }
}
