package gov.naco.soch.dashboard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.naco.soch.repository.DashBoardNintyFiveRepo;

@Service
@Transactional
public class NintyFiveService {

	@Autowired
	DashBoardNintyFiveRepo dashBoardNintyFiveRepo;
}
