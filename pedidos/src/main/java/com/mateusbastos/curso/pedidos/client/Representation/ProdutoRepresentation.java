package com.mateusbastos.curso.pedidos.client.Representation;

import java.math.BigDecimal;

public record ProdutoRepresentation(Long codigo, String nome, BigDecimal valorUnitario) {
}
