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
import org.springframework.web.bind.annotation.RestController;

import com.main.vespa.Model.Agente;
import com.main.vespa.Model.Ronda;
import com.main.vespa.Service.AgenteService;
import com.main.vespa.Service.RondaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/agentes")
@RequiredArgsConstructor
public class AgenteController {

    private final AgenteService service;
    private final RondaService rondaService;

    @GetMapping
    public List<Agente> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Agente buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<Agente> criar(@RequestBody Agente agente) {
        Agente salvo = service.salvar(agente);
        return ResponseEntity.created(URI.create("/agentes/" + salvo.getId())).body(salvo);
    }

    @PutMapping("/{id}")
    public Agente atualizar(@PathVariable Long id, @RequestBody Agente agente) {
        return service.atualizar(id, agente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/rondas")
    public List<Ronda> rondas(@PathVariable Long id) {
        return rondaService.listarPorAgente(id);
    }
}
