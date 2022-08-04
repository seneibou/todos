package sn.ept.git.seminaire.cicd.dto;

import lombok.NoArgsConstructor;
import sn.ept.git.seminaire.cicd.dto.base.TodoBaseDTO;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import sn.ept.git.seminaire.cicd.models.Tag;

import java.util.HashSet;
import java.util.Set;

@SuperBuilder
@Data
@NoArgsConstructor
public class TodoDTO extends TodoBaseDTO {

    private Set<TagDTO> tags ;
}