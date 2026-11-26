package com.main.vespa.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.vespa.Model.Agressor;

@Repository
public interface AgressorRepository extends JpaRepository<Agressor, Long> {
    Optional<Agressor> findByCpf(String cpf);
    boolean existsByCpf(String cpf);
}
