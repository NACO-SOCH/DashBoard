package gov.naco.soch.dashboard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gov.naco.soch.dashboard.DTO.FacilityDTO;
import gov.naco.soch.dashboard.service.FacilityService;

@RestController
@RequestMapping("/facility")
@CrossOrigin(origins = {"http://localhost:4200","*"}, allowedHeaders = "*")
public class FacilityController {
	
	@Autowired
	FacilityService facilityService;
	
	@GetMapping("/facilityIdCount")
	public ResponseEntity<List<FacilityDTO>> facilityIdCount(){
		List<FacilityDTO> result;
		try {
			result = facilityService.facilityIdCount();
		}catch(Exception e){
			return new  ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<FacilityDTO>>(result, HttpStatus.OK);
	}
	
	@GetMapping("/stateCount")
	public ResponseEntity<List<FacilityDTO>> stateCount(){
		List<FacilityDTO> result;
		try {
			result = facilityService.stateCount();
		}catch(Exception e){
			return new  ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<FacilityDTO>>(result, HttpStatus.OK);
	}
	
	@GetMapping("/stateICTCCount")
	public ResponseEntity<List<FacilityDTO>> stateICTCCount(){
		List<FacilityDTO> result;
		try {
			result = facilityService.stateICTCCount();
		}catch(Exception e){
			return new  ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<FacilityDTO>>(result, HttpStatus.OK);
	}
	
	@GetMapping("/facilityUserLoginData15Days")
	public ResponseEntity<List<FacilityDTO>> facilityUserLoginData15Days(){
		List<FacilityDTO> result;
		try {
			result = facilityService.facilityUserLoginData15Days();
		}catch(Exception e){
			return new  ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<FacilityDTO>>(result, HttpStatus.OK);
	}
	
	
}
