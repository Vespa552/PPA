package com.main.vespa.Controller;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.main.vespa.Model.MedidaProtetiva;
import com.main.vespa.Model.StatusMedida;
import com.main.vespa.Service.MedidaProtetivaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/medidas-protetivas")
@RequiredArgsConstructor
public class MedidaProtetivaController {

    private final MedidaProtetivaService service;

    @GetMapping
    public List<MedidaProtetiva> listar(@RequestParam(required = false) StatusMedida status) {
        return status == null ? service.listar() : service.listarPorStatus(status);
    }

    @GetMapping("/{id}")
    public MedidaProtetiva buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<MedidaProtetiva> criar(@RequestBody MedidaProtetiva medida) {
        MedidaProtetiva salva = service.salvar(medida);
        return ResponseEntity.created(URI.create("/medidas-protetivas/" + salva.getId())).body(salva);
    }

    @PutMapping("/{id}")
    public MedidaProtetiva atualizar(@PathVariable Long id, @RequestBody MedidaProtetiva medida) {
        return service.atualizar(id, medida);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // ---- alertas ----

    /** Medidas ativas que vencem nos próximos N dias (padrão: 7). */
    @GetMapping("/alertas/vencendo")
    public List<MedidaProtetiva> vencendo(@RequestParam(defaultValue = "7") int dias) {
        return service.listarVencendo(dias);
    }

    @GetMapping("/alertas/descumpridas")
    public List<MedidaProtetiva> descumpridas() {
        return service.listarDescumpridas();
    }

    /** Marca como VENCIDA as medidas ativas com data fim no passado. */
    @PostMapping("/atualizar-vencidas")
    public Map<String, Integer> atualizarVencidas() {
        return Map.of("atualizadas", service.atualizarVencidas());
    }

    @PatchMapping("/{id}/descumprida")
    public MedidaProtetiva marcarDescumprida(@PathVariable Long id) {
        return service.marcarDescumprida(id);
    }
}
