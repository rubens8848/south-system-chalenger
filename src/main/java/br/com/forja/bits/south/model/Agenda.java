package br.com.forja.bits.south.model;

import br.com.forja.bits.south.enums.AgendaResult;
import br.com.forja.bits.south.enums.AgendaStatus;
import br.com.forja.bits.south.model.requests.AgendaCreateRequest;
import br.com.forja.bits.south.utils.Util;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Agenda implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Builder.Default
    private AgendaStatus status = AgendaStatus.WAITING;

    private String title;

    private String description;

    private AgendaResult result;

    private Date finishDate;


    public Agenda(AgendaCreateRequest request) {
        this.title = request.getTitle();
        this.description = this.getDescription();
        this.status = AgendaStatus.WAITING;
//        this.finishDate = request.getFinishDate() == null ? Util.getInstance().makeDefaultDate() : request.getFinishDate();
    }

}