package br.com.forja.bits.donation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.OK )
public class OkException extends RuntimeException {

    public OkException(String msg) {
        super(msg);
    }

}
