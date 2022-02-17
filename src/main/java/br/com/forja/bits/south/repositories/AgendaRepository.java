package br.com.forja.bits.south.repositories;

import br.com.forja.bits.south.enums.AgendaStatus;
import br.com.forja.bits.south.model.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Integer> {

    List<Agenda> findAllByStatus(AgendaStatus agendaStatus);
}
