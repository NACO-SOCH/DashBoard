package gov.naco.soch.dashboard.service;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
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
import gov.naco.soch.dashboard.DTO.FacilityDTO;
import gov.naco.soch.dashboard.DTO.IndexBeneficiaryListDto;
import gov.naco.soch.dashboard.DTO.IndexReferralEntity;
import gov.naco.soch.entity.Address;
import gov.naco.soch.entity.Beneficiary;
import gov.naco.soch.entity.BeneficiaryFacilityMapping;
import gov.naco.soch.entity.BeneficiaryReferral;
import gov.naco.soch.entity.Facility;
import gov.naco.soch.entity.MasterGender;
import gov.naco.soch.entity.MasterReferralStatus;
import gov.naco.soch.entity.State;
import gov.naco.soch.repository.AddressRepository;
import gov.naco.soch.repository.BeneficiaryFacilityMappingRepository;
import gov.naco.soch.repository.BeneficiaryReferralRepository;
import gov.naco.soch.repository.BeneficiaryRepository;
import gov.naco.soch.repository.FacilityRepository;
import gov.naco.soch.repository.IndexTestingRepository;
import gov.naco.soch.singelton.LoggerSingleton;
import gov.naco.soch.util.UserUtils;

@Service
@Transactional
public class IndexBeneficiaryService {

	@Autowired
	IndexTestingRepository indexTestingRepository;

	@Autowired
	FacilityRepository facilityRepository;

	@Autowired
	BeneficiaryReferralRepository beneficiaryReferralRepository;

	@Autowired
	BeneficiaryFacilityMappingRepository beneficiaryFacilityMappingRepository;

	@Autowired
	BeneficiaryRepository beneficiaryRepository;

	@Autowired
	AddressRepository addressRepository;
	
     BeneficiaryService beneficiaryService;

	Logger logger = LoggerSingleton.getInstance().getLogger();
	
	
	private DecimalFormat formatter;
	@PostConstruct
	public void init() {
		formatter = new DecimalFormat("00000000000");
	}


	public void saveBeneficiaryIndex(AddIndexDTO addIndexDTO) {
		Long facilityId = UserUtils.getLoggedInUserDetails().getFacilityId();

		indexTestingRepository.saveBeneficiaryIndex(addIndexDTO.getFirstName(), addIndexDTO.getMiddleName(),
				addIndexDTO.getLastName(), addIndexDTO.getSex(), addIndexDTO.getBirthDate(), addIndexDTO.getAge(),
				addIndexDTO.getPhoneNo(), addIndexDTO.getAddressLine1(), addIndexDTO.getAddressLine2(),
				addIndexDTO.getStateId(), addIndexDTO.getDistrictId(), facilityId, addIndexDTO.getIndexTypeId(),
				addIndexDTO.getBeneficiaryId(), addIndexDTO.getPincode());
	}

//	public void saveBeneficiaryIndexScreening(BeneficiaryIndexScreeningDTO beneficiaryIndexScreeningDTO) {
//
//		Long UserId = UserUtils.getLoggedInUserDetails().getUserId();
//
//		logger.info(beneficiaryIndexScreeningDTO.getScreeningDate() + " date");
//
////		try {
//		if (beneficiaryIndexScreeningDTO.getScreeningDate() != null
//				&& beneficiaryIndexScreeningDTO.getOutReferralTo() != null) {
//			logger.info("dd");
//			indexTestingRepository.saveBeneficiaryIndexScreening(beneficiaryIndexScreeningDTO.getSskid(),
//					beneficiaryIndexScreeningDTO.getScreeningDate(),
//					beneficiaryIndexScreeningDTO.getHivScreeningResultId(),
//					beneficiaryIndexScreeningDTO.getIndexScreeningStatusId(),
//					beneficiaryIndexScreeningDTO.getOutReferralTo(), beneficiaryIndexScreeningDTO.getRemarks(), UserId,
//					UserId);
//		} else if (beneficiaryIndexScreeningDTO.getScreeningDate() != null) {
//			logger.info("ff");
//
//			if (beneficiaryIndexScreeningDTO.getOutReferralTo() != null) {
//				logger.info("ff 1");
//				indexTestingRepository.saveBeneficiaryIndexScreeningNonReactive(beneficiaryIndexScreeningDTO.getSskid(),
//						beneficiaryIndexScreeningDTO.getScreeningDate(),
//						beneficiaryIndexScreeningDTO.getHivScreeningResultId(),
//						beneficiaryIndexScreeningDTO.getIndexScreeningStatusId(),
//						beneficiaryIndexScreeningDTO.getOutReferralTo(), beneficiaryIndexScreeningDTO.getRemarks(),
//						UserId, UserId);
//			} else {
//				logger.info("ff 2");
//				indexTestingRepository.saveBeneficiaryIndexScreeningNonReactive1(
//						beneficiaryIndexScreeningDTO.getSskid(), beneficiaryIndexScreeningDTO.getScreeningDate(),
//						beneficiaryIndexScreeningDTO.getIndexScreeningStatusId(),
//						beneficiaryIndexScreeningDTO.getRemarks(),
//						beneficiaryIndexScreeningDTO.getHivScreeningResultId(), UserId, UserId);
//			}
//
//		} else {
//			logger.info("ee");
//			indexTestingRepository.saveBeneficiaryIndexScreeningwithoutdate(beneficiaryIndexScreeningDTO.getSskid(),
//					beneficiaryIndexScreeningDTO.getIndexScreeningStatusId(), beneficiaryIndexScreeningDTO.getRemarks(),
//					UserId, UserId);
//		}
////		}catch(Exception e) {
////			System.out.print(e.getMessage());
////		}
//	}

	public Page<IndexBeneficiaryListDto> findIndexBeneficiariesByFacilityId(Integer pageNumber, Integer pageSize,
			String searchParams) {
		Long facilityId = UserUtils.getLoggedInUserDetails().getFacilityId();
		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		Page<Object[]> pageResult = indexTestingRepository.findBeneficiaryByFacilityId(facilityId, pageable,
				searchParams);

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

	public Page<IndexBeneficiaryListDto> findIndexBeneficiariesTIByFacilityId(Integer pageNumber, Integer pageSize,
			String searchParams) {
		Long facilityId = UserUtils.getLoggedInUserDetails().getFacilityId();
		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		Page<Object[]> pageResult = indexTestingRepository.findBeneficiaryTIByFacilityId(facilityId, pageable,
				searchParams);

		List<IndexBeneficiaryListDto> resultList = new ArrayList<>();

		for (Object[] row : pageResult) {
			IndexBeneficiaryListDto beneficiaryDto = new IndexBeneficiaryListDto();

			beneficiaryDto.setId(((Number) row[0]).longValue());
			beneficiaryDto.setUid((String) row[1]);
			beneficiaryDto.setBeneficiaryName((String) row[2]);
			beneficiaryDto.setGenderName((String) row[3]);
			// beneficiaryDto.setArtNumber((String) row[4]);
			beneficiaryDto.setTotalIndexCount(((BigInteger) row[4]).longValue());
			beneficiaryDto.setIndexReferredToICTC(((BigInteger) row[5]).longValue());
			beneficiaryDto.setIndexAlreadyContacted(((BigInteger) row[6]).longValue());

			resultList.add(beneficiaryDto);
		}

		return new PageImpl<>(resultList, pageable, pageResult.getTotalElements());
	}

	public Page<IndexBeneficiaryListDto> findIndexBeneficiariesOSTByFacilityId(Integer pageNumber, Integer pageSize,
			String searchParams) {
		Long facilityId = UserUtils.getLoggedInUserDetails().getFacilityId();
		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		Page<Object[]> pageResult = indexTestingRepository.findBeneficiaryOSTByFacilityId(facilityId, pageable,
				searchParams);

		List<IndexBeneficiaryListDto> resultList = new ArrayList<>();

		for (Object[] row : pageResult) {
			IndexBeneficiaryListDto beneficiaryDto = new IndexBeneficiaryListDto();

			beneficiaryDto.setId(((Number) row[0]).longValue());
			beneficiaryDto.setUid((String) row[1]);
			beneficiaryDto.setBeneficiaryName((String) row[2]);
			beneficiaryDto.setGenderName((String) row[3]);
			beneficiaryDto.setOstNumber((String) row[4]);
			beneficiaryDto.setTotalIndexCount(((BigInteger) row[5]).longValue());
			beneficiaryDto.setIndexReferredToICTC(((BigInteger) row[6]).longValue());
			beneficiaryDto.setIndexAlreadyContacted(((BigInteger) row[7]).longValue());

			resultList.add(beneficiaryDto);
		}

		return new PageImpl<>(resultList, pageable, pageResult.getTotalElements());
	}

	public Page<IndexBeneficiaryListDto> findIndexBeneficiariesDSRCByFacilityId(Integer pageNumber, Integer pageSize,
			String searchParams) {
		Long facilityId = UserUtils.getLoggedInUserDetails().getFacilityId();
		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		Page<Object[]> pageResult = indexTestingRepository.findBeneficiaryDSRCByFacilityId(facilityId, pageable,
				searchParams);

		List<IndexBeneficiaryListDto> resultList = new ArrayList<>();

		for (Object[] row : pageResult) {
			IndexBeneficiaryListDto beneficiaryDto = new IndexBeneficiaryListDto();

			beneficiaryDto.setId(((Number) row[0]).longValue());
			beneficiaryDto.setUid((String) row[1]);
			beneficiaryDto.setBeneficiaryName((String) row[2]);
			beneficiaryDto.setGenderName((String) row[3]);
			beneficiaryDto.setDsrcCode((String) row[4]);
			beneficiaryDto.setTotalIndexCount(((BigInteger) row[5]).longValue());
			beneficiaryDto.setIndexReferredToICTC(((BigInteger) row[6]).longValue());
			beneficiaryDto.setIndexAlreadyContacted(((BigInteger) row[7]).longValue());

			resultList.add(beneficiaryDto);
		}

		return new PageImpl<>(resultList, pageable, pageResult.getTotalElements());
	}

	public Page<IndexBeneficiaryListDto> findIndexBeneficiariesICTCByFacilityId(Integer pageNumber, Integer pageSize,
			String searchParams) {
		Long facilityId = UserUtils.getLoggedInUserDetails().getFacilityId();
		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		Page<Object[]> pageResult = indexTestingRepository.findBeneficiaryICTCByFacilityId(facilityId, pageable,
				searchParams);

		List<IndexBeneficiaryListDto> resultList = new ArrayList<>();

		for (Object[] row : pageResult) {
			IndexBeneficiaryListDto beneficiaryDto = new IndexBeneficiaryListDto();

			beneficiaryDto.setId(((Number) row[0]).longValue());
			beneficiaryDto.setUid((String) row[1]);
			beneficiaryDto.setBeneficiaryName((String) row[2]);
			beneficiaryDto.setGenderName((String) row[3]);
			beneficiaryDto.setPid((String) row[4]);
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
			beneficiaryDTO.setRelationType((String) row[7]);
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

			if (row[0] != null) {
				Date sqlDate = (Date) row[0];
				LocalDate localDate = sqlDate.toLocalDate();
				beneficiaryDTO.setScreeningDate(localDate);
			} else {
				beneficiaryDTO.setScreeningDate(null);
			}

			beneficiaryDTO.setStatus((String) row[1]);
			beneficiaryDTO.setResult((String) row[2]);
			beneficiaryDTO.setOutReferralTo((String) row[3]);
			beneficiaryDTO.setRemarks((String) row[4]);
			
			if (row[5] != null) {
			    Timestamp timestamp = (Timestamp) row[5];
			    LocalDate localDate = timestamp.toLocalDateTime().toLocalDate();
			    beneficiaryDTO.setCreatedOn(localDate);
			} else {
			    beneficiaryDTO.setCreatedOn(null);
			}

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

			if (row[0] != null) {
				Date sqlDate = (Date) row[0];
				LocalDate localDate = sqlDate.toLocalDate();
				beneficiaryDTO.setScreeningDate(localDate);
			} else {
				beneficiaryDTO.setScreeningDate(null);
			}

			beneficiaryDTO.setStatus((String) row[1]);
			beneficiaryDTO.setResult((String) row[2]);
			beneficiaryDTO.setOutReferralTo((String) row[3]);

			resultList.add(beneficiaryDTO);
		}
		return resultList;
	}

//	@Transactional
	public void indexReferral(BeneficiaryIndexScreeningDTO beneficiaryIndexScreeningDTO) {
		
	    //BeneficiaryIndexScreeningDTO beneficiaryIndexScreeningDTO = new BeneficiaryIndexScreeningDTO();
//
//	    beneficiaryIndexScreeningDTO.setSskid(indexReferralDTO.getSskid());
//	    beneficiaryIndexScreeningDTO.setScreeningDate(indexReferralDTO.getScreeningDate());
//	    beneficiaryIndexScreeningDTO.setHivScreeningResultId(indexReferralDTO.getScreeningResult());
//	    beneficiaryIndexScreeningDTO.setIndexScreeningStatusId(indexReferralDTO.getScreeningStatus());
//	    beneficiaryIndexScreeningDTO.setOutReferralTo(indexReferralDTO.getOutReferralTo());
//	    beneficiaryIndexScreeningDTO.setRemarks(indexReferralDTO.getRemarks());
//	    
	   
	    saveIndexScreeningNew(beneficiaryIndexScreeningDTO);

		Long sskid = beneficiaryIndexScreeningDTO.getSskid();
		
		logger.info(sskid + " : sskid");
		
		// IndexReferralEntity beneficiaryIndex = (IndexReferralEntity)
		// indexTestingRepository.findBySskid(sskid);

		List<Object[]> resultList = indexTestingRepository.findBySskid(sskid);
		List<IndexReferralEntity> indexReferralEntities = new ArrayList<>();

		for (Object[] result : resultList) {
			IndexReferralEntity indexReferralEntity = new IndexReferralEntity();
			indexReferralEntity.setId(((Number) result[0]).longValue());

			indexReferralEntity.setFirstName((String) result[1]);
			indexReferralEntity.setMiddleName((String) result[2]);
			indexReferralEntity.setLastName((String) result[3]);
//			indexReferralEntity.setSex((Long) result[4] );
			indexReferralEntity.setSex(Long.parseLong(result[4].toString()));

			java.sql.Date birthDateSql = (java.sql.Date) result[5];
			LocalDate birthDate = birthDateSql.toLocalDate();
			indexReferralEntity.setBirthDate(birthDate);

			indexReferralEntity.setAge((String) result[6]);

			indexReferralEntity.setPhoneNo((String) result[7]);
			indexReferralEntity.setAddressLine1((String) result[8]);
			indexReferralEntity.setAddressLine2((String) result[9]);

			indexReferralEntity.setStateId(((Number) result[10]).longValue());
			indexReferralEntity.setDistrictId(((Number) result[11]).longValue());
			indexReferralEntity.setIndexTypeId(((Number) result[12]).longValue());
			indexReferralEntity.setBeneficiaryId(((Number) result[13]).longValue());
			indexReferralEntity.setIndexBeneficiaryId(result[14] != null ? ((Number) result[14]).longValue() : null);

			indexReferralEntity.setFacilityId(result[16] != null ? ((Number) result[16]).longValue() : null);

			indexReferralEntity.setPincode((String) result[15]);

			indexReferralEntities.add(indexReferralEntity);
		}

		IndexReferralEntity beneficiaryIndex = indexReferralEntities.get(0);
		Long beneficiaryId  = beneficiaryIndex.getIndexBeneficiaryId();
		
		if (beneficiaryId == null) {

			Address address = new Address();
			address.setAddressLineOne(beneficiaryIndex.getAddressLine1());
			address.setAddressLineTwo(beneficiaryIndex.getAddressLine2());
			address.setPincode(beneficiaryIndex.getPincode());
//          address.setStateId(beneficiaryIndex.getStateId());
			address.setState(new State(beneficiaryIndex.getStateId(), null));
			address.setPincode(beneficiaryIndex.getPincode());
			addressRepository.save(address);

			Beneficiary beneficiary = new Beneficiary();
			beneficiary.setAddress(address);
			beneficiary.setFirstName(beneficiaryIndex.getFirstName());
			beneficiary.setLastName(beneficiaryIndex.getLastName());
			beneficiary.setMiddleName(beneficiaryIndex.getMiddleName());
			beneficiary.setDateOfBirth(beneficiaryIndex.getBirthDate());
			beneficiary.setMobileNumber(beneficiaryIndex.getPhoneNo());
			beneficiary.setIsActive(true);
			beneficiary.setIsDelete(false);
			
			MasterGender gender = new MasterGender(beneficiaryIndex.getSex(), null);
			beneficiary.setGenderId(gender);
			
			beneficiary.setAge(beneficiaryIndex.getAge());
			
			
			
			beneficiaryRepository.save(beneficiary);
			
			beneficiaryId = beneficiary.getId();
			
			logger.info(sskid + "sskid");
			logger.info(beneficiaryId + "beneficiaryId");
			indexTestingRepository.saveIndexBeneficiaryId(sskid, beneficiaryId);
					
			
			logger.info(beneficiaryId + "");
//			logger.info(beneficiaryService.generateUid(beneficiaryId) + "xyz");
			
			String x  = 'U'+ formatter.format(beneficiaryId);
			logger.info(x + "x");
			beneficiary.setUid(x);
			
			beneficiaryRepository.save(beneficiary);
			
		}
		
		final Long xx = beneficiaryIndex.getIndexBeneficiaryId();
		logger.info(beneficiaryId + "xx");
		BeneficiaryReferral beneficiaryReferral = new BeneficiaryReferral();
 		Beneficiary beneficiary = beneficiaryRepository.findById(beneficiaryId).orElse(null); //
		beneficiaryReferral.setBeneficiary(beneficiary);
		
		MasterReferralStatus referralStatus = new MasterReferralStatus(1L);
		beneficiaryReferral.setBeneficiaryReferralStatusMaster(referralStatus);
		
		// beneficiaryReferral.setBeneficiaryId(beneficiaryIndex.getBeneficiaryId());
		
		beneficiaryReferral.setReferDate(LocalDate.now());
		Long facilityId = beneficiaryIndex.getFacilityId();
		Long outReferralTo = beneficiaryIndexScreeningDTO.getOutReferralTo();

		Facility facility = facilityRepository.findById(facilityId).orElse(null);
		// Facility facility2 = facilityRepository.findById(outReferralTo).orElse(null);
		beneficiaryReferral.setFacility1(facility);
		if (outReferralTo != null) {
			Facility facility2 = facilityRepository.findById(outReferralTo).orElse(null);
			beneficiaryReferral.setFacility2(facility2);
		}
		beneficiaryReferralRepository.save(beneficiaryReferral);

		BeneficiaryFacilityMapping beneficiaryFacilityMapping = new BeneficiaryFacilityMapping();
		Beneficiary newBeneficiary = new Beneficiary(); // Rename to newBeneficiary
		newBeneficiary.setId(beneficiaryId);
		beneficiaryFacilityMapping.setBeneficiary(newBeneficiary);
		beneficiary.setId(beneficiaryId);
		beneficiaryFacilityMapping.setBeneficiary(beneficiary);
		beneficiaryFacilityMapping.setFacility(facility);
		beneficiaryFacilityMappingRepository.save(beneficiaryFacilityMapping);

		// saveIndexScreening(indexReferralDTO);

	}

	public void saveIndexScreeningNew(BeneficiaryIndexScreeningDTO beneficiaryIndexScreeningDTO) {
		Long UserId = UserUtils.getLoggedInUserDetails().getUserId();

		logger.info(beneficiaryIndexScreeningDTO.getScreeningDate() + " date");
		logger.info(beneficiaryIndexScreeningDTO.getIndexScreeningStatusId() + " statusId");
		
		if (beneficiaryIndexScreeningDTO.getScreeningDate() != null
				&& beneficiaryIndexScreeningDTO.getOutReferralTo() != null) {
			logger.info("dd");
			indexTestingRepository.saveBeneficiaryIndexScreening(
					beneficiaryIndexScreeningDTO.getSskid(),
					beneficiaryIndexScreeningDTO.getScreeningDate(),
					beneficiaryIndexScreeningDTO.getHivScreeningResultId(),
					beneficiaryIndexScreeningDTO.getIndexScreeningStatusId(),
					beneficiaryIndexScreeningDTO.getOutReferralTo(),
					beneficiaryIndexScreeningDTO.getRemarks(), 
					UserId,
					UserId);
		} else if (beneficiaryIndexScreeningDTO.getScreeningDate() != null) {
			logger.info("ff");

			if (beneficiaryIndexScreeningDTO.getOutReferralTo() != null) {
				logger.info("ff 1");
				indexTestingRepository.saveBeneficiaryIndexScreeningNonReactive(
						beneficiaryIndexScreeningDTO.getSskid(),
						beneficiaryIndexScreeningDTO.getScreeningDate(),
						beneficiaryIndexScreeningDTO.getHivScreeningResultId(),
						beneficiaryIndexScreeningDTO.getIndexScreeningStatusId(),
						beneficiaryIndexScreeningDTO.getOutReferralTo(), 
						beneficiaryIndexScreeningDTO.getRemarks(),
						UserId, 
						UserId);
			} else {
				logger.info("ff 2");
				indexTestingRepository.saveBeneficiaryIndexScreeningNonReactive1(
						beneficiaryIndexScreeningDTO.getSskid(), 
						beneficiaryIndexScreeningDTO.getScreeningDate(),
						beneficiaryIndexScreeningDTO.getIndexScreeningStatusId(),
						beneficiaryIndexScreeningDTO.getRemarks(), 
						UserId, 
						UserId);
			}

		} else {
			logger.info("ee");
			indexTestingRepository.saveBeneficiaryIndexScreeningwithoutdate(
					beneficiaryIndexScreeningDTO.getSskid(),
					beneficiaryIndexScreeningDTO.getIndexScreeningStatusId(), 
					beneficiaryIndexScreeningDTO.getRemarks(),
					UserId, 
					UserId);
		}

	}
	
	public  List<FacilityDTO> facilityName (Integer district) {
		
		  List<Object[]> dashBoardOverviewList = indexTestingRepository.facilityName(district);
		  List<FacilityDTO> facilityDTOList = new ArrayList<>();
		  
		  try {
			  for (Object[] row : dashBoardOverviewList) {
			      FacilityDTO facilityDTO = new FacilityDTO();
			      facilityDTO.setId((Integer) row[0]);
			      facilityDTO.setName ((String) row[1]); 
			      facilityDTOList.add(facilityDTO);
			  }
		  }catch(Exception e) {
			  logger.info(e.getMessage());
		  }
		  return facilityDTOList;
	}
}
