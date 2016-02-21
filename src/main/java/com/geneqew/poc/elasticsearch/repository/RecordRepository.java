package com.geneqew.poc.elasticsearch.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.geneqew.poc.elasticsearch.domain.Record;

public interface RecordRepository {

	Record findOne(Long id);

	Record create(Record record);
	
	Record update(Record record);
	
	void delete(Long id);
	
}
