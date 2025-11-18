package com.mateusbastos.curso.pedidos.validator;

import com.mateusbastos.curso.pedidos.client.ClientesClient;
import com.mateusbastos.curso.pedidos.client.ProdutosClient;
import com.mateusbastos.curso.pedidos.client.Representation.ClienteRepresentation;
import com.mateusbastos.curso.pedidos.client.Representation.ProdutoRepresentation;
import com.mateusbastos.curso.pedidos.model.ItemPedido;
import com.mateusbastos.curso.pedidos.model.Pedido;
import com.mateusbastos.curso.pedidos.model.enums.exception.ValidationException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Objects.isNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class PedidoValidator {

    private final ProdutosClient produtosClient;
    private final ClientesClient clientesClient;

    public void validar(Pedido pedido) {
        Long codigoCliente = pedido.getCodigoCliente();
        validarCliente(codigoCliente);
        pedido.getItens().forEach(this::validarItem);
    }

    private void validarCliente(Long codigoCliente) {
        try {
            var response = clientesClient.obterDados(codigoCliente);
            ClienteRepresentation cliente = response.getBody();
            if (!isNull(cliente)) {
                log.info("Cliente encontrado: {}", cliente.nome());
            }
        } catch (FeignException.NotFound e) {
            var message = String.format("Cliente com código %d não encontrado.", codigoCliente);
            throw new ValidationException("codigoCliente", message);
        }
    }

    private void validarItem(ItemPedido item) {
        try {
            var response = produtosClient.obterDados(item.getCodigoProduto());
            ProdutoRepresentation produto = response.getBody();
            if (!isNull(produto)) {
                log.info("Produto encontrado: {}", produto.nome());
            }
        } catch (FeignException.NotFound e) {
            var message = String.format("Cliente com código %d não encontrado.", item.getCodigoProduto());
            throw new ValidationException("codigoProduto", message);
        }
    }
}
