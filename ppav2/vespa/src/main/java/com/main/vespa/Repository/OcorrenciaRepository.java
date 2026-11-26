package com.main.vespa.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.vespa.Model.Ocorrencia;
import com.main.vespa.Model.TipoOcorrencia;

@Repository
public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long> {
    List<Ocorrencia> findByRondaId(Long rondaId);
    List<Ocorrencia> findByRondaVitimaId(Long vitimaId);
    List<Ocorrencia> findByTipo(TipoOcorrencia tipo);
}
