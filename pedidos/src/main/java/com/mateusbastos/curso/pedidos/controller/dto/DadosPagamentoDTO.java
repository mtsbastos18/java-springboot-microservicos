package com.mateusbastos.curso.pedidos.controller.dto;

import com.mateusbastos.curso.pedidos.model.enums.TipoPagamento;

public record DadosPagamentoDTO(
        String dados,
        TipoPagamento tipoPagamento
) {

}
