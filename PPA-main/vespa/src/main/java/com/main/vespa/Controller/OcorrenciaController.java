package com.main.vespa.Controller;

import java.net.URI;
import java.util.List;

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
import com.main.vespa.Model.TipoOcorrencia;
import com.main.vespa.Service.OcorrenciaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ocorrencias")
@RequiredArgsConstructor
public class OcorrenciaController {

    private final OcorrenciaService service;

    @GetMapping
    public List<Ocorrencia> listar(@RequestParam(required = false) TipoOcorrencia tipo) {
        return tipo == null ? service.listar() : service.listarPorTipo(tipo);
    }

    @GetMapping("/{id}")
    public Ocorrencia buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<Ocorrencia> criar(@RequestBody Ocorrencia ocorrencia) {
        Ocorrencia salva = service.salvar(ocorrencia);
        return ResponseEntity.created(URI.create("/ocorrencias/" + salva.getId())).body(salva);
    }

    @PutMapping("/{id}")
    public Ocorrencia atualizar(@PathVariable Long id, @RequestBody Ocorrencia ocorrencia) {
        return service.atualizar(id, ocorrencia);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
