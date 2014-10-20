package com.adaofeliz.dropboxuploader.controller.advice;

import com.adaofeliz.dropboxuploader.dto.ErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

/**
 * Created on 18/10/14.
 */

@ControllerAdvice
public class ServiceControllerAdvice {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceControllerAdvice.class);

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handlingGenericException(Exception exception) {

        // Adding an Error Id
        String msg = exception.getMessage() + " Error Id: " + UUID.randomUUID().toString();

        // Logging
        LOG.error(msg, exception);

        // 500 Http Status Code
        HttpStatus httpStatusInternalServerError = HttpStatus.INTERNAL_SERVER_ERROR;

        // Sending Error to the Client
        ErrorDto errorDto = new ErrorDto();
        errorDto.error = msg;
        errorDto.status = httpStatusInternalServerError.getReasonPhrase();
        errorDto.code = httpStatusInternalServerError.value();

        return new ResponseEntity<>(errorDto, httpStatusInternalServerError);
    }
}
