package sn.ept.git.seminaire.cicd.models;

import sn.ept.git.seminaire.cicd.utils.SizeMapping;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = BaseEntity.CLAUSE)


@Entity
@Table(name="acicd_tags")

public class Tag extends BaseEntity implements Serializable {

    // Name size from 2 to 50
    @NotBlank
    @Size(min = SizeMapping.Name.MIN,max = SizeMapping.Name.MAX)
    @Column(unique = true)
    private String name;

    // Description size from 0 to 255
    @Size(min = SizeMapping.Description.MIN,max = SizeMapping.Description.MAX)
    private  String description;


    @Where(clause = BaseEntity.CLAUSE)
    @ToString.Exclude
    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private Set<Todo> todos = new HashSet<>();


    public static Tag fromId(UUID id){
        return Tag
                .builder()
                .id(id)
                .build();
    }


}
