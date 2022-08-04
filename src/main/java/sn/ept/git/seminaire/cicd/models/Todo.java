package sn.ept.git.seminaire.cicd.models;

import org.hibernate.annotations.DynamicUpdate;
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

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "acicd_todos")
@Where(clause = BaseEntity.CLAUSE)
@DynamicUpdate
public class Todo extends BaseEntity implements Serializable {

    @NotBlank
    @Size(min = SizeMapping.Title.MIN, max = SizeMapping.Title.MAX)
    @Column(unique = true)
    private String title;

    @Size(min = SizeMapping.Description.MIN, max = SizeMapping.Description.MAX)
    private String description;

    private boolean completed;


    @Where(clause = BaseEntity.CLAUSE)
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "tags_todos",
            joinColumns = @JoinColumn(name = "id_todo"),
            inverseJoinColumns = @JoinColumn(name = "id_tag")
    )
    private Set<Tag> tags = new HashSet<>();
    


    public Todo updateWith(Todo todo) {
        return Todo
                .builder()
                .id(this.getId())
                .title(todo.getTitle())
                .description(todo.getDescription())
                .completed(isCompleted())
                .build();

    }

}
