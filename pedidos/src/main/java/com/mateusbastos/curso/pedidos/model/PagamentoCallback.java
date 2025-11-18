package com.mateusbastos.curso.pedidos.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagamentoCallback {
    private Long codigo;
    private String chavePagamento;
    private Boolean status;
    private String observacoes;
}
