package gov.naco.soch.dashboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gov.naco.soch.dashboard.service.NintyFiveService;

@RestController
@RequestMapping("/nintyfive")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class NintyFiveController {
	
	@Autowired
	NintyFiveService nintyFiveService; 

}
