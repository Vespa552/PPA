package com.main.vespa.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.main.vespa.Model.Ronda;
import com.main.vespa.Model.SituacaoRonda;
import com.main.vespa.Repository.RondaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RondaService {

    private final RondaRepository repository;
    private final VitimaService vitimaService;
    private final AgenteService agenteService;

    public List<Ronda> listar() {
        return repository.findAll();
    }

    public Ronda buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ronda não encontrada: " + id));
    }

    public Ronda salvar(Ronda ronda) {
        ronda.setId(null);
        prepararEValidar(ronda);
        return repository.save(ronda);
    }

    public Ronda atualizar(Long id, Ronda ronda) {
        buscarPorId(id);
        ronda.setId(id);
        prepararEValidar(ronda);
        return repository.save(ronda);
    }

    public void deletar(Long id) {
        buscarPorId(id);
        repository.deleteById(id);
    }

    public List<Ronda> listarPorVitima(Long vitimaId) {
        vitimaService.buscarPorId(vitimaId);
        return repository.findByVitimaId(vitimaId);
    }

    public List<Ronda> listarPorAgente(Long agenteId) {
        agenteService.buscarPorId(agenteId);
        return repository.findByAgenteId(agenteId);
    }

    public List<Ronda> listarPorSituacao(SituacaoRonda situacao) {
        return repository.findBySituacao(situacao);
    }

    public List<Ronda> listarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return repository.findByDataHoraBetween(inicio, fim);
    }

    private void prepararEValidar(Ronda ronda) {
        if (ronda.getVitima() == null || ronda.getVitima().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe a vítima (vitima.id)");
        }
        if (ronda.getSituacao() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe a situação da ronda");
        }
        ronda.setVitima(vitimaService.buscarPorId(ronda.getVitima().getId()));
        if (ronda.getAgente() != null && ronda.getAgente().getId() != null) {
            ronda.setAgente(agenteService.buscarPorId(ronda.getAgente().getId()));
        } else {
            ronda.setAgente(null);
        }
        if (ronda.getDataHora() == null) {
            ronda.setDataHora(LocalDateTime.now());
        }
    }
}
