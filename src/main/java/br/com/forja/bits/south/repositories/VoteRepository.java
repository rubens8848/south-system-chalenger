package br.com.forja.bits.south.repositories;

import br.com.forja.bits.south.model.Agenda;
import br.com.forja.bits.south.model.User;
import br.com.forja.bits.south.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    Optional<List<Vote>> findByAgenda(Agenda agenda);

    Optional<List<Vote>> findByAgendaAndUser(Agenda agenda, User user);

}
