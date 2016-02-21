package com.geneqew.poc.elasticsearch.repository.impl;

import java.io.IOException;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geneqew.poc.elasticsearch.domain.Record;
import com.geneqew.poc.elasticsearch.exception.ExistingIndexException;
import com.geneqew.poc.elasticsearch.exception.NoIndexException;
import com.geneqew.poc.elasticsearch.repository.RecordRepository;

@Component
public class RecordRepositoryImpl implements RecordRepository {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(RecordRepositoryImpl.class);

	private static final String INDEX = "a10dans";
	private static final String TYPE = "record";

	@Autowired
	private Client client;
	@Autowired
	private ObjectMapper jacksonObjectMapper;

	@Override
	public Record findOne(Long id) {
		GetResponse response = client.prepareGet(INDEX, TYPE,
		        String.valueOf(id)).get();
		try {
			return jacksonObjectMapper.readValue(response.getSourceAsString(),
			        Record.class);
		} catch (IOException e) {
			LOGGER.error("failed to retrieve record with id {} ", id, e);
			throw new NoIndexException();
		}
	}

	@Override
	public Record create(Record record) {
		IndexResponse response;
		try {
			response = client.prepareIndex(INDEX, TYPE, record.getIdAsString())
			        .setSource(jacksonObjectMapper.writeValueAsString(record))
			        .get();
			if (response.isCreated()) {
				return record;
			}
		} catch (JsonProcessingException e) {
			LOGGER.error("failed tp process index");
			throw new RuntimeException();
		}
		throw new ExistingIndexException();
	}

	@Override
	public void delete(Long id) {
		DeleteResponse response = client.prepareDelete(INDEX, TYPE,
		        String.valueOf(id)).get();
		if (!response.isFound()) {
			throw new NoIndexException();
		}
	}

	@Override
	public Record update(Record record) {
		try {
	        client
	        	.prepareUpdate(INDEX, TYPE, record.getIdAsString())
	        	.setDoc(jacksonObjectMapper.writeValueAsString(record))
	        	.get();
	        return findOne(record.getId());
        } catch (Exception e) {
	        LOGGER.error("failed to update record {} ", record.getIdAsString() ,e);
	        throw new RuntimeException();
        }
	}

}
