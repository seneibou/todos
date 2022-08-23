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
import sn.ept.git.seminaire.cicd.repositories.TagRepository;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
class TagServiceTest extends ServiceBaseTest {


    @Autowired
    TagRepository repository;
    @Autowired
    ITagService service;

    TagVM vm;
   
    TagDTO dto;


    @BeforeAll
    static void beforeAll(){
        log.info(" before all");
    }

    @BeforeEach
     void beforeEach(){
       log.info(" before each");
        vm = TagVMTestData.defaultVM();
    }


    @Test
    void save_shouldSaveTag() {
        //A

        //act
        dto =service.save(vm);
        assertThat(dto)
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                ;
    }

    @Test
    void save_withSameName_shouldThrowException() {
        dto =service.save(vm);

        //vm.setName(dto.getName());
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
    void delete_shouldDeleteTag() {
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


    //java 8 requis,

    //vos tests ici
     @Test
     void addAll_shouldContainsElements(){
     List<TagVM> vms =  new LinkedList<TagVM>( );
     List<TagDTO> DTO_list =  new LinkedList<TagDTO>( );
     vms.add(vm);
     DTO_list = service.addALL(vms);
     assertThat(DTO_list)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
   
     }
}