package gov.naco.soch.dashboard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gov.naco.soch.dashboard.DTO.AddIndexDTO;
import gov.naco.soch.dashboard.DTO.BeneficiaryDTO;
import gov.naco.soch.dashboard.DTO.BeneficiaryIndexDTO;
import gov.naco.soch.dashboard.DTO.BeneficiaryIndexScreeningDTO;
import gov.naco.soch.dashboard.DTO.CustomPageResponse;
import gov.naco.soch.dashboard.DTO.FacilityDTO;
import gov.naco.soch.dashboard.DTO.IndexBeneficiaryListDto;
import gov.naco.soch.dashboard.service.IndexBeneficiaryService;

@RestController
@RequestMapping("/index")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class IndexBeneficiaryController {

	@Autowired
	IndexBeneficiaryService indexBeneficiaryService;

	@PostMapping("/saveBeneficiaryIndex")
	public ResponseEntity<String> saveBeneficiaryIndex(@RequestBody AddIndexDTO addIndexDTO) {
		try {
			indexBeneficiaryService.saveBeneficiaryIndex(addIndexDTO);
			return new ResponseEntity<>("1", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("0" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//	@PostMapping("/saveBeneficiaryIndex/Screening")
//	public ResponseEntity<String> saveBeneficiaryIndexScreening(
//			@RequestBody BeneficiaryIndexScreeningDTO beneficiaryIndexScreeningDTO) {
////		try {
//			indexBeneficiaryService.saveBeneficiaryIndexScreening(beneficiaryIndexScreeningDTO);
//			return new ResponseEntity<>("1", HttpStatus.CREATED);
////		} catch (Exception e) {
////			return new ResponseEntity<>("0" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
////		}
//	}

	@GetMapping("/beneficiaryART/list")
    public CustomPageResponse getIndexBeneficiariesList(@RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String searchParams) {

        if (pageNumber == null || pageSize == null) {
            pageNumber = 0;
            pageSize = Integer.MAX_VALUE;
        }
        Page<IndexBeneficiaryListDto> page = indexBeneficiaryService.findIndexBeneficiariesByFacilityId(pageNumber,
                pageSize, searchParams);

        List<IndexBeneficiaryListDto> content = page.getContent();
        long totalRecords = page.getTotalElements();
        int totalPages = page.getTotalPages();
        int currentPage = page.getNumber();

        return new CustomPageResponse(totalRecords, totalPages, currentPage, content);
    }

	@GetMapping("/beneficiaryTI/list")
	public CustomPageResponse findIndexBeneficiariesTIByFacilityId(@RequestParam(required = false) Integer pageNumber,
			@RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String searchParams) {

		if (pageNumber == null || pageSize == null) {
			pageNumber = 0;
			pageSize = Integer.MAX_VALUE;
		}
		Page<IndexBeneficiaryListDto> page = indexBeneficiaryService.findIndexBeneficiariesTIByFacilityId(pageNumber,
				pageSize, searchParams);

		List<IndexBeneficiaryListDto> content = page.getContent();
		long totalRecords = page.getTotalElements();
		int totalPages = page.getTotalPages();
		int currentPage = page.getNumber();

		return new CustomPageResponse(totalRecords, totalPages, currentPage, content);
	}

	@GetMapping("/beneficiaryOST/list")
	public CustomPageResponse findIndexBeneficiariesOSTByFacilityId(@RequestParam(required = false) Integer pageNumber,
			@RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String searchParams) {

		if (pageNumber == null || pageSize == null) {
			pageNumber = 0;
			pageSize = Integer.MAX_VALUE;
		}
		Page<IndexBeneficiaryListDto> page = indexBeneficiaryService.findIndexBeneficiariesOSTByFacilityId(pageNumber,
				pageSize, searchParams);

		List<IndexBeneficiaryListDto> content = page.getContent();
		long totalRecords = page.getTotalElements();
		int totalPages = page.getTotalPages();
		int currentPage = page.getNumber();

		return new CustomPageResponse(totalRecords, totalPages, currentPage, content);
	}

	@GetMapping("/beneficiaryDSRC/list")
	public CustomPageResponse findIndexBeneficiariesDSRCByFacilityId(@RequestParam(required = false) Integer pageNumber,
			@RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String searchParams) {

		if (pageNumber == null || pageSize == null) {
			pageNumber = 0;
			pageSize = Integer.MAX_VALUE;
		}

		Page<IndexBeneficiaryListDto> page = indexBeneficiaryService.findIndexBeneficiariesDSRCByFacilityId(pageNumber,
				pageSize, searchParams);

		List<IndexBeneficiaryListDto> content = page.getContent();
		long totalRecords = page.getTotalElements();
		int totalPages = page.getTotalPages();
		int currentPage = page.getNumber();

		return new CustomPageResponse(totalRecords, totalPages, currentPage, content);
	}

	@GetMapping("/beneficiaryICTC/list")
	public CustomPageResponse findIndexBeneficiariesICTCByFacilityId(@RequestParam(required = false) Integer pageNumber,
			@RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String searchParams) {

		if (pageNumber == null || pageSize == null) {
			pageNumber = 0;
			pageSize = Integer.MAX_VALUE;
		}

		Page<IndexBeneficiaryListDto> page = indexBeneficiaryService.findIndexBeneficiariesICTCByFacilityId(pageNumber,
				pageSize, searchParams);

		List<IndexBeneficiaryListDto> content = page.getContent();
		long totalRecords = page.getTotalElements();
		int totalPages = page.getTotalPages();
		int currentPage = page.getNumber();

		return new CustomPageResponse(totalRecords, totalPages, currentPage, content);
	}

	@GetMapping("/beneficiary/{Id}")
	public List<BeneficiaryDTO> findIndexBeneficiariesById(@PathVariable("Id") Long beneficiaryId) {
		return indexBeneficiaryService.findIndexBeneficiariesById(beneficiaryId);
	}

	@GetMapping("/beneficiaryBySearch/{Id}")
	public List<BeneficiaryDTO> findBeneficiaryBySearch(@PathVariable("Id") Long beneficiaryId) {
		return indexBeneficiaryService.findBeneficiaryBySearch(beneficiaryId);
	}

	@GetMapping("/indexScreeingListBySSK/{sskid}")
	public List<BeneficiaryIndexDTO> indexScreeingListBySSK(@PathVariable("sskid") Long sskid) {
		return indexBeneficiaryService.indexScreeingListBySSK(sskid);
	}

	@GetMapping("/indexScreeingListByFacilityId")
	public List<BeneficiaryIndexDTO> indexScreeingListByfacilityId() {
		return indexBeneficiaryService.indexScreeingListByfacilityId();
	}
	
	
	@PostMapping("/indexReferral")
    public ResponseEntity<String> indexReferral(@RequestBody BeneficiaryIndexScreeningDTO beneficiaryIndexScreeningDTO) {
            indexBeneficiaryService.indexReferral(beneficiaryIndexScreeningDTO);
            return new ResponseEntity<>("201", HttpStatus.CREATED);
         }
	
	@GetMapping("/facility/{districtId}")
	public  List<FacilityDTO>  facilityName( @PathVariable("districtId") Integer districtId) {
		return indexBeneficiaryService.facilityName(districtId);
		
	}

}
