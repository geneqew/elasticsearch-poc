package com.geneqew.poc.elasticsearch.controller;

import org.springframework.http.ResponseEntity;

import com.geneqew.poc.elasticsearch.domain.Record;

public interface RecordController {

	ResponseEntity<?> create(Record record);
	
	ResponseEntity<?> update(Long id, Record record);
	
	ResponseEntity<?> find(Long id);
	
	ResponseEntity<?> delete(Long id);
	
}
