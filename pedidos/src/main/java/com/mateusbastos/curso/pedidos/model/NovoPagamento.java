package com.mateusbastos.curso.pedidos.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NovoPagamento {
    Long codigoPedido;
    DadosPagamento dadosPagamento;
}
