package com.techflow.tarefas.service;

import com.techflow.tarefas.model.StatusTarefa;
import com.techflow.tarefas.model.Tarefa;
import com.techflow.tarefas.repository.TarefaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TarefaService {

    private final TarefaRepository repository;

    public TarefaService(TarefaRepository repository) {
        this.repository = repository;
    }

    public Tarefa criarTarefa(Tarefa tarefa) {
        return repository.save(tarefa);
    }

    @Transactional(readOnly = true)
    public List<Tarefa> listarTodas() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Tarefa> listarConcluidas() {
        return repository.findAll().stream()
                .filter(t -> t.getStatus() == StatusTarefa.CONCLUIDO)
                .collect(Collectors.toList());
    }

    public Tarefa salvar(Tarefa tarefa) {
        return repository.save(tarefa);
    }

    public void concluirTarefa(Long id) {
        Tarefa tarefa = repository.findById(id).orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
        tarefa.setStatus(StatusTarefa.CONCLUIDO);
        repository.save(tarefa);
    }

    @Transactional(readOnly = true)
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
