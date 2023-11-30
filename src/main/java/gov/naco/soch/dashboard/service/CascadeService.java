package gov.naco.soch.dashboard.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.naco.soch.dashboard.DTO.FacilityDTO;
import gov.naco.soch.repository.DashBoardCascadeRepo;
import gov.naco.soch.singelton.LoggerSingleton;

@Service
@Transactional
public class CascadeService {

	@Autowired
	DashBoardCascadeRepo dashBoardCascadeRepo;
	
	Logger logger = LoggerSingleton.getInstance().getLogger();
	
    public List<FacilityDTO> cascadeCoverage(Integer stateId) {
        List<Object[]> dashBoardOverviewList = dashBoardCascadeRepo.cascadeCoverage(stateId);
		  List<FacilityDTO> facilityDTOList = new ArrayList<>();
		  try {
		    for (Object[] row : dashBoardOverviewList) {
		        FacilityDTO facilityDTO = new FacilityDTO();
		        facilityDTO.setCount((Integer) row[0]);
		        facilityDTO.setName((String) row[1]);
		        facilityDTOList.add(facilityDTO);
		    }
		  }catch(Exception e) {
		    	logger.info(e.getMessage());
		    }
		    return facilityDTOList;

    }
	
}
