package com.techflow.tarefas.service;

import com.techflow.tarefas.model.Tarefa;
import com.techflow.tarefas.repository.TarefaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {

    private final TarefaRepository repository;

    public TarefaService(TarefaRepository repository) {
        this.repository = repository;
    }

    public Tarefa criarTarefa(Tarefa tarefa) {
        return repository.save(tarefa);
    }

    public List<Tarefa> listarTarefas() {
        return repository.findAll();
    }

    public Optional<Tarefa> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Tarefa atualizarTarefa(Long id, Tarefa tarefaAtualizada) {
        if (repository.existsById(id)) {
            tarefaAtualizada.setId(id);
            return repository.save(tarefaAtualizada);
        }
        return null;
    }

    public void deletarTarefa(Long id) {
        repository.deleteById(id);
    }
}
