package com.techflow.tarefas.cli;

import com.techflow.tarefas.model.PrioridadeTarefa;
import com.techflow.tarefas.model.StatusTarefa;
import com.techflow.tarefas.model.Tarefa;
import com.techflow.tarefas.service.TarefaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class CommandLineUI implements CommandLineRunner {

    private final TarefaService tarefaService;
    private final Scanner scanner = new Scanner(System.in);

    public CommandLineUI(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @Override
    public void run(String... args) {
        boolean running = true;
        while (running) {
            System.out.println("\n=== GERENCIADOR DE TAREFAS (KANBAN) ===");
            System.out.println("1. Listar todas as tarefas");
            System.out.println("2. Criar nova tarefa");
            System.out.println("3. Concluir tarefa");
            System.out.println("4. Listar tarefas concluídas");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1" -> exibirTarefas(tarefaService.listarTodas());
                case "2" -> criarTarefa();
                case "3" -> concluirTarefa();
                case "4" -> exibirTarefas(tarefaService.listarConcluidas());
                case "0" -> running = false;
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void exibirTarefas(List<Tarefa> tarefas) {
        if (tarefas.isEmpty()) {
            System.out.println("\nNenhuma tarefa encontrada.");
            return;
        }
        System.out.println("\n" + "=".repeat(80));
        System.out.printf("%-5s | %-20s | %-15s | %-10s%n", "ID", "TÍTULO", "STATUS", "PRIORIDADE");
        System.out.println("-".repeat(80));
        for (Tarefa t : tarefas) {
            System.out.printf("%-5d | %-20s | %-15s | %-10s%n", 
                t.getId(), t.getTitulo(), t.getStatus(), t.getPrioridade());
        }
        System.out.println("=".repeat(80));
    }

    private void criarTarefa() {
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();
        
        Tarefa novaTarefa = new Tarefa(titulo, descricao, StatusTarefa.A_FAZER, PrioridadeTarefa.MEDIA);
        tarefaService.salvar(novaTarefa);
        System.out.println("Tarefa criada com sucesso! ID atribuído automaticamente.");
    }

    private void concluirTarefa() {
        System.out.print("ID da tarefa para concluir: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            tarefaService.concluirTarefa(id);
            System.out.println("Tarefa " + id + " marcada como concluída!");
        } catch (NumberFormatException e) {
            System.out.println("Erro: O ID deve ser um número.");
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
