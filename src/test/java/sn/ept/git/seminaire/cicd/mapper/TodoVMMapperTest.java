package sn.ept.git.seminaire.cicd.mapper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sn.ept.git.seminaire.cicd.data.TodoVMTestData;
import sn.ept.git.seminaire.cicd.dto.vm.TodoVM;
import sn.ept.git.seminaire.cicd.mappers.vm.TodoVMMapper;
import sn.ept.git.seminaire.cicd.models.Todo;

import static org.assertj.core.api.Assertions.assertThat;

class TodoVMMapperTest extends MapperBaseTest {

    static TodoVM vm;
    static Todo entity;

    @Autowired
    private TodoVMMapper mapper;

    @BeforeAll
    static void beforeAll() {
        vm = TodoVMTestData.defaultVM();
    }

    @Test
    void toEntityShouldReturnCorrectEntity() {
        entity = mapper.asEntity(vm);

        assertThat(entity)
                .isNotNull()
                .usingRecursiveComparison()
                .ignoringActualNullFields() //to discover
                .ignoringFields("tags")
                .isEqualTo(vm);
    }

    @Test
    void toDTOShouldReturnCorrectDTO() {
        entity = mapper.asEntity(vm);
        vm = mapper.asDTO(entity);
        assertThat(vm)
                .isNotNull()
                .hasNoNullFieldsOrProperties() //to discover
                .usingRecursiveComparison()
                .isEqualTo(entity);
    }
}