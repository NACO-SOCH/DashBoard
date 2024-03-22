package gov.naco.soch.dashboard.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import gov.naco.soch.dashboard.service.CriteriaService;
import gov.naco.soch.dashboard.service.ReportsService;

@RestController
@RequestMapping("/Reports")
@CrossOrigin(origins = {"http://localhost:4200","*"}, allowedHeaders = "*")

public class ReportsController {

	@Autowired
	ReportsService reportsService;
	@Autowired
	CriteriaService criteriaService;
	
	@GetMapping("/loginCount")
    public ResponseEntity<List<Object[]>> getloginCount(
        @RequestParam Integer stateId,
        @RequestParam String startDate,
        @RequestParam String endDate,
        @RequestParam Integer facilityId,
        @RequestParam Integer facilityTypeId,
        @RequestParam Integer DistrictId) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedStartDate = dateFormat.parse(startDate);
        Date parsedEndDate = dateFormat.parse(endDate);

        List<Object[]> allData = new ArrayList<>(reportsService.getLoginCount(stateId, parsedStartDate, parsedEndDate, facilityId, facilityTypeId, DistrictId));

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Records", String.valueOf(allData.size()));

        return ResponseEntity.ok()
                .headers(headers)
                .body(allData);
    }
	
	@GetMapping("/loginCountState")
    public ResponseEntity<List<Object[]>> getloginCountState(
        @RequestParam String startDate,
        @RequestParam String endDate) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedStartDate = dateFormat.parse(startDate);
        Date parsedEndDate = dateFormat.parse(endDate);

        List<Object[]> allData = new ArrayList<>(reportsService.getLoginCountState( parsedStartDate, parsedEndDate));

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Records", String.valueOf(allData.size()));

        return ResponseEntity.ok()
                .headers(headers)
                .body(allData);
    }
	

	@GetMapping("/ComprehensiveloginReport")
	public ResponseEntity<byte[]> getloginReport(
	    @RequestParam String startDate,
	    @RequestParam String endDate,
	    @RequestParam(defaultValue = "0") Integer page,
	    @RequestParam(defaultValue = "1000") Integer pageSize) throws ParseException, IOException {

	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date parsedStartDate = dateFormat.parse(startDate);
	    Date parsedEndDate = dateFormat.parse(endDate);

	    SimpleDateFormat currentDateFormatter = new SimpleDateFormat("dd-MMM-yyyy");
	    String currentDate = currentDateFormatter.format(new Date());

	    String reportPeriod = "Report Period : " + formatDate(parsedStartDate) + " To " + formatDate(parsedEndDate);
	    
	    List<Object[]> allData = (reportsService.getLoginReport(parsedStartDate, parsedEndDate))
	            .collect(Collectors.toList());

	    int totalRecords = allData.size();
	    int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

	    Stream<Object[]> chunkDataStream = allData.stream()
	            .skip(page * pageSize)
	            .limit(totalRecords);

	    HttpHeaders headers = new HttpHeaders();
	    headers.add("X-Total-Records", String.valueOf(totalRecords));
	    headers.add("X-Total-Pages", String.valueOf(totalPages));
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    headers.setContentDispositionFormData("attachment", "Comprehensive_Data_Report.xlsx");

	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	    try (InputStream templateStream = getClass().getResourceAsStream("/Comprehensive_Data_Report.xlsx");
		         Workbook workbook = WorkbookFactory.create(templateStream)) {

	        Sheet sheet = workbook.getSheet("Sheet");

	        int rowIdx = sheet.getLastRowNum() + 1;
	        CellStyle numericCellStyle = workbook.createCellStyle();
	        DataFormat dataFormat = workbook.createDataFormat();
	        numericCellStyle.setDataFormat(dataFormat.getFormat("#0")); // Set the desired numeric format

	        for (Object[] rowData : chunkDataStream.collect(Collectors.toList())) {
	            Row row = sheet.createRow(rowIdx++);
	            int cellIdx = 0;
	            for (Object cellData : rowData) {
	                Cell cell = row.createCell(cellIdx++);
	                if (cellData != null) {
	                    if (cellData instanceof Number) {
	                        // Set numeric cell value and apply numeric cell style
	                        cell.setCellValue(((Number) cellData).doubleValue());
	                        cell.setCellStyle(numericCellStyle);
	                    } else {
	                        // Set string cell value
	                        cell.setCellValue(cellData.toString());
	                    }
	                } else {
	                    // Handle the case where cellData is null
	                    cell.setCellValue(""); // or set a default value
	                }
	            }
	        }
	        Row paramsRow = sheet.createRow(rowIdx++ + 2);
	        paramsRow.createCell(0).setCellValue(reportPeriod);

	        Row currentDateRow = sheet.createRow(rowIdx++);
	        currentDateRow.createCell(0).setCellValue("Report Downloaded On : " + currentDate);
	        workbook.setForceFormulaRecalculation(true); // Enable automatic formula recalculation

	        workbook.write(outputStream);
	    }

	    return ResponseEntity.ok()
	            .headers(headers)
	            .body(outputStream.toByteArray());
	}
	
	@GetMapping("/ComprehensiveloginReport/Rolewise")
	public ResponseEntity<byte[]> getloginReportRole(
		@RequestParam(required = false) Integer stateId,
	    @RequestParam String startDate,
	    @RequestParam String endDate,
	    @RequestParam(defaultValue = "0") Integer page,
	    @RequestParam(defaultValue = "1000") Integer pageSize) throws ParseException, IOException {

	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date parsedStartDate = dateFormat.parse(startDate);
	    Date parsedEndDate = dateFormat.parse(endDate);
	    
	    SimpleDateFormat currentDateFormatter = new SimpleDateFormat("dd-MMM-yyyy");
	    String currentDate = currentDateFormatter.format(new Date());

	    String reportPeriod = "Report Period : " + formatDate(parsedStartDate) + " To " + formatDate(parsedEndDate);

	    List<Object[]> allData = (reportsService.getLoginReportRole(stateId, parsedStartDate, parsedEndDate))
	            .collect(Collectors.toList());

	    int totalRecords = allData.size();
	    int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

	    Stream<Object[]> chunkDataStream = allData.stream()
	            .skip(page * pageSize)
	            .limit(totalRecords);

	    HttpHeaders headers = new HttpHeaders();
	    headers.add("X-Total-Records", String.valueOf(totalRecords));
	    headers.add("X-Total-Pages", String.valueOf(totalPages));
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    headers.setContentDispositionFormData("attachment", "Comprehensive_Data_RoleWise.xlsx");

	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	    try (InputStream templateStream = getClass().getResourceAsStream("/Comprehensive_Data_RoleWise.xlsx");
	         Workbook workbook = WorkbookFactory.create(templateStream)) {

	        Sheet sheet = workbook.getSheet("Sheet");

	        int rowIdx = sheet.getLastRowNum() + 1;
	        CellStyle numericCellStyle = workbook.createCellStyle();
	        DataFormat dataFormat = workbook.createDataFormat();
	        numericCellStyle.setDataFormat(dataFormat.getFormat("#0")); // Set the desired numeric format

	        for (Object[] rowData : chunkDataStream.collect(Collectors.toList())) {
	            Row row = sheet.createRow(rowIdx++);
	            int cellIdx = 0;
	            for (Object cellData : rowData) {
	                Cell cell = row.createCell(cellIdx++);
	                if (cellData != null) {
	                    if (cellData instanceof Number) {
	                        // Set numeric cell value and apply numeric cell style
	                        cell.setCellValue(((Number) cellData).doubleValue());
	                        cell.setCellStyle(numericCellStyle);
	                    } else {
	                        // Set string cell value
	                        cell.setCellValue(cellData.toString());
	                    }
	                } else {
	                    // Handle the case where cellData is null
	                    cell.setCellValue(""); // or set a default value
	                }
	            }
	        }
	        
	        Row paramsRow = sheet.createRow(rowIdx++ + 2);
	        paramsRow.createCell(0).setCellValue(reportPeriod);

	        Row currentDateRow = sheet.createRow(rowIdx++);
	        currentDateRow.createCell(0).setCellValue("Report Downloaded On : " + currentDate);
	        
	        workbook.setForceFormulaRecalculation(true); // Enable automatic formula recalculation

	        workbook.write(outputStream);
	    }

	    return ResponseEntity.ok()
	            .headers(headers)
	            .body(outputStream.toByteArray());
	}
	
	@GetMapping("/ComprehensiveloginReport/SACS")
	public ResponseEntity<byte[]> getloginReportRole(
	    @RequestParam String startDate,
	    @RequestParam String endDate,
	    @RequestParam(defaultValue = "0") Integer page,
	    @RequestParam(defaultValue = "1000") Integer pageSize) throws ParseException, IOException {

	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date parsedStartDate = dateFormat.parse(startDate);
	    Date parsedEndDate = dateFormat.parse(endDate);

	    
	    SimpleDateFormat currentDateFormatter = new SimpleDateFormat("dd-MMM-yyyy");
	    String currentDate = currentDateFormatter.format(new Date());

	    String reportPeriod = "Report Period : " + formatDate(parsedStartDate) + " To " + formatDate(parsedEndDate);
	    
	    List<Object[]> allData = (reportsService.getLoginReportSacs(parsedStartDate, parsedEndDate))
	            .collect(Collectors.toList());

	    int totalRecords = allData.size();
	    int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

	    Stream<Object[]> chunkDataStream = allData.stream()
	            .skip(page * pageSize)
	            .limit(totalRecords);

	    HttpHeaders headers = new HttpHeaders();
	    headers.add("X-Total-Records", String.valueOf(totalRecords));
	    headers.add("X-Total-Pages", String.valueOf(totalPages));
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    headers.setContentDispositionFormData("attachment", "Comprehensive_Data_Report_SACS.xlsx");

	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	    try (InputStream templateStream = getClass().getResourceAsStream("/Comprehensive_Data_Report_SACS.xlsx");
	         Workbook workbook = WorkbookFactory.create(templateStream)) {

	        Sheet sheet = workbook.getSheet("Sheet");
	        
	        int rowIdx = sheet.getLastRowNum() + 1;
	        CellStyle numericCellStyle = workbook.createCellStyle();
	        DataFormat dataFormat = workbook.createDataFormat();
	        numericCellStyle.setDataFormat(dataFormat.getFormat("#0")); // Set the desired numeric format

	        for (Object[] rowData : chunkDataStream.collect(Collectors.toList())) {
	            Row row = sheet.createRow(rowIdx++);
	            int cellIdx = 0;
	            for (Object cellData : rowData) {
	                Cell cell = row.createCell(cellIdx++);
	                if (cellData != null) {
	                    if (cellData instanceof Number) {
	                        cell.setCellValue(((Number) cellData).doubleValue());
	                        cell.setCellStyle(numericCellStyle);
	                    } else {	                        
	                        cell.setCellValue(cellData.toString());
	                    }
	                } else {
	                   
	                    cell.setCellValue(""); 
	                }
	            }
	        }
	        Row paramsRow = sheet.createRow(rowIdx++ + 2);
	        paramsRow.createCell(0).setCellValue(reportPeriod);

	        Row currentDateRow = sheet.createRow(rowIdx++);
	        currentDateRow.createCell(0).setCellValue("Report Downloaded On : " + currentDate);
	        workbook.setForceFormulaRecalculation(true); 

	        workbook.write(outputStream);
	    }

	    return ResponseEntity.ok()
	            .headers(headers)
	            .body(outputStream.toByteArray());
	}
	
	private String formatDate(Date date) {
	    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy");
	    return dateFormatter.format(date);
	}
	

	
	@GetMapping("/MasterLine")
	public ResponseEntity<byte[]> getMasterLine(
		@RequestParam Integer stateId,
	    @RequestParam(defaultValue = "0") Integer page,
	    @RequestParam(defaultValue = "1000") Integer pageSize) throws ParseException, IOException {

	    
	    List<Object[]> allData = (reportsService.getMasterLine(stateId))
	            .collect(Collectors.toList());

	    int totalRecords = allData.size();
	    int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

	    Stream<Object[]> chunkDataStream = allData.stream()
	            .skip(page * pageSize)
	            .limit(totalRecords);

	    HttpHeaders headers = new HttpHeaders();
	    headers.add("X-Total-Records", String.valueOf(totalRecords));
	    headers.add("X-Total-Pages", String.valueOf(totalPages));
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    headers.setContentDispositionFormData("attachment", "Master_Line_List.xlsx");

	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	    try (InputStream templateStream = getClass().getResourceAsStream("/Master_Line_List.xlsx");
	         Workbook workbook = WorkbookFactory.create(templateStream)) {

	        Sheet sheet = workbook.getSheet("Sheet1");
	        
	        int rowIdx = sheet.getLastRowNum() + 1;
	        CellStyle numericCellStyle = workbook.createCellStyle();
	        DataFormat dataFormat = workbook.createDataFormat();
	        numericCellStyle.setDataFormat(dataFormat.getFormat("#0")); // Set the desired numeric format

	        for (Object[] rowData : chunkDataStream.collect(Collectors.toList())) {
	            Row row = sheet.createRow(rowIdx++);
	            int cellIdx = 0;
	            for (Object cellData : rowData) {
	                Cell cell = row.createCell(cellIdx++);
	                if (cellData != null) {
	                    if (cellData instanceof Number) {
	                        cell.setCellValue(((Number) cellData).doubleValue());
	                        cell.setCellStyle(numericCellStyle);
	                    } else {	                        
	                        cell.setCellValue(cellData.toString());
	                    }
	                } else {
	                   
	                    cell.setCellValue(""); 
	                }
	            }
	        }
	        workbook.write(outputStream);
	    }

	    return ResponseEntity.ok()
	            .headers(headers)
	            .body(outputStream.toByteArray());
	}
	
	
	
	@GetMapping("/facilities")
    public ResponseEntity<List<Object[]>> getFacilities(
            @RequestParam Long stateId,
            @RequestParam Long facilityTypeId,
            @RequestParam Long DistrictId
    ) {
        List<Object[]> facilities = reportsService.getFacilities(stateId, facilityTypeId,DistrictId);
        return ResponseEntity.ok(facilities);
    }
	
	
	@GetMapping("/FacilityLine")
	public ResponseEntity<byte[]> getFacilityLine(
		@RequestParam Integer stateId,
	    @RequestParam(defaultValue = "0") Integer page,
	    @RequestParam(defaultValue = "1000") Integer pageSize) throws ParseException, IOException {

	    
	    List<Object[]> allData = (reportsService.getFacilityLine(stateId))
	            .collect(Collectors.toList());

	    int totalRecords = allData.size();
	    int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

	    Stream<Object[]> chunkDataStream = allData.stream()
	            .skip(page * pageSize)
	            .limit(totalRecords);

	    HttpHeaders headers = new HttpHeaders();
	    headers.add("X-Total-Records", String.valueOf(totalRecords));
	    headers.add("X-Total-Pages", String.valueOf(totalPages));
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    headers.setContentDispositionFormData("attachment", "Facility_Line_List.xlsx");

	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	    try (InputStream templateStream = getClass().getResourceAsStream("/Facility_Line_List.xlsx");
	         Workbook workbook = WorkbookFactory.create(templateStream)) {

	        Sheet sheet = workbook.getSheet("Sheet1");
	        
	        int rowIdx = sheet.getLastRowNum() + 1;
	        CellStyle numericCellStyle = workbook.createCellStyle();
	        DataFormat dataFormat = workbook.createDataFormat();
	        numericCellStyle.setDataFormat(dataFormat.getFormat("#0")); // Set the desired numeric format

	        for (Object[] rowData : chunkDataStream.collect(Collectors.toList())) {
	            Row row = sheet.createRow(rowIdx++);
	            int cellIdx = 0;
	            for (Object cellData : rowData) {
	                Cell cell = row.createCell(cellIdx++);
	                if (cellData != null) {
	                    if (cellData instanceof Number) {
	                        cell.setCellValue(((Number) cellData).doubleValue());
	                        cell.setCellStyle(numericCellStyle);
	                    } else {	                        
	                        cell.setCellValue(cellData.toString());
	                    }
	                } else {
	                   
	                    cell.setCellValue(""); 
	                }
	            }
	        }
	        workbook.write(outputStream);
	    }

	    return ResponseEntity.ok()
	            .headers(headers)
	            .body(outputStream.toByteArray());
	}
	
	
	
	@GetMapping("/dispensationReport")
	public ResponseEntity<byte[]> getdispensationReport(
		@RequestParam Integer facilityId,
	    @RequestParam String startDate,
	    @RequestParam String endDate,
	    @RequestParam(defaultValue = "0") Integer page,
	    @RequestParam(defaultValue = "1000") Integer pageSize) throws ParseException, IOException {

	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date parsedStartDate = dateFormat.parse(startDate);
	    Date parsedEndDate = dateFormat.parse(endDate);

	    SimpleDateFormat currentDateFormatter = new SimpleDateFormat("dd-MMM-yyyy");
	    String currentDate = currentDateFormatter.format(new Date());

	    String reportPeriod = "Report Period : " + formatDate(parsedStartDate) + " To " + formatDate(parsedEndDate);
	    List<Object[]> allData = (reportsService.getdispensationReport(facilityId, parsedStartDate, parsedEndDate))
	            .collect(Collectors.toList());
	    int totalRecords = allData.size();
	    int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

	    Stream<Object[]> chunkDataStream = allData.stream()
	            .skip(page * pageSize)
	            .limit(totalRecords);
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("X-Total-Records", String.valueOf(totalRecords));
	    headers.add("X-Total-Pages", String.valueOf(totalPages));
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    headers.setContentDispositionFormData("attachment", "Dispensation_List.xlsx");
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	    try (InputStream templateStream = getClass().getResourceAsStream("/Dispensation_List.xlsx");
	         Workbook workbook = WorkbookFactory.create(templateStream)) {
	    		 
	    	
	    	Sheet sheet = workbook.getSheet("Sheet1");    
	        int rowIdx = sheet.getLastRowNum() + 1;
	        CellStyle numericCellStyle = workbook.createCellStyle();
	        DataFormat dataFormat = workbook.createDataFormat();
	        numericCellStyle.setDataFormat(dataFormat.getFormat("#0")); // Set the desired numeric format

	        for (Object[] rowData : chunkDataStream.collect(Collectors.toList())) {
	            Row row = sheet.createRow(rowIdx++);
	            int cellIdx = 0;
	            for (Object cellData : rowData) {
	                Cell cell = row.createCell(cellIdx++);
	                if (cellData != null) {
	                    if (cellData instanceof Number) {
	                        cell.setCellValue(((Number) cellData).doubleValue());
	                        cell.setCellStyle(numericCellStyle);
	                    } else {	                        
	                        cell.setCellValue(cellData.toString());
	                    }
	                } else {
	                   
	                    cell.setCellValue(""); 
	                }
	            }
	        }
	        Row paramsRow = sheet.createRow(rowIdx++ + 2);
	        paramsRow.createCell(0).setCellValue(reportPeriod);

	        Row currentDateRow = sheet.createRow(rowIdx++);
	        currentDateRow.createCell(0).setCellValue("Report Downloaded On : " + currentDate);
	        workbook.setForceFormulaRecalculation(true); 

	        workbook.write(outputStream);
	    }

	    return ResponseEntity.ok()
	            .headers(headers)
	            .body(outputStream.toByteArray());
	}
	
//	@GetMapping("/WeeklyReport")
//    public ResponseEntity<List<Object[]>> getWeeklyReport(
//        @RequestParam String date) throws ParseException {
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date parseddate = dateFormat.parse(date);
//
//        List<Object[]> allData = new ArrayList<>(reportsService.getWeeklyReport(parseddate));
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("X-Total-Records", String.valueOf(allData.size()));
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .body(allData);
//    }
//	
//	@GetMapping("/ArtDispensationReport")
//    public ResponseEntity<byte[]> getArtDispensationReport(
//        @RequestParam Integer facilityId,
//        @RequestParam String startDate,
//        @RequestParam String endDate,
//        @RequestParam(defaultValue = "0") Integer page,
//        @RequestParam(defaultValue = "1000") Integer pageSize) throws ParseException, IOException {
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date parsedStartDate = dateFormat.parse(startDate);
//        Date parsedEndDate = dateFormat.parse(endDate);
//
//        SimpleDateFormat currentDateFormatter = new SimpleDateFormat("dd-MMM-yyyy");
//        String currentDate = currentDateFormatter.format(new Date());
//
//        String reportPeriod = "Report Period : " + formatDate(parsedStartDate) + " To " + formatDate(parsedEndDate);
//        List<Object[]> allData = (reportsService.getArtDispensationReport(facilityId, parsedStartDate, parsedEndDate))
//                .collect(Collectors.toList());
//        int totalRecords = allData.size();
//        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
//
//        Stream<Object[]> chunkDataStream = allData.stream()
//                .skip(page * pageSize)
//                .limit(totalRecords);
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("X-Total-Records", String.valueOf(totalRecords));
//        headers.add("X-Total-Pages", String.valueOf(totalPages));
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//        headers.setContentDispositionFormData("attachment", "ArtDispensation.xlsx");
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//        try (InputStream templateStream = getClass().getResourceAsStream("/ArtDispensation.xlsx");
//             Workbook workbook = WorkbookFactory.create(templateStream)) {
//                 
//            
//            Sheet sheet = workbook.getSheet("Sheet1");    
//            int rowIdx = sheet.getLastRowNum() + 1;
//            CellStyle numericCellStyle = workbook.createCellStyle();
//            DataFormat dataFormat = workbook.createDataFormat();
//            numericCellStyle.setDataFormat(dataFormat.getFormat("#0")); // Set the desired numeric format
//
//            for (Object[] rowData : chunkDataStream.collect(Collectors.toList())) {
//                Row row = sheet.createRow(rowIdx++);
//                int cellIdx = 0;
//                for (Object cellData : rowData) {
//                    Cell cell = row.createCell(cellIdx++);
//                    if (cellData != null) {
//                        if (cellData instanceof Number) {
//                            cell.setCellValue(((Number) cellData).doubleValue());
//                            cell.setCellStyle(numericCellStyle);
//                        } else {                            
//                            cell.setCellValue(cellData.toString());
//                        }
//                    } else {
//                       
//                        cell.setCellValue(""); 
//                    }
//                }
//            }
//            Row paramsRow = sheet.createRow(rowIdx++ + 2);
//            paramsRow.createCell(0).setCellValue(reportPeriod);
//
//            Row currentDateRow = sheet.createRow(rowIdx++);
//            currentDateRow.createCell(0).setCellValue("Report Downloaded On : " + currentDate);
//            workbook.setForceFormulaRecalculation(true); 
//
//            workbook.write(outputStream);
//        }
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .body(outputStream.toByteArray());
//    }
//    
    
//    @GetMapping("/GCPWReport")
//    public ResponseEntity<byte[]> GCPWReport(
//        @RequestParam String startDate,
//        @RequestParam String endDate,
//        @RequestParam(defaultValue = "0") Integer page,
//        @RequestParam(defaultValue = "1000") Integer pageSize) throws ParseException, IOException {
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date parsedStartDate = dateFormat.parse(startDate);
//        Date parsedEndDate = dateFormat.parse(endDate);
//
//        SimpleDateFormat currentDateFormatter = new SimpleDateFormat("dd-MMM-yyyy");
//        String currentDate = currentDateFormatter.format(new Date());
//
//        String reportPeriod = "Report Period : " + formatDate(parsedStartDate) + " To " + formatDate(parsedEndDate);
//        List<Object[]> allData = (reportsService.getGCPWReport( parsedStartDate, parsedEndDate))
//                .collect(Collectors.toList());
//        int totalRecords = allData.size();
//        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
//
//        Stream<Object[]> chunkDataStream = allData.stream()
//                .skip(page * pageSize)
//                .limit(totalRecords);
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("X-Total-Records", String.valueOf(totalRecords));
//        headers.add("X-Total-Pages", String.valueOf(totalPages));
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//        headers.setContentDispositionFormData("attachment", "GC_PW_LL.xlsx");
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//        try (InputStream templateStream = getClass().getResourceAsStream("/GC_PW_LL.xlsx");
//             Workbook workbook = WorkbookFactory.create(templateStream)) {
//                 
//            
//            Sheet sheet = workbook.getSheet("Sheet1");    
//            int rowIdx = sheet.getLastRowNum() + 1;
//            CellStyle numericCellStyle = workbook.createCellStyle();
//            DataFormat dataFormat = workbook.createDataFormat();
//            numericCellStyle.setDataFormat(dataFormat.getFormat("#0")); // Set the desired numeric format
//
//            for (Object[] rowData : chunkDataStream.collect(Collectors.toList())) {
//                Row row = sheet.createRow(rowIdx++);
//                int cellIdx = 0;
//                for (Object cellData : rowData) {
//                    Cell cell = row.createCell(cellIdx++);
//                    if (cellData != null) {
//                        if (cellData instanceof Number) {
//                            cell.setCellValue(((Number) cellData).doubleValue());
//                            cell.setCellStyle(numericCellStyle);
//                        } else {                            
//                            cell.setCellValue(cellData.toString());
//                        }
//                    } else {
//                       
//                        cell.setCellValue(""); 
//                    }
//                }
//            }
//            Row paramsRow = sheet.createRow(rowIdx++ + 2);
//            paramsRow.createCell(0).setCellValue(reportPeriod);
//
//            Row currentDateRow = sheet.createRow(rowIdx++);
//            currentDateRow.createCell(0).setCellValue("Report Downloaded On : " + currentDate);
//            workbook.setForceFormulaRecalculation(true); 
//
//            workbook.write(outputStream);
//        }
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .body(outputStream.toByteArray());
//    }
	
	@GetMapping("/WeeklyReport")
    public ResponseEntity<List<Object[]>> getWeeklyReport(
        @RequestParam String date) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parseddate = dateFormat.parse(date);

        List<Object[]> allData = new ArrayList<>(reportsService.getWeeklyReport(parseddate));

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Records", String.valueOf(allData.size()));

        return ResponseEntity.ok()
                .headers(headers)
                .body(allData);
    }
	
	
	@GetMapping("/ArtDispensationReport")
	public ResponseEntity<byte[]> getArtDispensationReport(
		@RequestParam Integer facilityId,
	    @RequestParam String startDate,
	    @RequestParam String endDate,
	    @RequestParam(defaultValue = "0") Integer page,
	    @RequestParam(defaultValue = "1000") Integer pageSize) throws ParseException, IOException {

	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date parsedStartDate = dateFormat.parse(startDate);
	    Date parsedEndDate = dateFormat.parse(endDate);

	    SimpleDateFormat currentDateFormatter = new SimpleDateFormat("dd-MMM-yyyy");
	    String currentDate = currentDateFormatter.format(new Date());

	    String reportPeriod = "Report Period : " + formatDate(parsedStartDate) + " To " + formatDate(parsedEndDate);
	    List<Object[]> allData = (reportsService.getArtDispensationReport(facilityId, parsedStartDate, parsedEndDate))
	            .collect(Collectors.toList());
	    int totalRecords = allData.size();
	    int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

	    Stream<Object[]> chunkDataStream = allData.stream()
	            .skip(page * pageSize)
	            .limit(totalRecords);
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("X-Total-Records", String.valueOf(totalRecords));
	    headers.add("X-Total-Pages", String.valueOf(totalPages));
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    headers.setContentDispositionFormData("attachment", "ArtDispensation.xlsx");
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	    try (InputStream templateStream = getClass().getResourceAsStream("/ArtDispensation.xlsx");
	         Workbook workbook = WorkbookFactory.create(templateStream)) {
	    		 
	    	
	    	Sheet sheet = workbook.getSheet("Sheet1");    
	        int rowIdx = sheet.getLastRowNum() + 1;
	        CellStyle numericCellStyle = workbook.createCellStyle();
	        DataFormat dataFormat = workbook.createDataFormat();
	        numericCellStyle.setDataFormat(dataFormat.getFormat("#0")); // Set the desired numeric format

	        for (Object[] rowData : chunkDataStream.collect(Collectors.toList())) {
	            Row row = sheet.createRow(rowIdx++);
	            int cellIdx = 0;
	            for (Object cellData : rowData) {
	                Cell cell = row.createCell(cellIdx++);
	                if (cellData != null) {
	                    if (cellData instanceof Number) {
	                        cell.setCellValue(((Number) cellData).doubleValue());
	                        cell.setCellStyle(numericCellStyle);
	                    } else {	                        
	                        cell.setCellValue(cellData.toString());
	                    }
	                } else {
	                   
	                    cell.setCellValue(""); 
	                }
	            }
	        }
	        Row paramsRow = sheet.createRow(rowIdx++ + 2);
	        paramsRow.createCell(0).setCellValue(reportPeriod);

	        Row currentDateRow = sheet.createRow(rowIdx++);
	        currentDateRow.createCell(0).setCellValue("Report Downloaded On : " + currentDate);
	        workbook.setForceFormulaRecalculation(true); 

	        workbook.write(outputStream);
	    }

	    return ResponseEntity.ok()
	            .headers(headers)
	            .body(outputStream.toByteArray());
	}
	
	
	
	
	@GetMapping("/GCPWReport")
	public ResponseEntity<byte[]> GCPWReport(
			@RequestParam Integer stateId,
			@RequestParam Integer districtId,
			@RequestParam Integer facilityId,
			@RequestParam String pidPrefix,
	    @RequestParam(defaultValue = "0") Integer page,
	    @RequestParam(defaultValue = "1000") Integer pageSize) throws ParseException, IOException {


	    SimpleDateFormat currentDateFormatter = new SimpleDateFormat("dd-MMM-yyyy");
	    String currentDate = currentDateFormatter.format(new Date());

	    //String reportPeriod = "Report Period : " + formatDate(parsedStartDate) + " To " + formatDate(parsedEndDate);
	    List<Object[]> allData = (criteriaService.getGCPWReport( stateId,districtId,facilityId,pidPrefix))
	            .collect(Collectors.toList());
	    int totalRecords = allData.size();
	    int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

	    Stream<Object[]> chunkDataStream = allData.stream()
	            .skip(page * pageSize)
	            .limit(totalRecords);
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("X-Total-Records", String.valueOf(totalRecords));
	    headers.add("X-Total-Pages", String.valueOf(totalPages));
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    headers.setContentDispositionFormData("attachment", "ICTC_Linelist.xlsx");
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	    try (InputStream templateStream = getClass().getResourceAsStream("/ICTC_Linelist.xlsx");
	         Workbook workbook = WorkbookFactory.create(templateStream)) {
	    		 
	    	
	    	Sheet sheet = workbook.getSheet("Sheet1");    
	        int rowIdx = sheet.getLastRowNum() + 1;
	        CellStyle numericCellStyle = workbook.createCellStyle();
	        DataFormat dataFormat = workbook.createDataFormat();
	        numericCellStyle.setDataFormat(dataFormat.getFormat("#0")); // Set the desired numeric format

	        for (Object[] rowData : chunkDataStream.collect(Collectors.toList())) {
	            Row row = sheet.createRow(rowIdx++);
	            int cellIdx = 0;
	            for (Object cellData : rowData) {
	                Cell cell = row.createCell(cellIdx++);
	                if (cellData != null) {
	                    if (cellData instanceof Number) {
	                        cell.setCellValue(((Number) cellData).doubleValue());
	                        cell.setCellStyle(numericCellStyle);
	                    } else {	                        
	                        cell.setCellValue(cellData.toString());
	                    }
	                } else {
	                   
	                    cell.setCellValue(""); 
	                }
	            }
	        }
	        Row paramsRow = sheet.createRow(rowIdx++ + 2);
	       // paramsRow.createCell(0).setCellValue(reportPeriod);

	        Row currentDateRow = sheet.createRow(rowIdx++);
	        currentDateRow.createCell(0).setCellValue("Report Downloaded On : " + currentDate);
	        workbook.setForceFormulaRecalculation(true); 

	        workbook.write(outputStream);
	    }

	    return ResponseEntity.ok()
	            .headers(headers)
	            .body(outputStream.toByteArray());
	}
	
	
	
	@GetMapping("/stockledgerFacReport")
	public ResponseEntity<byte[]> StockLedger(
	    @RequestParam String startdate,
	    @RequestParam String enddate,
	    @RequestParam Integer facilityId,
	    @RequestParam(defaultValue = "0") Integer page,
	    @RequestParam(defaultValue = "1000") Integer pageSize) throws ParseException, IOException {

	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date parsedStartDate = dateFormat.parse(startdate);
	    Date parsedEndDate = dateFormat.parse(enddate);

	    SimpleDateFormat currentDateFormatter = new SimpleDateFormat("dd-MMM-yyyy");
	    String currentDate = currentDateFormatter.format(new Date());

	    String reportPeriod = "Report Period : " + formatDate(parsedStartDate) + " To " + formatDate(parsedEndDate);
	    List<Object[]> allData = (reportsService.getStockLedgerFac( facilityId, parsedStartDate, parsedEndDate))
	            .collect(Collectors.toList());
	    int totalRecords = allData.size();
	    int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

	    Stream<Object[]> chunkDataStream = allData.stream()
	            .skip(page * pageSize)
	            .limit(totalRecords);
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("X-Total-Records", String.valueOf(totalRecords));
	    headers.add("X-Total-Pages", String.valueOf(totalPages));
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    headers.setContentDispositionFormData("attachment", "StockLedgerFacility.xlsx");
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	    try (InputStream templateStream = getClass().getResourceAsStream("/StockLedgerFacility.xlsx");
	         Workbook workbook = WorkbookFactory.create(templateStream)) {
	    		 
	    	
	    	Sheet sheet = workbook.getSheet("Sheet1");    
	        int rowIdx = sheet.getLastRowNum() + 1;
	        CellStyle numericCellStyle = workbook.createCellStyle();
	        DataFormat dataFormat = workbook.createDataFormat();
	        numericCellStyle.setDataFormat(dataFormat.getFormat("#0")); // Set the desired numeric format

	        for (Object[] rowData : chunkDataStream.collect(Collectors.toList())) {
	            Row row = sheet.createRow(rowIdx++);
	            int cellIdx = 0;
	            for (Object cellData : rowData) {
	                Cell cell = row.createCell(cellIdx++);
	                if (cellData != null) {
	                    if (cellData instanceof Number) {
	                        cell.setCellValue(((Number) cellData).doubleValue());
	                        cell.setCellStyle(numericCellStyle);
	                    } else {	                        
	                        cell.setCellValue(cellData.toString());
	                    }
	                } else {
	                   
	                    cell.setCellValue(""); 
	                }
	            }
	        }
	        Row paramsRow = sheet.createRow(rowIdx++ + 2);
	        paramsRow.createCell(0).setCellValue(reportPeriod);

	        Row currentDateRow = sheet.createRow(rowIdx++);
	        currentDateRow.createCell(0).setCellValue("Report Downloaded On : " + currentDate);
	        workbook.setForceFormulaRecalculation(true); 

	        workbook.write(outputStream);
	    }

	    return ResponseEntity.ok()
	            .headers(headers)
	            .body(outputStream.toByteArray());
	}
	
	@GetMapping("/stockledgerSacsReport")
	public ResponseEntity <List<Object[]>>StockLedgerSacs(
	    @RequestParam String stateName,
	    @RequestParam (required = false)String districtName,
	    @RequestParam (required = false)String facilityType,
	    @RequestParam(defaultValue = "0") Integer page,
	    @RequestParam(defaultValue = "1000") Integer pageSize) throws ParseException, IOException {

	    List<Object[]> allData = new ArrayList<>(reportsService.getStockLedgerSACS(stateName,districtName,facilityType));

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Records", String.valueOf(allData.size()));

        return ResponseEntity.ok()
                .headers(headers)
                .body(allData);
	}	
	
	@GetMapping("/getstockExpirySACS")
	public ResponseEntity <List<Object[]>>stockExpirySACS(
	    @RequestParam String stateName,
	    @RequestParam (required = false)String districtName,
	    @RequestParam(defaultValue = "0") Integer page,
	    @RequestParam(defaultValue = "1000") Integer pageSize) throws ParseException, IOException {

	    List<Object[]> allData = new ArrayList<>(reportsService.getstockExpirySACS(stateName,districtName));

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Records", String.valueOf(allData.size()));

        return ResponseEntity.ok()
                .headers(headers)
                .body(allData);
	}	
	@GetMapping("/getstockExpiryFacility")
	public ResponseEntity <List<Object[]>>stockExpiryFac(
	    @RequestParam Integer facilityId,
	    @RequestParam(defaultValue = "0") Integer page,
	    @RequestParam(defaultValue = "1000") Integer pageSize) throws ParseException, IOException {

	    List<Object[]> allData = new ArrayList<>(reportsService.getstockExpiryFac(facilityId));

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Records", String.valueOf(allData.size()));

        return ResponseEntity.ok()
                .headers(headers)
                .body(allData);
	}	
	
	@GetMapping("/stockOutFacReport")
	public ResponseEntity<Map<String, Object>> stockOutFacReport(
	        @RequestParam Integer facilityId,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size) {

	    Pageable pageable = PageRequest.of(page, size);
	    Page<Object[]> pageData = reportsService.getStockOutFacility(facilityId, pageable);
	    
	    Map<String, Object> response = new HashMap<>();
	    response.put("data", pageData.getContent());
	    response.put("totalRecords", pageData.getTotalElements());
	    response.put("currentPage", pageData.getNumber());
	    response.put("totalPages", pageData.getTotalPages());

	    return ResponseEntity.ok().body(response);
	}


	
	@GetMapping("/stockOutSacsReport")
	public ResponseEntity<Map<String, Object>> stockOutSACS(
	        @RequestParam String stateName,
	        @RequestParam(required = false) String districtName,
	        @RequestParam(required = false) String facilityType,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int pageSize) throws ParseException, IOException {

	    Page<Object[]> pageData = reportsService.getStockOutSACS(stateName, districtName, facilityType, page, pageSize);

	    Map<String, Object> response = new HashMap<>();
	    response.put("data", pageData.getContent());
	    response.put("currentPage", pageData.getNumber());
	    response.put("totalItems", pageData.getTotalElements());
	    response.put("totalPages", pageData.getTotalPages());

	    return ResponseEntity.ok().body(response);
	}
	
	@GetMapping("/stockOutSacsExcel")
	public ResponseEntity<byte[]> stockOutSACSExcel(
	    @RequestParam String stateName,
	    @RequestParam(defaultValue = "0") Integer page,
	    @RequestParam(defaultValue = "1000") Integer pageSize) throws ParseException, IOException {

	    List<Object[]> allData = (reportsService.getStockOutSACSExcel( stateName))
	            .collect(Collectors.toList());
	    int totalRecords = allData.size();
	    int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

	    Stream<Object[]> chunkDataStream = allData.stream()
	            .skip(page * pageSize)
	            .limit(totalRecords);
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("X-Total-Records", String.valueOf(totalRecords));
	    headers.add("X-Total-Pages", String.valueOf(totalPages));
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    headers.setContentDispositionFormData("attachment", "Stock_Out_SACS.xlsx");
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	    try (InputStream templateStream = getClass().getResourceAsStream("/Stock_Out_SACS.xlsx");
	         Workbook workbook = WorkbookFactory.create(templateStream)) {
	    		 
	    	
	    	Sheet sheet = workbook.getSheet("Sheet1");    
	        int rowIdx = sheet.getLastRowNum() + 1;
	        CellStyle numericCellStyle = workbook.createCellStyle();
	        DataFormat dataFormat = workbook.createDataFormat();
	        numericCellStyle.setDataFormat(dataFormat.getFormat("#0")); // Set the desired numeric format

	        for (Object[] rowData : chunkDataStream.collect(Collectors.toList())) {
	            Row row = sheet.createRow(rowIdx++);
	            int cellIdx = 0;
	            for (Object cellData : rowData) {
	                Cell cell = row.createCell(cellIdx++);
	                if (cellData != null) {
	                    if (cellData instanceof Number) {
	                        cell.setCellValue(((Number) cellData).doubleValue());
	                        cell.setCellStyle(numericCellStyle);
	                    } else {	                        
	                        cell.setCellValue(cellData.toString());
	                    }
	                } else {
	                   
	                    cell.setCellValue(""); 
	                }
	            }
	        }
	        //Row paramsRow = sheet.createRow(rowIdx++ + 2);
	        //paramsRow.createCell(0).setCellValue(reportPeriod);

	        //Row currentDateRow = sheet.createRow(rowIdx++);
	        //currentDateRow.createCell(0).setCellValue("Report Downloaded On : " + currentDate);
	        workbook.setForceFormulaRecalculation(true); 

	        workbook.write(outputStream);
	    }

	    return ResponseEntity.ok()
	            .headers(headers)
	            .body(outputStream.toByteArray());
	}

}
