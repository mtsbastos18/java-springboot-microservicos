package com.mateusbastos.curso.pedidos.controller.mappers;

import com.mateusbastos.curso.pedidos.controller.dto.ItemPedidoDTO;
import com.mateusbastos.curso.pedidos.controller.dto.NovoPedidoDTO;
import com.mateusbastos.curso.pedidos.model.ItemPedido;
import com.mateusbastos.curso.pedidos.model.Pedido;
import com.mateusbastos.curso.pedidos.model.enums.StatusPedido;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    ItemPedidoMapper ITEM_PEDIDO_MAPPER = Mappers.getMapper(ItemPedidoMapper.class);

    @Mapping(source = "itens", target = "itens", qualifiedByName = "mapItens")
    @Mapping(source="dadosPagamento", target="dadosPagamento")
    Pedido map(NovoPedidoDTO dto);

    @Named("mapItens")
    default List<ItemPedido> mapItens(List<ItemPedidoDTO> dtos) {
        return dtos.stream().map(ITEM_PEDIDO_MAPPER::map).toList();
    }

    @AfterMapping
    default void afterMapping(@MappingTarget Pedido pedido) {
        pedido.setStatus(StatusPedido.REALIZADO);
        pedido.setDataPedido(LocalDateTime.now());

        var total = calcularTotal(pedido);

        pedido.setTotal(total);

        pedido.getItens().forEach(itemPedido -> itemPedido.setPedido(pedido));
    }

    private static BigDecimal calcularTotal(Pedido pedido) {
        return pedido.getItens().stream()
                .map(itemPedido ->
                        itemPedido.getValorUnitario().multiply(BigDecimal.valueOf(itemPedido.getQuantidade()))
                ).reduce(BigDecimal.ZERO, BigDecimal::add).abs();
    }
}
