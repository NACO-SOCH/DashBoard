package gov.naco.soch.dashboard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gov.naco.soch.dashboard.DTO.FacilityDTO;
import gov.naco.soch.dashboard.service.CascadeService;

@RestController
@RequestMapping("/cascade")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CascadeController {

	@Autowired
	CascadeService cascadeService;

    @GetMapping("/cascadeCoverage/{stateId}")
    public ResponseEntity<List<FacilityDTO>> cascadeCoverage(@PathVariable Integer stateId) {
        try {
            List<FacilityDTO> result = cascadeService.cascadeCoverage(stateId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
