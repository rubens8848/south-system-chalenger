package br.com.forja.bits.south.scheduler;

import br.com.forja.bits.south.services.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
public class AgendaScheduler {

    @Autowired
    private AgendaService agendaService;

    public static final int EVERY_MINUTE = 60000;

    //verifica a cada um minuto pautas que ultrapassaram o tempo hábil de votação
    @Scheduled(fixedDelay = EVERY_MINUTE)
    void checkingExpiredAgendas() {
        agendaService.finishExpiredAgendas();
        System.out.println("finalizando agendas");
    }
}
