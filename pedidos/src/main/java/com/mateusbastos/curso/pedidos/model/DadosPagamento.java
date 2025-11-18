package com.mateusbastos.curso.pedidos.model;

import com.mateusbastos.curso.pedidos.model.enums.TipoPagamento;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DadosPagamento {
    private String dados;
    private TipoPagamento tipoPagamento;
}
