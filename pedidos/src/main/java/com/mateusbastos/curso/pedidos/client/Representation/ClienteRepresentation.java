package com.mateusbastos.curso.pedidos.client.Representation;

public record ClienteRepresentation(
        Long codigo,
        String nome,
        String cpf,
        String logradouro,
        String numero,
        String bairro,
        String email,
        String telefone
) {
}
