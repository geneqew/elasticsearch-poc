package com.geneqew.poc.elasticsearch.controller.impl;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.geneqew.poc.elasticsearch.controller.RecordController;
import com.geneqew.poc.elasticsearch.domain.Record;
import com.geneqew.poc.elasticsearch.repository.RecordRepository;

@Component
@RestController
@RequestMapping(
		path = "record",
		consumes = {"application/json", " application/x-www-form-urlencoded"},
		produces = "application/json")
public class RecordControllerImpl implements RecordController {

	@Autowired
	private RecordRepository recordRepository;
	
	@RequestMapping(method = RequestMethod.POST)
	@Override
	public ResponseEntity<?> create(
			@RequestBody(required = true) Record record) {
	    Record createdRecord = recordRepository.create(record);
	    return ResponseEntity.created(URI.create(createdRecord.getIdAsString())).body(createdRecord);
    }

	@RequestMapping(
			path = "{id}",
			method = RequestMethod.GET)
	@Override
	public ResponseEntity<?> find(
			@PathVariable("id") Long id) {
		Record record = recordRepository.findOne(id);
	    return ResponseEntity.ok(record);
    }

	@Override
	@RequestMapping(
			method = RequestMethod.DELETE,
			path = "{id}")
    public ResponseEntity<?> delete(@PathVariable("id")Long id) {
	    recordRepository.delete(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

	@Override
	@RequestMapping(
			method = RequestMethod.PUT,
			path = "{id}")
    public ResponseEntity<?> update(
    		@PathVariable("id")Long id, 
    		@RequestBody(required = true) Record record) {
	    
		
		return null;
    }

}
