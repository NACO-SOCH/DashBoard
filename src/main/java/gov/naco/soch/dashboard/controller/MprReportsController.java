package gov.naco.soch.dashboard.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gov.naco.soch.dashboard.service.MprReportsService;
import gov.naco.soch.singelton.LoggerSingleton;

@RestController
@RequestMapping("/MprReports")
@CrossOrigin(origins = { "http://localhost:4200", "*" }, allowedHeaders = "*")
public class MprReportsController {
	
	
	@Autowired
	MprReportsService mprReportsService;
	
    Logger logger = LoggerSingleton.getInstance().getLogger();

	@GetMapping("/ictcmpr")
	public ResponseEntity<byte[]> getIctcMpr(@RequestParam Integer facilityId, @RequestParam Integer mprMonth,
			@RequestParam Integer mprYear, @RequestParam Integer ictcStateId,
			@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "1000") Integer pageSize)
			throws IOException {

		List<Object[]> allData = mprReportsService.getIctcMpr(facilityId, mprMonth, mprYear, ictcStateId)
				.collect(Collectors.toList());

		int totalRecords = allData.size();
		int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

		Stream<Object[]> chunkDataStream = allData.stream().skip(page * pageSize).limit(totalRecords);

		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Total-Records", String.valueOf(totalRecords));
		headers.add("X-Total-Pages", String.valueOf(totalPages));
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", "ICTC_MPR.xlsx");

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		try (InputStream templateStream = getClass().getResourceAsStream("/ICTC_MPR.xlsx");
				Workbook workbook = WorkbookFactory.create(templateStream)) {

			Sheet sheet = workbook.getSheet("MPR");

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
			workbook.setForceFormulaRecalculation(true); // Enable automatic formula recalculation

			workbook.write(outputStream);
		}

		return ResponseEntity.ok().headers(headers).body(outputStream.toByteArray());
	}

	@GetMapping("/VLmpr")
	public ResponseEntity<byte[]> getVlMpr(@RequestParam Integer LabId, @RequestParam Integer mprMonth,
			@RequestParam Integer mprYear, @RequestParam Integer StateId,
			@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "1000") Integer pageSize)
			throws IOException {

		List<Object[]> allData = mprReportsService.getVLMPRData(LabId, mprMonth, mprYear, StateId)
				.collect(Collectors.toList());

		int totalRecords = allData.size();
		int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

		Stream<Object[]> chunkDataStream = allData.stream().skip(page * pageSize).limit(totalRecords);

		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Total-Records", String.valueOf(totalRecords));
		headers.add("X-Total-Pages", String.valueOf(totalPages));
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", "VL_MPR.xlsx");

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		try (InputStream templateStream = getClass().getResourceAsStream("/VL_MPR.xlsx");
				Workbook workbook = WorkbookFactory.create(templateStream)) {

			Sheet sheet = workbook.getSheet("VL");

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
			workbook.setForceFormulaRecalculation(true); // Enable automatic formula recalculation

			workbook.write(outputStream);
		}

		return ResponseEntity.ok().headers(headers).body(outputStream.toByteArray());
	}

	
	@GetMapping("/ConsolidatedVlMpr")
	public ResponseEntity<byte[]> getConsolidatedVlMpr(@RequestParam Integer LabId, @RequestParam Integer mprMonth,
			@RequestParam Integer mprYear, @RequestParam Integer StateId,
			@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "1000") Integer pageSize)
			throws IOException {

		List<Object[]> allData = mprReportsService.getConsolidatedVLMPRData(LabId, mprMonth, mprYear, StateId)
				.collect(Collectors.toList());

		int totalRecords = allData.size();
		int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

		Stream<Object[]> chunkDataStream = allData.stream().skip(page * pageSize).limit(totalRecords);

		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Total-Records", String.valueOf(totalRecords));
		headers.add("X-Total-Pages", String.valueOf(totalPages));
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", "Consolidated_VL_MPR.xlsx");

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		try (InputStream templateStream = getClass().getResourceAsStream("/Consolidated_VL_MPR.xlsx");
				Workbook workbook = WorkbookFactory.create(templateStream)) {

			Sheet sheet = workbook.getSheet("VL");

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
			workbook.setForceFormulaRecalculation(true); // Enable automatic formula recalculation

			workbook.write(outputStream);
		}

		return ResponseEntity.ok().headers(headers).body(outputStream.toByteArray());
	}
	
	
	@GetMapping("/CD4mpr")
	public ResponseEntity<byte[]> getCD4Mpr(@RequestParam Integer LabId, @RequestParam Integer mprMonth,
			@RequestParam Integer mprYear, @RequestParam Integer StateId,
			@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "1000") Integer pageSize)
			throws IOException {

		List<Object[]> allData = mprReportsService.getCD4MPRData(LabId, mprMonth, mprYear, StateId)
				.collect(Collectors.toList());

		int totalRecords = allData.size();
		int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

		Stream<Object[]> chunkDataStream = allData.stream().skip(page * pageSize).limit(totalRecords);

		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Total-Records", String.valueOf(totalRecords));
		headers.add("X-Total-Pages", String.valueOf(totalPages));
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", "CD4_MPR.xlsx");

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		try (InputStream templateStream = getClass().getResourceAsStream("/CD4_MPR.xlsx");
				Workbook workbook = WorkbookFactory.create(templateStream)) {

			Sheet sheet = workbook.getSheet("CD4");

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
						cell.setCellValue("");
					}
				}
			}
			workbook.setForceFormulaRecalculation(true);

			workbook.write(outputStream);
		}

		return ResponseEntity.ok().headers(headers).body(outputStream.toByteArray());
	}
	
	
	
	@GetMapping("/ConsolidatedCD4mpr")
	public ResponseEntity<byte[]> getConsolidatedCD4mpr(@RequestParam Integer LabId, @RequestParam Integer mprMonth,
			@RequestParam Integer mprYear, @RequestParam Integer StateId,
			@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "1000") Integer pageSize)
			throws IOException {

		List<Object[]> allData = mprReportsService.getConsolidatedCD4mpr(LabId, mprMonth, mprYear, StateId)
				.collect(Collectors.toList());

		int totalRecords = allData.size();
		int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

		Stream<Object[]> chunkDataStream = allData.stream().skip(page * pageSize).limit(totalRecords);

		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Total-Records", String.valueOf(totalRecords));
		headers.add("X-Total-Pages", String.valueOf(totalPages));
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", "Consolidated_CD4_MPR.xlsx");

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		try (InputStream templateStream = getClass().getResourceAsStream("/Consolidated_CD4_MPR.xlsx");
				Workbook workbook = WorkbookFactory.create(templateStream)) {

			Sheet sheet = workbook.getSheet("CD4");

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
						cell.setCellValue("");
					}
				}
			}
			workbook.setForceFormulaRecalculation(true);

			workbook.write(outputStream);
		}

		return ResponseEntity.ok().headers(headers).body(outputStream.toByteArray());
	}
	

	@GetMapping("/Eidmpr")
	public ResponseEntity<byte[]> getEidMpr(@RequestParam Integer LabId, @RequestParam Integer mprMonth,
			@RequestParam Integer mprYear, @RequestParam Integer StateId,
			@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "1000") Integer pageSize)
			throws IOException {

		List<Object[]> allData = mprReportsService.getEidMPRData(LabId, mprMonth, mprYear, StateId)
				.collect(Collectors.toList());

		int totalRecords = allData.size();
		int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

		Stream<Object[]> chunkDataStream = allData.stream().skip(page * pageSize).limit(totalRecords);

		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Total-Records", String.valueOf(totalRecords));
		headers.add("X-Total-Pages", String.valueOf(totalPages));
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", "EID_MPR.xlsx");

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		try (InputStream templateStream = getClass().getResourceAsStream("/EID_MPR.xlsx");
				Workbook workbook = WorkbookFactory.create(templateStream)) {

			Sheet sheet = workbook.getSheet("EID");

			int rowIdx = sheet.getLastRowNum() + 1;
			CellStyle numericCellStyle = workbook.createCellStyle();
			DataFormat dataFormat = workbook.createDataFormat();
			numericCellStyle.setDataFormat(dataFormat.getFormat("#0"));

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
			workbook.setForceFormulaRecalculation(true);

			workbook.write(outputStream);
		}

		return ResponseEntity.ok().headers(headers).body(outputStream.toByteArray());
	}

	@GetMapping("/timpr")
	public ResponseEntity<byte[]> getTiMPRData(@RequestParam Integer facility_id, @RequestParam Integer mprMonth,
			@RequestParam Integer mprYear, @RequestParam Integer ti_state_id,
			@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "1000") Integer pageSize)
			throws IOException {

		List<Object[]> allData = mprReportsService.getTiMPRData(facility_id, mprMonth, mprYear, ti_state_id)
				.collect(Collectors.toList());

		int totalRecords = allData.size();
		int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

		Stream<Object[]> chunkDataStream = allData.stream().skip(page * pageSize).limit(totalRecords);

		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Total-Records", String.valueOf(totalRecords));
		headers.add("X-Total-Pages", String.valueOf(totalPages));
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", "TI_MPR.xlsx");

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		try (InputStream templateStream = getClass().getResourceAsStream("/TI_MPR.xlsx");
				Workbook workbook = WorkbookFactory.create(templateStream)) {

			Sheet sheet = workbook.getSheet("TI-MPR");

			int rowIdx = sheet.getLastRowNum() + 1;
			CellStyle numericCellStyle = workbook.createCellStyle();
			DataFormat dataFormat = workbook.createDataFormat();
			numericCellStyle.setDataFormat(dataFormat.getFormat("#0"));

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

			workbook.setForceFormulaRecalculation(true);

			workbook.write(outputStream);
		}

		return ResponseEntity.ok().headers(headers).body(outputStream.toByteArray());
	}

	@GetMapping("/ArtMPR")
	public ResponseEntity<byte[]> getArtMPRData(@RequestParam Integer artcStateId, @RequestParam Integer facilityId,
			@RequestParam Integer mprMonth, @RequestParam Integer mprYear,
			@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "1000") Integer pageSize)
			throws IOException {

		List<Object[]> healthCamps = mprReportsService.getArtMPRData(facilityId, mprMonth, mprYear, artcStateId)
				.collect(Collectors.toList());

		int totalRecords = healthCamps.size();
		int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

		Stream<Object[]> chunkDataStream = healthCamps.stream().skip(page * pageSize).limit(totalRecords);

		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Total-Records", String.valueOf(totalRecords));
		headers.add("X-Total-Pages", String.valueOf(totalPages));
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", "CST_MPR_ARTC.xlsx");

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		try (InputStream templateStream = getClass().getResourceAsStream("/CST_MPR_ARTC.xlsx");
				Workbook workbook = WorkbookFactory.create(templateStream)) {

			Sheet sheet = workbook.getSheet("ARTC");

			int rowIdx = sheet.getLastRowNum() + 1;
			CellStyle numericCellStyle = workbook.createCellStyle();
			DataFormat dataFormat = workbook.createDataFormat();
			numericCellStyle.setDataFormat(dataFormat.getFormat("#0"));

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

			workbook.setForceFormulaRecalculation(true);
			workbook.write(outputStream);
		}

		return ResponseEntity.ok().headers(headers).body(outputStream.toByteArray());
	}

	@GetMapping("/ostmpr")
	public ResponseEntity<byte[]> getOstMpr(@RequestParam Integer facilityId, @RequestParam Integer mprMonth,
			@RequestParam Integer mprYear, @RequestParam Integer OstStateId,
			@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "1000") Integer pageSize)
			throws IOException {

		List<Object[]> allData = mprReportsService.getOstMpr(facilityId, mprMonth, mprYear, OstStateId)
				.collect(Collectors.toList());

		int totalRecords = allData.size();
		int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

		Stream<Object[]> chunkDataStream = allData.stream().skip(page * pageSize).limit(totalRecords);

		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Total-Records", String.valueOf(totalRecords));
		headers.add("X-Total-Pages", String.valueOf(totalPages));
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", "OST_MPR.xlsx");

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		try (InputStream templateStream = getClass().getResourceAsStream("/OST_MPR.xlsx");
				Workbook workbook = WorkbookFactory.create(templateStream)) {

			Sheet sheet = workbook.getSheet("MPR");

			int rowIdx = sheet.getLastRowNum() + 1;
			CellStyle numericCellStyle = workbook.createCellStyle();
			DataFormat dataFormat = workbook.createDataFormat();
			numericCellStyle.setDataFormat(dataFormat.getFormat("#0")); 

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

			workbook.setForceFormulaRecalculation(true); 

			workbook.write(outputStream);
		}
		return ResponseEntity.ok().headers(headers).body(outputStream.toByteArray());
	}
	
	@PostMapping("/mprCount")
	public ResponseEntity<Integer> MprCount(@RequestParam Integer reportType, @RequestParam Integer userid,@RequestParam(required = false) Integer stateid){
		Integer result;
		try {
			if (stateid == null) stateid = 0;
			result = mprReportsService.mprCount(reportType, userid,stateid);
			return new ResponseEntity<Integer>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/mprCountNational")
	public ResponseEntity<Integer> mprCountNational() {
		Integer result;
		try {
			result = mprReportsService.mprCountNational();
			return new ResponseEntity<Integer>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/mprCountStateWise/{stateId}")
	public ResponseEntity<Integer> mprCountStateWise(@PathVariable Integer stateId) {
		Integer result;
		try {
			result = mprReportsService.mprCountStateWise(stateId);
			return new ResponseEntity<Integer>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/offlineCountNational")
	public ResponseEntity<Integer> offlineCountNational() {
		Integer result;
		try {
			result = mprReportsService.offlineCountNational();
			return new ResponseEntity<Integer>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/offlineStateWise/{stateId}")
	public ResponseEntity<Integer> offlineStateWise(@PathVariable Integer stateId) {
		Integer result;
		try {
			result = mprReportsService.offlineStateWise(stateId);
			return new ResponseEntity<Integer>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

}
