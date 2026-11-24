package com.main.vespa.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.vespa.Model.Agente;

@Repository
public interface AgenteRepository extends JpaRepository<Agente, Long> {
    Optional<Agente> findByMatricula(String matricula);
    boolean existsByMatricula(String matricula);
}
