package com.mateusbastos.curso.clientes.service;

import com.mateusbastos.curso.clientes.model.Cliente;
import com.mateusbastos.curso.clientes.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public Cliente salvar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> obterPorCodigo(Long codigo) {
        return clienteRepository.findById(codigo);
    }
}
