package com.mateusbastos.curso.pedidos.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class ServicoBancarioClient {

    public String solicitarPagamento(Double valor) {
        // Lógica para solicitar pagamento ao serviço bancário
        log.info("Solicitando pagamento no valor de: {}", valor);
        return UUID.randomUUID().toString();
    }
}
