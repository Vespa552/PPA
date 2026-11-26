package com.main.vespa.Service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.main.vespa.Model.Vitima;
import com.main.vespa.Repository.VitimaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VitimaService {

    private final VitimaRepository repository;

    public List<Vitima> listar() {
        return repository.findAll();
    }

    public Vitima buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vítima não encontrada: " + id));
    }

    public Vitima salvar(Vitima vitima) {
        if (repository.existsByCpf(vitima.getCpf())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe vítima com esse CPF");
        }
        vitima.setId(null);
        return repository.save(vitima);
    }

    public Vitima atualizar(Long id, Vitima vitima) {
        buscarPorId(id);
        vitima.setId(id);
        return repository.save(vitima);
    }

    public void deletar(Long id) {
        buscarPorId(id);
        repository.deleteById(id);
    }
}
