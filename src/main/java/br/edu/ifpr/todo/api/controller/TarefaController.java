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
@RequestMapping("/api/tarefas")
public class TarefaController {

    private final TarefaService service;

    public TarefaController(TarefaService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TarefaResponse criar(@Valid @RequestBody TarefaRequest dto) {
        Tarefa salvo = service.criar(dto);
        return toResponse(salvo);
    }

    @GetMapping
    public List<Tarefa> listar(@RequestParam(required = false) TodoStatus status,
                               @RequestParam(required = false) Boolean importante) {
        return service.listar(status, importante);
    }

    @GetMapping("/{id}")
    public TarefaResponse buscar(@PathVariable Long id) {
        return toResponse(service.buscarPorId(id));
    }

    @PatchMapping("/{id}")
    public TarefaResponse atualizarParcial(@PathVariable Long id, @RequestBody TarefaRequest dto) {
        return toResponse(service.atualizarParcial(id, dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        service.remover(id);
    }

    private TarefaResponse toResponse(Tarefa t) {
        return new TarefaResponse(
                t.getId(),
                t.getNome(),
                t.getDescricao(),
                t.getStatus(),
                t.getDataCriacao(),
                t.getDataEntrega(),
                t.getImportante()
        );
    }
}
