package br.com.forja.bits.south.services;

import br.com.forja.bits.south.enums.AgendaStatus;
import br.com.forja.bits.south.middlewares.ValidationsMiddleware;
import br.com.forja.bits.south.model.Agenda;
import br.com.forja.bits.south.model.User;
import br.com.forja.bits.south.model.Vote;
import br.com.forja.bits.south.model.requests.VoteRequest;
import br.com.forja.bits.south.model.response.Response;
import br.com.forja.bits.south.repositories.VoteRepository;
import br.com.forja.bits.south.utils.Logging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static br.com.forja.bits.south.utils.Messages.*;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private AgendaService agendaService;

    @Autowired
    private ValidationsMiddleware validator;

    //usado para prevenir clique duplo
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResponseEntity vote(VoteRequest request) throws Exception {

        User user = validator.authenticatedUser();

        Agenda agenda = agendaService.findAgendaById(request.getAgendaId());

        if (!agenda.getStatus().equals(AgendaStatus.OPENED))
            return new Response(AGENDA_NOT_OPENED).badRequest();

        //verificando data de vencimento
        agendaService.checkDueDate(agenda);

        //verificando se o cpf está habilitado para votar
        Map cpfResponse = agendaService.isValidCpf(user.getCpf());

        //verifica se o usuário já votou
        if (voteRepository.findByAgendaAndUser(agenda, user).isPresent())
            return new Response(USERS_ALREADY_VOTED).conflict();

        if (cpfResponse.get("status").equals("ABLE_TO_VOTE")) {

            //salvando voto
            voteRepository.save(new Vote(request, user, agenda));

            Logging.getLog().info("voto computado, pauta: " + agenda.getId() + " - user: " + user.getId());

            return new Response((ABLE_TO_VOTE)).ok();

        } else {
            return new Response(UNABLE_TO_VOTE).notFound();
        }

    }


}
