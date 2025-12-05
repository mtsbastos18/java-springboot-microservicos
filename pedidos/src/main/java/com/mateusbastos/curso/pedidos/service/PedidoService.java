package com.mateusbastos.curso.pedidos.service;

import com.mateusbastos.curso.pedidos.client.ClientesClient;
import com.mateusbastos.curso.pedidos.client.ProdutosClient;
import com.mateusbastos.curso.pedidos.client.Representation.ClienteRepresentation;
import com.mateusbastos.curso.pedidos.client.Representation.ProdutoRepresentation;
import com.mateusbastos.curso.pedidos.client.ServicoBancarioClient;
import com.mateusbastos.curso.pedidos.model.*;
import com.mateusbastos.curso.pedidos.model.enums.StatusPedido;
import com.mateusbastos.curso.pedidos.model.exception.ItemNaoEncontradoException;
import com.mateusbastos.curso.pedidos.publisher.PagamentoPublisher;
import com.mateusbastos.curso.pedidos.repository.ItemPedidoRepository;
import com.mateusbastos.curso.pedidos.repository.PedidoRepository;
import com.mateusbastos.curso.pedidos.validator.PedidoValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoValidator pedidoValidator;
    private final ServicoBancarioClient servicoBancarioClient;
    private final ClientesClient apiClientes;
    private final ProdutosClient apiProdutos;
    private final PagamentoPublisher pagamentoPublisher;

    @Transactional
    public Pedido criarPedido(Pedido pedido) {
        // Lógica para criar um pedido
        pedidoValidator.validar(pedido);
        realizarPersistencia(pedido);
        enviarSolicitacaoPagamento(pedido);
        return pedido;
    }

    private void enviarSolicitacaoPagamento(Pedido pedido) {
        String chavePagamento = servicoBancarioClient.solicitarPagamento(pedido.getTotal().doubleValue());
        pedido.setChavePagamento(chavePagamento);
    }

    private void realizarPersistencia(Pedido pedido) {
        pedidoRepository.save(pedido);
        itemPedidoRepository.saveAll(pedido.getItens());
    }

    public void atualizarStatusPagamento(PagamentoCallback dadosPagamento) {
        // Defensive null-check for the incoming DTO
        if (dadosPagamento == null) {
            log.error("Dados de pagamento nulos ao atualizar status");
            return;
        }

        // Busca o pedido por código e chave de pagamento
        var pedidoOpt = pedidoRepository.findByCodigoAndChavePagamento(
                dadosPagamento.getCodigo(), dadosPagamento.getChavePagamento()
        );

        // Trata os dois caminhos: found / not found
        pedidoOpt.ifPresentOrElse(
                pedido -> {
                    aplicarStatusPagamento(pedido, dadosPagamento);
                    pedidoRepository.save(pedido);
                    carregarDadosCliente(pedido);
                    carregarItensPedido(pedido);
                    pagamentoPublisher.publicar(pedido);
                },
                () -> log.error(construirMensagemPedidoNaoEncontrado(dadosPagamento))
        );
    }

    // Extrai a construção da mensagem para melhorar legibilidade e evitar duplicação
    private String construirMensagemPedidoNaoEncontrado(PagamentoCallback dadosPagamento) {
        return "Pedido não encontrado para o código %d e chave de pagamento %s"
                .formatted(dadosPagamento.getCodigo(), dadosPagamento.getChavePagamento());
    }

    // Extrai a lógica de atualização de status para um metodo com responsabilidade única
    private void aplicarStatusPagamento(Pedido pedido, PagamentoCallback dadosPagamento) {
        if (Boolean.TRUE.equals(dadosPagamento.getStatus())) {
            pedido.setStatus(StatusPedido.PAGO);
        } else {
            pedido.setStatus(StatusPedido.ERRO_PAGAMENTO);
            pedido.setObservacoes(dadosPagamento.getObservacoes());
        }
    }

    @Transactional
    public void adicionarNovoPagamento(NovoPagamento pagamento) {
        // Lógica para adicionar um novo pagamento ao pedido
        var pedidoOpt = pedidoRepository.findById(pagamento.getCodigoPedido());

        pedidoOpt.ifPresentOrElse(
                pedido -> {
                    pedido.setDadosPagamento(pagamento.getDadosPagamento());
                    pedido.setStatus(StatusPedido.REALIZADO);
                    pedido.setObservacoes("Novo pagamento adicionado.");
                    enviarSolicitacaoPagamento(pedido);
                    pedidoRepository.save(pedido);
                },
                () -> {
                    log.error("Pedido não encontrado para o código %d".formatted(pagamento.getCodigoPedido()));
                    throw new ItemNaoEncontradoException("Pedido não encontrado para o código fornecido.");
                }
        );
    }

    public Optional<Pedido> carregarDadosCompletosPedido(Long codigoPedido) {
        var pedidoOpt = pedidoRepository.findById(codigoPedido);
        pedidoOpt.ifPresent(
                pedido -> {
                    carregarDadosCliente(pedido);
                    carregarItensPedido(pedido);
                }
        );

        return pedidoOpt;
    }

    private void carregarDadosCliente(Pedido pedido) {
        // Lógica para carregar os dados do cliente associado ao pedido
        // Exemplo: pedido.setDadosCliente(clienteClient.buscarClientePorCodigo(pedido.getCodigoCliente()));
        Long codigoCliente = pedido.getCodigoCliente();
        var dadosClienteResponse = apiClientes.obterDados(codigoCliente);
        pedido.setDadosCliente(dadosClienteResponse.getBody());
    }

    private void carregarItensPedido(Pedido pedido) {
        // Lógica para carregar os itens do pedido
        List<ItemPedido> itens = itemPedidoRepository.findByPedidoCodigo(pedido.getCodigo());
        pedido.setItens(itens);
        pedido.getItens().forEach(this::carregarDadosProduto);
    }

    private void carregarDadosProduto(ItemPedido item) {
        Long codigoProduto = item.getCodigoProduto();
        ResponseEntity<ProdutoRepresentation> dadosProdutoResponse = apiProdutos.obterDados(codigoProduto);
        assert dadosProdutoResponse.getBody() != null;
        item.setNome(dadosProdutoResponse.getBody().nome());
    }

}
