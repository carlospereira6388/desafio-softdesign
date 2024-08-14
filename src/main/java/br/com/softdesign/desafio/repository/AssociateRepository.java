package br.com.softdesign.desafio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.softdesign.desafio.model.Associate;

public interface AssociateRepository extends JpaRepository<Associate, Long> {
	
    Optional<Associate> findByDocument(String document);
}