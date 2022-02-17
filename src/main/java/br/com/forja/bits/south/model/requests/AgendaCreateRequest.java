package br.com.forja.bits.south.model.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class AgendaCreateRequest {

    @NotBlank()
    @NotNull()
    private String title;

    private String description;

    private Date finishDate;

}