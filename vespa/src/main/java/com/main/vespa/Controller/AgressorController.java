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

import com.main.vespa.Model.Agressor;
import com.main.vespa.Model.MedidaProtetiva;
import com.main.vespa.Service.AgressorService;
import com.main.vespa.Service.MedidaProtetivaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/agressores")
@RequiredArgsConstructor
public class AgressorController {

    private final AgressorService service;
    private final MedidaProtetivaService medidaService;

    @GetMapping
    public List<Agressor> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Agressor buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<Agressor> criar(@RequestBody Agressor agressor) {
        Agressor salvo = service.salvar(agressor);
        return ResponseEntity.created(URI.create("/agressores/" + salvo.getId())).body(salvo);
    }

    @PutMapping("/{id}")
    public Agressor atualizar(@PathVariable Long id, @RequestBody Agressor agressor) {
        return service.atualizar(id, agressor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/medidas-protetivas")
    public List<MedidaProtetiva> medidas(@PathVariable Long id) {
        return medidaService.listarPorAgressor(id);
    }
}
