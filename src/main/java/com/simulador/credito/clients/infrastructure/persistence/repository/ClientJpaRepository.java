package com.simulador.credito.clients.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simulador.credito.clients.infrastructure.persistence.entity.ClientEntity;

public interface ClientJpaRepository extends JpaRepository<ClientEntity, Long> {

    boolean existsByDocumentNumber(Long documentNumber);
}