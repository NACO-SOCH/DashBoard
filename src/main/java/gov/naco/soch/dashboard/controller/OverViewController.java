package gov.naco.soch.dashboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gov.naco.soch.dashboard.service.OverViewService;

@RestController
@RequestMapping("/overview")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class OverViewController {
	
	@Autowired
	OverViewService overViewService;
	
	@GetMapping("/loginRealTimeCount")
	public ResponseEntity<Integer> loginCount(){
		Integer resultCount;
		try {
			resultCount = overViewService.loginRealTimeCount();
		}catch(Exception e){
			return new  ResponseEntity<Integer>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Integer>(resultCount, HttpStatus.OK);
	}
	
	@GetMapping("/testRealTimeLoginCount")
	public ResponseEntity<Integer> testRealTimeLoginCount(){
		Integer resultCount;
		try {
			resultCount = overViewService.testRealTimeLoginCount();
		}catch(Exception e){
			return new  ResponseEntity<Integer>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Integer>(resultCount, HttpStatus.OK);
	}
	
	@GetMapping("/beneficiaryAddedRealTime")
	public ResponseEntity<Integer> beneficiaryAddedRealTime(){
		Integer resultCount;
		try {
			resultCount = overViewService.beneficiaryAddedRealTime();
		}catch(Exception e){
			return new  ResponseEntity<Integer>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Integer>(resultCount, HttpStatus.OK);
	}
	
	@GetMapping("/productWiseDispensation")
	public ResponseEntity<Integer> productWiseDispensation(){
		Integer resultCount;
		try {
			resultCount = overViewService.productWiseDispensation();
		}catch(Exception e){
			return new  ResponseEntity<Integer>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Integer>(resultCount, HttpStatus.OK);
	}
	
	@GetMapping("/beneficiaryWiseDispensation")
	public ResponseEntity<Integer> beneficiaryWiseDispensation(){
		Integer resultCount;
		try {
			resultCount = overViewService.beneficiaryWiseDispensation();
		}catch(Exception e){
			return new  ResponseEntity<Integer>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Integer>(resultCount, HttpStatus.OK);
	}
	
    @GetMapping("/artDispensationLast1Month")
    public ResponseEntity<Integer> artDispensationLast1Month() {
        try {
            Integer resultCount = overViewService.artDispensationLast1Month();
            return new ResponseEntity<>(resultCount, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/artDispensationStateLast1Month/{stateId}")
    public ResponseEntity<Integer> artDispensationStateLast1Month(@PathVariable Integer stateId) {
        try {
            Integer resultCount = overViewService.artDispensationStateLast1Month(stateId);
            return new ResponseEntity<>(resultCount, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/ictcTest1Month")
    public ResponseEntity<Integer> ictcTest1Month() {
        try {
            Integer resultCount = overViewService.ictcTest1Month();
            return new ResponseEntity<>(resultCount, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/ictcTestState1Month/{stateId}")
    public ResponseEntity<Integer> ictcTestState1Month(@PathVariable Integer stateId) {
        try {
            Integer resultCount = overViewService.ictcTestState1Month(stateId);
            return new ResponseEntity<>(resultCount, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/vlTest1Month")
    public ResponseEntity<Integer> vlTest1Month() {
        try {
            Integer resultCount = overViewService.vlTest1Month();
            return new ResponseEntity<>(resultCount, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/vlTestState1Month/{stateId}")
    public ResponseEntity<Integer> vlTestState1Month(@PathVariable Integer stateId) {
        try {
            Integer resultCount = overViewService.vlTestState1Month(stateId);
            return new ResponseEntity<>(resultCount, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/tiCorelast30Days")
    public ResponseEntity<Integer> getTiCoreLast30Days() {
    	Integer result;
    	try {
            result = overViewService.getTiCoreLast30Days();
            return new ResponseEntity<Integer>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tiBridgePopulation30Days")
    public ResponseEntity<Integer> getTiBridgePopulation30Days() {
    	Integer result;
        try {
            result = overViewService.getTiBridgePopulation30Days();
            return new ResponseEntity<Integer>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tiCorStateeLast30Days/{stateId}")
    public ResponseEntity<Integer> tiCorStateeLast30Days(@PathVariable Integer stateId) {
    	Integer result;
        try {
            result = overViewService.tiCorStateeLast30Days(stateId);
            return new ResponseEntity<Integer>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tiBridgePopulationState30Days/{stateId}")
    public ResponseEntity<Integer> tiBridgePopulationState30Days(@PathVariable Integer stateId) {
    	Integer result;
        try {
            result = overViewService.tiBridgePopulationState30Days(stateId);
            return new ResponseEntity<Integer>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
