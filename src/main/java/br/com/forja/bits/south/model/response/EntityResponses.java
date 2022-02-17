package br.com.forja.bits.south.model.response;

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
public class EntityResponses extends RuntimeException {

    private Object body;
    private String message;


//    public EntityResponses(String message){
//        this.message = message;
//    }

    public ResponseEntity ok(Object object) {
        return new ResponseEntity(object, HttpStatus.OK);
    }

    public ResponseEntity ok() {
        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity buildError(String message) {
        return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity loginError() {
        return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
    }

    public ResponseEntity badGateway() {
        return new ResponseEntity(HttpStatus.BAD_GATEWAY);
    }

    public ResponseEntity created() {
        return new ResponseEntity(HttpStatus.CREATED);
    }

    public ResponseEntity created(Object object) {
        return new ResponseEntity(object, HttpStatus.CREATED);
    }

    public ResponseEntity unauthorized() {
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity notFound(String message) {
        return new ResponseEntity(message, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity conflict(String message) {
        return new ResponseEntity(message, HttpStatus.CONFLICT);
    }
}