package gov.naco.soch.dashboard.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gov.naco.soch.repository.ReportsRepository;

@Service
@Transactional
public class ReportsService {
	@Autowired
	private ReportsRepository reportsRepository;
	
	public List<Object[]>getLoginCount(Integer stateId, Date startDate, Date endDate, Integer facilityId, Integer facilityTypeId, Integer DistrictId) {
        if (facilityId == null && facilityTypeId == null && DistrictId == null) {
            return reportsRepository.getLoginCount(stateId, startDate, endDate);
        }else if (stateId == null && facilityId == null && facilityTypeId != null && DistrictId == null) {
            return reportsRepository.getLoginCountState(startDate, endDate, facilityTypeId);
        }else if (facilityId == null && facilityTypeId != null && DistrictId == null) {
            return reportsRepository.getLoginCountfac(stateId, startDate, endDate, facilityTypeId);
        } else if (facilityId == null && facilityTypeId == null && DistrictId != null) {
            return reportsRepository.getLoginCountWithDistrict(stateId, startDate, endDate, DistrictId);
        } else if (facilityId == null && facilityTypeId != null && DistrictId != null) {
            return reportsRepository.getLoginCountFacAndDistrict(stateId, startDate, endDate, facilityTypeId, DistrictId);
        } else {
            return reportsRepository.getLoginCountallfac(stateId, startDate, endDate, facilityId, facilityTypeId, DistrictId);
            }
        }

	public List<Object[]>getLoginCountState(Date startDate, Date endDate) {
		return reportsRepository.getLoginCountallstates( startDate, endDate);
        }
	
	
	public List<Object[]>getFacilities(Long stateId, Long facilityTypeId, Long DistrictId) {
		if (DistrictId != null && facilityTypeId != null) {
			return reportsRepository.getFacilitiesByDistrict(stateId, facilityTypeId, DistrictId);
	     } else if (facilityTypeId != null) {
	    	 return reportsRepository.getFacilitiesByStateAndType(stateId, facilityTypeId);
		 } else if (DistrictId != null) {
			return reportsRepository.getFacilitiesByStateAndDistrict(stateId, DistrictId);
		} else {
			return reportsRepository.getFacilitiesByState(stateId);
		}
	}
	
	public Stream<Object[]>getLoginReport(Date startDate, Date endDate) {
	   return reportsRepository.getLoginReport(startDate, endDate).stream();
		
		}
	public Stream<Object[]>getLoginReportRole(Integer stateId, Date startDate, Date endDate) {
		if (stateId == null) {
			return reportsRepository.getLoginReportRoleAll(startDate, endDate).stream();		   			
			}
		else {
			return reportsRepository.getLoginReportRole(stateId, startDate, endDate).stream();
		}
	}
	
	public Stream<Object[]>getLoginReportSacs(Date startDate, Date endDate) {
		   return reportsRepository.getLoginReportSacs(startDate, endDate).stream();
			
			}
	
	public Stream<Object[]>getFacilityLine(Integer stateId) {
			return reportsRepository.getFacilityLine(stateId).stream();
		}
	
	public Stream<Object[]>getMasterLine(Integer stateId) {
		return reportsRepository.getMasterLine(stateId).stream();
	}
	
	public Stream<Object[]>getdispensationReport(Integer facilityId, Date startDate, Date endDate) {
		   return reportsRepository.getdispensationReport(facilityId, startDate, endDate).stream();			
	}

}
