package sn.ept.git.seminaire.cicd.dto.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@ToString
@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@EqualsAndHashCode
@SuperBuilder
public class BaseDTO implements Serializable {

    private UUID id;
    @JsonProperty(value = "created_date")
    private Instant createdDate ;

    @JsonProperty(value = "last_modified_date")
    private Instant lastModifiedDate;
    private int version;
    private boolean enabled ;
    private boolean deleted ;

}
