package com.mateusbastos.curso.faturamento.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mateusbastos.curso.faturamento.mapper.PedidoMapper;
import com.mateusbastos.curso.faturamento.model.Pedido;
import com.mateusbastos.curso.faturamento.service.NotaFiscalService;
import com.mateusbastos.curso.faturamento.subscriber.representation.DetalhePedidoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PedidoPagoSubscriber {
    private final ObjectMapper mapper;
    private final NotaFiscalService notaFiscalService;
    private final PedidoMapper pedidoMapper;

    @KafkaListener(groupId = "icompras-faturamento", topics = "${icompras.config.kafka.topics.pedidos-pagos}")
    public void topicListener(String message) {
        try {
            log.info("Mensagem recebida do tópico 'pedidos-pagos': {}", message);
            var dadosPedido = mapper.readValue(message, DetalhePedidoDTO.class);
            Pedido pedido = pedidoMapper.map(dadosPedido);
            notaFiscalService.emitirNotaFiscal(pedido);
        } catch (Exception e) {
            log.error("Erro ao processar mensagem do tópico 'pedidos-pagos': {}", e.getMessage(), e);
        }
    }
}
