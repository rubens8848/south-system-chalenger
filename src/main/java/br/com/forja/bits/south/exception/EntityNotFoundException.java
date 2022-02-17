package br.com.forja.bits.south.exception;

import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND )
public class EntityNotFoundException  extends NotFoundException {

    public EntityNotFoundException(String msg) {
        super(msg);
    }


}
