package br.com.forja.bits.south.controller;

import br.com.forja.bits.south.model.requests.VoteRequest;
import br.com.forja.bits.south.services.AgendaService;
import br.com.forja.bits.south.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/vote")
public class VoteController {

    @Autowired
    private VoteService voteService;

    @PostMapping("")
    private ResponseEntity vote(@RequestBody @Valid VoteRequest request) throws Exception {

        return voteService.vote(request);
    }


}