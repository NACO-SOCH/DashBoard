package gov.naco.soch.dashboard.service;

import java.util.ArrayList;
import java.util.List;

import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.naco.soch.dashboard.DTO.FacilityDTO;
import gov.naco.soch.repository.DashBoardOverViewRepo;

@Service
@Transactional
public class OverViewService {

	@Autowired
	DashBoardOverViewRepo dashBoardOverViewRepo;
	

	private static final Logger logger = Logger.getLogger(OverViewService.class.getName());
	
	
	public Integer loginRealTimeCount() {  
		logger.info("DashBoardService Class -- Login Real Time Count");
		return dashBoardOverViewRepo.loginRealTimeCount();
	}
	
	public Integer testRealTimeLoginCount() {
		return dashBoardOverViewRepo.testingRealTimeCount();
	}
	
	public Integer beneficiaryAddedRealTime() {
		return dashBoardOverViewRepo.beneficiaryAddedRealTime();
	}
	
	public Integer productWiseDispensation() {
		return dashBoardOverViewRepo.productWiseDispensation();
	}
	
	public Integer beneficiaryWiseDispensation() {
		return dashBoardOverViewRepo.beneficiaryWiseDispensation();
	}
	
	public Integer artDispensationLast1Month() {
		return dashBoardOverViewRepo.artDispensationLast1Month();
	}
	
	public Integer artDispensationStateLast1Month(Integer stateId) {
		return dashBoardOverViewRepo.artDispensationStateLast1Month(stateId);
	}
	
	public Integer ictcTest1Month() {
		return dashBoardOverViewRepo.ictcTest1Month();
	}
	
	public Integer ictcTestState1Month(Integer stateId) {
		return dashBoardOverViewRepo.ictcTestState1Month(stateId);
	}
	
	public Integer vlTest1Month() {
		return dashBoardOverViewRepo.vlTest30Days();
	}
	
	public Integer vlTestState1Month(Integer stateId) {
		return dashBoardOverViewRepo.vlTestState30Days(stateId);
	}
	
	
	public Integer getTiCoreLast30Days() {
		 return dashBoardOverViewRepo.tiCoreLast30Days();
	}

	public Integer getTiBridgePopulation30Days() {
		return dashBoardOverViewRepo.tiBridgePopulation30Days();
		
	}
	
	public Integer tiCorStateeLast30Days(Integer stateId) {
		return dashBoardOverViewRepo.tiCorStateeLast30Days(stateId);
		
	}
	
	public Integer tiBridgePopulationState30Days(Integer stateId) {
		return dashBoardOverViewRepo.tiBridgePopulationState30Days(stateId);
		
	}
	
	public List<FacilityDTO> loginDataStateWise30Days(){
		
		  List<Object[]> dashBoardOverviewList = dashBoardOverViewRepo.loginDataStateWise30Days();
		  List<FacilityDTO> facilityDTOList = new ArrayList<>();
		  try {
		    for (Object[] row : dashBoardOverviewList) {
		        FacilityDTO facilityDTO = new FacilityDTO();
		        facilityDTO.setName((String) row[0]);
		        facilityDTO.setCount((Integer) row[1]);
		        facilityDTOList.add(facilityDTO);
		    }
		    }catch(Exception e) {
		    	logger.info(e.getMessage());
		    }

		    return facilityDTOList;
	}
	
}
