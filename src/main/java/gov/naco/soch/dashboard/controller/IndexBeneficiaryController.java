package gov.naco.soch.dashboard.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import gov.naco.soch.dashboard.DTO.IndexBeneficiaryListDto;
import gov.naco.soch.dashboard.service.IndexBeneficiaryService;

@RestController
@RequestMapping("/index")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class IndexBeneficiaryController {
	
	@Autowired
	IndexBeneficiaryService indexBeneficiaryService;
	
	 @PostMapping("/saveBeneficiaryIndex")
	    public ResponseEntity<String> saveBeneficiaryIndex (@RequestBody AddIndexDTO addIndexDTO) {
	        try {
	        	indexBeneficiaryService.saveBeneficiaryIndex(addIndexDTO);
	            return new ResponseEntity<>("Person created successfully", HttpStatus.CREATED);
	        } catch (Exception e) {
	            return new ResponseEntity<>("Error creating person: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	 
	 @PostMapping("/saveBeneficiaryIndex/Screening")
	    public ResponseEntity<String> saveBeneficiaryIndexScreening (@RequestBody BeneficiaryIndexScreeningDTO beneficiaryIndexScreeningDTO) {
	        try {
	        	indexBeneficiaryService.saveBeneficiaryIndexScreening(beneficiaryIndexScreeningDTO);
	            return new ResponseEntity<>("Person created successfully", HttpStatus.CREATED);
	        } catch (Exception e) {
	            return new ResponseEntity<>("Error creating person: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	 
	 

	 @GetMapping("/beneficiary/list")
	 public List<IndexBeneficiaryListDto> getIndexBeneficiariesList(
	         @RequestParam(defaultValue = "0") Integer pageNumber,
	         @RequestParam(defaultValue = "100") Integer pageSize) {

	     return indexBeneficiaryService.findIndexBeneficiariesByFacilityId(pageNumber, pageSize).getContent();
	 }
	 
	 
	 
	 @GetMapping("/beneficiary/{Id}")
	 public List<BeneficiaryDTO> findIndexBeneficiariesById(@PathVariable("Id") Long beneficiaryId) {
	     return indexBeneficiaryService.findIndexBeneficiariesById(beneficiaryId);
	 }
	 
	 
	 @GetMapping("/indexScreeingListBySSK")
	 public List<BeneficiaryIndexDTO> indexScreeingListBySSK(){
		  return indexBeneficiaryService.indexScreeingListBySSK();
	 }

	 
	 
}
