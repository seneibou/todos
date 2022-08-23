package sn.ept.git.seminaire.cicd.dto.vm;

import lombok.NoArgsConstructor;
import sn.ept.git.seminaire.cicd.dto.base.TodoBaseDTO;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import java.util.Set;
import java.util.UUID;

@SuperBuilder
@Data
@NoArgsConstructor
public class TodoVM extends TodoBaseDTO {


    private Set<UUID> tags ;
}