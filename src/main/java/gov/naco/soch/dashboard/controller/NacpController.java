package gov.naco.soch.dashboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gov.naco.soch.dashboard.service.NacpService;

@RestController
@RequestMapping("/nacp")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class NacpController {
	
	@Autowired
	NacpService nacpService;

}
