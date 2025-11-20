package com.mateusbastos.curso.pedidos.controller.mappers;

import com.mateusbastos.curso.pedidos.controller.dto.NovoPagamentoDTO;
import com.mateusbastos.curso.pedidos.model.NovoPagamento;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NovoPagamentoMapper {

    NovoPagamento map(NovoPagamentoDTO novoPagamentoDTO);
}
