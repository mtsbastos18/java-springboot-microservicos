package com.mateusbastos.curso.pedidos.controller;

import com.mateusbastos.curso.pedidos.controller.dto.RecebimentoCallbackPagamentoDTO;
import com.mateusbastos.curso.pedidos.controller.mappers.PagamentoCallbackMapper;
import com.mateusbastos.curso.pedidos.model.PagamentoCallback;
import com.mateusbastos.curso.pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos/callback-pagamentos")
@RequiredArgsConstructor
public class RecebimentoCallbackPagamentoController {

    private final PedidoService pedidoService;
    private final PagamentoCallbackMapper pagamentoCallbackMapper;
    @PostMapping
    public ResponseEntity<Object> atualizarStatusPagamento(
            @RequestBody RecebimentoCallbackPagamentoDTO body,
            @RequestHeader(required = true, name = "apiKey") String apiKey
            ){
        PagamentoCallback dadosPagamento = pagamentoCallbackMapper.map(body);
        pedidoService.atualizarStatusPagamento(dadosPagamento);

        return ResponseEntity.ok().build();
    }
}
