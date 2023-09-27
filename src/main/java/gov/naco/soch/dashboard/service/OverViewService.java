package gov.naco.soch.dashboard.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.Logger;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gov.naco.soch.dashboard.DTO.FacilityDTO;
import gov.naco.soch.repository.DashBoardOverViewRepo;
import gov.naco.soch.singelton.LoggerSingleton;

@Service
@Transactional
public class OverViewService {

	@Autowired
	DashBoardOverViewRepo dashBoardOverViewRepo;
	
    Logger logger = LoggerSingleton.getInstance().getLogger();
	
	
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
	
	public Integer artDispensationCurrentFinancialYear() {
		return dashBoardOverViewRepo.artDispensationCurrentFinancialYear();
	}
	
	public Integer artDispensationStateCurrentFinancialYear(Integer stateId) {
		return dashBoardOverViewRepo.artDispensationStateCurrentFinancialYear(stateId);
	}
	
	public Integer ictcTestCurrentFinancialYear() {
		return dashBoardOverViewRepo.ictcTestCurrentFinancialYear();
	}
	
	public Integer ictcTestStateCurrentFinancialYear(Integer stateId) {
		return dashBoardOverViewRepo.ictcTestStateCurrentFinancialYear(stateId);
	}
	
	public Integer vlTestCurrentFinancialYear() {
		return dashBoardOverViewRepo.vlTestCurrentFinancialYear();
	}
	
	public Integer vlTestStateCurrentFinancialYear(Integer stateId) {
		return dashBoardOverViewRepo.vlTestStateCurrentFinancialYear(stateId);
	}
	
	
	public Integer getTiCoreCurrentFinancialYear() {
		 return dashBoardOverViewRepo.tiCoreLastCurrentFinancialYear();
	}

	public Integer getTiBridgePopulationCurrentFinancialYear() {
		return dashBoardOverViewRepo.tiBridgePopulationCurrentFinancialYear();
	}
	
	public Integer tiCoreStateCurrentFinancialYear(Integer stateId) {
		return dashBoardOverViewRepo.tiCoreStateCurrentFinancialYear(stateId);
	}
	
	public Integer tiBridgePopulationStateCurrentFinancialYear(Integer stateId) {
		return dashBoardOverViewRepo.tiBridgePopulationStateCurrentFinancialYear(stateId);	
	}
	
	public List<FacilityDTO> loginDataStateWise15Days(){
		
		  List<Object[]> dashBoardOverviewList = dashBoardOverViewRepo.loginDataStateWise15Days();
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
	
	public Integer vlRealTimeTesting() {
		return dashBoardOverViewRepo.vlRealTimeTesting();
	}
	
	public Integer dsrcCountCurrentFinancialYear() {
		return dashBoardOverViewRepo.dsrcCountCurrentFinancialYear();
	}
	
	public Integer dsrcCountStateCurrentFinancialYear(Integer stateId) {
		return dashBoardOverViewRepo.dsrcCountStateCurrentFinancialYear(stateId);
	}
	
	public List<FacilityDTO> stateWiseActiveUserAndLoginCount(){
		
		  List<Object[]> dashBoardOverviewList = dashBoardOverViewRepo.stateWiseActiveUserAndLoginCount();
		  List<FacilityDTO> facilityDTOList = new ArrayList<>();
		  try {
		    for (Object[] row : dashBoardOverviewList) {
		        FacilityDTO facilityDTO = new FacilityDTO();
		        facilityDTO.setName((String) row[0]);
		        facilityDTO.setActiveUsers((Integer) row[1]);
		        facilityDTO.setCount((Integer) row[2]);
		        facilityDTOList.add(facilityDTO);
		    }
		    }catch(Exception e) {
		    	logger.info(e.getMessage());
		    }

		    return facilityDTOList;
	}
	
	public Integer artDispensationStateWise(Integer stateId) {
		return dashBoardOverViewRepo.artDispensationStateWise(stateId);
	}
	
	public Integer vlTestingStateWise(Integer stateId) {
		return dashBoardOverViewRepo.artDispensationStateWise(stateId);
	}
	
	public Integer hivTestRealTime(Integer stateId) {
		return dashBoardOverViewRepo.hivTestRealTime(stateId);
	}
	
	public Integer realTimeUserViaState(Integer stateId) {
		return dashBoardOverViewRepo.realTimeUserViaState(stateId);
	}
	
//	public List<FacilityDTO> loginUsage(Integer stateId) {
//		return dashBoardOverViewRepo.loginUsage(stateId);
//	}
	
	public List<FacilityDTO> loginUsage(Integer stateId){
		
		  List<Object[]> dashBoardOverviewList = dashBoardOverViewRepo.loginUsage(stateId);
		  List<FacilityDTO> facilityDTOList = new ArrayList<>();
		  try {
		    for (Object[] row : dashBoardOverviewList) {
		        FacilityDTO facilityDTO = new FacilityDTO();
		        facilityDTO.setDateTime((Date) row[0]);
		        facilityDTO.setName((String) row[1]);
		        facilityDTO.setCount((Integer) row[2]); 
		        facilityDTOList.add(facilityDTO);
		    }
		  }catch(Exception e) {
		    	logger.info(e.getMessage());
		    }
		    return facilityDTOList;
	}
	
}
