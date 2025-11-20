package com.mateusbastos.curso.pedidos.controller.mappers;

import com.mateusbastos.curso.pedidos.controller.dto.RecebimentoCallbackPagamentoDTO;
import com.mateusbastos.curso.pedidos.model.PagamentoCallback;
import org.mapstruct.Mapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface PagamentoCallbackMapper {

    PagamentoCallback map(RecebimentoCallbackPagamentoDTO dto);
}
