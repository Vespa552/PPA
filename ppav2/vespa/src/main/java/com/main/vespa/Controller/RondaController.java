package com.main.vespa.Controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.main.vespa.Model.Ocorrencia;
import com.main.vespa.Model.Ronda;
import com.main.vespa.Model.SituacaoRonda;
import com.main.vespa.Service.OcorrenciaService;
import com.main.vespa.Service.RondaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rondas")
@RequiredArgsConstructor
public class RondaController {

    private final RondaService service;
    private final OcorrenciaService ocorrenciaService;

    /** Filtros opcionais: ?situacao=DESCUMPRIMENTO ou ?inicio=...&fim=... (ISO, ex: 2026-11-01T00:00:00). */
    @GetMapping
    public List<Ronda> listar(
            @RequestParam(required = false) SituacaoRonda situacao,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        if (inicio != null && fim != null) {
            return service.listarPorPeriodo(inicio, fim);
        }
        if (situacao != null) {
            return service.listarPorSituacao(situacao);
        }
        return service.listar();
    }

    @GetMapping("/{id}")
    public Ronda buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<Ronda> criar(@RequestBody Ronda ronda) {
        Ronda salva = service.salvar(ronda);
        return ResponseEntity.created(URI.create("/rondas/" + salva.getId())).body(salva);
    }

    @PutMapping("/{id}")
    public Ronda atualizar(@PathVariable Long id, @RequestBody Ronda ronda) {
        return service.atualizar(id, ronda);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/ocorrencias")
    public List<Ocorrencia> ocorrencias(@PathVariable Long id) {
        return ocorrenciaService.listarPorRonda(id);
    }
}
