package com.mateusbastos.curso.produtos.service;

import com.mateusbastos.curso.produtos.model.Produto;
import com.mateusbastos.curso.produtos.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Optional<Produto> obterPorCodigo(Long codigo) {
        return produtoRepository.findById(codigo);
    }

    public Produto[] obterTodos() {
        var produtos = produtoRepository.findAll();
        return produtos.toArray(new Produto[0]);
    }
}
