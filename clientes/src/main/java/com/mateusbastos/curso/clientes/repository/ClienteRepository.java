package com.mateusbastos.curso.clientes.repository;

import com.mateusbastos.curso.clientes.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
