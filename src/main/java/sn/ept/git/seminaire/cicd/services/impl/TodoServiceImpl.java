package sn.ept.git.seminaire.cicd.services.impl;

import sn.ept.git.seminaire.cicd.dto.TodoDTO;
import sn.ept.git.seminaire.cicd.dto.vm.TodoVM;
import sn.ept.git.seminaire.cicd.exceptions.ItemExistsException;
import sn.ept.git.seminaire.cicd.exceptions.ItemNotFoundException;
import sn.ept.git.seminaire.cicd.mappers.TodoMapper;
import sn.ept.git.seminaire.cicd.mappers.vm.TodoVMMapper;
import sn.ept.git.seminaire.cicd.models.Todo;
import sn.ept.git.seminaire.cicd.repositories.TagRepository;
import sn.ept.git.seminaire.cicd.repositories.TodoRepository;
import sn.ept.git.seminaire.cicd.services.ITodoService;
import sn.ept.git.seminaire.cicd.utils.ExceptionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TodoServiceImpl implements ITodoService {

    private final TodoRepository repository;
    private final TagRepository tagRepository;
    private final TodoMapper mapper;
    private final TodoVMMapper vmMapper;

    public TodoServiceImpl(TodoRepository repository, TagRepository tagRepository, TodoMapper mapper, TodoVMMapper vmMapper) {
        this.repository = repository;
        this.tagRepository = tagRepository;
        this.mapper = mapper;
        this.vmMapper = vmMapper;
    }

    @Transactional
    @Override
    public TodoDTO save(TodoVM vm) {
         Optional<Todo> optional = repository.findByTitle(vm.getTitle());
        ExceptionUtils.absentOrThrow(optional, ItemExistsException.TITLE_EXISTS, vm.getTitle());
        //should add tags
        return mapper.asDTO(repository.saveAndFlush(vmMapper.asEntity(vm)));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void delete(UUID uuid) {
        final Optional<Todo> optional = repository.findById(uuid);
        if(optional.isPresent()){
            final Todo todo = optional.get();
            todo.setDeleted(true);
            repository.saveAndFlush(todo);
            return;
        }
        throw new ItemNotFoundException(
                ItemNotFoundException.format(ItemNotFoundException.TODO_BY_ID, uuid.toString())
        );
    }

    @Override
    public Optional<TodoDTO> findById(UUID uuid) {
        return repository
                .findById(uuid)
                .map(mapper::asDTO);
    }

    @Override
    public List<TodoDTO> findAll() {
        return repository
                .findAll()
                .stream()
                .map(mapper::asDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<TodoDTO> findAll(Pageable pageable) {
        return repository
                .findAll(pageable)
                .map(mapper::asDTO);
    }

    @Transactional
    @Override
    public TodoDTO update(UUID uuid, TodoVM vm) {
         Optional<Todo>  optional = repository.findByTitleWithIdNotEquals(vm.getTitle(),uuid);
        ExceptionUtils.absentOrThrow(optional, ItemExistsException.TITLE_EXISTS, vm.getTitle());
        optional = repository.findById(uuid);
        if(optional.isPresent()){
            final Todo item = optional.get();
            item.setTitle(vm.getTitle());
            item.setDescription(vm.getDescription());
            item.setCompleted(vm.isCompleted());
            //should update tag
            return mapper.asDTO(repository.saveAndFlush(item));
        }
        throw new ItemNotFoundException(
                ItemNotFoundException.format(ItemNotFoundException.TODO_BY_ID, uuid.toString())
        );
    }

    @Transactional
    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

}
