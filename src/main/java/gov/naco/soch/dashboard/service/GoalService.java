package gov.naco.soch.dashboard.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.naco.soch.repository.DashBoardGoalRepo;

@Service
@Transactional
public class GoalService {
	
	@Autowired
	DashBoardGoalRepo dashBoardGoalRepo;
}
