package com.mateusbastos.curso.faturamento.service;

import com.mateusbastos.curso.faturamento.model.Pedido;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotaFiscalService {

    public void emitirNotaFiscal(Pedido pedido) {
        // Lógica para emitir a nota fiscal
        log.info("Emitindo nota fiscal para o pedido ID: {}", pedido.codigo());
    }
}
