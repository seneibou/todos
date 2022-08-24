package sn.ept.git.seminaire.cicd.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import sn.ept.git.seminaire.cicd.data.TestData;
import sn.ept.git.seminaire.cicd.data.TodoDTOTestData;
import sn.ept.git.seminaire.cicd.data.TodoVMTestData;
import sn.ept.git.seminaire.cicd.dto.TodoDTO;
import sn.ept.git.seminaire.cicd.dto.vm.TodoVM;
import sn.ept.git.seminaire.cicd.exceptions.ItemExistsException;
import sn.ept.git.seminaire.cicd.exceptions.ItemNotFoundException;
import sn.ept.git.seminaire.cicd.mappers.TodoMapper;
import sn.ept.git.seminaire.cicd.mappers.vm.TodoVMMapper;
import sn.ept.git.seminaire.cicd.models.Todo;
import sn.ept.git.seminaire.cicd.repositories.TodoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/*@SqlGroup({
        @Sql("classpath:0_todo_data_test.sql"),
        @Sql("classpath:1_todo_data_test.sql"),
})*/
@Slf4j
class TodoServiceTest extends ServiceBaseTest {

    @Autowired
    TodoRepository todoRepository;
    @Autowired
    ITodoService service;

      TodoVM vm ;
    TodoDTO dto;
    //should consider tags in add and update methods


    @BeforeAll
    static void beforeAll(){
        log.info(" before all");
    }

    @BeforeEach
     void beforeEach(){
       log.info(" before each");
        vm = TodoVMTestData.defaultVM();
    }

    @Test
    void save_shouldSaveTodo() {
        dto =service.save(vm);
        assertThat(dto)
                .isNotNull()
                .hasNoNullFieldsOrProperties();
    }

    @Test
    void save_withSameName_shouldThrowException() {
        dto =service.save(vm);
        vm.setTitle(dto.getTitle());
        assertThrows(
                ItemExistsException.class,
                () -> service.save(vm)
        );
    }


    @Test
    void update_shouldSucceed() {
        dto =service.save(vm);
        vm.setTitle(TestData.Update.title);
        vm.setDescription(TestData.Update.description);
        dto =  service.update(dto.getId(), vm);
        assertThat(dto)
                .isNotNull()
                .hasFieldOrPropertyWithValue("title",vm.getTitle())
                .hasFieldOrPropertyWithValue("description",vm.getDescription());
    }


    @Test
    void update_withBadId_shouldThrowException() {
        dto =service.save(vm);
        vm.setTitle(TestData.Update.title);
        vm.setDescription(TestData.Update.description);
        UUID id =UUID.randomUUID();
        assertThrows(
                ItemNotFoundException.class,
                () ->service.update(id, vm)
        );
    }

    @Test
    void update_withDuplicatedName_shouldThrowException() {
        dto =service.save(vm);
        UUID id =UUID.randomUUID();
        assertThrows(
                ItemExistsException.class,
                () ->service.update(id, vm)
        );
    }


    @Test
    void findAll_shouldReturnResult() {
        dto =service.save(vm);
        final List<TodoDTO> all = service.findAll();
        assertThat(all)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1)
                .contains(dto);
    }

    @Test
    void findAllPageable_shouldReturnResult() {
        dto =service.save(vm);
        final Page<TodoDTO> all = service.findAll(PageRequest.of(0,10));
        assertThat(all)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1)
                .contains(dto);
    }

    @Test
    void findById_shouldReturnResult() {
        dto =service.save(vm);
        final Optional<TodoDTO> optional = service.findById(dto.getId());
        assertThat(optional)
                .isNotNull()
                .isPresent()
                .get()
                .hasNoNullFieldsOrProperties();
    }

    @Test
    void findById_withBadId_ShouldReturnNoResult() {
        final Optional<TodoDTO> optional = service.findById(UUID.randomUUID());
        assertThat(optional)
                .isNotNull()
                .isNotPresent();
    }

    @Test
    void delete_shouldDeleteTodo() {
        dto = service.save(vm);
        long oldCount = todoRepository.count();
        service.delete(dto.getId());
        long newCount = todoRepository.count();
        assertThat(oldCount).isEqualTo(newCount+1);
    }

    @Test
    void delete_withBadId_ShouldThrowException() {
        UUID id = UUID.randomUUID();
        assertThrows(
                ItemNotFoundException.class,
                () ->service.delete(id)
        );
    }


    //java 8 requis,

    //vos tests ici
    @Test
    void complete_shouldSucceed() {
        dto =service.save(vm);
        vm.setTitle(TestData.Complete.title);
        vm.setDescription(TestData.Complete.description);
        dto =  service.complete(dto.getId(), vm);
        assertThat(dto)
                .isNotNull()
                .hasFieldOrPropertyWithValue("title",vm.getTitle())
                .hasFieldOrPropertyWithValue("description",vm.getDescription());
    }

    @Test
    void complete_withBadId_ShouldThrowException() {
        UUID id = UUID.randomUUID();
        assertThrows(
                ItemNotFoundException.class,
                () ->service.complete(id)
        );
    }

}