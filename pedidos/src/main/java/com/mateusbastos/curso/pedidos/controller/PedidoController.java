package com.mateusbastos.curso.pedidos.controller;

import com.mateusbastos.curso.pedidos.controller.dto.NovoPedidoDTO;
import com.mateusbastos.curso.pedidos.controller.mappers.PedidoMapper;
import com.mateusbastos.curso.pedidos.model.ErroResposta;
import com.mateusbastos.curso.pedidos.model.Pedido;
import com.mateusbastos.curso.pedidos.model.enums.exception.ValidationException;
import com.mateusbastos.curso.pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoMapper pedidoMapper;

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
}
