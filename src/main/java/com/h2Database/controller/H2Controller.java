package com.h2Database.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.h2Database.dtos.TigerCardDetails;
import com.h2Database.service.H2Service;

@RestController
public class H2Controller {
	
	@Autowired
	private H2Service metroService;

	//Sample Json Input format. The day and time parameter will be taken from current time
	/*{
    	"userId" : 1,
    	"fromZone" : 1,
    	"toZone" : 2
	}*/

	@RequestMapping(value = "/secureJourney", method = RequestMethod.POST)
	public ResponseEntity<TigerCardDetails> secureJourney(@RequestBody TigerCardDetails cardDetails) {
		return new ResponseEntity<TigerCardDetails>(metroService.computeJourneyDetails(cardDetails), HttpStatus.OK);
	}
}
