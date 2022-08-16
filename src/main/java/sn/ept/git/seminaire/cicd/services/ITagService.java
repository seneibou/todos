package sn.ept.git.seminaire.cicd.services;

import sn.ept.git.seminaire.cicd.dto.TagDTO;
import sn.ept.git.seminaire.cicd.dto.vm.TagVM;
import sn.ept.git.seminaire.cicd.services.generic.GenericService;
import java.util.List;
import java.util.UUID;

public interface ITagService extends GenericService<TagDTO, TagVM, UUID> {

     List<TagDTO > addALL(List<TagVM > vms);
}