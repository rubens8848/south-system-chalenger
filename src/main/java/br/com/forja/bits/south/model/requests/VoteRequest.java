package br.com.forja.bits.south.model.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Builder
@AllArgsConstructor
public class VoteRequest {

    @NotNull()
    @Positive
    private Integer agendaId;

    @NotNull
    private boolean approve;
}