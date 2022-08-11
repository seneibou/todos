package sn.ept.git.seminaire.cicd.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import sn.ept.git.seminaire.cicd.data.TagVMTestData;
import sn.ept.git.seminaire.cicd.data.TestData;
import sn.ept.git.seminaire.cicd.dto.TagDTO;
import sn.ept.git.seminaire.cicd.dto.vm.TagVM;
import sn.ept.git.seminaire.cicd.exceptions.ItemExistsException;
import sn.ept.git.seminaire.cicd.exceptions.ItemNotFoundException;
import sn.ept.git.seminaire.cicd.mappers.TagMapper;
import sn.ept.git.seminaire.cicd.mappers.vm.TagVMMapper;
import sn.ept.git.seminaire.cicd.repositories.TagRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
class TagServiceTest extends ServiceBaseTest {

    @Autowired
    protected TagMapper mapper;
    @Autowired
    protected TagVMMapper vmMapper;
    @Autowired
    TagRepository repository;
    @Autowired
    ITagService service;
     static TagVM vm ;
    TagDTO dto;


    @BeforeAll
    static void beforeAll(){
        log.info(" before all");
        vm = TagVMTestData.defaultVM();
    }

    @BeforeEach
     void beforeEach(){
       log.info(" before each");
    }

    @Test
    void save_shouldSaveSociete() {
        dto =service.save(vm);
        assertThat(dto)
                .isNotNull()
                .hasNoNullFieldsOrProperties();
    }

    @Test
    void save_withSameName_shouldThrowException() {
        dto =service.save(vm);
        vm.setName(dto.getName());
        assertThrows(
                ItemExistsException.class,
                () -> service.save(vm)
        );
    }



    @Test
    void findById_shouldReturnResult() {
        dto =service.save(vm);
        final Optional<TagDTO> optional = service.findById(dto.getId());
        assertThat(optional)
                .isNotNull()
                .isPresent()
                .get()
                .hasNoNullFieldsOrProperties();
    }

    @Test
    void findById_withBadId_ShouldReturnNoResult() {
        final Optional<TagDTO> optional = service.findById(UUID.randomUUID());
        assertThat(optional)
                .isNotNull()
                .isNotPresent();
    }

    @Test
    void delete_shouldDeleteSociete() {
        dto = service.save(vm);
        long oldCount = repository.count();
        service.delete(dto.getId());
        long newCount = repository.count();
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




    @Test
    void update_shouldSucceed() {
        dto =service.save(vm);
        vm.setName(TestData.Update.name);
        vm.setDescription(TestData.Update.description);
        dto =  service.update(dto.getId(), vm);
        assertThat(dto)
                .isNotNull()
                .hasFieldOrPropertyWithValue("name",vm.getName())
                .hasFieldOrPropertyWithValue("description",vm.getDescription());
    }


    @Test
    void update_withBadId_shouldThrowException() {
        dto =service.save(vm);
        vm.setName(TestData.Update.name);
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
        final List<TagDTO> all = service.findAll();
        assertThat(all)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1)
                .contains(dto);
    }

    @Test
    void findAllPageable_shouldReturnResult() {
        dto =service.save(vm);
        final Page<TagDTO> all = service.findAll(PageRequest.of(0,10));
        assertThat(all)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1)
                .contains(dto);
    }


}