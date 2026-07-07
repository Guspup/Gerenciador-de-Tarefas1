package com.techflow.tarefas.service;

import com.techflow.tarefas.model.PrioridadeTarefa;
import com.techflow.tarefas.model.StatusTarefa;
import com.techflow.tarefas.model.Tarefa;
import com.techflow.tarefas.repository.TarefaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TarefaServiceTest {

    @Mock
    private TarefaRepository repository;

    @InjectMocks
    private TarefaService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCriarTarefa() {
        Tarefa tarefa = new Tarefa("Tarefa 1", "Descrição", StatusTarefa.A_FAZER, PrioridadeTarefa.MEDIA);
        when(repository.save(any(Tarefa.class))).thenReturn(tarefa);

        Tarefa resultado = service.criarTarefa(tarefa);

        assertNotNull(resultado);
        assertEquals("Tarefa 1", resultado.getTitulo());
        assertEquals(PrioridadeTarefa.MEDIA, resultado.getPrioridade());
        verify(repository, times(1)).save(tarefa);
    }

    @Test
    void deveListarTodas() {
        when(repository.findAll()).thenReturn(Arrays.asList(new Tarefa(), new Tarefa()));

        List<Tarefa> resultado = service.listarTodas();

        assertEquals(2, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void deveSalvarTarefa() {
        Tarefa tarefa = new Tarefa("Salvar", "Desc", StatusTarefa.A_FAZER, PrioridadeTarefa.BAIXA);
        when(repository.save(any(Tarefa.class))).thenReturn(tarefa);

        Tarefa resultado = service.salvar(tarefa);

        assertNotNull(resultado);
        verify(repository, times(1)).save(tarefa);
    }

    @Test
    void deveConcluirTarefa() {
        Tarefa tarefa = new Tarefa("Concluir", "Desc", StatusTarefa.A_FAZER, PrioridadeTarefa.BAIXA);
        when(repository.findById(1L)).thenReturn(Optional.of(tarefa));

        service.concluirTarefa(1L);

        assertEquals(StatusTarefa.CONCLUIDO, tarefa.getStatus());
        verify(repository).save(tarefa);
    }

    @Test
    void deveBuscarPorId() {
        Tarefa tarefa = new Tarefa("Tarefa", "Desc", StatusTarefa.A_FAZER, PrioridadeTarefa.BAIXA);
        when(repository.findById(1L)).thenReturn(Optional.of(tarefa));

        Optional<Tarefa> resultado = service.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Tarefa", resultado.get().getTitulo());
    }

    @Test
    void deveAtualizarTarefa() {
        Tarefa tarefaExistente = new Tarefa("Antiga", "Desc", StatusTarefa.A_FAZER, PrioridadeTarefa.BAIXA);
        Tarefa novaTarefa = new Tarefa("Nova", "Desc", StatusTarefa.CONCLUIDO, PrioridadeTarefa.ALTA);
        
        when(repository.existsById(1L)).thenReturn(true);
        when(repository.save(any(Tarefa.class))).thenReturn(novaTarefa);

        Tarefa resultado = service.atualizarTarefa(1L, novaTarefa);

        assertNotNull(resultado);
        assertEquals("Nova", resultado.getTitulo());
        assertEquals(PrioridadeTarefa.ALTA, resultado.getPrioridade());
        verify(repository).save(novaTarefa);
    }

    @Test
    void deveListarConcluidas() {
        Tarefa t1 = new Tarefa("T1", "Desc", StatusTarefa.CONCLUIDO, PrioridadeTarefa.ALTA);
        Tarefa t2 = new Tarefa("T2", "Desc", StatusTarefa.A_FAZER, PrioridadeTarefa.BAIXA);
        when(repository.findAll()).thenReturn(Arrays.asList(t1, t2));

        List<Tarefa> resultado = service.listarConcluidas();

        assertEquals(1, resultado.size());
        assertEquals(StatusTarefa.CONCLUIDO, resultado.get(0).getStatus());
        verify(repository, times(1)).findAll();
    }

    @Test
    void deveDeletarTarefa() {
        service.deletarTarefa(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}
