package com.mateusbastos.curso.pedidos.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mateusbastos.curso.pedidos.model.Pedido;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PagamentoPublisher {

    private final DetalhePedidoMapper detalhePedidoMapper;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${icompras.config.kafka.topics.pedidos-pagos}")
    private String topicoPagamentos;

    public void publicar(Pedido pedido) {
        log.info("Publicando evento de pedido pago para o pedido de código: {}", pedido.getCodigo());

        try {
            var representationPedido = detalhePedidoMapper.map(pedido);
            var jsonPedido = objectMapper.writeValueAsString(representationPedido);

            kafkaTemplate.send(topicoPagamentos,"dados" , jsonPedido);
            log.info("Evento de pedido pago publicado com sucesso para o pedido de código: {}", pedido.getCodigo());
        } catch (JsonProcessingException error) {
            log.error( error.getMessage());
        }
    }
}
