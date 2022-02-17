package br.com.forja.bits.donation.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Response {

    private String message;
    private Object body;

    public Response(String message) {
        this.message = message;
    }

    public Response(Object body) {
        this.body = body;
    }

    public Response(Object body, String message) {
        this.body = body;
        this.message = message;
    }


    public ResponseEntity ok() {
        return new ResponseEntity<>(this, HttpStatus.OK);
    }

    public ResponseEntity badRequest() {
        return new ResponseEntity<>(this, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity conflict() {
        return new ResponseEntity<>(this, HttpStatus.CONFLICT);
    }

    public ResponseEntity created() {
        return new ResponseEntity<>(this, HttpStatus.CREATED);
    }

    public ResponseEntity notFound() {
        return new ResponseEntity<>(this, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity notAcceptable() {
        return new ResponseEntity<>(this, HttpStatus.NOT_ACCEPTABLE);
    }

    public ResponseEntity unauthorized() {
        return new ResponseEntity<>(this, HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity badGateway() {
        return new ResponseEntity<>(this, HttpStatus.BAD_GATEWAY);
    }

    public ResponseEntity timeOut() {
        return new ResponseEntity<>(this, HttpStatus.REQUEST_TIMEOUT);
    }

}