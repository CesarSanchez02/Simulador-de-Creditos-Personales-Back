package com.simulador.credito.clients.domain.repository;

import java.util.List;
import java.util.Optional;

import com.simulador.credito.clients.domain.model.Client;

public interface ClientRepository {

    Client save(Client client);

    Optional<Client> findById(Long id);

    List<Client> findAll();

    boolean existsByDocumentNumber(Long documentNumber);

    void deleteById(Long id);
}