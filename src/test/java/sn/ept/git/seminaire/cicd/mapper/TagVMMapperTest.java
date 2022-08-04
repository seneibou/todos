package sn.ept.git.seminaire.cicd.mapper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sn.ept.git.seminaire.cicd.data.TagVMTestData;
import sn.ept.git.seminaire.cicd.dto.vm.TagVM;
import sn.ept.git.seminaire.cicd.mappers.vm.TagVMMapper;
import sn.ept.git.seminaire.cicd.models.Tag;

import static org.assertj.core.api.Assertions.assertThat;

class TagVMMapperTest extends MapperBaseTest {

    static TagVM vm;
    static Tag entity;

    @Autowired
    private TagVMMapper mapper;

    @BeforeAll
    static void beforeAll() {
        vm = TagVMTestData.defaultVM();
    }

    @Test
    void toEntityShouldReturnCorrectEntity() {
        entity = mapper.asEntity(vm);

        assertThat(entity)
                .isNotNull()
                .usingRecursiveComparison()
                .ignoringActualNullFields()
                .isEqualTo(vm);
    }

    @Test
    void toDTOShouldReturnCorrectDTO() {
        entity = mapper.asEntity(vm);
        vm = mapper.asDTO(entity);
        assertThat(vm)
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(entity);
    }
}