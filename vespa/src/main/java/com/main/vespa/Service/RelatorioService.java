package com.main.vespa.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.main.vespa.Model.Ronda;
import com.main.vespa.Model.SituacaoRonda;
import com.main.vespa.Repository.RondaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RelatorioService {

    private final RondaRepository rondaRepository;

    public Map<String, Object> resumo() {
        Map<String, Object> resumo = new LinkedHashMap<>();
        resumo.put("totalRondas", rondaRepository.count());
        resumo.put("cumprimentos", rondaRepository.countBySituacao(SituacaoRonda.CUMPRIMENTO));
        resumo.put("descumprimentos", rondaRepository.countBySituacao(SituacaoRonda.DESCUMPRIMENTO));
        resumo.put("acompanhamentos", rondaRepository.countBySituacao(SituacaoRonda.ACOMPANHAMENTO));
        return resumo;
    }

    public List<Ronda> casosDeDescumprimento() {
        return rondaRepository.findBySituacao(SituacaoRonda.DESCUMPRIMENTO);
    }

    /** Estatística por região: quantidade de rondas por local. */
    public Map<String, Long> rondasPorLocal() {
        Map<String, Long> resultado = new LinkedHashMap<>();
        for (Object[] linha : rondaRepository.contarPorLocal()) {
            String local = linha[0] == null ? "(não informado)" : (String) linha[0];
            resultado.put(local, (Long) linha[1]);
        }
        return resultado;
    }
}
