package com.main.vespa.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.vespa.Model.MedidaProtetiva;
import com.main.vespa.Model.StatusMedida;

@Repository
public interface MedidaProtetivaRepository extends JpaRepository<MedidaProtetiva, Long> {
    List<MedidaProtetiva> findByVitimaId(Long vitimaId);
    List<MedidaProtetiva> findByAgressorId(Long agressorId);
    List<MedidaProtetiva> findByStatus(StatusMedida status);
    List<MedidaProtetiva> findByVitimaIdAndStatus(Long vitimaId, StatusMedida status);

    // medidas ativas que vencem dentro de um intervalo (alerta de vencimento)
    List<MedidaProtetiva> findByStatusAndDataFimBetween(StatusMedida status, LocalDate inicio, LocalDate fim);

    // medidas ativas cuja data fim já passou (para marcar como vencidas)
    List<MedidaProtetiva> findByStatusAndDataFimBefore(StatusMedida status, LocalDate data);
}
