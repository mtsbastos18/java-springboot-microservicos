package com.mateusbastos.curso.faturamento.subscriber.representation;

import java.math.BigDecimal;
import java.util.List;

public record DetalhePedidoDTO(
        Long codigo,
        Long codigoCliente,
        String nome,
        String cpf,
        String logradouro,
        String numero,
        String bairro,
        String email,
        String telefone,
        String dataPedido,
        BigDecimal total,
        List<DetalheItemPedidoDTO> itens
) {
}
