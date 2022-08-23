package sn.ept.git.seminaire.cicd.mappers.vm;

import org.mapstruct.Mapping;
import org.mapstruct.Named;
import sn.ept.git.seminaire.cicd.dto.vm.TodoVM;
import sn.ept.git.seminaire.cicd.mappers.generic.GenericMapper;
import sn.ept.git.seminaire.cicd.models.Tag;
import sn.ept.git.seminaire.cicd.models.Todo;
import org.mapstruct.Mapper;

import java.util.*;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface TodoVMMapper extends GenericMapper<Todo, TodoVM> {


    @Override
    @Mapping(source = "tags", target = "tags", qualifiedByName = "asEntityQualifier")
    Todo asEntity(TodoVM vm);

    @Override
    @Mapping(source = "tags", target = "tags", qualifiedByName = "asDTOQualifier")
    TodoVM asDTO(Todo todo);


    @Named("asEntityQualifier")
     static Set<Tag>  asEntityQualifier(Set<UUID> tags ) {
        return Optional
                .ofNullable(tags)
                .orElse(new HashSet<>())
                .stream()
                .map(Tag::fromId)
                .collect(Collectors.toSet());
    }

    @Named("asDTOQualifier")
    static Set<UUID>  asDTOQualifier( Set<Tag>   tags ) {
        return Optional
                .ofNullable(tags)
                .orElse(new HashSet<>())
                .stream()
                .map(Tag::getId)
                .collect(Collectors.toSet());
    }


}