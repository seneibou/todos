package sn.ept.git.seminaire.cicd.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import sn.ept.git.seminaire.cicd.data.TodoDTOTestData;
import sn.ept.git.seminaire.cicd.dto.TodoDTO;
import sn.ept.git.seminaire.cicd.mappers.TodoMapper;
import sn.ept.git.seminaire.cicd.models.Todo;

import static org.assertj.core.api.Assertions.assertThat;

class TodoDTOMapperTest {

    TodoDTO dto;
    Todo entity;

    private TodoMapper mapper=  Mappers.getMapper(TodoMapper.class);

    @BeforeEach
    void setUp() {
        dto = TodoDTOTestData.defaultDTO();
    }

    @Test
    void toEntityShouldReturnCorrectEntity() {
        entity = mapper.asEntity(dto);
        assertThat(entity)
                .isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("tags")
                .isEqualTo(dto);
    }

    @Test
    void toDTOShouldReturnCorrectDTO() {
        entity = mapper.asEntity(dto);
        dto =mapper.asDTO(entity);
        assertThat(dto)
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .ignoringFields("tags")
                .ignoringFieldsMatchingRegexes("^_")//just to discover
                .withEqualsForFields((idOne,idTwo)-> {
                    //use lambda
                    return  idOne instanceof String && idTwo.toString().equalsIgnoreCase(idOne.toString());
                },"id") //just to discover
                .isEqualTo(entity);

    }
}
