package com.main.vespa.Service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.main.vespa.Model.Agente;
import com.main.vespa.Repository.AgenteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgenteService {

    private final AgenteRepository repository;

    public List<Agente> listar() {
        return repository.findAll();
    }

    public Agente buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Agente não encontrado: " + id));
    }

    public Agente salvar(Agente agente) {
        if (repository.existsByMatricula(agente.getMatricula())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe agente com essa matrícula");
        }
        agente.setId(null);
        return repository.save(agente);
    }

    public Agente atualizar(Long id, Agente agente) {
        buscarPorId(id);
        agente.setId(id);
        return repository.save(agente);
    }

    public void deletar(Long id) {
        buscarPorId(id);
        repository.deleteById(id);
    }
}
