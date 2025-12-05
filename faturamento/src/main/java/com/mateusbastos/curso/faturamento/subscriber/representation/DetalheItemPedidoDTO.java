package com.mateusbastos.curso.faturamento.subscriber.representation;

import java.math.BigDecimal;

public record DetalheItemPedidoDTO(
        Long codigoProduto,
        String nome,
        Integer quantidade,
        BigDecimal valorUnitario,
        BigDecimal total
) {

}
