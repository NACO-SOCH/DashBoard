package gov.naco.soch.dashboard.service;


import java.text.DecimalFormat;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import gov.naco.soch.singelton.LoggerSingleton;


@Service
public class BeneficiaryService {
	
	private DecimalFormat formatter;
	
	@PostConstruct
	public void init() {
		formatter = new DecimalFormat("00000000000");
	}

	Logger logger = LoggerSingleton.getInstance().getLogger();
    /**
     * Save a beneficiary.
     *
     * @param beneficiaryDTO the entity to save.
     * @return the persisted entity.
     */
//    BeneficiaryDTO save(BeneficiaryDTO beneficiaryDTO);
//    
//    Beneficiary saveBeneficiary(Beneficiary beneficiary);
//
//    /**
//     * Get all the beneficiaries.
//     *
//     * @param pageable the pagination information.
//     * @return the list of entities.
//     */
//    Page<BeneficiaryDTO> findAll(Pageable pageable);
//
//    /**
//     * Get the "id" beneficiary.
//     *
//     * @param id the id of the entity.
//     * @return the entity.
//     */
//    Optional<BeneficiaryDTO> findOne(Long id);
//    
//    Optional<Beneficiary> find(Long id);
//    
//    BeneficiaryDTO getOne(Long id);
    

	public String generateUid(Long uid) {
		logger.info("inside the function");
		logger.info('U'+ formatter.format(uid));
		return 'U'+ formatter.format(uid);
	}
	
	
	
	

}
