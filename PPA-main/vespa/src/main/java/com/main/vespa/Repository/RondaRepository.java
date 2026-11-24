package com.main.vespa.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.main.vespa.Model.Ronda;
import com.main.vespa.Model.SituacaoRonda;

@Repository
public interface RondaRepository extends JpaRepository<Ronda, Long> {
    List<Ronda> findByVitimaId(Long vitimaId);
    List<Ronda> findByAgenteId(Long agenteId);
    List<Ronda> findBySituacao(SituacaoRonda situacao);
    List<Ronda> findByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim);
    long countBySituacao(SituacaoRonda situacao);

    // estatística por região (usando o campo "local" da ronda)
    @Query("select r.local, count(r) from Ronda r group by r.local order by count(r) desc")
    List<Object[]> contarPorLocal();
}
