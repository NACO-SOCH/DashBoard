package gov.naco.soch.dashboard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gov.naco.soch.dashboard.DTO.CoverageDTO;
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
	
	
	//-------------------------//
	
	@GetMapping("/totalTICoreCount")
    public ResponseEntity<Integer> getTotalTICoreCount() {
        try {
            Integer result = facilityService.getTotalTICoreCount();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/totalTICoreStateWiseCount")
    public ResponseEntity<List<FacilityDTO>> getTotalTICoreStateWiseCount() {
        try {
            List<FacilityDTO> result = facilityService.getTotalTICoreStateWiseCount();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/totalTIBridgeCount")
    public ResponseEntity<Integer> getTotalTIBridgeCount() {
        try {
            Integer result = facilityService.getTotalTIBridgeCount();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/totalTIBridgeStateWiseCount")
    public ResponseEntity<List<FacilityDTO>> getTotalTIBridgeStateWiseCount() {
        try {
            List<FacilityDTO> result = facilityService.getTotalTIBridgeStateWiseCount();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/totalOSTCount")
    public ResponseEntity<Integer> getTotalOSTCount() {
        try {
            Integer result = facilityService.getTotalOSTCount();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/totalOSTStateWiseCount")
    public ResponseEntity<List<FacilityDTO>> getTotalOSTStateWiseCount() {
        try {
            List<FacilityDTO> result = facilityService.getTotalOSTStateWiseCount();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/totalLWSCount")
    public ResponseEntity<Integer> getTotalLWSCount() {
        try {
            Integer result = facilityService.getTotalLWSCount();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/totalLWSStateWiseCount")
    public ResponseEntity<List<FacilityDTO>> getTotalLWSStateWiseCount() {
        try {
            List<FacilityDTO> result = facilityService.getTotalLWSStateWiseCount();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	
    @GetMapping("/facilityCoverage/{stateId}/{type}")
    public ResponseEntity<List<CoverageDTO>> facilityCoverage(@PathVariable Integer stateId,@PathVariable String type) {
        try {
            List<CoverageDTO> result = facilityService.facilityCoverage(stateId,type);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
}
