package com.mateusbastos.curso.pedidos.controller;

import com.mateusbastos.curso.pedidos.controller.dto.NovoPagamentoDTO;
import com.mateusbastos.curso.pedidos.controller.dto.NovoPedidoDTO;
import com.mateusbastos.curso.pedidos.controller.mappers.NovoPagamentoMapper;
import com.mateusbastos.curso.pedidos.controller.mappers.PedidoMapper;
import com.mateusbastos.curso.pedidos.model.ErroResposta;
import com.mateusbastos.curso.pedidos.model.exception.ItemNaoEncontradoException;
import com.mateusbastos.curso.pedidos.model.exception.ValidationException;
import com.mateusbastos.curso.pedidos.publisher.DetalhePedidoMapper;
import com.mateusbastos.curso.pedidos.publisher.representation.DetalhePedidoDTO;
import com.mateusbastos.curso.pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoMapper pedidoMapper;
    private final NovoPagamentoMapper novoPagamentoMapper;
    private final DetalhePedidoMapper detalhePedidoMapper;

    @PostMapping
    public ResponseEntity<Object> criar(@RequestBody NovoPedidoDTO dto) {
        try {
            var pedido = pedidoMapper.map(dto);
            var novoPedido = pedidoService.criarPedido(pedido);
            return ResponseEntity.ok(novoPedido.getCodigo());
        } catch (ValidationException e) {
            var erro = new ErroResposta("Erro de validação", e.getField(), e.getMessage());
            return ResponseEntity.badRequest().body(erro);
        }
    }

    @PutMapping("pagamentos")
    public ResponseEntity<Object> atualizarPagamento(@RequestBody NovoPagamentoDTO dadosNovoPagamentoDTO) {
        // Implementação futura
        try {
            var novoPagamento = novoPagamentoMapper.map(dadosNovoPagamentoDTO);
            pedidoService.adicionarNovoPagamento(novoPagamento);
            return ResponseEntity.noContent().build();
        } catch (ItemNaoEncontradoException error) {
            return ResponseEntity.badRequest().body(new ErroResposta("Pedido não encontrado", "codigoPedido", error.getMessage()));
        }
    }

    @GetMapping("{codigo}")
    public ResponseEntity<DetalhePedidoDTO> obterPedido(@PathVariable Long codigo) {
        return pedidoService.carregarDadosCompletosPedido(codigo)
                .map(detalhePedidoMapper::map)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
