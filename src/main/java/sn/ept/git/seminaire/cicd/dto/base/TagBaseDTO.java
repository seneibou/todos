package sn.ept.git.seminaire.cicd.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import sn.ept.git.seminaire.cicd.utils.SizeMapping;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagBaseDTO extends BaseDTO {

    @NotBlank
    @Size(min = SizeMapping.Name.MIN,max = SizeMapping.Name.MAX)
    private  String name;

    @Size(min = SizeMapping.Description.MIN,max = SizeMapping.Description.MAX)
    private  String description;

}