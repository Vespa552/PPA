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

import com.main.vespa.Model.MedidaProtetiva;
import com.main.vespa.Model.Ocorrencia;
import com.main.vespa.Model.Ronda;
import com.main.vespa.Model.Vitima;
import com.main.vespa.Service.MedidaProtetivaService;
import com.main.vespa.Service.OcorrenciaService;
import com.main.vespa.Service.RondaService;
import com.main.vespa.Service.VitimaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/vitimas")
@RequiredArgsConstructor
public class VitimaController {

    private final VitimaService service;
    private final MedidaProtetivaService medidaService;
    private final RondaService rondaService;
    private final OcorrenciaService ocorrenciaService;

    @GetMapping
    public List<Vitima> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Vitima buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<Vitima> criar(@RequestBody Vitima vitima) {
        Vitima salva = service.salvar(vitima);
        return ResponseEntity.created(URI.create("/vitimas/" + salva.getId())).body(salva);
    }

    @PutMapping("/{id}")
    public Vitima atualizar(@PathVariable Long id, @RequestBody Vitima vitima) {
        return service.atualizar(id, vitima);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // ---- histórico da vítima ----

    @GetMapping("/{id}/medidas-protetivas")
    public List<MedidaProtetiva> medidas(@PathVariable Long id) {
        return medidaService.listarPorVitima(id);
    }

    @GetMapping("/{id}/medidas-protetivas/vigentes")
    public List<MedidaProtetiva> medidasVigentes(@PathVariable Long id) {
        return medidaService.listarVigentesPorVitima(id);
    }

    @GetMapping("/{id}/rondas")
    public List<Ronda> rondas(@PathVariable Long id) {
        return rondaService.listarPorVitima(id);
    }

    @GetMapping("/{id}/ocorrencias")
    public List<Ocorrencia> ocorrencias(@PathVariable Long id) {
        return ocorrenciaService.listarPorVitima(id);
    }
}
