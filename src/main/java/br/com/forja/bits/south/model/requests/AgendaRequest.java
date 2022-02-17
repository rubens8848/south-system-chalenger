package br.com.forja.bits.south.model.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class AgendaRequest {

    @NotNull()
    @Positive
    private Integer agendaId;

    private Date finishDate;
}