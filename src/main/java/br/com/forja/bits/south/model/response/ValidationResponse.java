package br.com.forja.bits.south.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationResponse {

    private String field;
    private String error;

}