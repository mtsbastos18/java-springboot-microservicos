package com.mateusbastos.curso.pedidos.controller.mappers;

import com.mateusbastos.curso.pedidos.controller.dto.ItemPedidoDTO;
import com.mateusbastos.curso.pedidos.model.ItemPedido;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemPedidoMapper {
    ItemPedido map(ItemPedidoDTO dto);
}
