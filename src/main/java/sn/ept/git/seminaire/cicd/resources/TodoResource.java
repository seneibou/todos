package sn.ept.git.seminaire.cicd.resources;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import sn.ept.git.seminaire.cicd.dto.TodoDTO;
import sn.ept.git.seminaire.cicd.dto.vm.TodoVM;
import sn.ept.git.seminaire.cicd.models.Todo;
import sn.ept.git.seminaire.cicd.services.ITodoService;
import sn.ept.git.seminaire.cicd.utils.ResponseUtil;
import sn.ept.git.seminaire.cicd.utils.UrlMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
public class TodoResource {

    private final ITodoService service;

    public TodoResource(ITodoService service) {
        this.service = service;
    }

    @GetMapping(UrlMapping.Todo.ALL)
    public ResponseEntity<Page<TodoDTO>> findAll(
            @PageableDefault Pageable page
    ) {
        Page<TodoDTO> result = service.findAll(page);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(UrlMapping.Todo.FIND_BY_ID)
    public ResponseEntity<TodoDTO> findById(@PathVariable ("id") UUID id) {
        return ResponseUtil.wrapOrNotFound(service.findById(id),HttpStatus.OK);
    }

    @PostMapping(UrlMapping.Todo.ADD)
    public ResponseEntity<TodoDTO> create(@Valid @RequestBody TodoVM vm) {
        TodoDTO created = service.save(vm);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @DeleteMapping(UrlMapping.Todo.DELETE)
    public ResponseEntity<Todo> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(UrlMapping.Todo.UPDATE)
    public ResponseEntity<TodoDTO> update(
            @PathVariable("id") UUID id,
            @RequestBody @Valid TodoVM vm) {
        final TodoDTO dto = service.update(id, vm);
        Optional<TodoDTO> optional = Optional.ofNullable(dto);
        return ResponseUtil.wrapOrNotFound(optional,HttpStatus.ACCEPTED);
    }
}
