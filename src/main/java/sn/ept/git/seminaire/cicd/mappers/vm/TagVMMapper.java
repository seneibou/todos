package sn.ept.git.seminaire.cicd.mappers.vm;

import sn.ept.git.seminaire.cicd.dto.vm.TagVM;
import sn.ept.git.seminaire.cicd.mappers.generic.GenericMapper;
import sn.ept.git.seminaire.cicd.models.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagVMMapper extends GenericMapper<Tag, TagVM> {

}