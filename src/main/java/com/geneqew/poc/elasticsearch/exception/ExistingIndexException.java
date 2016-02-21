package com.geneqew.poc.elasticsearch.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "")
public class ExistingIndexException extends RuntimeException {

	private static final long serialVersionUID = 1L;

}
