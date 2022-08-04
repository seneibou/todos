package sn.ept.git.seminaire.cicd.services;

import sn.ept.git.seminaire.cicd.dto.TodoDTO;
import sn.ept.git.seminaire.cicd.dto.vm.TodoVM;
import sn.ept.git.seminaire.cicd.services.generic.GenericService;
import java.util.UUID;


public interface ITodoService extends GenericService<TodoDTO, TodoVM, UUID> {

}
