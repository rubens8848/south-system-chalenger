package br.com.forja.bits.south.services;

import br.com.forja.bits.south.enums.AgendaResult;
import br.com.forja.bits.south.enums.AgendaStatus;
import br.com.forja.bits.south.exception.BusinessException;
import br.com.forja.bits.south.exception.EntityNotFoundException;
import br.com.forja.bits.south.model.Agenda;
import br.com.forja.bits.south.model.Vote;
import br.com.forja.bits.south.model.requests.AgendaCreateRequest;
import br.com.forja.bits.south.model.requests.AgendaRequest;
import br.com.forja.bits.south.model.response.EntityResponses;
import br.com.forja.bits.south.model.response.Response;
import br.com.forja.bits.south.repositories.AgendaRepository;
import br.com.forja.bits.south.repositories.VoteRepository;
import br.com.forja.bits.south.utils.Logging;
import br.com.forja.bits.south.utils.Util;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static br.com.forja.bits.south.utils.Messages.*;

@Service
public class AgendaService {

    @Autowired
    private AgendaRepository agendaRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public ResponseEntity<?> createAgenda(AgendaCreateRequest request) {

        try {

            // convertendo e Salvando pauta
            Agenda savedAgenda = agendaRepository.save(new Agenda(request));

            return new Response(savedAgenda, AGENDA_CREATED).created();

        } catch (Exception e) {
            throw new BusinessException(FAIL_TO_CREATE_AGENDA);
        }
    }

    public ResponseEntity startAgenda(AgendaRequest request) throws Exception {

        Agenda agenda = findAgendaById(request.getAgendaId());

        if (!(agenda.getStatus().equals(AgendaStatus.WAITING) || agenda.getStatus().equals(AgendaStatus.PAUSED)))
           return new Response(INVALID_STATUS).conflict();

        //seta a data limite da pauta ou ajusta para 1 minuto da data atual
        agenda.setFinishDate(request.getFinishDate() == null
                ? Util.getInstance().makeDefaultDate()
                : request.getFinishDate());

        agenda.setStatus(AgendaStatus.OPENED);
        agendaRepository.save(agenda);

        Logging.getLog().info("pauta aberta - " + agenda.getId());

        return new Response(agenda).ok();

    }

    public Agenda findAgendaById(Integer id) throws Exception {
        return agendaRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Could not find the requested agenda"));
    }


    public void checkDueDate(Agenda agenda) {
        Date date = new Date();
        if (date.after(agenda.getFinishDate())) {
            defineAgendaResult(agenda);
            throw new BusinessException(THE_TIME_TO_VOTE_HAS_EXPIRED);
        }
    }

    private void checkDueDateForScheduling(Agenda agenda) {
        Date date = new Date();
        if (date.after(agenda.getFinishDate())) {
            defineAgendaResult(agenda);
        }
    }

    public Map isValidCpf(String cpf) throws EntityNotFoundException {
        try {
            String url = "https://user-info.herokuapp.com/users/" + cpf;
            RestTemplate restTemplate = new RestTemplate();
            Map cpfResponse = restTemplate.getForEntity(url, Map.class).getBody();

            assert cpfResponse != null;
            return cpfResponse;
        }catch (Exception e){
           throw new EntityNotFoundException(UNABLE_TO_VOTE);
        }
    }

    private void defineAgendaResult(Agenda agenda) {
        List<Vote> votes = voteRepository.findByAgenda(agenda).orElse(null);
        int approve = 0;
        int denied = 0;

        if (votes != null && votes.size() >= 1) {
            for (Vote vote : votes) {
                if (vote.isApprove())
                    approve += 1;
                else
                    denied += 1;
            }
        }

        if ((approve > denied))
            agenda.setResult(AgendaResult.APPROVED);
        else if (approve == denied)
            agenda.setResult(AgendaResult.DRAW);
        else
            agenda.setResult(AgendaResult.DENIED);

        //comunica a finalização da agenda
        try {
            new Thread(() -> {
                kafkaTemplate.send("agenda", agenda.toString());
            }).start();
        } catch (Exception e) {
            Logging.getLog().error(FAIL_TO_SEND_KAFKA_MESSAGE + " - agenda - " + e.getMessage());
        }

        agenda.setStatus(AgendaStatus.CLOSED);
        agendaRepository.save(agenda);

        Logging.getLog().info("agenda " + agenda.getTitle() + " finalizada, resultado = " + agenda.getResult());

    }

    public ResponseEntity finishAgenda(AgendaRequest request) throws Exception {

        Agenda agenda = findAgendaById(request.getAgendaId());

        if (agenda.getStatus().equals(AgendaStatus.CLOSED))
            throw new BusinessException(AGENDA_ALREADY_CLOSED);

        defineAgendaResult(agenda);

        Logging.getLog().info("pauta finalizada - " + agenda.getId());

        return new Response(agenda).ok();
    }

    public void finishExpiredAgendas() {

        List<Agenda> agendas = agendaRepository.findAllByStatus(AgendaStatus.OPENED);

        if (agendas != null && agendas.size() >= 1) {
            agendas.forEach(this::checkDueDateForScheduling);
        }
    }

    public ResponseEntity getAgendas() {

        List<Agenda> all = agendaRepository.findAll();

        return new EntityResponses().ok(all);
    }
}