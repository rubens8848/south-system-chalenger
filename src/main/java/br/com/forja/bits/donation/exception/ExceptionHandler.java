package br.com.forja.bits.donation.exception;

import br.com.forja.bits.donation.model.response.EntityResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(EntityResponses.class)
    public ResponseEntity<?> checkNotFound(){
        return new EntityResponses().notFound("not found");
    }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal
            (Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("status", status.getReasonPhrase());

        return super.handleExceptionInternal(ex, map, headers, status, request);
    }
}
