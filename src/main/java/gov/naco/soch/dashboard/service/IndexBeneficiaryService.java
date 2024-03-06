package gov.naco.soch.dashboard.service;

import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import gov.naco.soch.dashboard.DTO.AddIndexDTO;
import gov.naco.soch.dashboard.DTO.BeneficiaryDTO;
import gov.naco.soch.dashboard.DTO.BeneficiaryIndexDTO;
import gov.naco.soch.dashboard.DTO.BeneficiaryIndexScreeningDTO;
import gov.naco.soch.dashboard.DTO.IndexBeneficiaryListDto;
import gov.naco.soch.repository.IndexTestingRepository;
import gov.naco.soch.singelton.LoggerSingleton;
import gov.naco.soch.util.UserUtils;

@Service
@Transactional
public class IndexBeneficiaryService {
	
	@Autowired
	IndexTestingRepository indexTestingRepository;

	  Logger logger = LoggerSingleton.getInstance().getLogger();
	  
	public void saveBeneficiaryIndex(AddIndexDTO addIndexDTO) {
		
		indexTestingRepository.saveBeneficiaryIndex(
		            addIndexDTO.getFirstName(),
		            addIndexDTO.getMiddleName(),
		            addIndexDTO.getLastName(),
		            addIndexDTO.getSex(),
		            addIndexDTO.getBirthDate(),
		            addIndexDTO.getAge(),
		            addIndexDTO.getPhoneNo(),
		            addIndexDTO.getAddressLine1(),
		            addIndexDTO.getAddressLine2(),
		            addIndexDTO.getStateId(),
		            addIndexDTO.getDistrictId(),
		            addIndexDTO.getIndexTypeId(),
		            addIndexDTO.getBeneficiaryId(),
		            addIndexDTO.getPincode()
		        );
	}
	
	public void saveBeneficiaryIndexScreening(BeneficiaryIndexScreeningDTO beneficiaryIndexScreeningDTO) {

		Long UserId = UserUtils.getLoggedInUserDetails().getUserId();

		logger.info(beneficiaryIndexScreeningDTO.getScreeningDate() + "date");
		if ( (beneficiaryIndexScreeningDTO.getScreeningDate() != null || beneficiaryIndexScreeningDTO.getHivScreeningResultId() != null ) && beneficiaryIndexScreeningDTO.getOutReferralTo() != null ) {
			logger.info("dd");
			indexTestingRepository.saveBeneficiaryIndexScreening(
					beneficiaryIndexScreeningDTO.getSskid(),
					beneficiaryIndexScreeningDTO.getScreeningDate(),
					beneficiaryIndexScreeningDTO.getHivScreeningResultId(),
					beneficiaryIndexScreeningDTO.getIndexScreeningStatusId(),
					beneficiaryIndexScreeningDTO.getOutReferralTo(), 
					beneficiaryIndexScreeningDTO.getRemarks(), 
					UserId,
					UserId
					);
		}else if(beneficiaryIndexScreeningDTO.getOutReferralTo() == null){
			logger.info("ff");
			
			indexTestingRepository.saveBeneficiaryIndexScreeningNonReactive(
					beneficiaryIndexScreeningDTO.getSskid(),
					beneficiaryIndexScreeningDTO.getScreeningDate(),
					beneficiaryIndexScreeningDTO.getHivScreeningResultId(),
					beneficiaryIndexScreeningDTO.getIndexScreeningStatusId(),
					beneficiaryIndexScreeningDTO.getRemarks(), 
					UserId,
					UserId
					);
		} 
		else {
			logger.info("ee");
			indexTestingRepository.saveBeneficiaryIndexScreeningwithoutdate(
					beneficiaryIndexScreeningDTO.getSskid(),
					beneficiaryIndexScreeningDTO.getIndexScreeningStatusId(), 
					beneficiaryIndexScreeningDTO.getRemarks(), 
					UserId,
					UserId
					);
		} 
	}

	
	public Page<IndexBeneficiaryListDto> findIndexBeneficiariesByFacilityId(Integer pageNumber, Integer pageSize) {
	    Long facilityId = UserUtils.getLoggedInUserDetails().getFacilityId();
	    Pageable pageable = PageRequest.of(pageNumber, pageSize);

	    Page<Object[]> pageResult = indexTestingRepository.findBeneficiaryByFacilityId(facilityId, pageable);

	    List<IndexBeneficiaryListDto> resultList = new ArrayList<>();

	    for (Object[] row : pageResult) {
	        IndexBeneficiaryListDto beneficiaryDto = new IndexBeneficiaryListDto();

	        beneficiaryDto.setId(((Number) row[0]).longValue());
	        beneficiaryDto.setUid((String) row[1]);
	        beneficiaryDto.setBeneficiaryName((String) row[2]);
	        beneficiaryDto.setGenderName((String) row[3]);
	        beneficiaryDto.setArtNumber((String) row[4]);
	        beneficiaryDto.setTotalIndexCount(((BigInteger) row[5]).longValue());
	        beneficiaryDto.setIndexReferredToICTC(((BigInteger) row[6]).longValue());
	        beneficiaryDto.setIndexAlreadyContacted(((BigInteger) row[7]).longValue());

	        resultList.add(beneficiaryDto);
	    }

	    return new PageImpl<>(resultList, pageable, pageResult.getTotalElements());
	}
	
	
	public List<BeneficiaryDTO> findIndexBeneficiariesById(Long beneficiaryId) {
		List<Object[]> pageResult = indexTestingRepository.findBeneficiaryById(beneficiaryId);

		List<BeneficiaryDTO> resultList = new ArrayList<>();

		for (Object[] row : pageResult) {
			BeneficiaryDTO beneficiaryDTO = new BeneficiaryDTO();

			beneficiaryDTO.setSskid(((Integer) row[0]).longValue());
			beneficiaryDTO.setFirstName((String) row[1]);
			beneficiaryDTO.setMiddleName((String) row[2]);
			beneficiaryDTO.setLastName((String) row[3]);
			beneficiaryDTO.setPhoneNo((String) row[4]);
			beneficiaryDTO.setScreeningDate((Date) row[5]);
			beneficiaryDTO.setStatus((String) row[6]);

			resultList.add(beneficiaryDTO);
		}

		return resultList;
	}
	
	

	public List<BeneficiaryDTO> findBeneficiaryBySearch(Long sskid) {
		List<Object[]> pageResult = indexTestingRepository.findBeneficiaryBySearch(sskid);

		List<BeneficiaryDTO> resultList = new ArrayList<>();

		for (Object[] row : pageResult) {
			BeneficiaryDTO beneficiaryDTO = new BeneficiaryDTO();

			beneficiaryDTO.setSskid(((Integer) row[0]).longValue());
			beneficiaryDTO.setFirstName((String) row[1]);
			beneficiaryDTO.setMiddleName((String) row[2]);
			beneficiaryDTO.setLastName((String) row[3]);
			beneficiaryDTO.setPhoneNo((String) row[4]);
			beneficiaryDTO.setScreeningDate((Date) row[5]);
			beneficiaryDTO.setStatus((String) row[6]);

			resultList.add(beneficiaryDTO);
		}

		return resultList;
	}
	
	

	public List<BeneficiaryIndexDTO> indexScreeingListBySSK(Long sskid) {
		List<Object[]> pageResult = indexTestingRepository.indexScreeingListBySSK(sskid);

		List<BeneficiaryIndexDTO> resultList = new ArrayList<>();

		for (Object[] row : pageResult) {
			BeneficiaryIndexDTO beneficiaryDTO = new BeneficiaryIndexDTO();
			
//			beneficiaryDTO.setScreeningDate((LocalDate) row[0]);
			Date sqlDate = (Date) row[0];
	        LocalDate localDate = sqlDate.toLocalDate();
	        beneficiaryDTO.setScreeningDate(localDate);

			beneficiaryDTO.setStatus((String) row[1]);
			beneficiaryDTO.setResult((String) row[2]);
			beneficiaryDTO.setOutReferralTo((String) row[3]);
			
			resultList.add(beneficiaryDTO);
		}

		return resultList;
	}
	
	
	
	public List<BeneficiaryIndexDTO> indexScreeingListByfacilityId() {
		Long facilityId = UserUtils.getLoggedInUserDetails().getFacilityId();
		List<Object[]> pageResult = indexTestingRepository.indexScreeingListByfacilityId(facilityId);

		List<BeneficiaryIndexDTO> resultList = new ArrayList<>();

		for (Object[] row : pageResult) {
			BeneficiaryIndexDTO beneficiaryDTO = new BeneficiaryIndexDTO();
			
//			beneficiaryDTO.setScreeningDate((LocalDate) row[0]);
			Date sqlDate = (Date) row[0];
	        LocalDate localDate = sqlDate.toLocalDate();
	        beneficiaryDTO.setScreeningDate(localDate);

			beneficiaryDTO.setStatus((String) row[1]);
			beneficiaryDTO.setResult((String) row[2]);
			beneficiaryDTO.setOutReferralTo((String) row[3]);
			
			resultList.add(beneficiaryDTO);
		}

		return resultList;
	}
	

}
