package com.simulador.credito.clients.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.simulador.credito.clients.domain.model.Client;
import com.simulador.credito.clients.domain.repository.ClientRepository;
import com.simulador.credito.clients.infrastructure.persistence.entity.ClientEntity;

@Repository
public class ClientRepositoryImp implements ClientRepository {

    private final ClientJpaRepository clientJpaRepository;

    public ClientRepositoryImp(ClientJpaRepository clientJpaRepository) {
        this.clientJpaRepository = clientJpaRepository;
    }

    @Override
    public Client save(Client client) {
        ClientEntity entity = toEntity(client);
        return toDomain(clientJpaRepository.save(entity));
    }

    @Override
    public Optional<Client> findById(Long id) {
        return clientJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Client> findAll() {
        return clientJpaRepository.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public boolean existsByDocumentNumber(Long documentNumber) {
        return clientJpaRepository.existsByDocumentNumber(documentNumber);
    }

    @Override
    public void deleteById(Long id) {
        clientJpaRepository.deleteById(id);
    }

    private ClientEntity toEntity(Client client) {
        ClientEntity entity = new ClientEntity();
        entity.setId(client.getId());
        entity.setFullName(client.getFullName());
        entity.setDocumentNumber(client.getDocumentNumber());
        entity.setEmail(client.getEmail());
        entity.setPhone(client.getPhone());
        entity.setCreatedAt(client.getCreatedAt());
        return entity;
    }

    private Client toDomain(ClientEntity entity) {
        Client client = new Client();
        client.setId(entity.getId());
        client.setFullName(entity.getFullName());
        client.setDocumentNumber(entity.getDocumentNumber());
        client.setEmail(entity.getEmail());
        client.setPhone(entity.getPhone());
        client.setCreatedAt(entity.getCreatedAt());
        return client;
    }
}