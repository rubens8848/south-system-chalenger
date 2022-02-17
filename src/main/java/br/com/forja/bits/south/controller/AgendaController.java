package br.com.forja.bits.south.controller;

import br.com.forja.bits.south.model.requests.AgendaCreateRequest;
import br.com.forja.bits.south.model.requests.AgendaRequest;
import br.com.forja.bits.south.services.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/agenda")
public class AgendaController {

    @Autowired
    private AgendaService agendaService;

    @PostMapping("")
    private ResponseEntity create(@RequestBody @Valid AgendaCreateRequest request) throws Exception {

        return agendaService.createAgenda(request);
    }

    @PostMapping("/start")
    private ResponseEntity start(@RequestBody @Valid AgendaRequest request) throws Exception {

        return agendaService.startAgenda(request);
    }

    @PostMapping("/finish")
    private ResponseEntity finish(@RequestBody @Valid AgendaRequest request) throws Exception {

        return agendaService.finishAgenda(request);
    }

    @GetMapping()
    private ResponseEntity getAgendas() throws Exception {

        return agendaService.getAgendas();
    }


}