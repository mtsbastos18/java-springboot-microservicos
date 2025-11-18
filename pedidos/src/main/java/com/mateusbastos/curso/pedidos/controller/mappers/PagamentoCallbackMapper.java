package com.mateusbastos.curso.pedidos.controller.mappers;

import com.mateusbastos.curso.pedidos.controller.dto.RecebimentoCallbackPagamentoDTO;
import com.mateusbastos.curso.pedidos.model.PagamentoCallback;
import org.mapstruct.Mapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface PagamentoCallbackMapper {

    @Mapping(source="codigo", target="codigo")
    @Mapping(source="chavePagamento", target="chavePagamento")
    @Mapping(source="status", target="status")
    @Mapping(source="observacoes", target="observacoes")
    PagamentoCallback map(RecebimentoCallbackPagamentoDTO dto);
}
