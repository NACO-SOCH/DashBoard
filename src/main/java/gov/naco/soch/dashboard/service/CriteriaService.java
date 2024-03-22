package gov.naco.soch.dashboard.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

import gov.naco.soch.entity.IctcLinelistReport;

@Service
public class CriteriaService {
	
	@PersistenceContext
    private EntityManager entityManager;
	private static final Logger logger = LoggerFactory.getLogger(CriteriaService.class);
 
	
	public Stream<Object[]> getGCPWReport(Integer stateId, Integer facilityId, Integer districtId,
			String pidPrefix) {
		try {
			logger.info("before CriteriaBuilder");
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
			Root<IctcLinelistReport> report = cq.from(IctcLinelistReport.class);
			List<Predicate> predicates = new ArrayList<>();
			logger.info("after CriteriaBuilder");
			if (stateId != null && stateId != 0) {
				predicates.add(cb.equal(report.get("stateId"), stateId));
			}
			if (districtId != null && districtId != 0) {
				predicates.add(cb.equal(report.get("districtId"), districtId));
			}
			if (facilityId != null && facilityId != 0) {
				predicates.add(cb.equal(report.get("facilityId"), facilityId));
			}
			if (pidPrefix != null && !pidPrefix.trim().isEmpty()) {
				predicates.add(cb.like(report.get("pid"), pidPrefix + "%"));
			}
			
			cq.multiselect(report.get("pid"), report.get("nameOfIctc"), report.get("testingDate"),
					report.get("firstName"), report.get("lastName"), report.get("mobileNumber"), report.get("age"),
					report.get("sex"), report.get("education"), report.get("occupation"), report.get("state"),
					report.get("district"), report.get("block"), report.get("maritalStatus"), report.get("hivType"),
					report.get("routeOfTransmissionOfHiv"), report.get("nameOfArtCentre"),
					report.get("dateOfReferral"), report.get("preArtNumber"), report.get("preArtRegistrationDate"),
					report.get("artInitiation"), report.get("artNumber"), report.get("baseLineCd4TestDate"),
					report.get("baseLineCd4Count"), report.get("riskProfiling"))
					.where(cb.and(predicates.toArray(new Predicate[0])));
			logger.info("after multiselect");
			//logger.info("after multiselect" + entityManager.createQuery(cq).getResultStream() );
			return entityManager.createQuery(cq).getResultList().stream();
			
		}  catch (PersistenceException ex) {
	        logger.error("Error while executing getGCPWReport", ex);
	        throw new RuntimeException("Error accessing data", ex);
	    }
	}

}
