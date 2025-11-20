package com.mateusbastos.curso.pedidos.controller.dto;

public record NovoPagamentoDTO(
        Long codigoPedido,
        DadosPagamentoDTO dadosPagamento
) {
}
