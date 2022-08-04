package sn.ept.git.seminaire.cicd.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sn.ept.git.seminaire.cicd.data.TagDTOTestData;
import sn.ept.git.seminaire.cicd.dto.TagDTO;
import sn.ept.git.seminaire.cicd.mappers.TagMapper;
import sn.ept.git.seminaire.cicd.models.Tag;
import sn.ept.git.seminaire.cicd.repositories.TagRepository;
import java.util.Optional;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;

class TagRepositoryTest extends RepositoryBaseTest {

    @Autowired
    private TagMapper mapper;
    @Autowired
    private TagRepository repository;

    static TagDTO dto;
    Tag entity;
    Optional<Tag> optionalSociete;

    @BeforeAll
    static void beforeAll(){
        dto = TagDTOTestData.defaultDTO();
    }

    @BeforeEach
    void setUp() {
        entity = mapper.asEntity(dto);
        repository.deleteAll();
        entity = repository.saveAndFlush(entity);
    }

    @Test
    void findByName_shouldReturnResult() {
        optionalSociete = repository.findByName(entity.getName());
        assertThat(optionalSociete)
                .isNotNull()
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(entity);
    }

    @Test
    void findByName_withBadName_shouldReturnNotFound() {
        optionalSociete = repository.findByName(UUID.randomUUID().toString());
        assertThat(optionalSociete)
                .isNotNull()
                .isNotPresent();
    }

    @Test
    void findByName_afterDelete_shouldReturnNotFound() {
        entity.setDeleted(true);
        entity = repository.saveAndFlush(entity);
        optionalSociete = repository.findByName(entity.getName());
        assertThat(optionalSociete)
                .isNotNull()
                .isNotPresent();
    }


    @Test
    void findByNameWithIdNotEqual_shouldReturnResult() {
        optionalSociete = repository.findByNameWithIdNotEquals(entity.getName(),UUID.randomUUID());
        assertThat(optionalSociete)
                .isNotNull()
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(entity);
    }

    @Test
    void  findByNameWithIdNotEqual_withSameId_shouldReturnNoResult () {
        optionalSociete = repository.findByNameWithIdNotEquals(entity.getName(),entity.getId());
        assertThat(optionalSociete)
                .isNotNull()
                .isNotPresent();
    }


}