package com.simulador.credito.clients.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.simulador.credito.clients.application.dto.ClientResponse;
import com.simulador.credito.clients.application.dto.CreateClientRequest;
import com.simulador.credito.clients.application.exception.ClientNotFoundException;
import com.simulador.credito.clients.application.exception.DuplicateClientException;
import com.simulador.credito.clients.domain.model.Client;
import com.simulador.credito.clients.domain.repository.ClientRepository;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientResponse create(CreateClientRequest request) {
        if (clientRepository.existsByDocumentNumber(request.getDocumentNumber())) {
            throw new DuplicateClientException();
        }

        Client client = new Client();
        client.setFullName(request.getFullName());
        client.setDocumentNumber(request.getDocumentNumber());
        client.setEmail(request.getEmail());
        client.setPhone(request.getPhone());

        return toResponse(clientRepository.save(client));
    }

    public List<ClientResponse> findAll() {
        return clientRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public ClientResponse findById(Long id) {
        return clientRepository.findById(id)
                .map(this::toResponse)
            .orElseThrow(() -> new ClientNotFoundException(id));
    }

    public ClientResponse update(Long id, CreateClientRequest request) {
        Client client = clientRepository.findById(id)
            .orElseThrow(() -> new ClientNotFoundException(id));

        client.setFullName(request.getFullName());
        client.setDocumentNumber(request.getDocumentNumber());
        client.setEmail(request.getEmail());
        client.setPhone(request.getPhone());

        return toResponse(clientRepository.save(client));
    }

    public void delete(Long id) {
        if (clientRepository.findById(id).isEmpty()) {
            throw new ClientNotFoundException(id);
        }

        clientRepository.deleteById(id);
    }

    private ClientResponse toResponse(Client client) {
        return new ClientResponse(
                client.getId(),
                client.getFullName(),
                client.getDocumentNumber(),
                client.getEmail(),
                client.getPhone(),
                client.getCreatedAt());
    }
}