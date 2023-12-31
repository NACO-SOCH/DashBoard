package gov.naco.soch.dashboard.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigInteger;
import gov.naco.soch.dashboard.DTO.FacilityDTO;
import gov.naco.soch.repository.MprReportsRepository;

@Service
@Transactional
public class MprReportsService {

	@Autowired
	private MprReportsRepository mprReportsRepository;

	private static final Logger logger = Logger.getLogger(MprReportsService.class.getName());

	public Stream<Object[]> getIctcMpr(Integer facility_id, Integer mpr_month, Integer mpr_year,
			Integer ictc_state_id) {

		if (facility_id == null && ictc_state_id == null) {
			logger.info("method 1");
			return mprReportsRepository.getIctcMPRData(mpr_month, mpr_year).stream();
		} else if (facility_id != null) {
			logger.info("method 2");
			return mprReportsRepository.getIctcMPRDataName(mpr_month, mpr_year, facility_id).stream();
		} else {
			return mprReportsRepository.getIctcMPRDatastate(mpr_month, mpr_year, ictc_state_id).stream();
		}
	}

	public Stream<Object[]> getVLMPRData(Integer Lab_id, Integer mpr_month, Integer mpr_year, Integer state_id) {

		if (Lab_id == null && state_id == null) {
			logger.info("method 1");
			return mprReportsRepository.getVLMPRData(mpr_month, mpr_year).stream();
		} else if (Lab_id != null) {
			logger.info("method 2");
			return mprReportsRepository.getVLMPRDataName(mpr_month, mpr_year, Lab_id).stream();
		} else {
			return mprReportsRepository.getVLMPRDatastate(mpr_month, mpr_year, state_id).stream();
		}
	}
	
	
	
	public Stream<Object[]> getConsolidatedVLMPRData(Integer Lab_id, Integer mpr_month, Integer mpr_year, Integer state_id) {

		if (Lab_id == null && state_id == null) {
			logger.info("method 1");
			return mprReportsRepository.getConsolidatedVLMPRData(mpr_month, mpr_year).stream();
		} else if (Lab_id != null) {
			logger.info("method 2");
			return mprReportsRepository.getConsolidatedVLMPRDataName(mpr_month, mpr_year, Lab_id).stream();
		} else {
			return mprReportsRepository.getConsolidatedVLMPRDatastate(mpr_month, mpr_year, state_id).stream();
		}
	}

	public Stream<Object[]> getCD4MPRData(Integer Lab_id, Integer mpr_month, Integer mpr_year, Integer state_id) {

		if (Lab_id == null && state_id == null) {
			logger.info("method 1");
			return mprReportsRepository.getCD4MPRData(mpr_month, mpr_year).stream();
		} else if (Lab_id != null) {
			logger.info("method 2");
			return mprReportsRepository.getCD4MPRDataName(mpr_month, mpr_year, Lab_id).stream();
		} else {
			logger.info("method 3");
			return mprReportsRepository.getCD4MPRDatastate(mpr_month, mpr_year, state_id).stream();
		}
	}
	
	public Stream<Object[]> getConsolidatedCD4mpr(Integer Lab_id, Integer mpr_month, Integer mpr_year, Integer state_id) {

		if (Lab_id == null && state_id == null) {
			logger.info("method 1");
			return mprReportsRepository.getConsolidatedCD4MPRData(mpr_month, mpr_year).stream();
		} else if (Lab_id != null) {
			logger.info("method 2");
			return mprReportsRepository.getConsolidatedCD4MPRDataName(mpr_month, mpr_year, Lab_id).stream();
		} else {
			logger.info("method 3");
			return mprReportsRepository.getConsolidatedCD4MPRDatastate(mpr_month, mpr_year, state_id).stream();
		}
	}

	public Stream<Object[]> getEidMPRData(Integer Lab_id, Integer mpr_month, Integer mpr_year, Integer state_id) {

		if (Lab_id == null && state_id == null) {
			logger.info("method 1");
			return mprReportsRepository.getEidMPRData(mpr_month, mpr_year).stream();
		} else if (Lab_id != null) {
			logger.info("method 2");
			return mprReportsRepository.getEidMPRDataName(mpr_month, mpr_year, Lab_id).stream();
		} else {
			logger.info("method 3");
			return mprReportsRepository.getEidMPRDatastate(mpr_month, mpr_year, state_id).stream();
		}
	}

	public Stream<Object[]> getTiMPRData(Integer facility_id, Integer mpr_month, Integer mpr_year,
			Integer ti_state_id) {

		if (facility_id == null && ti_state_id == null) {
			// logger.info("method 1");
			return mprReportsRepository.getTiMPRData(mpr_month, mpr_year).stream();
// 	        logger.info(objStream.count() + " size");//
// 	        return objStream;
		} else if (facility_id != null) {
			// logger.info("method 2");
			return mprReportsRepository.getTiMPRDataName(mpr_month, mpr_year, facility_id).stream();
		} else {
			return mprReportsRepository.getTiMPRDatastate(mpr_month, mpr_year, ti_state_id).stream();
		}
	}

	public Stream<Object[]> getArtMPRData(Integer facility_id, Integer mpr_month, Integer mpr_year,
			Integer artc_state_id) {

		if (facility_id == null && artc_state_id == null) {
			// logger.info("method 1");
			return mprReportsRepository.getAllData(mpr_month, mpr_year).stream();
		} else if (facility_id != null) {
			// logger.info("method 2");
			return mprReportsRepository.getAllDataName(mpr_month, mpr_year, facility_id).stream();
		} else {
			return mprReportsRepository.getAllDatastate(mpr_month, mpr_year, artc_state_id).stream();
		}
	}

	public Stream<Object[]> getOstMpr(Integer facility_id, Integer mpr_month, Integer mpr_year, Integer ost_state_id) {

		if (facility_id == null && ost_state_id == null) {
			// logger.info("method 1");
			return mprReportsRepository.getOstMPRData(mpr_month, mpr_year).stream();
		} else if (facility_id != null) {
			// logger.info("method 2");
			return mprReportsRepository.getOstMPRDataName(mpr_month, mpr_year, facility_id).stream();
		} else {
			return mprReportsRepository.getOstMPRDatastate(mpr_month, mpr_year, ost_state_id).stream();
		}
	}
	
	 public int mprCount (Integer reportType, Long userid,Integer stateid) {
		 return mprReportsRepository.mprCount(reportType, userid, stateid);
	 }
	 
	 public int mprCountNational() {
		 return mprReportsRepository.mprCountNational();
	 }

	 public int mprCountStateWise (Integer stateid) {
		 return mprReportsRepository.mprCountStateWise(stateid);
	 }
	 
	 public List<FacilityDTO> offlineCountNational() {
		 List<Object[]> dashBoardOverviewList = mprReportsRepository.offlineCountNational();
		 List<FacilityDTO> facilityDTOList = new ArrayList<>();
			try {
			    for (Object[] row : dashBoardOverviewList) {
			        FacilityDTO facilityDTO = new FacilityDTO();
			        facilityDTO.setName((String) row[0]);
//			        facilityDTO.setCount( (Integer)row[1]);
//			        facilityDTOList.add(facilityDTO);
			        BigInteger count = (BigInteger) row[1];
		            facilityDTO.setCount(count.intValue());
			        facilityDTOList.add(facilityDTO);
			    }
			 }catch(Exception e) {
			  logger.info(e.getMessage());
			 }
		 return facilityDTOList;
	 }

	 //change
	 public List<FacilityDTO> offlineStateWise (Integer stateid) {
		 List<Object[]> dashBoardOverviewList = mprReportsRepository.offlineStateWise(stateid);
			List<FacilityDTO> facilityDTOList = new ArrayList<>();
			try {
			    for (Object[] row : dashBoardOverviewList) {
			        FacilityDTO facilityDTO = new FacilityDTO();
			        facilityDTO.setName((String) row[0]);
//			        facilityDTO.setCount( (Integer)row[1]);
			        BigInteger count = (BigInteger) row[1];
		            facilityDTO.setCount(count.intValue());
			        facilityDTOList.add(facilityDTO);
			    }
			 }catch(Exception e) {
			  logger.info(e.getMessage());
			 }
			    return facilityDTOList;
	 }
	 
	public List<FacilityDTO> reportDownloadTypeWiseCount(){
			
		List<Object[]> dashBoardOverviewList = mprReportsRepository.reportDownloadTypeWiseCount();
		List<FacilityDTO> facilityDTOList = new ArrayList<>();
			try {
			    for (Object[] row : dashBoardOverviewList) {
			        FacilityDTO facilityDTO = new FacilityDTO();
			        facilityDTO.setName((String) row[0]);
			        facilityDTO.setDownloadCount( ((BigInteger) row[1]).longValue());
			        facilityDTOList.add(facilityDTO);
			    }
			 }catch(Exception e) {
			  logger.info(e.getMessage());
			 }
			    return facilityDTOList;
	  }
	
	public List<FacilityDTO> reportDownloadTypeWiseCountState(Integer stateid){
	    List<Object[]> dashBoardOverviewList = mprReportsRepository.reportDownloadTypeWiseCountState(stateid);
	    List<FacilityDTO> facilityDTOList = new ArrayList<>();
	    
	    try {
	    	for (Object[] row : dashBoardOverviewList) {
	            FacilityDTO facilityDTO = new FacilityDTO();

	            // Convert Object to String
	            facilityDTO.setName(row[0] != null ? row[0].toString() : null);

	            // Access the correct index for download_count (column 1)
	            facilityDTO.setDownloadCount( ((BigInteger) row[1]).longValue());;
	            
	            facilityDTOList.add(facilityDTO);
	        }
	    } catch (Exception e) {
	        logger.info(e.getMessage());
	    }
	    
	    return facilityDTOList;
	}

	
	
	
	public List<FacilityDTO> reportDownloadTypeWiseCountStateOffline(){
		
		List<Object[]> dashBoardOverviewList = mprReportsRepository.reportDownloadTypeWiseCountStateOffline();
		List<FacilityDTO> facilityDTOList = new ArrayList<>();
			try {
			    for (Object[] row : dashBoardOverviewList) {
			        FacilityDTO facilityDTO = new FacilityDTO();
			        facilityDTO.setStateName((String) row[0]);
			        facilityDTO.setDownloadCount( ((BigInteger) row[1]).longValue());
			        facilityDTOList.add(facilityDTO);
			    }
			 }catch(Exception e) {
			  logger.info(e.getMessage());
			 }
			    return facilityDTOList;
	  }
}
