package com.main.vespa.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.vespa.Model.Ronda;
import com.main.vespa.Service.RelatorioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/relatorios")
@RequiredArgsConstructor
public class RelatorioController {

    private final RelatorioService service;

    @GetMapping("/resumo")
    public Map<String, Object> resumo() {
        return service.resumo();
    }

    @GetMapping("/descumprimentos")
    public List<Ronda> descumprimentos() {
        return service.casosDeDescumprimento();
    }

    @GetMapping("/rondas-por-local")
    public Map<String, Long> rondasPorLocal() {
        return service.rondasPorLocal();
    }
}
