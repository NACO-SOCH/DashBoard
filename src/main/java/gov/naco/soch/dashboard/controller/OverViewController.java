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

import gov.naco.soch.dashboard.DTO.FacilityDTO;
import gov.naco.soch.dashboard.DTO.GeoMappingDTO;
import gov.naco.soch.dashboard.service.OverViewService;

@RestController
@RequestMapping("/overview")
@CrossOrigin(origins = { "http://localhost:4200", "*" }, allowedHeaders = "*")
public class OverViewController {

	@Autowired
	OverViewService overViewService;

	@GetMapping("/loginRealTimeCount")
	public ResponseEntity<Integer> loginCount() {
		Integer resultCount;
		try {
			resultCount = overViewService.loginRealTimeCount();
		} catch (Exception e) {
			return new ResponseEntity<Integer>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Integer>(resultCount, HttpStatus.OK);
	}

	@GetMapping("/testRealTimeLoginCount")
	public ResponseEntity<Integer> testRealTimeLoginCount() {
		Integer resultCount;
		try {
			resultCount = overViewService.testRealTimeLoginCount();
		} catch (Exception e) {
			return new ResponseEntity<Integer>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Integer>(resultCount, HttpStatus.OK);
	}
	
	
	@GetMapping("/beneficiaryAddedRealTime")
	public ResponseEntity<Integer> beneficiaryAddedRealTime() {
		Integer resultCount;
		try {
			resultCount = overViewService.beneficiaryAddedRealTime();
		} catch (Exception e) {
			return new ResponseEntity<Integer>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Integer>(resultCount, HttpStatus.OK);
	}

	@GetMapping("/productWiseDispensation")
	public ResponseEntity<Integer> productWiseDispensation() {
		Integer resultCount;
		try {
			resultCount = overViewService.productWiseDispensation();
		} catch (Exception e) {
			return new ResponseEntity<Integer>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Integer>(resultCount, HttpStatus.OK);
	}

	@GetMapping("/beneficiaryWiseDispensation")
	public ResponseEntity<Integer> beneficiaryWiseDispensation() {
		Integer resultCount;
		try {
			resultCount = overViewService.beneficiaryWiseDispensation();
		} catch (Exception e) {
			return new ResponseEntity<Integer>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Integer>(resultCount, HttpStatus.OK);
	}

	// -------------//
	@GetMapping("/artDispensationCurrentFinancialYear")
	public ResponseEntity<Integer> artDispensationCurrentFinancialYear() {
		try {
			Integer resultCount = overViewService.artDispensationCurrentFinancialYear();
			return new ResponseEntity<>(resultCount, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/artDispensationStateCurrentFinancialYear/{stateId}")
	public ResponseEntity<Integer> artDispensationStateCurrentFinancialYear(@PathVariable Integer stateId) {
		try {
			Integer resultCount = overViewService.artDispensationStateCurrentFinancialYear(stateId);
			return new ResponseEntity<>(resultCount, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/ictcTestCurrentFinancialYear")
	public ResponseEntity<Integer> ictcTestCurrentFinancialYear() {
		try {
			Integer resultCount = overViewService.ictcTestCurrentFinancialYear();
			return new ResponseEntity<>(resultCount, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/ictcTestStateCurrentFinancialYear/{stateId}")
	public ResponseEntity<Integer> ictcTestStateCurrentFinancialYear(@PathVariable Integer stateId) {
		try {
			Integer resultCount = overViewService.ictcTestStateCurrentFinancialYear(stateId);
			return new ResponseEntity<>(resultCount, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/vlTestCurrentFinancialYear")
	public ResponseEntity<Integer> vlTestCurrentFinancialYear() {
		try {
			Integer resultCount = overViewService.vlTestCurrentFinancialYear();
			return new ResponseEntity<>(resultCount, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/vlTestStateCurrentFinancialYear/{stateId}")
	public ResponseEntity<Integer> vlTestStateCurrentFinancialYear(@PathVariable Integer stateId) {
		try {
			Integer resultCount = overViewService.vlTestStateCurrentFinancialYear(stateId);
			return new ResponseEntity<>(resultCount, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/tiCoreCurrentFinancialYear")
	public ResponseEntity<Integer> getTiCoreCurrentFinancialYear() {
		Integer result;
		try {
			result = overViewService.getTiCoreCurrentFinancialYear();
			return new ResponseEntity<Integer>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/tiBridgePopulationCurrentFinancialYear")
	public ResponseEntity<Integer> getTiBridgePopulationCurrentFinancialYear() {
		Integer result;
		try {
			result = overViewService.getTiBridgePopulationCurrentFinancialYear();
			return new ResponseEntity<Integer>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/tiCorStateCurrentFinancialYear/{stateId}")
	public ResponseEntity<Integer> tiCoreStateCurrentFinancialYear(@PathVariable Integer stateId) {
		Integer result;
		try {
			result = overViewService.tiCoreStateCurrentFinancialYear(stateId);
			return new ResponseEntity<Integer>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/tiBridgePopulationStateCurrentFinancialYear/{stateId}")
	public ResponseEntity<Integer> tiBridgePopulationStateCurrentFinancialYear(@PathVariable Integer stateId) {
		Integer result;
		try {
			result = overViewService.tiBridgePopulationStateCurrentFinancialYear(stateId);
			return new ResponseEntity<Integer>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/loginDataStateWise15Days")
	public ResponseEntity<List<FacilityDTO>> loginDataStateWise15Days() {
		List<FacilityDTO> result;
		try {
			result = overViewService.loginDataStateWise15Days();
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<FacilityDTO>>(result, HttpStatus.OK);
	}

	@GetMapping("/vlRealTimeTesting")
	public ResponseEntity<Integer> vlRealTimeTesting() {
		Integer result;
		try {
			result = overViewService.vlRealTimeTesting();
			return new ResponseEntity<Integer>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/dsrcCountCurrentFinancialYear")
	public ResponseEntity<Integer> dsrcCountCurrentFinancialYear() {
		Integer result;
		try {
			result = overViewService.dsrcCountCurrentFinancialYear();
			return new ResponseEntity<Integer>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/dsrcCountStateCurrentFinancialYear/{stateId}")
	public ResponseEntity<Integer> dsrcCountStateCurrentFinancialYear(@PathVariable Integer stateId) {
		Integer result;
		try {
			result = overViewService.dsrcCountStateCurrentFinancialYear(stateId);
			return new ResponseEntity<Integer>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/stateWiseActiveUserAndLoginCount")
	public ResponseEntity<List<FacilityDTO>> stateWiseActiveUserAndLoginCount() {
		List<FacilityDTO> result;
		try {
			result = overViewService.stateWiseActiveUserAndLoginCount();
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<FacilityDTO>>(result, HttpStatus.OK);
	}

	@GetMapping("/artDispensationStateWise/{stateId}")
	public ResponseEntity<Integer> artDispensationStateWise(@PathVariable Integer stateId) {
		Integer result;
		try {
			result = overViewService.artDispensationStateWise(stateId);
			return new ResponseEntity<Integer>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/vlTestingStateWise/{stateId}")
	public ResponseEntity<Integer> vlTestingStateWise(@PathVariable Integer stateId) {
		Integer result;
		try {
			result = overViewService.vlTestingStateWise(stateId);
			return new ResponseEntity<Integer>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/hivTestRealTime/{stateId}")
	public ResponseEntity<Integer> hivTestRealTime(@PathVariable Integer stateId) {
		Integer result;
		try {
			result = overViewService.hivTestRealTime(stateId);
			return new ResponseEntity<Integer>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/realTimeUserViaState/{stateId}")
	public ResponseEntity<Integer> realTimeUserViaState(@PathVariable Integer stateId) {
		Integer result;
		try {
			result = overViewService.realTimeUserViaState(stateId);
			return new ResponseEntity<Integer>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/loginUsage/{stateId}")
	public ResponseEntity<List<FacilityDTO>> loginUsage(@PathVariable Integer stateId) {
		List<FacilityDTO> result;
		try {
			result = overViewService.loginUsage(stateId);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<FacilityDTO>>(result, HttpStatus.OK);
	}
	
	@GetMapping("/geoMapping")
	public ResponseEntity<List<GeoMappingDTO>> geoMapping() {
		List<GeoMappingDTO> result;
		try {
			result = overViewService.geoMapping();
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<GeoMappingDTO>>(result, HttpStatus.OK);
	}
	
	@GetMapping("/geoMappingState/{stateId}")
	public ResponseEntity<List<GeoMappingDTO>> geoMappingState(@PathVariable Integer stateId) {
		List<GeoMappingDTO> result;
		try {
			result = overViewService.geoMappingState(stateId);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<GeoMappingDTO>>(result, HttpStatus.OK);
	}

}
