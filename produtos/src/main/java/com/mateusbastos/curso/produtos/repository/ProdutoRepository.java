package com.mateusbastos.curso.produtos.repository;

import com.mateusbastos.curso.produtos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
