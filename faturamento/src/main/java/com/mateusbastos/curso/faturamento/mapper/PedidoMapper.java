package com.mateusbastos.curso.faturamento.mapper;

import com.mateusbastos.curso.faturamento.model.Cliente;
import com.mateusbastos.curso.faturamento.model.ItemPedido;
import com.mateusbastos.curso.faturamento.model.Pedido;
import com.mateusbastos.curso.faturamento.subscriber.representation.DetalhePedidoDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoMapper {

    public Pedido map(DetalhePedidoDTO detalhePedidoDTO) {
        Cliente cliente = new Cliente(
                detalhePedidoDTO.nome(),
                detalhePedidoDTO.cpf(),
                detalhePedidoDTO.logradouro(),
                detalhePedidoDTO.numero(),
                detalhePedidoDTO.bairro(),
                detalhePedidoDTO.email(),
                detalhePedidoDTO.telefone()
        );

        List<ItemPedido> itens = getListItems(detalhePedidoDTO);

        return new Pedido(
                detalhePedidoDTO.codigo(),
                cliente,
                detalhePedidoDTO.dataPedido(),
                detalhePedidoDTO.total(),
                itens
        );
    }

    @NotNull
    private static List<ItemPedido> getListItems(DetalhePedidoDTO detalhePedidoDTO) {
        return detalhePedidoDTO.itens().stream()
                .map(itemDTO -> new ItemPedido(
                        itemDTO.codigoProduto(),
                        itemDTO.nome(),
                        itemDTO.valorUnitario(),
                        itemDTO.quantidade(),
                        itemDTO.total()
                ))
                .toList();
    }
}
