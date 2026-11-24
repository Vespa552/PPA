package com.main.vespa.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.vespa.Model.Vitima;

@Repository
public interface VitimaRepository extends JpaRepository<Vitima, Long> {
    Optional<Vitima> findByCpf(String cpf);
    boolean existsByCpf(String cpf);
}
