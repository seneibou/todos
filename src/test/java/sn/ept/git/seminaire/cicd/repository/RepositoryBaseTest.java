package sn.ept.git.seminaire.cicd.repository;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import sn.ept.git.seminaire.cicd.TodoApplication;


@SpringBootTest(classes = {TodoApplication.class})
@Transactional
class RepositoryBaseTest { }