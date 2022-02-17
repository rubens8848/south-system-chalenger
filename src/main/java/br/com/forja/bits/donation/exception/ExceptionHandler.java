package br.com.forja.bits.donation.exception;

import br.com.forja.bits.donation.model.response.Response;
import br.com.forja.bits.donation.model.response.ValidationResponse;
import br.com.forja.bits.donation.utils.Logging;
import javassist.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<ValidationResponse> errors = ex.getBindingResult()
                .getFieldErrors().stream()
                .map(e -> new ValidationResponse(e.getField(), e.getDefaultMessage()))
                .collect(Collectors.toList());

        return new Response("Invalid Fields", errors).badRequest();
//        return handleExceptionInternal(ex, errorDetails, headers, errorDetails.getStatus(), request);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinesException(Exception e) {
        return new Response(e.getMessage()).badRequest();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleException(Exception e) {
        return new Response(e.getMessage()).notFound();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> handleUnauthorizedException(Exception e) {
        return new Response(e.getMessage()).unauthorized();

    }

    @org.springframework.web.bind.annotation.ExceptionHandler(TimeoutException.class)
    public ResponseEntity<?> handleDefaultException(TimeoutException e) {
        return new Response("Time out").timeOut();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleDefaultException(Exception e) {
        Logging.getLog().error("exception - " + e.getMessage());
        return new Response("Something unexpected happened, contact our support for more information").badRequest();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(OkException.class)
    public ResponseEntity<?> handleOkException(Exception e) {
        return new Response(e.getMessage()).ok();
    }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal
            (Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("status", status.getReasonPhrase());

        return super.handleExceptionInternal(ex, map, headers, status, request);
    }
}
