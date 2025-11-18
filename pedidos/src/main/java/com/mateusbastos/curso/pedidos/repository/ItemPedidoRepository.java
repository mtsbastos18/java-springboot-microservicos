package com.mateusbastos.curso.pedidos.repository;

import com.mateusbastos.curso.pedidos.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
}
