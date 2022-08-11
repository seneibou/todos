package sn.ept.git.seminaire.cicd.dto.base;

import sn.ept.git.seminaire.cicd.utils.SizeMapping;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoBaseDTO extends BaseDTO {

    @NotBlank
    @Size(min = SizeMapping.Title.MIN, max = SizeMapping.Title.MAX)
    private String title;

    @Size(min = SizeMapping.Description.MIN, max = SizeMapping.Description.MAX)
    private String description;

    private boolean completed;

}