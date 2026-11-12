package com.main.vespa.Service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.main.vespa.Model.Agressor;
import com.main.vespa.Repository.AgressorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgressorService {

    private final AgressorRepository repository;

    public List<Agressor> listar() {
        return repository.findAll();
    }

    public Agressor buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Agressor não encontrado: " + id));
    }

    public Agressor salvar(Agressor agressor) {
        if (repository.existsByCpf(agressor.getCpf())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe agressor com esse CPF");
        }
        agressor.setId(null);
        return repository.save(agressor);
    }

    public Agressor atualizar(Long id, Agressor agressor) {
        buscarPorId(id);
        agressor.setId(id);
        return repository.save(agressor);
    }

    public void deletar(Long id) {
        buscarPorId(id);
        repository.deleteById(id);
    }
}
