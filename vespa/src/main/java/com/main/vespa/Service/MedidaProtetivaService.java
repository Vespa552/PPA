package com.main.vespa.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.main.vespa.Model.MedidaProtetiva;
import com.main.vespa.Model.StatusMedida;
import com.main.vespa.Repository.MedidaProtetivaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedidaProtetivaService {

    private final MedidaProtetivaRepository repository;
    private final VitimaService vitimaService;
    private final AgressorService agressorService;

    public List<MedidaProtetiva> listar() {
        return repository.findAll();
    }

    public MedidaProtetiva buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Medida protetiva não encontrada: " + id));
    }

    public MedidaProtetiva salvar(MedidaProtetiva medida) {
        medida.setId(null);
        prepararEValidar(medida);
        return repository.save(medida);
    }

    public MedidaProtetiva atualizar(Long id, MedidaProtetiva medida) {
        buscarPorId(id);
        medida.setId(id);
        prepararEValidar(medida);
        return repository.save(medida);
    }

    public void deletar(Long id) {
        buscarPorId(id);
        repository.deleteById(id);
    }

    // ---------- consultas / alertas ----------

    public List<MedidaProtetiva> listarPorVitima(Long vitimaId) {
        vitimaService.buscarPorId(vitimaId);
        return repository.findByVitimaId(vitimaId);
    }

    public List<MedidaProtetiva> listarVigentesPorVitima(Long vitimaId) {
        vitimaService.buscarPorId(vitimaId);
        return repository.findByVitimaIdAndStatus(vitimaId, StatusMedida.ATIVA);
    }

    public List<MedidaProtetiva> listarPorAgressor(Long agressorId) {
        agressorService.buscarPorId(agressorId);
        return repository.findByAgressorId(agressorId);
    }

    public List<MedidaProtetiva> listarPorStatus(StatusMedida status) {
        return repository.findByStatus(status);
    }

    /** Alerta: medidas ativas que vencem nos próximos {@code dias} dias. */
    public List<MedidaProtetiva> listarVencendo(int dias) {
        LocalDate hoje = LocalDate.now();
        return repository.findByStatusAndDataFimBetween(StatusMedida.ATIVA, hoje, hoje.plusDays(dias));
    }

    /** Alerta: medidas marcadas como descumpridas. */
    public List<MedidaProtetiva> listarDescumpridas() {
        return repository.findByStatus(StatusMedida.DESCUMPRIDA);
    }

    /** Marca como VENCIDA toda medida ativa cuja data fim já passou. Retorna quantas foram atualizadas. */
    @Transactional
    public int atualizarVencidas() {
        List<MedidaProtetiva> vencidas = repository.findByStatusAndDataFimBefore(StatusMedida.ATIVA, LocalDate.now());
        vencidas.forEach(m -> m.setStatus(StatusMedida.VENCIDA));
        repository.saveAll(vencidas);
        return vencidas.size();
    }

    @Transactional
    public MedidaProtetiva marcarDescumprida(Long id) {
        MedidaProtetiva medida = buscarPorId(id);
        medida.setStatus(StatusMedida.DESCUMPRIDA);
        return repository.save(medida);
    }

    // ---------- regras ----------

    private void prepararEValidar(MedidaProtetiva medida) {
        if (medida.getVitima() == null || medida.getVitima().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe a vítima (vitima.id)");
        }
        if (medida.getAgressor() == null || medida.getAgressor().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe o agressor (agressor.id)");
        }
        if (medida.getDataFim() != null && medida.getDataInicio() != null
                && medida.getDataFim().isBefore(medida.getDataInicio())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "dataFim não pode ser anterior a dataInicio");
        }
        medida.setVitima(vitimaService.buscarPorId(medida.getVitima().getId()));
        medida.setAgressor(agressorService.buscarPorId(medida.getAgressor().getId()));
        if (medida.getStatus() == null) {
            medida.setStatus(StatusMedida.ATIVA);
        }
    }
}
