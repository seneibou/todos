package sn.ept.git.seminaire.cicd.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import sn.ept.git.seminaire.cicd.TodoApplication;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(classes = {TodoApplication.class})
@Transactional
class RepositoryBaseTest {

    @Test
    void contextLoads()
    {
        assertThat(Boolean.TRUE).isTrue();
    }
}