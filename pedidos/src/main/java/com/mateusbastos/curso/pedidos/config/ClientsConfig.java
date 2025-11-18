package com.mateusbastos.curso.pedidos.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.mateusbastos.curso.pedidos.client")
public class ClientsConfig {

}
