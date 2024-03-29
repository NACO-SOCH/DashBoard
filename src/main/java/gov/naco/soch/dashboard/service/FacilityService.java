package gov.naco.soch.dashboard.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.naco.soch.dashboard.DTO.CoverageDTO;
import gov.naco.soch.dashboard.DTO.FacilityDTO;
import gov.naco.soch.repository.DashBoardFacilityRepo;
import gov.naco.soch.singelton.LoggerSingleton;

@Service
@Transactional
public class FacilityService {
	
	@Autowired
	DashBoardFacilityRepo dashBoardFacilityRepo;

	Logger logger = LoggerSingleton.getInstance().getLogger();
	
	public List<FacilityDTO> facilityIdCount(){
		
		  List<Object[]> dashBoardOverviewList = dashBoardFacilityRepo.facilityIdCount();
		  List<FacilityDTO> facilityDTOList = new ArrayList<>();
		  
		  try {
			  for (Object[] row : dashBoardOverviewList) {
			      FacilityDTO facilityDTO = new FacilityDTO();
			      facilityDTO.setFacilityTypeId((Integer) row[0]);
			      facilityDTO.setCount((Integer) row[1]); 
			      facilityDTOList.add(facilityDTO);
			  }
		  }catch(Exception e) {
			  logger.info(e.getMessage());
		  }
		  return facilityDTOList;
	}
	
	public List<FacilityDTO> stateCount(){
		
		  List<Object[]> dashBoardOverviewList = dashBoardFacilityRepo.stateCount();
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
	
	public List<FacilityDTO> stateICTCCount(){
		
		  List<Object[]> dashBoardOverviewList = dashBoardFacilityRepo.stateICTCCount();
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
	
	
	public List<FacilityDTO> facilityUserLoginData15Days(){
		
		  List<Object[]> dashBoardOverviewList = dashBoardFacilityRepo.facilityUserLoginData15Days();
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
	
	public Integer getTotalTICoreCount() {
        return dashBoardFacilityRepo.getTotalTICoreCount();
    }

    public List<FacilityDTO> getTotalTICoreStateWiseCount() {
        List<Object[]> dashBoardOverviewList = dashBoardFacilityRepo.getTotalTICoreStateWiseCount();
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

    public Integer getTotalTIBridgeCount() {
        return dashBoardFacilityRepo.getTotalTIBridgeCount();
    }

    public List<FacilityDTO> getTotalTIBridgeStateWiseCount() {
        List<Object[]> dashBoardOverviewList = dashBoardFacilityRepo.getTotalOSTStateWiseCount();
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

    public Integer getTotalOSTCount() {
        return dashBoardFacilityRepo.getTotalOSTCount();
    }

    public List<FacilityDTO> getTotalOSTStateWiseCount() {
        List<Object[]> dashBoardOverviewList = dashBoardFacilityRepo.getTotalOSTStateWiseCount();
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

    public Integer getTotalLWSCount() {
        return dashBoardFacilityRepo.getTotalLWSCount();
    }

    public List<FacilityDTO> getTotalLWSStateWiseCount() {
        List<Object[]> dashBoardOverviewList = dashBoardFacilityRepo.getTotalLWSStateWiseCount();
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
    
    public List<CoverageDTO> facilityCoverage(Integer stateId, String type) {
        List<Object[]> dashBoardOverviewList = dashBoardFacilityRepo.facilityCoverage(stateId,type);
		  List<CoverageDTO> facilityDTOList = new ArrayList<>();
		  try {
		    for (Object[] row : dashBoardOverviewList) {
		    	CoverageDTO facilityDTO = new CoverageDTO();
		        facilityDTO.setYear((Integer) row[0]);
		        facilityDTO.setMonth((Integer) row[1]);
		        facilityDTO.setCoverage1((String) row[2]);
		        facilityDTO.setCoverage2((String) row[3]);
		        facilityDTO.setCoverage3((String) row[4]);
		        facilityDTOList.add(facilityDTO);
		    }
		  }catch(Exception e) {
		    	logger.info(e.getMessage());
		    }
		    return facilityDTOList;

    }
    
}
