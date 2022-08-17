package sn.ept.git.seminaire.cicd.repositories;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sn.ept.git.seminaire.cicd.data.TodoDTOTestData;
import sn.ept.git.seminaire.cicd.dto.TodoDTO;
import sn.ept.git.seminaire.cicd.mappers.TodoMapper;
import sn.ept.git.seminaire.cicd.models.Todo;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class TodoRepositoryTest extends RepositoryBaseTest {

    @Autowired
    private TodoMapper mapper;
    @Autowired
    private TodoRepository repository;

    static TodoDTO dto;
    Todo entity;
    Optional<Todo> optionalTodo;
    //should consider tags

    @BeforeAll
    static void beforeAll(){
        dto = TodoDTOTestData.defaultDTO();
    }

    @BeforeEach
    void setUp() {
        entity = mapper.asEntity(dto);
        repository.deleteAll();
        entity = repository.saveAndFlush(entity);
    }

    @Test
    void findByName_shouldReturnResult() {
        optionalTodo = repository.findByTitle(entity.getTitle());
        assertThat(optionalTodo)
                .isNotNull()
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(entity);
    }

    @Test
    void findByName_withBadName_shouldReturnNotFound() {
        optionalTodo = repository.findByTitle(UUID.randomUUID().toString());
        assertThat(optionalTodo)
                .isNotNull()
                .isNotPresent();
    }

    @Test
    void findByName_afterDelete_shouldReturnNotFound() {
        entity.setDeleted(true);
        entity = repository.saveAndFlush(entity);
        optionalTodo = repository.findByTitle(entity.getTitle());
        assertThat(optionalTodo)
                .isNotNull()
                .isNotPresent();
    }


    @Test
    void findByNameWithIdNotEqual_shouldReturnResult() {
        optionalTodo = repository.findByTitleWithIdNotEquals(entity.getTitle(),UUID.randomUUID());
        assertThat(optionalTodo)
                .isNotNull()
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(entity);
    }

    @Test
    void  findByNameWithIdNotEqual_withSameId_shouldReturnNoResult () {
        optionalTodo = repository.findByTitleWithIdNotEquals(entity.getTitle(),entity.getId());
        assertThat(optionalTodo)
                .isNotNull()
                .isNotPresent();
    }


}