package br.edu.ifpr.todo.api.controller;

import br.edu.ifpr.todo.api.dto.TarefaRequest;
import br.edu.ifpr.todo.api.dto.TarefaResponse;
import br.edu.ifpr.todo.domain.model.Tarefa;
import br.edu.ifpr.todo.domain.model.TodoStatus;
import br.edu.ifpr.todo.domain.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    private final TarefaService service;

    public TarefaController(TarefaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Tarefa> listar(@RequestParam(required = false) String q,
                               @RequestParam(required = false) TodoStatus status,
                               @RequestParam(required = false) Boolean importante,
                               @RequestParam(required = false) String ate) {
        LocalDate data = ate != null ? LocalDate.parse(ate) : null;
        return service.listar(q, status, importante, data);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TarefaResponse criar(@Valid @RequestBody TarefaRequest dto) {
        Tarefa salvo = service.criar(dto);
        return new TarefaResponse(
                salvo.getId(),
                salvo.getNome(),
                salvo.getDescricao(),
                salvo.getStatus(),
                salvo.getDataCriacao(),
                salvo.getDataEntrega(),
                salvo.getImportante()
        );
    }
}
