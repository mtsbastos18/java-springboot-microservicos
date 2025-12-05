package com.mateusbastos.curso.pedidos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class PedidosApplication {

//    @Bean
//    public CommandLineRunner commandLineRunner(KafkaTemplate<String, String> kafkaTemplate) {
//        return args -> {
//            // Exemplo de envio de mensagem para o Kafka
//            kafkaTemplate.send("icompras.pedidos-pagos", "dados", "{ json }" );
//        };
//    }

	public static void main(String[] args) {
		SpringApplication.run(PedidosApplication.class, args);
	}

}
