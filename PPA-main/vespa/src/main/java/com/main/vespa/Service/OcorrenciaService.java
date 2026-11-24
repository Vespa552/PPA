package com.main.vespa.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.main.vespa.Model.Ocorrencia;
import com.main.vespa.Model.TipoOcorrencia;
import com.main.vespa.Repository.OcorrenciaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OcorrenciaService {

    private final OcorrenciaRepository repository;
    private final RondaService rondaService;
    private final VitimaService vitimaService;

    public List<Ocorrencia> listar() {
        return repository.findAll();
    }

    public Ocorrencia buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ocorrência não encontrada: " + id));
    }

    public Ocorrencia salvar(Ocorrencia ocorrencia) {
        ocorrencia.setId(null);
        prepararEValidar(ocorrencia);
        return repository.save(ocorrencia);
    }

    public Ocorrencia atualizar(Long id, Ocorrencia ocorrencia) {
        buscarPorId(id);
        ocorrencia.setId(id);
        prepararEValidar(ocorrencia);
        return repository.save(ocorrencia);
    }

    public void deletar(Long id) {
        buscarPorId(id);
        repository.deleteById(id);
    }

    public List<Ocorrencia> listarPorRonda(Long rondaId) {
        rondaService.buscarPorId(rondaId);
        return repository.findByRondaId(rondaId);
    }

    /** Histórico de ocorrências de uma vítima (via rondas). */
    public List<Ocorrencia> listarPorVitima(Long vitimaId) {
        vitimaService.buscarPorId(vitimaId);
        return repository.findByRondaVitimaId(vitimaId);
    }

    public List<Ocorrencia> listarPorTipo(TipoOcorrencia tipo) {
        return repository.findByTipo(tipo);
    }

    private void prepararEValidar(Ocorrencia ocorrencia) {
        if (ocorrencia.getRonda() == null || ocorrencia.getRonda().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe a ronda (ronda.id)");
        }
        if (ocorrencia.getTipo() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe o tipo da ocorrência");
        }
        ocorrencia.setRonda(rondaService.buscarPorId(ocorrencia.getRonda().getId()));
        if (ocorrencia.getDataHora() == null) {
            ocorrencia.setDataHora(LocalDateTime.now());
        }
    }
}
